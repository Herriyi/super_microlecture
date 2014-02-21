package org.sicdlab.microlecture.business.login.service;

import org.sicdlab.microlecture.common.baseService.BaseService;

public interface LoginService extends BaseService {
	String checkByEmailAndCookie(String cookieEmail,String cookiePswd);
	String checkByEmailAndPswd(String email,String pswd);

}
