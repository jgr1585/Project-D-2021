$(document).ready(function () {
    $("#RepresentativeCheck").change(function () {

        if ($(this).is(':checked')) {

            $("input[type='text']").change(copyFields);
            copyFields();
            $(".guestData").prop("readonly", true);

        } else {

            $("input[type='text']").off("change");
            $(".guestData").val("");
            $(".guestData").prop("readonly", false);

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