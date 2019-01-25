$(document).ready(function () {
    var editingOfaValueInProgress = false;
    var save = $('<i>').addClass("fas fa-check icon green");
    var cancel = $('<i>').addClass("fas fa-times icon red");
    cancel.on("click", function () {
        $(this).siblings("span").show();
        $(this).siblings("input").remove();
        cleanUp();
    });
    var categoryInput = $('<input>')
        .attr("type", "text")
        .attr("required", true);
    var amountInput = $('<input>')
        .attr("type", "number")
        .attr("min", 0)
        .attr("step", 0.01);

    function cleanUp() {
        save.remove();
        cancel.detach();
        editingOfaValueInProgress = false;
    }

    $("#monthSelect").on("change", function () {
        var month = $(this).val();
        document.location.href = "/user/main/" + month;
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
        if (!editingOfaValueInProgress) {
            editingOfaValueInProgress = true;
            var originalSpan = $(this);
            categoryInput.attr("id", originalSpan.attr("id"))
                .val(originalSpan.text())
                .appendTo(originalSpan.parent())
                .focus();
            save.appendTo(originalSpan.parent());
            cancel.appendTo(originalSpan.parent());
            save.on("click", function () {
                $.ajax({
                    url: '/user/main/edit',
                    dataType: "json",
                    type: "POST",
                    data: {
                        changed: "category",
                        value: categoryInput.val(),
                        id: categoryInput.attr("id")
                    },
                    success: function (result) {
                        originalSpan.text(result.categoryName).show();
                        categoryInput.remove();
                        cleanUp();
                    }
                });
            });
            originalSpan.hide();
        }
    });
    $(".editableAmount").on("click", "span", function () {
        if (!editingOfaValueInProgress) {
            editingOfaValueInProgress = true;
            var originalSpan = $(this);
            amountInput.attr("id", originalSpan.attr("id"))
                .val(originalSpan.text())
                .appendTo($(this).parent())
                .focus();
            save.appendTo($(this).parent());
            cancel.appendTo($(this).parent());
            save.on("click", function () {
                $.ajax({
                    url: '/user/main/edit',
                    dataType: "json",
                    type: "POST",
                    data: {
                        changed: "amount",
                        value: amountInput.val(),
                        id: amountInput.attr("id")
                    },
                    success: function (result) {
                        originalSpan.text(result.budgeted).show();
                        var id = amountInput.attr("id");
                        amountInput.remove();
                        var available = $('<span>').text(result.available);
                        $("#available-" + id).parent().append(available);
                        $("#available-" + id).remove();
                        available.attr("id", "available-" + id);
                        $("#totalBudgeted").text("Total budgeted this month: " + result.totalBudgeted);
                        cleanUp();
                    }
                });
            });
            originalSpan.hide();
        }
    });
});
