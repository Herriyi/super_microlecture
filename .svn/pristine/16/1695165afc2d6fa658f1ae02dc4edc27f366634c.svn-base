package org.sicdlab.microlecture.business.note.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.note.service.NoteService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.Grade;
import org.sicdlab.microlecture.common.bean.User;
import org.springframework.stereotype.Component;


@Component
public class NoteServiceImpl extends BaseServiceImpl implements NoteService {

	@Override
	public boolean IsAreadyGrade(User user,String ObjId) {
		// TODO Auto-generated method stub
		
		List<Grade> list=getCurrentSession().createCriteria(Grade.class).add(Restrictions.eq("user", user)).add(Restrictions.eq("gradeObject", ObjId)).list();
		
		if(list.size()!=0){
			return false;
		}else{
			return true;	
		}
	}

	@Override
	public double queryGrade(String ObjId) {
		// TODO Auto-generated method stub
		
		String sql="select avg(mark) from grade where GRADE_OBJECT='"+ObjId+"'";
		double avgGrade=(double) getCurrentSession().createQuery(sql).uniqueResult();
		
		double a=(Math.round(avgGrade*10+5))/10;
		return a;
	}

}
