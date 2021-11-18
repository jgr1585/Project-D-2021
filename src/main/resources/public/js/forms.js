$(document).ready(function () {
    $("#RepresentativeCheck").change(function () {

        if ($(this).is(':checked')) {

            $("input[type='text']").change(copyFields);
            copyFields();
            $(".guestData").prop("readonly", true);

        } else {
//this is good with jquery
            $("input[type='text']").off("change");
            $(".guestData").val("").prop("readonly", false);

        }
    });
});

function copyFields() {

    $('#privateGuestFirstName').val($('#representativeFirstName').val());
    $('#privateGuestLastName').val($('#representativeLastName').val());
    $('#guestStreet').val($('#representativeStreet').val());
    $('#guestCity').val($('#representativeCity').val());
    $('#guestCountry').val($('#representativeCountry').val());
    $('#guestZip').val($('#representativeZip').val());

    M.updateTextFields();
}