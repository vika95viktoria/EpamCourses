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
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="../css/select2.css">
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
        <table id="routes" class="table table-striped" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th><h3><fmt:message key="deleteRoute.from"/></h3></th>
                <th><h3><fmt:message key="deleteRoute.to"/></h3></th>
                <th><h3><fmt:message key="routes.min"/> &euro;</h3></th>
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
                            <c:out value="${minPrice[status.index]}"/>
                        </td>
                        <td>
                            <a href="airepam?command=flights&routeId=${element.id}" role="button">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<ctg:footer/>
<script src="http://code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<script src="../js/bootstrap.min.js"></script>
<script src="../js/scripts.js"></script>
<script type="text/javascript" src="js/jquery.tooltipster.min.js"></script>
<script src="./js/jquery.validate.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
<script src="../js/select2.js"></script>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
        src="http://cdn.datatables.net/plug-ins/f3e99a6c02/integration/bootstrap/3/dataTables.bootstrap.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        $('#routes').DataTable();
    });

</script>
</body>
</html>
