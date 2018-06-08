package org.springframework.security.oauth2.provider.code;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.alibaba.fastjson.JSON;

/**
 * Implementation of authorization code services that stores the codes and
 * authentication in memory.
 * 
 * @author Ryan Heaton
 * @author Dave Syer
 */
public class InMemoryAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
	protected final Log logger = LogFactory.getLog(getClass());

	protected final ConcurrentHashMap<String, OAuth2Authentication> authorizationCodeStore = new ConcurrentHashMap<String, OAuth2Authentication>();

	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		logger.debug("put token code----------------:" + code + ", auth------------:" + JSON.toJSONString(authentication));
		System.out.println("put token code----------------:" + code + ", auth------------:" + JSON.toJSONString(authentication));
		this.authorizationCodeStore.put(code, authentication);
	}

	@Override
	public OAuth2Authentication remove(String code) {
		OAuth2Authentication auth = this.authorizationCodeStore.remove(code);
		logger.debug("remove token code----------------:" + code + ", auth------------:" + JSON.toJSONString(auth));
		System.out.println("remove token code----------------:" + code + ", auth------------:" + JSON.toJSONString(auth));
		return auth;
	}

}
