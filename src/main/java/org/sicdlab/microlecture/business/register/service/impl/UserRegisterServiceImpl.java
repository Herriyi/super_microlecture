package org.sicdlab.microlecture.business.register.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.register.service.UserRegisterService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.stereotype.Service;

@Service("UserRegisterServiceImpl")
public class UserRegisterServiceImpl extends BaseServiceImpl implements UserRegisterService{

	@Override
	public void userRegister(User user) {
		// TODO Auto-generated method stub
		getCurrentSession().save(user);
		
	}

	@Override
	public void emailVerify(User user) {
		// TODO Auto-generated method stub
		getCurrentSession().update(user);
	}

	@Override
	public int checkEmail(String userEmail) {
		// TODO Auto-generated method stub
		List<User> list=getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", userEmail)).list();
		
		
		return list.size();
	}

	@Override
	public User getUserInfo(String userId) {
		// TODO Auto-generated method stub
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userId)).setMaxResults(1).uniqueResult();
		
		return user;
	}

	@Override
	public User getUserInfo1(String userEmail) {
		// TODO Auto-generated method stub
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", userEmail)).setMaxResults(1).uniqueResult();
		
		return user;
	}

	@Override
	public int checkNickName(String nickname) {
		// TODO Auto-generated method stub
		
		List<User> list=getCurrentSession().createCriteria(User.class).add(Restrictions.eq("nickname", nickname)).list();
		
		
		return list.size();
		
	}
	
	
	
}
