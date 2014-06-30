package com.zxw.system.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zxw.organization.model.User;
import com.zxw.organization.service.UserService;
import com.zxw.system.constant.ConstantValue;


@Controller
public class BaseController {
	
	@Autowired
	private UserService userService = null;
	
	/**
	 * 登录请求
	 * @param username
	 * @param userpass
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, @RequestParam(required=true) String username, 
			@RequestParam(required=true) String userpass) {
		request.getSession().setAttribute(ConstantValue.CURRENT_USER_KEY, userService.getUserByNameAndPass(username, userpass));
		return "redirect:isLogin.html";		
	}
	
	/**
	 * 是否登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("isLogin.html")
	public String isLong(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if(this.getCurrentUser(request) != null) {
			return "main";
		}
		return "login";
	}
	
	/**
	 * 页面找不到
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("page_not_find.html")
	public ModelAndView pageNotFind(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		
		view.setViewName("404");
		
		return view;	
	}
	
	/**
	 * 异常处理页面
	 * @param request
	 * @param response
	 * @param exception
	 * @return
	 */
	@RequestMapping("service_exception.html")
	@ResponseBody
	public JsonResult exception(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		try {
			exception.printStackTrace(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return new JsonResult(false, "服务器内部错误....."+exception.getMessage(), null);
	}
	
	/**
	 * 从session中获得当前登录的用户
	 * @param request
	 * @return
	 */
	protected User getCurrentUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(ConstantValue.CURRENT_USER_KEY);		
	}

}
