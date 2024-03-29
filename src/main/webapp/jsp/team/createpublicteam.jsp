<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>创建公开小组 - 好知网</title>
   <link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />
<link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">

<script type="text/javascript" src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<script type="text/javascript" src="<c:url value="/js/ga.js"/>"></script>

</head>
<body class="lily-theme">


<jsp:include page="/jsp/include/head2.jsp"></jsp:include>






<section class="container group">
<div class="container-padding clearfix">
  <h1>创建公开小组</h1>

      <form id="group-form" class="form group-form" method="post" action="createTeam.htm">
      
      <p>
        <label for="group_name" class="required">小组名称:</label>
        <input type="text" id="team_name" name="teamName" required="required"    class="wide" />
      </p>
      <p>
        <label for="group_about" class="required">小组介绍:</label>
        <textarea id="group_about" name="teamIntro" required="required"    class="wide low mceEditor"></textarea>
      </p>
      <p>
        <label for="group_about" class="required">小组类型:</label>
        <select id="team_type" name="teamType" required="required"> 
        <c:forEach items="${majorType}" var="mtype">
        <option value="${mtype.dicValue}"><c:out value="${mtype.dicValue}"></c:out></option>
        </c:forEach>
        </select>
      </p>
      <p>
        <label for="group_joinMethod" class="required">成员加入方式:</label>
        <select id="group_joinMethod" name="method" required="required"><option value="1">允许任何人加入小组</option><option value="2">[即将开放] 只有接受组员邀请才能加入小组</option><option value="3">[即将开放] 需要小组管理员批准后才能加入小组</option></select>
      </p>

      <p class="actions">  
        <button type="submit" class="btn btn-success">创建</button>
        <a href="/groups/" class="btn mls">取消</a>
      </p>

    </form>

</div>

</section>

<script type="text/javascript" src="/tinymce-3.46/tiny_mce.js?v=1201" ></script>
<script type="text/javascript" src="/tinymce-3.46/tiny_mce_config.js?v=1201" ></script>
<script type="text/javascript">tinyMCE.init(tinymce_config_simple);</script>



<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
</html>