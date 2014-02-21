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
</head>
<body class="flats-theme">

<jsp:include page="/jsp/include/head2.jsp"></jsp:include>

<section class="container clearfix">

<div class="me clearfix">
<div class="row-fluid">
<div class="span2 well" style="margin-left: 10px;margin-top: 20px">
      
     <div class="menu-mod clearfix">
        <ul class="menus">
          <li ><a href="myrank.htm">我的排行</a></li>
        </ul>
      
       <div class="divider"></div>
        
        <ul class="menus">
          <li ><a href="rank.htm">挑战排行</a></li>
        </ul>

         <div class="divider"></div>

        <ul class="menus">
          <li ><a href="rankTeam.htm">小组排行</a></li>
        </ul>

         <div class="divider"></div>

        </div>
</div>

<div class="span9">
      
      
      
      <div class="span9 offset2 " style="margin-left: 10px;margin-top: 20px" >
      
       <div class="list-tt">
	       <span class="btn-success btn">总排行</span><br><br>
	       
       </div>
       <table class="table table-hover">
              <thead>
                <tr>
                  <th>排名</th>
                  <th>名称</th>
                  <th>类型</th>
                  <th>建设度</th>
                </tr>
              </thead>
              <tbody>
               <c:forEach var="x" items="${teamList}" varStatus="status">
                
                <c:choose><c:when test="${x==null}"></c:when><c:otherwise>
                <c:if test="${status.index<10}">
                <tr>
                  <td>${status.index+1}</td>
                  <td>${x.teamName}</td>
                  <td>${x.type}</td>
                  <td>${x.construction}</td>
                </tr>
                </c:if>
                <c:if test="${status.index>=10}">
                </c:if>
                </c:otherwise></c:choose>
                </c:forEach>
              </tbody>
            </table>
            
     </div>
     
    


<c:forEach var="y" items="${teambiglist}" varStatus="status">
<div class="span5 " style="margin-left: 35px;margin-top: 30px;height: 500px" >
   <div class="list-tt">
	       <span class="btn-success btn-small">${typeList.get(status.index).dicValue}</span><br><br>
	       
       </div>
       <c:choose><c:when test="${y==null}"><h2>沙发空缺中</h2></c:when><c:otherwise>
      <table class="table table-hover">
              <thead>
                <tr>
                  <th>排名</th>
                  <th>名称</th>
                  <th>类型</th>
                  <th>建设度</th>
                </tr>
              </thead>
              <tbody>
               <c:forEach var="x" items="${y}" varStatus="status2">
                
                <c:choose><c:when test="${x==null}"></c:when><c:otherwise>
                <c:if test="${status2.index<10}">
                <tr>
                  <td>${status2.index+1}</td>
                  <td>${x.teamName}</td>
                  <td>${x.type}</td>
                  <td>${x.construction}</td>
                </tr>
                </c:if>
                <c:if test="${status2.index>=10}">
                </c:if>
                </c:otherwise></c:choose>
                </c:forEach>
              </tbody>
            </table>
            </c:otherwise></c:choose>
            </div>
</c:forEach>
</div>
</div>

</div>


</section>


<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
</html>