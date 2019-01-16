$(document).ready(function () {
    $("#addCashFlow").hide();
    $("#addTransaction").on("click", function () {
        $(this).hide();
        $("#addCashFlow").show();
    });
});
