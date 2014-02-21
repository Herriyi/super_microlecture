<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="<c:url value="/pic/icon.ico" />" />
<link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen">

<script type="text/javascript" src="<c:url value="/bootstrap/js/jquery-1.8.3.min.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/css/components.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v2.css"/>">
<link rel="stylesheet" href="<c:url value="/css/site_v3.css"/>">
<script type="text/javascript" src="<c:url value="/js/ga.js"/>"></script>


<title>微课程-首页</title>
</head>
<body class="flats-theme">


	<jsp:include page="/jsp/include/head.jsp"></jsp:include>
	

	<section class="container homepage">

		<div class="flat">
			<ul class="features clearfix">
				<li>
					<div class="feature">
						<i class="feature-icon feature-plan"></i>
						<div class="title">有计划的学习</div>
						<div class="note">一系列学习小工具，让你随时记录学习笔记、掌握学习进度。</div>
					</div>
				</li>
				<li>
					<div class="feature">
						<i class="feature-icon feature-friend"></i>
						<div class="title">找到学习的伙伴</div>
						<div class="note">向老师提问，与同学交流，让学习之路不再寂寞。</div>
					</div>
				</li>
				<li>
					<div class="feature">
						<i class="feature-icon feature-share"></i>
						<div class="title">分享你的知识</div>
						<div class="note">创建课程，让知识在流动中发光！</div>
					</div>
				</li>
			</ul>

			<div class="signup-bar clearfix">
				<div class="saying">
					<div class="text">必须在奋斗中求生存，求发展。</div>
					<div class="speaker">--- 茅盾</div>
				</div>
				<div class="signup-btns">
					<a href="goRegisterPage.htm" class="btn btn btn-large signup-btn">立即注册</a>
					<div class="connects">
						已有帐号登录： <a href=""><img src="./pic/qq_48x48.gif" width="24"
							height="24"></a> <a href=""><img
							src="./pic/weibo_48x48.png" width="24" height="24"></a> <a
							href=""><img src="./pic/renren_48x48.gif" width="24"
							height="24"></a> <a href=""><img
							src="./pic/douban_48x48.gif" width="24" height="24"></a>
					</div>

				</div>
			</div>
		</div>

		<!-- 推荐课程 -->

		<div class="flat clearfix">
			<h2>热门课程</h2>
			<ul class="cells cells-middle">
			<c:forEach items="${list}" var="list">
				<li class="cell">
					<div class="course-item">
						<div class="thumb">
							<a href="courseDetailPage.htm?courseId=${list.course.courseId}"><img src="<c:url value="/pic/daa.jpg"/>"></a>
						</div>
						<p class="title">
							<a href="courseDetailPage.htm?courseId=${list.course.courseId}"><span class="video" title="视频课程"></span>${list.course.courseTitle}</a>
						</p>
						<div class="summary">${list.course.courseIntro}</div>
						<p class="metas clearfix">
							<span title="查看次数" style="float: right;"><i class="icon-signal"></i>${list.course.scanNum}</span>
							<span class="fl by">by
								<a href="#" class="show-user-card" title="">${list.user.nickname}</a>
							</span>
						</p>
					</div>
				</li>
				</c:forEach>
			</ul>
		</div>

		<!-- 最新课程 -->

		<div class="flat clearfix">
			<h2>最新课程</h2>
			<ul class="cells cells-middle">
			<c:forEach items="${list1}" var="list1">
				<li class="cell">
					<div class="course-item">
						<div class="thumb">
							<a href="courseDetailPage.htm?courseId=${list1.course.courseId}"><img src="<c:url value="/pic/course.jpg"/>"></a>
						</div>
						<p class="title">
							<a href="courseDetailPage.htm?courseId=${list1.course.courseId}"><span class="video" title="视频课程"></span>${list1.course.courseTitle}</a>
						</p>
						<div class="summary">${list1.course.courseIntro}</div>
						<p class="metas clearfix">
							<span title="查看次数" style="float: right;"><i class="icon-signal"></i>${list1.course.scanNum}</span>
							<span class="fl by">by
								<a href="#" class="show-user-card" title="">${list1.user.nickname}</a>
							</span>
						</p>
					</div>
				</li>
				</c:forEach>
			</ul>
		</div>

		<!-- 话题、小组 -->


		<div class="flat clearfix">
			<h2>话题、小组</h2>
			<div class="flat-main">
				<h3>最新话题</h3>

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
			</div>

			<!-- 推荐小组 -->


			<div class="flat-side">
				<h3>推荐小组</h3>
				<div class="clearfix">
					<ul class="grids smallpic-grids">
					<c:forEach items="${teamlist}" var="teamlist">
						<li class="grid"><a href=""><img src="<c:url value="${teamlist.headImage.imageSmall}"/>" title="${teamlist.teamName}" class="thumb"></a>
							<p>
								<a href="" title="${teamlist.teamName}">${teamlist.teamName}</a>
							</p>
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>


		</div>
		<!-- 笔记、标签 -->
		<div class="flat clearfix">
			<h2>笔记、标签</h2>
			<div class="flat-main">
				<h3>推荐笔记</h3>
				<ul class="picked-notes">
				<c:forEach items="${notelist}" var="notelist">
					<li class="mbm">
						<div class="title">
							<a href="">${notelist.userCourse.course.courseTitle}的笔记</a>
						</div>
						<div class="summary gray">${notelist.noteContent}</div>
						<div class="metas gray">
							<span class="gray">by</span> <span class="thin mrm"><a
								href="" class="show-user-card"
								 title="${notelist.userCourse.user.nickname}">${notelist.userCourse.user.nickname}</a></span>
						</div>
					</li>
					</c:forEach>
				</ul>
			</div>

			<!-- 热门标签 -->

			<div class="flat-side">
				<h3>热门标签</h3>
				
				<div class="tags">
				<c:forEach items="${lablist}" var="lablist">
					<a href="#" class="tag">${lablist.labelName}</a>
					</c:forEach>
				</div>
				
			</div>

		</div>

		<!-- 课程频道 -->

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

		<!-- 友情链接 -->

		<div class="flat clearfix">
			<h2>友情链接</h2>
			<a href="http://hao.360.cn/" target="_blank" class="mrs">360安全网址导航</a>
			| <a href="http://www.hao123.com/" target="_blank" class="mrs">hao123</a>
			| <a href="http://www.lvse.com/" target="_blank" class="mrs">绿色网</a>
			| <a href="http://www.chinaz.com/" target="_blank" class="mrs">站长之家</a>
			| <a href="http://yn.zwbk.org/" target="_blank" class="mrs">
				云南百科信息网</a> | <a href="http://www.91lx.org/" target="_blank" class="mrs">启德教育</a>
			| <a href="http://hz.jianzhi8.com/" target="_blank" class="mrs">杭州兼职网</a>
			| <a href="http://www.psycofe.com/" target="_blank" class="mrs">生活心理学</a>
			|
		</div>


	</section>



	<jsp:include page="/jsp/include/foot1.jsp"></jsp:include>
</body>
</html>