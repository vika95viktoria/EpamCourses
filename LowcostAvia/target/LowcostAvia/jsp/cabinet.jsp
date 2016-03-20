<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
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
    <link rel="stylesheet" type="text/css" href="css/tooltipster.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">


    <title>AirEpam</title>
</head>
<body>
<div id="content">
    <%@include file="header.jsp" %>
<%--    <ctg:headerTag></ctg:headerTag>--%>
    <div class="container-fluid">
        <div class="container-fluid">
            <div class="col-xs-2">
                <img src="pictures/user.png" id="user-icon">
            </div>
            <div class="col-xs-10" id="userinfo">
                <span style="color: black"><fmt:message key="cabinet.login" />: ${currentUser}</span>
                <br>
                <br>
                <span style="color: black"><fmt:message key="cabinet.name" />: ${user.name} ${user.surname}</span>
                <br>
                <br>
                <span style="color: black"><fmt:message key="cabinet.email" />: ${user.email}</span>
                <br>
                <br>
                <span style="color: black"><fmt:message key="cabinet.amount" />: ${user.card.amount} &euro;</span>
            </div>
        </div>
        <c:if test="${not empty tickets}">
            <div class="centered" id="ticketTable">
<span>
<br>
    <fmt:message key="cabinet.tickets" />
     <br>
     </span>
            </div>
            <div class="container-fluid centered clearfix">
                <table id="tickets" class="table table-striped" cellspacing="0" width="100%">

                    <thead>
                    <tr>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="element" items="${tickets}" varStatus="a">
                        <tr>
                            <td><c:out value="${element.flight.route.cityFrom.name}"/></td>
                            <td><c:out value="${element.flight.route.cityTo.name}"/></td>
                            <td><ctg:cabinetDate date="${element.flight.dateOut}" locale="${sessionScope.language}"/></td>
                            <td><ctg:cabinetDate date="${element.flight.dateIn}" locale="${sessionScope.language}"/></td>
                            <td>
                                <button type="button" class="btn btn-info " id="ticketInfoButton" data-toggle="modal"
                                        data-target="#ticketInfo${a.count}"><fmt:message key="cabinet.info" />
                                </button>
                            </td>
                        </tr>
                        <div id="ticketInfo${a.count}" class="modal fade" role="dialog">
                            <div class="modal-dialog">

                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title"><fmt:message key="cabinet.ticketInfo" /></h4>
                                    </div>
                                    <div class="modal-body clearfix">
                                        <div>
                                            <div class="col-xs-6">
                                                <h4><fmt:message key="cabinet.userInfo" /></h4>
                                            </div>
                                            <div class="col-xs-6">
                                                <p>
                                                        ${element.name} ${element.surname}
                                                </p>
                                            </div>
                                        </div>
                                        <div>
                                            <div class="col-xs-6">
                                                <h4><fmt:message key="cabinet.flight" /></h4>
                                                <br>
                                            </div>
                                            <div class="col-xs-6">
                                                <p>
                                                    <c:out value="${element.flight.route.cityFrom.name}"/>
                                                    <i class="fa fa-long-arrow-right icon-black"></i>
                                                    <c:out value="${element.flight.route.cityTo.name}"/>
                                                </p>
                                                <p>
                                                    <ctg:cabinetDate date="${element.flight.dateOut}" locale="${sessionScope.language}"/>
                                                    <i class="fa fa-long-arrow-right icon-black"></i>
                                                    <ctg:cabinetDate date="${element.flight.dateIn}" locale="${sessionScope.language}"/>
                                                </p>
                                            </div>
                                        </div>

                                        <div id="luggageChange" class="luggageChange">
                                            <div class="col-xs-6">
                                                <h4><fmt:message key="cabinet.luggage" /> </h4>
                                            </div>
                                            <div class="col-xs-6">
                                                <c:choose>
                                                    <c:when test="${element.luggage == '0'}">
                                                        <select class="form-control" name="count"
                                                                id="luggageCount${a.count}">
                                                            <option></option>
                                                            <option><fmt:message key="cabinet.15" /></option>
                                                            <option><fmt:message key="cabinet.20" /></option>
                                                            <option><fmt:message key="cabinet.25" /></option>
                                                        </select>
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${element.luggage} <fmt:message key="cabinet.kg" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <br>
                                        </div>


                                        <div>
                                            <div class="col-xs-6">
                                                <h4><fmt:message key="cabinet.boarding" /> </h4>
                                            </div>
                                            <div class="col-xs-6">
                                                <c:choose>
                                                    <c:when test="${element.hasPriority == 'false'}">
                                                        <input id="priority${a.count}" class="form-control"
                                                               type="number" name="prior" value="0" min="0" max="1"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:message key="cabinet.included" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="modal-footer clearfix">
                                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                                data-target="#delete${a.count}" data-dismiss="modal" style="float:left">
                                            <fmt:message key="cabinet.delete" />
                                        </button>
                                        <button type="button" class="btn btn-success" data-dismiss="modal"
                                                onclick="editTicket(${element.hasPriority},${element.luggage},${element.id},${a.count})">
                                            <fmt:message key="cabinet.confirm" />
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div id="delete${a.count}" class="modal fade" role="dialog">
                            <div class="modal-dialog">
                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title"> <fmt:message key="cabinet.sure" /></h4>
                                    </div>
                                    <div class="modal-body">
                                        <p><h4> <fmt:message key="cabinet.warning" /></h4></p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary" data-dismiss="modal"
                                                onclick="deleteTicket(${element.id},${element.flight.id},${element.flight.dateOut.getTime()},${element.isBusiness})">
                                            <fmt:message key="cabinet.confirm.delete" />
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
    <div id="confirmation" class="modal fade" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <button type="button" class="close" data-dismiss="modal">&times;</button>

                <div class="alert alert-success fade in center" style="width: 100%">
                    <strong><span style="align-content: center"><fmt:message key="cabinet.success.delete" /></span></strong>
                </div>
            </div>
        </div>
    </div>

    <div id="confirmation2" class="modal fade" role="dialog">
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
<script src="http://code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<script src="./js/bootstrap.min.js"></script>
<script src="./js/bootstrap-number-input.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
<script src="js/select2.js"></script>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script src="../js/scripts.js"></script>
<script type="text/javascript" src="js/jquery.tooltipster.min.js"></script>
<script src="./js/jquery.validate.js"></script>
<script type="text/javascript"
        src="http://cdn.datatables.net/plug-ins/f3e99a6c02/integration/bootstrap/3/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="../js/controllers/cabinetController.js"></script>
<script>
    $(document).ready(function () {
    messageResource.init({
        filePath : 'resource/'
    });
    var name = 'error_'+'${language}'
    messageResource.load(name);
    });
</script>
</body>
</html>
