$(document).ready(function () {
    $("#monthSelect").on("change", function () {
        var month = $(this).val();
        document.location.href="/user/main/"+month;
    });
    $(".edit").on("change", function () {
       $(this).attr("name", $(this).attr("name") + "-changed");
    });
});
