<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
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
    <link rel="stylesheet" type="text/css" href="css/tooltipster.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>AirEpam</title>


</head>
<body class="main">
<div id="content">
    <%@include file="/jsp/header.jsp" %>
    <div id="welcome">
        <h1>
            <open><fmt:message key="index.open"/></open>
        </h1>
        <h2>

            <start><fmt:message key="index.start"/></start>
        </h2>
    </div>


    <div id="search-form" class="search">

        <div id="search-inner">
            <form action="/airepam" method="POST" id="searchForm">
                <input type="hidden" name="command" value="flights"/>
                <input type="hidden" name="return" id="return" value="true"/>
                <table>
                    <tr>
                        <td>
                            <input type="radio" name="radiog_dark" id="radio4" class="css-checkbox"
                                   onclick="hideReturn()"/><label for="radio4" class="css-label radGroup2"><fmt:message
                                key="index.one.way"/></label>
                        </td>
                        <td>
                            <input type="radio" name="radiog_dark" id="radio5" class="css-checkbox"
                                   onclick="openReturn()" checked="checked"/><label for="radio5"
                                                                                    class="css-label radGroup2"><fmt:message
                                key="index.return"/></label>
                        </td>
                    </tr>
                </table>


                <select class="form-control search-select" id="selectFrom" name="selectFrom">
                    <option></option>
                    <c:forEach var="element" items="${applicationScope.cities}">
                        <option><c:out value="${element.name}"/></option>
                    </c:forEach>
                </select>
                <i class="fa fa-arrow-right icon-black"></i>
                <select class="form-control search-select" id="selectTo" name="selectTo">
                    <option></option>
                </select>

                <div class="clearfix">

                    <div class='input-group date' id='datepicker' data-date-format="dd-mm-yyyy">
                        <input type='text' class="form-control" readonly='true' name="dateOut"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                    </div>

                    <div class='input-group date' id='datepicker2' data-date-format="dd-mm-yyyy">
                        <input type='text' class="form-control" readonly='true' name="dateIn"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                    </div>
                    <div>
                        <select class="form-control" name="count">
                            <option><fmt:message key="index.one"/></option>
                            <option><fmt:message key="index.two"/></option>
                            <option><fmt:message key="index.three"/></option>
                            <option><fmt:message key="index.four"/></option>
                            <option><fmt:message key="index.five"/></option>
                        </select>
                        <button type="submit" class="btn btn-info"><fmt:message key="index.search"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>
<ctg:footer/>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/jquery.validate.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
<script src="js/select2.js"></script>
<script src="js/scripts.js"></script>
<script src="js/controllers/indexController.js"></script>
<script type="text/javascript" src="js/jquery.tooltipster.min.js"></script>
<script>

    $(document).ready(function () {
        $(".search-select").select2();
        $("#selectFrom").select2({
            placeholder: "From",
            allowClear: true
        });
        $("#selectTo").select2({
            placeholder: "To",
            allowClear: true
        });
        $(".select2-container--default").css("width", "42%");
        $(".select2-container--default").css("margin", "10px");
        if (${not empty errorLoginPassMessage}) {
            $('#login-form').modal('show');
        }
    })

</script>
</body>
</html>