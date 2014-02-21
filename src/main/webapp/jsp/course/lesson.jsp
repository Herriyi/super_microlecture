<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sicd" uri="/sicd-tags"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${userCourse.course.courseTitle}-微课程</title>
<link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />
<link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">

<script type="text/javascript" src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<link rel="stylesheet" href="<c:url value="/css/bdsstyle.css"/>">
<script type="text/javascript" src="<c:url value="/js/xheditor/xheditor-1.1.6-zh-cn.js"/>"></script> 

<script type="text/javascript">
	$(function(){

		$("#note-open-btn").click(function(){

			$("#note-popup-composer").css("visibility","visible")

			});

		$("#chacha").click(function(){

			$("#note-popup-composer").css("visibility","hidden")

			});

		$(".btn.btn-link").click(function(){
			var a=$(this).html();
			//alert(a);
		if(a=="回复"){
			//alert("aaa");
	       $(this).parent().parent().children(".st").children(".frame").css("display","block");
	       $(this).html("收起");
			}else if(a=="收起"){
				$(this).parent().parent().children(".st").children(".frame").css("display","none");
				$(this).html("回复");
				}
			});
		$("#save").click(function(){
	          $("#form1").submit();
			});

		$(".btn.btn-success").live("click",function(){
			//alert("aa");
		     $(this).parent().parent().children(".form1.reply-form").submit();

				});
        $("#note-submit-btn").click(function(e){
            e.preventDefault();

            var pub=$("#pub").val();
            var uC=$("#courseTimeId").val();
            var con=$("#con").val();
              $.ajax({
              	type:"post",
              	url:"goCreateNote.htm",
              	data:"courseId="+uC+"&content="+con+"&isPublic="+pub,
              	success:function(msg){
              		if(msg=="ok"){
              			alert("保存成功！");
              		}
              	}
            
              });
            });


		
       var a=$("#index").html();		
		$("#lesson").children("li").each(function(){
              var b=$(this).children("span.lesson-index").html();
           if(a==b){
        	   $(this).addClass("current");
               }
			});




		$("#follow-user").click(function(){
			var userId1=$("#userId1").val();
			var userId=$("#userId").val();
			alert(userId);
			if(userId1==""){
					alert("请先登录");
					return false;
				}else{
			 $.ajax({
	           	type:"post",
	           	url:"addAttention.htm",
	           	data:"userBid="+userId,
	           	success:function(msg){
	           		if(msg=="ok"){
	           			$("#follow-user").css("display","none");
	       				$("#unfollow-user").css("display","inline-block");
	           		}
	           		if(msg=="no"){
	           		}
	           		
	           	}
	         
	           });
				}


			});
		$("#unfollow-user").click(function(){
			var userId=$("#userId").val();
			$.ajax({
	       	type:"post",
	       	url:"delAttention.htm",
	       	data:"userBid="+userId,
	       	success:function(msg){
	       		if(msg=="ok"){
	       			$("#follow-user").css("display","inline-block");
	   				$("#unfollow-user").css("display","none");
	       		}
	       		if(msg=="no"){
	       		}
	       		
	       	}
	     
	       });

		});


		});
	
</script>
<script type="text/javascript">
function test(){
	var courseId=document.getElementById("courseTimeId").value;
	//alert(document.getElementById("start").innerHTML);
	var a=document.getElementById("start").innerHTML;
	if(a=="开始学习"){
		window.location.href="startStudy.htm?courseId="+courseId;
		}
	
}

</script>


