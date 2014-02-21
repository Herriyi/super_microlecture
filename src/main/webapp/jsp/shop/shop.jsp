<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
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


<script type="text/javascript" src="<c:url value="/js/ga.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap.min.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/scojs.css"/>">
<script type="text/javascript" src="<c:url value="/js/sco.message.js"/>"></script>

</head>
<body class="flats-theme">


<jsp:include page="/jsp/include/head2.jsp"></jsp:include>



 <script type="text/javascript">
 $(document).ready(function(){
	   
	   var message='<%=request.getAttribute("message")%>';
	   var message2='Welcome back!';
	  
	  if(message==null||message=='null'){
		  
	  }else{
		  $.scojs_message(message, $.scojs_message.TYPE_OK);
	  }
	}); 
	 
  
</script>
<section class="container homepage">

    <div class="flat clearfix">
        <h2>微商城</h2>
        <ul class="cells channel-cells">
           <li class="cell"><a href=""><i class="channel-icon channel-icon-photography"></i>笔记</a></li>
           <li class="cell"><a href=""><i class="channel-icon channel-icon-programme"></i>道具</a></li>
          
    
      </ul>   
    </div>

   <div class="flat clearfix">
        <h2>道具</h2>
        <ul class="cells cells-middle">
        <c:forEach var="prop" items="${shopprop}" varStatus="status">
        <li class="cell">
        <div class="course-item">
         <div class="thumb">
         <c:choose>
         <c:when test="${prop.goodsObject==2}">
         <img src="<c:url value="/pic/propdefault2.jpg"/>" alt="" />
         </c:when>
         <c:otherwise><img src="<c:url value="/pic/propdefault1.gif"/>" alt="" /></c:otherwise>
         </c:choose>
         
         
         </div>
         <p class="title">
         <a href="buy.htm">
         <c:choose>
         <c:when test="${prop.goodsObject==2}">
         <span class="video" ></span>微挑战道具:免答</c:when>
           <c:otherwise><span class="video" ></span>微挑战道具:原地复活</c:otherwise>
         </c:choose>
         </a>
         </p>
          <div class="summary">${prop.goodsIntro}</div>
          <p class="metas clearfix">
           <span class="fl by">by <a href="" class="show-user-card"  title="商家"></a></span>
          
          <span class="fr learn" title="购买人数">${prop.putawayDate}</span>
          <span class="fr view mrm" title="金币">数量:${prop.number}  单价：${prop.price}金币</span>
         
        </p>
     </div>
     <form action="buyprop.htm" method="post" name="buyprop">
      <span class="">
          <input type="text" name="buynumber" style="width:60px;margin-left: 10px;margin-top: 17px"/>
          <input type="hidden" name="id" value="${prop.goodsId}"></input>
          <button type="submit" class="btn btn-success" value="购买" style="margin-left: 50px;margin-top: 5px">购买</button></span>
       </form>
     </li>
     </c:forEach>
    </ul>        
    <div class="pagination mvm"><ul>
      
      
  </ul>
</div>   
 </div>
 <div class="flat clearfix">
        <h2>笔记(测试中)</h2>
        <ul class="cells cells-middle">
        <c:forEach var="note" items="${shopnote}" varStatus="status">
        <li class="cell">
        <div class="course-item">
         <div class="thumb"><a href=""><img src="" alt="" /></a></div>
         <p class="title"><a href="buy.htm" title=""><span class="video" title="视频课程"></span>COMMING SOON</a></p>
          <div class="summary">${note.goodsIntro}</div>
          <p class="metas clearfix">
          <span class="fl by">by <a href="" class="show-user-card"  title="商家"></a></span>
          
          <span class="fr learn" title="购买人数"><c:out value="${note.putawayDate}"/></span>
          <span class="fr view mrm" title="金币">数量:${note.number}  单价：${note.price}金币</span>
        </p>
     </div>
     <!--提交数量不能为空或者0  -->
     <form action="" method="post" name="buynote">
       <input type="hidden" name="id" value="${note.goodsId}"></input>
       <input type="hidden" name="type" value="note"></input>
       <input type="hidden" name="seller" value="${note.userBySallerId}"></input>
        <button class="btn btn-success disabled" type="button" value="购买" style="margin-left: 140px;margin-top: 15px">购买</button>
     </form>
     </li>
     </c:forEach>
    </ul>        
    <div class="pagination mvm"><ul>
     
      
      </ul>
     </div>   
 </div>
    
</section>

<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>


</body>
</html>