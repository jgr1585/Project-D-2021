/**
 * Created by TeamD
 */

function filterCollapsible(elems, from, until) {
    if (elems != null && from != null && until != null) {
        let minUnixTS = from.toDateString != null ? Date.parse(from.toDateString()) : Date.parse(from);
        let maxUnixTS = until.toDateString != null ? Date.parse(until.toDateString()) : Date.parse(until);

        // default date checks
        if (minUnixTS != null && maxUnixTS != null && !Number.isNaN(minUnixTS) && !Number.isNaN(maxUnixTS)) {

            for (let i = 0; i < elems.length; i++) {
                let $el = $(elems[i]);

                let $timeRange = $el.find(".timeRange");

                let fromText = $timeRange.find(".from").text();
                let toText = $timeRange.find(".to").text();

                if (fromText != null && fromText !== "" && toText != null && toText !== "") {

                    let fromUnixTS = Date.parse(new Date(fromText).toDateString());

                    if (!unixTSInBetween(minUnixTS, maxUnixTS, fromUnixTS)) {
                        // console.log("hide");
                        $el.addClass("displayNone");
                    } else {
                        // console.log("show");
                        $el.removeClass("displayNone");
                    }
                }
            }
        } else {
            console.log("Converting Date to unixTS failed.")
        }
    } else {
        console.log("Wrong params.");
    }
}

function unixTSInBetween(start, end, check) {
    return check >= start && check <= end;
}

// returns the date in the UTC timezone, with the literal date values of the local time.
// function createDateAsUTC(date) {
//     return new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds()));
// }
//
// returns the date in the local timezone, but with the UTC literal date values.
// function convertDateToUTC(date) {
//     return new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(), date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());
// }