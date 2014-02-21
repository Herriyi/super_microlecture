package org.sicdlab.microlecture.business.privateMail.service;

import java.util.List;

import org.sicdlab.microlecture.common.baseService.BaseService;

public interface PrivateMailService extends BaseService {
	
	public void sendMail(String sender,String receiver,String context);
	
	public List userMailList(String userid);
	
	public int sumMail(String userid);
	
	public void delMail(String mailid);
}
