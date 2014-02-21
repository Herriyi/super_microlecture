package org.sicdlab.microlecture.common.aspect;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.sicdlab.microlecture.authority.annotation.CheckAuthority;
import org.sicdlab.microlecture.common.baseService.BaseService;
import org.sicdlab.microlecture.common.bean.Message;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.util.ServletUtil;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GoldAndCreditAsp implements Ordered {
	
	
	private BaseService service;

	@Resource(name = "baseServiceImpl")
	public void setService(BaseService service) {
		this.service = service;
	}
	/**
	 * 申明一个切入点
	 */ 
	@Pointcut("execution(* org.sicdlab.microlecture.business..*.*(..))")
	public void skanPackage() {

	}
	@After("skanPackage()&&@annotation(authority)")
	public void doAfter(JoinPoint joinPoint, CheckAuthority authority) {
		
		System.out.println("加分还是减分呢？%%%%%%%%%%%%%");
		
		HttpSession session = ServletUtil.getRequest().getSession();
		HttpServletRequest request = ServletUtil.getRequest();
		User user = (User) session.getAttribute("user");
		System.out.println("userId---------------:"+user.getUserId());
		
		String action=authority.name().trim();
		
		if(user!=null){
			System.out.println("-------------"+action);
			int gold=user.getGold();
			int credit=user.getCredit();
			
			if(action.equals("创建课程")){
				user.setGold(gold+50);
				user.setCredit(credit+100);
				service.update(user);
				sendAmail(user, action, "+50", "+100");
			}
			if(action.equals("删除课程")){
				user.setGold(gold-50);
				user.setCredit(credit-100);
				service.update(user);
				sendAmail(user, action, "-50", "-100");
							
			}
			if(action.equals("标为原创")){
				user.setGold(gold+30);
				user.setCredit(credit+300);
				service.update(user);	
				sendAmail(user, action, "+30", "+300");
	
			}
			if(action.equals("课程加精华")){
				user.setGold(gold+50);
				user.setCredit(credit+500);
				service.update(user);	
				sendAmail(user, action, "+50", "+500");
			}
			if(action.equals("学习课程")){
				
				user.setCredit(credit+5);
				service.update(user);	
				sendAmail(user, action, "+0", "+5");
			}
			if(action.equals("")){
				user.setGold(gold+1);
				user.setCredit(credit+10);
				service.update(user);
				
				sendAmail(user, action, "+1", "+10");
	
			}
			if(action.equals("发表话题")){
				user.setGold(gold+3);
				user.setCredit(credit+10);
				service.update(user);	
				sendAmail(user, action, "+3", "+10");
			}
			if(action.equals("回复话题")){
				user.setGold(gold+1);
				user.setCredit(credit+5);
				service.update(user);
				sendAmail(user, action, "+1", "+5");
	
			}
			if(action.equals("删除话题")){
				user.setGold(gold-2);
				user.setCredit(credit-20);
				service.update(user);
				sendAmail(user, action, "-2", "-20");
	
			}
			if(action.equals("删除回复")){
				user.setGold(gold-1);
				user.setCredit(credit-10);
				service.update(user);
				sendAmail(user, action, "-1", "-10");
	
			}
			if(action.equals("删除小组")){
				user.setGold(gold-5);
				user.setCredit(credit-20);
				service.update(user);
				sendAmail(user, action, "-5", "-20");
	
			}
			
		}
		
	}
	public void sendAmail(User receiver,String action,String gold,String credit){
		
		User sender=service.findById(User.class, "0");
		String context="尊敬的用户,由于您**"+action+"**,您的学分"+credit+",您的金币"+gold;
		Message message=new Message();
		message.setMessageId(UUIDGenerator.randomUUID());
		message.setMessageState("未读");
		message.setSendDate(new Date());
		message.setContent(context);
		message.setUserByReceiverId(receiver);
		message.setUserBySenderId(sender);
		service.save(message);
	}
	
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
