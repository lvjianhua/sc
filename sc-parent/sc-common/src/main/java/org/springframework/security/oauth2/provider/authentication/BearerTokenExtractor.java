/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.springframework.security.oauth2.provider.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * {@link TokenExtractor} that strips the authenticator from a bearer token
 * request (with an Authorization header in the form "Bearer
 * <code>&lt;TOKEN&gt;</code>", or as a request parameter if that fails). The
 * access token is the principal in the authentication token that is extracted.
 *
 * 更改解析类 如果是从zuul服务器过来但是authentication为空 则使用默认的构造oauth类
 * 
 * @author Dave Syer edit by shawn
 * 
 */
public class BearerTokenExtractor implements TokenExtractor {

	private final static Log logger = LogFactory.getLog(BearerTokenExtractor.class);

	@Override
	public Authentication extract(HttpServletRequest request) {
		String tokenValue = extractToken(request);
		if (tokenValue != null) {
			PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(tokenValue,
					"");
			return authentication;
		} else {
			if (request.getHeader("x-forwarded-host") != null) {
				Collection<GrantedAuthority> configAttributes = new ArrayList<>();
				configAttributes.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
				
				AnonymousAuthenticationToken authentication = new AnonymousAuthenticationToken("gfresh_anonymous",
						// "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0OTQ4ODI4NDYsInVzZXJfbmFtZSI6ImdmcmVzaF9hbm9ueW1vdXMiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiOWFlYzViYjUtZjFkMC00YmNhLTkyNjctMjEyODdlNGE5YTJhIiwiY2xpZW50X2lkIjoicmV0YWlsc3RvcmUiLCJzY29wZSI6WyJvcGVuaWQiXX0.i5OWdLTL0mRbUTwPkug5k00HgOX89HazX_4V5b2_4_fQfl80MUxoZwdpIhDWBLJEKlGOUMn-r57pF5zLjBhaRS8ANpfdGlUErPdsLFeY9T3VHL-CIXFiiVgMW6y8ZJvoBdC4YtlRnmsyxPQFczlfcdtwOyQQ1fWiT6HDiWbmQLN0z_H3_MmqeIzgM4FzoB4SqP8f3IPF6pSvJIIeLPBT4-DaQlFpsDeLjT6KQBsplMTWh5xnlTBmiBo2DLZcRe7EV3VfyN7ab_gBfWqvMufyvvlYo9mCSUN0pj_a_MH6KqhYSbRga3hOXiYa5rlMttJRy7-AuhW-qIl5IB7J9f6atA",
						"gfresh_anonymous", configAttributes);
				return authentication;
			}
		}
		return null;
	}

	protected String extractToken(HttpServletRequest request) {
		// first check the header...
		String token = extractHeaderToken(request);

		// bearer type allows a request parameter as well
		if (token == null) {
			logger.debug("Token not found in headers. Trying request parameters.");
			token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
			if (token == null) {
				logger.debug("Token not found in request parameters.  Not an OAuth2 request.");
			} else {
				request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, OAuth2AccessToken.BEARER_TYPE);
			}
		}

		return token;
	}

	/**
	 * Extract the OAuth bearer token from a header.
	 * 
	 * @param request
	 *            The request.
	 * @return The token, or null if no OAuth authorization header was supplied.
	 */
	protected String extractHeaderToken(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaders("Authorization");
		while (headers.hasMoreElements()) { // typically there is only one (most
											// servers enforce that)
			String value = headers.nextElement();
			if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
				String authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
				// Add this here for the auth details later. Would be better to
				// change the signature of this method.
				request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
						value.substring(0, OAuth2AccessToken.BEARER_TYPE.length()).trim());
				int commaIndex = authHeaderValue.indexOf(',');
				if (commaIndex > 0) {
					authHeaderValue = authHeaderValue.substring(0, commaIndex);
				}
				return authHeaderValue;
			}
		}

		return null;
	}

}