</head>
<body class="flats-theme">
<c:if test="${empty user.userId}">
	<jsp:include page="/jsp/include/head.jsp"></jsp:include>
	</c:if>
	<c:if test="${!empty user.userId}">
		<jsp:include page="/jsp/include/head2.jsp"></jsp:include>
	</c:if>
	
	<input type="hidden" id="userId1" value="${user.userId}">
	<input type="hidden" id="userId" value="${userCourse.user.userId}">
	<section class="container course">
		
		<div id="course-main">
			<div id="course-header"
				class="flat course-header course-small-header">
				<div class="imageblock clearfix">
					<div class="imageblock-image">
						<a href=""><img src=""width="48" heiht="48" alt="${userCourse.course.courseTitle}"></a>
					</div>
					<div class="imageblock-content">

						<h1 class="mbm">
							<a href="">${userCourse.course.courseTitle}</a>
						</h1>

						<div style="margin-left: -5px;">
							<div class="clearfix mtm">

								<div class="pills" style="margin-bottom: 0px">									
									<a href="">学员(${students})</a>
								</div>
							</div>
							     <div class="action-bar">
							     <c:if test="${userCourse.user.userId ne user.userId}">
                               <button class="btn btn-large btn-success" id="start" onclick="test()">${state}</button>
                                </c:if>
                                </div>
						</div>

					</div>
				</div>
			</div>

			<div id="lessoncontent" class="flat lesson-flat">

				<h1>
					<span class="lesson-index" id="index">L${course.lessonNum}</span>
					<span class="lesson-title">${course.courseTitle}</span>
				</h1>

				<div class="lesson-toolbar  mbm">
					<div class="lesson-toolbar-inner">
						<div class="lesson-toolbar-main">
							<div class="lesson-toolbar-main-inner clearfix">

								<div class="btn-group fr posrel">	
								<c:if test="${state=='学习中'}">				
									<button id="note-open-btn" class="btn btn-small" title="写笔记"
										data-open-url="/course/4631/lesson/49296?openNote=1">
										<i class="icon-pencil"></i> 写笔记
									</button>
							    </c:if>	
									<span class="float-new posabs" style="top: -12px; right: 0;"></span>
								</div>



							</div>
						</div>
					</div>
				</div>
				<div class="editor-content">
				  <c:if test="${!empty resource.imageText}">
                     ${resource.imageText.content}
                  </c:if>
                  <c:if test="${!empty resource.link}">
                     ${resource.link.linkUrl}"
                  </c:if>
                  <c:if test="${!empty resource.video}">
                 
                  <div class="media-player">
                   <video controls="controls" poster="<c:url value='/pic/logo2.jpg'/>"> 
	                 <source src="<c:url value='/resource/video/${resource.video.videoUrl}'/>" type="video/mp4" /> 
                   </video>
                  </div>              
                  </c:if>
					
						
			
                    	            
			<div class="btn-toolbar mbl clearfix" id="lesson-user-actions">
				<span class="pull-right">
			
				<c:if test="${state=='学习中'}">
				<a href="haveLeaned.htm?courseId=${course.courseId}" id="set-learned-btn" class="btn btn-success">
				<i class="glyphicon-ok"></i>学过了</a> 
				</c:if>
				<c:if test="${state=='已学'}">
				<a href="" id="cancel-learned-btn" class="btn btn-success disabled">
				<i class="glyphicon-ok"></i> 已学</a>
				</c:if>
				</span>
			</div>	
				</div>
			</div>
                   
     <c:if test="${commentNum eq 0}">
       <h3>暂时还没有评论</h3>
    </c:if>   
    <c:if test="${commentNum>0}">
   <div class="flat">
      <h3>${commentNum} 回复</h3>
      <ul class="discuss-replies" style="border: 1px solid;border-color:#DDDDDD ">
      <c:forEach items="${comments}" var="cm1">
          <li class="reply" data-author="${cm1.user.nickname}">
             <div class="who">
                <a href="" class="show-user-card"><img src="${cm1.user.headImage.imageMid}" alt="${cm1.user.nickname}"></a>
             </div>
          <div class="mbs">
              <strong class="mrs"><a href="" class="show-user-card " title="${cm1.user.nickname}">${cm1.user.nickname}</a></strong>
              <span class="said-meta"><fmt:formatDate value="${cm1.commentDate}" pattern="yy-MM-dd HH:mm"/></span>
          </div>
          <div class="said-content editor-content reply-editor-content">
              ${cm1.commentContent}
          </div>
           <p align="right">
          <button type="button" class="btn btn-link">回复</button> 
          </p> 
                         
          <ul class="discuss-replies" style="border: 1px solid;border-color:#DDDDDD ">   
          <c:forEach items="${comments2}" var="cm2"> 
           <c:if test="${cm2.comment.commentId eq cm1.commentId}">
           <li class="reply" data-author="${cm2.user.nickname}">
             <div class="who">
                <a href="" class="show-user-card"><img src="${cm2.user.headImage.imageMid}" alt="${cm2.user.nickname}" width="40px" height="40px"></a>
             </div>
              <div class="mbs">
                <strong class="mrs"><a href="" class="show-user-card " title="${cm2.user.nickname}">${cm2.user.nickname}</a></strong>
                <span class="said-meta"><fmt:formatDate value="${cm2.commentDate}" pattern="yyyy-MM-dd HH:mm"/></span>
              </div>
             <div class="said-content editor-content reply-editor-content">
                  ${cm2.commentContent}
             </div>
          </li>
          </c:if>    
          </c:forEach>  
         </ul>
         
          <div class="st">     
          <div style="display: none;" class="frame">
          <form id="form2" class="form1 reply-form" method="post" action="createReply.htm">
            <p class="text">
            <textarea class="xheditors aa" name="content" style="padding-left: 0px" required="required"></textarea>
            <input type="hidden" name="courseTimeId" value="${course.courseId}"/>
            <input type="hidden" name="parentId" value="${cm1.commentId}" />
            </p>
          </form>
            <p align="right">          
               <button class="btn btn-success">回复</button>
            </p>
           </div>         
          </div>
         </li>  
         </c:forEach>  
       </ul>
      </div>
    </c:if>

  <div class="flat">
      <h3>添加一条新回复</h3>

       <form id="form1" class=" form reply-form" method="post" action="createReply.htm">
          
          <p><textarea class="xheditors reply" id="content" name="content" required="required"></textarea></p>
            <input type="hidden" id="courseTimeId" name="courseTimeId" value="${course.courseId}" />         
      </form>
         <p>
            <button type="submit" class="btn btn-success" id="save">发布回复</button>
          </p>  
    </div>
            
							
	  </div>
	  
		<div id="course-cover">
			<div class="dialog" id="note-popup-composer"
				style="visibility:hidden;">
				<div class="wrap">
					<div class="title">
						<a href="javascript:;" class="close clost-it dialog-close" data-role="cancel" id="chacha">x</a> <span class="text">笔记：${course.courseTitle}</span>
					</div>

					<div class="content">
						<form action="goCreateNote.htm" method="post" id="note-form" >
						<input type="hidden" value="${course.courseId}" name="courseId" id="userCourseId">
							<div class="phs pvs">
								<div class="error" style="display: none"></div>
								<div class="mbs">
								
									 <textarea class="xheditors note" id="con" name="content" required="required"></textarea>
											
									
								</div>
								<div class="clearfix">
									<div class="fl">
										<select id="pub"name="isPublic"
											required="required"><option value="是"
												selected="selected">公开笔记</option>
											<option value="否">私有笔记</option></select>
									</div>
									<div class="fr">
										
										<button class="btn btn-success"
										id="note-submit-btn">保存</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="course-side">

			<div class="flat lesson-summary-flat">
				<h2>课时简介</h2>
				<p>${course.courseIntro}</p>
				  <p class="stat-bar tac">
                    <span class="stat-item" title="${course.scanNum} 查看">查看：<span class="view-stat-icon"></span>${course.scanNum}</span>
                    <span class="stat-item" title="${commentNum} 评论">评论：<span class="comment-stat-icon"></span>${commentNum}</span>
                  </p>
			</div>
			<div class="flat lesson-nav" id="lesson-window-list" style="display: block;">				
				<h2>课时列表</h2>
				<ul id="lesson">
				    <c:forEach items="${lessons}" var="les">			
					<li>
					<span class="lesson-index">L${les.lessonNum}</span> 
					<span class="lesson-title"> <a href="">${les.courseTitle}</a></span>
					</li>	
					</c:forEach>					
				</ul>
				<div class="clearfix window-list-bottom">
					<span class="fl">共${lessonNum}课时</span>
					<div class="fr">
					</div>
				</div>
			</div>
			

			<div class="flat">
				<h3>课程创建人</h3>

				<div class="course-author-block imageblock clearfix">
					<div class="imageblock-image">
						<a href="" class="show-user-card"data-uid="7800"><img src="${userCourse.user.headImage.imageMid}"></a>
					</div>
					    <div class="imageblock-content">
					    
	<c:if test="${user.userId!=userCourse.user.userId}">				    
      <c:if test="${at==0}">
      <button id="follow-user" class="btn btn-small action-ajax fr"><i class="icon-plus"></i> 关注TA</button>
      <a href="" id="unfollow-user" class="btn btn-small disabled action-ajax fr" style="display: none;">已关注</a>
      </c:if>
      <c:if test="${at==1}">
      <button id="follow-user" class="btn btn-small action-ajax fr" style="display: none;"><i class="icon-plus"></i> 关注TA</button>
      <a href="" id="unfollow-user" class="btn btn-small disabled action-ajax fr"> 已关注</a>
      </c:if>
      </c:if>
      <div  class="nickname"><a href="" class="show-user-card" data-uid="1295165" title="${userCourse.user.nickname}">${userCourse.user.nickname}<span class="o-ver-icn" title="${userCourse.user.signature}"></span></a></div>
      <div>
        <a href="/u/tsingyuan/library?tab=created" class="stats"><em>4</em> 课程</a>
        <a href="/u/tsingyuan/fans" class="stats">
          <em>${fansNum}</em> 粉丝</span></a>
      </div>
        <div class="mtm gray">${userCourse.user.signature}</div>
    </div>  

				</div>
			</div>

			 <div class="flat">
                 <c:if test="${leaned==0}">暂无用户学过</c:if>
                 <c:if test="${leaned>0}">
                 <h3>${leaned}人学过</h3>
                 <c:forEach items="${userCourses3}" var="uc3">
                 <a href="" title="${uc3.user.nickname}"><img src="${uc3.user.headImage.imageMid}"  alt="${uc3.user.nickname}的头像" width=24 height=24></a> 
                 </c:forEach>
                 </c:if>            
             </div>

    <div class="flat">
     <c:if test="${leaning==0}">暂无用户正在学习</c:if>
     <c:if test="${leaning>0}">
      <h3>${leaning}人正在学习</h3>
         <c:forEach items="${userCourses2}" var="uc2">
         <a href="" title="${uc2.user.nickname}"><img src="${uc2.user.headImage.imageMid}"  alt="${uc2.user.nickname}的头像" width=24 height=24></a> 
         </c:forEach>
      </c:if>            
   </div>


		</div>
	</section>
