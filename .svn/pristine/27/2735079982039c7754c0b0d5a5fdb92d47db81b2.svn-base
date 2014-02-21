package org.sicdlab.microlecture.business.search.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.search.service.SearchService;
import org.sicdlab.microlecture.common.bean.Discuss;
import org.sicdlab.microlecture.common.bean.DocModel;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.bean.UserCourse;
import org.sicdlab.microlecture.common.bean.UserTeam;
import org.sicdlab.microlecture.util.IndexManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Controller
public class SearchController {

	@Autowired
	private SearchService service;
	
	//创建索引
	@RequestMapping("createIndex.htm")
	public ModelAndView searchByKey(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
	    //对User,UserTeam,UserCourse,Discuss创建索引
		Directory dir=FSDirectory.open(new File("E://lucene//index4"));
		Analyzer analyzer=new IKAnalyzer();
		IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_43, analyzer);
		IndexWriter writer=new IndexWriter(dir,config);
		DetachedCriteria detachedCriteria1=DetachedCriteria.forClass(User.class)
				.add(Restrictions.eq("userState", "激活"));
		DetachedCriteria detachedCriteria2=DetachedCriteria.forClass(UserTeam.class)
				.add(Restrictions.eq("userPosition", "组长"))
				.createCriteria("team")
				.add(Restrictions.eq("teamState", "批准"));
		DetachedCriteria detachedCriteria3=DetachedCriteria.forClass(UserCourse.class)
				.add(Restrictions.eq("userPosition", "创建者"))
				.createCriteria("course")
				.add(Restrictions.eq("courseState", "批准"))
				.add(Restrictions.isNull("course"));
		DetachedCriteria detachedCriteria4=DetachedCriteria.forClass(Discuss.class);
		List<User> users=service.queryAllOfCondition(User.class,detachedCriteria1);
		List<UserTeam> userTeams=service.queryAllOfCondition(UserTeam.class,detachedCriteria2);
		List<UserCourse> userCourses=service.queryAllOfCondition(UserCourse.class,detachedCriteria3);
		List<Discuss> discusses=service.queryAllOfCondition(Discuss.class,detachedCriteria4);
		System.out.println("开始创建索引........................");
		long start=System.currentTimeMillis();
		for(User user : users){
			System.out.println("创建用户索引.......");
			Document userDoc=new Document();
			userDoc.add(new TextField("nickName", user.getNickname(), Store.YES));
			userDoc.add(new TextField("teamName", "", Store.YES));
			userDoc.add(new TextField("courseTitle", "", Store.YES));
			userDoc.add(new TextField("topic", "", Store.YES));
			userDoc.add(new StringField("userId", user.getUserId(), Store.YES));
			userDoc.add(new StringField("userTeamId", "", Store.YES));
			userDoc.add(new StringField("userCourseId", "", Store.YES));
			userDoc.add(new StringField("discussId", "", Store.YES));
			userDoc.add(new StringField("type", "用户", Store.YES));
			writer.addDocument(userDoc);
		}
		for(UserTeam userTeam:userTeams){
			System.out.println("创建小组索引.......");
			Document teamDoc=new Document();
			teamDoc.add(new TextField("nickName", userTeam.getUser().getNickname(), Store.YES));
			teamDoc.add(new TextField("teamName", userTeam.getTeam().getTeamName(), Store.YES));
			teamDoc.add(new TextField("courseTitle", "", Store.YES));
			teamDoc.add(new TextField("topic", "", Store.YES));
			teamDoc.add(new StringField("userId", userTeam.getUser().getUserId(), Store.YES));
			teamDoc.add(new StringField("userTeamId", userTeam.getUserTeamId(), Store.YES));
			teamDoc.add(new StringField("userCourseId", "", Store.YES));
			teamDoc.add(new StringField("discussId", "", Store.YES));
			teamDoc.add(new StringField("type", "小组", Store.YES));
			writer.addDocument(teamDoc);
		}
		for(UserCourse userCourse:userCourses){
			System.out.println("创建课程索引.......");
			Document courseDoc=new Document();
			courseDoc.add(new TextField("nickName", userCourse.getUser().getNickname(), Store.YES));
			courseDoc.add(new TextField("teamName", "", Store.YES));
			courseDoc.add(new TextField("courseTitle", userCourse.getCourse().getCourseTitle(), Store.YES));
			courseDoc.add(new TextField("topic", "", Store.YES));
			courseDoc.add(new StringField("userId", userCourse.getUser().getUserId(), Store.YES));
			courseDoc.add(new StringField("userTeamId", "", Store.YES));
			courseDoc.add(new StringField("userCourseId", userCourse.getUserCourseId(), Store.YES));
			courseDoc.add(new StringField("discussId", "", Store.YES));
			courseDoc.add(new StringField("type", "课程", Store.YES));
			writer.addDocument(courseDoc);
		}
		for(Discuss discuss:discusses){
			System.out.println("创建讨论索引.......");
			Document discussDoc=new Document();
			discussDoc.add(new TextField("nickName", discuss.getUser().getNickname(), Store.YES));
			discussDoc.add(new TextField("teamName", "", Store.YES));
			discussDoc.add(new TextField("courseTitle", "", Store.YES));
			discussDoc.add(new TextField("topic", discuss.getTopic(), Store.YES));
			discussDoc.add(new StringField("userId", discuss.getUser().getUserId(), Store.YES));
			discussDoc.add(new StringField("userTeamId", "", Store.YES));
			discussDoc.add(new StringField("userCourseId", "", Store.YES));
			discussDoc.add(new StringField("discussId", discuss.getDiscussId(), Store.YES));
			discussDoc.add(new StringField("type", "讨论", Store.YES));
			writer.addDocument(discussDoc);
		}
		long end=System.currentTimeMillis();
		System.out.println("创建索引完成,花费时间："+(end-start)+"毫秒");	
		writer.close();
		return new ModelAndView("");
	}
	
	// 多域查询
	@RequestMapping("MulsearchByKey.htm")
	public ModelAndView MulsearchByKey(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		List<User> users=new ArrayList<User>(); 
		List<UserTeam> userTeams=new ArrayList<UserTeam>(); 
		List<UserCourse> userCourses=new ArrayList<UserCourse>(); 
		List<Discuss> discusses=new ArrayList<Discuss>(); 
		String keyWord = ServletRequestUtils.getStringParameter(req, "keyWord", "");
		//String keyWord="月之女神";
		String[] fields={"nickName","teamName","courseTitle","topic"};
		BooleanClause.Occur[] occurs={BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD};
		String indexPath="E://lucene//index4";
		Directory dir=FSDirectory.open(new File(indexPath));
        IndexReader reader=DirectoryReader.open(dir);                
        IndexSearcher searcher=new IndexSearcher(reader);
        Query query=MultiFieldQueryParser.parse(Version.LUCENE_43, keyWord, fields, occurs,new IKAnalyzer());       
        TopDocs topDocs=searcher.search(query, reader.numDocs());
        ScoreDoc[] scoreDocs=topDocs.scoreDocs;
        System.out.println("查询结果总数----"+topDocs.totalHits+"最大的评分"+topDocs.getMaxScore());
    	for(int i=0;i<scoreDocs.length;i++){
    		int doc=scoreDocs[i].doc;
    		Document document=searcher.doc(doc);
            System.out.println("type:"+document.get("type")); 		
    		if(document.get("type").equals("用户")){
    			User user=service.findById(User.class, document.get("userId").toString());   			    			
    			users.add(user);
    		}else if(document.get("type").equals("小组")){
    			UserTeam userTeam=service.findById(UserTeam.class, document.get("userTeamId").toString());
    			userTeams.add(userTeam);
    		}else if(document.get("type").equals("课程")){
    			UserCourse userCourse=service.findById(UserCourse.class, document.get("userCourseId").toString());
    			userCourses.add(userCourse);
    		}else if(document.get("type").equals("讨论")){
    			Discuss discuss=service.findById(Discuss.class, document.get("discussId").toString());
    			discusses.add(discuss);
    		}
    	}
    	reader.close();  
    	System.out.println("users.size()"+users.size());
    	req.setAttribute("users", users);
    	req.setAttribute("userTeams",userTeams);
    	req.setAttribute("userCourses",userCourses);
    	req.setAttribute("discusses",discusses);
		return new ModelAndView("/search/results");
	}
	
	/*—————————————————————————————TEST———————————————————————————————————*/
	@RequestMapping("deleteTest.htm")
	public ModelAndView deleteTest(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		IndexManage manage=new IndexManage();
        DocModel docModel=new DocModel();
        docModel.setUserId("77a05bbca6834494b4d728376e928663");
        docModel.setType("用户");
        manage.deleteIndex(docModel);
		return new ModelAndView("");
	}
}
