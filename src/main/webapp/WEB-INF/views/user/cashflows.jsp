<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Transactions</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="/js/cashflow.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<ul class="nav nav-pills nav-fill">
    <li class="nav-item">
        <a class="nav-link" href="/user/main/default">Budget view</a>
    </li>
    <li class="nav-item">
        <a class="nav-link active" href="/user/cashflows">Transactions view</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/user/logout">Logout</a>
    </li>
</ul>
<table class="table table-bordered table-sm">
    <thead class="thead-light">
    <tr>
        <th>Category</th>
        <th>Date</th>
        <th>Outflow</th>
        <th>Inflow</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="cashFlow" items="${cashFlows}">
        <tr>
            <td>${cashFlow.category.name}</td>
            <td>${cashFlow.date}</td>
            <td>${cashFlow.out}</td>
            <td>${cashFlow.in}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form:form modelAttribute="cashFlow" method="post" id="addCashFlow">
    <form:select path="category">
        <form:options items="${categories}" itemValue="id" itemLabel="name"/>
    </form:select>
    <form:input type="date" path="date"/>
    <form:input type="number" step="0.01" min="0.01" path="amount"/>
    <input type="submit" value="Add">
</form:form>
<button id="addTransaction" class="btn btn-primary">Add transaction</button>
</body>
</html>