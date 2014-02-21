<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>挑战记录</title>
<link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />
<link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">

<script type="text/javascript" src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<script type="text/javascript" src="<c:url value="/js/ga.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/Chart.js"/>"></script>
</head>
<body class="flats-theme">
<script src="<c:url value="/js/Chart.js"/>"></script>

<jsp:include page="/jsp/include/head2.jsp"></jsp:include>



<section class="container clearfix">
  <div id="me" class="me clearfix">
    <div class="main">
      <div class="wrap">
      <div class="main-head">
      <h2>我的道具</h2>
    </div>
    <div class="mod">
    <div class="row-fluid">
       <ul class='task-cards'>
       
       <c:forEach var="y" items="${myprop}" varStatus="status">
         <li>
           <div class="task-card imageblock">
           <div class="icon imageblock-image">
           <c:choose>
           <c:when test="${y.prop.propId=='2'}">
           <img src="<c:url value="/pic/propdefault2.jpg"/>" />
           </c:when>
           <c:otherwise>
           <img src="<c:url value="/pic/propdefault1.gif"/>" /></c:otherwise>
           </c:choose>
           
           
           </div>
            <div class="detail imageblock-content">
              <h4>${y.prop.propName}</h4>
              <p>
                 <strong>介绍：</strong>${y.prop.propIntro}
               </p>
              <p>
             <strong>数量：</strong>
                 ${y.number}
             </p>
           </div>
          <div class="button">
                    <a href="mypropuse.htm?propId=${y.prop.propId}&userPropId=${y.userPropId}" class="btn btn-small pop-task-btn">我要使用</a>
           </div>
          </div>
        </li>
        </c:forEach>
        </ul>
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
          <li ><a href="challenge.htm"><i class="feature-icon feature-icon-faq"></i>微挑战</a></li>
          <li ><a href="rank.htm"><i class="feature-icon feature-icon-faq"></i>微排行</a></li>
          <li ><a href="myrank.htm"><i class="feature-icon feature-icon-faq"></i>我的排行</a></li>
          <li ><a href="teamPage.htm"><i class="feature-icon feature-icon-group"></i>小组</a></li>
          <li ><a href="shop.htm"><i class="feature-icon feature-icon-faq"></i>商城</a></li>
          <li class="active"><a href="myprop.htm"><i class="feature-icon feature-icon-faq"></i>我的道具</a></li>
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

</body></html>