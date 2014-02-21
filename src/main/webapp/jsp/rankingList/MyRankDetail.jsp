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

<script>
$(function(){
var ctx = $("#myChart").get(0).getContext("2d");


var data = {
		labels : ["January","February","March","April","May","June","July"],
		datasets : [
			{
				fillColor : "rgba(220,220,220,0.5)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				data : [65,59,90,81,56,55,40]
			    
			},
			{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : [28,48,40,19,96,27,100]
			}
		]
	}
  
    new Chart(ctx).Line(data,true);
    
    
	})
  
</script>
</head>
<body class="flats-theme">


<jsp:include page="/jsp/include/head2.jsp"></jsp:include>


<section class="container clearfix">
  <div id="me" class="me clearfix">
    <div class="main">
      <div class="wrap">
      <div class="main-head">
      <h2>挑战记录<span>&raquo; 计算机</span></h2>
    </div>
    <div class="mod">
    <div class="row-fluid">
    <div class="span10 offset1">
           <table class="table table-hover">
              <thead>
                <tr>
                  <th>排名</th>
                  <th></th>
                  <th>得分</th>
                  <th></th>
                  <th>日期</th>
                </tr>
              </thead>
              <tbody>
                <c:choose>
                <c:when test="${myranklist==null}">
                <h3>还没有挑战过</h3>
                </c:when>
                <c:otherwise>
                <c:forEach var="x" items="${myranklist}" varStatus="status">
                <tr>
                  <td>${x.majorRank}</td>
                  <td></td>
                  <td>${x.totalScore}</td>
                  <td></td>
                  <td>${x.challengeDate}</td>
                </tr>
                </c:forEach>
                </c:otherwise>
                </c:choose>
              </tbody>
            </table>
          </div>
      <!--   <div class="span9">
          <h4>我的成绩(蓝)/评价成绩(灰)</h4>
          <canvas id="myChart" width="350" height="300"></canvas>
       </div>
      -->
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
            <a class="user-level user-level-6" title="努力升级吧!" href="/help/helplevel.jsp" target="_blank">${level.lv}级</a>${level.title}

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
          <li class="active"><a href="myrank.htm"><i class="feature-icon feature-icon-faq"></i>我的排行</a></li>
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