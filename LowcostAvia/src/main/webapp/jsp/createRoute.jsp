<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="pagecontent"/>
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
    <link rel="stylesheet" href="./css/select2.css">
    <link rel="stylesheet" href="./css/bootstrap-timepicker.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="css/tooltipster.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>AirEpam</title>
</head>
<body>
<div id="content">
    <%@include file="/jsp/header.jsp" %>
    <form action="/airepam" method="POST" id="createCity">
        <input type="hidden" name="command" value="openCreator"/>
        <div id="cityCreator" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title"><fmt:message key="createRoute.add.city"/></h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label" for="cityName"><fmt:message
                                    key="createRoute.city.name"/></label>
                            <input type="text" class="form-control" id="cityName" name="cityName">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"><fmt:message key="createRoute.add"/></button>
                    </div>
                </div>

            </div>
        </div>
    </form>


    <form action="/airepam" method="POST" id="addRouteForm">
        <input type="hidden" name="command" value="addRoute"/>
        <div id="create-form" class="container-fluid" style="width:90%">
            <legend><span id="createHeader"><fmt:message key="createRoute.create.route"/></span></legend>

            <select class="form-control search-select" id="selectFromCr" name="from">
                <option></option>
                <c:forEach var="element" items="${applicationScope.cities}">
                    <option><c:out value="${element.name}"/></option>
                </c:forEach>
            </select>

            <select class="form-control search-select" id="selectToCr" name="to">
                <option></option>
                <c:forEach var="element" items="${applicationScope.cities}">
                    <option><c:out value="${element.name}"/></option>
                </c:forEach>
            </select>


            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#cityCreator"><fmt:message
                    key="createRoute.add.city"/>
            </button>


            <div class="FormsPrices">
                <div class="form-group">
                    <label class="control-label" for="EconomyTicketCount"><fmt:message
                            key="createRoute.economy.count"/></label>
                    <input type="number" class="form-control" id="EconomyTicketCount" name="economyCount">
                </div>
                <div class="form-group">
                    <label class="control-label" for="BusinessTicketCount"><fmt:message
                            key="createRoute.business.count"/></label>
                    <input type="number" class="form-control" id="BusinessTicketCount" name="businessCount">
                </div>
                <div class="form-group">
                    <label class="control-label" for="EconomyPrice"><fmt:message
                            key="createRoute.economy.price"/></label>
                    <input type="number" class="form-control" id="EconomyPrice" name="economyPrice">
                </div>
                <div class="form-group">
                    <label class="control-label" for="BusinessPrice"><fmt:message
                            key="createRoute.business.price"/></label>
                    <input type="number" class="form-control" id="BusinessPrice" name="businessPrice">
                </div>

            </div>

            <div class="clearfix">
                <div class="labelForTime">
                    <label class="control-label"><fmt:message key="createRoute.departure"/></label>
                </div>
                <div class="labelForTime">
                    <label class="control-label"><fmt:message key="createRoute.arrival"/></label>
                </div>
                <div class="input-group bootstrap-timepicker timepicker" id="timepickerFrom">
                    <input id="timepicker1" type="text" class="form-control input-small" name="timeFrom">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                </div>

                <div class="input-group bootstrap-timepicker timepicker" id="timepickerTo">
                    <input id="timepicker2" type="text" class="form-control input-small" name="timeTo">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                </div>
            </div>

            <div class="container-fluid" id="week">
                <h4><fmt:message key="createRoute.flight.on"/></h4>
                <br>
                <div>
                    <label>
                        <input type="checkbox" name="week" value=1> <fmt:message key="createRoute.monday"/>
                    </label>
                    <label>
                        <input type="checkbox" name="week" value=2> <fmt:message key="createRoute.tuesday"/>
                    </label>
                    <label>
                        <input type="checkbox" name="week" value=3> <fmt:message key="createRoute.wednesday"/>
                    </label>
                    <label>
                        <input type="checkbox" name="week" value=4> <fmt:message key="createRoute.thursday"/>
                    </label>
                    <label>
                        <input type="checkbox" name="week" value=5> <fmt:message key="createRoute.friday"/>
                    </label>
                    <label>
                        <input type="checkbox" name="week" value=6> <fmt:message key="createRoute.saturday"/>
                    </label>
                    <label>
                        <input type="checkbox" name="week" value=0> <fmt:message key="createRoute.sunday"/>
                    </label>
                </div>
            </div>
            <div class="clearfix">
                <div class="labelForTime">
                    <label class="control-label"> <fmt:message key="createRoute.first.flight"/></label>
                </div>
                <div class="labelForTime">
                    <label class="control-label"> <fmt:message key="createRoute.last.flight"/></label>
                </div>
                <div class='input-group date' id='datepickerInCreator' data-date-format="dd-mm-yyyy">
                    <input type='text' class="form-control " readonly='true' id="dtd" name="dateFrom"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>

                <div class='input-group date' id='datepickerInCreator2' data-date-format="dd-mm-yyyy">
                    <input type='text' class="form-control" readonly='true' id="dtd2" name="dateTo"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>

            </div>

            <div class="clearfix">
                <button type="submit" class="btn btn-success" style="float:right"><fmt:message
                        key="createRoute.create"/></button>
            </div>
        </div>
    </form>
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
<ctg:footer/>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<script src="./js/bootstrap.min.js"></script>

<script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
<script src="./js/select2.js"></script>
<script src="./js/bootstrap-timepicker.min.js"></script>
<script src="../js/scripts.js"></script>
<script src="./js/jquery.validate.js"></script>
<script type="text/javascript" src="./js/jquery.tooltipster.min.js"></script>
<script type="text/javascript" src="../js/controllers/createRouteController.js"></script>
</body>
</html>