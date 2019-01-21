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
        var input = $('<input>')
            .attr("type", "text")
            .attr("value", $(this).text())
            .attr("required", true)
            .attr("id", $(this).attr("id"))
            .appendTo($(this).parent());
        input.on("blur", function() {
            $.ajax({
                url : '/user/main/edit',
                dataType: "json",
                type: "POST",
                data : {
                    changed : "category",
                    value : $(this).val(),
                    id : $(this).attr("id")
                },
                success : function(result) {
                    $('<span>')
                        .attr("class", "editableCategory")
                        .attr("id", input.attr("id"))
                        .text(result.categoryName)
                        .appendTo(input.parent());
                    input.remove();
                }
            });
        });
        $(this).remove();
    });
    $(".editableAmount").on("click", function () {
        var input = $('<input>')
            .attr("type", "number")
            .attr("min", 0)
            .attr("step", 0.01)
            .attr("id", $(this).attr("id"))
            .attr("value", $(this).text())
            .appendTo($(this).parent());
        input.on("blur", function () {
            $.ajax({
                url : '/user/main/edit',
                dataType: "json",
                type: "POST",
                data : {
                    changed : "amount",
                    value : $(this).val(),
                    id : $(this).attr("id")
                },
                success : function(result) {
                    var id = input.attr("id");
                    $('<span>')
                        .attr("class", "editableAmount")
                        .attr("id", id)
                        .text(result.budgeted)
                        .appendTo(input.parent());
                    input.remove();
                    var available = $('<span>').text(result.available);
                    $("#available-"+id).parent().append(available);
                    $("#available-"+id).remove();
                    available.attr("id", "available-"+id);
                    $("#totalBudgeted").text("Total budgeted this month: "+result.totalBudgeted);
                }
            });
        })
        $(this).remove();
    });
});
