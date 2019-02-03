$(document).ready(function () {
    $("#charts").addClass("active");
    $("#monthSelect").val($("#monthSelect").attr("data"));
    drawChart();

    function drawChart() {
        if ($("#chartSelect").val() === "bar") {
            $("#barDiv").show();
            $("#pieDiv").hide();
        } else {
            $("#pieDiv").show();
            $("#barDiv").hide();
        }
        $.ajax({
            url: '/user/charts/draw/' + $("#monthSelect").val(),
            dataType: "json",
            type: "POST",
            success: function (result) {
                if ($("#chartSelect").val() === "bar") {
                    var barChartParent = $("#barChart").parent();
                    $("#barChart").remove();
                    $("<canvas>").attr("id", "barChart").attr("width", "100%").attr("height", "35").appendTo(barChartParent);
                    var myBarChart = new Chart($("#barChart")[0].getContext("2d"), {
                        type: 'bar',
                        data: {
                            labels: result.categories,
                            datasets: [{
                                label: "Budgeted",
                                data: result.budgeted,
                                backgroundColor: "blue"
                            },
                                {
                                    label: "Spending",
                                    data: result.spending,
                                    backgroundColor: "red"
                                }]
                        },
                        options: {
                            scales: {
                                yAxes: [{
                                    ticks: {
                                        beginAtZero: true
                                    }
                                }]
                            }
                        }
                    });
                } else {
                    var budgetedChartParent = $("#budgetedChart").parent();
                    $("#budgetedChart").remove();
                    $("<canvas>").attr("id", "budgetedChart").attr("width", "100%").attr("height", "75").appendTo(budgetedChartParent);
                    var myBudgetedChart = new Chart($("#budgetedChart")[0].getContext("2d"), {
                        type: 'pie',
                        data: {
                            labels: result.categories,
                            datasets: [{
                                label: result.categories,
                                data: result.budgeted,
                                backgroundColor: ["#0074D9", "#FF4136", "#2ECC40", "#FF851B", "#7FDBFF", "#B10DC9", "#FFDC00", "#001f3f", "#39CCCC", "#01FF70", "#85144b", "#F012BE", "#3D9970", "#111111", "#AAAAAA"]
                            }]
                        },
                        options: {
                            scales: {
                                yAxes: [{
                                    ticks: {
                                        beginAtZero: true
                                    }
                                }]
                            }
                        }
                    });
                    var spendingChartParent = $("#spendingChart").parent();
                    $("#spendingChart").remove();
                    $("<canvas>").attr("id", "spendingChart").attr("width", "100%").attr("height", "75").appendTo(spendingChartParent);
                    var mySpendingChart = new Chart($("#spendingChart")[0].getContext("2d"), {
                        type: 'pie',
                        data: {
                            labels: result.categories,
                            datasets: [{
                                label: result.categories,
                                data: result.spending,
                                backgroundColor: ["#0074D9", "#FF4136", "#2ECC40", "#FF851B", "#7FDBFF", "#B10DC9", "#FFDC00", "#001f3f", "#39CCCC", "#01FF70", "#85144b", "#F012BE", "#3D9970", "#111111", "#AAAAAA"]
                            }]
                        },
                        options: {
                            scales: {
                                yAxes: [{
                                    ticks: {
                                        beginAtZero: true
                                    }
                                }]
                            }
                        }
                    });
                }
            }
        });
    }

    $("#monthSelect").on("change", function () {
        drawChart();
    });
    $("#chartSelect").on("change", function () {
        drawChart();
    });
});
