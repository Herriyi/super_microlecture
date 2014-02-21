package org.sicdlab.microlecture.business.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.common.baseService.BaseService;
import org.sicdlab.microlecture.common.bean.Level;
import org.sicdlab.microlecture.common.bean.Rule;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sicdlab.microlecture.business.user.service.UserControllerService;



@Controller
public class UserController {

	
	
	@Autowired
	private UserControllerService  uservice;
	

	

	@RequestMapping("goaccountRevise.htm")
	public ModelAndView goaccountRevise() {
		return new ModelAndView("/userPage/accountRevise");
	}
	
	@RequestMapping("goaccount.htm")
	public ModelAndView goaccount() {
		return new ModelAndView("/userPage/account");
	}
	
	@RequestMapping("goaccountnickname.htm")
	public ModelAndView goaccountnickname() {
		return new ModelAndView("/userPage/accountNickname");
	}
	
	
	
	@RequestMapping("goaccountavatar.htm")
	public ModelAndView goaccountavatar() {
		return new ModelAndView("/userPage/accountAvatar");
	}
	
	@RequestMapping("user.htm")
	public ModelAndView gouser() {
		return new ModelAndView("/userPage/user");
	}
	@RequestMapping("goaccountPassword.htm")
	public ModelAndView goaccountPassword() {
		return new ModelAndView("/userPage/accountPassword");
	}
	
