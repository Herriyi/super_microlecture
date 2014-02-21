package org.sicdlab.microlecture.business.admin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.authority.annotation.CheckAuthority;
import org.sicdlab.microlecture.business.admin.service.AdminService;
import org.sicdlab.microlecture.business.privateMail.service.PrivateMailService;
import org.sicdlab.microlecture.common.bean.Announcement;
import org.sicdlab.microlecture.common.bean.Authority;
import org.sicdlab.microlecture.common.bean.Course;
import org.sicdlab.microlecture.common.bean.DataDic;
import org.sicdlab.microlecture.common.bean.FriendliLink;
import org.sicdlab.microlecture.common.bean.Inform;
import org.sicdlab.microlecture.common.bean.Level;
import org.sicdlab.microlecture.common.bean.LevelAuthority;
import org.sicdlab.microlecture.common.bean.Message;
import org.sicdlab.microlecture.common.bean.Note;
import org.sicdlab.microlecture.common.bean.OperationLog;
import org.sicdlab.microlecture.common.bean.Rule;
import org.sicdlab.microlecture.common.bean.SensitiveWords;
import org.sicdlab.microlecture.common.bean.Team;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.bean.UserCourse;
import org.sicdlab.microlecture.common.bean.UserTeam;
import org.sicdlab.microlecture.common.tag.pageTag.PageHelper;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;





@Controller
public class AdminController {
	
