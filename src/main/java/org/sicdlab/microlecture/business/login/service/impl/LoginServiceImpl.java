package org.sicdlab.microlecture.business.login.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.login.service.LoginService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Component
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {
	@Override
	public String checkByEmailAndCookie(String cookieEmail, String cookiePswd) {
		
		List<User> userinfo=(List<User>) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", cookieEmail)).add(Restrictions.eqOrIsNull("password", cookiePswd)).add(Restrictions.eq("userState", "激活")).list();
		if(userinfo.size()!=0){
			return userinfo.get(0).getUserId();
		}else{
			return "no-this-person";	
		}
	
	}

	@Override
	public String checkByEmailAndPswd(String email, String pswd) {
	
		System.out.println("#################");
		List<User> userinfo=(List<User>) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email",email)).add(Restrictions.eq("password", pswd)).add(Restrictions.eq("userState", "激活")).list();
 		
		if(userinfo.size()!=0){
			return userinfo.get(0).getUserId();
		}else{
			
			return "no-such-person";
		}
	
	}
}