	@RequestMapping("revisePassword.htm")
	public ModelAndView revisedPassword(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userid=request.getSession().getAttribute("userId").toString();
		User user=new User();
		user=(User) request.getSession().getAttribute("user");
		System.out.println(user.getUserId());
		String oldPassword=request.getParameter("oldPassword");
		String newPassword=request.getParameter("newPassword");
		String newPasswordConfirm=request.getParameter("newPasswordConfirm");
		System.out.println("ssss"+oldPassword+"sss"+newPassword);
		String message = "失败";
		if(oldPassword==null||oldPassword==""||newPassword==null||newPassword==""||newPasswordConfirm==null||newPasswordConfirm==""){
			message="密码不能为空";
			request.setAttribute("message", message);
			return new ModelAndView("forward:goaccountPassword.htm");
		}
		if(!newPassword.equals(newPasswordConfirm)){
			message="两次输入密码不一致";
			request.setAttribute("message", message);
			return new ModelAndView("forward:goaccountPassword.htm");
		}
		if(oldPassword.length()<6||newPassword.length()<6||newPasswordConfirm.length()<6){
			message="密码太短";
			request.setAttribute("message", message);
			return new ModelAndView("forward:goaccountPassword.htm");
		}
		if(oldPassword.length()>26||newPassword.length()>26||newPasswordConfirm.length()>26){
			message="密码太长";
			request.setAttribute("message", message);
			return new ModelAndView("forward:goaccountPassword.htm");
		}
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern pattern = Pattern.compile(regEx); 
		Matcher matcher = pattern.matcher(newPassword); 
		boolean b= matcher.matches();
		
		if(b){
			message="非法字符";
			request.setAttribute("message", message);
			return new ModelAndView("forward:goaccountPassword.htm");
		}
		
		regEx="^[A-Za-z0-9]+$";
		pattern = Pattern.compile(regEx); 
		matcher = pattern.matcher(newPassword);
		b= matcher.matches();
		if(!b){
			message="请输入由数字和26个英文字母组成的密码";
			request.setAttribute("message", message);
			return new ModelAndView("forward:goaccountPassword.htm");
		}
		if(user.getPassword().equals(oldPassword)){
			User userhehe=new User();
			userhehe=(User) uservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userid)).uniqueResult();
			userhehe.setPassword(newPassword);
			user.setPassword(newPassword);
			uservice.save(userhehe);
			message="修改成功";
			request.setAttribute("message", message);
			return new ModelAndView("forward:goaccountPassword.htm");
		}
		
		
		
		
		request.setAttribute("userId", userid);
	
		request.setAttribute("message", message);
		return new ModelAndView("forward:goaccount.htm");
	}
	
	@RequestMapping("goUserHomePage.htm")
	public ModelAndView goUserHomePage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userid=request.getSession().getAttribute("userId").toString();
		User user=new User();
		
		
		
		
		
		
		request.setAttribute("user", user);
		
		
		request.setAttribute("userId", userid);
	
		
		return new ModelAndView("userPage/user");
	}
	
	
	
	//获取用户信息和等级！！！！！！！！！！！！！！！！！！！！！！！！
	public Level getUserInfo(HttpServletRequest request,HttpServletResponse response){
		String userid=request.getSession().getAttribute("userId").toString();
		
		User user=new User();
		user=(User) uservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userid)).uniqueResult();
		int credit=user.getCredit();
		System.out.println("credit ::"+credit);
		Level level=uservice.getUserLevel(credit);
		HttpSession hs=request.getSession();
		hs.removeAttribute("user");
		hs.setAttribute("user", user);
		hs.setAttribute("level", level);
		request.removeAttribute("user");
		request.setAttribute("user", user);
		request.removeAttribute("level");
		request.setAttribute("level", level);
		return level;
	}
	
	
	
	@RequestMapping("account.htm")
	public ModelAndView goaccount(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		
		//获取用户信息和等级！！！！！！！！！！！！！！！！！！！！！！！！
		getUserInfo(request,response);
		return new ModelAndView("forward:goaccount.htm");
	}
	
	
	//用户修改个人信息
	@RequestMapping("accountupdate.htm")
      public ModelAndView account(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//用户的信息
		String userid=request.getSession().getAttribute("userId").toString();
		User user=(User) request.getSession().getAttribute("user");
		User user2=new User();
		user2=(User) uservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userid)).uniqueResult();
		//获取修改的信息
		String usersex=request.getParameter("userprofile_sex");
		String useryear=request.getParameter("year");
		String usermonth=request.getParameter("month");
		String userday=request.getParameter("day");
		String usercity=request.getParameter("city");
		String usersignture=request.getParameter("userprofile_signature");
		String userabout=request.getParameter("about");
		String userbirth=useryear+"-"+usermonth+"-"+userday;
		if(!(useryear==""||usermonth==""||userday==""||useryear==null||usermonth==null||userday==null)){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(userbirth);
		user2.setBirthday(date);
		user.setBirthday(date);
		}
		if(!(usersignture==null||usersignture=="")){
		user2.setSignature(usersignture);
		user.setSignature(usersignture);}
		
		user2.setGender(usersex);
		user.setGender(usersex);
		if(!(userbirth=="--")){
		
		}
		if(!(userabout==null||userabout=="")){
		user2.setIntro(userabout);
		user.setIntro(userabout);
		}
		if(!(usercity==null||usercity=="")){
		user2.setCity(usercity);
		user.setCity(usercity);
		}
		
		//保存信息
		uservice.update(user2);
		
		
	
		
		return new ModelAndView("redirect:goaccount.htm");
	}
	
	
	
	@RequestMapping("accountnicknamejudge.htm")
	public ModelAndView accountnicknamejudge(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//用户的信息
				String userid=request.getSession().getAttribute("userId").toString();
				User user=new User();
				user=(User) uservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userid)).uniqueResult();
		//读取金币规则之修改昵称
	        int gold= user.getGold();
	        if(gold>=5){
	        	return new ModelAndView("forward:goaccountnickname.htm");
	        }else{
	        	return new ModelAndView("redirect:goaccount.htm");
	        }
	}
	@RequestMapping("accountnickname.htm")
	public ModelAndView accountnickname(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//用户的信息
				String userid=request.getSession().getAttribute("userId").toString();
				User user=new User();
				user=(User) uservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userid)).uniqueResult();
		        
			//声明消息
				String message;
			//读取金币规则---修改昵称
	        int gold= user.getGold();
	        if(gold>=5){
	        	String nickname=request.getParameter("nickname");
	        	if(user.getNickname()==nickname){
	        		message="已经修改";
	        		getUserInfo(request,response);
	        		return new ModelAndView("forward:goaccountnickname.htm","message",message);
	        	}
	        	Rule rule=(Rule) uservice.getCurrentSession().createCriteria(Rule.class).add(Restrictions.eq("action","修改昵称")).uniqueResult(); 
	        	gold=gold+rule.getGold();
	        	//保存信息
	        	user.setGold(gold);
	        	user.setNickname(nickname);
	        	uservice.save(user);
	        	message="修改成功";
	        	getUserInfo(request,response);
	        	return new ModelAndView("forward:goaccountnickname.htm","message",message);
	        }else{
	        	message="金币不足";
	        	getUserInfo(request,response);
	        	return new ModelAndView("forward:goaccount.htm","message",message);
	        }
	}
	
	//处理头像函数
	@RequestMapping("accountavatar.htm")
	public ModelAndView accountavatar(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userid=request.getSession().getAttribute("userId").toString();
		if(userid==null){
			userid="ololololololololololololol";
		}
		User user=new User();
		//user=(User) service.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userid)).uniqueResult();
		int credit=user.getCredit();
		Level level=uservice.getUserLevel(credit);
		
		request.setAttribute("user", user);
		request.setAttribute("level", level);
	
		
		return new ModelAndView("goaccountavatar.htm");
	}
}
