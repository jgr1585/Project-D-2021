//add html validation to Page

//Add new Check for Date
$.validator.addMethod("minDateToday", function(value, element) {
    let curDate = new Date();
    let inputDate = new Date(value);
    if (inputDate >= curDate) {
        return true;
    } else {
        return false;
    }
}, "Invalid Date!");

//Require at lest n in class
$.validator.addMethod("atLeastNInClass", function (value, element, param) {
    let count = 0;

    $(param.class).each( function() {
            count += $(this).val();
    });

    if (count >= param.min) {
        return true;
    } else {
        return false;
    }

}, "Values are below Minimum!");

//Compare Date
$.validator.addMethod("validateDate", function (value, element) {
    let from = new Date($("#from").val());
    let until = new Date($("#until").val());


    if (from <= until) {
        return true;
    } else {
        return false;
    }

}, "From is after Until!");


$(document).ready(function () {

    //Init Form
    $('form').each(function() {
        $(this).validate();
    });

    //Date not Null
    $("input[type='date']").rules("add", {
        minDateToday: true,
        required: true,
        validateDate: true
    });

    //Require at lest one Category
    $(".roomCat").rules("add", {
        required: true,
        min: 0,
        atLeastNInClass: {
            class: ".roomCat",
            min: 1
        }
    });

});