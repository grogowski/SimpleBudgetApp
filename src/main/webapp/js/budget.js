$(document).ready(function () {
    var editingOfaValueInProgress=false;
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
    $(".editableCategory").on("click", "span", function () {
        if(!editingOfaValueInProgress) {
            editingOfaValueInProgress=true;
            var originalSpan = $(this);
            var input = $('<input>')
                .attr("type", "text")
                .attr("value", $(this).text())
                .attr("required", true)
                .attr("id", $(this).attr("id"))
                .appendTo($(this).parent())
                .focus();
            var save = $('<i>').addClass("fas fa-check icon green").appendTo($(this).parent());
            var cancel = $('<i>').addClass("fas fa-times icon red").appendTo($(this).parent());
            save.on("click", function () {
                $.ajax({
                    url: '/user/main/edit',
                    dataType: "json",
                    type: "POST",
                    data: {
                        changed: "category",
                        value: input.val(),
                        id: input.attr("id")
                    },
                    success: function (result) {
                        $('<span>')
                            .attr("id", input.attr("id"))
                            .text(result.categoryName)
                            .appendTo(input.parent());
                        input.remove();
                        save.remove();
                        cancel.remove();
                        editingOfaValueInProgress=false;
                    }
                });
            });
            cancel.on("click", function () {
                originalSpan.appendTo($(this).parent());
                input.remove();
                save.remove();
                cancel.remove();
                editingOfaValueInProgress=false;
            });
            $(this).remove();
        }
    });
    $(".editableAmount").on("click", "span", function () {
        if(!editingOfaValueInProgress) {
            editingOfaValueInProgress = true;
            var originalSpan = $(this);
            var input = $('<input>')
                .attr("type", "number")
                .attr("min", 0)
                .attr("step", 0.01)
                .attr("id", $(this).attr("id"))
                .attr("value", $(this).text())
                .appendTo($(this).parent())
                .focus();
            var save = $('<i>').addClass("fas fa-check icon green").appendTo($(this).parent());
            var cancel = $('<i>').addClass("fas fa-times icon red").appendTo($(this).parent());
            save.on("click", function () {
                $.ajax({
                    url: '/user/main/edit',
                    dataType: "json",
                    type: "POST",
                    data: {
                        changed: "amount",
                        value: input.val(),
                        id: input.attr("id")
                    },
                    success: function (result) {
                        var id = input.attr("id");
                        $('<span>')
                            .attr("id", id)
                            .text(result.budgeted)
                            .appendTo(input.parent());
                        input.remove();
                        save.remove();
                        cancel.remove();
                        var available = $('<span>').text(result.available);
                        $("#available-" + id).parent().append(available);
                        $("#available-" + id).remove();
                        available.attr("id", "available-" + id);
                        $("#totalBudgeted").text("Total budgeted this month: " + result.totalBudgeted);
                        editingOfaValueInProgress=false;
                    }
                });
            });
            cancel.on("click", function () {
                originalSpan.appendTo($(this).parent());
                input.remove();
                save.remove();
                cancel.remove();
                editingOfaValueInProgress=false;
            });
            $(this).remove();
        }
    });
});
