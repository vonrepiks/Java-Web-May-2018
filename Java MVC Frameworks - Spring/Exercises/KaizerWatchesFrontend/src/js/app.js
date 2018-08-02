let constants = {
    serviceUrl: "http://127.0.0.1:8080"
};

app.templateLoader.loadTemplate('.navbar-holder', 'navbar');

app.router.on("#/", null, function () {
    $.ajax({
        type: 'GET',
        url: constants.serviceUrl + '/watches/top'
    }).done((data) => {
        app.templateLoader.loadTemplate('.app', 'home', function () {
            for (let elem of data) {
                $('.watches')
                    .append('<div class="col-md-3">'
                        + '<div class="watch-image text-center">'
                        + '<img src="' + elem['imageUrl'] + '" class="img-thumbnail" width="200" height="200">'
                        + '</div>'
                        + '<div class="watch-name mt-2">'
                        + '<h4 class="text-center">' + elem['name'] + '</h4>'
                        + '</div>'
                        + '<div class="watch-price mt-2">'
                        + '<h4 class="text-center">$' + elem['price'] + '</h4>'
                        + '</div>'
                        + '<div class="watch-link">'
                        + '<a class="nav-link text-center" href="#/watches/details?id=' + elem['id'] + '">'
                        + '<h4>Details</h4>'
                        + '</a>'
                        + '</div>');
            }
        });
    }).fail((err) => {
        console.log(err);
    });
});

app.router.on("#/watches/all", null, function () {
    $.ajax({
        type: 'GET',
        url: constants.serviceUrl + '/watches/all'
    }).done((data) => {
        app.templateLoader.loadTemplate('.app', 'watches-all', function () {
            let i = 1;

            for (let elem of data) {
                $('.all-watches')
                    .append('<tr class="row">'
                        + '<td class="col-md-1" scope="col"><h5>' + i + '</h5></td>'
                        + '<td class="col-md-7" scope="col"><h5>' + elem['name'] + '</h5></td>'
                        + '<td class="col-md-2" scope="col"><h5>$' + elem['price'] + '</h5></td>'
                        + '<td class="col-md-2" scope="col">'
                        + '<a class="nav-link-black" href="#/watches/details?id=' + elem['id'] + '">'
                        + '<h5>Details</h5>'
                        + '</a>'
                        + '</td>'
                        + '</tr>');

                i++;
            }
        });
    }).fail((err) => {
        console.log(err);
    });
});

app.router.on("#/watches/add", null, function () {
    app.templateLoader.loadTemplate('.app', 'watches-add', function () {
        $('#submit-watch').click(function (e) {
            let name = $('#name').val();
            let price = $('#price').val();
            let image = $('#imageUrl').val();
            let description = $('#description').val();

            $.ajax({
                type: 'POST',
                url: constants.serviceUrl + '/watches/add',
                data: {
                    name: name,
                    price: price,
                    imageUrl: image,
                    description: description
                }
            }).done((data) => {
                window.location.href = '#/';
            }).fail((err) => {
                console.log(err);
            });
        })
    });
});

app.router.on('#/watches/details', ['id'], function (id) {
    $.ajax({
        type: 'GET',
        url: constants.serviceUrl + '/watches/details?id=' + id
    }).done((data) => {
        app.templateLoader.loadTemplate('.app', 'watches-details', function () {
            $('.watch-details')
                .append('<div class="col-md-5">'
                    + '<div class="watch-image text-center">'
                    + '<img src="' + data['imageUrl'] + '" class="img-thumbnail" width="500" height="500">'
                    + '</div>'
                    + '</div>'
                    + '<div class="col-md-5 d-flex flex-column">'
                    + '<h1 class="text-center">' + data['name'] + '</h1>'
                    + '<h2 class="text-center">Price: $' + data['price'] + '</h2>'
                    + '<h2 class="text-center mt-3">Viewed: ' + data['views'] + ' times</h2>'
                    + '<h2 class="text-center mt-3">Description</h2>'
                    + '<p class="mt-3 text-center">'
                    + data['description']
                    + '</p>'
                    + '</div>');
        });
    }).fail((err) => {
        console.log(err);
    });
});

window.onload = function (event) {
    handleRoute(event);
};