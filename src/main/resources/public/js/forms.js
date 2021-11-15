$(document).ready(function () {
    $("#RepresentativeCheck").change(function() {
       let isChecked= $(this).is(':checked');
       
       if (isChecked) {
           $("input[type='text']").change(cloneLine);
           cloneLine();
       } else {
           $("input[type='text']").off("change");
           $(".repData").val("");
       }
       
    });
});

function cloneLine() {
    let GuestFirstName = document.getElementById('privateGuestFirstName');
    let GuestLastName = document.getElementById('privateGuestLastName');
    let GuestStreet = document.getElementById('guestStreet');
    let GuestCity = document.getElementById('guestCity');
    let GuestCountry = document.getElementById('guestCountry');
    let GuestZip = document.getElementById('guestZip');

    let RepFirstName = document.getElementById('representativeFirstName');
    let RepLastName = document.getElementById('representativeLastName');
    let RepStreet = document.getElementById('representativeStreet');
    let RepCity = document.getElementById('representativeCity');
    let RepCountry = document.getElementById('representativeCountry');
    let RepZip = document.getElementById('representativeZip');

    RepFirstName.value = GuestFirstName.value;
    RepLastName.value = GuestLastName.value;
    RepStreet.value = GuestStreet.value;
    RepCity.value = GuestCity.value;
    RepCountry.value = GuestCountry.value;
    RepZip.value = GuestZip.value;

    M.updateTextFields();
}