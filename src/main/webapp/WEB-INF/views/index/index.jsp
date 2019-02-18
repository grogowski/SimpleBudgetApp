<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Simple Budget App</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
    <link rel="stylesheet" href="css/index.css"/>
</head>
<body>
<div id="home">
    <!-- Start Navigation -->
    <nav class="navbar navbar-dark bg-dark navbar-expand-md fixed-top">
        <div class="container-fluid">
            <a class="navbar-brand">Simple Budget App</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#menu">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="menu">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#about">About</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/register">Register</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Login</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav><!-- End Navigation -->

    <!-- Landing Page -->
    <div class="landing">
        <div class="home-wrap">
            <div class="home-inner"></div>
        </div>
    </div>

    <!-- Landing Page Caption-->
    <div class="caption center-block text-center">
        <h2 class="pb-2">Start budgeting with Simple Budget App!</h2>
        <a id="start_button" class="btn btn-outline-light py-2 px-3" href="/register">Get started</a>
    </div>

</div> <!-- End Home Section -->

<!-- Start About Section -->
<div id="about">
    <div class="jumbotron m-0">
        <h3 class="heading">About</h3>
        <div class="row">
            <div class="col-md-4 text-center">
                <h4>Purpose</h4>
                <p>
                    This app is meant as a simple, user-friendly tool for budgeting. Plan your expenses, monitor execution of your
                    plan, keep your past plans as a reference.
                </p>
            </div>
            <div class="col-md-4 text-center">
                <h4>Who is it for?</h4>
                <p>
                    Do you normally find yourself out of money before your paycheck arrives no matter how high your income is? Is
                    your savings account still at 0 even though you promised yourself to start saving numerous times? Are you
                    constantly surprised how much you spend and wonder where does this money go? If your answer to any of this
                    questions is "yes" you should give it a try.
                </p>
            </div>
            <div class="col-md-4">
                <h4>Languages / frameworks / libraries</h4>
                <ul>
                    <li>Java 8</li>
                    <li>Spring MVC</li>
                    <li>Hibernate</li>
                    <li>JBCrypt</li>
                    <li>jQuery</li>
                    <li>MySQL</li>
                    <li>JSP, JSTL</li>
                </ul>
            </div>
        </div>
    </div>
</div><!-- End About Section -->

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
