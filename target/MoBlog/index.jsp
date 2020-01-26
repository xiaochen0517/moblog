<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/admin/login" method="post">
    <input type="text" name="username" placeholder="username">
    <br>
    <input type="password" name="password" placeholder="password">
    <br>
    <img src="${pageContext.request.contextPath}/util/vcode">
    <br>
    <input type="text" name="vcode" placeholder="vcode">
    <br>
    <input type="submit" value="submit">
</form>
</body>
</html>
