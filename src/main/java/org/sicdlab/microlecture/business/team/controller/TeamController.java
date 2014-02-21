package org.sicdlab.microlecture.business.team.controller;



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.authority.annotation.CheckAuthority;
import org.sicdlab.microlecture.business.label.service.LabelService;
import org.sicdlab.microlecture.business.team.service.TeamService;
import org.sicdlab.microlecture.common.bean.Attention;
import org.sicdlab.microlecture.common.bean.Comment;
import org.sicdlab.microlecture.common.bean.Course;
import org.sicdlab.microlecture.common.bean.DataDic;
import org.sicdlab.microlecture.common.bean.Discuss;
import org.sicdlab.microlecture.common.bean.Favorite;
import org.sicdlab.microlecture.common.bean.FriendliLink;
import org.sicdlab.microlecture.common.bean.HeadImage;
import org.sicdlab.microlecture.common.bean.ImageText;
import org.sicdlab.microlecture.common.bean.Inform;
import org.sicdlab.microlecture.common.bean.Label;
import org.sicdlab.microlecture.common.bean.LabelObject;
import org.sicdlab.microlecture.common.bean.Level;
import org.sicdlab.microlecture.common.bean.Resource;
import org.sicdlab.microlecture.common.bean.Team;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.bean.UserCourse;
import org.sicdlab.microlecture.common.bean.UserTeam;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class TeamController {

	@Autowired
	private TeamService teamService;
	@Autowired
	private LabelService lab;
	
	private String labels;
	private String previousLabels;
	
	@RequestMapping("teamPage.htm")
	public ModelAndView teamPage(HttpServletRequest req,HttpServletResponse res){
		User user=(User)req.getSession().getAttribute("user");
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(UserTeam.class)
				.add(Restrictions.eq("user", user))				
				.add(Restrictions.eq("userPosition", "组长"));
		DetachedCriteria criteria=detachedCriteria.createCriteria("team")
				.add(Restrictions.eq("teamState", "批准"));
		
		DetachedCriteria detachedCriteria2=DetachedCriteria.forClass(UserTeam.class)
				.add(Restrictions.eq("user", user))
				.add(Restrictions.eq("userPosition", "组员"));
		DetachedCriteria criteria2=detachedCriteria2.createCriteria("team")
				.add(Restrictions.eq("teamState", "批准"));
		//查询活跃小组
		DetachedCriteria criteria3=DetachedCriteria.forClass(Team.class)
				.add(Restrictions.eq("teamState", "批准"))
				.addOrder(Order.desc("construction"));
		//查询数据字典
		DetachedCriteria criteria4=DetachedCriteria.forClass(DataDic.class)
				.add(Restrictions.eq("dicKey", "专业分类"));
		//查询热门话题
	    DetachedCriteria criteria5=DetachedCriteria.forClass(Discuss.class)
	    		.addOrder(Order.desc("publishDate"))
	    		.addOrder(Order.desc("scanNum"));
		List<UserTeam> userTeam=(List<UserTeam>) teamService.queryAllOfCondition(UserTeam.class, criteria);
		List<UserTeam> userTeam2=(List<UserTeam>) teamService.queryAllOfCondition(UserTeam.class, criteria2);
        List<Team> teams=(List<Team>)teamService.queryAllOfCondition(Team.class, criteria3);
        List<DataDic> dics=(List<DataDic>)teamService.queryAllOfCondition(DataDic.class, criteria4);
        List<Discuss> discusses=(List<Discuss>)teamService.queryAllOfCondition(Discuss.class, criteria5);
		req.setAttribute("userTeam", userTeam);
        req.setAttribute("userTeam2", userTeam2);
        req.setAttribute("teams", teams);
        req.setAttribute("dics", dics);
        req.setAttribute("discusses", discusses);
		return new ModelAndView("/team/team");
	}
	
	@RequestMapping("teamHomePage.htm")
	public ModelAndView teamHomePage(HttpServletRequest req,HttpServletResponse res){
        String teamId=ServletRequestUtils.getStringParameter(req, "teamId", "");
        User user=(User)req.getSession().getAttribute("user");
        Team team=teamService.findById(Team.class, teamId);
        DetachedCriteria detachedCriteria=DetachedCriteria.forClass(UserTeam.class)        		
        		.add(Restrictions.eq("team", team))
        		.add(Restrictions.eq("userState", "批准"))
        		.addOrder(Order.desc("approveDate"));
        DetachedCriteria criteria=DetachedCriteria.forClass(Discuss.class)
	    		 .add(Restrictions.eq("team", team))
	    		 .addOrder(Order.desc("top"))
	    		 .addOrder(Order.desc("publishDate"));        


        List<Discuss> discusses=(List<Discuss>) teamService.queryAllOfCondition(Discuss.class, criteria);    
        List<UserTeam> userTeams=(List<UserTeam>)teamService.queryAllOfCondition(UserTeam.class, detachedCriteria);  
       
        
        UserTeam userTeam=new UserTeam();
        UserTeam userTeam2=new UserTeam();
        for(int i=0;i<userTeams.size();i++){
        	if(userTeams.get(i).getUser().getUserId().equals(user.getUserId())){
        		userTeam=userTeams.get(i);
        	}
        	if(userTeams.get(i).getUserPosition().equals("组长")){
        		userTeam2=userTeams.get(i);
        	}
        }        
        int discussNum=discusses.size(); 
        int memberNum=userTeams.size();
        DetachedCriteria  criteria2=DetachedCriteria.forClass(Level.class)
        		.add(Restrictions.eq("type", "小组用户"))
        		.addOrder(Order.asc("lvCondition"));
        List<Level> levels=(List<Level>)teamService.queryAllOfCondition(Level.class, criteria2);
        DetachedCriteria  criteria3=DetachedCriteria.forClass(Level.class)
        		.add(Restrictions.eq("type", "小组"))
        		.addOrder(Order.asc("lvCondition"));
        List<Level> levels2=(List<Level>)teamService.queryAllOfCondition(Level.class, criteria3);
        DetachedCriteria criteria4=DetachedCriteria.forClass(LabelObject.class)
        		.add(Restrictions.eq("objectId", team.getTeamId()));
        List<LabelObject> labelObjects=(List<LabelObject>)teamService.queryAllOfCondition(LabelObject.class, criteria4);
        Level level=new Level();
        Level level2=new Level();
        for(int j=0;j<levels.size();j++){
        	if(j==levels.size()-1){
        	 level=levels.get(j);
        	 break;
        	}
        	 
             if(userTeam.getContribution()!=null){
            	 
        	if(userTeam.getContribution()<levels.get(j).getLvCondition()){
        		   level=levels.get(j-1);
        	        break;
        	}
             }
        }
        for(int k=0;k<levels2.size();k++){
        	if(k==levels2.size()-1){
        	 level2=levels2.get(k);
        	 break;
        	}
        	if(team.getConstruction()<levels2.get(k).getLvCondition()){
        		   level2=levels2.get(k-1);
        	        break;
        	}
        }
        req.setAttribute("userTeams", userTeams);
        req.setAttribute("userTeam", userTeam);
        req.setAttribute("userTeam2", userTeam2);
        req.setAttribute("discussNum", discussNum);
        req.setAttribute("memberNum", memberNum);
        req.setAttribute("discusses", discusses);
        req.setAttribute("level", level);
        req.setAttribute("level2", level2);
        req.setAttribute("lablist", labelObjects);
		return new ModelAndView("/team/teamHome");
	}
		
	@RequestMapping("createGuidePage.htm")
	public ModelAndView createGuidePage(HttpServletRequest req,HttpServletResponse res){
		
		return new ModelAndView("/team/createguide");
	}
	
	@RequestMapping("createTeamPage.htm")
	public ModelAndView createTeamPage(HttpServletRequest req,HttpServletResponse res){
		DetachedCriteria criteria=DetachedCriteria.forClass(DataDic.class)
				.add(Restrictions.eq("dicKey", "专业分类"));
		List<DataDic> majorType=(List<DataDic>) teamService.queryAllOfCondition(DataDic.class, criteria);
		return new ModelAndView("/team/createpublicteam","majorType",majorType);
	}
	
	@CheckAuthority(name="创建小组")
	@RequestMapping("createTeam.htm")
	public ModelAndView createTeam(HttpServletRequest req,HttpServletResponse res){
		String teamName=ServletRequestUtils.getStringParameter(req, "teamName","");
		String teamIntro=ServletRequestUtils.getStringParameter(req, "teamIntro", "");
		String teamType=ServletRequestUtils.getStringParameter(req, "teamType", "");
		User user=(User)req.getSession().getAttribute("user");
		
		Team team=new Team();
		team.setTeamId(UUIDGenerator.randomUUID());
		team.setTeamName(teamName);
		team.setTeamIntro(teamIntro);
		team.setType(teamType);
		team.setConstruction(0);
		team.setTeamState("申请中");
		team.setApplyDate(new Date());
		teamService.save(team);
		
		UserTeam userTeam=new UserTeam();
		userTeam.setUserTeamId(UUIDGenerator.randomUUID());
		userTeam.setApplyDate(new Date());
		userTeam.setApproveDate(new Date());
		userTeam.setUser(user);
		userTeam.setUserPosition("组长");
		userTeam.setContribution(0);
		userTeam.setTeam(team);
		userTeam.setUserState("批准");
		teamService.save(userTeam);
		return new ModelAndView("redirect:teamPage.htm");
	}
	
	
	@RequestMapping("takePartInTeam.htm")
	public ModelAndView takePartInTeam(HttpServletRequest req,HttpServletResponse res){
		String teamId=ServletRequestUtils.getStringParameter(req, "teamId", "");
		User user=(User)req.getSession().getAttribute("user");
		Team team=teamService.findById(Team.class, teamId);
		UserTeam userTeam=new UserTeam();
		userTeam.setUserTeamId(UUIDGenerator.randomUUID());
		userTeam.setApplyDate(new Date());
		userTeam.setUser(user);
		userTeam.setUserState("申请中");
		userTeam.setTeam(team);
		teamService.save(userTeam);
		return new ModelAndView("redirect:teamHomePage.htm?teamId="+teamId);
	}
	
	@RequestMapping("membersAdminPage.htm")
	public ModelAndView membersAdminPage(HttpServletRequest req,HttpServletResponse res){
		String teamId=ServletRequestUtils.getStringParameter(req, "teamId", "");
		User user=(User)req.getSession().getAttribute("user");
		Team team=teamService.findById(Team.class, teamId);
		 DetachedCriteria detachedCriteria=DetachedCriteria.forClass(UserTeam.class)        		
	        		.add(Restrictions.eq("team", team))
	        		.add(Restrictions.eq("userState", "批准"))
	        		.addOrder(Order.desc("approveDate"));
	     List<UserTeam> userTeams=(List<UserTeam>)teamService.queryAllOfCondition(UserTeam.class, detachedCriteria);
	     DetachedCriteria detachedCriteria2=DetachedCriteria.forClass(UserTeam.class)        		
	        		.add(Restrictions.eq("team", team))
	        		.add(Restrictions.eq("userState", "申请中"))
	        		.addOrder(Order.desc("applyDate"));
	     List<UserTeam> userTeams2=(List<UserTeam>)teamService.queryAllOfCondition(UserTeam.class, detachedCriteria2);
	     UserTeam userTeam=new UserTeam();
	        UserTeam userTeam2=new UserTeam();
	        for(int i=0;i<userTeams.size();i++){
	        	if(userTeams.get(i).getUser().getUserId().equals(user.getUserId())){
	        		userTeam=userTeams.get(i);
	        	}
	        	if(userTeams.get(i).getUserPosition().equals("组长")){
	        		userTeam2=userTeams.get(i);
	        	}
	        }	     
	     int memberNum=userTeams.size();
	     req.setAttribute("userTeams", userTeams);
	     req.setAttribute("userTeams2", userTeams2);
	     req.setAttribute("memberNum", memberNum);
	     req.setAttribute("userTeam", userTeam);
	     req.setAttribute("userTeam2", userTeam2);
		return new ModelAndView("/team/membersAdmin");
	}
	
	@RequestMapping("kickOutTeam.htm")
	public ModelAndView kickOutTeam(HttpServletRequest req,HttpServletResponse res){		
		String userTeamId=ServletRequestUtils.getStringParameter(req, "userTeamId", "");
        UserTeam userTeam=teamService.findById(UserTeam.class,userTeamId);
        String teamId=userTeam.getTeam().getTeamId();
        teamService.delete(userTeam);
		return new ModelAndView("redirect:membersAdminPage.htm?teamId="+teamId);
	}
	
	@RequestMapping("banTeamUser.htm")
	public ModelAndView banTeamUser(HttpServletRequest req,HttpServletResponse res){		
		String userTeamId=ServletRequestUtils.getStringParameter(req, "userTeamId", "");
        UserTeam userTeam=teamService.findById(UserTeam.class,userTeamId);
        String teamId=userTeam.getTeam().getTeamId();
        userTeam.setUserState("封禁");
        teamService.update(userTeam);
		return new ModelAndView("redirect:membersAdminPage.htm?teamId="+teamId);
	}
	
	@RequestMapping("addApplyUser.htm")
	public ModelAndView addApplyUser(HttpServletRequest req,HttpServletResponse res){		
		String userTeamId=ServletRequestUtils.getStringParameter(req, "userTeamId", "");
        UserTeam userTeam=teamService.findById(UserTeam.class,userTeamId);
        String teamId=userTeam.getTeam().getTeamId();
        userTeam.setUserState("批准");
        userTeam.setApproveDate(new Date());
        userTeam.setContribution(0);
        userTeam.setUserPosition("组员");
        teamService.update(userTeam);
		return new ModelAndView("redirect:membersAdminPage.htm?teamId="+teamId);
	}
	
	@RequestMapping("manageTeam.htm")
	public ModelAndView manageTeam(HttpServletRequest req,HttpServletResponse res){		
		String teamId=ServletRequestUtils.getStringParameter(req, "teamId", "");
		User user=(User)req.getSession().getAttribute("user");
		Team team=teamService.findById(Team.class, teamId);
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(UserTeam.class)        		
	        		.add(Restrictions.eq("team", team))
	        		.add(Restrictions.eq("userState", "批准"))
	        		.addOrder(Order.desc("approveDate"));
	    List<UserTeam> userTeams=(List<UserTeam>)teamService.queryAllOfCondition(UserTeam.class, detachedCriteria);	     
	    labels = lab.getTenHotLabels();
		previousLabels = lab.getObjectLabels(team.getTeamId(), "team"); 		
	     UserTeam userTeam=new UserTeam();
	        UserTeam userTeam2=new UserTeam();
	        for(int i=0;i<userTeams.size();i++){
	        	if(userTeams.get(i).getUser().getUserId().equals(user.getUserId())){
	        		userTeam=userTeams.get(i);
	        	}
	        	if(userTeams.get(i).getUserPosition().equals("组长")){
	        		userTeam2=userTeams.get(i);
	        	}
	        }	     
	     int memberNum=userTeams.size();
	     req.setAttribute("userTeams", userTeams);
	     req.setAttribute("memberNum", memberNum);
	     req.setAttribute("userTeam", userTeam);
	     req.setAttribute("userTeam2", userTeam2);
	     req.setAttribute("labels", labels);
	     req.setAttribute("previousLabels", previousLabels);
		return new ModelAndView("/team/admin");
	}
	
	@RequestMapping("updateTeamInfo.htm")
	public ModelAndView updateTeamInfo(HttpServletRequest req,HttpServletResponse res){		
		String teamId=ServletRequestUtils.getStringParameter(req, "teamId", "");
		String teamName=ServletRequestUtils.getStringParameter(req, "teamName", "");
		String teamIntro=ServletRequestUtils.getStringParameter(req, "teamIntro", "");
		String labels=ServletRequestUtils.getStringParameter(req, "keyWordsHidden", "");
		lab.saveObjectLabels(labels, teamId, "team");		
		Team team=teamService.findById(Team.class, teamId);
		team.setTeamName(teamName);
		team.setTeamIntro(teamIntro);
		teamService.update(team);
		return new ModelAndView("redirect:manageTeam.htm?teamId="+teamId);
	}
	
	@RequestMapping("discussPage.htm")
	public ModelAndView discussPage(HttpServletRequest req,HttpServletResponse res){
		String teamId=ServletRequestUtils.getStringParameter(req, "teamId", "");
		User user=(User)req.getSession().getAttribute("user");
		Team team=teamService.findById(Team.class, teamId);
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(UserTeam.class)        		
	        		.add(Restrictions.eq("team", team))
	        		.add(Restrictions.eq("userState", "批准"))
	        		.addOrder(Order.desc("approveDate"));
	     List<UserTeam> userTeams=(List<UserTeam>)teamService.queryAllOfCondition(UserTeam.class, detachedCriteria);	   
	     UserTeam userTeam=new UserTeam();
	     UserTeam userTeam2=new UserTeam();
	        for(int i=0;i<userTeams.size();i++){
	        	if(userTeams.get(i).getUser().getUserId().equals(user.getUserId())){
	        		userTeam=userTeams.get(i);
	        	}
	        	if(userTeams.get(i).getUserPosition().equals("组长")){
	        		userTeam2=userTeams.get(i);
	        	}
	        }
	     DetachedCriteria criteria=DetachedCriteria.forClass(Discuss.class)
	    		 .add(Restrictions.eq("team", team))
	    		 .addOrder(Order.desc("top"))
	    		 .addOrder(Order.desc("publishDate"));
	     List<Discuss> discusses=(List<Discuss>) teamService.queryAllOfCondition(Discuss.class, criteria);    
	        
	     int memberNum=userTeams.size();
	     int discussNum=team.getDiscusses().size();
	     req.setAttribute("userTeams", userTeams);
	     req.setAttribute("memberNum", memberNum);
	     req.setAttribute("userTeam", userTeam);
	     req.setAttribute("userTeam2", userTeam2);
	     req.setAttribute("discussNum", discussNum);
	     req.setAttribute("discusses", discusses);
		 return new ModelAndView("/team/discuss");
	}
	
	@RequestMapping("createDiscussPage.htm")
	public ModelAndView createDiscussPage(HttpServletRequest req,HttpServletResponse res){		
		String teamId=ServletRequestUtils.getStringParameter(req, "teamId", "");
		Team team=teamService.findById(Team.class, teamId);
		req.setAttribute("team", team);
		return new ModelAndView("/team/creatediscuss");
	}
	
	@CheckAuthority(name="发表话题")
	@RequestMapping("createDiscuss.htm")
	public ModelAndView createDiscuss(HttpServletRequest req,HttpServletResponse res){		
		String teamId=ServletRequestUtils.getStringParameter(req, "teamId", "");
		String topic=ServletRequestUtils.getStringParameter(req, "topic", "");
		String content=ServletRequestUtils.getStringParameter(req, "content", "");
		User user=(User)req.getSession().getAttribute("user");
		Discuss discuss=new Discuss();
		discuss.setDiscussId(UUIDGenerator.randomUUID());
		discuss.setPublishDate(new Date());
		discuss.setTeam(teamService.findById(Team.class, teamId));
		discuss.setUser(user);
		discuss.setTopic(topic);
		discuss.setTop(0);
		discuss.setScanNum(0);
		teamService.save(discuss);
				
		Resource resource=new Resource();
		resource.setResourceId(UUIDGenerator.randomUUID());		
		resource.setResourceObject(discuss.getDiscussId());
		teamService.save(resource);
		
		ImageText imageText=new ImageText();
		imageText.setResourceId(resource.getResourceId());
		imageText.setContent(content);
		imageText.setResource(resource);
		teamService.save(imageText);
		return new ModelAndView("redirect:discussPage.htm?teamId="+teamId);
	}
	
	@RequestMapping("discussDetailPage.htm")
	public ModelAndView discussDetailPage(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		//String userId=ServletRequestUtils.getStringParameter(req, "userId", "");		
		Discuss discuss=teamService.findById(Discuss.class, discussId);		
		User user=discuss.getUser();
		User user2=(User)req.getSession().getAttribute("user");
		DetachedCriteria criteria=DetachedCriteria.forClass(Attention.class)
				.add(Restrictions.eq("userByUserId", user2))
				.add(Restrictions.eq("userByAttentionedUserId", user));
		DetachedCriteria criteria2=DetachedCriteria.forClass(Discuss.class)
				.addOrder(Order.desc("publishDate"))
				.createCriteria("team")
				.add(Restrictions.eq("teamId", discuss.getTeam().getTeamId()));				
		DetachedCriteria criteria3=DetachedCriteria.forClass(Resource.class)
				.add(Restrictions.eq("resourceObject", discuss.getDiscussId()));
		DetachedCriteria criteria4=DetachedCriteria.forClass(UserCourse.class)
				.add(Restrictions.eq("userPosition", "创建者"))
				.createCriteria("user")
				.add(Restrictions.eq("userId", user.getUserId()));
		DetachedCriteria criteria5=DetachedCriteria.forClass(UserTeam.class)
				.add(Restrictions.eq("userPosition", "组长"))
				.createCriteria("team")
				.add(Restrictions.eq("teamId", discuss.getTeam().getTeamId()));
		DetachedCriteria criteria6=DetachedCriteria.forClass(Favorite.class)
				.add(Restrictions.eq("user", user2))
                .add(Restrictions.eq("objectId", discuss.getDiscussId()));
		DetachedCriteria criteria7=DetachedCriteria.forClass(Comment.class)
				.add(Restrictions.eq("commentObject", discussId))
				.add(Restrictions.isNull("comment"))
				.addOrder(Order.asc("commentDate"));
		DetachedCriteria criteria8=DetachedCriteria.forClass(Comment.class)
				.add(Restrictions.eq("commentObject", discussId))
				.add(Restrictions.isNotNull("comment"))
				.addOrder(Order.asc("commentDate"));
		DetachedCriteria criteria9=DetachedCriteria.forClass(Attention.class)
				.add(Restrictions.eq("userByUserId", user));
		List<Attention> attentions=(List<Attention>)teamService.queryAllOfCondition(Attention.class, criteria);
		List<Discuss> discusses=(List<Discuss>)teamService.queryAllOfCondition(Discuss.class, criteria2);
		List<Resource> resources=(List<Resource>)teamService.queryAllOfCondition(Resource.class, criteria3);
		List<UserCourse> userCourses=(List<UserCourse>)teamService.queryAllOfCondition(UserCourse.class, criteria4);
		List<UserTeam> userTeams=(List<UserTeam>)teamService.queryAllOfCondition(UserTeam.class, criteria5);
		List<Favorite> favorites=(List<Favorite>)teamService.queryAllOfCondition(Favorite.class, criteria6);
		List<Comment> comments=(List<Comment>)teamService.queryAllOfCondition(Comment.class, criteria7);
		List<Comment> comments2=(List<Comment>)teamService.queryAllOfCondition(Comment.class, criteria8);
		List<Attention> attentions2=(List<Attention>)teamService.queryAllOfCondition(Attention.class, criteria9);
		Resource resource=resources.get(0);	
		UserTeam userTeam=userTeams.get(0);
		int courseNum=userCourses.size();
		int fansNum=attentions2.size();
		int at=0;
		if(!attentions.isEmpty()){
			at=1;
		}
		
		int flag=1;
		if(favorites.isEmpty()){
			flag=0;
		}
        int commentNum=comments.size()+comments2.size();
		discuss.setScanNum(discuss.getScanNum()+1);
		teamService.update(discuss);
	
		req.setAttribute("discuss", discuss);
		req.setAttribute("fansNum", fansNum);
		req.setAttribute("at", at);
		req.setAttribute("discusses", discusses);
		req.setAttribute("resource", resource);
		req.setAttribute("courseNum", courseNum);
		req.setAttribute("userTeam", userTeam);
		req.setAttribute("flag", flag);
		req.setAttribute("comments", comments);
		req.setAttribute("comments2", comments2);
		req.setAttribute("commentNum", commentNum);
		return new ModelAndView("/team/discussDetail");
	}
	
	@RequestMapping("makeEssence.htm")
	public ModelAndView makeEssence(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		Discuss discuss=teamService.findById(Discuss.class, discussId);
		discuss.setEssence("精华");
		teamService.update(discuss);
		return new ModelAndView("redirect:discussDetailPage.htm?discussId="+discussId);
	}
	
	@RequestMapping("cancelEssence.htm")
	public ModelAndView cancelEssence(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		Discuss discuss=teamService.findById(Discuss.class, discussId);
		discuss.setEssence("");
		teamService.update(discuss);
		return new ModelAndView("redirect:discussDetailPage.htm?discussId="+discussId);
	}
	
	@RequestMapping("makeTop.htm")
	public ModelAndView makeTop(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		Discuss discuss=teamService.findById(Discuss.class, discussId);
		discuss.setTop(1);
		teamService.update(discuss);
		return new ModelAndView("redirect:discussDetailPage.htm?discussId="+discussId);
	}
	
	@RequestMapping("cancelTop.htm")
	public ModelAndView cancelTop(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		Discuss discuss=teamService.findById(Discuss.class, discussId);
		discuss.setTop(0);
		teamService.update(discuss);
		return new ModelAndView("redirect:discussDetailPage.htm?discussId="+discussId);
	}
	
	@CheckAuthority(name="删除话题")
	@RequestMapping("deleteDiscuss.htm")
	public ModelAndView deleteDiscuss(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		Discuss discuss=teamService.findById(Discuss.class, discussId);
        teamService.delete(discuss);
        //删除评论
		return new ModelAndView("redirect:discussDetailPage.htm?discussId="+discussId);
	}
	
	@RequestMapping("addInform.htm")
	public ModelAndView addInform(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");		
        req.setAttribute("discussId",discussId);
		return new ModelAndView("/inform/addinform");
	}
	
	@RequestMapping("informDiscuss.htm")
	public ModelAndView informDiscuss(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		String informreason=ServletRequestUtils.getStringParameter(req, "informreason", "");
		User user=(User)req.getSession().getAttribute("user");
		Inform inform=new Inform();
		inform.setInformId(UUIDGenerator.randomUUID());
		inform.setInformDate(new Date());
		inform.setInformObject(discussId);
		inform.setUser(user);
		inform.setInformReason(informreason);
		inform.setInformType("话题");
		inform.setInformState("未处理");
		teamService.save(inform);
		return new ModelAndView("/common/outSuccess");
	}
	
	@RequestMapping("editDiscussPage.htm")
	public ModelAndView editDiscussPage(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		Discuss discuss=teamService.findById(Discuss.class, discussId);
		DetachedCriteria criteria=DetachedCriteria.forClass(ImageText.class)
				.createCriteria("resource")
				.add(Restrictions.eq("resourceObject", discussId));
		List<ImageText> texts=(List<ImageText>)teamService.queryAllOfCondition(ImageText.class, criteria);
		ImageText text=texts.get(0);
		req.setAttribute("discuss", discuss);
		req.setAttribute("text", text);
		return new ModelAndView("/team/editdiscuss");
	}
	
	@RequestMapping("updateDiscuss.htm")
	public ModelAndView updateDiscuss(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		String topic=ServletRequestUtils.getStringParameter(req, "topic", "");
		String content=ServletRequestUtils.getStringParameter(req, "content", "");
		Discuss discuss=teamService.findById(Discuss.class, discussId);
		discuss.setTopic(topic);
		teamService.update(discuss);
		DetachedCriteria criteria=DetachedCriteria.forClass(ImageText.class)
				.createCriteria("resource")
				.add(Restrictions.eq("resourceObject", discussId));
		List<ImageText> texts=(List<ImageText>)teamService.queryAllOfCondition(ImageText.class, criteria);
		ImageText text=texts.get(0);
		text.setContent(content);
		teamService.update(text);
		return new ModelAndView("/common/outSuccess");
	}
	
	@CheckAuthority(name="回复话题")
	@RequestMapping("createContent.htm")
	public ModelAndView createContent(HttpServletRequest req,HttpServletResponse res){		
		String discussId=ServletRequestUtils.getStringParameter(req, "discussId", "");
		String content=ServletRequestUtils.getStringParameter(req, "content", "");
		String parentId=ServletRequestUtils.getStringParameter(req, "parentId", "");
		User user=(User)req.getSession().getAttribute("user");
        Comment comment=new Comment();
        comment.setCommentId(UUIDGenerator.randomUUID());
        comment.setCommentDate(new Date());
        comment.setCommentContent(content);
        comment.setCommentObject(discussId);
        comment.setUser(user);
        comment.setType("话题");
        if(!parentId.equals(null)){
        	comment.setComment(teamService.findById(Comment.class, parentId));
        }
        teamService.save(comment);
		return new ModelAndView("redirect:discussDetailPage.htm?discussId="+discussId);
	}
	
	@RequestMapping("constructTeam.htm")
	public ModelAndView constructTeam(HttpServletRequest req,HttpServletResponse res){		
		String teamId=ServletRequestUtils.getStringParameter(req, "teamId", "");
		String gold=ServletRequestUtils.getStringParameter(req, "gold", "");
		User user=(User)req.getSession().getAttribute("user");
		Team team=teamService.findById(Team.class, teamId);
		team.setConstruction(team.getConstruction()+Integer.parseInt(gold));
		teamService.update(team);
		DetachedCriteria criteria=DetachedCriteria.forClass(UserTeam.class)
				.add(Restrictions.eq("team", team))
				.add(Restrictions.eq("user", user));
		List<UserTeam> userTeams=(List<UserTeam>)teamService.queryAllOfCondition(UserTeam.class, criteria);
		UserTeam userTeam=userTeams.get(0);
		userTeam.setContribution(userTeam.getContribution()+Integer.parseInt(gold));
		teamService.update(userTeam);
		user.setGold(user.getGold()-Integer.parseInt(gold));
		teamService.update(user);
		return new ModelAndView("redirect:teamHomePage.htm?teamId="+teamId);
	}
	
	
	@RequestMapping("goteampicture.htm")
	public ModelAndView goteampicture(HttpServletRequest request,HttpServletResponse response) throws Exception{		
		//teamId
		String teamId=request.getParameter("teamId");
		Team teamforpicture=new Team();
		teamforpicture=(Team) teamService.getCurrentSession().createCriteria(Team.class).add(Restrictions.eq("teamId", teamId)).uniqueResult();
		HttpSession hs=request.getSession();
		hs.setAttribute("teamforpicture", teamforpicture);
		hs.setMaxInactiveInterval(100);
		return new ModelAndView("/team/picture");
	}
	@RequestMapping("teamPicture.htm")
	public  void teamPicture(HttpServletRequest request,HttpServletResponse response) throws Exception{		
		HttpSession hs=request.getSession();
		Team team0=(Team) hs.getAttribute("teamforpicture");
		Team team=(Team) teamService.getCurrentSession().createCriteria(Team.class).add(Restrictions.eq("teamId", team0.getTeamId())).uniqueResult();
		System.out.println(team.getTeamId());
		//存两份一份供用户读取，如下
		//设置基本路径
		String uploadPath=request.getSession().getServletContext().getRealPath("/")+"";
		
		String PathToService=uploadPath.split(".metadata")[0]+"/Supper_Microlecture/src/main/webapp/pic/imagehead/";

		
		System.out.println("uploadPath"+uploadPath);
		

		
		System.out.println("uploadPath*********************"+uploadPath);
		File upPath=new File(PathToService);
		if (!upPath.exists())upPath.mkdirs();
		//创建小头像
		String jsn=request.getParameter("dataAll");
		
		System.out.println("+++++++jsn:"+jsn.substring(0, 50));
		JSONObject jsonObject=JSONObject.fromObject(jsn);
		String img1=((String)jsonObject.get("data1")).substring(22);
		img1=img1.replaceAll("_", "+");
		System.out.println("+++++++img1："+img1.substring(0, 30));
		   //byte[] b1=img1.getBytes();
	       Base64 base64=new Base64();
	       byte[] b1=base64.decode(img1);
	       for (int i = 0; i < b1.length; ++i) {
               if (b1[i] < 0) {// 调整异常数据
            	   b1[i] += 256;
               }
           }
	        String uploadPath1 = "/pic/imagehead/"+team.getTeamId()+"3.jpg";
	        System.out.println(uploadPath+uploadPath1);
			File folder1 = new File(uploadPath+uploadPath1);
			//检查文件存在与否
			if (!folder1.exists())
				folder1.createNewFile();
			File folder1S=new File(PathToService+team.getTeamId()+"3.jpg");
			 if(!folder1S.exists())folder1S.createNewFile();
		        // 创建文件输出流对象   
			    OutputStream d1 = new FileOutputStream(folder1);
		        // 写入输出流   
		        d1.write(b1);
		        d1.flush();
		        // 关闭输出流   
		        d1.close();
		        OutputStream d1S = new FileOutputStream(folder1S);
		        d1S.write(b1);
		        d1S.flush();
//		         关闭输出流   
		        d1S.close();
		        System.out.println("唯一小组头像已保存到"+uploadPath+uploadPath1);
		     
						        
		        //存头像数据
					       if(folder1.length()>=0){
					    	   HeadImage hi=new HeadImage();
					    	   if(team.getHeadImage()==null){
					    	   hi.setImageId(UUIDGenerator.randomUUID());
					    	   hi.setImageSmall(uploadPath1);
					    	   }
					    	   else{
					    		   hi=(HeadImage)teamService.getCurrentSession().createCriteria(HeadImage.class).add(Restrictions.eq("imageId",team.getHeadImage().getImageId())).uniqueResult();
					    		   
						    	   hi.setImageSmall(uploadPath1);
						    	  
					    	   }
					    	   teamService.saveOrUpdate(hi);
					    	   team.setHeadImage(hi);
					    	   teamService.update(team);
					    	   hs.setAttribute("teamforpicture", team);
					       }
		        
		        //返回后台数据
		        PrintWriter  pw = response.getWriter();  
		        
		        String math="?"+Math.random();
		        uploadPath1="<c:url value=\""+uploadPath1+"\"/>";
			       
			       String data=uploadPath1;
		          
		        System.out.println(data);  
		        pw.println(data); 
		        //hs.removeAttribute("teamId");
		
	}
}
