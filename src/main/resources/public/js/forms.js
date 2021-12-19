/**
 * Created by TeamD
 */

$(document).ready(function () {
    $("#representativeCheck").change(function () {
        if ($(this).is(':checked')) {
            $("input[type='text']").change(function() {
                copyFields(false);
            });

            copyFields(false);
            $(".guestData").prop("readonly", true);
        } else {
            $("input[type='text']").off("change");
            $(".guestData").val("").prop("readonly", false);
        }
    });
});

function copyFields(clear) {
    if (clear) {
        $('#privateGuestFirstName').val("");
        $('#privateGuestLastName').val("");
        $('#guestStreet').val("");
        $('#guestCity').val("");
        $('#guestCountry').val("");
        $('#guestZip').val("");
    } else {
        $('#privateGuestFirstName').val($('#representativeFirstName').val());
        $('#privateGuestLastName').val($('#representativeLastName').val());
        $('#guestStreet').val($('#representativeStreet').val());
        $('#guestCity').val($('#representativeCity').val());
        $('#guestCountry').val($('#representativeCountry').val());
        $('#guestZip').val($('#representativeZip').val());
    }

    M.updateTextFields();
}

function copyOrganizationFields() {
    let dropDownValue = $('#alreadyExistingOrganization').val();

    if (dropDownValue == null || dropDownValue === "") {
        return;
    }

    if (dropDownValue === "noOrganization") {
        $('#organizationName')
            .val("")
            .css('display', false);
        $('#organizationDiscount')
            .val("")
            .css('display', false);
        $('#organizationStreet')
            .val("")
            .css('display', false);
        $('#organizationZip')
            .val("")
            .css('display', false);
        $('#organizationCity')
            .val("")
            .css('display', false);
        $('#organizationCountry')
            .val("")
            .css('display', false);
    } else if (dropDownValue === "addNewOrganization") {
        $('#organizationName').val("");
        $('#organizationDiscount').val("");
        $('#organizationStreet').val("");
        $('#organizationZip').val("");
        $('#organizationCity').val("");
        $('#organizationCountry').val("");
    } else {
        let $organizationEl = $('.' + dropDownValue);

        $('#organizationName').val($organizationEl.find('.organizationName').attr('value'));
        $('#organizationDiscount').val($organizationEl.find('.organizationDiscount').attr('value'));
        $('#organizationStreet').val($organizationEl.find('.organizationStreet').attr('value'));
        $('#organizationZip').val($organizationEl.find('.organizationZip').attr('value'));
        $('#organizationCity').val($organizationEl.find('.organizationCity').attr('value'));
        $('#organizationCountry').val($organizationEl.find('.organizationCountry').attr('value'));
    }

    M.updateTextFields();
}