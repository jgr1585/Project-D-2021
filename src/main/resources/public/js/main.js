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