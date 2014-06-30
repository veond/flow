package com.zxw.system.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * spring  的 拦截器  （动态代理）
 * @author 19lou-zxw
 * 
 */
public class SetBaseUrlInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 之前
	 */
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//	}

	/**
	 *  之后： 拦截器(动态代理) 将 baseUrl 值设置进 request
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//设置base url
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		request.setAttribute("baseUrl", basePath);
		
	}
	
	

}
