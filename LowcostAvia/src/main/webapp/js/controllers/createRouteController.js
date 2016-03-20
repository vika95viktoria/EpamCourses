$('#confirmation').on('hidden.bs.modal', function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/airepam?command=openCreator"
    });
});
$(document).ready(function () {

    $(".search-select").select2();
    $("#selectFromCr").select2({
        placeholder: "From",
        allowClear: true
    });
    $("#selectToCr").select2({
        placeholder: "To",
        allowClear: true
    });
    $(".select2-container--default").css("width", "42%");
    $(".select2-container--default").css("margin", "10px");
    $('#timepicker1').timepicker();
    $('#timepicker2').timepicker();

});
$("#createCity").submit(function (e) {
    var name = $("#cityName").val();
    var s = "http://localhost:8080/airepam?command=addCity";
    if ($("#createCity").valid()) {
        $.ajax({
            type: "POST",
            data: 'city=' + name,
            url: s,
            processData: false,
            success: function (data) {
                document.getElementById("result").innerHTML = data;
                $('#confirmation').modal('show');
                $('#cityName').val = "";

            }
        });
    }
});

$("#selectFromCr").on("select2:select", function (e) {
    $('#selectToCr').select2('val', 'All');
    var e = document.getElementById("selectFromCr");
    var e2 = document.getElementById("selectToCr");
    e2.remove(e.selectedIndex);
});
$('#datepickerInCreator2').on('show', function (ev) {
    var a = $('#datepickerInCreator').datepicker('getDate');
    $('#datepickerInCreator2').datepicker('setStartDate', a)
});
$('input[name="week"]').change(function () {
    var disable = [0, 1, 2, 3, 4, 5, 6];
    if ($('input[name="week"]').is(':checked')) {
        var enable = $("#week input:checkbox:checked").map(function () {
            return parseInt($(this).val());
        }).get();
        for (var i = 0; i < enable.length; i++) {
            var index = disable.indexOf(enable[i]);
            if (index > -1) {
                disable.splice(index, 1);
            }
        }
        $("#datepickerInCreator, #datepickerInCreator2").datepicker('setDaysOfWeekDisabled', disable);
    }
});


$(function () {
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
    $("#datepickerInCreator, #datepickerInCreator2").datepicker({
        autoclose: true,
        todayHighlight: true,
        startDate: now
    }).datepicker('update', new Date());

});
