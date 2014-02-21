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
import org.sicdlab.microlecture.common.bean.OperationLog;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.util.BrowseTool;
import org.sicdlab.microlecture.util.ServletUtil;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogRecordAsp implements Ordered {
	
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
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		HttpSession session = ServletUtil.getRequest().getSession();
		HttpServletRequest request = ServletUtil.getRequest();
		User user = (User) session.getAttribute("user");
		System.out.println("userId:"+user.getUserId());
		if (user!=null) {
			System.out.println("##########################");
			OperationLog log = new OperationLog();
			log.setLogId(UUIDGenerator.randomUUID());
			log.setLogDate(new Date());
			log.setLogOperation(authority.name().trim());
			log.setLogUserIp(request.getRemoteAddr());
			log.setLogExplorer(BrowseTool.checkBrowse(request
					.getHeader("User-agent")));
			log.setUserId(user.getUserId());
			service.save(log);
         
		}
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
