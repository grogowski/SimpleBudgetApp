<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<form method="post">
    <label>Email</label><br>
    <input name="email"/><br>
    <label>Password</label><br>
    <input type="password" name="password"/><br>
    <input type="submit" value="Login">
</form>
<a href="/register">Register as new user</a>
</body>
</html>
