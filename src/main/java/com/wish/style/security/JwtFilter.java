package com.wish.style.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter{
	@Autowired 
	JwtService jwtService;
	private HandlerExceptionResolver exceptionResolver;
	@Autowired
	ApplicationContext context;
	@Autowired
	public JwtFilter(HandlerExceptionResolver exceptionResolver) {
		this.exceptionResolver=exceptionResolver;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		String token=null;
		String username=null;
		try
		{
			if (authHeader != null && authHeader.contains("Bearer ") && !authHeader.contains("Bearer null")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UserDetails userDetails=context.getBean(AuthUserDetailsService.class).loadUserByUsername(username);
				if(jwtService.validateToken(token,userDetails))
				{
					UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
			filterChain.doFilter(request, response);
		}
		catch(Exception e)
		{
			exceptionResolver.resolveException(request, response, null, e);
		}
	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String requestURI = request.getRequestURI();
		if (requestURI.contains("/api/auth"))
		{
			return true;
		}
		return false;
	}
}
