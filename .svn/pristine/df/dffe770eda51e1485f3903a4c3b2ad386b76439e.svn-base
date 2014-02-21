package org.sicdlab.microlecture.business.microRank.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.microRank.service.MicroRankService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.Challenge;
import org.sicdlab.microlecture.common.bean.DataDic;
import org.sicdlab.microlecture.common.bean.Team;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Component
public class MicroRankServiceImpl extends BaseServiceImpl implements MicroRankService{
	@Override
	public  ArrayList<ArrayList<Challenge>> getbiglist(List<DataDic> typeList){
		ArrayList<ArrayList<Challenge>> biglist=new ArrayList<>();
		//List<DataDic> typeList=getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicKey", "专业分类")).list();
		//ArrayList<Challenge> ranklist = new ArrayList<>();

		ArrayList<User> userlist=(ArrayList<User>) getCurrentSession().createCriteria(Challenge.class).setProjection(Projections.groupProperty("user")).list();
		int k;
		for(int j=0;j<typeList.size();j++){
			ArrayList<Challenge> ranklist = new ArrayList<>();
			String type=typeList.get(j).getDicValue();
			User user;
			k=0;
			System.out.println("typeyeptypetypetype"+type);
			for(int i=0;i<userlist.size();i++){
				Challenge temp=new Challenge();
				System.out.println(userlist.get(i).getUserId());
				//user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId",userlist.get(i).getUserId())).uniqueResult();
				temp=(Challenge)getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("major", type)).add(Restrictions.eq("user", userlist.get(i))).addOrder(Order.desc("totalScore")).setMaxResults(1).uniqueResult();
				//if(temp!=null){
					ranklist.add(temp);
					//System.out.println("iiiiiiiiiiiiiiiiiiiii"+i);
				//}
				if(temp!=null){k++;}
				//user=null;
				temp=null;
			}
			//System.out.println("__________________________________"+ranklist+"---------"+ranklist.size());
			if(k==userlist.size()){
			Collections.sort(ranklist, new Comparator<Challenge>() {
	            public int compare(Challenge arg0, Challenge arg1) {
	                return arg1.getTotalScore().compareTo(arg0.getTotalScore());
	            }
	        });
			}
			//if(ranklist.size()>=1){
				biglist.add(ranklist);
			//}
			ranklist=null;
		}
		//System.out.println("sizesssssssssssssss"+userlist.size());
		//System.out.println("+++++++++++++++"+biglist.size()+"+++++++"+biglist.get(0));
		return biglist;
	}
	@Override
	public List getTypeList(){
		List typeList=getCurrentSession().createCriteria(DataDic.class).add(Restrictions.eq("dicKey", "专业分类")).list();
		
		return typeList;
	}

	@Override
	public ArrayList<ArrayList<Team>> getteambiglist(ArrayList<DataDic> typeList) {
		ArrayList<ArrayList<Team>> biglist=new ArrayList<>();
		ArrayList<Team> ranklist = new ArrayList<>();
		//ArrayList<User> userlist=(ArrayList<User>) getCurrentSession().createCriteria(Challenge.class).setProjection(Projections.groupProperty("user")).list();
		String type;
		for(int j=0;j<typeList.size();j++){
			 type=typeList.get(j).getDicValue();
			//for(int i=0;i<userlist.size();i++){
				//System.out.println(userlist.get(0).getUserId());
			 ArrayList<Team> temp=new ArrayList<>();
			 temp=(ArrayList<Team>)getCurrentSession().createCriteria(Team.class).add(Restrictions.eq("teamState", "批准")).add(Restrictions.eq("type", type)).addOrder(Order.desc("construction")).list();
				
			//}
			//Collections.sort(temp, new Comparator<Team>() {
	        //    public int compare(Team arg0, Team arg1) {
	        //        return arg1.getConstruction().compareTo(arg0.getConstruction());
	        //    }
	       // });
			
			biglist.add(temp);
			temp=null;
		}
		
		return biglist;
	}

}
