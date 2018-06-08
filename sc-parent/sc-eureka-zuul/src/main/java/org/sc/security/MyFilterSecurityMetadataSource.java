package org.sc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import org.sc.client.Privilege;
import org.sc.client.RolePrivilegeVo;
import org.sc.client.SecurityConfigVo;
import org.sc.client.UserClient;
import org.sc.controller.Response;
import org.sc.service.RedisService;

/**
 * Created by admin on 2017/5/11.
 */
@Service
public class MyFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserClient userClient;

	@Autowired
	private RedisService redisService;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();

	private static Map<Pattern, Collection<ConfigAttribute>> resourceRegxMap = new HashMap<>();

	public MyFilterSecurityMetadataSource() {
		System.out.println("metadatasource init -----------------------------");
		// loadResourceDefine();
	}

	public void loadResource() {
		loadResourceDefine();
	}

	public Boolean refreshResource() {
		resourceMap = null;
		resourceRegxMap = null;

		loadResourceDefine();
		return true;
	}

	/**
	 * 被@PostConstruct修饰的方法会在服务器加载Server的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
	 * 如果需要在运行时就把路径资源加载进来 则在运行的方法上加@PostConstruct注解
	 *
	 * @param object
	 * @return
	 */

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) {
		/** 通过fegin来加载对应的授权url信息 */
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();

		if (request.getMethod().equals("OPTIONS")) {
			Collection<ConfigAttribute> configAttributes = new ArrayList<>();
			configAttributes.add(new SecurityConfig("ROLE_ANONYMOUS"));
			return configAttributes;
		}

		String url = request.getRequestURI().toString();

		logger.info("request url :" + url);

		if (url.contains("/uaa/") || url.endsWith("/login") || url.endsWith("/loginOk")
				|| url.endsWith("/serverMaintenance")) {
			return resourceMap.get("ROLE_UAA_ACCESS");
		}

		if (url.contains("/favicon.ico")) {
			Collection<ConfigAttribute> configAttributes = new ArrayList<>();
			configAttributes.add(new SecurityConfig("ROLE_ANONYMOUS"));
			return configAttributes;
		}

		Collection<ConfigAttribute> caList = doFilterWithUrl(url);

		if (caList.isEmpty()) {
			// 查询固定的key是否存在 如果不存在 则需要全局拉取授权
			if (redisService.getList("/api/order/**", SecurityConfigVo.class).isEmpty()) {
				loadResourceDefine();
				caList = doFilterWithUrl(url);
			}

			if (caList.isEmpty()) {
				SecurityConfig sc = new SecurityConfig("ROLE_USER");

				caList.add(sc);
			}
		}

		return caList;
	}

	private Collection<ConfigAttribute> doFilterWithUrl(String url) {
		List<SecurityConfigVo> caList = redisService.getList(url, SecurityConfigVo.class);

		// 如果找不到对应的权限 则修改成通配符形式的
		if (caList == null) {
			Pattern pattern = Pattern.compile("(/\\w*/\\w*/)");
			Matcher m = pattern.matcher(url);
			if (m.find()) {
				url = m.group(0) + "**";
			}

			caList = redisService.getList(url, SecurityConfigVo.class);
		}

		if (caList != null) {
			Collection<ConfigAttribute> configAttributes = new ArrayList<>();

			for (int i = 0, len = caList.size(); i < len; i++) {
				SecurityConfig sc = new SecurityConfig(caList.get(i).getAttribute());

				configAttributes.add(sc);
			}

			return configAttributes;
		}

		return new ArrayList<>();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		/** 把login路径放到授权列表中 */
		/*
		 * Collection<ConfigAttribute> configAttributes = new ArrayList<>();
		 * configAttributes.add(new SecurityConfig(""));
		 */
		Set<ConfigAttribute> allAttributes = new HashSet();
		Iterator var2 = this.resourceMap.entrySet().iterator();

		while (var2.hasNext()) {
			Map.Entry<String, Collection<ConfigAttribute>> entry = (Map.Entry) var2.next();
			allAttributes.addAll((Collection) entry.getValue());
		}

		return allAttributes;

	}

	@SuppressWarnings("rawtypes")
	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceRegxMap = new HashMap<>();
			resourceMap = new HashMap<>();
		}

		Collection<ConfigAttribute> configAttributes = new ArrayList<>();
		configAttributes.add(new SecurityConfig("ROLE_UAA_ACCESS"));
		resourceMap.put("ROLE_UAA_ACCESS", configAttributes);

		Response response = userClient.getRolePrivilege();

		String sss = JSON.toJSONString(response.getData());
		List<RolePrivilegeVo> list = new ArrayList<>();
		list = JSON.parseArray(sss, RolePrivilegeVo.class);

		Map<String, List<Privilege>> allMap = new HashMap<>();
		for (int i = 0, len = list.size(); i < len; i++) {
			RolePrivilegeVo rpv = list.get(i);

			List<Privilege> privilegeList = getPrivilegeList(list, rpv.getId());

			privilegeList.addAll(rpv.getPrivileges());

			if (!StringUtils.isEmpty(rpv.getRoleCode())) {
				if (ObjectUtils.isEmpty(allMap.get(rpv.getRoleCode().toUpperCase()))) {
					allMap.put(rpv.getRoleCode().toUpperCase(), privilegeList);
				} else {
					allMap.get(rpv.getRoleCode().toUpperCase()).addAll(privilegeList);
				}
			}
		}

		Iterator<Entry<String, List<Privilege>>> iter = allMap.entrySet().iterator();

		Map<String, Collection<ConfigAttribute>> authMap = new HashMap<>();

		while (iter.hasNext()) {
			Entry<String, List<Privilege>> entity = iter.next();

			List<Privilege> tmpList = entity.getValue();

			for (Privilege privilege : tmpList) {
				if (StringUtils.isEmpty(privilege.getUrl())) {
					continue;
				}

				if (authMap.get(privilege.getUrl()) != null) {
					Collection<ConfigAttribute> ca = authMap.get(privilege.getUrl());

					SecurityConfig sc = new SecurityConfig(entity.getKey());
					ca.add(sc);
				} else {
					Collection<ConfigAttribute> ca = new ArrayList<>();
					SecurityConfig sc = new SecurityConfig(entity.getKey());

					ca.add(sc);

					authMap.put(privilege.getUrl().trim(), ca);
				}
			}
		}

		// 如果包含*则证明有正则 使用正则来匹配
		Iterator<Entry<String, Collection<ConfigAttribute>>> authIter = authMap.entrySet().iterator();

		while (authIter.hasNext()) {
			Entry<String, Collection<ConfigAttribute>> entity = authIter.next();

			String tmpStr = entity.getKey();

			if (tmpStr.indexOf("**") != -1) {
				Pattern pattern = Pattern.compile(tmpStr.replaceAll("\\*\\*", ".\\*"), Pattern.CASE_INSENSITIVE);

				resourceRegxMap.put(pattern, entity.getValue());
			} else {
				resourceMap.put(tmpStr, entity.getValue());
			}

			redisService.setList(tmpStr, (List<ConfigAttribute>) entity.getValue());
		}

	}

	private List<Privilege> getPrivilegeList(List<RolePrivilegeVo> list, String parentId) {
		List<Privilege> privilegeList = new ArrayList<>();

		for (int i = 0, len = list.size(); i < len; i++) {
			RolePrivilegeVo rpv = list.get(i);
			if (StringUtils.isEmpty(rpv.getParentId())) {
				continue;
			} else {
				if (rpv.getParentId().equals(parentId)) {
					privilegeList.addAll(rpv.getPrivileges());
					privilegeList.addAll(getPrivilegeList(list, rpv.getId()));
				}
			}
		}

		return privilegeList;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	public static void main(String[] args) {
		String url = "/api/order/sadf/asdga";
		Pattern pattern = Pattern.compile("(/\\w*/\\w*/)");
		Matcher m = pattern.matcher(url);
		if (m.find()) {
			System.out.println(m.group(0));
		}
	}
}
