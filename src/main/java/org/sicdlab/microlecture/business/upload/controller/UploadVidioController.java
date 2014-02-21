package org.sicdlab.microlecture.business.upload.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.upload.service.UploadService;
import org.sicdlab.microlecture.common.baseService.BaseService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.Course;
import org.sicdlab.microlecture.common.bean.Resource;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.bean.UserCourse;
import org.sicdlab.microlecture.common.bean.Video;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class UploadVidioController extends BaseServiceImpl implements BaseService {
	
	@Autowired
	private UploadService service;
	
	
	@RequestMapping("goUpload.htm")
	public ModelAndView goUpload(HttpServletRequest request){
		
		String courseId="1";
		
		request.setAttribute("courseId", courseId);
		
		return new ModelAndView("/help/upload");
	}
	@RequestMapping("goShowVideo.htm")
	public ModelAndView goShowVideo(HttpServletRequest request){
		
		String courseId="1";
		
		
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Resource.class);
		
		detachedCriteria.add(Restrictions.eq("resourceObject", courseId));
		detachedCriteria.add(Restrictions.eq("type", "video"));
		
		List<Resource> resourcelist=service.queryMaxNumOfCondition(Resource.class, detachedCriteria, 1);
		
		Resource resource=resourcelist.get(0);
		System.out.println(resource.getResourceId());
		
		DetachedCriteria dCriteria=DetachedCriteria.forClass(Video.class);
		
		dCriteria.add(Restrictions.eq("resourceId", resource.getResourceId()));
		
		List<Video> videolist=service.queryAllOfCondition(Video.class, dCriteria);
		
		System.out.println(videolist.size());
		
		Video video=videolist.get(0);
		
		request.setAttribute("video", video);
		
		return new ModelAndView("/help/showVidio");
	}
	
	
	
	@RequestMapping("uploadAll.htm")
	public ModelAndView uploadAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
	User user=(User) request.getSession().getAttribute("user");
	
	String courseIntro=null,lessonNum=null,courseTitle=null,message=null,courseId=null;
	if(ServletFileUpload.isMultipartContent(request)){
		DiskFileItemFactory disk=new DiskFileItemFactory();
		disk.setSizeThreshold(20*1024);
		disk.setRepository(disk.getRepository());
		ServletFileUpload up=new ServletFileUpload(disk);
		int maxsize=1000*1024*1024;
		List list=up.parseRequest(request);
		Iterator i=list.iterator();
		String resourceUrl = "";
		String postfix = "";
		while(i.hasNext()){
			FileItem fm=(FileItem) i.next();
			if(!fm.isFormField()){

				String filePath=fm.getName();
				String fileName="";
				int startIndex=filePath.lastIndexOf("\\");
				if(startIndex!=-1){
					fileName=filePath.substring(startIndex+1);
				}else{
					fileName=filePath;
				}
				resourceUrl = fileName;

				if(fm.getSize()>maxsize){
					message="文件太大，不超过1000M";
					break;
				}
				
				String filepath="F:\\eclipse_workplace\\Supper_Microlecture\\src\\main\\webapp\\resource\\video";
				
				//		System.out.println("filepath =====" + filepath);
				//		String filename="";
				postfix=resourceUrl.substring(resourceUrl.lastIndexOf(".")+1,resourceUrl.length());
				System.out.println("postfix======" + postfix);
				//					System.out.println("==============="+postfix);
				

				File uploadPath = new File(filepath);

				File saveFile=new File(uploadPath,fileName);
				fm.write(saveFile);
				message="文件上传成功！";
			}
			else{
				String formName=fm.getFieldName();
				String con=fm.getString("utf-8");
				if(formName.equals("courseIntro")){
					courseIntro=con;
				}
				else if(formName.equals("lessonNum")){
					lessonNum=con;
				}else if(formName.equals("courseTitle")){
					courseTitle=con;
				}else if(formName.equals("courseId")){
					courseId=con;
				}
			}

		}
		
		Course course=new Course();
		course.setCourseId(UUIDGenerator.randomUUID());
		course.setApplyDate(new Date());
		course.setCourse(service.findById(Course.class, courseId));
		course.setCourseIntro(courseIntro);
		course.setCourseState("申请中");
		course.setCourseTitle(courseTitle);
		course.setLessonNum(Integer.parseInt(lessonNum));
		course.setScanNum(0);
		service.save(course);
		
		UserCourse uC=new UserCourse();
		uC.setCourse(course);
		uC.setUserCourseId(UUIDGenerator.randomUUID());
		uC.setUserPosition("创建者");
		uC.setUser(user);
		service.save(uC);
		
		Resource resource=new Resource();
		resource.setResourceId(UUIDGenerator.randomUUID());
		resource.setResourceObject(course.getCourseId());
		resource.setType("video");
		service.save(resource);
		
		Video video=new Video();
		
		video.setResourceId(resource.getResourceId());
		video.setResource(resource);
		video.setVideoUrl(resourceUrl);
		 
		service.save(video);
		return new ModelAndView("redirect:courseDetailPage.htm?courseId="+courseId);
		
	}
	return new ModelAndView("redirect:courseDetailPage.htm?courseId="+courseId);
}
}

