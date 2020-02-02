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
                <li role="presentation">
                    <a href="${pageContext.request.contextPath}/admin/homepage">主页</a>
                </li>
            </ul>
            <ul class="nav navbar-nav">
                <li role="presentation"
                        <c:if test="${activeitem == 1}">
                            class="active"
                        </c:if>
                ><a href="${pageContext.request.contextPath}/admin/userinfopage?userid=${userid}">用户信息</a></li>
                <li role="presentation"
                        <c:if test="${activeitem == 2}">
                            class="active"
                        </c:if>
                ><a href="${pageContext.request.contextPath}/admin/articlepage?page=1&userid=${userid}">文章管理</a></li>
            </ul>
            <c:if test="${activeitem == 2}">
                <form class="navbar-form navbar-right">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="搜索文章">
                    </div>
                    <button type="submit" class="btn btn-default">搜索</button>
                </form>
            </c:if>
        </div>
    </div>
</nav>
