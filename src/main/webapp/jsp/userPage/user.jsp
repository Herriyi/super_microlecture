<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录-微课程</title>
<link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />
<link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">

<script type="text/javascript" src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<script type="text/javascript" src="<c:url value="/js/ga.js"/>"></script>

<script type="text/javascript">
	$(function(){

			var a=$("#att").val();
			var userId=$("#btt").val();

			if(a=="no"){
				$("#follow-user").css("display","none");
				$("#unfollow-user").css("display","inline-block");
				}
			if(a=="ok"){
				$("#follow-user").css("display","inline-block");
				$("#unfollow-user").css("display","none");
				}
			$("#follow-user").click(function(){

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



				});
			$("#unfollow-user").click(function(){
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
</head>
<body class="lily-theme">


<jsp:include page="/jsp/include/head2.jsp"></jsp:include>





<section class="content container">
  <div class="container-padding clearfix">
    <div class="user-side">
      <div class="mod uportrait-mod">
      <div class="portrait">
                  <img src="<c:url value="${user1.headImage.imageLage}"/>" alt="">
              </div>
      </div>
      
      <div class="mod uinfo-mod">
                
          <div class="info-item"><span class="info-item-name">昵称：</span> <strong>${user1.nickname}</strong></div>
          <c:if test="${empty user1.city}">
          <div class="info-item"><span class="info-item-name">现居：地球村</span></div>
          </c:if>
          <c:if test="${!empty user1.city}">
          <div class="info-item"><span class="info-item-name">现居：${user1.city}</span></div>
          </c:if>
          <div class="info-item"><span class="fr">${user1.credit}学分</span><span class="info-item-name">等级：${level.lv}级&nbsp;${level.title}</span></div>
 			<c:if test="${empty user1.intro}">	
          <div class="info-item"><span class="info-item-name">简介：</span> 还什么都没写</div>
          </c:if>
          <c:if test="${!empty user1.intro}">	
          <div class="info-item"><span class="info-item-name">简介：</span> ${user1.intro}</div>
          </c:if>
          <c:if test="${user1.userId==user.userId}">
           <div class="info-item"> <a href="account.htm">编辑个人资料</a> <span class="info-item-name mhs">|</span>  <a href="goaccountavatar.htm">更新头像</a> </div>
            </c:if>
            <c:if test="${user1.userId!=user.userId}">
           		<div class="info-item"><a href="goSendMail1.htm?userName=${user1.nickname}" class="btn-n2sec">发私信</a></div>
            </c:if>               
                
      </div>
      
            
      <div class="mod">
        <h3>关注</h3>
        	
                <div class="mini-users-panel clearfix">
                <c:forEach items="${attList}" var="att">
          			
          			<a href="goPersonnal.htm?userId=${att.userByAttentionedUserId.userId}"><img src="<c:url value="${att.userByAttentionedUserId.headImage.imageMid}"/>" title="${att.userByAttentionedUserId.nickname}"></a>
          		 </c:forEach> 
          		</div>
             
       </div>
      
      <div class="mod">
        <h3>粉丝</h3>
        
       	  <div class="mini-users-panel clearfix">
       	  <c:forEach items="${fansList}" var="fans">
         	 <a href="goPersonnal.htm?userId=${fans.userByUserId.userId}"><img src="<c:url value="${fans.userByUserId.headImage.imageMid}"/>" title="${fans.userByUserId.nickname}" ></a> 
          </c:forEach>
          </div>
         
      </div>
     
    </div>
  
    <div class="user-main">
      <div class="UIImageBlock upage-top clearfix">
    <div class="UIImageBlock_Content">
    <div class="head">
    <c:if test="${user1.userId!=user.userId}">
    	<div class="fr" id="follow-user-opts">
          <a href="javascript:;" id="follow-user" class="btn btn-success ajax-action" style="display: inline-block;"><i class="icon-plus icon-white"></i> 关注TA</a>
          <a href="javascript:;" id="unfollow-user" class="btn btn-success active ajax-action" style="display: none;">已关注 | X </a>
          <input type="hidden" name="aa" value="${isOk}" id="att">
          <input type="hidden" name="bb" value="${user1.userId}" id="btt">
        </div>
    </c:if>
                          <div class=""><h2>${user1.nickname}</h2></div>
    </div>
    <c:if test="${empty user1.signature}">
    		<div class="mood">
                <a href=""> ^.^ <span class="fsn">设置签名档</span></a>
              </div>
     </c:if>
     <c:if test="${!empty user1.signature}">
    		<div class="mood">
                 <span class="fsn">${user1.signature}</span>
              </div>
     </c:if>
    <div class="nav clearfix">
      <a href="goPersonnal.htm?userId=${user1.userId}" class="on">主页</a>
      <a href="goPersonalTeam.htm?userId=${user1.userId}">小组</a>
      <a href="goPersonalAtt.htm?userId=${user1.userId}">好友</a>
      <a href="goPersonalNote.htm?userId=${user1.userId}">笔记</a>
    </div>
  </div>
</div>


      <div class="upage-content">
        <c:if test="${user1.userId==user.userId}">
        <div class="mod">
        
          <div class="mod-head">
          <h3><span>我的标签</span></h3>
        	</div>
           <div class="tags">
        	<c:forEach items="${lablist}" var="lablist">
             	<a href="" class="tag">${lablist.label.labelName}</a>
        	</c:forEach>
        	</div>
        	<div class="info-item"> <a href="queryLabel.htm?type=user">编辑标签</a></div>
        </div>
        </c:if>
        <c:if test="${user1.userId!=user.userId}">
        <div class="mod">
        
           <div class="mod-head">
          <h3><span>他的标签</span></h3>
        	</div>
          <div class="tags">
        	<c:forEach items="${lablist}" var="lablist">
             	<a href="" class="tag">${lablist.label.labelName}</a>
        	</c:forEach>
        	</div>
                    
        </div>
        </c:if>
       	<hr>
        <div class="mod">
          			<h3>在学的课程</h3>
      				 <ul class="course-cards">
      				 <c:forEach items="${courseList}" var="coursed">
                        <li>
							<div class="course-item">
  								<div class="thumb"><a href=""><img src="/pic/daa.jpg"></a></div>
 									<p class="title"><a href="" title="新版课程创建指南">${coursed.course.courseTitle}</a></p>
    								<p class="metas clearfix">
    									
										<span class="fr view mrm" title="查看次数">浏览次数：${coursed.course.scanNum}</span>
  									</p>
							</div>
						</li>
						</c:forEach>
         			</ul>
         </div>
        	<hr>
                <div class="mod">
          				<h3>学过的课程</h3>
      				 <ul class="course-cards">
      				  <c:forEach items="${courseList1}" var="course">
                        <li>
							<div class="course-item">
  								<div class="thumb"><a href=""><img src="/pic/daa.jpg"></a></div>
 									<p class="title"><a href="" title="新版课程创建指南">${course.course.courseTitle}</a></p>
    								<p class="metas clearfix">
										<span class="fr view mrm" title="查看次数">浏览次数：${course.course.scanNum}</span>
  									</p>
							</div>
						</li>
						</c:forEach>
         			</ul>
                  </div>
     
    </div>
  
</div>
</div></section>


<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
</html>