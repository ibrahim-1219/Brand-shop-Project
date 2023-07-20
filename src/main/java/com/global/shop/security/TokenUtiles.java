package com.global.shop.security;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtiles {

	private long TOKEN_VALIDITY = 604800L; // 7 day
	private static String TOKEN_SECRET = "notification-api";

	public static String generateToken(final String username , boolean isRefresh) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setIssuer("app-Service")
				.claim("created", Calendar.getInstance().getTime())
				.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET).compact();
	}

	public String getUserNameFromToken(String token) {
		Claims claims = getClaims(token);
		return claims.getSubject();
	}

	public boolean isTokenValid(String token, AppUserDetail userDetails) {
		
		String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getEmail()) );
	}


	private Claims getClaims(String token) {
		Claims claims;

		try {

			claims = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
		} catch (Exception ex) {
			claims = null;
		}

		return claims;
	}
	
}
