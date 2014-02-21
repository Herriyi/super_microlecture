package org.sicdlab.microlecture.business.course.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.authority.annotation.CheckAuthority;
import org.sicdlab.microlecture.business.course.service.CourseService;
import org.sicdlab.microlecture.business.label.service.LabelService;
import org.sicdlab.microlecture.common.bean.Attention;
import org.sicdlab.microlecture.common.bean.Comment;
import org.sicdlab.microlecture.common.bean.Course;
import org.sicdlab.microlecture.common.bean.DataDic;
import org.sicdlab.microlecture.common.bean.Grade;
import org.sicdlab.microlecture.common.bean.ImageText;
import org.sicdlab.microlecture.common.bean.LabelObject;
import org.sicdlab.microlecture.common.bean.Resource;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.bean.UserCourse;
import org.sicdlab.microlecture.common.bean.UserTeam;
import org.sicdlab.microlecture.common.bean.Video;
import org.sicdlab.microlecture.common.tag.pageTag.PageHelper;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourseController {
	@Autowired
	private CourseService service;
	@Autowired
	private LabelService lab;
	
	private String labels;
	private String previousLabels;
	
	@RequestMapping("createCoursePage.htm")
	public ModelAndView createCoursePage(HttpServletRequest req,HttpServletResponse res){		
		DetachedCriteria criteria=DetachedCriteria.forClass(DataDic.class)
				.add(Restrictions.eq("dicKey", "专业分类"));
		List<DataDic> dataDics=(List<DataDic>)service.queryAllOfCondition(DataDic.class, criteria);
//		labels = lab.getTenHotLabels();		
		req.setAttribute("dataDics", dataDics);
//		req.setAttribute("labels", labels);
		return new ModelAndView("/course/coursecreate");
	}
	
	@RequestMapping("createCourse.htm")
	public ModelAndView createCourse(HttpServletRequest req,HttpServletResponse res){	
		User user=(User)req.getSession().getAttribute("user");		
		String courseTitle=ServletRequestUtils.getStringParameter(req, "courseTitle","");
		String courseIntro=ServletRequestUtils.getStringParameter(req, "courseIntro","");
	    String type=ServletRequestUtils.getStringParameter(req, "type","");	   	    
	    Course course=new Course();
	    course.setCourseId(UUIDGenerator.randomUUID());
	    course.setApplyDate(new Date());
	    course.setCourseIntro(courseIntro);
	    course.setCourseState("申请中");
	    course.setType(type);
	    course.setScanNum(0);
	    course.setCourseTitle(courseTitle);
	    service.save(course);	  
	    UserCourse userCourse=new UserCourse();
	    userCourse.setUserCourseId(UUIDGenerator.randomUUID());
	    userCourse.setUserPosition("创建者");
	    userCourse.setCourse(course);
	    userCourse.setUser(user);
	    service.save(userCourse);
	    
		return new ModelAndView("redirect:courseList.htm");
	}
	
	@RequestMapping("createLessonPage.htm")
	public ModelAndView createLessonPage(HttpServletRequest req,HttpServletResponse res){		
		String courseId=ServletRequestUtils.getStringParameter(req, "courseId","");
		Course course=service.findById(Course.class, courseId);
		DetachedCriteria criteria=DetachedCriteria.forClass(UserCourse.class)
				.createCriteria("course")
				.add(Restrictions.eq("course", course));
		List<UserCourse> userCourses=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria);
		int lessons=userCourses.size();
		req.setAttribute("course", course);
		req.setAttribute("lessons", lessons);	
		return new ModelAndView("/course/createlesson");
	}
	
	@RequestMapping("createLesson.htm")
	public ModelAndView createLesson(HttpServletRequest req,HttpServletResponse res){	
		User user=(User)req.getSession().getAttribute("user");	
		String courseId=ServletRequestUtils.getStringParameter(req, "courseId","");
		String courseTitle=ServletRequestUtils.getStringParameter(req, "courseTitle","");
		String courseIntro=ServletRequestUtils.getStringParameter(req, "courseIntro","");	   		   
	    String content=ServletRequestUtils.getStringParameter(req, "content","");
	    String lessonNum=ServletRequestUtils.getStringParameter(req, "lessonNum","");
	    Course course=new Course();
	    course.setCourseId(UUIDGenerator.randomUUID());
	    course.setApplyDate(new Date());
	    course.setCourseIntro(courseIntro);
	    course.setCourseState("申请中");	    
	    course.setScanNum(0);
	    course.setCourseTitle(courseTitle);
	    course.setLessonNum(Integer.parseInt(lessonNum));
	    course.setCourse(service.findById(Course.class, courseId));
	    service.save(course);

	    UserCourse userCourse=new UserCourse();
	    userCourse.setUserCourseId(UUIDGenerator.randomUUID());
	    userCourse.setUserPosition("创建者");
	    userCourse.setCourse(course);
	    userCourse.setUser(user);
	    service.save(userCourse);
	    
	    Resource resource=new Resource();
		resource.setResourceId(UUIDGenerator.randomUUID());	
		resource.setResourceObject(course.getCourseId());
		service.save(resource);
	  
	    ImageText imageText=new ImageText();
		imageText.setResourceId(resource.getResourceId());
		imageText.setContent(content);
		imageText.setResource(resource);
		service.save(imageText);
	    
	  		 
		return new ModelAndView("redirect:courseList.htm");
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("courseList.htm")
	public ModelAndView courseList(HttpServletRequest req,HttpServletResponse res){	
		User user=(User)req.getSession().getAttribute("user");				
	    DetachedCriteria criteria=DetachedCriteria.forClass(UserCourse.class)
	    		.add(Restrictions.eq("user",user))
                .add(Restrictions.eq("userPosition", "创建者"))
                .createCriteria("course")
                .add(Restrictions.isNull("course"));
	    int pageSize=5;
		int totalPage=service.countTotalPage(criteria, pageSize);
		PageHelper.forPage(totalPage, pageSize);	
	    List<UserCourse> userCourses=(List<UserCourse>)service.getByPage(criteria, pageSize);
		req.setAttribute("userCourses", userCourses);
	    return new ModelAndView("/course/myCourse");
	}
	
	@RequestMapping("courseDetailPage.htm")
	public ModelAndView courseDetailPage(HttpServletRequest req,HttpServletResponse res){	
		String courseId=ServletRequestUtils.getStringParameter(req, "courseId", "");	
	    Course course=service.findById(Course.class, courseId);	    
		User user=(User)req.getSession().getAttribute("user");
         
		//查询该课程的用户课程信息（能得到创建者信息）
		DetachedCriteria criteria=DetachedCriteria.forClass(UserCourse.class)
				.add(Restrictions.eq("userPosition","创建者"))
				.add(Restrictions.eq("course", course));
		//查询该课程的所有课时
//		DetachedCriteria criteria2=DetachedCriteria.forClass(UserCourse.class)
//				.add(Restrictions.eq("user", user))
//				.createCriteria("course")
//				.add(Restrictions.eq("course", course))				
//				.addOrder(Order.asc("applyDate"));
		        
		//按开始学习时间降序排列查询该课程用户学习状态为"学习中"的用户课程信息
		DetachedCriteria criteria3=DetachedCriteria.forClass(UserCourse.class)
				.add(Restrictions.eq("userPosition","学员"))
				.add(Restrictions.eq("course", course))	
				.add(Restrictions.eq("learnState", "学习中"))
				.addOrder(Order.desc("startDate"));
		//按开始学习时间降序排列查询该课程用户学习状态为"已学"的用户课程信息
		DetachedCriteria criteria4=DetachedCriteria.forClass(UserCourse.class)
				.add(Restrictions.eq("userPosition","学员"))
				.add(Restrictions.eq("course", course))
				.add(Restrictions.eq("learnState", "已学"))
				.addOrder(Order.desc("startDate"));  
		List<UserCourse> userCourses=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria);
		UserCourse userCourse=userCourses.get(0);
		//查询该课程创建者所有的用户课程信息
		DetachedCriteria criteria5=DetachedCriteria.forClass(UserCourse.class)
				.add(Restrictions.eq("userPosition","创建者"))
				.add(Restrictions.eq("user", userCourse.getUser()));
		
		//查询关注者为登录用户，被关注着为课程创建者的关注信息
		DetachedCriteria criteria6=DetachedCriteria.forClass(Attention.class)
				.add(Restrictions.eq("userByUserId", user))
				.add(Restrictions.eq("userByAttentionedUserId", userCourse.getUser()));
		
		//查询课程创建者的关注信息
		DetachedCriteria criteria7=DetachedCriteria.forClass(Attention.class)
				.add(Restrictions.eq("userByAttentionedUserId", userCourse.getUser()));
	    DetachedCriteria criteria8=DetachedCriteria.forClass(LabelObject.class)
	        		.add(Restrictions.eq("objectId", userCourse.getCourse().getCourseId()));		    	    
	    DetachedCriteria dCriteria9=DetachedCriteria.forClass(Grade.class);
		
	    dCriteria9.add(Restrictions.eq("gradeObject", courseId));
	    //查询课程的学习状态
	    DetachedCriteria criteria9=DetachedCriteria.forClass(UserCourse.class)
	    		.add(Restrictions.eq("user", user))
	    		.add(Restrictions.eq("course", course))
	    		.add(Restrictions.eq("userPosition", "学员"));
	    String state="开始学习";
	    List<UserCourse> courses=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria9);
	    if(!courses.isEmpty()){
	    	state=courses.get(0).getLearnState();
	    }
	    //查询该课程对应所有的课时及用户学习状态
	    DetachedCriteria criteria10=DetachedCriteria.forClass(Course.class)
	    		.add(Restrictions.eq("course", course))
	    		.addOrder(Order.asc("applyDate"));
	    List<Course> courses2=(List<Course>)service.queryAllOfCondition(Course.class, criteria10);
	    List<UserCourse> userCourse2=new ArrayList<UserCourse>();
	    String status="未学";
	    
	    for(int i=0;i<courses2.size();i++){
	    	UserCourse userCourse3=new UserCourse();
	    	DetachedCriteria criteria11=DetachedCriteria.forClass(UserCourse.class)
	    			.add(Restrictions.eq("user", user))
	    			.add(Restrictions.eq("course", courses2.get(i)))
	    			.add(Restrictions.eq("userPosition", "学员"));
	    	List<UserCourse> courses3=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria11);
	    	if(!courses3.isEmpty()){
	    		status=courses3.get(0).getLearnState();

	      }
	    	
	    	userCourse3.setCourse(courses2.get(i));
	    	userCourse3.setLearnState(status);
	    	userCourse2.add(userCourse3);
	    }
	    
		//查询关注者为未登录用户，被关注着为课程创建者的关注信息
		DetachedCriteria criteria2=DetachedCriteria.forClass(Attention.class)
				.add(Restrictions.eq("userByAttentionedUserId", userCourse.getUser()));
		
		List<Grade> list=service.queryAllOfCondition(Grade.class, dCriteria9);
		double a=0;
		if(list.size()!=0){
			a=service.queryGrade(courseId);	
		}else{
			 a=0;
		}
		
		System.out.println(a);
		
		req.setAttribute("a", a);
	    List<LabelObject> labelObjects=(List<LabelObject>)service.queryAllOfCondition(LabelObject.class, criteria8);
		
		List<UserCourse> userCourses3=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria3);
		List<UserCourse> userCourses4=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria4);
		List<UserCourse> userCourses5=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria5);
		List<Attention> attentions=(List<Attention>)service.queryAllOfCondition(Attention.class, criteria6);
		List<Attention> attentions2=(List<Attention>)service.queryAllOfCondition(Attention.class, criteria7);
		int fansNum=attentions2.size();
	
		int courseNum=userCourses5.size();
		int students=userCourses3.size()+userCourses4.size();
		int at=0;
		if(!attentions.isEmpty()){
			at=1;
		}
		int lessons=userCourse2.size();
		course.setScanNum(course.getScanNum()+1);
		service.update(course);
		req.setAttribute("userCourse", userCourse);
		req.setAttribute("fansNum", fansNum);
		
		req.setAttribute("students", students);
		req.setAttribute("courseNum", courseNum);
		req.setAttribute("at", at);
		//req.setAttribute("userCourses2", userCourses2);
		req.setAttribute("userCourses3", userCourses3);
		req.setAttribute("userCourses4", userCourses4);
		req.setAttribute("user", user);
		req.setAttribute("lessons", lessons);
		req.setAttribute("lablist", labelObjects);
		req.setAttribute("state", state);
		req.setAttribute("userCourse2", userCourse2);
		return new ModelAndView("/course/coursehome");
	}
	
	@RequestMapping("manageCoursePage.htm")
	public ModelAndView manageCoursePage(HttpServletRequest req,HttpServletResponse res){		
		String courseId=ServletRequestUtils.getStringParameter(req, "courseId", "");
        Course course=service.findById(Course.class, courseId);
		DetachedCriteria criteria=DetachedCriteria.forClass(DataDic.class)
				.add(Restrictions.eq("dicKey", "专业分类"));
		List<DataDic> dataDics=(List<DataDic>)service.queryAllOfCondition(DataDic.class, criteria);
        req.setAttribute("course", course);
        req.setAttribute("dataDics", dataDics);
		return new ModelAndView("/course/manageCourse");
	}
	
	@RequestMapping("manageCourse.htm")
	public ModelAndView manageCourse(HttpServletRequest req,HttpServletResponse res){		
		String courseTitle=ServletRequestUtils.getStringParameter(req, "courseTitle", "");
		String courseIntro=ServletRequestUtils.getStringParameter(req, "courseIntro", "");
		String type=ServletRequestUtils.getStringParameter(req, "type", "");
		String courseId=ServletRequestUtils.getStringParameter(req, "courseId", "");   
		Course course=service.findById(Course.class, courseId);
		course.setCourseTitle(courseTitle);
		course.setCourseIntro(courseIntro);
		course.setType(type);
		service.update(course);
		return new ModelAndView("/common/outSuccess");
	}
	
	
	@RequestMapping("lessonPage.htm")
	public ModelAndView lessonPage(HttpServletRequest req,HttpServletResponse res){		
        String childrenId=ServletRequestUtils.getStringParameter(req, "childrenId", "");
        User user=(User)req.getSession().getAttribute("user");
        Course course=service.findById(Course.class, childrenId);
        //查询该课时对应课程的用户课程信息
        DetachedCriteria criteria=DetachedCriteria.forClass(UserCourse.class)
        		.add(Restrictions.eq("course", course.getCourse()))
        		.add(Restrictions.eq("userPosition", "创建者"));
        List<UserCourse> userCourses=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria);
        UserCourse userCourse=userCourses.get(0);
          //按开始学习时间降序排列查询该课程用户学习状态为"学习中"的用户课程信息
      		DetachedCriteria criteria2=DetachedCriteria.forClass(UserCourse.class)
      				.add(Restrictions.eq("userPosition","学员"))
      				.add(Restrictions.eq("course", course.getCourse()))	
      				.add(Restrictions.eq("learnState", "学习中"))
      				.addOrder(Order.desc("startDate"));
            //按开始学习时间降序排列查询该课程用户学习状态为"已学"的用户课程信息
    		DetachedCriteria criteria3=DetachedCriteria.forClass(UserCourse.class)
    				.add(Restrictions.eq("userPosition","学员"))
    				.add(Restrictions.eq("course", course.getCourse()))
    				.add(Restrictions.eq("learnState", "已学"))
    				.addOrder(Order.desc("startDate")); 
           //查询关注者为登录用户，被关注着为课程创建者的关注信息
    		DetachedCriteria criteria4=DetachedCriteria.forClass(Attention.class)
    				.add(Restrictions.eq("userByUserId", user))
    				.add(Restrictions.eq("userByAttentionedUserId", userCourse.getUser()));	
    		//查询被关注着为课程创建者的关注信息
    		DetachedCriteria criteria5=DetachedCriteria.forClass(Attention.class)
    				.add(Restrictions.eq("userByAttentionedUserId", userCourse.getUser()));
    		//评论信息
    		DetachedCriteria criteria6=DetachedCriteria.forClass(Comment.class)
    				.add(Restrictions.eq("commentObject", childrenId))
    				.add(Restrictions.isNull("comment"))
    				.addOrder(Order.asc("commentDate"));
    		DetachedCriteria criteria7=DetachedCriteria.forClass(Comment.class)
    				.add(Restrictions.eq("commentObject", childrenId))
    				.add(Restrictions.isNotNull("comment"))
    				.addOrder(Order.asc("commentDate"));
    		//课时资源信息
    		DetachedCriteria criteria8=DetachedCriteria.forClass(Resource.class)
    				.add(Restrictions.eq("resourceObject", childrenId));
    		//课程对应的课时列表
    		DetachedCriteria criteria9=DetachedCriteria.forClass(Course.class)
    				.add(Restrictions.eq("course", userCourse.getCourse()))
    				.addOrder(Order.asc("lessonNum"));
    		 DetachedCriteria criteria0=DetachedCriteria.forClass(UserCourse.class)
    		    		.add(Restrictions.eq("user", user))
    		    		.add(Restrictions.eq("course", course))
    		    		.add(Restrictions.eq("userPosition", "学员"));
    		    String state="开始学习";
    		    List<UserCourse> courses=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria0);
    		    if(!courses.isEmpty()){
    		    	state=courses.get(0).getLearnState();
    		    }
    		List<UserCourse> userCourses2=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria2);
    		List<UserCourse> userCourses3=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria3);
    		List<Attention> attentions=(List<Attention>)service.queryAllOfCondition(Attention.class, criteria4);
    		List<Attention> attentions2=(List<Attention>)service.queryAllOfCondition(Attention.class, criteria5);
    		List<Comment> comments=(List<Comment>)service.queryAllOfCondition(Comment.class, criteria6);
    		List<Comment> comments2=(List<Comment>)service.queryAllOfCondition(Comment.class, criteria7);
    		List<Resource> resources=(List<Resource>)service.queryAllOfCondition(Resource.class, criteria8);    		
    	    List<Course> lessons=(List<Course>)service.queryAllOfCondition(Course.class, criteria9);
    		Resource resource=resources.get(0);
    		int fansNum=attentions2.size();
    		int students=userCourses2.size()+userCourses3.size();
    		int at=0;
    		if(!attentions.isEmpty()){
    			at=1;
    		}
    		course.setScanNum(course.getScanNum()+1);
    		req.setAttribute("course", course);
    		req.setAttribute("userCourse", userCourse);
    		req.setAttribute("userCourses2", userCourses2);
    		req.setAttribute("userCourses3", userCourses3);
    		req.setAttribute("fansNum", fansNum);
    		req.setAttribute("students", students);
    		req.setAttribute("at", at);
    		req.setAttribute("leaning", userCourses2.size());
    		req.setAttribute("leaned", userCourses3.size());
    		req.setAttribute("user", user);
    		req.setAttribute("comments", comments);
    		req.setAttribute("comments2", comments2);
    		req.setAttribute("commentNum", comments.size()+comments2.size());
    		req.setAttribute("resource", resource);
    		req.setAttribute("lessons", lessons);
    		req.setAttribute("lessonNum", lessons.size());
    		req.setAttribute("state", state);
		    return new ModelAndView("/course/lesson");
	}

	@CheckAuthority(name="回复话题")
	@RequestMapping("createReply.htm")
	public ModelAndView createReply(HttpServletRequest req,HttpServletResponse res){		
		String courseTimeId=ServletRequestUtils.getStringParameter(req, "courseTimeId", "");
		String content=ServletRequestUtils.getStringParameter(req, "content", "");
		String parentId=ServletRequestUtils.getStringParameter(req, "parentId", "");
		User user=(User)req.getSession().getAttribute("user");
        Comment comment=new Comment();
        comment.setCommentId(UUIDGenerator.randomUUID());
        comment.setCommentDate(new Date());
        comment.setCommentContent(content);
        comment.setCommentObject(courseTimeId);
        comment.setUser(user);
        comment.setType("课时");
        if(!parentId.equals(null)){
        	comment.setComment(service.findById(Comment.class, parentId));
        }
        service.save(comment);
		return new ModelAndView("redirect:lessonPage.htm?childrenId="+courseTimeId);
	}
	
	@RequestMapping("startLearn.htm")
	public ModelAndView startLearn(HttpServletRequest req,HttpServletResponse res){		
		String courseId=ServletRequestUtils.getStringParameter(req, "courseId", "");
		User user=(User)req.getSession().getAttribute("user");
		Course course=service.findById(Course.class, courseId);
		UserCourse userCourse=new UserCourse();
		userCourse.setCourse(course);
		userCourse.setLearnState("学习中");
		userCourse.setStartDate(new Date());
		userCourse.setUser(user);
		userCourse.setUserCourseId(UUIDGenerator.randomUUID());
		userCourse.setUserPosition("学员");
		service.save(userCourse);
		return new ModelAndView("redirect:courseDetailPage.htm?courseId="+courseId);
	}
	
	@RequestMapping("startStudy.htm")
	public ModelAndView startStudy(HttpServletRequest req,HttpServletResponse res){		
		String courseId=ServletRequestUtils.getStringParameter(req, "courseId", "");
		User user=(User)req.getSession().getAttribute("user");
		Course course=service.findById(Course.class, courseId);
		UserCourse userCourse=new UserCourse();
		userCourse.setCourse(course);
		userCourse.setLearnState("学习中");
		userCourse.setStartDate(new Date());
		userCourse.setUser(user);
		userCourse.setUserCourseId(UUIDGenerator.randomUUID());
		userCourse.setUserPosition("学员");
		service.save(userCourse);
		return new ModelAndView("redirect:lessonPage.htm?childrenId="+courseId);
	}
	
	@RequestMapping("haveLeaned.htm")
	public ModelAndView haveLeaned(HttpServletRequest req,HttpServletResponse res){		
		String courseId=ServletRequestUtils.getStringParameter(req, "courseId", "");
		User user=(User)req.getSession().getAttribute("user");
		Course course=service.findById(Course.class, courseId);
		DetachedCriteria criteria=DetachedCriteria.forClass(UserCourse.class)
				.add(Restrictions.eq("user", user))
				.add(Restrictions.eq("course", course))
				.add(Restrictions.eq("userPosition", "学员"));
		List<UserCourse> courses=(List<UserCourse>)service.queryAllOfCondition(UserCourse.class, criteria);
		UserCourse userCourse=courses.get(0);
		userCourse.setLearnState("已学");
		service.update(userCourse);
		return new ModelAndView("redirect:lessonPage.htm?childrenId="+courseId);
	}
}


