$(document).ready(function () {
    //Select Active Page in Navbar
    let page = document.querySelector('meta[name="page"]').content;
    $("#nav" + page).addClass("active");
});