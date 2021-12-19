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

function swapForm(x) {
    if (x.id === "private") {
        document.getElementById("organizationForm").style.display = "none";
        document.getElementById("privateForm").style.display = "block";
        document.getElementById("representativeCheck").disabled = false;

        $('#organizationName').attr('disabled', false);
        $('#organizationDiscount').attr('disabled', false);
        $('#guestStreet').attr('disabled', false);
        $('#guestZip').attr('disabled', false);
        $('#guestCity').attr('disabled', false);
        $('#guestCountry').attr('disabled', false);
        $(".guestData").prop("readonly", false);

        if (document.getElementById("representativeCheck").checked) {
            copyFields(false);
        } else {
            copyFields(true);
        }
    } else {
        document.getElementById("privateForm").style.display = "none";
        document.getElementById("organizationForm").style.display = "block";
        document.getElementById("representativeCheck").disabled = true;

        copyFields(true);
        copyOrganizationFields();
    }
}

function copyOrganizationFields() {
    let dropDownValue = $('#alreadyExistingOrganization').val();

    if (dropDownValue == null || dropDownValue === "") {
        return;
    }

    if (dropDownValue === "addNewOrganization") {
        $('#organizationName').val("");
        $('#organizationDiscount').val("");
        $('#guestStreet').val("");
        $('#guestZip').val("");
        $('#guestCity').val("");
        $('#guestCountry').val("");

        $(".guestData").prop("readonly", false);
    } else {
        let $organizationEl = $('.' + dropDownValue);

        $('#organizationName').val($organizationEl.find('.organizationName').attr('value'));
        $('#organizationDiscount').val($organizationEl.find('.organizationDiscount').attr('value'));
        $('#guestStreet').val($organizationEl.find('.organizationStreet').attr('value'));
        $('#guestZip').val($organizationEl.find('.organizationZip').attr('value'));
        $('#guestCity').val($organizationEl.find('.organizationCity').attr('value'));
        $('#guestCountry').val($organizationEl.find('.organizationCountry').attr('value'));

        $(".guestData").prop("readonly", true);
    }

    M.updateTextFields();
}