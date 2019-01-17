<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Budget</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="/js/budget.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<ul class="nav nav-pills nav-fill">
    <li class="nav-item">
        <a class="nav-link active" href="/user/main/default">Budget view</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/user/cashflows">Transactions view</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/user/logout">Logout</a>
    </li>
</ul>
<div class="row">
    <div class="col-sm-3">
        <select name="months" id="monthSelect">
            <c:forEach var="month" items="${availableMonths}">
                <option value="${month.key}">${month.value}</option>
            </c:forEach>
        </select></div>
    <div class="col-sm-3">
        <h5>Total budgeted this month: ${budgeted}</h5>
    </div>
    <div class="col-sm-3">
        <div><h5>Present account balance: ${balance}</h5></div>
    </div>
    <div class="col-sm-3">
        <input form="bigForm" type="submit" class="btn btn-primary" value="Save changes">
    </div>
</div>

<form id="bigForm" method="post">
    <table class="table table-bordered table-sm">
        <thead class="thead-light">
        <tr>
            <th>Category</th>
            <th>Budgeted</th>
            <th>Spending</th>
            <th>Available</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${records}">
            <tr>
                <td><input class="edit form-control" type="text" value="${record.category.name}"
                           name="category-${record.category.id}"></td>
                <td><input class="edit form-control" type="number" step="0.01" min="0" value="${record.budgetedAmount}"
                           name="record-${record.id}"></td>
                <td>${record.spending}</td>
                <td>${record.available}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>
<form:form modelAttribute="category" method="post" id="addCategoryForm" action="/user/addCategory">
    <form:input type="text" path="name"/>
    <input class="btn btn-primary" type="submit" value="Add">
    <input type="hidden" name="month" value="${month}">
</form:form>
<button id="addCategoryButton" class="btn btn-primary">Add Category</button>
</body>
</html>
