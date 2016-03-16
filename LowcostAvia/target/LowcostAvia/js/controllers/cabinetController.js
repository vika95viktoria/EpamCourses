$('#confirmation').on('hidden.bs.modal', function () {
    location.reload();
});

$('#confirmation2').on('hidden.bs.modal', function () {
    location.reload();
});


function editTicket(priorityVal, luggage, id, a) {

    var editPrior = 1;
    var editLugg = luggage;
    var s = '#priority' + a;
    var s2 = '#luggageCount' + a;
    if (priorityVal == false) {
        var prior = $(s).val();
        editPrior = parseInt(prior);
    }
    if (luggage == 0) {
        var lugg = $(s2).val();
        if (lugg != '') {
            editLugg = parseInt(lugg.substring(0, 2));
        }
    }
    if (priorityVal == true && luggage > 0) {
        return;
    }
    if (priorityVal == true) {
        if (editLugg == 0) {
            return;
        }
    }
    if (luggage > 0) {
        if (editPrior == 0) {
            return;
        }
    }


    if (editPrior != 0 || editLugg != 0) {
        var information = new Object();
        information.luggage = editLugg;
        information.luggageChange = false;
        information.priorChange = false;
        information.priority = editPrior;
        information.ticketId = id;
        if (information.luggage != luggage) {
            information.luggageChange = true;
        }
        if (priorityVal != true && information.priority != 0) {
            information.priorChange = true;
        }
        $.ajax({
            type: "POST",
            data: 'ticketInfo=' + JSON.stringify(information),
            url: "http://localhost:8080/airepam?command=editTicket",
            processData: false,
            success: function (data) {
                document.getElementById("result").innerHTML = data;
                $('#confirmation2').modal('show');
            }
        });
    }
}
function deleteTicket(id, flightId, dateOut, isBusiness) {
    var json = new Object();
    var today = new Date();
    var date = new Date(dateOut);
    var actual = false;
    if (date > today) {
        actual = true;
    }
    json.id = id;
    json.flightId = flightId;
    json.actual = actual;
    json.isBusiness = isBusiness;
    $.ajax({
        type: "POST",
        data: 'ticketInfo=' + JSON.stringify(json),
        url: "http://localhost:8080/airepam?command=deleteTicket",
        processData: false,
        success: function (data) {
            $('#confirmation').modal('show');
        }
    });
}

$('#after').bootstrapNumber();
$('input[name="prior"]').bootstrapNumber({
    upClass: 'success',
    downClass: 'danger'
});
$(document).ready(function () {
    $('#tickets').DataTable();
});
