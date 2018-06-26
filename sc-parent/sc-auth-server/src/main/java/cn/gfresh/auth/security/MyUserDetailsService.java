package cn.gfresh.auth.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;

import cn.gfresh.auth.client.Role;
import cn.gfresh.auth.client.UserClient;
import cn.gfresh.auth.configuration.Response;

@Component
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserClient userClient;

	/**
	 * 重写此方法从数据库加载用户信息
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = user = new User(userId, System.currentTimeMillis() + "", obtionGrantedAuthorities(userId));

		return user;
	}

	private Set<GrantedAuthority> obtionGrantedAuthorities(String userId) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();

		Response response = userClient.getByUserId(userId);

		String sss = JSON.toJSONString(response.getData());
		List<Role> list = new ArrayList<>();
		list = JSON.parseArray(sss, Role.class);

		if(!ObjectUtils.isEmpty(list) && list.size() > 0){
			for (int i = 0, len = list.size(); i < len; i++) {
				Role role = list.get(i);
				
				authSet.add(new SimpleGrantedAuthority(role.getRoleCode().toUpperCase()));
			}
		}
		
		authSet.add(new SimpleGrantedAuthority("ROLE_USER"));

		return authSet;
	}
}
