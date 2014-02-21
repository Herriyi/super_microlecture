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

 <script type="text/javascript">
 $(document).ready(function(){
	   
	   var message='<%=request.getParameter("message")%>';
	   
	  if(message==null||message=='null'){
		  
	  }else{
		  $.scojs_message(message, $.scojs_message.TYPE_OK);
	  }
	}); 
	 
  
</script>




<section class="container group">
<div class="container-padding clearfix">
  <h1>购买</h1>

      <form id="group-form"  method="post" action="buypropconfig.htm">
      
       <table class="table table-hover">
       <thead>
       <tr>
       <th></th>
       <th></th>
       </tr>
       </thead>
       
       <tbody> 
       <tr>
       <td><span class="btn  btn-info disabled">商品介绍</span></td>
       <td> 
        <font color="black" style="font-size: large;font-weight: bold;">${goods.goodsIntro}</font><br><br>
       </td>
       </tr>
       <tr>
       <td>        <span class="btn  btn-info disabled ">购买数量</span>

       <td> 
        <font color="black" style="font-size: large;font-weight: bold;">${number}</font><br><br>
       </td>
       </tr><tr>
       <td>        <span class="btn  btn-info disabled">单价</span>

       <td> 
       <font color="black" style="font-size: large;font-weight: bold;"> ${goods.price}</font><br><br>
       </td>
       </tr><tr>
       <td>        <span class="btn  btn-info disabled">包含道具</span>

       <td> 
        <font color="black" style="font-size: large;font-weight: bold;">${prop.propName}</font><br><br>
       </td>
       </tr>
       
       
       <tr>
       <td>        <span class="btn  btn-info disabled">道具简介</span>

       <td> 
         <font color="black" style="font-size: large;font-weight: bold;">${prop.propIntro}</font><br><br>       </td>
       </tr>
       
        
       <tr>
       <td>                <span class="btn  btn-info disabled ">您现有金币</span>


       <td> 
        <font color="black" style="font-size: large;font-weight: bold;">${user.gold}个</font>  <br><br>
       </tr> 
       <tr>
       <td>               <span class="btn  btn-info disabled">您将支付金币</span>


       <td> 
        <font color="black" style="font-size: large;font-weight: bold;">${golds}个</font>  <c:choose><c:when test="${buymessage=='可以支付'}"><span class="btn btn-warning btn-mini">${buymessage}</span></c:when><c:otherwise><span class="btn btn-danger btn-mini">${buymessage}</span></c:otherwise></c:choose>
       </tr> 
       <tr>
       <td>        <span class="btn  btn-info disabled">您将获得积分</span>

       <td> 
                  <font color="black" style="font-size: large;font-weight: bold;">${credits}学分</font><br><br>
      </td>
       </tr>
       
       
       
       </tbody>
       
       </table>
        
     
      
      
      
        
       
     
        
       <br><br>
        
         
      <c:choose>
      <c:when test="${buymessage=='可以支付'}">
      <p class="actions">  
        <!-- 提交数量 -->
        <input type="hidden" name="number" value="${number}">
        <input type="hidden" name="goods" value="${goods.goodsId}">
        <input type="hidden" name="prop" value="${prop.propId}">   
        <button type="submit" class="btn btn-success btn-large btn-block" >确认</button>
        <a href="shop.htm" class="btn   btn-large btn-block" >取消</a>
      </p>
      </c:when>
      <c:otherwise>
      <a href="shop.htm" class="btn btn-primary btn-large" style="float:right; ">返回</a>
      </c:otherwise>
      </c:choose>
      
      

    </form>

</div>

</section>



<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
</html>