package org.sicdlab.microlecture.business.admin.service;

import org.sicdlab.microlecture.common.baseService.BaseService;

public interface AdminService extends BaseService {
	public String checkByEmailAndPswd(String email, String pswd);
}
