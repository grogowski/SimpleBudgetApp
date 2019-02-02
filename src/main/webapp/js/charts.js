$(document).ready(function () {
    $("#charts").addClass("active");
    drawChart();
    function drawChart() {
        $.ajax({
            url: '',
            dataType: "json",
            type: "POST",
            data: {

            },
            success: function (result) {
            }
        });
        var myChart = new Chart($("#myChart")[0].getContext("2d") , {
            type: 'bar',
            data: {
                labels: ["Category 1", "Category 2", "Category 3"],
                datasets: [{
                    label: "Budgeted",
                    data: [100, 120, 50],
                    backgroundColor: "blue"
                },
                    {
                        label: "Spending",
                        data: [95, 150, 50],
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
    $("#monthSelect").on("change", drawChart());
});
