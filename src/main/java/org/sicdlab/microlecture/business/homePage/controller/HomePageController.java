package org.sicdlab.microlecture.business.homePage.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.attention.service.AttentionService;
import org.sicdlab.microlecture.business.label.service.LabelService;
import org.sicdlab.microlecture.business.user.service.UserControllerService;
import org.sicdlab.microlecture.common.bean.Attention;
import org.sicdlab.microlecture.common.bean.Course;
import org.sicdlab.microlecture.common.bean.Discuss;
import org.sicdlab.microlecture.common.bean.Label;
import org.sicdlab.microlecture.common.bean.LabelObject;
import org.sicdlab.microlecture.common.bean.Level;
import org.sicdlab.microlecture.common.bean.Note;
import org.sicdlab.microlecture.common.bean.Team;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.bean.UserCourse;
import org.sicdlab.microlecture.common.bean.UserTeam;
import org.sicdlab.microlecture.common.tag.pageTag.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;






@Controller
public class HomePageController {
	
	@Autowired
	private LabelService lab;
	
	@Autowired
	private UserControllerService userService;
	@Autowired
	private AttentionService attention;
	
	private String labels;
	private String previousLabels;
	
	
	
	@RequestMapping("turnToHomePage.htm")
	public ModelAndView turnToHomePage(HttpServletRequest request){
		User user=(User) request.getSession().getAttribute("user");
		
		if(user==null){ 
			
			//热门课程
			DetachedCriteria dCriteria=DetachedCriteria.forClass(Course.class);
			dCriteria.addOrder(Order.desc("scanNum"));
			dCriteria.add(Restrictions.eq("courseState", "批准"));
			dCriteria.add(Restrictions.isNull("course"));
			List<Course> courseList=userService.queryMaxNumOfCondition(Course.class, dCriteria, 4);
			
			List<UserCourse> list=new ArrayList<UserCourse>();
			for(int i=0;i<courseList.size();i++){
				
				Course course=courseList.get(i);
				DetachedCriteria dCr=DetachedCriteria.forClass(UserCourse.class);
				
				dCr.add(Restrictions.eq("course", course));
				List<UserCourse> uL=userService.queryAllOfCondition(UserCourse.class, dCr);
				UserCourse userCourse=new UserCourse();
				for(int j=0;j<uL.size();j++){
					if(uL.get(j).getUserPosition().equals("创建者")){
						
						userCourse=uL.get(j);
					}
				}
				list.add(userCourse);
				
			}
			
			
			//最新课程
			DetachedCriteria dCriteria1=DetachedCriteria.forClass(Course.class);
			dCriteria1.addOrder(Order.desc("applyDate"));
			dCriteria1.add(Restrictions.eq("courseState", "批准"));
			dCriteria1.add(Restrictions.isNull("course"));
			List<Course> courseList1=userService.queryMaxNumOfCondition(Course.class, dCriteria1, 4);
			
			List<UserCourse> list1=new ArrayList<UserCourse>();
			for(int i=0;i<courseList1.size();i++){
				
				Course course=courseList1.get(i);
				DetachedCriteria dCr1=DetachedCriteria.forClass(UserCourse.class);
				
				dCr1.add(Restrictions.eq("course", course));
				List<UserCourse> uL1=userService.queryAllOfCondition(UserCourse.class, dCr1);
				UserCourse userCourse1=new UserCourse();
				for(int j=0;j<uL1.size();j++){
					if(uL1.get(j).getUserPosition().equals("创建者")){
						
						userCourse1=uL1.get(j);
					}
				}
				list1.add(userCourse1);
				
			}
			
			
			
			//推荐小组
			DetachedCriteria dCriteria2=DetachedCriteria.forClass(Team.class);
			dCriteria2.addOrder(Order.desc("construction"));
			dCriteria2.add(Restrictions.eq("teamState", "批准"));
			List<Team> teamlist=userService.queryMaxNumOfCondition(Team.class, dCriteria2, 4);
			
			//热门话题
			DetachedCriteria dCriteria3=DetachedCriteria.forClass(Discuss.class);
			dCriteria3.addOrder(Order.desc("scanNum"));
			List<Discuss> discusslist=userService.queryMaxNumOfCondition(Discuss.class, dCriteria3, 3);
			
			//热门标签
			DetachedCriteria dCriteria4=DetachedCriteria.forClass(Label.class);
			dCriteria4.addOrder(Order.desc("frequency"));
			List<Label> lablist=userService.queryMaxNumOfCondition(Label.class, dCriteria4, 8);
			
			//最新笔记
			DetachedCriteria dCriteria5=DetachedCriteria.forClass(Note.class);
			dCriteria5.addOrder(Order.desc("addDate"));
			dCriteria5.add(Restrictions.eq("public_", "是"));
			List<Note> notelist=userService.queryMaxNumOfCondition(Note.class, dCriteria5,3);
			
			
			
			request.setAttribute("list", list);
			request.setAttribute("list1", list1);
			request.setAttribute("teamlist", teamlist);
			request.setAttribute("discusslist", discusslist);
			request.setAttribute("lablist", lablist);
			request.setAttribute("notelist", notelist);
			return new ModelAndView("/homePage/homePage");	
		}
		
		int credit=user.getCredit();
		System.out.println("credit----"+credit);
		Level level=userService.getUserLevel(credit);
		//热门标签
		DetachedCriteria dCriteria=DetachedCriteria.forClass(Label.class);
		dCriteria.addOrder(Order.desc("frequency"));
		List<Label> lablist=userService.queryMaxNumOfCondition(Label.class, dCriteria, 8);
		//课程推荐
		
		DetachedCriteria dCriteria3=DetachedCriteria.forClass(LabelObject.class)
				.add(Restrictions.eq("objectId", user.getUserId()));
		
		List<LabelObject> labelList=userService.queryAllOfCondition(LabelObject.class, dCriteria3);
		List<UserCourse> ucList=new ArrayList<UserCourse>();
		for(int i=0;i<labelList.size();i++){
			Label label=labelList.get(i).getLabel();
			
			DetachedCriteria dCriteria4=DetachedCriteria.forClass(LabelObject.class)
					.add(Restrictions.eq("label", label))
					.add(Restrictions.eq("objectType", "course"));
			List<LabelObject> labelList1=userService.queryAllOfCondition(LabelObject.class, dCriteria4);
			
			for(int j=0;j<labelList1.size();j++){
				String objectId=labelList1.get(j).getObjectId();
				
				Course course=userService.findById(Course.class, objectId);
				
				DetachedCriteria dCriteria5=DetachedCriteria.forClass(UserCourse.class)
						.add(Restrictions.eq("course", course))
						.createCriteria("course").add(Restrictions.eq("courseState", "批准"));
						
				List<UserCourse> uslist=userService.queryAllOfCondition(UserCourse.class, dCriteria5);
				
				UserCourse userCourse=uslist.get(0);
				
				ucList.add(userCourse);
			}
			
			
		}
		
		
		
		
		
		//达人推荐
		DetachedCriteria dCriteria2=DetachedCriteria.forClass(User.class)
				.addOrder(Order.desc("credit"))
				.add(Restrictions.ne("userId",user.getUserId()));
		List<User> userList=userService.queryMaxNumOfCondition(User.class, dCriteria2, 4);
		
		request.setAttribute("ucList", ucList);
		request.setAttribute("lablist", lablist);
		request.setAttribute("userList", userList);
		return new ModelAndView("/homePage/homePageLogined","level",level);
		
	}
	@RequestMapping("turnToTuiTeam.htm")
	public ModelAndView turnToTuiTeam(HttpServletRequest request){
		User user=(User) request.getSession().getAttribute("user");
		int credit=user.getCredit();
		System.out.println("credit----"+credit);
		Level level=userService.getUserLevel(credit);
		//热门标签
		DetachedCriteria dCriteria=DetachedCriteria.forClass(Label.class);
		dCriteria.addOrder(Order.desc("frequency"));
		List<Label> lablist=userService.queryMaxNumOfCondition(Label.class, dCriteria, 8);
		
		//达人推荐
		DetachedCriteria dCriteria2=DetachedCriteria.forClass(User.class)
				.addOrder(Order.desc("credit"))
				.add(Restrictions.ne("userId",user.getUserId()));
		List<User> userList=userService.queryMaxNumOfCondition(User.class, dCriteria2, 4);
		
		//小组推荐
		
		DetachedCriteria dCriteria3=DetachedCriteria.forClass(LabelObject.class)
				.add(Restrictions.eq("objectId", user.getUserId()));
		
		List<LabelObject> labelList=userService.queryAllOfCondition(LabelObject.class, dCriteria3);
		List<UserTeam> utList=new ArrayList<UserTeam>();
		for(int i=0;i<labelList.size();i++){
			Label label=labelList.get(i).getLabel();
			
			DetachedCriteria dCriteria4=DetachedCriteria.forClass(LabelObject.class)
					.add(Restrictions.eq("label", label))
					.add(Restrictions.eq("objectType", "team"));
			List<LabelObject> labelList1=userService.queryAllOfCondition(LabelObject.class, dCriteria4);
			
			for(int j=0;j<labelList1.size();j++){
				String objectId=labelList1.get(j).getObjectId();
				
				Team team=userService.findById(Team.class, objectId);
				
				DetachedCriteria dCriteria5=DetachedCriteria.forClass(UserTeam.class)
						.add(Restrictions.eq("team", team))
				.add(Restrictions.eq("userPosition", "组长"))
				.createCriteria("team").add(Restrictions.eq("teamState", "批准"));
						
				List<UserTeam> uslist=userService.queryAllOfCondition(UserTeam.class, dCriteria5);
				
				UserTeam userteam=uslist.get(0);
				
				utList.add(userteam);
			}
			
			
		}
		for(int x=0;x<utList.size()-1;x++){
			
			for(int y=x+1;y<utList.size();y++){
				UserTeam userTeam1=utList.get(x);
				UserTeam userTeam2=utList.get(y);
				if(userTeam1.getUserTeamId().equals(userTeam2.getUserTeamId())){
					utList.remove(y);
					System.out.println("yyyyyyyyyyyyyy"+y);
					System.out.println("xxxxxxxxxxxxxxxxxxx"+utList.size());
				}
			}
			
			
		}
		
		
		
		
		request.setAttribute("utList", utList);
		request.setAttribute("lablist", lablist);
		request.setAttribute("userList", userList);
		request.setAttribute("level", level);
		return new ModelAndView("/homePage/homePageLogined1");
	}
	@RequestMapping("turnToHelpPage.htm")
	public ModelAndView turnToHelpPage(){
		return new ModelAndView("/help/helplevel");
	}
	
	
	
