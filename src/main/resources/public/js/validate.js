/**
 * Created by TeamD
 */

//add html validation to Page

//Add new Check for Date
$.validator.addMethod("minDateToday", function (value) {
    let curDate = new Date();
    let inputDate = new Date(value);
    curDate.setHours(0, 0, 0, 0);
    inputDate.setHours(0, 0, 0, 0);
    return inputDate >= curDate;
}, "Invalid Date!");

//Require at lest n in class
$.validator.addMethod("atLeastNInClass", function (value, element, param) {
    let count = 0;

    $(param.class).each(function () {
        count += $(this).val();
    });

    return count >= param.min;

}, "Values are below Minimum!");

//Compare Date
$.validator.addMethod("validateDate", function () {
    let from = new Date($("#from").val());
    let until = new Date($("#until").val());


    return from <= until;

}, "From is after Until!");

//Check CreditCard number
$.validator.addMethod("creditCard", function (value, element) {
    if (this.optional(element)) {
        return "dependency-mismatch";
    }

    // Accept only spaces, digits and dashes
    if (/[^0-9 \-]+/.test(value)) {
        return false;
    }

    let nCheck = 0,
        nDigit = 0,
        bEven = false,
        n, cDigit;

    value = value.replace(/\D/g, "");

    // Basing min and max length on
    // https://dev.ean.com/general-info/valid-card-types/
    if (value.length < 13 || value.length > 19) {
        return false;
    }

    for (n = value.length - 1; n >= 0; n--) {
        cDigit = value.charAt(n);
        nDigit = parseInt(cDigit, 10);
        if (bEven) {
            if ((nDigit *= 2) > 9) {
                nDigit -= 9;
            }
        }

        nCheck += nDigit;
        bEven = !bEven;
    }

    return (nCheck % 10) === 0;
}, "Please enter a valid credit card number.");

//Return true if the field value matches the given format RegExp
$.validator.addMethod("pattern", function (value, element, param) {
    if (this.optional(element)) {
        return true;
    }
    if (typeof param === "string") {
        param = new RegExp("^(?:" + param + ")$");
    }
    return param.test(value);
}, "Invalid format.");

//Check if Something is selected
$.validator.addMethod("checkSelectNotAllowed", function (value, element, param) {

    for (let notAllowed of param) {
        if (notAllowed === $(element).val()) {
            return false;
        }
    }

    return true;

}, "Select Something else!");


$(document).ready(function () {

    //Init Form
    $('form').each(function () {
        $(this).validate({
            errorElement: 'span',
            errorClass: 'helper-text'
        });
    });

    //Date not Null
    $("input[type='date']").each(function () {
        $(this).rules("add", {
            minDateToday: true,
            required: true,
            validateDate: true
        });
    });

    //Require at lest one Category
    $(".roomCat").rules("add", {
        min: 0,
        atLeastNInClass: {
            class: ".roomCat",
            min: 1
        }
    });

    //Require every Text input
    $("input[type='text']").each(function () {
        $(this).rules("add", {
            required: true,
            minlength: 3,
            maxlength: 255
        });
    });

    //Phone Number Validation
    $("input[id='representativePhone']").each(function () {
        $(this).rules("add", {
            required: true,
            pattern: "[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$"
        });
    });

    //Zip Code Validation
    $("#representativeZip").rules("add", {
        required: true,
        digits: true
    });

    $("#guestZip").rules("add", {
        required: true,
        digits: true
    })

    //Require E-Mail
    $("input[type='email']").rules("add", {
        required: true,
    });

    //Select a Payment
    $("#payment").rules("add", {
        checkSelectNotAllowed: [
            "Select payment method"
        ]
    });

    $("#representativeCreditCardNumber").rules("add", {
        required: true,
        creditCard: true,
    });

    $("#organizationDiscount").rules("add", {
        required: true,
    });
});