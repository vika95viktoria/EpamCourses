<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link type="text/css" rel="stylesheet" href="style/bootstrap.min.css"/>
<html>
<head>
    <title>Result of Parsing</title></head>
<body style="padding: 5px">
<table class="table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Operator name</th>
        <th>Payroll</th>
        <th>InNet</th>
        <th>OutNet</th>
        <th>Landline</th>
        <th>sms</th>
        <th>favourites</th>
        <th>Tarification</th>
        <th>Payment</th>
    </tr>
    </thead>
    <c:forEach var="elem" items="${tariffs}">
        <tr>
            <td><c:out value="${ elem.name }"/></td>
            <td><c:out value="${ elem.operatorName }"/></td>
            <td><c:out value="${ elem.payroll }"/></td>
            <td><c:out value="${ elem.callPrices.inNet }"/></td>
            <td><c:out value="${ elem.callPrices.outNet }"/></td>
            <td><c:out value="${ elem.callPrices.landline }"/></td>
            <td><c:out value="${ elem.sms }"/></td>
            <td><c:out value="${ elem.parameters.favourites }"/></td>
            <td><c:out value="${ elem.parameters.tarification }"/></td>
            <td><c:out value="${ elem.parameters.payment }"/></td>
        </tr>
    </c:forEach>
</table>
<button class="btn btn-primary pull-right" style="margin-right: 5px; margin-bottom: 5px" type="button" name="Go back"
        onclick="history.back()">back
</button>
</body>
</html>
