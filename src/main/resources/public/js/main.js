/**
 * Created by TeamD
 */

function swapForm(x) {
    let radioName = document.getElementsByName(x.name);
    for (let i = 0; i < radioName.length; i++) {
        document.getElementById(radioName[i].id.concat("Form")).style.display = "none";
    }
    document.getElementById(x.id.concat("Form")).style.display = "initial";
}

function handleDatePickers() {
    let todayDate = new Date();

    let $elems = $('.dpOverview');
    if ($elems.length > 0) {
        let $li = $("#overview").find("li[id$='li']");
        filterCollapsible($li, todayDate, todayDate);

        initDatePicker($elems, {
            format: "dd/mm/yyyy",
            firstDay: 1,
            minDate: todayDate,
            defaultDate: todayDate,
            setDefaultDate: true,
            autoClose: true,
            onClose: function () {
                let $li = $("#overview").find("li[id$='li']");

                let fromPicker, untilPicker;
                if (this.$el.attr("id") === "from") {
                    fromPicker = this;
                    untilPicker = M.Datepicker.getInstance($("#until"));

                    if (fromPicker.date > untilPicker.date) {
                        syncDate(fromPicker, untilPicker);
                    }
                } else {
                    fromPicker = M.Datepicker.getInstance($("#from"));
                    untilPicker = this;

                    if (untilPicker.date < fromPicker.date) {
                        syncDate(untilPicker, fromPicker);
                    }
                }

                filterCollapsible($li, fromPicker.date, untilPicker.date);
            },
        });
    }


    // TODO set default options for create booking and Check in pickers
    // initDatePicker($('.dpOverview'), {});
    // initDatePicker($('.dpOverview'), {});
}

function syncDate(picker, pickerToSync) {
    if (picker != null && pickerToSync != null) {
        pickerToSync.setDate(picker.date)
        pickerToSync.$el.val(picker.toString());
    }
}

function initDatePicker(elems, options) {
    if (elems != null && elems.length > 0 && options != null) {
        M.Datepicker.init(elems, options);
    }
}

$(document).ready(function () {
    // select Active Page in Navbar
    let page = document.querySelector('meta[name="page"]').content;
    $("#nav" + page).addClass("active");

    let elems = $('select');
    M.FormSelect.init(elems, {});

    // enable Collapsible
    elems = $('.collapsible');
    M.Collapsible.init(elems, {});

    // enable Tooltips
    elems = $('.tooltipped');
    M.Tooltip.init(elems, {});

    // enable DatePickers
    handleDatePickers();

    // disable click Event for Underlying Element in Class: disableClickThrow
    $('.disableClickThrow').click((event) => event.stopPropagation());
});