<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="pagecontent" />
<!DOCTYPE html>
<html>
<head>

    <link href="https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/journal/bootstrap.min.css" rel="stylesheet"
          integrity="sha256-fHWoqb8bPKjLiC7AuV6qy/lt0hrzfwM0ciyAu7Haf5w= sha512-3t2GeiCRNeKhZnUaUMygGiLKZzb/vPhvfw3y1Rt2FCwKuRaLVrOCTpavIYsZ4xqM52mbO7M93NaXbm/9dOA2Og=="
          crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="css/select2.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css"
          href="http://cdn.datatables.net/plug-ins/f3e99a6c02/integration/bootstrap/3/dataTables.bootstrap.css">
    <link rel="stylesheet" href="css/nav-wizard.bootstrap.css">
    <link rel="stylesheet" type="text/css" href="slick/slick.css"/>
    <link rel="stylesheet" href="./css/bootstrap-timepicker.min.css">
    <link rel="stylesheet" type="text/css" href="slick/slick-theme.css"/>
    <link rel="stylesheet" href="../css/checkbox.css">
    <link rel="stylesheet" type="text/css" href="css/tooltipster.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">


    <title>AirEpam</title>
</head>
<body>
<div id="content">
    <%@include file="header.jsp" %>
    <div class="container">
        <div class="container-fluid center">
            <div class="navbar-header " id="tabs">
                <ul class='nav nav-wizard' >

                    <li class="active"><a href='#ticket' data-toggle="tab"><fmt:message key="flightPage.ticket" /></a></li>

                    <li><a href='#luggage' data-toggle="tab"><fmt:message key="flightPage.luggage" /></a></li>

                    <li><a href='#priority' data-toggle="tab" onclick="cost()"><fmt:message key="flightPage.priority" /></a></li>
                    <li><a href='#payment' data-toggle="tab" onclick="cost()"><fmt:message key="flightPage.payment" /></a></li>

                </ul>
            </div>
        </div>
        <form id="buyTicket">
            <input type="hidden" name="command" value="ticket"/>
            <div id="tabFlyContent" class="tab-content">
                <div class="tab-pane fade active in" id="ticket">
                    <div class="container-fluid clearfix" id="ticket-form">
                        <div class="datesSlide container-fluid centerSlide">
                            <jsp:useBean id="DateUtils" class="com.epam.lowcost.util.DateUtils"/>
                            <c:forEach var="element" items="${flights}">
                                <div style=" text-align: center;" data-toggle="tooltip2"
                                     title="You can edit flight conditions on double click" class="centered"
                                     role="button"
                                     ondblclick="edit(${element.dateOut.getTime()},${element.dateIn.getTime()},${element.economyPrice},${element.businessPrice},${element.id},false)"
                                     onclick="getTicket( ${element.dateOut.getTime()},${element.dateIn.getTime()}, ${element.economyPrice},${element.businessPrice},${element.id})">
                            <span>
                           <c:out value="${DateUtils.formateDate(element.dateOut,sessionScope.language)}"/>
                                <p>
                                    <c:out value="${element.economyPrice} "/>
                                    &euro;
                                </p>
                                    </span>
                                </div>


                            </c:forEach>
                            <input type="hidden" name="flightid" id="flightid"/>
                            <input type="hidden" name="isBusiness" id="isBusiness"/>
                        </div>
                        <div class="myTable " id="flyTo">
                            <br>
                            <h4><c:out value="${cityFrom} - ${cityTo} "/></h4>
                            <br>
                            <c:choose>
                                <c:when test="${not empty errorFlight}">
                          <span style="text-align:center">
                            <h4>${errorFlight}</h4></span>
                                </c:when>
                                <c:otherwise>
                                    <table class="table  table-bordered">
                                        <thead>
                                        <tr>
                                            <th><fmt:message key="flightPage.dep.arr" /></th>
                                            <th><fmt:message key="flightPage.economy" /></th>
                                            <th><fmt:message key="flightPage.business" /></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>
                                                <span id="dateFrom"></span>
                                                <p>
                                                    <span id="demo1"></span>
                                                    <i class="fa fa-long-arrow-right icon-black"></i>
                                                    <span id="demo2"></span>
                                                </p>
                                            </td>
                                            <td>
                                                <span id="economyPrice"></span>
                                                <span>&euro;</span>
                                                <br>
                                                <div class="radio radio-success">
                                                    <input type="radio" name="radiog_dark" id="radio21" onclick="disp()">
                                                    <label></label>
                                                </div>
                                            </td>
                                            <input type="hidden" name="currentPrice" id="currentPrice" />
                                            <td>
                                                <span id="businessPrice"></span>
                                                <span>&euro;</span>
                                                <br>
                                                <div class="radio radio-success">
                                                    <input type="radio" name="radiog_dark" id="radio22" onclick="disp()">
                                                    <label></label>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </c:otherwise>
                            </c:choose>
                        </div>


                        <c:if test="${not empty isReturn}">
                            <div class="datesSlide container-fluid centerSlide">
                                <c:forEach var="element" items="${returnFlights}">
                                    <div style=" text-align: center;" class="centered" role="button"
                                         ondblclick="edit(${element.dateOut.getTime()},${element.dateIn.getTime()},${element.economyPrice},${element.businessPrice},${element.id},true)"
                                         onclick="getTicket2( ${element.dateOut.getTime()},${element.dateIn.getTime()}, ${element.economyPrice},${element.businessPrice},${element.id})">
                            <span>
                           <c:out value="${DateUtils.formateDate(element.dateOut,sessionScope.language)}"/>
                                <p>
                                    <c:out value="${element.economyPrice} "/>
                                    &euro;
                                </p>
                                    </span>
                                    </div>
                                </c:forEach>
                                <input type="hidden" name="returnFlightId" id="returnFlightId"/>
                                <input type="hidden" name="isBusiness2" id="isBusiness2"/>
                                <input type="hidden" name="currentPrice2" id="currentPrice2"/>
                            </div>
                            <div class="myTable " id="flyBack">
                                <br>
                                <h4><c:out value="${cityTo} - ${cityFrom} "/></h4>
                                <br>
                                <c:choose>
                                    <c:when test="${not empty errorReturn}">
                           <span style="text-align:center">
                            <h4>${errorReturn}</h4></span>
                                    </c:when>
                                    <c:otherwise>
                                        <table class="table  table-bordered">
                                            <thead>
                                            <tr>
                                                <th><fmt:message key="flightPage.dep.arr" /></th>
                                                <th><fmt:message key="flightPage.economy" /></th>
                                                <th><fmt:message key="flightPage.business" /></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>
                                                    <span id="dateTo"></span>
                                                    <p>
                                                        <span id="demo3"></span>
                                                        <i class="fa fa-long-arrow-right icon-black"></i>
                                                        <span id="demo4"></span>
                                                    </p>
                                                </td>
                                                <td>
                                                    <span id="economyPrice2"></span>
                                                    <span>&euro;</span>
                                                    <br>
                                                    <div class="radio radio-success">
                                                        <input type="radio" name="radiog_dark2" id="radio23" onclick="disp2()">
                                                        <label></label>
                                                    </div>
                                                </td>
                                                <td><span id="businessPrice2"></span>
                                                    <span>&euro;</span>
                                                    <br>
                                                    <div class="radio radio-success">
                                                        <input type="radio" name="radiog_dark2" id="radio24" onclick="disp2()">
                                                        <label></label>
                                                    </div>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:if>
                    </div>
                </div>


                <div class="tab-pane fade" id="luggage">
                    <span id="bags"><h3><fmt:message key="flightPage.bags" /></h3> </span>
                    <br>
                    <br>
                    <div class="container center">
                        <div class="row">
                            <div class="col-xs-4">
                                <img src="pictures/luggage.jpg" width="80%" height="80%">
                            </div>
                            <div class="col-xs-8">
                                <div class="col-xs-6">
                                    <h4><c:out value="${cityFrom} - ${cityTo} "/></h4>
                                    <table class="table table-striped">
                                        <thead>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td><fmt:message key="cabinet.15" /></td>
                                            <td><span>${luggage15} &euro;</span></td>
                                            <td>
                                                <input type="radio" name="luggage" value="15"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><fmt:message key="cabinet.20" /></td>
                                            <td><span>${luggage20} &euro;</span></td>
                                            <td>
                                                <input type="radio" name="luggage" value="20"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><fmt:message key="cabinet.25" /></td>
                                            <td><span>${luggage25} &euro;</span></td>
                                            <td>
                                                <input type="radio" name="luggage" value="25"/>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <input id="luggCount" class="form-control" type="number" value="0" min="0"
                                           max="${count}"/>
                                </div>
                                <c:if test="${not empty returnFlights}">
                                    <div class="col-xs-6">
                                        <h4><c:out value="${cityTo} - ${cityFrom} "/></h4>
                                        <table class="table table-striped">
                                            <thead>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td><fmt:message key="cabinet.15" /></td>
                                                <td><span>${luggage15} &euro;</span></td>
                                                <td>
                                                    <input type="radio" name="luggage2" value="15"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><fmt:message key="cabinet.20" /></td>
                                                <td><span>${luggage20} &euro;</span></td>
                                                <td>
                                                    <input type="radio" name="luggage2" value="20"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><fmt:message key="cabinet.25" /></td>
                                                <td><span>${luggage25} &euro;</span></td>
                                                <td>
                                                    <input type="radio" name="luggage2" value="25"/>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <input id="luggCountReturn" class="form-control" type="number" value="0" min="0"
                                               max="${count}"/>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="tab-pane fade" id="priority">
                    <span id="prior"><h3><fmt:message key="flightPage.priority" /></h3> </span>
                    <br>
                    <br>

                    <div class="container-fluid clearfix center" id="boarding">
                        <div class="row">
                            <div class="col-xs-6">
                                <img src="pictures/boarding.jpg" width="80%" height="80%">
                            </div>
                            <div class="col-xs-6">
                                <h4><c:out value="${cityFrom} - ${cityTo}: "/>
                                    <span>${priorityPrice} &euro;</span>
                                    <br>
                                    <br>
                                </h4>
                                <input id="boardCount" class="form-control" type="number" value="0" min="0"
                                       max="${count}"/>
                                <c:if test="${not empty returnFlights}">
                                    <h4><c:out value="${cityTo} - ${cityFrom}: "/>
                                        <span>${priorityPrice} &euro;</span>
                                        <br>
                                        <br>
                                    </h4>
                                    <input id="boardCountReturn" class="form-control" type="number" value="0" min="0"
                                           max="${count}"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="tab-pane fade" id="payment">
                    <div class="modal-body center">
                        <c:forEach begin="1" end="${count}" varStatus="loop">
                            <p>
                            <h4>
                                <c:out value="${loop.count}  "/>
                                <span><fmt:message key="flightPage.passenger" /></span>
                            </h4>
                            </p>
                            <p>
                                <label for="passengerName${loop.count}"><fmt:message key="flightPage.passenger.name" /></label>
                                <input id="passengerName${loop.count}" type="text" name="passengerName${loop.count}"
                                       class="names" value=""/>
                            </p>

                            <p>
                                <label for="passengerSurname${loop.count}"><fmt:message key="flightPage.passenger.surname" /> </label>
                                <input id="passengerSurname${loop.count}" type="text"
                                       name="passengerSurname${loop.count}" class="surnames" value=""/>
                            </p>
                        </c:forEach>
                        <p>
                            <span><h3><fmt:message key="flightPage.cost" /> : <span id="finalprice"></span> &euro; </h3></span>
                        </p>

                        <div class="clearfix">
                            <c:choose>
                                <c:when test="${not empty role }">
                                    <button type="submit" class="btn btn-primary"><fmt:message key="flightPage.buy" /></button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="btn btn-disabled" data-toggle="tooltip"
                                            title="<fmt:message key="flightPage.please" />"><fmt:message key="flightPage.buy" />
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <div id="confirmation" class="modal fade" role="dialog">
                                <div class="modal-dialog">

                                    <!-- Modal content-->
                                    <div class="modal-content">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>

                                        <div class="alert alert-info fade in center" style="width: 100%">
                                            <strong><span id="result"></span></strong>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </p>
                    </div>
                </div>
            </div>
        </form>
        <div id="editFlight-form" style="display:none">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" onclick="closeEdit()">&times;</button>
                        <h3 class="modal-title"><fmt:message key="flightPage.change.flight" /></h3>
                    </div>
                    <form action="/airepam" id="editFlight" method="POST">
                        <input type="hidden" name="command" value="editFlight"/>
                        <div class="modal-body">
                            <div class="alert alert-dismissible alert-danger container-fluid"
                                 style="text-align: center; width:90%; display: none" id="incorrect">
                                <strong><fmt:message key="deleteRoute.password.incorrect" /></strong>
                            </div>
                            <input type="hidden" name="flightId" id="EditingflightId"/>
                            <input type="hidden" name="newDateOut" id="newDateOut"/>
                            <input type="hidden" name="newDateIn" id="newDateIn"/>
                            <div class="form-group">
                                <h4><span id="route"></span>
                                    <br>
                                    <br>
                                    <span id="timeForEdit" style="color: black"></span>
                                </h4>
                                <br>
                                <br>
                                <label class="control-label" for="EconomyPrice"><fmt:message key="createRoute.economy.price" /></label>
                                <input type="number" class="form-control" id="EditEconomyPrice" name="EditEconomyPrice">
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="BusinessPrice"><fmt:message key="createRoute.business.price" /></label>
                                <input type="number" class="form-control" id="EditBusinessPrice"
                                       name="EditBusinessPrice">
                            </div>
                            <div class="clearfix">
                                <div style="margin-bottom: 15px">
                                    <label class="control-label"><fmt:message key="createRoute.departure" /></label>
                                    <div class="input-group bootstrap-timepicker timepicker">
                                        <input id="timepicker3" type="text" class="form-control input-small"
                                               id="newTimeOut" name="newTimeOut">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                    </div>
                                </div>
                                <div style="margin-bottom: 15px">
                                    <label class="control-label"><fmt:message key="createRoute.arrival" /></label>

                                    <div class="input-group bootstrap-timepicker timepicker">
                                        <input id="timepicker4" type="text" class="form-control input-small"
                                               id="newTimeIn" name="newTimeIn">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                    </div>
                                </div>
                            </div>
                            <label class="control-label" for="password"><fmt:message key="deleteRoute.password" /></label>
                            <input type="password" class="form-control" id="password" name="password">

                            <p>
                            <div class="clearfix">
                                <button type="submit" class="btn btn-primary" style="float: right;"><fmt:message key="flightPage.accept" />
                                </button>
                            </div>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
