$("#selectFrom").on("select2:select", function (e) {
    $('#selectTo').select2('val', 'All');
    var e = document.getElementById("selectFrom");
    var from = e.options[e.selectedIndex].text;
    var s = "http://localhost:8080/airepam?command=cityTo";
    $.ajax({
        type: "POST",
        url: s,
        data: 'cityFrom=' + from,
        processData: false,
        success: function (data) {
            var json = JSON.parse(data);
            $('#selectTo').empty();
            $('#selectTo').append('<option></option>')
            $(json).each(function (i, elem) {
                $('#selectTo').append('<option>' + elem.name + '</option>')
            });

        }
    });
});


$(function () {
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
    $("#datepicker, #datepicker2").datepicker({
        autoclose: true,
        todayHighlight: true,
        startDate: now
    }).datepicker('update', new Date());
    ;

});

function hideReturn() {
    document.getElementById('datepicker2').style.visibility = "hidden";
    document.getElementById("return").value = false;
}
function openReturn() {
    document.getElementById('datepicker2').style.visibility = "visible";
}
$('#datepicker2').on('show', function (ev) {
    var a = $('#datepicker').datepicker('getDate');
    $('#datepicker2').datepicker('setStartDate', a)
});
