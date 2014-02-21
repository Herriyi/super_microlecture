package org.sicdlab.microlecture.business.challenge.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.challenge.service.ChallengeService;
import org.sicdlab.microlecture.common.bean.Answer;
import org.sicdlab.microlecture.common.bean.Challenge;
import org.sicdlab.microlecture.common.bean.DataDic;
import org.sicdlab.microlecture.common.bean.Prop;
import org.sicdlab.microlecture.common.bean.Question;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.bean.UserProp;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ChallengeController {

	@Autowired
	private ChallengeService  chservice;
	
	@RequestMapping("goFight.htm")
    public ModelAndView goFight(){
		
		return new ModelAndView("/challege/dailyFight");
		
	}
	
	@RequestMapping("gochallenge.htm")
    public ModelAndView gochallenge(){
		
		return new ModelAndView("/challege/challengeHome");
		
	}
	
	@RequestMapping("goresult.htm")
    public ModelAndView goresult(){
		
		return new ModelAndView("/challege/resultTest");
		
	}
	
	//比较两个时间是不是同一天
	public boolean datecompare(Date one,Date two){
		SimpleDateFormat sdf=new SimpleDateFormat("YY-MM-dd");
		String temp1=sdf.format(one);
		String temp2=sdf.format(two);
		boolean TorF=temp1.equals(temp2);
		System.out.println("11111111111111111--"+temp1+"222222222222222--"+temp2);
		return TorF;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("challenge.htm")
    public ModelAndView challenge(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		List<DataDic> challengeclassic;
		challengeclassic=chservice.getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicKey", "专业分类")).list();
		HttpSession hs=request.getSession(false);
		hs.setAttribute("challengeclassic", challengeclassic);
		hs.setMaxInactiveInterval(200);
		//可用道具
		Prop prop=(Prop) chservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propName", "原地复活")).uniqueResult();
		
		//用户道具
		UserProp up=(UserProp) chservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("prop", prop)).add(Restrictions.eq("user", hs.getAttribute("user"))).add(Restrictions.gt("number", 0)).uniqueResult();
		
		request.setAttribute("userProp", up);
		
		
		return new ModelAndView("forward:gochallenge.htm");
		
	}

	@RequestMapping("fight.htm")
    public ModelAndView fight(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String message=null;
		User user=new User();
		//String userid=request.getSession().getAttribute("userId").toString();
		user=(User) request.getSession().getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:checkLogin.htm");
		}
		//检查用户是否有权限挑战，无权限，则跳转
				
				Challenge challenge=(Challenge) chservice.getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eqOrIsNull("user", user)).addOrder(Order.desc("challengeDate")).setMaxResults(1).uniqueResult();
				if(challenge!=null){
					boolean isornot=datecompare(new Date(),challenge.getChallengeDate());
					System.out.println("转换时间转换时间转换时间"+isornot);
					
					if(isornot){
						message="今日已经挑战过了";
						//可用道具
						Prop prop=(Prop) chservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propName", "原地复活")).uniqueResult();
						UserProp up=(UserProp) chservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("prop", prop)).add(Restrictions.eq("user", user)).add(Restrictions.gt("number", 0)).uniqueResult();
						
						request.setAttribute("userProp", up);
						request.setAttribute("message", message);
						return new ModelAndView("forward:gochallenge.htm");
					}
				}
				
				
	     HttpSession hs=request.getSession();
	   //查询可使用道具
			Prop prop=(Prop) chservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propName", "免答")).uniqueResult();
			
			//用户道具
			UserProp up=(UserProp) chservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("prop", prop)).add(Restrictions.eq("user", hs.getAttribute("user"))).add(Restrictions.gt("number", 0)).uniqueResult();
			request.setAttribute("userProp", up);
		//生成题目
			Question question;
			String type=request.getParameter("type");
			System.out.println("++++++++++"+type);
			question=chservice.getquestion(type);
			hs.setAttribute("question", question);
		//生成答案
			List<Answer> options;
			System.out.println("xxxxxxxxxxxxxxxxx"+question.getQuestionId());
			options=chservice.getanswer(question.getQuestionId());
			hs.setAttribute("options",options);
		//生成挑战记录
		Challenge challengeuser1 = new Challenge();
		challengeuser1.setChallengeId(UUIDGenerator.randomUUID());
		challengeuser1.setPassNum(0);
		challengeuser1.setTotalScore(0);
		challengeuser1.setMajorRank(0);
		challengeuser1.setTeamRank(0);
		challengeuser1.setUser(user);
		challengeuser1.setMajor(type);
		challengeuser1.setChallengeDate(new Date());
		chservice.save(challengeuser1);
		hs.setAttribute("challengeuser", challengeuser1);
		//开始倒计时
		/*int time=10;
		hs.setAttribute("time", time);
		final HttpSession hs2=hs;
		TimerTask timertask=new TimerTask() {
			
			@Override
			public void run() {
				int time=10;
				for(int i=0;i<10;i++){
					
					hs2.setAttribute("time", time-1);
				}
				
			}
		};
		Timer timer=new Timer();
		timer.schedule(timertask,1000,1000);*/
		return new ModelAndView("forward:goFight.htm");
		
	}
	@SuppressWarnings("unused")
	@RequestMapping("fightNext.htm")
    public ModelAndView fightNext(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String message=null;
		User user=(User) request.getSession().getAttribute("user");
		if(user==null){
			return new ModelAndView("redirect:/jsp/homePage/homePage");
		}
		String icheck=request.getParameter("iCheck");//用户的回答
		System.out.println("icheckicheckicheckicheck"+icheck);
		HttpSession hs=request.getSession();
		///List<Answer> answer=(List<Answer>) request.getSession().getAttribute("options");
		String answer="1";
		boolean right;
		if(request.getParameter("useprop")!=null){
			String propId=request.getParameter("useprop");
			System.out.println("propidpropidpropidpropidpropidpropidpropid"+propId);
			Prop prop=(Prop) chservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propId", propId)).uniqueResult();
			UserProp up=(UserProp) chservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("user", user)).add(Restrictions.eq("prop", prop)).uniqueResult();
			int nu=up.getNumber();
			System.out.println("numberprop2numberprop2numberprop2numberprop2numberprop2"+nu);
			up.setNumber(nu-1);
			chservice.update(up);
			right=true;
		}else{
			right=answer.equals(icheck);
		}
		Challenge challengeuser2=(Challenge)hs.getAttribute("challengeuser");
		Challenge challenge=(Challenge) chservice.getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("challengeId", challengeuser2.getChallengeId())).uniqueResult();
		String type=challengeuser2.getMajor();
		//防止重复提交
		if(challengeuser2==null){
			message="回答错误,今日机会已经用完,日后再来吧,少年";
			request.setAttribute("message",message);//引导用户使用道具
			return new ModelAndView("forward:gochallenge.htm");
		}
		
		if(right){
			System.out.println("ooooooooooooooooooooooooo");
			
			
			
			int totalScore=challengeuser2.getTotalScore()+1;
			challengeuser2.setTotalScore(totalScore);
			challenge.setTotalScore(totalScore);
			int passNum=challengeuser2.getPassNum();
			challengeuser2.setPassNum(passNum+1);
			challenge.setPassNum(passNum+1);
			chservice.update(challenge);
			//hs.removeAttribute("challengeuser");
			hs.setAttribute("challengeuser", challengeuser2);
			
			//生成题目
			Question question;
			
			question=chservice.getquestion(type);
			//hs.removeAttribute("options");
			hs.setAttribute("question", question);
		//生成答案
			List<Answer> options;
			options=chservice.getanswer(question.getQuestionId());
			System.out.println("oooooooooooooo"+options.size());
			//hs.removeAttribute("options");
			hs.setAttribute("options",options);
		   //查询可使用道具
			Prop prop=(Prop) chservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propName", "免答")).uniqueResult();
			
			//用户道具
			UserProp up2=(UserProp) chservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("prop", prop)).add(Restrictions.eq("user", hs.getAttribute("user"))).add(Restrictions.gt("number", 0)).uniqueResult();
			request.setAttribute("userProp", up2);
			message="回答正确,加油!";
			request.setAttribute("message",message);
			return new ModelAndView("forward:goFight.htm");
			
		}else{
			
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			//生成排名
			int majorRank=chservice.getMajorRank(user,type);//此次排名
			challengeuser2.setMajorRank(majorRank);
			challenge.setMajorRank(majorRank);
			chservice.update(challenge);
			hs.setAttribute("challengeuser", challengeuser2);
			//获取历史最高分
			Challenge maxScore=(Challenge) chservice.getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("user", user)).addOrder(Order.desc("totalScore")).setMaxResults(1).uniqueResult();
			if(maxScore.getChallengeId()==challenge.getChallengeId()){
				maxScore=(Challenge) chservice.getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("user", user)).addOrder(Order.desc("totalScore")).setFirstResult(1).setMaxResults(1).uniqueResult();
			}
			int maxS=maxScore.getTotalScore();
			//历史最好排名
			
			//是否进入前十
			String rank10th="0";
			 if(majorRank<10){
				 rank10th="1";
			 }
			//是否打破最好排名
			 String rankBreak="0";
			 int maxR=((Challenge)chservice.getCurrentSession().createCriteria(Challenge.class).addOrder(Order.asc("majorRank")).setMaxResults(1).uniqueResult()).getMajorRank();
			 if(maxR>majorRank){
				 rankBreak="1";
			 }
			//比较一下历史分数
			
			String scoreBreak;
			if(maxS<challengeuser2.getTotalScore()){
				scoreBreak="1";
			}else{
				scoreBreak="0";
			}
			//读取用户道具数据
			//System.out.println(""+hs.getAttributeNames().nextElement());
			Prop prop=(Prop) chservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propName", "原地复活")).uniqueResult();
			System.out.println(""+prop.getPropName());
			Long propsum=(Long) chservice.getCurrentSession().createCriteria(UserProp.class).setProjection(Projections.sum("number")).add(Restrictions.eq("user", user)).uniqueResult();
			System.out.println("sumsumsumsumsumsumsumsumsumsumpropsumpropsum"+propsum);
			UserProp userProp=(UserProp) chservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("prop", prop)).add(Restrictions.eq("user", user)).add(Restrictions.gt("number", 0)).setMaxResults(1).uniqueResult();
			if(userProp==null){
				userProp=null;
				request.setAttribute("userProp", userProp);
			}else{
				request.setAttribute("propsum",propsum);
				request.setAttribute("userProp", userProp);
			}
			
			//如果没有道具则提示用户购买，添加商城链接
			
			message="回答错误,今日机会已经用完,日后再来吧,少年";
			request.setAttribute("scoreBreak", scoreBreak);
			request.setAttribute("message",message);//引导用户使用道具
			request.setAttribute("challengeuser", challengeuser2);
			request.setAttribute("rank10th", rank10th);
			request.setAttribute("rankbreak", rankBreak);
			request.setAttribute("maxR", maxR);
			request.setAttribute("maxS",maxS);
			hs.removeAttribute("challengeuser");
			return new ModelAndView("forward:goresult.htm");
		}
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("useProp.htm")
    public ModelAndView useProp(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//删除当日记录
		chservice.delete(chservice.getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("challengeId", request.getParameter("challengeId"))).uniqueResult());
		User user=(User) request.getSession().getAttribute("user");
		Prop prop=(Prop) chservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propName", "原地复活")).uniqueResult();
		UserProp userprop=(UserProp) chservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("prop", prop)).add(Restrictions.eq("user", user)).add(Restrictions.gt("number", 0)).setMaxResults(1).uniqueResult();
		if(userprop==null){
			return new ModelAndView("forward:challenge.htm");
		}
		String messagepropuse="道具使用成功";
		int number=userprop.getNumber();
		userprop.setNumber(number-1);
		chservice.update(userprop);
		List<DataDic> challengeclassic;
		challengeclassic=chservice.getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicKey", "专业分类")).list();
		HttpSession hs=request.getSession(false);
		hs.setAttribute("challengeclassic", challengeclassic);
		hs.setMaxInactiveInterval(300);
		request.setAttribute("message", messagepropuse);
		return new ModelAndView("forward:gochallenge.htm");
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("usePropFormHome.htm")
    public ModelAndView usePropFormHome(HttpServletRequest request,HttpServletResponse response)throws Exception{
		User user=(User) request.getSession().getAttribute("user");
		//删除当日记录
		if(request.getParameter("challengeId")==null){
			Challenge challenge=(Challenge) chservice.getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("user", user)).addOrder(Order.desc("challengeDate")).setMaxResults(1).uniqueResult();
			Date a=challenge.getChallengeDate();
			Date b=new Date();
			if(datecompare(a,b)){
				chservice.delete(challenge);
			}
		}else{
			chservice.delete(chservice.getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("challengeId", request.getParameter("challengeId"))).uniqueResult());

		}
		Prop prop=(Prop) chservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propName", "原地复活")).uniqueResult();
		UserProp userprop=(UserProp) chservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("prop", prop)).add(Restrictions.eq("user", user)).add(Restrictions.gt("number", 0)).setMaxResults(1).uniqueResult();
		if(userprop==null){
			return new ModelAndView("forward:challenge.htm");
		}
		String messagepropuse="道具使用成功";
		int number=userprop.getNumber();
		userprop.setNumber(number-1);
		chservice.update(userprop);
		List<DataDic> challengeclassic;
		challengeclassic=chservice.getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicKey", "专业分类")).list();
		HttpSession hs=request.getSession(false);
		hs.setAttribute("challengeclassic", challengeclassic);
		hs.setMaxInactiveInterval(100);
		request.setAttribute("message", messagepropuse);
		request.setAttribute("userProp", userprop);
		return new ModelAndView("forward:gochallenge.htm");
		
	}


}
