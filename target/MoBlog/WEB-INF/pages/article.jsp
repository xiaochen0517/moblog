<%--
  Created by IntelliJ IDEA.
  User: 28270
  Date: 2020/2/2
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>后台管理系统-主页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <style rel="stylesheet">
        .tableBody > tr > td {
            vertical-align: middle !important;
        }
    </style>
    <script type="text/javascript">
        //上一页
        function lastPage(page){
            if (page>0){
                window.location.href= "${pageContext.request.contextPath}/admin/articlepage?page="+page+"&userid=${userid}";
            }else{
                alert("已到第一页！")
            }
            return null;
        }

        // 下一页
        function afterPage(page) {
            if (page<=${allpage}){
                window.location.href= "${pageContext.request.contextPath}/admin/articlepage?page="+page+"&userid=${userid}";
            }else{
                alert("已到最后一页！")
            }
            return null;
        }
    </script>
</head>
<body>
<%@include file="userheadbar.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-2">
            <%@include file="userleftbar.jsp" %>
        </div>
        <div class="col-md-10">
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>标题</th>
                    <th>发布时间</th>
                    <th>浏览数</th>
                    <th>点赞数</th>
                    <th>分类</th>
                    <th>标签</th>
                    <th></th>
                </tr>
                </thead>
                <tbody class="tableBody">
                <c:forEach items="${articles}" var="article" varStatus="i">
                    <tr>
                        <td>${i.count}</td>
                        <td>${article.title}</td>
                        <td>${article.publisht}</td>
                        <td>${article.browse}</td>
                        <td>${article.like}</td>
                        <td>${article.sortname}</td>
                        <td>${article.label}</td>
                        <td>
                            <button type="button" class="btn btn-success" onclick="showUserinfo(${user.id})">查看</button>
                            <button type="button" class="btn btn-danger">删除</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li>
                        <a class="lastPage" onclick="lastPage(${page-1})" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <c:forEach begin="1" end="${allpage}" var="i">
                        <li
                                <c:if test="${i == page}">
                                    class="active"
                                </c:if>
                        ><a href="${pageContext.request.contextPath}/admin/articlepage?page=${i}&userid=${userid}">${i}</a></li>
                    </c:forEach>
                    <li>
                        <a onclick="afterPage(${page+1})" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
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
