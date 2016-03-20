$('#confirmation').on('hidden.bs.modal', function () {
    location.reload();
});

$(document).ready(function () {
    $('#routes').DataTable();

});
function checkboxValid() {
    if (check()) {
        $('#passCheck').modal('show');
    }
    else {
        $('#confirmation2').modal('show');
    }
}
function check() {
    var count = 0;
    $('input[name="toBeDeleted"]').each(function () {
        if ($(this).is(':checked')) {
            count++;
        }
    });
    if (count > 0) {
        return true;
    }
    return false;
}
