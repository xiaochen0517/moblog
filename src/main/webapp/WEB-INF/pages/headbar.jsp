<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default" style="margin-left: 10px;margin-right: 10px;margin-bottom: 10px">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/homepage">
                Mo
            </a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li role="presentation"
                        <c:if test="${activeitem == 1}">
                            class="active"
                        </c:if>
                ><a href="${pageContext.request.contextPath}/admin/homepage">主页</a></li>
                <li role="presentation"
                        <c:if test="${activeitem == 2}">
                            class="active"
                        </c:if>
                ><a href="${pageContext.request.contextPath}/admin/userpage?page=1">用户管理</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <div class="form-group">
                    <input type="text" class="form-control"
                    <c:choose>
                            <c:when test="${activeitem == 2}">placeholder="搜索用户名"</c:when>
                            <c:otherwise>placeholder="搜索"</c:otherwise>
                    </c:choose>
                    >
                </div>
                <button type="submit" class="btn btn-default">搜索</button>
            </form>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
