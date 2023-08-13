package com.global.shop.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.global.shop.service.CustomerService;



public class AuthFilter extends OncePerRequestFilter {

	private String TOKEN_HEADER = "Authorization";
	@Autowired
	private CustomerService userService;
	@Autowired
	private TokenUtiles tokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String header = request.getHeader(TOKEN_HEADER);
		final SecurityContext securityContext = SecurityContextHolder.getContext();

		if (header != null && securityContext.getAuthentication() == null) {
			String jwtToken = header.substring("Bearer ".length());
			String username = tokenUtil.getUserNameFromToken(jwtToken);
			if (username != null) {
				AppUserDetail appUserDetail =  (AppUserDetail) userService.loadUserByUsername(username);
				if (tokenUtil.isTokenValid(jwtToken, appUserDetail)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							appUserDetail, null, appUserDetail.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		filterChain.doFilter(request, response);
	}
}
