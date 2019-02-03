$(document).ready(function () {
    $("#charts").addClass("active");
    $("#monthSelect").val($("#monthSelect").attr("data"));
    drawChart();
    function drawChart() {
        $.ajax({
            url: '/user/charts/draw/'+$("#monthSelect").val(),
            dataType: "json",
            type: "POST",
            success: function (result) {
                var myChart = new Chart($("#myChart")[0].getContext("2d") , {
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
            }
        });
    }
    $("#monthSelect").on("change", function () {
        drawChart();
    });
});
