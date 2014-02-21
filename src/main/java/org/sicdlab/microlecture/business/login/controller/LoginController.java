package org.sicdlab.microlecture.business.login.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sicdlab.microlecture.authority.annotation.CheckAuthority;
import org.sicdlab.microlecture.business.login.service.LoginService;
import org.sicdlab.microlecture.business.privateMail.service.PrivateMailService;
import org.sicdlab.microlecture.business.user.service.UserControllerService;
import org.sicdlab.microlecture.common.bean.Level;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

	@Autowired
	private PrivateMailService pmservice;
	@Autowired
	private UserControllerService userService;
	@Autowired
	private LoginService service;
	
	//没有Cookie 的登陆
	
	@RequestMapping("login.htm")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession hs=request.getSession();
		hs.invalidate();
		String userEmail=ServletRequestUtils.getStringParameter(request, "userEmail");
		String userPswd=ServletRequestUtils.getStringParameter(request, "userPassword");
		//自动登录标记
		String autoLogin=ServletRequestUtils.getStringParameter(request, "autoLogin");
		
		
		System.out.println(autoLogin);
		
		String userid=service.checkByEmailAndPswd(userEmail,userPswd);
		int sumMail=pmservice.sumMail(userid);
		if(!userid.equals("no-such-person")){
			User userinfo=service.findById(User.class, userid);
			
			String image=userinfo.getHeadImage().getImageMid();
			int credit=userinfo.getCredit();
			System.out.println("credit----"+credit);
			Level level=userService.getUserLevel(credit);
			
			
			hs=request.getSession();
			
			hs.setAttribute("userName", userinfo.getNickname());
			
			hs.setAttribute("userId", userinfo.getUserId());
			
			hs.setAttribute("email", userinfo.getEmail());
			hs.setAttribute("user", userinfo);
			hs.setAttribute("startTime", new Date());
			hs.setAttribute("sumMail", sumMail);
			if(autoLogin!=null){
				Cookie cookie=new Cookie("cookieEmail",userinfo.getEmail()+"&&"+userinfo.getPassword());
				
				cookie.setMaxAge(60*60*24*14);
				
				response.addCookie(cookie);
			
			}
			request.setAttribute("level", level);
			Calendar cal = Calendar.getInstance();
			request.getSession().setAttribute("xingqiji", cal.get(Calendar.DAY_OF_WEEK)-1);
			request.getSession().setAttribute("ri", cal.get(Calendar.DAY_OF_MONTH));
			request.getSession().setAttribute("yue", cal.get(Calendar.MONTH)+1);
			return new ModelAndView("redirect:turnToHomePage.htm");
		}else{
			request.setAttribute("note", "邮箱或是密码不正确，请重新输入");
			return new ModelAndView("/login/login");
		}
	
	}
	//从cookie直接登陆
	@RequestMapping("checkLogin.htm")
	public ModelAndView checkLogin(HttpServletRequest request) throws Exception{
		Enumeration<?> e=request.getSession().getAttributeNames();
		
		while(e.hasMoreElements()){
			String attributeName=(String) e.nextElement();
			request.getSession().removeAttribute(attributeName);
		}
		Cookie[] cookies=request.getCookies();
		
		String[] cooks=null;
		
		String cookieEmail=null;
		String cookiePswd=null;
		
		User userinfo=new User();
		if(cookies!=null){
			boolean exist=false;
			for(Cookie coo:cookies){
				String value=coo.getValue();
				
				cooks=value.split("&&");
				if(cooks.length==2){
					cookieEmail=cooks[0];	
					cookiePswd=cooks[1];
					exist=true;
					break;
				}
			
			}
			if(exist){
				
				String userid=service.checkByEmailAndCookie(cookieEmail, cookiePswd);
				if(!userid.equals("no-this-person")){
					int sumMail=pmservice.sumMail(userid);
					userinfo=service.findById(User.class, userid);
					
					
					int credit=userinfo.getCredit();
					System.out.println("credit----"+credit);
					Level level=userService.getUserLevel(credit);
					HttpSession hs=request.getSession();
					hs=request.getSession();
					
					hs.setAttribute("userName", userinfo.getNickname());
					
					hs.setAttribute("userId", userinfo.getUserId());
					
					hs.setAttribute("userEmail", userinfo.getEmail());
					hs.setAttribute("user", userinfo);
					hs.setAttribute("startTime", new Date());	
					hs.setAttribute("sumMail", sumMail);
					request.setAttribute("level", level);
					
					Calendar cal = Calendar.getInstance();
					request.getSession().setAttribute("xingqiji", cal.get(Calendar.DAY_OF_WEEK)-1);
					request.getSession().setAttribute("ri", cal.get(Calendar.DAY_OF_MONTH));
					request.getSession().setAttribute("yue", cal.get(Calendar.MONTH)+1);
				
					return new ModelAndView("redirect:turnToHomePage.htm");
			
				}
			
			}
		
		}
		
		return new ModelAndView("/login/login");
	}
	
	@RequestMapping("goLoginPage.htm")
	public ModelAndView goLoginPage(){
		return new ModelAndView("redirect:checkLogin.htm");
	}
	@RequestMapping("logout.htm")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().invalidate();
		Cookie cookie = new Cookie("cookieEmail", null); 
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return new ModelAndView("redirect:goLoginPage.htm");
	}
	
}
