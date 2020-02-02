<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
</head>
<body>
<%@include file="userheadbar.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-2">
            <%--侧边导航--%>
            <%@include file="userleftbar.jsp" %>
        </div>
        <div class="col-md-10">
            <div class="jumbotron">
                <div class="row">
                    <div class="col-md-4 col-md-offset-2">
                        <img height="80px" src="${pageContext.request.contextPath}/images/mop.png" alt="..." class="img-circle">
                    </div>
                    <div class="col-md-6">
<%--                        <br>--%>
                        <h3>昵称：${account.nickname}</h3>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="colo-md-10 col-md-offset-2">
                        <p>id:${account.uid}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="colo-md-10 col-md-offset-2">
                        <p>地址:${account.address}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="colo-md-10 col-md-offset-2">
                        <p>邮箱:${account.email}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="colo-md-10 col-md-offset-2">
                        <p>电话:${account.tel}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="colo-md-10 col-md-offset-2">
                        <p>介绍:${account.introduce}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"/>
</body>
</html>
