<%@page import="org.sicdlab.microlecture.common.bean.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>

<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录-微课程</title>
<link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />
<link rel="stylesheet"
	href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">

<script type="text/javascript"
	src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<script type="text/javascript"
	src="<c:url value="/js/sco.message.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/ga.js"/>"></script>

<script type="text/javascript">
$(function() {
	

	function preview1(file) {
		widthpreview = 0;
		heightpreview = 0;
		img1 = new Image(), url = img1.src = URL.createObjectURL(file)
		img2 = new Image(), url = img2.src = URL.createObjectURL(file)
		img3 = new Image(), url = img3.src = URL.createObjectURL(file)
		uri = null;
		$img1 = $(img1)
		$img2 = $(img2)
		$img3 = $(img3)
		img1.onload = function() {
			widthpreview = img1.width;
			heightpreview = img1.height;
			uri = img1.src;
			
			elem1 = document.querySelector("#cutimage1");
			elem2 = document.querySelector("#cutimage2");
			elem3 = document.querySelector("#cutimage3");
			canvas1 = elem1.getContext('2d');
			canvas1.fillStyle="#FFFFFF";
			canvas2 = elem2.getContext('2d');
			canvas3 = elem3.getContext('2d');
			image = new Image();
			canvas1.clearRect(0, 0, elem1.width, elem1.height);
			canvas1.drawImage(img1, 0, 0, elem1.width, elem1.height);
			canvas1.save();

			if (elem2.width > elem2.height) {
				canvas2.clearRect(0, 0, elem2.width, elem2.height);
				canvas2.drawImage(img1, 0, 0, elem2.width,
						elem2.height);
				canvas2.save();
			} else {
				canvas2.clearRect(0, 0, elem2.width, elem2.height);
				canvas2.drawImage(img1, 0, 0, elem2.width,
						elem2.height);
				canvas2.save();
			}

			if (elem3.width > elem3.height) {
				canvas3.clearRect(0, 0, elem3.width, elem3.height);
				canvas3.drawImage(img1, -0.1 * elem3.width, 0, elem3.width,
						elem3.height);
				canvas3.save();
			} else {
				canvas3.clearRect(0, 0, elem3.width, elem3.height);
				canvas3.drawImage(img1, 0, 0.1 * elem3.height, elem3.width,
						elem3.height);
				canvas3.save();
			}
			$("#cutimage1").css("top", 0).css("left", 0).width(200).height(150);
			$("#cutimage2").css("top", 0).css("left", 0).width(100).height(100);
			$("#cutimage3").css("top", 0).css("left", 0).width(60).height(60);
			URL.revokeObjectURL(url)
			//$('#preview1').empty().append($img1)
			//$('#preview2').empty().append($img2)
			//$('#preview3').empty().append($img3)

		}

	}
	function msg(){
			    e.preventDefault();
			    $.scojs_message('保存成功', $.scojs_message.TYPE_OK);
		
	}

		$('[type=file]').change(function(e) {
			$('#preview').css({
				display : 'inline',
				border : "0px"
			})
			var file = e.target.files[0];
			preview1(file);

		});

		$("#savecanvas").click(
				function() {
					elem4 = document.querySelector("#cutimage1");
					elem5 = document.querySelector("#cutimage2");
					elem6 = document.querySelector("#cutimage3");
					var info1 = elem4.toDataURL("image/jpg");
					var data1 = info1.replace(/\+/g,"_");
					var info2 = elem5.toDataURL("image/jpg");
					var data2 = info2.replace(/\+/g,"_");
					var info3 = elem6.toDataURL("image/jpg");
					var data3 = info3.replace(/\+/g,"_");
					//alert(data1.substring(0, 1500));
					var dataAll={"data1":data1,"data2":data2,"data3":data3}; 

					//var pos=0;
					// for(var i = 0; i < 320; i++) {
					//     var tmp = parseInt(col1[i]);
					//     saveimg1.data[pos + 0] = (tmp >> 16) & 0xff;
					//     saveimg1.data[pos + 1] = (tmp >> 8) & 0xff;
					//    saveimg1.data[pos + 2] = tmp & 0xff;
					//     saveimg1.data[pos + 3] = 0xff;
					//     pos+= 4;
					// }
					//  if (pos >= 4 * 320 * 240) {
					///canvas1.putImageData(saveimg1, 0, 0);
					// 利用Ajax将数据post到服务器端
// 					$.ajax({
// 						type : "post",
// 						url : "upload.htm",
// 						processData : false,
// 						data : {
// 							name : "John"
// 						}
// 					});
					$.ajaxSetup({cache:false})
					$.ajax({
						type:"post",
						url:"upload.htm",
						data:"dataAll="+JSON.stringify(dataAll),
						//dataType:"JSON",
						success:function(data){
							location.reload();
							history.go(0);
							 window.navigate(location);
							 
	 						//msg();
	 						//alert('sus');
	 						
	 		                 
	 						//json = eval("(" + json + ")");
	 						
							
							//alert(data);
							//var path=data.split('%');
							//path1=path[0];
							
						//	path2=path[1];
						//	path3=path[2];
							//var curWwwPath=window.document.location.href;
						    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
						 ///   var pathName=window.document.location.pathname;
						//    var pos=curWwwPath.indexOf(pathName);
						//    //获取主机地址，如： http://localhost:8083
						//    var localhostPaht=curWwwPath.substring(0,pos);
						///    //获取带"/"的项目名，如：/uimcardprj
						//    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
						//   
						//    path1='microlecture/pic/imagehead/xox1.jpg';
						//	path11=projectName+path1;
							
						//	alert(path1);
						//	console.log(path1);
							//alert(path1);
							//alert(path2);
							
			               //$("#userimg1").attr("src",path1);
			              // $("#userimg2").attr("src",path2);
			             //  $("#userimg3").attr("src",path3);
			                     
			              // $("#xxx").text(data.hobby);
			                //return data;
			            },
			            error : function(textStatus,e){
			                alert("提交失败");
			            }
					});
					
					
					//$.post("upload.htm", {type: "data1", saveimg1:canvas1.toDataURL("image/png")},function(data,statusText){
					//    $('#showsavesuccess').empty();
					//    $('#showsavesuccess').append("服务器响应："+statusText+"<br/>");
					//    $('#showsavesuccess').append(data);
					//    },"html"
					// );
					//  pos = 0;
					//{type: "data1", saveimg1:canvas1.toDataURL("image/png")} {imgxx:"shit"}
					// }

					//  function cut(){
					//var elem = document.querySelector("canvas");
					//var canvas1 = elem.getContext('2d');

					//scale(3.0,1.0);
					//    var image = new Image();
					//  image = $img1;
					//alert(image.width);
					//alert(image.height);

					//image.addEventListener("load",function(){
					//   canvas1.drawImage(image,0,0,elem.width,elem.height)},false
					//);

					//      }
					//  cut();
					//window.addEventListener("load",cut,false);  
					//  function modimage(e){
					//   img=e.target;
					//  canvas.drawImage(img,0,0);
					//var info=canvas.getImageData(0,0,0.5*canvas.width,0.5*canvas.height);
					// var pos;
					//for(x=0;x<=canvas.width;x++){
					//       pos=(info.width*4*y)+(x*4);
					//      info.data[pos]=2
					//      }
					//
					//   }
					//    canvas.putImageData(info,0,0);
					//   }
				});
});	
</script>

