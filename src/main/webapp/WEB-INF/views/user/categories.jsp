<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Categories</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
          integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="/js/categories.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="/css/budget.css" rel="stylesheet">
</head>
<body>
<%@ include file="header_menu.jspf" %>
<div class="row row-eq-height">
    <div class="col-md-6">
        <div class="card my-3 mx-2 h-100">
            <div class="card-header">
                Income Categories
            </div>
            <div class="card-body">
                <ul>
                    <c:forEach var="inCategory" items="${inCategories}">
                        <li class="editable"><span id="${inCategory.id}">${inCategory.name}</span></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card my-3 mx-2 h-100">
            <div class="card-header">
                Spending Categories
            </div>
            <div class="card-body">
                <ul>
                    <c:forEach var="outCategory" items="${outCategories}">
                        <li class="editable"><span id="${outCategory.id}">${outCategory.name}</span></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <div class="card my-3 mx-2">
            <div class="card-body">
                <button id="addIncomeCategoryButton" class="btn btn-primary">Add income category</button>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card my-3 mx-2">
            <div class="card-body">
                <button id="addSpendingCategoryButton" class="btn btn-primary">Add spending category</button>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col">
        <div class="card my-3 mx-2">
            <div class="card-body">
                <form:form class="form-inline" modelAttribute="category" method="post" id="addCategoryForm"
                           action="/user/addCategory">
                    <form:input class="form-control" type="text" path="name" placeholder="New category name"/>
                    <form:input path="inflow" type="hidden" value="false"/>
                    <input id="subButton" class="btn btn-primary" type="submit" value="Add spending category">
                    <input type="hidden" name="displayedMonth" value="na">
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

