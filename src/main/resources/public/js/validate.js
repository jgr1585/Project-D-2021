//add html validation to Page

//Add new Check for Date
$.validator.addMethod("minDateToday", function(value) {
    let curDate = new Date();
    let inputDate = new Date(value);
    return inputDate >= curDate;
}, "Invalid Date!");

//Require at lest n in class
$.validator.addMethod("atLeastNInClass", function (value, element, param) {
    let count = 0;

    $(param.class).each( function() {
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

//Return true if the field value matches the given format RegExp
$.validator.addMethod( "pattern", function( value, element, param ) {
    if ( this.optional( element ) ) {
        return true;
    }
    if ( typeof param === "string" ) {
        param = new RegExp( "^(?:" + param + ")$" );
    }
    return param.test( value );
}, "Invalid format." );


//Check if Something is selected
$.validator.addMethod( "checkSelectNotAllowed", function( value, element, param ) {

    for(let notAllowed of param) {
        if (notAllowed === $(element).val()) {
            return false;
        }
    }

    return true;

}, "Select Something else!" );


$(document).ready(function () {

    //Init Form
    $('form').each(function() {
        $(this).validate();
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
        required: true,
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
            maxlength: 255,
            pattern: "[a-zA-Z\s]+"
        });
    });

    $("input[id='representativePhone']").each(function (){
        $(this).rules("add",{
            required: true,
            pattern:"[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$"
        });
    });

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



});