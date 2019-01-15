<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th colspan="3">Records</th>
    </tr>
    <tr>
        <th>Category</th>
        <th>Budgeted</th>
        <th>Spending</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="record" items="${records}">
        <tr>
            <td>${record.category.name}</td>
            <td>${record.budgetedAmount}</td>
            <td>${record.spending}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
