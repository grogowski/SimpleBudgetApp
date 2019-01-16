<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Register</title>
    <!-- Bootstrap core CSS-->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin.css" rel="stylesheet">
</head>
<body class="bg-dark">
<%--<form:form modelAttribute="user" method="post">--%>
    <%--<label>Email</label><br>--%>
    <%--<form:input path="email"/><br>--%>
    <%--<form:errors path="email"/><br>--%>
    <%--<label>Password</label><br>--%>
    <%--<form:input type="password" path="password"/><br>--%>
    <%--<label>Repeat password</label><br>--%>
    <%--<input type="password" name="repeated"/><br>--%>
    <%--<input type="submit" value="Register">--%>
<%--</form:form>--%>

<div class="container">
    <div class="card card-register mx-auto mt-5">
        <div class="card-header">Register an Account</div>
        <div class="card-body">
            <form:form modelAttribute="user" method="post">
                <div class="form-group">
                    <div class="form-label-group">
                        <form:input path="email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required="required"/>
                        <label for="inputEmail">Email address</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-row">
                        <div class="col-md-6">
                            <div class="form-label-group">
                                <form:input type="password" path="password" id="inputPassword" class="form-control" placeholder="Password" required="required"/>
                                <label for="inputPassword">Password</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-label-group">
                                <input type="password" name="repeated" id="confirmPassword" class="form-control" placeholder="Confirm password" required="required">
                                <label for="confirmPassword">Confirm password</label>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="submit" value="Register" class="btn btn-primary btn-block">
            </form:form>
        </div>
    </div>
</div>


<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>
</body>
</html>
