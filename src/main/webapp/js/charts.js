$(document).ready(function () {
    $("#charts").addClass("active");
    drawChart();
    function drawChart() {
        var myChart = new Chart($("#myChart")[0].getContext("2d") , {
            type: 'bar',
            data: {
                labels: ["Category 1", "Category 2", "Category 3"],
                datasets: [{
                    label: "Budgeted",
                    data: [100, 120, 50],
                },
                    {
                        label: "Spending",
                        data: [95, 150, 50],
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
