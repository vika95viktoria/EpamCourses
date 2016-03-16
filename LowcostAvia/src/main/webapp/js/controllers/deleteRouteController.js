$('#confirmation').on('hidden.bs.modal', function () {
    location.reload();
});

$("#deleteForm").submit(function (e) {

    var url = "http://localhost:8080/airepam?command=deleteFlights";
    if ($("#deleteForm").valid()) {
        $.ajax({
            type: "POST",
            url: url,
            data: $("#deleteForm").serialize(),
            success: function (data) {
                if (data == 'Incorrect password') {
                    document.getElementById("incorrect").style.display = "block";
                }
                else {
                    document.getElementById("password").value = "";
                    document.getElementById("deleteForm").reset();
                    document.getElementById("incorrect").style.display = "none";
                    document.getElementById("result").innerHTML = data;
                    $('#confirmation').modal('show');
                }
            }
        });

        e.preventDefault();
    }
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
