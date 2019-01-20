$(document).ready(function () {
    $("#monthSelect").on("change", function () {
        var month = $(this).val();
        document.location.href="/user/main/"+month;
    });
    $(".edit").one("change", function () {
       $(this).attr("name", $(this).attr("name") + "-changed");
    });
    $("#addCategoryForm").hide();
    $("#addCategoryButton").on("click", function () {
        $(this).hide();
        $("#addCategoryForm").show();
    });
    $(".editableCategory").on("click", function () {
        var name = $(this).attr("id");
        $('<input>').attr("type", "text").attr("name", name).attr("value", $(this).text()).appendTo($(this).parent());
        $(this).remove();
    });
    $(".editableAmount").on("click", function () {
        var name = $(this).attr("id");
        $('<input>').attr("type", "number").attr("min", 0).attr("step", 0.01).attr("name", name).attr("value", $(this).text()).appendTo($(this).parent());
        $(this).remove();
    });
});
