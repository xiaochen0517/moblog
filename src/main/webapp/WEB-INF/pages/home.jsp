<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>后台管理系统-主页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <script type="text/javascript">
        window.onload = function () {
            var now = new Date(), hour = now.getHours();
            if (hour < 13) {
                document.getElementById('helloTitle').innerHTML = '上午好，${sessionScope.username}'
            } else {
                document.getElementById('helloTitle').innerHTML = '下午好，${sessionScope.username}'
            }
        }
    </script>
</head>
<body>
<%@include file="headbar.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-2">
            <%--侧边导航--%>
            <%@include file="leftbar.jsp" %>
        </div>
        <div class="col-md-10">
            <div class="jumbotron">
                <h2 id="helloTitle"></h2>
                <p>BY:陌尘吖</p>
                <p><a class="btn btn-primary btn-lg" target="_blank" href="http://www.androidzy.cn/" role="button">进入MoChen`s BLOG</a></p>
            </div>
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <strong>Warning!</strong> Better check yourself, you're not looking too good.
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"/>
</body>
</html>
