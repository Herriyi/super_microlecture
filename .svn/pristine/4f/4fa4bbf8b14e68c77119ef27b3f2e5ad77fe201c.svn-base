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
<link rel="stylesheet" href="<c:url value="/css/square/green.css"/>">
<script type="text/javascript" src="<c:url value="/js/jquery.icheck.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/scojs.css"/>">
<script type="text/javascript" src="<c:url value="/js/sco.message.js"/>"></script>
<script>

</script>

<script type="text/javascript">
	
</script>
<style>
  .time-item strong{background:#F19406;color:#fff;line-height:49px;font-size:36px;font-family:Arial;padding:0 10px;margin-right:10px;border-radius:5px;box-shadow:1px 1px 3px rgba(0,0,0,0.2);}
#day_show{float:left;line-height:49px;color:#c71c60;font-size:32px;margin:0 10px;font-family:Arial, Helvetica, sans-serif;}
.item-title .unit{background:none;line-height:49px;font-size:24px;padding:0 10px;float:left;}
  h1{font-family:"微软雅黑";font-size:40px;margin:20px 0;;border-bottom:solid 1px #ccc;padding-bottom:20px;letter-spacing:2px;}
</style>
</head>
<body class="flats-theme">


<jsp:include page="/jsp/include/head2.jsp"></jsp:include>


<script type="text/javascript">
 
</script>


<section class="container ">

  <div class="light-page">
    <div class="page-head"><h2>${challengeuser.totalScore}分&nbsp;&nbsp;</h2></div>
    <div class="page-body clearfix">

     <div class="page-body-main">
     
     <ul class="thumbnails">
     
         <li class="span3">
            <div class="thumbnail featuredbox">
              <a href="experiments/sync.html"><img src="/pic/shop/everyday.jpg" width="200" height="150"></a>
              <div class="hotfeaturedtext">
                <strong>完成每日挑战</strong>
                <p><span >分数:</span>&nbsp;&nbsp;&nbsp;&nbsp;${challengeuser.totalScore}</p>
                <p><span >名次:</span>&nbsp;&nbsp;&nbsp;&nbsp;${challengeuser.majorRank}</p>
              </div> <!--/featuredtext-->
              <div class="hotfeaturedbutton"> 
                <hr>
                <a href="experiments/sync.html"><span></span></a>
              </div>
            </div>
          </li>
     
         
          <!--/////// FEATURED BOX ///////-->
         <c:choose><c:when test="${scoreBreak=='1'}">
          <li class="span3">
            <div class="thumbnail featuredbox">
              <a href="experiments/sync.html"><img src="/pic/shop/break.jpg" width="200" height="150"></a>
              <div class="hotfeaturedtext">
                <strong>个人新高分</strong>
                <p>${challengeuser.totalScore}分<span class="btn-success">高于</span></p>历史最高分:${maxS}分 
              </div> <!--/featuredtext-->
              <div class="hotfeaturedbutton"> 
                <hr>
                <a href=""><span></span></a>
              </div>
            </div>
          </li>
          </c:when> <c:otherwise></c:otherwise></c:choose>
          <!--///////END FEATURED BOX///////-->
          <!--/////// FEATURED BOX ///////-->
          <c:choose><c:when test="${rankBreak=='1'}">
          <li class="span3">
            <div class="thumbnail featuredbox">
              <a href="http://www.soshareit.com" target="_blank"><img src="/pic/shop/break2.jpg" width="200" height="150"></a>
              <div class="hotfeaturedtext">
                <strong>打破专业排名记录</strong>
                <p><span class="btn-success">原有排名</span>${maxR}</p>
                <p><span class="btn-success">现有排名</span>${challengeuser.majorRank}</p>
              </div> <!--/featuredtext-->
              <div class="hotfeaturedbutton"> 
                <hr>
                <a href="http://soshareit.com" target="_blank"><span></span></a>
              </div>
            </div>
          </li>
           </c:when><c:otherwise></c:otherwise></c:choose>
          <!--///////END FEATURED BOX///////-->
          <!--/////// FEATURED BOX ///////-->
          <c:choose><c:when test="${rank10th=='1'}">
          <li class="span3">
            <div class="thumbnail featuredbox">
              <a href="experiments/bittorrent-live.html"><img src="/pic/shop/rank10th.jpg" width="200" height="150"></a>
              <div class="hotfeaturedtext">
                <strong>进入前十</strong>
                <p>位列：${challengeuser.majorRank}</p>
              </div> <!--/featuredtext-->
              <div class="hotfeaturedbutton"> 
                <hr>
                <a href="experiments/bittorrent-live.html"><span></span></a>
              </div>
            </div>
          </li>
          </c:when>
          <c:otherwise>
          <li class="span3">
            <div class="thumbnail featuredbox">
              <a href="experiments/bittorrent-live.html"><img src="/pic/shop/fail.jpg" width="200" height="150"> </a>
              <div class="hotfeaturedtext">
                <strong>未能进入前十</strong>
                <p></p>
              </div> <!--/featuredtext-->
              <div class="hotfeaturedbutton"> 
                <hr>
                <a href="experiments/bittorrent-live.html"><span></span></a>
              </div>
            </div>
          </li>
          </c:otherwise></c:choose>
          <!--///////END FEATURED BOX///////-->
          <!--/////// FEATURED BOX ///////-->
          
          <!--///////END FEATURED BOX///////-->
        </ul>
        
    </div>
    
    <div class="page-body-side">
        
          
      
       
          <p class="fsm">答题规则</p>
          <c:choose><c:when test="${(userProp=='null')||(userProp==null)}">
          <p class="" style="color: red;">没有道具!</p>
          <p><a href="shop.htm">&raquo; 前往商城购买</a></p>
          </c:when>
          <c:otherwise>
          <p class="fsm">现有道具：${userProp.prop.propName} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${userProp.number}个</p>
          <p class="">${userProp.prop.propIntro}</p>
          
          <p><a href="useProp.htm?challengeId=${challengeuser.challengeId}">&raquo; 立即使用道具</a></p>
        </c:otherwise></c:choose>
      </div>

  </div>
</div>
  <a href="challenge.htm" class="btn btn-large btn-block btn-primary" type="button">返回挑战首页</a>
</section>


<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
         