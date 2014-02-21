package org.sicdlab.microlecture.business.microRank.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.microRank.service.MicroRankService;
import org.sicdlab.microlecture.business.user.service.UserControllerService;
import org.sicdlab.microlecture.common.bean.Challenge;
import org.sicdlab.microlecture.common.bean.DataDic;
import org.sicdlab.microlecture.common.bean.Level;
import org.sicdlab.microlecture.common.bean.Team;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MicroRankController {
	@Autowired
	private MicroRankService  mkservice;
	private UserControllerService  uservice;
	
	@RequestMapping("goRank.htm")
	public ModelAndView goRank(){
		
		return new ModelAndView("/rankingList/rankingList");
		
	}
	@RequestMapping("goRankDetailClassic.htm")
	public ModelAndView goRankDetailClassic(){
		
		return new ModelAndView("/rankingList/rankDetailClassic");
		
	}
	@RequestMapping("goRankTeam.htm")
	public ModelAndView goRankTeam(){
		
		return new ModelAndView("/rankingList/rankTeam");
		
	}
	@RequestMapping("goRankDetailTeam.htm")
	public ModelAndView goRankDetailTeam(){
		
		return new ModelAndView("/rankingList/rankDetailTeam");
		
	}
	@RequestMapping("gomyrankdetail.htm")
	public ModelAndView gomyrankdetail(){
		
		return new ModelAndView("/rankingList/MyRankDetail");
		
	}
	@RequestMapping("gomyrank.htm")
	public ModelAndView gomyrank(){
		
		return new ModelAndView("/rankingList/MyRankList");
		
	}
	
	
	
	
	@RequestMapping("rank.htm")
	public  ModelAndView rank(HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession hs=request.getSession();
		ArrayList<ArrayList<Challenge>> biglist=new ArrayList<>();
		ArrayList<DataDic> typeList=(ArrayList<DataDic>) mkservice.getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicKey", "专业分类")).list();
		biglist=mkservice.getbiglist(typeList);
		request.setAttribute("typeList", typeList);
		request.setAttribute("biglist", biglist);
		
		return new ModelAndView("forward:goRank.htm");
		
	}
	@RequestMapping("rankDetail.htm")
	public  ModelAndView rankDetail(HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession hs=request.getSession();
		ArrayList<ArrayList<Challenge>> biglist=new ArrayList<>();
		ArrayList<DataDic> typeList=(ArrayList<DataDic>) mkservice.getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicKey", "专业分类")).list();
		biglist=mkservice.getbiglist(typeList);
		hs.setAttribute("biglist", biglist);
		
		return new ModelAndView("redirect:goRank.htm");
		
	}
	//小组排行，还有上榜小组组员排行
	@SuppressWarnings("unchecked")
	@RequestMapping("rankTeam.htm")
	public  ModelAndView rankTeam(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//总排行
		ArrayList<Team> teamList=new ArrayList<>();
		teamList=(ArrayList<Team>)mkservice.getCurrentSession().createCriteria(Team.class).addOrder(Order.desc("construction")).add(Restrictions.eq("teamState", "批准")).setMaxResults(10).list();
		request.setAttribute("teamList", teamList);
		//分类排行
		ArrayList<DataDic> typeList=(ArrayList<DataDic>) mkservice.getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicKey", "专业分类")).list();
		System.out.println("typeList.size()typeList.size()typeList.size()typeList.size()==="+typeList.size());
		ArrayList<ArrayList<Team>> teambiglist=new ArrayList<>();
		teambiglist=mkservice.getteambiglist(typeList);
		request.removeAttribute("typeList");
		request.setAttribute("typeList", typeList);
		request.setAttribute("teambiglist", teambiglist);
		
		return new ModelAndView("forward:goRankTeam.htm");
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("myrankdetail.htm")
	public ModelAndView myrankdetail(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String type=request.getParameter("type");
		DataDic dd=(DataDic) mkservice.getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicId", type)).uniqueResult();
		List<Challenge> myranklist;
		User user=(User) request.getSession(false).getAttribute("user");
		myranklist=mkservice.getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("major", dd.getDicValue())).add(Restrictions.eq("user", user)).addOrder(Order.desc("challengeDate")).list();
		request.setAttribute("myranklist", myranklist);
		
		return new ModelAndView("forward:gomyrankdetail.htm");
	}
	
	@RequestMapping("myrank.htm")
	public ModelAndView myrank(HttpServletRequest request,HttpServletResponse response)throws Exception {
		
		List<Challenge> myranklist;
		User user=(User) request.getSession(false).getAttribute("user");
		List<DataDic> dd=mkservice.getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicKey", "专业分类")).list();
		//myranklist=mkservice.getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("major", type)).add(Restrictions.eq("user", user)).addOrder(Order.desc("challengeDate")).list();
		request.setAttribute("ddlist", dd);
		
		return new ModelAndView("forward:gomyrank.htm");
	}

}
