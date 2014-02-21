package org.sicdlab.microlecture.business.user.service.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.user.service.UserControllerService;
import org.sicdlab.microlecture.common.baseService.BaseService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.stereotype.Component;
import org.sicdlab.microlecture.common.bean.Level;


	@Component
	public class UserControllerServiceImpl extends BaseServiceImpl implements UserControllerService {

		@Override
		public User getUserInfo(String userid) {
			// TODO Auto-generated method stub
			
			
			User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userid)).setMaxResults(1).uniqueResult();
			return user;
		}

		@Override
		public Level getUserLevel(int credit) {
			// TODO Auto-generated method stub
			Level level=(Level) getCurrentSession().createCriteria(Level.class).add(Restrictions.le("lvCondition", credit)).add(Restrictions.eq("type", "用户")).addOrder(Order.desc("lvCondition")).setFirstResult(0).setMaxResults(1).uniqueResult();
			
			return level;
		}

		
		

	}