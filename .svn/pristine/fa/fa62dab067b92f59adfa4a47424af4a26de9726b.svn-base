package org.sicdlab.microlecture.business.myprop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.microRank.service.MicroRankService;
import org.sicdlab.microlecture.business.myprop.service.mypropService;
import org.sicdlab.microlecture.business.user.service.UserControllerService;
import org.sicdlab.microlecture.common.bean.Challenge;
import org.sicdlab.microlecture.common.bean.Level;
import org.sicdlab.microlecture.common.bean.Prop;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.bean.UserProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class mypropController {
	@Autowired
	private mypropService  mpservice;
	private UserControllerService  uservice;
	
	@RequestMapping("gomyprop.htm")
	public ModelAndView gomyprop(){
		return new ModelAndView("/myprop/myprop");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("myprop.htm")
	public ModelAndView myprop(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//道具列表
		HttpSession hs=request.getSession(false);
		User user=(User) hs.getAttribute("user");
		ArrayList<Prop> proplist=(ArrayList<Prop>) mpservice.getCurrentSession().createCriteria(Prop.class).list();
		ArrayList myprop=new ArrayList<>();
		for(int i=0;i<proplist.size();i++){
			UserProp up=(UserProp) mpservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("user", user)).add(Restrictions.eq("prop", proplist.get(i))).uniqueResult(); 
            myprop.add(up);
            up=null;
		}
		List<UserProp> userprophehe=mpservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("user", user)).list();
		request.setAttribute("myprop", userprophehe);
		
		return new ModelAndView("/myprop/myprop");
	}
	
	///用户使用道具，不能使用则提醒场合不对
	@RequestMapping("mypropuse.htm")
	public ModelAndView mypropuse(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//道具列表
		HttpSession hs=request.getSession(false);
		User user=(User) hs.getAttribute("user");
		List<UserProp> userprophehe=mpservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("user", user)).list();
		request.setAttribute("myprop", userprophehe);
		request.setAttribute("chart", 67);
		
		return new ModelAndView("/myprop/myprop");
	}


}
