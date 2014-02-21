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
$(document).ready(function(){
  $('input').iCheck({
    checkboxClass: 'icheckbox_square',
    radioClass: 'iradio_square-green',
    increaseArea: '20%' // optional
  });
});
</script>

<script type="text/javascript">
	var intDiff = parseInt(20);//初始日期，以秒为单位的
	function timer(intDiff){
		window.setInterval(function(){
		var day=0,
			hour=0,
			minute=0,
			second=0;//时间默认值		
		if(intDiff > 0){
			minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
			second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            
		}
		if(intDiff == 0){
			$('#course-create-form').submit();
		}
		if (minute <= 9) minute = '0' + minute;
		if (second <= 9) second = '0' + second;
		$('#minute_show').html('<s></s>'+minute+'m');
		$('#second_show').html('<s></s>'+second+'s');
		intDiff--;
		}, 1000);
	} 
	$(function(){
		timer(intDiff);
	});	
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
 $(document).ready(function(){
	   
	   var message='<%=(String)request.getAttribute("message")%>';
	   var message2='答题开始';
	  
	  if(message==null||message=='null'){
		  $.scojs_message(message2, $.scojs_message.TYPE_ERROR);
	  }else{
		  $.scojs_message(message, $.scojs_message.TYPE_OK);
	  }
	}); 
	 
  
</script>



<section class="container ">

  <div class="light-page">
    <div class="page-head"><h2>你已经得到${challengeuser.totalScore}分</h2></div>
    <div class="page-body clearfix">

     <div class="page-body-main">
     <form id="course-create-form" class="form-horizontal" method="post" action="fightNext.htm" >
     
       <label>第${challengeuser.passNum}关   &nbsp; &nbsp;&nbsp;<span style="font-size: 5px">难度：${question.questionLv}</span></label>
       <div class="control-group">
         
          <div class="controls">
              <label class=""> ${question.queestionContent}</label>
          </div>
       </div>


       <div class="control-group" id="course-category-control-group">
        
      
       <div class="">
          <c:forEach var="op" items="${options}" varStatus="status">
          <div class="row-fluid">
          <div class="span1">
           <input type="radio" name="iCheck" value="${op.answerScore}"></input></div>
           <div class="span6">
           <span>${op.answerContent}</span>
           </div>
          </div>
          </c:forEach>
       </div>
       </div>          
       <div class="control-group">
            <div class="controls">
              <button type="submit" class="btn btn-success">提交</button>
            </div>
       </div>
    </form>
   </div>
     
      <div class="page-body-side">
         <div class="mod">
         <div class="mod-head"><h3><span>计时器</span></h3></div>
         <div class="gray mbs tar">
         <span class="badge badge-warning fss">DANGER</span> ！
         </div>
         <div class="mbm"><div href="" class="btn btn-success btn-large" style="width: 200px;">
                          
            
            
            <div class="time-item">
               <strong id="minute_show">0m</strong>
               <strong id="second_show">0s</strong>
            </div>                  
          </div></div>
        
         </div>
          
      
       
          <p class="fsm">答题规则</p>
          
          <c:choose><c:when test="${(userProp=='null')||(userProp==null)}">
          <p class="" style="color: red;">没有可用道具!</p>
          <p><a href="shop.htm" title="当前答题将视为放弃">&raquo; 前往商城购买</a></p>
          </c:when>
          <c:otherwise>
          <p class="fsm">可用道具：${userProp.prop.propName} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${userProp.number}个</p>
          <p class="">${userProp.prop.propIntro}</p>
          
          <p><a href="fightNext.htm?useprop=${userProp.prop.propId}">&raquo; 立即使用道具</a></p>
          </c:otherwise></c:choose>
       
      </div>

    </div>

  </div>

  
</section>


<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
         