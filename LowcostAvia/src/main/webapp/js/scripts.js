$(document).ready(function () {

    $('#tabs').tooltipster({
        trigger: 'custom',
        onlyOne: false,
        position: 'top'
    });

    $('#editFlight input[type="number"]').tooltipster({
        trigger: 'custom',
        onlyOne: false,
        position: 'top-left'
    });
    $('#editFlight input[type="text"]').tooltipster({
        trigger: 'custom',
        onlyOne: false,
        position: 'top-left'
    });
    $('#editFlight input[type="password"]').tooltipster({
        trigger: 'custom',
        onlyOne: false,
        position: 'top-left'
    });

    $("#editFlight").validate({
        rules: {
            EditEconomyPrice: "required",
            EditBusinessPrice: "required",
            password: {
                required: true,
                minlength: 3
            },
            newTimeIn: "required",
            newTimeOut: "required",
        },
        messages: {
            password: "Please provide the password",
            EditEconomyPrice: "Please enter economy price",
            EditBusinessPrice: "Please enter business price",
            newTimeOut: "Please enter the time of departure",
            newTimeIn: "Please enter the time of arrival",

        }
        ,
        errorPlacement: function (error, element) {
            $(element).tooltipster('update', $(error).text());
            $(element).tooltipster('show');
        },
        success: function (label, element) {
            $(element).tooltipster('hide');
        }
    });
    var message = "";
    $("#buyTicket").validate({
        ignore: ".ignore",
        invalidHandler: function (e, validator) {
            if (validator.errorList.length)
                $('#tabs a[href="#' + jQuery(validator.errorList[0].element).closest(".tab-pane").attr('id') + '"]').tab('show')
        },
        rules: {
            passengerName1: "required",
            passengerSurname1: "required",
            passengerName2: "required",
            passengerSurname2: "required",
            passengerName3: "required",
            passengerSurname3: "required",
            passengerName4: "required",
            passengerSurname4: "required",
            passengerName5: "required",
            passengerSurname5: "required",
            radiog_dark: "required",
            radiog_dark2: "required",
            currentPrice: "required",
            currentPrice2: "required"
        },
        messages: {
            passengerName1: "Please enter the name of the 1 passenger! ",
            passengerSurname1: "Please  enter the surname of the 1 passenger! ",
            passengerName2: "Please  enter the name of the 2 passenger! ",
            passengerSurname2: "Please  enter the surname of the 2 passenger! ",
            passengerName3: "Please  enter the name of the 3 passenger! ",
            passengerSurname3: "Please  enter the surname of the 3 passenger! ",
            passengerName4: "Please  enter the name of the 4 passenger! ",
            passengerSurname4: "Please  enter the surname of the 4 passenger! ",
            passengerName5: "Please  enter the name of the 5 passenger! ",
            passengerSurname5: "Please  enter the surname of the 5 passenger! ",
            radiog_dark: "Please select the class of your ticket! ",
            radiog_dark2: "Please select the class of your return ticket! ",
            currentPrice: "Please select the flight! ",
            currentPrice2: "Please select the flight! "
        },
        errorPlacement: function (error, element) {
            if (message.indexOf($(error).text()) == -1) {
                message = message.concat($(error).text());
            }
            $('#tabs').tooltipster('update', message);
            $('#tabs').tooltipster('show');
        },
    });

    $('#addRouteForm input[type="number"]').tooltipster({
        trigger: 'custom',
        onlyOne: false,
        position: 'top-left'
    });


    $('#addRouteForm input[name="week"]').tooltipster({
        trigger: 'custom',
        onlyOne: false,
        position: 'top-left'
    });

    $('#addRouteForm select').tooltipster({
        trigger: 'custom',
        onlyOne: false,
        position: 'top-left'
    });
    $('#searchForm select').tooltipster({
        trigger: 'custom',
        onlyOne: false,
        position: 'top-left'
    });
    $('.modal').on('show.bs.modal', function (e) {
        $('body').addClass('test');
    });
    $("#login-form").on('shown.bs.modal', function () {
        $("#selectFrom").tooltipster('hide');
        $("#selectTo").tooltipster('hide');
    });
    $("#signup-form").on('shown.bs.modal', function () {
        $("#selectFrom").tooltipster('hide');
        $("#selectTo").tooltipster('hide');
    });
    $("#selectFromCr").on("select2:select", function (e) {
        $("#selectFromCr").tooltipster('hide');
    });
    $("#selectToCr").on("select2:select", function (e) {
        $("#selectToCr").tooltipster('hide');
    });
    $("#selectFrom").on("select2:select", function (e) {
        $("#selectFrom").tooltipster('hide');
    });
    $("#selectTo").on("select2:select", function (e) {
        $("#selectTo").tooltipster('hide');
    });
    $("#addRouteForm").validate({
        rules: {
            from: "required",
            to: "required",
            economyCount: "required",
            businessCount: "required",
            economyPrice: "required",
            businessPrice: "required",
            week: {
                required: true,
                minlength: 1
            }
        },
        messages: {
            from: "Please select the departure point",
            to: "Please select the arrival point",
            economyCount: "Please enter count of economy tickets",
            businessCount: "Please enter count of business tickets",
            economyPrice: "Please enter the price of economy tickets",
            businessPrice: "Please enter the price of business tickets",
            week: "Please select at least 1 day"
        },
        errorPlacement: function (error, element) {
            $(element).tooltipster('update', $(error).text());
            $(element).tooltipster('show');
        },
        success: function (label, element) {
            $(element).tooltipster('hide');
        }
    });
    $("#searchForm").validate({
        rules: {
            selectFrom: "required",
            selectTo: "required"
        },
        messages: {
            selectFrom: "Please select the departure point",
            selectTo: "Please select the arrival point"
        },
        errorPlacement: function (error, element) {
            $(element).tooltipster('update', $(error).text());
            $(element).tooltipster('show');
        },
        success: function (label, element) {
            $(element).tooltipster('hide');
        }
    });

    $("#deleteForm").validate({
        rules: {
            password: "required",
        },
        messages: {
            password: "Please provide a password"
        }
    });

    $("#createCity").validate({
        rules: {
            cityName: {
                required: true,
                minlength: 3
            }
        },
        messages: {
            cityName: "Please type the name of the city"
        }
    });
    $("#loginForm").validate({
        rules: {
            password: "required",
            username: "required"
        },
        messages: {
            username: "Please provide username",
            password: "Please provide password"

        }
    });

    $("#signupForm").validate({
        rules: {
            name: "required",
            cardType: "required",
            amount: {
                required: true,
                min: 10,
                max: 10000
            },
            surname: "required",
            user: {
                required: true,
                minlength: 2
            },
            pass: {
                required: true,
                minlength: 5
            },
            email: {
                required: true,
                email: true
            },
            number: {
                required: true,
                minlength: 16,
                maxlength: 16
            },
        },
        messages: {
            name: "Please enter your firstname",
            surname: "Please enter your lastname",
            user: {
                required: "Please enter a username",
                minlength: "Your username must consist of at least 2 characters"
            },
            pass: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long"
            },
            number: {
                required: "Please enter the number of your card",
                minlength: "Your card number must be  16 characters long"
            },
            email: "Please enter a valid email address",
            amount: {
                required: "Please enter amount on your card",
                min: "You should have at least 10 euro",
                max: "We provide operations below 10 000 euro"
            }
        }
    });
})
function changeToRus() {
    var s = "http://localhost:8080/airepam?command=changeLanguage&lang=rus";
    $.ajax({
        type: "POST",
        url: s,
        processData: false,
        success: function (data) {
            if (window.location.href == "http://localhost:8080/airepam?command=logout") {
                window.location = window.location.href.split("?")[0];
            }
            else {
                location.reload();
            }
        }
    });
}
function changeToEng() {
    var s = "http://localhost:8080/airepam?command=changeLanguage&lang=eng";
    $.ajax({
        type: "POST",
        url: s,
        processData: false,
        success: function (data) {
            if (window.location.href == "http://localhost:8080/airepam?command=logout") {
                window.location = window.location.href.split("?")[0];
            }
            else {
                location.reload();
            }
        }
    });
}