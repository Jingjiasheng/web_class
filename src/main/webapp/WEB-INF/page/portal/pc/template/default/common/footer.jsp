<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="footer-inner">
	<div class="footmenu">
		<span class="pc-menu-only">
			<a href="https://github.com/Jingjiasheng/web_class" target="_blank" class="menu-item company-intro">网站介绍</a>
			<span class="dl"></span>
			<a href="https://github.com/Jingjiasheng/web_class" target="_blank" class="menu-item">联系方式</a>
			<span class="dl"></span>
		</span>
		<a href="https://github.com/Jingjiasheng/web_class" rel="nofollow" target="_blank" class="menu-item">帮助与反馈</a>
		<span class="dl"></span>
		<a href="https://github.com/Jingjiasheng/web_class" rel="nofollow" target="_blank" class="menu-item">侵权投诉</a>
		<c:if test="${!empty webInfo.statisticalCode}">
			<span class="dl"></span>
			<span class="menu-item">${webInfo.statisticalCode}</span>
		</c:if>
		
	</div>
	<p class="copyright">Copyright © 2018 沐课 All Rights Reserved</p>
</div>

<!--回到顶部-->
<div class="alien">
	<div class="feedback">
		<a href="https://github.com/Jingjiasheng/web_class" target="_blank"></a>
	</div>
	<div id="goTopBtn">
		<img width="29" height="65" src="${pageContext.request.contextPath}/static/default/images/goTopBtn.gif">
	</div>
</div>

<script>
	javaex.goTopBtn({
		id : "goTopBtn"
	});
</script>