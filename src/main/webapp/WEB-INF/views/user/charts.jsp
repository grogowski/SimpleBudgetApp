<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Charts</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.js"></script>
    <script src="/js/charts.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
          integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<%@ include file="header_menu.jspf" %>
<div class="row">
    <div class="col-lg-6">
        <div class="card my-3 ml-3">
            <div class="card-header">
                Month
            </div>
            <div class="card-body">
                <select id="monthSelect" data="${displayedMonth}">
                    <c:forEach var="month" items="${availableMonths}">
                        <option value="${month.key}">${month.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="col-lg-6">
        <div class="card my-3 mr-3">
            <div class="card-header">
                Chart type
            </div>
            <div class="card-body">
                <select id="chartSelect" data="${displayedMonth}">
                        <option value="bar" selected>Bar</option>
                        <option value="pie">Pie</option>
                </select>
            </div>
        </div>
    </div>
</div>
<div id="barDiv" class="card mb-3 mx-3">
    <div class="card-header">
        <i class="fas fa-chart-bar"></i>
        Budgeted/Spending Bar Chart</div>
    <div class="card-body">
        <canvas id="barChart" width="100%" height="35"></canvas>
    </div>
    <div class="card-footer small text-muted"></div>
</div>
<div id="pieDiv" class="row">
    <div class="col-lg-6">
        <div class="card mb-3 ml-3">
            <div class="card-header">
                <i class="fas fa-chart-pie"></i>
                Budgeted Pie Chart</div>
            <div class="card-body">
                <canvas id="budgetedChart" width="100%" height="75"></canvas>
            </div>
            <div class="card-footer small text-muted"></div>
        </div>
    </div>
    <div class="col-lg-6">
        <div class="card mb-3 mr-3">
            <div class="card-header">
                <i class="fas fa-chart-pie"></i>
                Spending Pie Chart</div>
            <div class="card-body">
                <canvas id="spendingChart" width="100%" height="75"></canvas>
            </div>
            <div class="card-footer small text-muted"></div>
        </div>
    </div>
</div>
</body>
</html>
