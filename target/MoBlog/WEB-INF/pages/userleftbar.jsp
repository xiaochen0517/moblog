<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--侧边导航--%>
<ul class="nav nav-pills nav-stacked">
    <li role="presentation"
            <c:if test="${activeitem == 1}">
                class="active"
            </c:if>
    ><a href="${pageContext.request.contextPath}/admin/userinfopage">用户信息</a></li>
    <li role="presentation"
            <c:if test="${activeitem == 2}">
                class="active"
            </c:if>
    ><a href="${pageContext.request.contextPath}/admin/articlepage?page=1&userid=${account.uid}">文章管理</a></li>
</ul>
