<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Transactions</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="/js/cashflow.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="/css/budget.css" rel="stylesheet">
</head>
<body>
<%@ include file="header_menu.jspf" %>
<form id="bigForm" action="/user/editCashFlow" method="post">
    <div class="table-responsive">
        <table class="table table-bordered table-sm">
            <thead class="thead-light">
            <tr>
                <th>Category</th>
                <th>Date</th>
                <th>Outflow</th>
                <th>Inflow</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cashFlow" items="${cashFlows}">
                <tr class="${cashFlow.inflow == true ? 'editable in' : 'editable out'}" id="${cashFlow.id}">
                    <td>
                        <span class="category" id="${cashFlow.category.id}">${cashFlow.category.name}</span>
                    </td>
                    <td>
                        <span class="date">${cashFlow.date}</span>
                    </td>
                    <td>
                        <span class="out">${cashFlow.inflow == true ? '' : cashFlow.amount}</span>
                    </td>
                    <td>
                        <span class="in">${cashFlow.inflow == true ? cashFlow.amount : ''}</span>
                    </td>
                    <td><a href="/user/deleteCashFlow/${cashFlow.id}" class="btn btn-primary">Delete</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>
<form:form modelAttribute="cashFlow" method="post" id="addCashFlow" action="/user/addCashFlow">
    <select id="inCategory">
        <c:forEach var="category" items="${inCategories}">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select>
    <select id="outCategory">
        <c:forEach var="category" items="${outCategories}">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select>
    <form:input type="date" path="date"/>
    <form:input type="number" step="0.01" min="0.01" path="amount" placeholder="amount"/>
    <form:input id="inflowCheck" path="inflow" type="hidden"/>
    <input type="submit" value="Add">
</form:form>
<button id="addIncome" class="btn btn-primary">Add income</button>
<button id="addSpending" class="btn btn-primary">Add spending</button>
</body>
</html>
