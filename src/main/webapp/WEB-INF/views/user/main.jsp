<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Budget</title>
</head>
<body>
<h2>Total budgeted: ${budgeted}</h2>
<h2>Balance: ${balance}</h2>
<select name="months" id="monthSelect">
    <c:forEach var="month" items="${availableMonths}">
        <option value="${month.key}">${month.value}</option>
    </c:forEach>
</select>
<form method="post">
    <table>
        <thead>
        <tr>
            <th colspan="4">Records</th>
        </tr>
        <tr>
            <th colspan="4"><input type="submit" value="Update"></th>
        </tr>
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
                <td><input class="edit" type="text" value="${record.category.name}"
                           name="category-${record.category.id}"></td>
                <td><input class="edit" type="number" step="0.01" min="0" value="${record.budgetedAmount}"
                           name="record-${record.id}"></td>
                <td>${record.spending}</td>
                <td>${record.available}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/js/budget.js"></script>
</body>
</html>
