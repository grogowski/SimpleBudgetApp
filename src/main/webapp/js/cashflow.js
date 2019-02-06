$(document).ready(function () {
    $("#transactions").addClass("active");
    $("#addCashFlow").hide();
    var editingOfaValueInProgress = false;
    $("#addIncome").on("click", function () {
        $(this).hide();
        $("#addSpending").hide();
        $("#outCategory").hide();
        $("#inflowCheck").val("true");
        $("#inCategory").attr("name", "category").show();
        $("#addCashFlow").show();
    });
    $("#addSpending").on("click", function () {
        $(this).hide();
        $("#addIncome").hide();
        $("#inCategory").hide();
        $("#outCategory").attr("name", "category").show();
        $("#addCashFlow").show();
    });
    $(".editable").on("click", function () {
        if (!editingOfaValueInProgress) {
            editingOfaValueInProgress = true;
            var originalRow = $(this);
            var inputRow = originalRow.clone();
            originalRow.hide();
            inputRow.insertAfter(originalRow);
            var name = $(this).attr("id");
            var category = inputRow.find(".category");
            if ($(this).hasClass("out")) {
                var inputCategory = $('#outCategory')
                    .clone()
                    .attr("name", "categoryId")
                    .val(category.attr("id"))
                    .appendTo(category.parent());
            } else {
                var inputCategory = $('#inCategory')
                    .clone()
                    .attr("name", "categoryId")
                    .val(category.attr("id"))
                    .appendTo(category.parent());
            }
            category.remove();
            var date = inputRow.find(".date");
            var inputDate = $('<input>')
                .attr("type", "date")
                .attr("name", "date")
                .attr("value", date.text())
                .insertAfter(date);
            date.remove();
            if ($(this).hasClass("out")) {
                var amountOut = inputRow.find(".out");
                var inputOut = $('<input>')
                    .attr("type", "number")
                    .attr("min", 0).attr("step", 0.01)
                    .attr("name", "amount")
                    .attr("value", amountOut.text())
                    .insertAfter(amountOut);
                amountOut.remove();
            } else {
                var amountIn = inputRow.find(".in");
                var inputIn = $('<input>')
                    .attr("type", "number")
                    .attr("min", 0).attr("step", 0.01)
                    .attr("name", "amount")
                    .attr("value", amountIn.text())
                    .insertAfter(amountIn);
                amountIn.remove();
            }
            buttonRow = $("<tr>");
            td = $("<td>").attr("colspan", "5").appendTo(buttonRow);
            buttonRow.insertAfter(inputRow);
            cancelButton = $("<button>")
                .text("Cancel")
                .addClass("btn btn-secondary")
                .appendTo(td);
            saveButton = $("<input>")
                .addClass("btn btn-primary")
                .attr("type", "submit")
                .attr("form", "bigForm")
                .val("Save").appendTo(td);
            $("<input>").attr("name", "id").val(originalRow.attr("id")).hide().appendTo(buttonRow.find("td"));
            cancelButton.on("click", function () {
                originalRow.show();
                inputRow.remove();
                buttonRow.remove();
                editingOfaValueInProgress = false;
            })
        }
    });
});