</head>
<body class="flats-theme">
    <%
							//User user = (User) session.getAttribute("user");
							//if (user == null) {
						%>
						
						<%
							//} else {
						%>

							<%
								//}
							%>
    
    
    
	<jsp:include page="/jsp/include/head2.jsp"></jsp:include>

	<section class="container clearfix">
		<div id="me" class="me clearfix">
			<div class="main">
				<div class="wrap">
					<div class="main-head">
						<h2>帐户设置</h2>
					</div>

					<div class="mod">

						<div class="tabs clearfix">
							<ul>
    							    <li ><a href="account.htm">个人资料</a></li>
                                    <li class="on"><a href="goaccountavatar.htm">更新头像</a></li>
                                    <li><a href="goaccountPassword.htm">修改密码</a></li>
                                    <li><a href="queryLabel.htm?type=user">添加标签</a></li>
    
  							</ul>
						</div>

						<h2>你当前的头像</h2>
                        
						<div id="view" class="row-fluid" style="">
							<ul class="thumbnails">
								<li class="span3"><a href="#" class="thumbnail" id="view1">
										<img id="userimg1"
										src="<c:url value="${user.headImage.imageLage}"/>"
										class="mrm" />
										
								</a></li>
								<li class="span2"><a href="#" class="thumbnail" id="view2">
										<img id="userimg2"
										src="<c:url value="${user.headImage.imageMid}"/>"
										class="mrm" />
								</a></li>
								<li class="span1"><a href="#" class="thumbnail" id="view3">
										<img id="userimg3"
										src="<c:url value="${user.headImage.imageSmall}"/>" />
								</a></li>
							</ul>
						</div>

						<h2>这个头像我不喜欢，我要换换</h2>

						<p id="showsavesuccess"></p>

						<div id="preview" class="" style="display: none">
							<h2>预览</h2>
							<ul class="thumbnails">
								<li><span class="" id="preview1"
									style="border-style: none; border: 0px">
										<canvas id="cutimage1" width="200"  height="150"></canvas>
								</span></li>
								<li><span class="" id="preview2"
									style="border-style: none; border: 0px">
										<canvas id="cutimage2" width="100"  height="100"></canvas>
								</span></li>
								<li><span class="" id="preview3"
									style="border-style: none; border: 0px">
										<canvas id="cutimage3" width="60"  height="60"></canvas>
								</span></li>
							</ul>
						</div>


						<form class="form" method="post" enctype="multipart/form-data"
							action="">
							<p>
								从你的电脑中选择你喜欢的图片: <br /> <br /> <input type='file' name="avatar"
									size="50" /><br /> <span class="assist-text">你可以上传JPG、JPEG、GIF、PNG或BMP文件。</span>
							</p>
							<p>
								<input type="hidden" name="objectId" id="bb"
									value="ololololololololololololol">

								
							</p>
						</form>
						
						

                       
						<button id="savecanvas" class="btn btn-success">保存</buttton>

					</div>
				</div>
			</div>
			<div class="side">

				<div class="avatar-mod clearfix">
					<a href="#" class="avatar"><img
						src="<c:url value="${user.headImage.imageMid}"/>"></a>
					<div class="infos">
						<div class="nickname">
							<a href="#" title=""><c:out value="${user.nickname}"/></a>
						</div>
						<div class="icons">
							<a class="user-level user-level-6" title="努力升级吧!" href="" target="_blank">${level.lv}级</a>
                         <a class="user-level user-level-6">${level.title}</a>  

						</div>
					</div>
				</div>

				<div class="stats-mod">
					<ul class="user-stats clearfix">
						<li><a href=""><strong><c:out value="${user.credit}"/></strong>学分</a></li>
						<li><a href=""><strong><c:out value="${user.gold}"/></strong>金币</a></li>
					</ul>
				</div>

        <jsp:include page="/jsp/include/leftside.jsp"></jsp:include> 
	  </div>
		</div>
	</section>



	<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
</html>