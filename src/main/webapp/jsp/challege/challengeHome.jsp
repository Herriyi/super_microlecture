<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>我的好知 - 好知网</title>
  <link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />
<link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">

<script type="text/javascript" src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<script type="text/javascript" src="<c:url value="/js/ga.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/scojs.css"/>">
<script type="text/javascript" src="<c:url value="/js/sco.message.js"/>"></script>
</head>
<body class="flats-theme">

<jsp:include page="/jsp/include/head2.jsp"></jsp:include>

<script type="text/javascript">
 $(document).ready(function(){
	   
	   var message='<%=(String)request.getAttribute("message")%>';
	   
	  
	  if(message==null||message=='null'){
		  
	  }else{
		  $.scojs_message(message, $.scojs_message.TYPE_ERROR);
	  }
	}); 
	 
  
</script>



<section class="container clearfix">

  <div id="me" class="me clearfix">
    <div class="main">
      <div class="wrap">
            <div class="main-main">
          

      <div class="mod">
    
        <div class="tab-bar">
           <ul class="clearfix">
               <li class="active"><a href="">挑战</a></li>
              
          </ul>
       </div>  
     </div>

  <div class="mod">
  <c:forEach var="classic" items="${challengeclassic}" varStatus="status">
  <div class="course-row"  id="course-row-4837">
    <div class="imageblock">
      <div class="imageblock-image"><a href=""><img src="http://f1.howzhi.com/course-icon/2013/09-06/22545930c193023993.jpg"></a></div>
      <div class="imageblock-content">
        <div class="title">
         <h2><a href="" class="ignore-btn"   title="" style="visibility: visible; ">${classic.dicValue}</a></h2>
          
        </div>
        <div class="summary">跃跃欲试</div>
        <div class="metas">
         
          <a href="#"></a>
        </div>
        <form action="fight.htm" method="post">
        <input type="hidden"  name="type" value="${classic.dicValue}">
          <button type="submit"  class="btn btn-mini btn-success fr join-btn" style="visibility: visible; margin-top: -16px; ">挑战</button>
        </form> 
      </div>
    </div>
   </div>
   </c:forEach>
    
   
  
  </div>


</div>

<div class="main-side">

  

    <div class="mod">
       
        
          <!--  <p class="fsm">答题规则</p>-->
          <c:choose><c:when test="${(userProp=='null')||(userProp==null)}">
          <p class="" style="color: red;">没有可用道具!</p>
          <p><a href="shop.htm">&raquo; 前往商城购买</a></p>
          </c:when>
          <c:otherwise>
          <p class="fsm">可用道具：${userProp.prop.propName} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${userProp.number}个</p>
          <p class="">${userProp.prop.propIntro}</p>
          
          <p><a href="usePropFormHome.htm">&raquo; 立即使用道具</a></p>
        </c:otherwise></c:choose>
    </div>

</div>

</div>
    </div>
    <div class="side">

      <div class="avatar-mod clearfix">
        <a href="user.jsp" class="avatar"><img src="<c:url value="${user.headImage.imageMid}"/>" /></a>
        <div class="infos">
          <div class="nickname"><a href="user.jsp" title="fanfanle">${user.nickname}</a></div>
          <div class="icons">
            <a class="user-level user-level-6" title="努力升级吧!" href="turnToHelpPage.htm" target="_blank">${level.lv}级</a>
            <a class="user-level user-level-6">${level.title}</a>

          </div>
        </div>
      </div>

      <div class="stats-mod">
          <ul class="user-stats clearfix">
            <li><a href="/me/point"><strong>${user.credit}</strong>学分</a></li>
            <li><a href="/me/coin"><strong>${user.gold}</strong>金币</a></li>
          </ul>
      </div>

      <div class="menu-mod clearfix">
         <ul class="menus">
          <li ><a href="turnToHomePage.htm"><i class="feature-icon feature-icon-home"></i>我的首页</a></li>
          <li ><a href="courseList.htm"><i class="feature-icon feature-icon-teach"></i>我的课程</a></li>
          <li ><a href="goNote.htm"><i class="feature-icon feature-icon-note"></i>笔记</a></li>
          <li class="active"><a href="challenge.htm"><i class="feature-icon feature-icon-faq"></i>微挑战</a></li>
          <li ><a href="rank.htm"><i class="feature-icon feature-icon-faq"></i>微排行</a></li>
          <li ><a href="myrank.htm"><i class="feature-icon feature-icon-faq"></i>我的排行</a></li>
          <li ><a href="teamPage.htm"><i class="feature-icon feature-icon-group"></i>小组</a></li>
          <li ><a href="shop.htm"><i class="feature-icon feature-icon-faq"></i>商城</a></li>
          <li ><a href="myprop.htm"><i class="feature-icon feature-icon-faq"></i>我的道具</a></li>
          <li ><a href="myFavotite.htm"><i class="feature-icon feature-icon-favorite"></i>收藏夹</a></li>
        </ul>

        <div class="divider"></div>

        <ul class="menus">
          <li ><a href="myAttention.htm"><i class="feature-icon feature-icon-friend"></i>好友</a></li>
          <li ><a href="goPrivateMail.htm"><i class="feature-icon feature-icon-message"></i>私信</a></li>
        </ul>

        <div class="divider"></div>

        <ul class="menus">
          <li ><a href="turnToHelpPage.htm"><i class="feature-icon feature-icon-coin"></i>金币</a></li>
          <li ><a href="account.htm"><i class="feature-icon feature-icon-setting"></i>账户设置</a></li>
        </ul>
      </div>

    </div>
  </div>
  
</section>


<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
</html>