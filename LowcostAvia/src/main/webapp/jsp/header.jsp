<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<c:choose>
    <c:when test="${not empty language && language=='ru_RU'}">
<fmt:setLocale value="ru_RU" scope="session" />
        </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US" scope="session" />
        </c:otherwise>
    </c:choose>
<fmt:setBundle basename="pagecontent" />

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="airepam">&lt; AirEpam&gt;</a>
        </div>

        <div class="collapse navbar-collapse " id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav ">
                <li><a href="airepam?command=routes"><fmt:message key="label.routes" /> <span class="sr-only">(current)</span></a></li>
                <c:if test="${not empty role}">
                    <li><a href="airepam?command=manage"><fmt:message key="label.manage" /></a></li>
                </c:if>
                <c:if test="${role == 'admin'}">
                    <li><a href="airepam?command=openCreator"><fmt:message key="label.add" /></a></li>
                    <li><a href="airepam?command=routes&action=remove"><fmt:message key="label.delete" /></a></li>
                </c:if>
            </ul>


            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${not empty role}">
                        <li><a href="airepam?command=logout"><span class="glyphicon glyphicon-log-out"></span>
                            <fmt:message key="label.logout" /></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="#register" data-toggle="modal" data-target="#signup-form"><span
                                class="glyphicon glyphicon-user"></span><fmt:message key="label.register" /></a>
                        </li>
                        <li><a href="#login" data-toggle="modal" data-target="#login-form"><span
                                class="glyphicon glyphicon-log-in"></span>
                            <fmt:message key="label.login" /></a></li>
                    </c:otherwise>
                </c:choose>
                <li class="dropdown" id="locale">
                    <a  class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-expanded="false"><fmt:message key="header.language" /><span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a  onclick="changeToRus()"><fmt:message key="label.rus" /></a></li>
                        <li><a  onclick="changeToEng()"><fmt:message key="label.eng" /></a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div id="signup-form" class="modal fade" role="dialog">


    <div id="signup-inner" class="modal-dialog ">
        <div class="modal-content">
            <div class="clearfix modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <img id="signup-icon" src="./pictures/signup.png" alt=""/>


                <h3 class="modal-title"><fmt:message key="signup.title" /></h3>


            </div>


            <form action="/airepam" method="POST" id="signupForm" accept-charset="UTF-8">
                <input type="hidden" name="command" value="register"/>
                <div class="modal-body">
                    <p>

                        <label for="name"><fmt:message key="signup.name" /></label>
                        <input id="name" type="text" name="name" value=""/>
                    </p>

                    <p>
                        <label for="surname"><fmt:message key="signup.surname" /></label>
                        <input id="surname" type="text" name="surname" value=""/>
                    </p>

                    <p>

                        <label for="email"><fmt:message key="signup.email" /></label>
                        <input id="email" type="text" name="email" value=""/>
                    </p>

                    <p>
                        <label for="user"><fmt:message key="signup.username" /></label>
                        <input id="user" type="text" name="user"/>
                    </p>

                    <p>
                        <label for="pass" class=" control-label"><fmt:message key="signup.password" /></label>
                        <input id="pass" type="password" name="pass" value=""/>
                    </p>

                    <p>
                        <label class="control-label"><fmt:message key="signup.cardNumber" /></label>
                        <input type="number" name="number">
                    </p>

                    <p>
                        <label for="select" class="control-label"><fmt:message key="signup.cardType" /></label>
                        <select class="form-control" id="select" name="card">
                            <option>Visa</option>
                            <option>Mastercard</option>
                            <option>Visa Electron</option>
                            <option>Maestro</option>
                        </select>
                    </p>

                    <p>
                        <label for="inputAmount" class=" control-label"><fmt:message key="signup.amount" /></label>
                        <input type="number" min="100" max="10000" id="inputAmount" name="amount">

                    </p>


                    <p>
                    <div class="clearfix">
                        <button type="reset" class="btn btn-default"><fmt:message key="button.cancel" /></button>
                        <button type="submit" class="btn btn-primary"><fmt:message key="signup.signup" /></button>
                    </div>
                    </p>
                </div>
            </form>

            <div id="required" class="modal-footer">
                <p><fmt:message key="signup.required" /><br/>
                    <fmt:message key="signup.note" /></p>
            </div>
        </div>
    </div>
</div>


<div id="login-form" class="modal fade" role="dialog">
    <div id="login-inner" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title"><fmt:message key="label.login" /></h3>
            </div>
            <form action="/airepam" method="POST" id="loginForm">
                <input type="hidden" name="command" value="login"/>
                <c:if test="${not empty errorLoginPassMessage}">
                    <div class="alert alert-dismissible alert-danger container-fluid"
                         style="text-align: center; width:90%">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <strong><fmt:message key="errorLoginPassMessage" /></strong>
                    </div>
                </c:if>
                <div class="modal-body">
                    <p>
                        <label for="username"><fmt:message key="signup.username" /></label>
                        <input id="username" type="text" name="username"/>
                    </p>

                    <p>
                        <label for="password" class=" control-label"><fmt:message key="signup.password" /></label>
                        <input id="password" type="password" name="password" value=""/>
                    </p>

                    <p>
                    <div class="clearfix">
                        <button type="reset" class="btn btn-default" ><fmt:message key="button.cancel" /></button>
                        <button type="submit" class="btn btn-primary"><fmt:message key="label.login" /></button>
                    </div>
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>