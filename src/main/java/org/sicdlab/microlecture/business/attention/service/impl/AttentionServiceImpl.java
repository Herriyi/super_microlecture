package org.sicdlab.microlecture.business.attention.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.attention.service.AttentionService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.Attention;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@Component
public class AttentionServiceImpl extends BaseServiceImpl implements AttentionService {
	@Override
	public void addAttention(String userA, String userB) {
		// TODO Auto-generated method stub
		
		User userA1=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userA)).list().get(0);
		User userB1=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userB)).list().get(0);
		
		Attention attention=new Attention();
		List<Attention> list=getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByUserId", userB1)).add(Restrictions.eq("userByAttentionedUserId", userA1)).list();
		if(list.size()!=0){
		
		Attention attention1=list.get(0);
		attention1.setAttentionEach("互粉");
		getCurrentSession().save(attention1);
		
	
		
		attention.setUserByUserId(userA1);
		attention.setUserByAttentionedUserId(userB1);
		attention.setAttentionId(UUIDGenerator.randomUUID());
		attention.setAttentionDate(new Date());
		attention.setAttentionEach("互粉");
		getCurrentSession().save(attention);
		}else{
			attention.setUserByUserId(userA1);
			attention.setUserByAttentionedUserId(userB1);
			attention.setAttentionId(UUIDGenerator.randomUUID());
			attention.setAttentionDate(new Date());
			getCurrentSession().save(attention);
		}
	}

	@Override
	public void delAttention(String userAid,String userBid) {
		// TODO Auto-generated method stub
		
		
		//A取消对B的关注
		User userid=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userAid)).list().get(0);
		User userid1=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userBid)).list().get(0);
		
		List<Attention> list=getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByUserId", userid)).add(Restrictions.eq("userByAttentionedUserId", userid1)).list();
		
		Attention attention1=list.get(0);
		
		attention1.setAttentionEach("");
		
		getCurrentSession().update(attention1);
		
		Attention attention=(Attention) getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByUserId", userid)).add(Restrictions.eq("userByAttentionedUserId", userid1)).list().get(0);;
		
		getCurrentSession().clear();
		getCurrentSession().delete(attention);
	
	}

	@Override
	public List queryBothFans(String userAId) {
		
		// TODO Auto-generated method stub
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userAId)).list().get(0);
		
		
		
		
		List <Attention> list =getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByUserId", user)).add(Restrictions.eq("attentionEach", "互粉")).list();
		
		
		
		return list;
	}

	@Override
	public int sumAttention(String userAid) {
		// TODO Auto-generated method stub
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userAid)).list().get(0);
		
		List<Attention> list=getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByUserId", user)).list();
		
		return list.size();
	}

	@Override
	public int sumFans(String userAid) {
		// TODO Auto-generated method stub
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userAid)).list().get(0);
		
		List<Attention> list=getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByAttentionedUserId", user)).list();
		return list.size();
	}

	@Override
	public int sumBothFans(String userAid) {
		// TODO Auto-generated method stub
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userAid)).list().get(0);
		List <Attention> list =getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByUserId", user)).add(Restrictions.eq("attentionEach", "互粉")).list();
		
		return list.size();
	}

	

	

	@Override
	public List queryFans(String userAid) {
		
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userAid)).list().get(0);
		// TODO Auto-generated method stub
		List<Attention> list=getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByAttentionedUserId", user)).list();
		return list;
	}

	@Override
	public List queryAttention(String userAid) {
		// TODO Auto-generated method stub
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userAid)).list().get(0);
		
		List<Attention> list=getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByUserId", user)).list();
		
		
	
		return list;
	}

	
	@Override
	public boolean isAlreadyAttention(String userAid, String userBid) {
		// TODO Auto-generated method stub
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userAid)).list().get(0);
		User user1=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userBid)).list().get(0);
		
		List<Attention> list=getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByUserId", user)).add(Restrictions.eq("userByAttentionedUserId", user1)).list();
		if(list.size()==0){
			return true;
		}
		else{
			
			return false;
		}
	}
	public boolean isAlreadyBothFans(String userAid, String userBid) {
		// TODO Auto-generated method stub
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userAid)).list().get(0);
		User user1=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userBid)).list().get(0);
		
		List<Attention> list=getCurrentSession().createCriteria(Attention.class).add(Restrictions.eq("userByUserId", user)).add(Restrictions.eq("userByAttentionedUserId", user1)).add(Restrictions.eq("attentionEach", "互粉")).list();
		if(list.size()==0){
			return true;
		}
		else{
			
			return false;
		}
	}
}
