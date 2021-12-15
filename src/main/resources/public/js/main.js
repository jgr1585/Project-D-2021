/**
 * Created by TeamD
 */

const DATE_FORMAT = "yyyy-mm-dd";

function handleDatePickers() {
    let todayDate = new Date();

    let $elems = $(".dpOverview");
    if ($elems.length > 0) {
        let $li = $("#overview").find("li[id$='li']");
        filterCollapsible($li, todayDate, todayDate);

        initDatePicker($elems, {
            format: DATE_FORMAT,
            firstDay: 1,
            minDate: todayDate,
            defaultDate: todayDate,
            setDefaultDate: true,
            autoClose: true,
            onClose: function () {
                let $li = $("#overview").find("li[id$='li']");

                let pickers = syncDatePicker(this, $("#until"), $("#from"));
                if (pickers.from != null && pickers.until != null) {
                    filterCollapsible($li, pickers.from.date, pickers.until.date);
                }
            },
        });
    }

    $elems = $(".dpChooseCategories");
    if ($elems.length > 0) {
        let $fromEl = $("#from");
        let $untilEl = $("#until");

        let options = {
            format: DATE_FORMAT,
            firstDay: 1,
            minDate: todayDate,
            setDefaultDate: true,
            autoClose: true,
            onClose: function () {
                syncDatePicker(this, $("#until"), $("#from"));
            }
        };

        initDatePicker($fromEl, options);

        if ($elems.closest("form").attr("action").includes("checkIn")) {
            $fromEl.addClass("disabledPicker")
                .parent().addClass("disabledPicker");

            options.minDate = M.Datepicker.getInstance($fromEl).date;
            initDatePicker($untilEl, options);
        } else {
            initDatePicker($untilEl, options);
        }
    }
}

function syncDate(picker, pickerToSync) {
    if (picker != null && pickerToSync != null) {
        pickerToSync.setDate(picker.date)
        pickerToSync.$el.val(picker.toString());
    }
}

function syncDatePicker(_this, $until, $from) {
    let fromPicker, untilPicker;

    if (_this != null && $until != null && $from != null) {
        if (_this.$el.attr("id") === "from") {
            fromPicker = _this;
            untilPicker = M.Datepicker.getInstance($until);

            if (fromPicker.date > untilPicker.date) {
                syncDate(fromPicker, untilPicker);
            }
        } else {
            fromPicker = M.Datepicker.getInstance($from);
            untilPicker = _this;

            if (untilPicker.date < fromPicker.date) {
                syncDate(untilPicker, fromPicker);
            }
        }
    }

    return {
        from: fromPicker,
        until: untilPicker,
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
    $(".nav" + page).addClass("active");

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

    // enable
    elems = $('.sidenav');
    M.Sidenav.init(elems, {});
});