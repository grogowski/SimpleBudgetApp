<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Simple Budget App</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/index.css">
</head>
<body>
<!-- navbar -->
<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav mr-4">
            <li class="nav-item">
                <a class="nav-link" href="/login">Login</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/register">Register</a>
            </li>
        </ul>
    </div>
</nav>
<header class="header">
    <div class="overlay">
        <div class="container">
            <div class="description">
                <h1>Simple Budget App</h1>
                <a class="btn btn-dark btn-lg" href="#about">See more</a>
            </div>
        </div>
    </div>
</header>
<div id="about" class="px-3 py-3">
    <h2>Purpose:</h2>
    <p>This app is meant as a simple, user-friendly tool for budgeting. Plan your expenses, monitor execution of your
        plan, keep your past plans as a reference.</p>
    <h2>Who is it for?</h2>
    <p>Do you normally find yourself out of money before your paycheck arrives no matter how high your income is? Is
        your savings account still at 0 even though you promised yourself to start saving numerous times? Are you
        constantly surprised how much you spend and wonder where does this money go? If your answer to any of this
        questions is "yes" you should give it a try.</p>
    <h2>Languages / frameworks / libraries:</h2>
    <ul>
        <li>Java 8</li>
        <li>Spring MVC</li>
        <li>Hibernate</li>
        <li>JBCrypt</li>
        <li>MySQL</li>
        <li>JSP, JSTL</li>
    </ul>
</div>
<script type="text/javascript" src='/js/index.js'></script>
</body>
</html>
