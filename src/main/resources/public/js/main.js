function swapForm(x) {
    let radioName = document.getElementsByName(x.name);
    for (let i = 0; i < radioName.length; i++) {
        document.getElementById(radioName[i].id.concat("Form")).style.display = "none";
    }
    document.getElementById(x.id.concat("Form")).style.display = "initial";
}

$(document).ready(function () {
    // select Active Page in Navbar
    let page = document.querySelector('meta[name="page"]').content;
    $("#nav" + page).addClass("active");

    let elems = $('select');
    M.FormSelect.init(elems, {});

    //Enable Collapsible
    elems = $('.collapsible');
    M.Collapsible.init(elems, {});

    //Enable Tooltips
    elems = $('.tooltipped');
    M.Tooltip.init(elems, {});

    //Disable click Event for Underlying Element in Class: disableClickThrow
    $('.disableClickThrow').click((event) => event.stopPropagation());
});

function CopyValues() {
    // let RepresentativeIsChecked = document.getElementById('RepresentativeCheck');
    let RepresentativeIsChecked = document.querySelectorAll('input[name="RepresentativeCheck"]:checked');

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

    if (RepresentativeIsChecked) {

        ['input', 'change'].forEach(evt =>
            window.addEventListener(evt, () => {
                RepFirstName.value = GuestFirstName.value;
                RepLastName.value = GuestLastName.value;
                RepStreet.value = GuestStreet.value;
                RepCity.value = GuestCity.value;
                RepCountry.value = GuestCountry.value;
                RepZip.value = GuestZip.value;

                M.updateTextFields();
            })
        );
    } else {
        //event listener muss noch removed werden?
        //window.add.... ist vl nicht optimal

        RepFirstName.value = "";
        RepLastName.value = "";
        RepStreet.value = "";
        RepCity.value = "";
        RepCountry.value ="";
        RepZip.value = "";
    }
}