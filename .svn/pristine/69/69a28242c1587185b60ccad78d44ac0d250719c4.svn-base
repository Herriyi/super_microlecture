package org.sicdlab.microlecture.business.attention.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sicdlab.microlecture.authority.annotation.CheckAuthority;
import org.sicdlab.microlecture.business.attention.service.AttentionService;
import org.sicdlab.microlecture.business.user.service.UserControllerService;
import org.sicdlab.microlecture.common.bean.Attention;
import org.sicdlab.microlecture.common.bean.Level;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class AttentionController {
	@Autowired
	private UserControllerService userService;
	@Autowired
	private AttentionService attention;
	//添加关注
	
	@RequestMapping("addAttention.htm")
	public void addAttention(HttpServletRequest request,HttpServletResponse response)throws Exception{
		PrintWriter pw=response.getWriter();
		String userBid=request.getParameter("userBid");
		String userAid=request.getSession().getAttribute("userId").toString();
		
		if(attention.isAlreadyAttention(userAid, userBid)){
			
			attention.addAttention(userAid, userBid);
			
			pw.print("ok");//以什么方式显示关注成功
		}else{
			
			pw.print("no");
			
		}
		pw.close();
	
	}
	@RequestMapping("addAttention1.htm")
	public ModelAndView addAttention1(HttpServletRequest request)throws Exception{
		
		String userBid=request.getParameter("userBid");
		String userAid=request.getSession().getAttribute("userId").toString();
		if(userAid==null){
			
			attention.addAttention(userAid, userBid);
			
			
			return new ModelAndView("redirect:myFans.htm");
		}
		
		return new ModelAndView("/login/login");
		
	
	}
	
	@RequestMapping("delAttention.htm")
	public void delAttention(HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter pw=response.getWriter();
		String userBid=request.getParameter("userBid");
		
		String userAid=request.getSession().getAttribute("userId").toString();
		
		attention.delAttention(userAid, userBid);
		
		pw.write("ok");
		
		pw.close();
		
		
	}
	@RequestMapping("delAttention1.htm")
	public ModelAndView delAttention1(HttpServletRequest request){
		
		String type=request.getParameter("type");
		
		String userBid=request.getParameter("userBid");
		
		String userAid=request.getSession().getAttribute("userId").toString();
		attention.delAttention(userAid, userBid);
		if(type.equals("1")){
			return new ModelAndView("redirect:myFans.htm");
		}else if(type.equals("0")){
			return new ModelAndView("redirect:myAttention.htm");
		}
		
		return new ModelAndView("redirect:myAttention.htm");
		
		
	}
	@RequestMapping("myAttention.htm")
	public ModelAndView myAttention(HttpServletRequest request) throws Exception{
		
		
		User user=(User) request.getSession().getAttribute("user");
		
		if(user==null){
			
			return new ModelAndView("/login/login");
		}else{
			
			int credit=user.getCredit();
			System.out.println("credit----"+credit);
			Level level=userService.getUserLevel(credit);
			request.setAttribute("level", level);
			List<Attention> list=attention.queryAttention(user.getUserId());
			
			request.setAttribute("sum", list.size());
			
			return new ModelAndView("/friend/myAttention","list",list);
		}
	}
	@RequestMapping("myFans.htm")
	public ModelAndView myFans(HttpServletRequest request) throws Exception{
		User user=(User) request.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("/login/login");
		}else{
			
			int credit=user.getCredit();
			System.out.println("credit----"+credit);
			Level level=userService.getUserLevel(credit);
			request.setAttribute("level", level);
			List<Attention> list=attention.queryFans(user.getUserId());
			
			request.setAttribute("sum", list.size());
			
			return new ModelAndView("/friend/myFans","list",list);
		}
	}
	
}
