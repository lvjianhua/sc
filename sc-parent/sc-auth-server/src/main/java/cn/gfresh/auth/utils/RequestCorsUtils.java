package cn.gfresh.auth.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import cn.gfresh.auth.configuration.AuthProp;

public class RequestCorsUtils {
	public static void convertCorsHead(HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(request.getHeader("Access-Control-Allow-Origin"))) {
			// response.setHeader("Access-Control-Allow-Origin", "*");
			if (StringUtils.isEmpty(request.getHeader("Origin"))) {
				response.setHeader("Access-Control-Allow-Origin", "*");
			} else if (checkOrigin(getOrigins(), request.getHeader("Origin"))) {
				System.out.println("origin is=====================" + request.getHeader("Origin"));
				response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			}
		}

		if (StringUtils.isEmpty(request.getHeader("Access-Control-Max-Age"))) {
			response.setHeader("Access-Control-Max-Age", (30 * 24 * 60 * 60) + "");
		}

		if (StringUtils.isEmpty(request.getHeader("Access-Control-Allow-Methods"))) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		}
		if (StringUtils.isEmpty(request.getHeader("Access-Control-Allow-Headers"))) {
			response.setHeader("Access-Control-Allow-Headers",
					"Origin, X-Requested-With, Content-Type, Accept, accept-encoding, origin, accept-language");
		}
		if (StringUtils.isEmpty(request.getHeader("Access-Control-Allow-Credentials"))) {
			response.setHeader("Access-Control-Allow-Credentials", "true");
		}
		if (!StringUtils.isEmpty(request.getHeader("Accept")) && request.getHeader("Accept").equals("*/*")) {
			response.setHeader("Accept", "application/json, text/plain, */*");
		}

	}

	public static String isContainPortIp(String ipAddress) {
		String ip = "((\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\:\\d{1,5})";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);

		while (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	public static String isContainIp(String ipAddress) {
		String ip = "((\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3}))";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);

		while (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	public static String getScheme(String url) {
		String ip = "\\w.*://";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(url);

		while (matcher.find()) {
			return matcher.group(0).replaceAll("://", "");
		}
		return "";
	}

	public static String[] getOrigins() {
		AuthProp authProp = (AuthProp) SpringContextUtils.getBean(AuthProp.class);
		return authProp.getAllowOrigin().split(",");
	}

	public static boolean checkOrigin(String[] origins, String origin) {
		for (String s : origins) {
			if (origin.contains(s)) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		/*
		 * System.out.println(getScheme("https://10.10.100.125/api/order"));
		 * 
		 * String str = "oVSwi8";
		 * 
		 * for (int i = 0; i < 10; i++) { System.out.println(spread(str.hashCode())); }
		 * 
		 * ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
		 * 
		 * map.put(str, "test!!!!!!!!!");
		 * 
		 * System.out.println(map.remove(str));
		 */

		/*
		 * InMemoryAuthorizationCodeServices imacs = new
		 * InMemoryAuthorizationCodeServices();
		 * 
		 * UsernamePasswordAuthenticationToken upat = new
		 * UsernamePasswordAuthenticationToken("test", "test");
		 * 
		 * OAuth2Request req = new OAuth2Request(null, "", null, true, null, null, "",
		 * null, null);
		 * 
		 * OAuth2Authentication oauth2 = new OAuth2Authentication(req, upat);
		 * oauth2.setAuthenticated(true);
		 * 
		 * System.out.println("oauth2:" + oauth2.hashCode());
		 * 
		 * String code = imacs.createAuthorizationCode(oauth2);
		 * 
		 * System.out.println("code is:" + code);
		 * 
		 * System.out.println("after:" +
		 * imacs.consumeAuthorizationCode(code).hashCode());
		 */
	}

	static final int spread(int arg) {
		return (arg ^ arg >>> 16) & Integer.MAX_VALUE;
	}
}
