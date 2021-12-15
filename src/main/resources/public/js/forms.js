/**
 * Created by TeamD
 */

$(document).ready(function () {
    $("#RepresentativeCheck").change(function () {

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

        document.getElementById("RepresentativeCheck").disabled = false;

        if (document.getElementById("RepresentativeCheck").checked) {
            copyFields(false);
        }
    } else {
        document.getElementById("privateForm").style.display = "none";
        document.getElementById("organizationForm").style.display = "block";

        document.getElementById("RepresentativeCheck").disabled = true;
        copyFields(true);
    }
}

function copyOrganizationFields() {
    let dropDownValue = $('#alreadyExistingOrganization').val();

    if (dropDownValue == null || dropDownValue === "") {
        return;
    }

    if (dropDownValue === "addNewOrganization") {
        $('#organizationName')
            .val("")
            .attr('disabled', false);

        $('#organizationDiscount')
            .val("")
            .attr('disabled', false);

        $('#guestStreet')
            .val("")
            .attr('disabled', false);

        $('#guestZip')
            .val("")
            .attr('disabled', false);

        $('#guestCity')
            .val("")
            .attr('disabled', false);

        $('#guestCountry')
            .val("")
            .attr('disabled', false);
    } else {
        let $organizationEl = $('.' + dropDownValue);

        $('#organizationName')
            .val($organizationEl.find('.organizationName').attr('value'))
            .attr('disabled', true);
        $('#organizationDiscount')
            .val($organizationEl.find('.organizationDiscount').attr('value'))
            .attr('disabled', true);
        $('#guestStreet')
            .val($organizationEl.find('.organizationStreet').attr('value'))
            .attr('disabled', true);
        $('#guestZip')
            .val($organizationEl.find('.organizationZip').attr('value'))
            .attr('disabled', true);
        $('#guestCity')
            .val($organizationEl.find('.organizationCity').attr('value'))
            .attr('disabled', true);
        $('#guestCountry')
            .val($organizationEl.find('.organizationCountry').attr('value'))
            .attr('disabled', true);
    }

    M.updateTextFields();
}