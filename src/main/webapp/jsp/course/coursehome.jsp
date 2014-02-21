<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${userCourse.course.courseTitle}-微课程</title>
<link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />

<link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link type="text/css" href="<c:url value="/css/zzsc.css"/>" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<script type="text/javascript" src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<script type="text/javascript">
$(document).ready(function(){
    var stepW = 24;
    
    var a=$("#aa").val();
   
    var stars = $("#star > li");
    $("#showb1").css({"width":stepW*a});
    $("#showb").css("width",0);
    stars.each(function(i){
        $(stars[i]).click(function(e){
            var n = i+1;
            $("#showb").css({"width":stepW*n});
            
            $(this).find('a').blur();
            
            var courseId=$("#courseId").val();
            
            $("#score").val(n);
            
           $.ajax({
            	type:"post",
            	url:"doGrade.htm",
            	data:"score="+n+"&courseId="+courseId,
            	success:function(msg){
            		if(msg=="ok"){
            			alert("评价成功！");
            		}
            		if(msg=="no"){
            			$("#showb").css("width",0);
            			alert("你已经评价过了，不能重复评价！");
            			 
            		}
            		if(msg=="go"){
            			
            			alert("请登录后再评价");
            			 
            		}
            	}
          
            });
            return stopDefault(e);
          
        });
    });
});
function stopDefault(e){
    if(e && e.preventDefault)
           e.preventDefault();
    else
           window.event.returnValue = false;
    return false;
};

</script>
<script type="text/javascript">
$("#start").live("click",function(){	
	if($("#start").html()=="开始学习"){
    var courseId=$("#courseId").val();
    //alert(courseId);
	location.href="startLearn.htm?courseId="+courseId;
	}

});

//$("#status").children("li.lili").each(function(){
	 //var a=$(this).children(".lesson-learned-status").html();
		//alert("a");
	//});	
</script>
</head>
<body class="flats-theme">


<c:if test="${empty user.userId}">
	<jsp:include page="/jsp/include/head.jsp"></jsp:include>
	</c:if>
	<c:if test="${!empty user.userId}">
		<jsp:include page="/jsp/include/head2.jsp"></jsp:include>
	</c:if>

<section class="container course">
  <div id="course-main">
    <div id="course-header" class="flat course-header course-large-header">

  <div class="imagblock clearfix">
    <div class="imageblock-image posrel">
      <img src="" alt="${userCourse.course.courseTitle}" width=190 height=140 />
          </div>
    <div class="imageblock-content">
        <div class="clearfix">
  <h1  class="pull-left">${userCourse.course.courseTitle}</h1>
    </div>

      <ul class="course-metas">
        <li>
          <c:if test="${a!='0.0'}">
			<div id="xzw_starSys">
				<div id="xzw_starBox">
					<ul class="star" id="star1">
						<li><a href="javascript:void(0)" title="很差" class="one-star"></a></li>
						<li><a href="javascript:void(0)" title="较差" class="two-stars"></a></li>
						<li><a href="javascript:void(0)" title="还行" class="three-stars"></a></li>
						<li><a href="javascript:void(0)" title="推荐" class="four-stars"></a></li>
						<li><a href="javascript:void(0)" title="力荐" class="five-stars"></a></li>
					</ul>
					<div class="current-rating" id="showb1">
						<input type="hidden" name="grade" value="" id="score1">
						<input type="hidden" name="a" value="${a}" id="aa">
					</div>
				</div>
		</div>
		<span>${a}</span>
		</c:if>
	<c:if test="${a=='0.0'}">
		<span>还没有人评分，快去评分吧</span>
	</c:if>
       </li>
        <li>
          <span>课时：</span>${lessons} 课
        </li>
        <li>
          <span>学员：</span><a href="">${students}</a> 人
        </li>
        <li>
          <span>浏览：</span>${userCourse.course.scanNum} 次
        </li>
      </ul>

      <div class="action-bar">
      <c:if test="${userCourse.user.userId ne user.userId}">
       <button class="btn btn-large btn-success" id="start">${state}</button>
       </c:if>
      </div>
     
    </div>
  </div>

  <div class="course-summary-metas mtl">
    <h2>简介</h2>
    <div class="course-summary mbl">
       ${userCourse.course.courseIntro}
    </div>
    <h2>标签</h2>
    <div class="course-tags" id="course-tags">
     <c:if test="${empty lablist}">
     <b>暂时没有标签</b>
     <c:if test="${user.userId eq userCourse.user.userId}">
          <a href="">（添加标签）</a>
     </c:if>
     </c:if>
        <c:if test="${!empty lablist}">
        <c:forEach items="${lablist}" var="lablist">
            <a href="" class="tag">${lablist.label.labelName}</a>
        </c:forEach>
        </c:if>
    </div>
  </div>


  <div  class="clearfix mvl" style="background:#fff; border-bottom:1px dashed #ddeeee;border-top:1px dashed #ddeeee; padding: 8px 8px;margin-bottom:40px;">
      <div class="fr">
               我的评价：
               
      <div id="xzw_starSys">
		<div id="xzw_starBox">
		<ul class="star" id="star">
			<li><a href="javascript:void(0)" title="很差" class="one-star"></a></li>
			<li><a href="javascript:void(0)" title="较差" class="two-stars"></a></li>
			<li><a href="javascript:void(0)" title="还行" class="three-stars"></a></li>
			<li><a href="javascript:void(0)" title="推荐" class="four-stars"></a></li>
			<li><a href="javascript:void(0)" title="力荐" class="five-stars"></a></li>
		</ul>
		<div class="current-rating" id="showb">
			<input type="hidden" name="grade" id="score">
			<input type="hidden" name="course" id="courseId" value="${userCourse.course.courseId}">
		</div>
	</div>
	</div>
               
      </div>
   </div>

