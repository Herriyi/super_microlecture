package org.sicdlab.microlecture.business.privateMail.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.privateMail.service.PrivateMailService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.Message;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Component
public class PrivateMailServiceImpl extends BaseServiceImpl implements PrivateMailService {
	
	@Override
	public void sendMail(String sender, String receiver, String context) {
		// TODO Auto-generated method stub
		
		Message ms=new Message();
		User user1=new User();
		user1.setUserId(sender);
		User user2=new User();
		
		user2.setUserId(receiver);
		ms.setSendDate(new Date());
		ms.setMessageId(UUIDGenerator.randomUUID());
		ms.setUserBySenderId(user1);
		ms.setUserByReceiverId(user2);
		ms.setContent(context);
		ms.setMessageState("未读");
		
		getCurrentSession().save(ms);
		
		
	}

	@Override
	public List userMailList(String userid) {
		// TODO Auto-generated method stub
		
		User user=new User();
		user.setUserId(userid);
		
		List<Message> msgList=getCurrentSession().createCriteria(Message.class).add(Restrictions.eq("userByReceiverId", user)).addOrder(Order.desc("sendDate")).list();
		
		
		return msgList;
	}

	@Override
	public void delMail(String mailid) {
		// TODO Auto-generated method stub
		
		Message msg=new Message();
		msg.setMessageId(mailid);
		
		getCurrentSession().clear();
		
		getCurrentSession().delete(msg);
		
		
	}

	@Override
	public int sumMail(String userid) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setUserId(userid);
		List<Message> list=getCurrentSession().createCriteria(Message.class).add(Restrictions.eq("userByReceiverId", user)).add(Restrictions.eq("messageState", "未读")).list();
		
		
		return list.size();
	}
	
	
	
}
