var app = app || {};

function handleRoute(e) {
    let urlPath = window.location.hash.substr(0,
        (window.location.hash.indexOf('?') >= 0
        ? window.location.hash.indexOf('?')
        : window.location.hash.length));

    if(routeMap[urlPath]) {
        let entry = routeMap[urlPath];

        if(!entry['callback']) return;

        if(entry['parameters']) {
            let extractedParams = [];

            entry['parameters'].forEach(x => {
                if(app.queryUtility.queryParameter(x) != null) {
                    extractedParams.push(app.queryUtility.queryParameter(x));
                }
            });

            entry.callback.apply(null, extractedParams);
        } else {
            entry.callback();
        }
    }
}

$(window).on('hashchange', handleRoute);

let routeMap = {};

app.router = (function () {
    return {
        on: function (route, parameters, callback) {
            routeMap[route] = {
                parameters: parameters,
                callback: callback
            }
        }
    }
})();