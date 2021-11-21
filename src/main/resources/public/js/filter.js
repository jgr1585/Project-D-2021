/**
 * Created by TeamD
 */

function filterCollapsible(elems, from, until) {
    if (elems != null && from != null && until != null) {
        console.log(from + " || " + until);

        let minUnixTS = Date.parse(from);
        let maxUnixTS = Date.parse(until);

        console.log(minUnixTS + " || " + maxUnixTS);

        for (let i = 0; i < elems.length; i++) {
            let $el = $(elems[i]);
            console.log($el);

            let $timeRange = $el.find(".timeRange");

            let fromText = $timeRange.find(".from").text();
            let toText = $timeRange.find(".to").text();

            // default checks
            if (fromText != null && fromText !== "" && toText != null && toText !== "") {
                console.log(fromText);

                let fromUnixTS = Date.parse(fromText);

                console.log(fromUnixTS);

                if (!unixTSInBetween(minUnixTS, maxUnixTS, fromUnixTS)) {
                    console.log("hide");
                    $el.addClass("displayNone");
                } else {
                    console.log("show");
                    $el.removeClass("displayNone");
                }
            }
        }
    } else {
        console.log("Wrong params.");
    }
}

function unixTSInBetween(start, end, check) {
    return check > start && check < end;
}