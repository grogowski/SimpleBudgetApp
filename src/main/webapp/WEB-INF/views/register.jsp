<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form:form modelAttribute="user" method="post">
    <label>Email</label><br>
    <form:input path="email"/><br>
    <form:errors path="email"/><br>
    <label>Password</label><br>
    <form:input type="password" path="password"/><br>
    <label>Repeat password</label><br>
    <input type="password" name="repeated"/><br>
    <input type="submit" value="Register">
</form:form>
</body>
</html>
