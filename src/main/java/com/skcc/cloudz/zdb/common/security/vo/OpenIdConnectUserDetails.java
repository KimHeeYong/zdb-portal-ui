package com.skcc.cloudz.zdb.common.security.vo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class OpenIdConnectUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String email;
	private String group;
	private OAuth2AccessToken token;

	public OpenIdConnectUserDetails(Map<String, String> userInfo, OAuth2AccessToken token) {
		this.userId = userInfo.get("sub");
		this.email = userInfo.get("email");
//		this.group = userInfo.get("group");
		this.token = token;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public OAuth2AccessToken getToken() {
		return token;
	}

	public void setToken(OAuth2AccessToken token) {
		this.token = token;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OpenIdConnectUserDetails [").append("</br>");
		builder.append("<b>userId</b>=");
		builder.append(userId).append("</br>");
		builder.append("<b>email</b>=");
		builder.append(email).append("</br>");
		builder.append("<b>group</b>=");
		builder.append(group).append("</br>");
		builder.append("<b>access_token</b>=" + token.getValue()).append("</br>");
		builder.append("<b>id_token</b>=" + token.getAdditionalInformation().get("id_token")).append("</br>");
		builder.append("<b>refresh_token</b>=" + token.getRefreshToken()).append("</br>");
		builder.append("]");
		return builder.toString();
	}

	
}