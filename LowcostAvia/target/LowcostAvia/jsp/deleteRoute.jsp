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
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="../css/select2.css">
    <link rel="stylesheet" href="../css/checkbox.css">
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
    <div class="container-fluid centered">
        <div style="text-align:center; ">
            <h3 style="color:red"><fmt:message key="deleteRoute.choose" /></h3>
        </div>
        <form action="/airepam" method="POST" id="deleteForm">
            <input type="hidden" name="command" value="deleteFlights"/>
            <table id="routes" class="table table-striped" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th><h3><fmt:message key="deleteRoute.from" /></h3></th>
                    <th><h3><fmt:message key="deleteRoute.to" /></h3></th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="element" items="${routes}" varStatus="status">
                    <c:if test="${minPrice[status.index]!=0}">
                        <tr>
                            <td><c:out value="${element.cityFrom}"/></td>
                            <td><c:out value="${element.cityTo}"/></td>
                            <td>
                                <div class="checkbox checkbox-primary">
                                    <input id="checkbox" class="styled" type="checkbox" name="toBeDeleted"
                                           value="${element.id}">
                                    <label for="checkbox">
                                        <h4> <fmt:message key="deleteRoute.delete" /></h4>
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
            <br>
            <br>
            <div class="clearfix">
                <button type="button" class="btn btn-danger btn-lg" style="float:right" onclick="checkboxValid()"><fmt:message key="deleteRoute.delete.selected" />
                </button>
            </div>
            <div id="passCheck" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title"><fmt:message key="deleteRoute.password" /></h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-dismissible alert-danger container-fluid"
                                 style="text-align: center; width:90%; display: none" id="incorrect">
                                <strong><fmt:message key="deleteRoute.password.incorrect" /></strong>
                            </div>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary"><fmt:message key="deleteRoute.delete" /></button>
                        </div>
                    </div>

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

        <div id="confirmation2" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>

                    <div class="alert alert-danger fade in center" style="width: 100%">
                        <strong><fmt:message key="deleteRoute.select" /></strong>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<ctg:footer/>
<script src="http://code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<script src="../js/bootstrap.min.js"></script>

<script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
<script src="../js/select2.js"></script>
<script src="../js/scripts.js"></script>
<script src="./js/jquery.validate.js"></script>
<script type="text/javascript" src="js/jquery.tooltipster.min.js"></script>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
        src="http://cdn.datatables.net/plug-ins/f3e99a6c02/integration/bootstrap/3/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="../js/controllers/deleteRouteController.js"></script>
<script>
    $("#deleteForm").submit(function (e) {

        var url = "http://localhost:8080/airepam?command=deleteFlights";
        if ($("#deleteForm").valid()) {
            $.ajax({
                type: "POST",
                url: url,
                data: $("#deleteForm").serialize(),
                success: function (data) {
                    if ('${not empty error}') {
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
        messageResource.init({
            filePath : 'resource/'
        });
        var name = 'error_'+'${language}'
        messageResource.load(name);
    });
</script>
</body>
</html>

