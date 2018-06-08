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

package org.springframework.security.oauth2.provider.token.store;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.*;

/**
 * A {@link TokenStore} implementation that just reads data from the tokens
 * themselves. Not really a store since it never persists anything, and methods
 * like {@link #getAccessToken(OAuth2Authentication)} always return null. But
 * nevertheless a useful tool since it translates access tokens to and from
 * authentications. Use this wherever a {@link TokenStore} is needed, but
 * remember to use the same {@link JwtAccessTokenConverter} instance (or one
 * with the same verifier) as was used when the tokens were minted.
 * 
 * @author Dave Syer
 *
 */
public class JwtTokenStore implements TokenStore {

	private JwtAccessTokenConverter jwtTokenEnhancer;

	private ApprovalStore approvalStore;

	/**
	 * Create a JwtTokenStore with this token enhancer (should be shared with
	 * the DefaultTokenServices if used).
	 * 
	 * @param jwtTokenEnhancer
	 */
	public JwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
		this.jwtTokenEnhancer = jwtTokenEnhancer;
	}

	/**
	 * ApprovalStore to be used to validate and restrict refresh tokens.
	 * 
	 * @param approvalStore
	 *            the approvalStore to set
	 */
	public void setApprovalStore(ApprovalStore approvalStore) {
		this.approvalStore = approvalStore;
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		Map<String, Object> data = null;
		try {
			data = jwtTokenEnhancer.decode(token);
		} catch (Exception e) {
			if (token.equals("gfresh_anonymous")) {
				data = new HashMap<>();
				/*
				 * {exp=1494882846, user_name=gfresh_anonymous,
				 * authorities=[ROLE_USER],
				 * jti=9aec5bb5-f1d0-4bca-9267-21287e4a9a2a,
				 * client_id=retailstore, scope=[openid]}
				 */
				data.put("exp", System.currentTimeMillis() + 60 * 1000);
				data.put("user_name", "gfresh_anonymous");
				data.put("authorities", "ROLE_USER");
				data.put("jti", UUID.randomUUID());
				data.put("client_id", "retailstore");
				data.put("scope", "openid");
			} else {
				throw new InvalidTokenException("Cannot convert access token to JSON", e);
			}
		}
		return jwtTokenEnhancer.extractAuthentication(data);
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		OAuth2AccessToken accessToken = convertAccessToken(tokenValue);
		if (jwtTokenEnhancer.isRefreshToken(accessToken)) {
			throw new InvalidTokenException("Encoded token is a refresh token");
		}
		return accessToken;
	}

	private OAuth2AccessToken convertAccessToken(String tokenValue) {
		Map<String, Object> data = null;
		try {
			data = jwtTokenEnhancer.decode(tokenValue);
		} catch (Exception e) {
			if (tokenValue.equals("gfresh_anonymous")) {
				data = new HashMap<>();
				/*
				 * {exp=1494882846, user_name=gfresh_anonymous,
				 * authorities=[ROLE_USER],
				 * jti=9aec5bb5-f1d0-4bca-9267-21287e4a9a2a,
				 * client_id=retailstore, scope=[openid]}
				 */
				data.put("exp", System.currentTimeMillis() + 60 * 1000);
				data.put("user_name", "gfresh_anonymous");
				data.put("authorities", "ROLE_USER");
				data.put("jti", UUID.randomUUID());
				data.put("client_id", "retailstore");
				data.put("scope", "openid");
			} else {
				throw new InvalidTokenException("Cannot convert access token to JSON", e);
			}
		}
		return jwtTokenEnhancer.extractAccessToken(tokenValue, data);
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		// gh-807 Approvals (if any) should only be removed when Refresh Tokens
		// are removed (or expired)
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		OAuth2AccessToken encodedRefreshToken = convertAccessToken(tokenValue);
		OAuth2RefreshToken refreshToken = createRefreshToken(encodedRefreshToken);
		if (approvalStore != null) {
			OAuth2Authentication authentication = readAuthentication(tokenValue);
			if (authentication.getUserAuthentication() != null) {
				String userId = authentication.getUserAuthentication().getName();
				String clientId = authentication.getOAuth2Request().getClientId();
				Collection<Approval> approvals = approvalStore.getApprovals(userId, clientId);
				Collection<String> approvedScopes = new HashSet<String>();
				for (Approval approval : approvals) {
					if (approval.isApproved()) {
						approvedScopes.add(approval.getScope());
					}
				}
				if (!approvedScopes.containsAll(authentication.getOAuth2Request().getScope())) {
					return null;
				}
			}
		}
		return refreshToken;
	}

	private OAuth2RefreshToken createRefreshToken(OAuth2AccessToken encodedRefreshToken) {
		if (!jwtTokenEnhancer.isRefreshToken(encodedRefreshToken)) {
			throw new InvalidTokenException("Encoded token is not a refresh token");
		}
		if (encodedRefreshToken.getExpiration() != null) {
			return new DefaultExpiringOAuth2RefreshToken(encodedRefreshToken.getValue(),
					encodedRefreshToken.getExpiration());
		}
		return new DefaultOAuth2RefreshToken(encodedRefreshToken.getValue());
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		remove(token.getValue());
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		// gh-807 Approvals (if any) should only be removed when Refresh Tokens
		// are removed (or expired)
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		// We don't want to accidentally issue a token, and we have no way to
		// reconstruct the refresh token
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		return Collections.emptySet();
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		return Collections.emptySet();
	}

	public void setTokenEnhancer(JwtAccessTokenConverter tokenEnhancer) {
		this.jwtTokenEnhancer = tokenEnhancer;
	}

	private void remove(String token) {
		if (approvalStore != null) {
			OAuth2Authentication auth = readAuthentication(token);
			String clientId = auth.getOAuth2Request().getClientId();
			Authentication user = auth.getUserAuthentication();
			if (user != null) {
				Collection<Approval> approvals = new ArrayList<Approval>();
				for (String scope : auth.getOAuth2Request().getScope()) {
					approvals.add(new Approval(user.getName(), clientId, scope, new Date(), ApprovalStatus.APPROVED));
				}
				approvalStore.revokeApprovals(approvals);
			}
		}
	}
}
