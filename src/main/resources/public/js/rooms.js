/**
 * Created by TeamD
 */

$(document).ready(function () {
    let elems = $('.autocomplete');

    insertAutocompleteData(elems);

    $(elems).on("change", function () {
        setTimeout(() => {
            insertAutocompleteData($('.autocomplete'));
        }, 100);
    });
});

function insertAutocompleteData(selects) {

    for (let i = 0; i < selects.length; i++) {
        let $el = $(selects[i]);

        let selectId = $el.attr("id");
        let categoryId = selectId.split("_")[0];

        let options = {
            data: {},
        };

        // set all rooms into dataObject according to the category id
        let rooms = $('.rooms');
        for (let i = 0; i < rooms.length; i++) {
            let $el = $(rooms[i]);

            let id = $el.find(".id").text();

            if ($el.find(".cat-id").text() === categoryId) {
                options.data[id] = null;
            }
        }

        // delete all selected values from other selects
        for (let i = 0; i < selects.length; i++) {
            let $el = $(selects[i]);
            delete options.data[$el.val()];
        }

        // init autocomplete element
        M.Autocomplete.init($el, options);
    }
}