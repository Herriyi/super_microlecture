    <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />
<script type="text/javascript" src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/bootstrap/js/bootstrap.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<script type="text/javascript" src="<c:url value="/js/sea.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/ga.js"/>"></script>
<script type="text/javascript">
	$('.carousel').carousel();
</script>
<title>微课程</title>
</head>
<body class="flats-theme">


	<c:if test="${empty user.userId}">
	<jsp:include page="/jsp/include/head.jsp"></jsp:include>
	</c:if>
	<c:if test="${!empty user.userId}">
		<jsp:include page="/jsp/include/head2.jsp"></jsp:include>
	</c:if>
	
	
	
	
<section class="container channel">

	<div class="channel-header channel-row">
		<h1>
			<a href="">${type}</a>
		</h1>
	</div>
	<div class="channel-row clearfix">
		<div class="channel-main">
			<div class="channel-featured flat">
				
				<div class="featured-slider slider">
            		<div class="slides">
            		
            			<div class="slide cloned" style="left:0px;">
            			<c:forEach items="${clist}" var="clist">
                    		<div class="caption">
                        		<div class="title"><a href="">${clist.course.courseTitle}</a></div>
                        		<div class="description">${clist.course.courseIntro}</div>
                        		<div class="metas">
                           			
                            		<div class="stats">${clist.course.scanNum}查看<span class="mhs">|</span><span>by <a href=""title="">${clist.user.nickname}</a></span></div>
                        		</div>
                    		</div>
                    		</c:forEach>
                    			<a href="" class="cover"><img src="<c:url value="/pic/computer.jpg"/>"></a>
                		</div>
                		
                	</div>
                </div>
				</div>
			</div>
			<div class="channel-side channel-featured-adv"><a href="" target="_blank"><img src="<c:url value="/pic/home/think.jpg"/>" width="300" height="250"></a></div>
		</div>
		
		
		
		
		
		
		
		
	<div class="channel-row channel-straight-row">
		<div class="flat clearfix">
			<h2>热门课程</h2>
			<ul class="cells cells-middle">


				<c:forEach items="${list}" var="list">
				<li class="cell">
					<div class="course-item">
						<div class="thumb">
							<a href="courseDetailPage.htm?courseId=${list.course.courseId}"><img src="<c:url value="/pic/course.jpg"/>"></a>
						</div>
						<p class="title">
							<a href="courseDetailPage.htm?courseId=${list.course.courseId}">${list.course.courseTitle}</a>
						</p>
						<div class="summary">${list.course.courseIntro}</div>
						<p class="metas clearfix">
							<span title="查看次数" style="float: right;"><i class="icon-signal"></i>${list.course.scanNum}</span>
							<span class="fl by">by
								<a href="goPersonnal.htm?userId=${list.user.userId}" class="show-user-card" title="">${list.user.nickname}</a>
							</span>
						</p>
					</div>
				</li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<div class="channel-row">
		<div class="flat clearfix">
			<h2>最新小组话题</h2>
			<div class="channel-main">

				<div class="discuss-list2">
					<ul>
					<c:forEach items="${discusslist}" var="discusslist">
						<li>
							<div class="imageblock clearfix">
								<div class="imageblock-image">
									<a href=""class="show-user-card" ><img src="${discusslist.user.headImage.imageSmall}" title="${discusslist.user.nickname}"></a>
								</div>
								<div class="imageblock-content">
									
									<p class="title">
										<a href="">${discusslist.topic}</a>
									</p>
									<p class="metas">
										<a href="" title="来自『${discusslist.team.teamName}』小组"
											class="mrm">${discusslist.team.teamName}</a> by <a
											href=""
											class="show-user-card"  title="${discusslist.user.nickname}">${discusslist.user.nickname}</a> <span
											class="mhm">${discusslist.scanNum}次查看</span>
											<span class="mls">${discusslist.publishDate}</span>
									</p>
								</div>
							</div>
						</li>
						</c:forEach>
					</ul>

				</div>
			<div class="gray mvm tar">加入你对味的的小组，查看更多话题吧！</div>
			</div>
			<div class="channel-side">
				<h3>${type}小组</h3>
				<ul class="grids smallpic-grids">
					<c:forEach items="${teamlist}" var="teamlist">
						<li class="grid"><a href=""><img src="<c:url value="/pic/team.jpg"/>" title="${teamlist.teamName}" class="thumb"></a>
							<p>
								<a href="" title="${teamlist.teamName}">${teamlist.teamName}</a>
							</p>
						</li>
						</c:forEach>
					</ul>

				<div>
					<a href="createGuidePage.htm">» 申请创建小组</a>
				</div>
			</div>
		</div>
	</div>

	<div class="channel-row channel-straight-row clearfix">
		<div class="flat">
			<div class="clearfix">
				<h2 class="fl">最新课程</h2>
				<a href="" class="fr more">» 更多</a>
			</div>
			<ul class="cells cells-middle">
			<c:forEach items="${list1}" var="list1">
				<li class="cell">
					<div class="course-item">
						<div class="thumb">
							<a href="#"><img src="<c:url value="/pic/course.jpg"/>"></a>
						</div>
						<p class="title">
							<a href="#">${list1.course.courseTitle}</a>
						</p>
						<div class="summary">${list1.course.courseIntro}</div>
						<p class="metas clearfix">
							<span title="查看次数" style="float: right;"><i class="icon-signal"></i>${list1.course.scanNum}</span>
							<span class="fl by">by
								<a href="goPersonnal.htm?userId=${list1.user.userId}" class="show-user-card" title="">${list1.user.nickname}</a>
							</span>
						</p>
					</div>
				</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	
	

	<div class="channel-row">
		<div class="flat clearfix">
			<h3>热门标签</h3>
				
				<div class="tags">
				<c:forEach items="${lablist}" var="lablist">
					<a href="#" class="tag">${lablist.labelName}</a>
					</c:forEach>
				</div>
		</div>
	</div>

	<div class="channel-row">
		<div class="flat clearfix">
			<h2>课程频道</h2>
			<ul class="cells channel-cells">
				<li class="cell"><a href="goMajorHome.htm?type=计算机"><i
						class="channel-icon channel-icon-photography"></i>计算机</a></li>
				<li class="cell"><a href="goMajorHome.htm?type=文学"><i
						class="channel-icon channel-icon-programme"></i>文学</a></li>
				<li class="cell"><a href="goMajorHome.htm?type=艺术"><i
						class="channel-icon channel-icon-interest"></i>艺术</a></li>
				<li class="cell"><a href="goMajorHome.htm?type=经济"><i
						class="channel-icon channel-icon-computer"></i>经济</a></li>
				<li class="cell"><a href="goMajorHome.htm?type=管理"><i
						class="channel-icon channel-icon-language"></i>管理</a></li>
				<li class="cell"><a href="goMajorHome.htm?type=医学"><i
						class="channel-icon channel-icon-life"></i>医学</a></li>
				<li class="cell"><a href="goMajorHome.htm?type=哲学"><i
						class="channel-icon channel-icon-career"></i>哲学</a></li>
				<li class="cell"><a href="goMajorHome.htm?type=体育"><i
						class="channel-icon channel-icon-culture"></i>体育</a></li>
				<li class="cell"><a href="goMajorHome.htm?type=建筑"><i
						class="channel-icon channel-icon-openclass"></i>建筑</a></li>
				<li class="cell"><a href="goMajorHome.htm?type=其他"><i
						class="channel-icon channel-icon-openclass"></i>其他</a></li>
			</ul>
		</div>
	</div>

</section>



<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>

</body>
</html>