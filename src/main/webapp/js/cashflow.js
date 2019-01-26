$(document).ready(function () {
    $("#transactions").addClass("active");
    $("#addCashFlow").hide();
    $("#addTransaction").on("click", function () {
        $(this).hide();
        $("#addCashFlow").show();
    });
    $(".editableIn").on("click", function () {
        var originalRow = $(this);
        var inputRow = originalRow.clone();
        originalRow.hide();
        inputRow.insertAfter(originalRow);
        var date = inputRow.find(".date");
        var inputDate = $('<input>')
            .attr("type", "date")
            .attr("name", "date")
            .attr("value", date.text())
            .insertAfter(date);
        date.remove();
        var amountIn = inputRow.find(".in");
        var inputIn = $('<input>')
            .attr("type", "number")
            .attr("min", 0).attr("step", 0.01)
            .attr("name", "in")
            .attr("value", amountIn.text())
            .insertAfter(amountIn);
        amountIn.remove();
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
        $("<input>").attr("name", "out").val("0").hide().appendTo(buttonRow.find("td"));
        $("<input>").attr("name", "categoryId").val("0").hide().appendTo(buttonRow.find("td"));
        cancelButton.on("click", function () {
            originalRow.show();
            inputRow.remove();
            buttonRow.remove();
        })
    });
    $(".editableOut").on("click", function () {
        var originalRow = $(this);
        var inputRow = originalRow.clone();
        originalRow.hide();
        inputRow.insertAfter(originalRow);
        var name = $(this).attr("id");
        var category = inputRow.find(".category");
        var inputCategory = $('#category')
            .clone()
            .attr("name", "categoryId")
            .val(category.attr("id"))
            .appendTo(category.parent());
        $("option[value='']", inputCategory).remove();
        category.remove();
        var date = inputRow.find(".date");
        var inputDate = $('<input>')
            .attr("type", "date")
            .attr("name", "date")
            .attr("value", date.text())
            .insertAfter(date);
        date.remove();
        var amountOut = inputRow.find(".out");
        var inputOut = $('<input>')
            .attr("type", "number")
            .attr("min", 0).attr("step", 0.01)
            .attr("name", "out")
            .attr("value", amountOut.text())
            .insertAfter(amountOut);
        amountOut.remove();
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
        $("<input>").attr("name", "in").val("0").hide().appendTo(buttonRow.find("td"));
        cancelButton.on("click", function () {
            originalRow.show();
            inputRow.remove();
            buttonRow.remove();
        })
    });
});
