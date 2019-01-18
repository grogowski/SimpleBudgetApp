$(document).ready(function () {
    $("#addCashFlow").hide();
    $("#addTransaction").on("click", function () {
        $(this).hide();
        $("#addCashFlow").show();
    });
    $(".editableCategory").on("click", function () {
        var name = $(this).attr("id");
        var clone = $('#category').clone().attr("name", name).attr("id", null).attr("value", $(this).text()).appendTo($(this).parent());
        $("option[value='']", clone).remove();
        $(this).remove();
    });
    $(".editableDate").on("click", function () {
        var name = $(this).attr("id");
        $('<input>').attr("type", "date").attr("name", name).attr("value", $(this).text()).appendTo($(this).parent());
        $(this).remove();
    });
    $(".editableOut").on("click", function () {
        var name = $(this).attr("id");
        $('<input>').attr("type", "number").attr("min", 0).attr("step", 0.01).attr("name", name).attr("value", $(this).text()).appendTo($(this).parent());
        $(this).remove();
    });
    $(".editableIn").on("click", function () {
        var name = $(this).attr("id");
        $('<input>').attr("type", "number").attr("min", 0).attr("step", 0.01).attr("name", name).attr("value", $(this).text()).appendTo($(this).parent());
        $(this).remove();
    });
});
