package org.sicdlab.microlecture.business.attention.service;

import java.util.List;

import org.sicdlab.microlecture.common.baseService.BaseService;

public interface AttentionService extends BaseService{
	
	public void addAttention(String userA,String userB);//添加关注 需要自己的Id 和被关注人的Id
	
	public List queryBothFans(String userAid);//查询 与登录用户互粉的人的ID
	
	
	
	public void delAttention(String userAid,String userBid);//userBid 是你要取消关注的人的Id
	
	public int sumAttention(String userAid);//统计登录人的关注的人的总数
	
	public int sumFans(String userAid);//统计登录人的粉丝的数量
	
	
	public int sumBothFans(String userAid);//统计登录人的互粉的数量
	
	public List queryFans(String userAid);
	
	public List queryAttention(String userAid);
	
	public boolean isAlreadyAttention(String userAid,String userBid);
	public boolean isAlreadyBothFans(String userAid,String userBid);
}