	@RequestMapping("howtocreate.htm")
	public ModelAndView howtocreate(){
		return new ModelAndView("/course/howtocreate");
	}
	@RequestMapping("turnToPersonPage.htm")
	public ModelAndView turnToPersonPage(){
		return new ModelAndView("/homePage/homePageLogined");
	}
	@RequestMapping("editLabel.htm")
	public ModelAndView editLabel(HttpServletRequest request){
		
		String labels=request.getParameter("keyWordsHidden");
		System.out.println("#####################labels@@@@@@@@@:"+labels);
		User user=(User) request.getSession().getAttribute("user");
		String objectId=user.getUserId();
		String objectType=request.getParameter("objectType");
		
		lab.saveObjectLabels(labels, objectId, objectType);
		
		String type=request.getParameter("type");
		
		return new ModelAndView("redirect:queryLabel.htm","type",type);
		
		
	}
	@RequestMapping("queryLabel.htm")
	public ModelAndView queryLabel(HttpServletRequest request){
		
		String type=request.getParameter("type");
		User user=(User) request.getSession().getAttribute("user");
		labels = lab.getTenHotLabels();
		previousLabels = lab.getObjectLabels(user.getUserId(), type);
		ModelMap map=new ModelMap();
		
		map.put("labels", labels);
		map.put("previousLabels", previousLabels);
		return new ModelAndView("/userPage/accountLabel",map);
	}
	@RequestMapping("goPersonnal.htm")
	public ModelAndView goPersonnal(HttpServletRequest request)throws Exception{
		
		//
		
		if((request.getSession().getAttribute("user"))==null){
			return new ModelAndView("redirect:goLoginPage.htm");
			
		}else{
			User user=(User) request.getSession().getAttribute("user");
		if((request.getParameter("userId")).equals("")){
			return new ModelAndView("redirect:goLoginPage.htm");
		}
		String userId=request.getParameter("userId");
		User user1=userService.findById(User.class, userId);
		
		DetachedCriteria dCriteria=DetachedCriteria.forClass(Attention.class);
		
		dCriteria.add(Restrictions.eq("userByUserId", user1));
		
		List<Attention> attList=userService.queryAllOfCondition(Attention.class, dCriteria);
		
		DetachedCriteria dCriteria1=DetachedCriteria.forClass(Attention.class);
		
		dCriteria1.add(Restrictions.eq("userByAttentionedUserId", user1));
		
		List<Attention> fansList=userService.queryAllOfCondition(Attention.class, dCriteria1);
		
		
		
		
		DetachedCriteria dCriteria2=DetachedCriteria.forClass(UserCourse.class)
		
		.add(Restrictions.eq("user", user1))
		
		.add(Restrictions.eq("learnState", "学习中"))
		
		.createCriteria("course").add(Restrictions.isNull("course"));
		
		List<UserCourse> courseList=userService.queryAllOfCondition(UserCourse.class, dCriteria2);
		
		
		
		
		DetachedCriteria dCriteria3=DetachedCriteria.forClass(UserCourse.class);
		
		dCriteria3.add(Restrictions.eq("learnState", "已学"));
		dCriteria3.add(Restrictions.eq("user", user1))
		.createCriteria("course").add(Restrictions.isNull("course"));
		
		List<UserCourse> courseList1=userService.queryAllOfCondition(UserCourse.class, dCriteria3);
		
		
		DetachedCriteria dCriteria4=DetachedCriteria.forClass(LabelObject.class);
		
		dCriteria4.add(Restrictions.eq("objectId", user1.getUserId()));
		
		List<LabelObject> lablist=userService.queryAllOfCondition(LabelObject.class, dCriteria4);
		
		String isOk="";
		boolean a=attention.isAlreadyAttention(user.getUserId(), user1.getUserId());
		
		if(a){
			isOk="ok";
		}else{
			isOk="no";
		}
		
		
		int credit=user1.getCredit();
		System.out.println("credit----"+credit);
		Level level=userService.getUserLevel(credit);
		request.setAttribute("isOk", isOk);
		request.setAttribute("user1", user1);
		request.setAttribute("user", user);
		request.setAttribute("level", level);
		request.setAttribute("attList", attList);
		request.setAttribute("lablist", lablist);
		request.setAttribute("fansList", fansList);
		request.setAttribute("courseList", courseList);
		request.setAttribute("courseList1", courseList1);
		
		
		return new ModelAndView("/userPage/user");}
	}
	@RequestMapping("goPersonalTeam.htm")
	public ModelAndView goPersonalTeam(HttpServletRequest request){
		String userId=request.getParameter("userId");
		User user1=userService.findById(User.class, userId);
		
		DetachedCriteria dCriteria=DetachedCriteria.forClass(UserTeam.class);
		
		dCriteria.add(Restrictions.eq("user", user1));
		
		List<UserTeam> userteam=userService.queryAllOfCondition(UserTeam.class, dCriteria);
		
		DetachedCriteria dCriteria2=DetachedCriteria.forClass(Discuss.class);
		
		dCriteria2.add(Restrictions.eq("user", user1));
		
		List<Discuss> discuss=userService.queryAllOfCondition(Discuss.class, dCriteria2);
		
		
		
		request.setAttribute("user1", user1);
		request.setAttribute("userteam", userteam);
		request.setAttribute("discuss", discuss);
		
		return new ModelAndView("/userPage/usergroup");
		
	}
	@RequestMapping("goPersonalNote.htm")
	public ModelAndView goPersonalNote(HttpServletRequest request){
		String userId=request.getParameter("userId");
		
		User user1=userService.findById(User.class, userId);
		
		User user=(User) request.getSession().getAttribute("user");
		
		
		DetachedCriteria dCriteria=DetachedCriteria.forClass(UserCourse.class);
		
		dCriteria.add(Restrictions.eq("user", user1));
		
		List<UserCourse> notelist=userService.queryAllOfCondition(UserCourse.class, dCriteria);
		
		request.setAttribute("user1", user1);
		request.setAttribute("user", user);
		
		return new ModelAndView("/userPage/usernote","notelist",notelist);
		
	}
	@RequestMapping("goPersonalFans.htm")
	public ModelAndView goPersonalFans(HttpServletRequest request){
		String userId=request.getParameter("userId");
		User user1=userService.findById(User.class, userId);
		List<Attention> list=attention.queryFans(userId);
		request.setAttribute("user1", user1);
		request.setAttribute("sum", list.size());
		return new ModelAndView("/userPage/userfans","list",list);
		
	}
	@RequestMapping("goPersonalAtt.htm")
	public ModelAndView goPersonalAtt(HttpServletRequest request){
		
		String userId=request.getParameter("userId");
		User user1=userService.findById(User.class, userId);
		List<Attention> list=attention.queryAttention(userId);
		request.setAttribute("sum", list.size());
		request.setAttribute("user1", user1);
		return new ModelAndView("/userPage/userfellow","list",list);
		
	}
	
