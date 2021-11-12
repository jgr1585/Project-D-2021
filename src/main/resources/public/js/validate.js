//add html validation to Page



$(document).ready(function () {
    //Date not Null
    $("#from #until").validate({
       rules: {
           field: {
               required: true
           }
       }
    });

    //Require at lest one Category
});