<div>
<div class="clearfix mtm">
  <div class="pills" style="margin-bottom:0px">
    <a href="" class="on">课时</a>
  </div>
</div>
</div>
</div>

 <div class="flat">
 
 <div class="btn-group fr">
 <c:if test="${userCourse.user.userId==user.userId}">
  <a class="btn btn-small" href="createLessonPage.htm?courseId=${userCourse.course.courseId}">新增课时</a> 
 </c:if>
</div>

    <h2>课时 <span class="lessons-total">(共${lessons}课)</span></h2>
    <div class="lessons">
        <ul id="status">
        <c:if test="${lessons==0}">
        <b>创建者暂时还没有添加课时</b>
        <c:if test="${user.userId eq userCourse.user.userId}">
          <a href="createLessonPage.htm?courseId=${userCourse.course.courseId}">（添加课时）</a>
        </c:if>
        </c:if>
         <c:if test="${lessons>0}">
         <c:forEach items="${userCourse2}" var="uc2" varStatus="vs">
         <li class="lili">
         <span class="lesson-index">L${vs.index+1}</span>
         <span class="lesson-title"><a href="lessonPage.htm?childrenId=${uc2.course.courseId}">${uc2.course.courseTitle}</a></span>
         <span class="lesson-learned-status">
         ${uc2.learnState}
         </span>
          <div class="lesson-summary">          
           ${fn:substring(uc2.course.courseIntro,0,50)}
          </div>
         </li> 
         </c:forEach> 
         </c:if>                                          
       </ul>
        </div>
    </div>  
  </div>


  
<div id="course-side">         
    <div class="flat">
       <h3>课程创建人</h3>   
       <div class="course-author-block imageblock clearfix">
       <div class="imageblock-image"><a href="goPersonnal.htm?userId=${userCourse.user.userId}" class="show-user-card"><img src="${userCourse.user.headImage.imageMid}" alt="${userCourse.user.nickname}"></a>
       </div>
       <div class="imageblock-content">
 
   <c:if test="${userCourse.user.userId ne user.userId}">
    <c:if test="${at==0}">
    <a href="" class="btn btn-small action-ajax fr"><i class="icon-plus"></i> 关注TA</a>
    </c:if>
    <c:if test="${at==1}">
    <a href="" class="btn btn-small disabled action-ajax fr"><i class="icon-plus"></i> 已关注</a>
    </c:if>
   </c:if> 
      <div class="nickname"><a href="goPersonnal.htm?userId=${userCourse.user.userId}" class="show-user-card" title="${userCourse.user.nickname}">${userCourse.user.nickname}<span class="o-ver-icn"></span></a></div>
      <div>
        <a href="" class="stats"><em>${courseNum}</em> 课程</a>
        <a href="" class="stats">
          <em>${fansNum}</em> 粉丝</span></a>
       </div>
     </div>             
     <div class="mtm gray">${userCourse.user.signature}</div>
    </div>
   </div>
    

<div class="flat">
  <h2>最近加入的学员</h2>
      <ul class="grids smallpic-grids">
         <c:if test="${empty userCourses3}">
                              暂无用户学习此课程
         </c:if>
         <c:if test="${!empty userCourses3}">
         <c:forEach items="${userCourses3}" var="uc3">
          <li class="grid">
              <a href="goPersonnal.htm?userId=${uc3.user.userId}" class="show-user-card"><img src="${uc3.user.headImage.imageMid}" title="${uc3.user.nickname}"></a>
              <div><a href="" class="show-user-card" title="${uc3.user.nickname}">${uc3.user.nickname}</a></div>
         </li>
         </c:forEach>
         </c:if>
     </ul>
</div>  

<div class="flat">
<h2>已完成该课程的学员</h2>
      <ul class="grids smallpic-grids">
         <c:if test="${empty userCourses4}">
                             暂无用户学完此课程
         </c:if>
         <c:if test="${!empty userCourses4}">
         <c:forEach items="${userCourses4}" var="uc4">
          <li class="grid">
              <a href="" class="show-user-card"><img src="${uc4.user.nickname}" alt="${uc4.user.headImage.imageMid}"></a>
              <div><a href="" class="show-user-card" title="${uc4.user.headImage.imageMid}">${uc4.user.headImage.imageMid}</a></div>
         </li>
         </c:forEach>
         </c:if>
     </ul>
</div> 

  <div>
         
      </div>
  </div>
</section>

<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
</html>