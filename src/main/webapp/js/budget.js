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
});