	@Autowired
	private AdminService admin;
	@Autowired
	private PrivateMailService pmservice;
	/*##################管理员登陆部分##################*/
	@RequestMapping("goAdminLogin.htm")
	public ModelAndView goAdminLogin() {

		return new ModelAndView("/admin/login");
	}
	
	
	@RequestMapping("adminLogin.htm")
	public ModelAndView adminLogin(HttpServletRequest request) throws Exception{
		HttpSession hs=request.getSession();
		hs.invalidate();
		String userEmail=ServletRequestUtils.getStringParameter(request, "userEmail");
		String userPswd=ServletRequestUtils.getStringParameter(request, "userPassword");
		String userid=admin.checkByEmailAndPswd(userEmail,userPswd);
		int sumEmail=pmservice.sumMail(userid);
		if(!userid.equals("no-such-person")){
			User userinfo=admin.findById(User.class, userid);
			hs=request.getSession();
			hs.setAttribute("user", userinfo);
			hs.setAttribute("sumEmail", sumEmail);
			return new ModelAndView("redirect:goAdminHome.htm");
		}else{
			request.setAttribute("note", "邮箱或是密码不正确，请重新输入");
			return new ModelAndView("/admin/login");
		}
		
	}

	
	@RequestMapping("goAdminHome.htm")
	public ModelAndView goAdminHome(HttpServletRequest request) {
		User user=(User) request.getSession().getAttribute("user");
		
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			//用户数
			DetachedCriteria dCriteria=DetachedCriteria.forClass(User.class);
			List<User> userlist=admin.queryAllOfCondition(User.class, dCriteria);
			int userSum=userlist.size();
			//课程数
			DetachedCriteria dCriteria1=DetachedCriteria.forClass(Course.class);
			dCriteria1.add(Restrictions.eq("courseState", "批准"));
			dCriteria1.add(Restrictions.isNull("course"));
			List<Course> courselist=admin.queryAllOfCondition(Course.class, dCriteria1);
			int courseSum=courselist.size();
			//小组数
			DetachedCriteria dCriteria2=DetachedCriteria.forClass(Team.class);
			dCriteria2.add(Restrictions.eq("teamState", "批准"));
			List<Team> teamlist=admin.queryAllOfCondition(Team.class, dCriteria2);
			int teamSum=teamlist.size();
			//笔记数
			DetachedCriteria dCriteria3=DetachedCriteria.forClass(Note.class);
			List<Note> notelist=admin.queryAllOfCondition(Note.class, dCriteria3);
			int noteSum=notelist.size();
			//最近操作
			DetachedCriteria dCriteria4=DetachedCriteria.forClass(OperationLog.class);
			dCriteria4.addOrder(Order.desc("logDate"));
			List<OperationLog> logelist=admin.queryMaxNumOfCondition(OperationLog.class, dCriteria4, 5);
			//最新课程
			DetachedCriteria dCriteria5=DetachedCriteria.forClass(Course.class);
			dCriteria5.add(Restrictions.eq("courseState", "批准"));
			dCriteria5.add(Restrictions.isNull("course"));
			dCriteria5.addOrder(Order.desc("applyDate"));
			List<Course> courselist1=admin.queryMaxNumOfCondition(Course.class, dCriteria5, 5);
			
			//最新小组
			DetachedCriteria dCriteria6=DetachedCriteria.forClass(Team.class);
			dCriteria6.add(Restrictions.eq("teamState", "批准"));
			dCriteria6.addOrder(Order.desc("applyDate"));
			List<Team> teamlist1=admin.queryMaxNumOfCondition(Team.class, dCriteria6, 5);
			
			//最新笔记
			DetachedCriteria dCriteria7=DetachedCriteria.forClass(Note.class);
			dCriteria7.addOrder(Order.desc("addDate"));
			List<Note> notelist1=admin.queryMaxNumOfCondition(Note.class, dCriteria7, 5);
			
			request.setAttribute("userSum", userSum);
			request.setAttribute("courseSum", courseSum);
			request.setAttribute("teamSum", teamSum);
			request.setAttribute("noteSum", noteSum);
			request.setAttribute("logelist", logelist);
			request.setAttribute("courselist1", courselist1);
			request.setAttribute("teamlist1", teamlist1);
			request.setAttribute("notelist1", notelist1);
			return new ModelAndView("/admin/index");
		}
		
	}
	@RequestMapping("logout1.htm")
	public ModelAndView logout1(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:goAdminLogin.htm");
	}
	
	/*##################管理员用户管理##################*/
	@CheckAuthority(name="管理用户")
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToUserManage.htm")
	public ModelAndView turnToUserManage(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(User.class);
		detachedCriteria.add(Restrictions.ne("userState", "管理员"));
		int pageSize=6;
		int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
		PageHelper.forPage(totalPage, pageSize);
		List<User> userList = (List<User>) admin.getByPage(detachedCriteria, pageSize);
		
		return new ModelAndView("/admin/userManage", "userList", userList);}
	}
	
	@RequestMapping("activateAccount.htm")
	public ModelAndView activateAccount(HttpServletRequest req,
			HttpServletResponse res) {
		String userId = ServletRequestUtils.getStringParameter(req, "userId",
				"");
		User user = admin.findById(User.class, userId);
		user.setUserState("激活");
		admin.update(user);
		return new ModelAndView("redirect:turnToUserManage.htm");
	}
	@RequestMapping("lockAccount.htm")
	public ModelAndView lockAccount(HttpServletRequest req,
			HttpServletResponse res) {
		String userId = ServletRequestUtils.getStringParameter(req, "userId",
				"");
		User user = admin.findById(User.class, userId);
		user.setUserState("锁定");
		admin.update(user);
		return new ModelAndView("redirect:turnToUserManage.htm");
	}
	/*##################管理员管理公告##################*/
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToAnnouncementManage.htm")
	public ModelAndView turnToAnnouncementManage(HttpServletRequest req,
			HttpServletResponse res) {
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Announcement.class)
        		.addOrder(Order.desc("publishDate"));
        int pageSize=8;
        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<Announcement> announcements=(List<Announcement>) admin.getByPage(detachedCriteria, pageSize);
		return new ModelAndView("/admin/boardManage","announcements",announcements);
	}
	
	@RequestMapping("addAnnouncement.htm")
	public ModelAndView addAnnouncement(HttpServletRequest req,
			HttpServletResponse res) {
        String announcementContent=ServletRequestUtils.getStringParameter(req, "content", "");
        Announcement announcement=new Announcement();
        announcement.setAnnouncementId(UUIDGenerator.randomUUID());
        announcement.setAnnouncementContent(announcementContent);
        announcement.setPublishDate(new Date());
        admin.save(announcement);
		return new ModelAndView("redirect:turnToAnnouncementManage.htm");
	}
	
	@RequestMapping("alterAnnouncement.htm")
	public ModelAndView alterAnnouncement(HttpServletRequest req,
			HttpServletResponse res) {
        String announcementContent=ServletRequestUtils.getStringParameter(req, "alterContent", "");
        String announcementId=ServletRequestUtils.getStringParameter(req, "announcementId", "");
        Announcement announcement=admin.findById(Announcement.class, announcementId);        
        announcement.setAnnouncementContent(announcementContent);
        announcement.setPublishDate(new Date());
        admin.update(announcement);
		return new ModelAndView("redirect:turnToAnnouncementManage.htm");
	}
	@RequestMapping("deletAnnouncement.htm")
	public ModelAndView deletAnnouncement(HttpServletRequest req,
			HttpServletResponse res) {       
        String announcementId=ServletRequestUtils.getStringParameter(req, "announcementId", "");
        Announcement announcement=admin.findById(Announcement.class, announcementId);        
        admin.delete(announcement);
		return new ModelAndView("redirect:turnToAnnouncementManage.htm");
	}
	/*##################管理员管理数据字典##################*/
	
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToDictionaryManage.htm")
	public ModelAndView turnToDictionaryManage(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(DataDic.class)
        		.addOrder(Order.asc("dicKey"));
        int pageSize=8;
        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<DataDic> dataDictionaries=(List<DataDic>)admin.getByPage(detachedCriteria, pageSize);
		return new ModelAndView("/admin/dateDictionManage","dataDictionaries",dataDictionaries);}
	}
	
	@RequestMapping("addIterm.htm")
	public ModelAndView addIterm(HttpServletRequest req,
			HttpServletResponse res) {
       String inputKey=ServletRequestUtils.getStringParameter(req, "inputKey", "");
       String inputValue=ServletRequestUtils.getStringParameter(req, "inputValue", "");
       String inputIntro=ServletRequestUtils.getStringParameter(req, "inputIntro", "");
       DataDic dataDictionary=new DataDic();
       dataDictionary.setDicId(UUIDGenerator.randomUUID());
       dataDictionary.setDicKey(inputKey);
       dataDictionary.setDicValue(inputValue);
       dataDictionary.setIntro(inputIntro);
       admin.save(dataDictionary);
		return new ModelAndView("redirect:turnToDictionaryManage.htm");
	}
	
	@RequestMapping("alterIterm.htm")
	public ModelAndView alterIterm(HttpServletRequest req,
			HttpServletResponse res) {
       String alterKey=ServletRequestUtils.getStringParameter(req, "alterKey", "");
       String alterValue=ServletRequestUtils.getStringParameter(req, "alterValue", "");
       String alterIntro=ServletRequestUtils.getStringParameter(req, "alterIntro", "");
       String itermId=ServletRequestUtils.getStringParameter(req, "itermId", "");

       DataDic dataDictionary=admin.findById(DataDic.class, itermId);
       dataDictionary.setDicKey(alterKey);
       dataDictionary.setDicValue(alterValue);
       dataDictionary.setIntro(alterIntro);
       admin.update(dataDictionary);
		return new ModelAndView("redirect:turnToDictionaryManage.htm");
	}
	
	@RequestMapping("deletIterm.htm")
	public ModelAndView deletIterm(HttpServletRequest req,
			HttpServletResponse res) {
       String itermId=ServletRequestUtils.getStringParameter(req, "itermId", "");
       DataDic dataDictionary=admin.findById(DataDic.class, itermId);
       admin.delete(dataDictionary);
		return new ModelAndView("redirect:turnToDictionaryManage.htm");
	}
	
	
	
	/*##################管理员管理友情链接##################*/
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToFriendLinkManage.htm")
	public ModelAndView turnToFriendLinkManage(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(FriendliLink.class);
        		
        int pageSize=8;
        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<FriendliLink> friendliLink=(List<FriendliLink>)admin.getByPage(detachedCriteria, pageSize);
		return new ModelAndView("/admin/friendLinkManage","friendLink",friendliLink);}
	}
	
	@RequestMapping("addFriendLink.htm")
	public ModelAndView addFriendLink(HttpServletRequest req,
			HttpServletResponse res)throws Exception {
       String linkName=ServletRequestUtils.getStringParameter(req, "linkName", "");
       String url=ServletRequestUtils.getStringParameter(req, "url", "");
       
       FriendliLink friendLink=new FriendliLink();
       friendLink.setFriendlyLinkId(UUIDGenerator.randomUUID());
       friendLink.setLinkName(linkName);
       friendLink.setUrl(url);
     
       admin.save(friendLink);
		return new ModelAndView("redirect:turnToFriendLinkManage.htm");
	}
	
	@RequestMapping("alterFriendLink.htm")
	public ModelAndView alterFriendLink(HttpServletRequest req,
			HttpServletResponse res) {
       String linkName=ServletRequestUtils.getStringParameter(req, "linkName", "");
       String url=ServletRequestUtils.getStringParameter(req, "url", "");
    
       String friendlyLinkId=ServletRequestUtils.getStringParameter(req, "friendlyLinkId", "");

       FriendliLink friendLink=admin.findById(FriendliLink.class, friendlyLinkId);
       friendLink.setLinkName(linkName);
       friendLink.setUrl(url);
       
       admin.update(friendLink);
		return new ModelAndView("redirect:turnToFriendLinkManage.htm");
	}
	
	@RequestMapping("deletFriendLink.htm")
	public ModelAndView deletFriendLink(HttpServletRequest req,
			HttpServletResponse res) {
       String friendlyLinkId=ServletRequestUtils.getStringParameter(req, "friendlyLinkId", "");
       FriendliLink friendLink=admin.findById(FriendliLink.class, friendlyLinkId);
       admin.delete(friendLink);
		return new ModelAndView("redirect:turnToFriendLinkManage.htm");
	}
	
	/*##################管理员管理举报##################*/
	@SuppressWarnings("unchecked")
	@RequestMapping("showInformList.htm")
	public ModelAndView showInformList(HttpServletRequest request) throws Exception{
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Inform.class);
		
		dCriteria.addOrder(Order.desc("informDate"));
		int pageSize = 4;
		int totalPage = admin.countTotalPage(dCriteria, pageSize);
		PageHelper.forPage(totalPage, pageSize);
		
		List<Inform> reportList = (List<Inform>) admin.getByPage(dCriteria, pageSize);
		
		
		return new ModelAndView("/admin/reportManage","reportList",reportList);
	}
	
	@RequestMapping("dealReport.htm")
	public ModelAndView dealReport(HttpServletRequest request) throws Exception{
	
		String informObjectType = ServletRequestUtils.getStringParameter(request, "informType","");
		String informObjectId = ServletRequestUtils.getStringParameter(request, "informObject","");
		String imformId = ServletRequestUtils.getStringParameter(request, "informId","");
		String type = ServletRequestUtils.getStringParameter(request, "type","");
		
		System.out.println("informObjectType++"+informObjectType+"informObjectId++"+informObjectId+"imformId++"+imformId+"type++"+type);
		Inform inform=admin.findById(Inform.class, imformId);
		
		//忽略
		if(type.equals("1")){
			admin.delete(inform);
			return new ModelAndView("redirect:showInformList.htm");
		}
		//接受
		if(type.equals("2")){
			System.out.println("$$$$$$$$$$$$$");
			if(informObjectType.equals("user")){
				User user=admin.findById(User.class, informObjectId);
				user.setUserState("锁定");
				admin.update(user);
				inform.setInformState("已处理");
				admin.update(inform);
				return new ModelAndView("redirect:showInformList.htm");
			}
			if(informObjectType.equals("course")){
				Course course=admin.findById(Course.class, informObjectId);
				course.setCourseState("封禁");
				admin.update(course);
				inform.setInformState("已处理");
				admin.update(inform);
				return new ModelAndView("redirect:showInformList.htm");
			}
			if(informObjectType.equals("note")){
				Note note=admin.findById(Note.class, informObjectId);
				note.setPublic_("封禁");
				admin.update(note);
				inform.setInformState("已处理");
				admin.update(inform);
				return new ModelAndView("redirect:showInformList.htm");
			}
			if(informObjectType.equals("team")){
				Team team=admin.findById(Team.class, informObjectId);
				team.setTeamState("封禁");
				admin.update(team);
				inform.setInformState("已处理");
				admin.update(inform);
				return new ModelAndView("redirect:showInformList.htm");
			}
			
		}
		
		return new ModelAndView("redirect:showInformList.htm");
	}
	/*##################管理员管理 小组##################*/
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToTeamManage.htm")
	public ModelAndView turnToTeamManage(HttpServletRequest req) throws Exception{
		
			User user=(User) req.getSession().getAttribute("user");
			if(user==null){
				
				return new ModelAndView("redirect:goAdminLogin.htm");
			}else{
				
	        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Team.class);
	        detachedCriteria.addOrder(Order.desc("applyDate"));
	        int pageSize=8;
	        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
	        PageHelper.forPage(totalPage, pageSize);
	        List<Team> teamList=(List<Team>)admin.getByPage(detachedCriteria, pageSize);
			return new ModelAndView("/admin/teamManage","teamList",teamList);}
	}
	@RequestMapping("alterTeamState.htm")
	public ModelAndView alterTeamState(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
	
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
		String teamId=req.getParameter("teamId");
		System.out.println(teamId);
		String type=req.getParameter("type");
		
		
		Team team=admin.findById(Team.class, teamId);
		
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(UserTeam.class);
		
		detachedCriteria.add(Restrictions.eq("team", team));
		detachedCriteria.add(Restrictions.eq("userPosition", "组长"));
		
		List<UserTeam> userlist= admin.queryMaxNumOfCondition(UserTeam.class, detachedCriteria, 1);
		
		User user1=userlist.get(0).getUser();
		int gold=user1.getGold();
		int credit=user1.getCredit();
		if(type.equals("1")){
			team.setApproveDate(new Date());
			team.setTeamState("批准");
			admin.update(team);
			
			
			user1.setGold(gold+1);
			user1.setCredit(credit+10);
			admin.update(user);
			
			sendAmail(user1, "创建小组成功", "+1", "+10");
			return new ModelAndView("redirect:turnToTeamManage.htm");
		}
		if(type.equals("2")){
			
			team.setTeamState("封禁");
			admin.update(team);
			
			
			user1.setGold(gold-3);
			user1.setCredit(credit-30);
			admin.update(user);
			
			sendAmail(user1, "小组被封禁", "-3", "-30");
			return new ModelAndView("redirect:turnToTeamManage.htm");
		}
        
		return new ModelAndView("redirect:turnToTeamManage.htm");
		
		
		}
	}
	
	/*##################管理员发邮件##################*/
	public void sendAmail(User receiver,String action,String gold,String credit){
		
		User sender=admin.findById(User.class, "0");
		String context="尊敬的用户,由于您**"+action+"**,您的学分"+credit+",您的金币"+gold;
		Message message=new Message();
		message.setMessageId(UUIDGenerator.randomUUID());
		message.setMessageState("未读");
		message.setSendDate(new Date());
		message.setContent(context);
		message.setUserByReceiverId(receiver);
		message.setUserBySenderId(sender);
		admin.save(message);
	}
	
	/*##################管理员管理课程##################*/
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToCourseManage.htm")
	public ModelAndView turnToCourseManage(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Course.class);
      
        detachedCriteria.add(Restrictions.eq("courseState", "申请中"));
        detachedCriteria.add(Restrictions.isNull("course"));
        detachedCriteria.addOrder(Order.desc("applyDate"));
  
        int pageSize=8;
        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<Course> courseList=(List<Course>)admin.getByPage(detachedCriteria, pageSize);
		return new ModelAndView("/admin/courseManage","courseList",courseList);}
	}
	@RequestMapping("turnToLessonManage.htm")
	public ModelAndView turnToLessonManage(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
			
		String courseId=req.getParameter("courseId");
		
		Course course=admin.findById(Course.class, courseId);
		
		return new ModelAndView("/admin/courseManage1","course",course);}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToNormalCourse.htm")
	public ModelAndView turnToNormalCourse(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Course.class);
        
        detachedCriteria.add(Restrictions.eq("courseState", "批准"));
        detachedCriteria.add(Restrictions.isNull("course"));
        detachedCriteria.addOrder(Order.desc("applyDate"));
       
        int pageSize=8;
        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<UserCourse> courseList=(List<UserCourse>)admin.getByPage(detachedCriteria, pageSize);
		return new ModelAndView("/admin/normalCourse","courseList",courseList);}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToLockCourse.htm")
	public ModelAndView turnToLockCourse(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Course.class);
       
        detachedCriteria.add(Restrictions.eq("courseState", "封禁"));
        detachedCriteria.addOrder(Order.desc("applyDate"));
        detachedCriteria.add(Restrictions.isNull("course"));
        
        int pageSize=8;
        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<UserCourse> courseList=(List<UserCourse>)admin.getByPage(detachedCriteria, pageSize);
		return new ModelAndView("/admin/lockCourse","courseList",courseList);}
	}

	@RequestMapping("alterCourseState.htm")
	public ModelAndView alterCourseState(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
	
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
		String courseId=req.getParameter("courseId");
		String type=req.getParameter("type");
		
		
		Course course=admin.findById(Course.class, courseId);
		
		if(type.equals("1")){
			course.setApproveDate(new Date());
			course.setCourseState("批准");
			admin.update(course);
			return new ModelAndView("redirect:turnToCourseManage.htm");
		}
		if(type.equals("2")){
			course.setCourseState("封禁");
			admin.update(course);
			return new ModelAndView("redirect:turnToCourseManage.htm");
		}
		if(type.equals("3")){
			course.setCourseState("封禁");
			admin.update(course);
			return new ModelAndView("redirect:turnToLessonManage.htm","courseId",course.getCourse().getCourseId());
		}
		if(type.equals("4")){
			course.setApproveDate(new Date());
			course.setCourseState("批准");
			admin.update(course);
			return new ModelAndView("redirect:turnToLessonManage.htm","courseId",course.getCourse().getCourseId());
		}
        
		return new ModelAndView("redirect:turnToLockCourse.htm");
		
		
		}
	
	
	
	}
	
	/*##################管理员管理日志##################*/
	
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToLogManage.htm")
	public ModelAndView turnToLogManage(HttpServletRequest req,
			HttpServletResponse res) {
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(OperationLog.class)
        		.addOrder(Order.desc("logDate"));
        int pageSize=8;
        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<OperationLog> logs=(List<OperationLog>) admin.getByPage(detachedCriteria, pageSize);
		return new ModelAndView("/admin/logManage","logs",logs);
	}
	/*##################管理员管理敏感词汇##################*/
	@SuppressWarnings("unchecked")
	@RequestMapping("sensitiveWorlList.htm")
	public ModelAndView sensitiveWordList(HttpServletRequest request) throws Exception{		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SensitiveWords.class);
		int pageSize = 10;
		int totalPage = admin.countTotalPage(dCriteria, pageSize);
		PageHelper.forPage(totalPage, pageSize);
		List<SensitiveWords> sensitiveWordList = (List<SensitiveWords>) admin.getByPage(dCriteria, pageSize);
		return new ModelAndView("/admin/sensitiveWManage","sensitiveWordList", sensitiveWordList);
	}
	
	@RequestMapping("addSensitiveWord.htm")
	public ModelAndView addSensitiveWord(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String sensitiveWord = ServletRequestUtils.getStringParameter(request, "sensitiveWord", "");
		String replaceWord = ServletRequestUtils.getStringParameter(request, "replaceWord", "");
		SensitiveWords sensitive = new SensitiveWords();
		sensitive.setReplaceWord(replaceWord);
		sensitive.setSensitiveWord(sensitiveWord);
		sensitive.setSensitiveWordsId(UUIDGenerator.randomUUID().toString());
		admin.save(sensitive);
		return new ModelAndView("redirect:sensitiveWorlList.htm");
	}
	
	@RequestMapping("delSensitiveWord.htm")
	public ModelAndView delSensitiveWord(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String sensitiveWordId = ServletRequestUtils.getStringParameter(request, "sensitiveWordId", "");
		SensitiveWords sensitive = admin.findById(SensitiveWords.class, sensitiveWordId);
		admin.delete(sensitive);
		return new ModelAndView("redirect:sensitiveWorlList.htm");
	}
	/*##################管理员管理报表##################*/
	@RequestMapping("turnToChartManage.htm")
	public ModelAndView turnToChartManage(HttpServletRequest request){
		
		
		
		
		return new ModelAndView();
	}
	
	
	
	
	
	
	/*##################管理员管理爬虫##################*/
	/*##################管理员管理权限##################*/
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToAuthorManage.htm")
	public ModelAndView turnToAuthorManage(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Authority.class);
        DetachedCriteria dCriteria=DetachedCriteria.forClass(Level.class);
        
        List<Authority> authorityList=(List<Authority>)admin.queryAllOfCondition(Authority.class, detachedCriteria);		
        
        int pageSize=8;
        int totalPage=admin.countTotalPage(dCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<Level> levelList=(List<Level>) admin.getByPage(dCriteria, pageSize);
        
        req.setAttribute("authorityList", authorityList);
        
		return new ModelAndView("/admin/authorManage","levelList",levelList);}
	}
	@RequestMapping("addAuthority.htm")
	public ModelAndView addAuthority(HttpServletRequest request){
		String levelId=request.getParameter("levelId");
		System.out.println("levelId=======-----"+levelId);
		
		String authorityId=request.getParameter("authorityId");
		
		System.out.println("authorityId=======-----"+authorityId);
		Level level=admin.findById(Level.class, levelId);
		Authority authority=admin.findById(Authority.class, authorityId);
		
		DetachedCriteria dCriteria=DetachedCriteria.forClass(LevelAuthority.class);
		dCriteria.add(Restrictions.eq("level", level));
		dCriteria.add(Restrictions.eq("authority", authority));
		
		List<LevelAuthority> lvAuthority=admin.queryAllOfCondition(LevelAuthority.class, dCriteria);
		System.out.println("lvAuthority.size()"+lvAuthority.size());
		if(lvAuthority.size()==0){
			LevelAuthority lev=new LevelAuthority();
			lev.setAuthority(authority);
			lev.setLevel(level);
			lev.setLevelAuthorityId(UUIDGenerator.randomUUID());
			admin.save(lev);
		}
		return new ModelAndView("redirect:turnToAuthorManage.htm");
	}
	@RequestMapping("deleteAuthority.htm")
	public ModelAndView deleteAuthority(HttpServletRequest request){
		String levelId=request.getParameter("levelId");
		Level level=admin.findById(Level.class, levelId);
		String authorityId=request.getParameter("authorityId");
		Authority authority=admin.findById(Authority.class, authorityId);
		
		DetachedCriteria dCriteria=DetachedCriteria.forClass(LevelAuthority.class);
		dCriteria.add(Restrictions.eq("level", level));
		dCriteria.add(Restrictions.eq("authority", authority));
		
		List<LevelAuthority> lvAuthority=admin.queryAllOfCondition(LevelAuthority.class, dCriteria);
		LevelAuthority lva=lvAuthority.get(0);
		admin.delete(lva);
		
		return new ModelAndView("redirect:turnToAuthorManage.htm");
	}
	
	/*##################管理员管理等级##################*/
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToLevelManage.htm")
	public ModelAndView turnToLevelManage(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Level.class);
        		
        int pageSize=8;
        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<Level> levelList=(List<Level>)admin.getByPage(detachedCriteria, pageSize);
		return new ModelAndView("/admin/levelManage","levelList",levelList);}
	}
	
	@RequestMapping("addLevel.htm")
	public ModelAndView addLevel(HttpServletRequest req,HttpServletResponse res)throws Exception {
      System.out.println("&^^^^%%%%$$$###@@");
		String lv=ServletRequestUtils.getStringParameter(req, "level", "");
       String title=ServletRequestUtils.getStringParameter(req, "title", "");
       int level1=Integer.parseInt(lv);
       String condition=ServletRequestUtils.getStringParameter(req, "condition", "");
       int condition1=Integer.parseInt(condition);
       String type=ServletRequestUtils.getStringParameter(req, "type", "");
       
      Level level=new Level();
      level.setLevelId(UUIDGenerator.randomUUID());
      level.setTitle(title);
      level.setLv(level1);
      level.setLvCondition(condition1);
      level.setType(type);
      admin.save(level);
		return new ModelAndView("redirect:turnToLevelManage.htm");
	}
	
	@RequestMapping("alterLevel.htm")
	public ModelAndView alterLevel(HttpServletRequest req,
			HttpServletResponse res) {
       String levelId=ServletRequestUtils.getStringParameter(req, "levelId", "");
       String lv1=ServletRequestUtils.getStringParameter(req, "level", "");
       int lv=Integer.parseInt(lv1);
       String condition1=ServletRequestUtils.getStringParameter(req, "condition", "");
       int condition=Integer.parseInt(condition1);
       String title=ServletRequestUtils.getStringParameter(req, "title", "");
       String type=ServletRequestUtils.getStringParameter(req, "type", "");
       
       Level level=admin.findById(Level.class, levelId);
       level.setLv(lv);
       level.setLvCondition(condition);
       level.setTitle(title);
       level.setType(type);
       
       admin.update(level);
       return new ModelAndView("redirect:turnToLevelManage.htm");
	}
	
	@RequestMapping("deletLevel.htm")
	public ModelAndView deletLevel(HttpServletRequest req,
			HttpServletResponse res) {
		 String levelId=ServletRequestUtils.getStringParameter(req, "levelId", "");
		 Level level=admin.findById(Level.class, levelId);
       admin.delete(level);
       return new ModelAndView("redirect:turnToLevelManage.htm");
	}
	/*##################管理员管理积分金币规则##################*/
	@SuppressWarnings("unchecked")
	@RequestMapping("turnToCreditAndGold.htm")
	public ModelAndView turnToCreditAndGold(HttpServletRequest req,
			HttpServletResponse res) throws Exception{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			
			return new ModelAndView("redirect:goAdminLogin.htm");
		}else{
			
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Rule.class);
        		
        int pageSize=8;
        int totalPage=admin.countTotalPage(detachedCriteria, pageSize);
        PageHelper.forPage(totalPage, pageSize);
        List<Rule> rulelist=(List<Rule>)admin.getByPage(detachedCriteria, pageSize);
		return new ModelAndView("/admin/goldAndCreditManage","rulelist",rulelist);}
	}
	
	@RequestMapping("addCreditAndGold.htm")
	public ModelAndView addCreditAndGold(HttpServletRequest req,
			HttpServletResponse res)throws Exception {
       String action=ServletRequestUtils.getStringParameter(req, "action", "");
       String credit1=ServletRequestUtils.getStringParameter(req, "credit", "");
       int credit=Integer.parseInt(credit1);
       String gold1=ServletRequestUtils.getStringParameter(req, "gold", "");
       int gold=Integer.parseInt(gold1);
       String remark=ServletRequestUtils.getStringParameter(req, "remark", "");
       
       Rule rule=new Rule();
      rule.setRuleId(UUIDGenerator.randomUUID());
      rule.setCredit(credit);
      rule.setGold(gold);
      rule.setAction(action);
      rule.setRemark(remark);
       admin.save(rule);
		return new ModelAndView("redirect:turnToCreditAndGold.htm");
	}
	
	@RequestMapping("alterCreditAndGold.htm")
	public ModelAndView alterCreditAndGold(HttpServletRequest req,
			HttpServletResponse res) {
       String ruleId=ServletRequestUtils.getStringParameter(req, "ruleId", "");
       String credit1=ServletRequestUtils.getStringParameter(req, "credit", "");
       int credit=Integer.parseInt(credit1);
       String gold1=ServletRequestUtils.getStringParameter(req, "gold", "");
       int gold=Integer.parseInt(gold1);

       Rule rule=admin.findById(Rule.class, ruleId);
       rule.setCredit(credit);
       rule.setGold(gold);
       
       admin.update(rule);
       return new ModelAndView("redirect:turnToCreditAndGold.htm");
	}
	
	@RequestMapping("deletCreditAndGold.htm")
	public ModelAndView deletCreditAndGold(HttpServletRequest req,
			HttpServletResponse res) {
		String ruleId=ServletRequestUtils.getStringParameter(req, "ruleId", "");
		 Rule rule=admin.findById(Rule.class, ruleId);
       admin.delete(rule);
       return new ModelAndView("redirect:turnToCreditAndGold.htm");
	}
	
}
