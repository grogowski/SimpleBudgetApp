$(document).ready(function () {
    $("#categories").addClass("active");
    var editingOfaValueInProgress = false;
    var save = $('<i>').addClass("fas fa-check icon green");
    var cancel = $('<i>').addClass("fas fa-times icon red");
    cancel.on("click", function () {
        $(this).siblings("span").show();
        $(this).siblings("input").remove();
        cleanUp();
    });
    function cleanUp() {
        save.remove();
        cancel.detach();
        editingOfaValueInProgress = false;
    }
    $(".editable").on("click", "span", function () {
        if (!editingOfaValueInProgress) {
            editingOfaValueInProgress = true;
            var originalSpan = $(this);
            originalSpan.hide();
            var newCategoryName = $('<input>')
                .attr("type", "text")
                .attr("required", true)
                .val(originalSpan.text())
                .appendTo(originalSpan.parent())
                .focus();
            save.appendTo(originalSpan.parent());
            cancel.appendTo(originalSpan.parent());
            save.on("click", function () {
                $.ajax({
                    url: '/user/editCategoryName',
                    dataType: "json",
                    type: "POST",
                    data: {
                        newCategoryName: newCategoryName.val(),
                        id: originalSpan.attr("id")
                    },
                    success: function () {
                        originalSpan.text(newCategoryName.val()).show();
                        newCategoryName.remove();
                        cleanUp();
                    }
                });
            });
        }
    });
});
