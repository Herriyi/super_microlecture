package org.sicdlab.microlecture.business.note.service;

import org.sicdlab.microlecture.common.baseService.BaseService;
import org.sicdlab.microlecture.common.bean.User;

public interface NoteService extends BaseService {
	
	public boolean IsAreadyGrade(User user,String ObjId);
	
	
	public double queryGrade(String ObjId);
	
	
}
