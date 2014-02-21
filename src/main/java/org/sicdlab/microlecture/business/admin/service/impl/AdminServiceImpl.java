package org.sicdlab.microlecture.business.admin.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.admin.service.AdminService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Component
public class AdminServiceImpl extends BaseServiceImpl implements AdminService {
	@Override
	public String checkByEmailAndPswd(String email, String pswd) {
	
		System.out.println("#################");
		List<User> userinfo=(List<User>) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email",email)).add(Restrictions.eq("password", pswd)).add(Restrictions.eq("userState", "管理员")).list();
 		
		if(userinfo.size()!=0){
			return userinfo.get(0).getUserId();
		}else{
			
			return "no-such-person";
		}
	
	}
}
