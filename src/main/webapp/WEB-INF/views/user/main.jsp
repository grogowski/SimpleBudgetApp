<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
<h2>Total budgeted: ${budgeted}</h2>
<h2>Balance: ${balance}</h2>
<table>
    <thead>
    <tr>
        <th colspan="4">Records</th>
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
            <td>${record.category.name}</td>
            <td>${record.budgetedAmount}</td>
            <td>${record.spending}</td>
            <td>${record.available}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
