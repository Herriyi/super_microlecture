<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>更改昵称 - 好知网</title>
  <link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />
<link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">

<script type="text/javascript" src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<script type="text/javascript" src="<c:url value="/js/ga.js"/>"></script>
</head>
</head>
<body class="flats-theme">

<jsp:include page="/jsp/include/head2.jsp"></jsp:include>


<section class="container clearfix">
  <div id="me" class="me clearfix">
    <div class="main">
      <div class="wrap">

<div class="main-head">
<h2>更改昵称</h2>
</div>

<div class="mod">
  <div class="tabs clearfix">
  <ul>
    <li class="on"><a href="account.htm">个人资料</a></li>
    <li><a href="goaccountavatar.htm">更新头像</a></li>
    <li><a href="goaccountPassword.htm">修改密码</a></li>
    <li><a href="queryLabel.htm?type=user">添加标签</a></li>
    
  </ul>
</div>
  
  <form id="account-nickname-form" class="form-horizontal" method="post" action="accountnickname.htm">
    <div class="control-group">
      <label class="control-label">昵称:</label>
      <div class="controls">
        <input type="text" name="nickname" class="span4" />
        <div class="help-block">昵称最多16个字符，支持中英文、数字、下划线，一个中文字算2个字符</div>
      </div>
    </div>

    <div class="control-group">
      <div class="controls">
        <div class="alert">注意：修改一次昵称，需要5个金币！</div>
        <button type="submit" class="btn btn-success">保存</button>
      </div>
    </div>

  </form>
</div>
</div>
    </div>
    <div class="side">

      <div class="avatar-mod clearfix">
        <a href="user.jsp" class="avatar"><img src="<c:url value="${user.headImage.imageMid}"/>" /></a>
        <div class="infos">
          <div class="nickname"><a>${user.nickname}</a></div>
          <div class="icons">
              <a class="user-level user-level-6" title="努力升级吧!" href="" target="_blank">${level.lv}级</a>
            <a class="user-level user-level-6">${level.title}</a>
          </div>
        </div>
      </div>

      <div class="stats-mod">
          <ul class="user-stats clearfix">
            <li><a href=""><strong>${user.credit}</strong>学分</a></li>
            <li><a href=""><strong>${user.gold}</strong>金币</a></li>
          </ul>
      </div>

      <jsp:include page="/jsp/include/leftside.jsp"></jsp:include> 
    </div>
  </div>
</section>


<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
</html>