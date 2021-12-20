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

    copyOrganizationFields();
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
    let $dropDown = $('#alreadyExistingOrganization');

    if (!$dropDown.length) {
        return;
    }

    let dropDownValue = $dropDown.val();

    if (dropDownValue == null || dropDownValue === "") {
        return;
    }

    if (dropDownValue === "noOrganization") {
        clearOrganizationFields();
        showOrganizationFields(false);
    } else if (dropDownValue === "addNewOrganization") {
        clearOrganizationFields();
        disableOrganizationFields(false);
        showOrganizationFields(true);
    } else {
        let $organizationEl = $('.' + dropDownValue);

        $('#organizationName').val($organizationEl.find('.organizationName').attr('value'));
        $('#organizationDiscount').val($organizationEl.find('.organizationDiscount').attr('value'));
        $('#organizationStreet').val($organizationEl.find('.organizationStreet').attr('value'));
        $('#organizationZip').val($organizationEl.find('.organizationZip').attr('value'));
        $('#organizationCity').val($organizationEl.find('.organizationCity').attr('value'));
        $('#organizationCountry').val($organizationEl.find('.organizationCountry').attr('value'));

        disableOrganizationFields(true);
        showOrganizationFields(true);
    }

    M.updateTextFields();
}

function clearOrganizationFields() {
    $('#organizationName').val("");
    $('#organizationDiscount').val("");
    $('#organizationStreet').val("");
    $('#organizationZip').val("");
    $('#organizationCity').val("");
    $('#organizationCountry').val("");
}

function disableOrganizationFields(disabled) {
    $('#organizationName').attr('disabled', disabled);
    $('#organizationDiscount').attr('disabled', disabled);
    $('#organizationStreet').attr('disabled', disabled);
    $('#organizationZip').attr('disabled', disabled);
    $('#organizationCity').attr('disabled', disabled);
    $('#organizationCountry').attr('disabled', disabled);
}

function showOrganizationFields(show) {
    if (show) {
        $('#organizationName').parent().css('display', 'block');
        $('#organizationDiscount').parent().css('display', 'block');
        $('#organizationStreet').parent().css('display', 'block');
        $('#organizationZip').parent().css('display', 'block');
        $('#organizationCity').parent().css('display', 'block');
        $('#organizationCountry').parent().css('display', 'block');
    } else {
        $('#organizationName').parent().css('display', 'none');
        $('#organizationDiscount').parent().css('display', 'none');
        $('#organizationStreet').parent().css('display', 'none');
        $('#organizationZip').parent().css('display', 'none');
        $('#organizationCity').parent().css('display', 'none');
        $('#organizationCountry').parent().css('display', 'none');
    }
}