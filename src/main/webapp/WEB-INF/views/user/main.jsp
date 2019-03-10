<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Budget</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
          integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="/js/budget.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="/css/budget.css" rel="stylesheet">
</head>
<body>
<%@ include file="header_menu.jspf" %>
<div class="row row-eq-height text-center">
    <div class="col-md-4">
        <div class="card my-2 mx-2 text-center">
            <div class="card-header">
                <h5>Month</h5>
            </div>
            <div class="card-body">
                <select id="monthSelect" data="${displayedMonth}">
                    <c:forEach var="month" items="${availableMonths}">
                        <option value="${month.key}">${month.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card my-2 mx-2 text-center">
            <div class="card-header">
                <h5>Total budgeted this month</h5>
            </div>
            <div class="card-body">
                <h5 id="totalBudgeted">${budgeted}</h5>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card my-2 mx-2 text-center">
            <div class="card-header">
                <h5>Present account balance</h5>
            </div>
            <div class="card-body">
                <h5>${balance}</h5>
            </div>
        </div>
    </div>
</div>
<div class="table-responsive">
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
                <td class="editableCategory"><span id="${record.category.id}">${record.category.name}</span></td>
                <td class="editableAmount"><span id="${record.id}">${record.budgetedAmount}</span></td>
                <td><span>${record.spending}</span></td>
                <td><span id="available-${record.id}">${record.available}</span></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="row">
    <div class="col">
        <div class="card my-2 mx-2">
            <div class="card-body">
                <form:form class="form-inline" modelAttribute="category" method="post" id="addCategoryForm"
                           action="/user/addCategory">
                    <form:input class="form-control" type="text" path="name" placeholder="New category name"/>
                    <form:input path="inflow" type="hidden" value="false"/>
                    <input class="btn btn-primary" type="submit" value="Add spending category">
                    <input type="hidden" name="displayedMonth" value="${displayedMonth}">
                </form:form>
                <button id="addCategoryButton" class="btn btn-primary">Add spending category</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