<script src="http://code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="slick/slick.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-number-input.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
<script src="../js/select2.js"></script>
<script src="./js/scripts.js"></script>
<script src="./js/jquery.validate.js"></script>
<script src="./js/bootstrap-timepicker.min.js"></script>
<script type="text/javascript" src="js/jquery.tooltipster.min.js"></script>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
        src="http://cdn.datatables.net/plug-ins/f3e99a6c02/integration/bootstrap/3/dataTables.bootstrap.js"></script>

<script>
    var monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"];
    var monthNamesRus = ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"];

    $('#after').bootstrapNumber();
    $('#boardCount,#boardCountReturn,#luggCountReturn,#luggCount').bootstrapNumber({
        upClass: 'success',
        downClass: 'danger'
    });


    function getTicket(dateIn, dateOut, economyPrice, businessPrice, id) {
        var d = new Date(dateIn);
        var d2 = new Date(dateOut);
        var x = document.getElementById("demo1");
        var x2 = document.getElementById("demo2");
        var x3 = document.getElementById("dateFrom");
        var x4 = document.getElementById("economyPrice");
        var x5 = document.getElementById("businessPrice");
        var y = document.getElementById("flightid");
        var h = addZero(d.getHours());
        var m = addZero(d.getMinutes());
        var h2 = addZero(d2.getHours());
        var m2 = addZero(d2.getMinutes());
        x.innerHTML = h + ":" + m;
        x2.innerHTML = h2 + ":" + m2;
        if('${language == "ru_RU"}'=='true'){
            x3.innerHTML = d.getDate() + " " + monthNamesRus[d.getMonth()];
        }
        else {
            x3.innerHTML = d.getDate() + " " + monthNames[d.getMonth()];
        }
        x4.innerHTML = economyPrice;
        x5.innerHTML = businessPrice;
        y.value = id;
    }
    function getTicket2(dateIn, dateOut, economyPrice, businessPrice, id) {
        var d = new Date(dateIn);
        var d2 = new Date(dateOut);
        var x = document.getElementById("demo3");
        var x2 = document.getElementById("demo4");
        var x3 = document.getElementById("dateTo");
        var x4 = document.getElementById("economyPrice2");
        var x5 = document.getElementById("businessPrice2");
        var y = document.getElementById("returnFlightId");
        var h = addZero(d.getHours());
        var m = addZero(d.getMinutes());
        var h2 = addZero(d2.getHours());
        var m2 = addZero(d2.getMinutes());
        x.innerHTML = h + ":" + m;
        x2.innerHTML = h2 + ":" + m2;
        if('${language == "ru_RU"}'=='true'){
            x3.innerHTML = d.getDate() + " " + monthNamesRus[d.getMonth()];
        }
        else {
            x3.innerHTML = d.getDate() + " " + monthNames[d.getMonth()];
        }
        x4.innerHTML = economyPrice;
        x5.innerHTML = businessPrice;
        y.value = id;
    }
    function disp() {
        var y = document.getElementById("currentPrice");
        if (document.getElementById("radio21").checked) {
            document.getElementById("isBusiness").value = false;
            y.value = document.getElementById("economyPrice").innerText;
        }
        if (document.getElementById("radio22").checked) {
            document.getElementById("isBusiness").value = true;
            y.value = document.getElementById("businessPrice").innerText;
        }
    }

    function disp2() {
        var y = document.getElementById("currentPrice2");
        if (document.getElementById("radio23").checked) {
            document.getElementById("isBusiness2").value = false;
            y.value = document.getElementById("economyPrice2").innerText;
        }
        if (document.getElementById("radio24").checked) {
            document.getElementById("isBusiness2").value = true;
            y.value = document.getElementById("businessPrice2").innerText;
        }

    }
    function cost() {
        var final = 0;
        var numberOfPassengers = '${count}';
        var price = document.getElementById("currentPrice").value;
        var luggage = $('input[name=luggage]:checked', '#buyTicket').val();
        var luggageCount = document.getElementById("luggCount").value;
        var luggagePrice = 0;
        if (luggageCount != 0) {
            switch (luggage) {
                case "15":
                    luggagePrice = '${luggage15}'
                    break;
                case "20":
                    luggagePrice = '${luggage20}'
                    break;
                case "25":
                    luggagePrice = '${luggage25}'
                    break;
                default:
                    luggagePrice = 0;
            }
        }
        var priorityCount = document.getElementById("boardCount").value;
        final = numberOfPassengers * price + priorityCount * '${priorityPrice}' + luggageCount * luggagePrice;
        if ('${not empty returnFlights }' == 'true') {
            var returnPrice = document.getElementById("currentPrice2").value;
            var luggage2 = $('input[name=luggage2]:checked', '#buyTicket').val();
            var luggageCount2 = document.getElementById("luggCountReturn").value;
            var luggagePrice2 = 0;
            if (luggageCount2 != 0) {
                switch (luggage2) {
                    case "15":
                        luggagePrice2 = '${luggage15}'
                        break;
                    case "20":
                        luggagePrice2 = '${luggage20}'
                        break;
                    case "25":
                        luggagePrice2 = '${luggage25}'
                        break;
                    default:
                        luggagePrice2 = 0;
                }
            }
            var priorityCount2 = document.getElementById("boardCountReturn").value;
            final = final + numberOfPassengers * returnPrice + priorityCount2 * '${priorityPrice}' + luggageCount2 * luggagePrice2;
        }
        document.getElementById("finalprice").innerHTML = final;

    }


    function closeEdit() {
        $('#editFlight input[type="password"],#editFlight input[type="number"],#editFlight input[type="text"] ').tooltipster('hide');
        document.getElementById("incorrect").style.display = "none";
        document.getElementById("editFlight-form").style.display = "none";
    }

    $("#editFlight").submit(function (e) {
        if($("#editFlight").valid()) {
            var url = "http://localhost:8080/airepam?command=editFlight";

            $.ajax({
                type: "POST",
                url: url,
                data: $("#editFlight").serialize(),
                success: function (data) {
                    if (data == 'Incorrect password') {
                        document.getElementById("incorrect").style.display = "block";
                    }
                    else {
                        document.getElementById("editFlight").reset();
                        document.getElementById("incorrect").style.display = "none";
                        document.getElementById("editFlight").style.display = "none";
                        location.reload();
                    }
                }
            });
            e.preventDefault();
        }
    });

    function edit(dateOut, dateIn, economyPrice, businessPrice, id, isReturn) {
        if ('${role}' == 'admin') {
            var d = new Date(dateOut);
            var d2 = new Date(dateIn);
            var x = document.getElementById("EditEconomyPrice");
            var x2 = document.getElementById("EditBusinessPrice");
            var x3 = document.getElementById("timeForEdit");
            var x4 = document.getElementById("EditingflightId");
            var x5 = document.getElementById("newDateOut");
            var x6 = document.getElementById("newDateIn");
            var h = addZero(d.getHours());
            var m = addZero(d.getMinutes());
            var h2 = addZero(d2.getHours());
            var m2 = addZero(d2.getMinutes());
            if (h > 12) {
                var timeIn = h - 12 + ":" + m + " PM";
            }
            else {
                var timeIn = h + ":" + m + " AM";
            }
            if (h2 > 12) {
                var timeOut = h2 - 12 + ":" + m2 + " PM";
            }
            else {
                var timeOut = h2 + ":" + m2 + " AM";
            }
            x3.innerHTML = d.getDate() + " " + monthNames[d.getMonth()] + " " + h + ":" + m + " - " + h2 + ":" + m2;
            x.value = economyPrice;
            x2.value = businessPrice;
            x4.value = id;
            x5.value = dateOut;
            x6.value = dateIn;
            if (isReturn) {
                document.getElementById("route").innerHTML = '${cityTo}' + " - " + '${cityFrom}';
            }
            else {
                document.getElementById("route").innerHTML = '${cityFrom}' + " - " + '${cityTo}';
            }
            $('#timepicker3').timepicker('setTime', timeIn);
            $('#timepicker4').timepicker('setTime', timeOut);
            document.getElementById("editFlight-form").style.display = "block";
        }
    }
    $("#buyTicket").submit(function (e) {
        if ($("#buyTicket").valid()) {
            var ticket = new Object();
            var returnTicket = new Object();
            var ticketInfo = new Array();
            var count = 0;
            var names = new Array();
            var surnames = new Array();
            if ('${not empty flights }' == 'true') {
                $('.names').each(function () {
                    names[count] = $(this).val();
                    count++;
                });
                count = 0;
                $('.surnames').each(function () {
                    surnames[count] = $(this).val();
                    count++;
                })

                ticket.names = names;
                ticket.surnames = surnames;
                ticket.luggage = 0;
                if ($('input[name=luggage]:checked', '#buyTicket').val() != null) {
                    ticket.luggage = $('input[name=luggage]:checked', '#buyTicket').val();
                }
                ticket.luggageCount = document.getElementById("luggCount").value;
                ticket.priorityCount = document.getElementById("boardCount").value;
                ticket.flightId = document.getElementById("flightid").value;
                ticket.isBusiness = document.getElementById("isBusiness").value;
                ticket.price = document.getElementById("currentPrice").value;
                ticketInfo.push(ticket);
            }
            if ('${not empty returnFlights }' == 'true') {
                returnTicket.names = ticket.names;
                returnTicket.surnames = ticket.surnames;
                returnTicket.luggage = 0;
                if ($('input[name=luggage2]:checked', '#buyTicket').val() != null) {
                    returnTicket.luggage = $('input[name=luggage2]:checked', '#buyTicket').val();
                }
                returnTicket.luggageCount = document.getElementById("luggCountReturn").value;
                returnTicket.priorityCount = document.getElementById("boardCountReturn").value;
                returnTicket.flightId = document.getElementById("returnFlightId").value;
                returnTicket.isBusiness = document.getElementById("isBusiness2").value;
                returnTicket.price = document.getElementById("currentPrice2").value;
                ticketInfo.push(returnTicket);
            }
            console.log(JSON.stringify(ticketInfo));
            $.ajax({
                type: "POST",
                data: 'ticketInfo=' + JSON.stringify(ticketInfo),
                url: "http://localhost:8080/airepam?command=ticket",
                processData: false,
                success: function (data) {
                    document.getElementById("result").innerHTML = data;
                    document.getElementById("buyTicket").reset();
                    $('#tabs').tooltipster('hide');
                    document.getElementById("finalprice").innerHTML = "";
                    $('#confirmation').modal('show');
                }
            });
            e.preventDefault();
        }
    });

    function addZero(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }

    $(document).ready(function () {
        $('.datesSlide').slick({
            infinite: false,
            slidesToShow: 4,
            slidesToScroll: 1
        });
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="tooltip2"]').tooltip();

    });
</script>
</body>
</html>
