<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <style rel="stylesheet">
        #form{
            padding: 20px;
            margin-top: 50px;
            background-color: #00000030;
            border-radius: 10px;
        }
        body{
            background-image: url("${pageContext.request.contextPath}/images/loginbg.jpeg");
        }
    </style>
</head>
<body>
<div class="container">
    <div class="col-md-4"></div>
    <form id="form" class="col-md-4" action="${pageContext.request.contextPath}/admin/login" method="post">
        <div class="form-group">
            <label for="Username">Username</label>
            <input type="text" class="form-control" id="Username" placeholder="Username" name="username">
        </div>
        <div class="form-group">
            <label for="Password">Password</label>
            <input type="password" class="form-control" id="Password" placeholder="Password" name="password">
        </div>
        <div class="form-inline">
            <input type="text" class="form-control" placeholder="vcode" name="vcode">
            <img src="${pageContext.request.contextPath}/util/vcode" alt="vcode">
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox"> Remember Password
            </label>
        </div>
        <button type="submit" class="btn btn-primary col-md-12">Submit</button>
    </form>
    <div class="col-md-4"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"/>
</body>
</html>