		/*---------------------------导航栏课程首页------------------------*/
	@SuppressWarnings("unchecked")
	@RequestMapping("goCourseHome.htm")
	public ModelAndView goCourseHome(HttpServletRequest request){
		DetachedCriteria dCriteria=DetachedCriteria.forClass(UserCourse.class)
				.add(Restrictions.eq("userPosition", "创建者")).createCriteria("course")
		.add(Restrictions.eq("courseState", "批准"))
		.add(Restrictions.isNull("course"))
		.addOrder(Order.desc("applyDate"));
		
		int pageSize=12;
		int totalPage=userService.countTotalPage(dCriteria, pageSize);
		PageHelper.forPage(totalPage, pageSize);
		List<UserCourse> list1 = (List<UserCourse>) userService.getByPage(dCriteria, pageSize);
		request.setAttribute("list1", list1);
		return new ModelAndView("/homePage/coursehome");
		
	}
	
	
	
	
	
		/*---------------------------导航栏专业首页------------------------*/
	
	@RequestMapping("goMajorHome.htm")
	public ModelAndView goMajorHome(HttpServletRequest request) throws Exception{
		String type=request.getParameter("type");
		
		//
		DetachedCriteria dCriteria7=DetachedCriteria.forClass(UserCourse.class)
				.add(Restrictions.eq("userPosition", "创建者")).createCriteria("course")
		.add(Restrictions.eq("courseState", "批准"))
		.add(Restrictions.eq("type", type))
		.add(Restrictions.isNull("course"));
		
		List<UserCourse> clist=userService.queryMaxNumOfCondition(UserCourse.class, dCriteria7,1);
		
		//最新课程
		DetachedCriteria dCriteria=DetachedCriteria.forClass(Course.class);
		dCriteria.add(Restrictions.eq("courseState", "批准"));
		dCriteria.add(Restrictions.eq("type", type));
		dCriteria.add(Restrictions.isNull("course"));
		dCriteria.addOrder(Order.desc("applyDate"));
		
		
		List<Course> courseList1=userService.queryMaxNumOfCondition(Course.class, dCriteria, 4);
		
		List<UserCourse> list1=new ArrayList<UserCourse>();
		for(int i=0;i<courseList1.size();i++){
			
			Course course=courseList1.get(i);
			DetachedCriteria dCr1=DetachedCriteria.forClass(UserCourse.class);
			
			dCr1.add(Restrictions.eq("course", course));
			List<UserCourse> uL1=userService.queryAllOfCondition(UserCourse.class, dCr1);
			UserCourse userCourse1=new UserCourse();
			for(int j=0;j<uL1.size();j++){
				if(uL1.get(j).getUserPosition().equals("创建者")){
					
					userCourse1=uL1.get(j);
				}
			}
			list1.add(userCourse1);
		}
		//热门课程
		DetachedCriteria dCriteria1=DetachedCriteria.forClass(Course.class);
		dCriteria1.addOrder(Order.desc("scanNum"));
		dCriteria1.add(Restrictions.eq("courseState", "批准"));
		dCriteria1.add(Restrictions.eq("type", type));
		dCriteria1.add(Restrictions.isNull("course"));
		List<Course> courseList=userService.queryMaxNumOfCondition(Course.class, dCriteria1, 4);
		
		List<UserCourse> list=new ArrayList<UserCourse>();
		for(int i=0;i<courseList.size();i++){
			
			Course course=courseList.get(i);
			DetachedCriteria dCr=DetachedCriteria.forClass(UserCourse.class);
			
			dCr.add(Restrictions.eq("course", course));
			List<UserCourse> uL=userService.queryAllOfCondition(UserCourse.class, dCr);
			UserCourse userCourse=new UserCourse();
			for(int j=0;j<uL.size();j++){
				if(uL.get(j).getUserPosition().equals("创建者")){
					
					userCourse=uL.get(j);
				}
			}
			list.add(userCourse);
			
		}
		//小组话题
		
		DetachedCriteria dCriteria3=DetachedCriteria.forClass(Discuss.class);
		dCriteria3.addOrder(Order.desc("scanNum")).createCriteria("team").add(Restrictions.eq("type", type)).add(Restrictions.eq("teamState", "批准"));
		List<Discuss> discusslist=userService.queryMaxNumOfCondition(Discuss.class, dCriteria3, 4);
		//相关小组
		
		DetachedCriteria dCriteria4=DetachedCriteria.forClass(Team.class);
		dCriteria4.addOrder(Order.desc("construction"));
		dCriteria4.add(Restrictions.eq("teamState", "批准"));
		dCriteria4.add(Restrictions.eq("type", type));
		List<Team> teamlist=userService.queryMaxNumOfCondition(Team.class, dCriteria4, 6);
		
		//热门标签
		DetachedCriteria dCriteria5=DetachedCriteria.forClass(Label.class);
		dCriteria5.addOrder(Order.desc("frequency"));
		List<Label> lablist=userService.queryMaxNumOfCondition(Label.class, dCriteria5, 8);
		
		
		request.setAttribute("list", list);
		request.setAttribute("clist", clist);
		request.setAttribute("list1", list1);
		request.setAttribute("teamlist", teamlist);
		request.setAttribute("lablist", lablist);
		request.setAttribute("type", type);
		request.setAttribute("discusslist", discusslist);
		return new ModelAndView("/homePage/majorHome");
		
	}
}
