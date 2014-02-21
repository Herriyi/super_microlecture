package org.sicdlab.microlecture.common.aspect;

import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.sicdlab.microlecture.authority.annotation.CheckAuthority;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckAuthorityAsp implements Ordered{
	
	/**
	 * 申明一个切入点
	 */
	@Pointcut("execution(* org.sicdlab.microlecture.business..*.*(..))")
	public void skanPackage() {

	}

	@Before("skanPackage()&&@annotation(authority)")
	public void doBefore(JoinPoint joinPoint, CheckAuthority authority) {

		if (authority.value()) {
			String signature = joinPoint.getSignature().toString();
			System.out.println("signature:" + signature);
			// 判断是否 有权限，无则抛出定义好的异常
			
		}
	}
	
	
	
	
	
	

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
