package org.sicdlab.microlecture.business.challenge.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javassist.runtime.Desc;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.challenge.service.ChallengeService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.Answer;
import org.sicdlab.microlecture.common.bean.Challenge;
import org.sicdlab.microlecture.common.bean.Question;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class ChallengeServiceImpl extends BaseServiceImpl implements ChallengeService{

	public Question getquestion(String type){
		
		Question question=new Question();
	    ArrayList<Question> li=(ArrayList<Question>) getCurrentSession().createCriteria(Question.class).add(Restrictions.eq("questionType",type)).list();
		Random rand = new Random();
		//DetachedCriteria dCriteria;
		//dCriteria=DetachedCriteria.forClass(Question.class);
		System.out.println("----------------"+li.size());
		int num=rand.nextInt(li.size());
		question=li.get(num);
     return question;
	}
	public List<Answer> getanswer(String s){
		@SuppressWarnings("unchecked")
		Question q=(Question) getCurrentSession().createCriteria(Question.class).add(Restrictions.eq("questionId", s)).uniqueResult();
		List<Answer> options=getCurrentSession().createCriteria(Answer.class).add(Restrictions.eq("question", q)).list();
		
		return options;
	}
	
	@SuppressWarnings({ "unused" })
	public int getMajorRank(User user,String type){
		ArrayList<User> userlist=(ArrayList<User>) getCurrentSession().createCriteria(Challenge.class).setProjection(Projections.groupProperty("user")).list();
		System.out.println("groupbybybybybybybybybybybybybybybyby"+userlist.size());
		ArrayList<Challenge> ranklist = new ArrayList<>();
		Challenge nowdayschallenge=(Challenge) getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("user", user)).add(Restrictions.eq("major", type)).addOrder(Order.desc("challengeDate")).setMaxResults(1).uniqueResult();
		for(int i=0;i<userlist.size();i++){
			System.out.println(userlist.get(0).getUserId());
			Challenge temp=(Challenge) getCurrentSession().createCriteria(Challenge.class).add(Restrictions.eq("major", type)).add(Restrictions.eq("user", userlist.get(i))).addOrder(Order.desc("totalScore")).setMaxResults(1).uniqueResult();
			ranklist.add(temp);
			temp=null;
		}
		Collections.sort(ranklist, new Comparator<Challenge>() {
            public int compare(Challenge arg0, Challenge arg1) {
                return arg1.getTotalScore().compareTo(arg0.getTotalScore());
            }
        });
		int j=0;
		for(int i=0;i<ranklist.size();i++){
			
			int b=nowdayschallenge.getTotalScore().compareTo(ranklist.get(i).getTotalScore());
			
			//boolean b=user.equals(ranklist.get(i).getUser());
			if(b>0)break;
			j++;
		}
		
		
		System.out.println(ranklist);
		System.out.println(j);
		return j+1;
	}
	
	
	
	
	public int getTeamRank(User user){
		return 0;
		
	}
	
}