<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>
</body>
<script type="text/javascript">
jQuery(function($){
	if($('textarea.xheditors.reply').length!=0){
		$('textarea.xheditors.reply').xheditor({
			upLinkUrl:"uploadFile.htm",
			upLinkExt:"zip,rar,txt,doc,docx,pdf,ppt,pptx,pps,ppsx,xlsx,xls,7z",
			upImgUrl:"uploadPic.htm",
			upImgExt:"jpg,jpeg,gif,png", tools:'simple',
			forcePtag:false,
			html5Upload:false,  
			emotMark:true
				});
		}
	});
	</script>
	<script type="text/javascript">
jQuery(function($){
	if($('textarea.xheditors.note').length!=0){
		$('textarea.xheditors.note').xheditor({
			upLinkUrl:"uploadFile.htm",
			upLinkExt:"zip,rar,txt,doc,docx,pdf,ppt,pptx,pps,ppsx,xlsx,xls,7z",
			upImgUrl:"uploadPic.htm",
			upImgExt:"jpg,jpeg,gif,png", tools:'simple',
			forcePtag:false,
			html5Upload:false,  
			emotMark:true
				});
		}
	});
	</script>
		<script type="text/javascript">
jQuery(function($){
	if($('textarea.xheditors.aa').length!=0){
		$('textarea.xheditors.aa').xheditor({
			upLinkUrl:"uploadFile.htm",
			upLinkExt:"zip,rar,txt,doc,docx,pdf,ppt,pptx,pps,ppsx,xlsx,xls,7z",
			upImgUrl:"uploadPic.htm",
			upImgExt:"jpg,jpeg,gif,png", tools:'simple',
			forcePtag:false,
			html5Upload:false,  
			emotMark:true
				});
		}
	});
	</script>
</html>