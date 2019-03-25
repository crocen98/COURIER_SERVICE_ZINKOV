<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:requestEncoding value="UTF-8"/>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:setBundle basename="language" var="bundle" scope="application"/>


<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="https://api-maps.yandex.ru/2.1/?apikey=b9f00779-39b3-4da0-b8c3-becb9d63520e&lang=ru_RU"
            type="text/javascript">
    </script>
    <title>SB Admin 2 - Register</title>

    <link href="${pageContext.request.contextPath}/css/fontawesome-free/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>

<body class="bg-gradient-primary">

<div class="container">
    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            <strong>
                <fmt:message key="error.tag.letdown" bundle="${bundle}"/>
            </strong><fmt:message key="${param.error}" bundle="${bundle}"/>
        </div>
    </c:if>
    <tag:error errorMap="errors"/>
    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <div class="row">
                <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4"><fmt:message key="signup.formname"
                                                                           bundle="${bundle}"/></h1>
                        </div>
                        <form class="user" method="POST" id="form_signup"
                              action="${pageContext.servletContext.contextPath}/index?command=register_command">
                            <div class="form-group row">
                                <label for="sel1">
                                    <fmt:message key="signup.rolecheck" bundle="${bundle}"/>
                                </label>
                                <select class="form-control " style="border-radius: 15px;" id="sel1" name="user_role">
                                    <c:forEach var="elem" items="${user_roles}" varStatus="status">
                                        <option>${elem.role}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <div id="label_login">
                                    <label for="logininput" id="logininputlabel">
                                        <fmt:message key="signup.logincheck" bundle="${bundle}"/>
                                    </label>
                                </div>
                                <input id="logininput" oninput="loadXMLDoc(this)" required type="text"
                                       class="form-control form-control-user"
                                       pattern="(\w|\d|-|_){1,35}" name="login"
                                       placeholder="<fmt:message key="form.login" bundle="${bundle}"/>">
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input required type="text" class="form-control form-control-user"
                                           pattern="^(\w|\d|-|[a-яА-Я]){1,35}$" name="first_name"
                                           placeholder="<fmt:message key="form.firstname" bundle="${bundle}"/>">
                                </div>
                                <div class="col-sm-6">
                                    <input required type="text" class="form-control form-control-user"
                                           pattern="^(\w|\d|-|[a-яА-Я]){1,35}$" name="last_name"
                                           placeholder="<fmt:message key="form.lastnmae" bundle="${bundle}"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <input required type="email" pattern="^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$"
                                       class="form-control form-control-user" name="email"
                                       placeholder="<fmt:message key="form.email" bundle="${bundle}"/>">
                            </div>

                            <div class="form-group">
                                <input required type="phone"
                                       pattern="^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$"
                                       class="form-control form-control-user" name="phone"
                                       placeholder="<fmt:message key="form.phone" bundle="${bundle}"/>">
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input required type="password" pattern="(\w|\d|-|_){1,35}"
                                           class="form-control form-control-user"
                                           id="exampleInputPassword"
                                           placeholder="<fmt:message key="form.password" bundle="${bundle}"/>"
                                           name="password">
                                </div>
                                <input type="hidden" id="password_hash" name="password_hash">

                                <div class="col-sm-6">
                                    <input required type="password" pattern="(\w|\d|-|_){1,35}"
                                           class="form-control form-control-user"
                                           id="exampleRepeatPassword"
                                           placeholder="<fmt:message key="form.repeatpassword" bundle="${bundle}"/>">
                                </div>

                            </div>
                            <div class="form-group">
                                <div class="form-group">
                                    <input required type="text" class="form-control form-control-user"
                                           id="coordinates" disabled
                                           placeholder="<fmt:message key="form.location" bundle="${bundle}"/>">
                                    <input required id="сoordinatesInput" name="location" type="hidden"
                                           value="">
                                </div>

                            </div>
                            <div class=" d-flex justify-content-center">
                                <button id="but" disabled id="but_sign_up" class="btn btn-primary btn-user btn-block">
                                    <fmt:message key="signup.formname" bundle="${bundle}"/>
                                </button>
                            </div>
                        </form>
                        <hr>
                        <div class="text-center">
                            <a class="small"
                               href="${pageContext.servletContext.contextPath}/index?command=to_password_recovery_page"><fmt:message
                                    key="signup.forgotpassword" bundle="${bundle}"/></a>
                        </div>
                        <div class="text-center">
                            <a class="small"
                               href="${pageContext.servletContext.contextPath}/index?command=to_log_in_page"><fmt:message
                                    key="signup.alreadyhaveaccount" bundle="${bundle}"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="map" style="width: 100%; height: 400px"></div>
    </div>

</div>
<script src="${pageContext.request.contextPath}/js/sha256.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.bundle.min.js"></script>

<script src="${pageContext.request.contextPath}/js/jquery/jquery.easing.min.js"></script>

<script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>
<script>
    hashPass("password_hash", "form_signup", "exampleInputPassword", "logininput");
</script>

<script src="${pageContext.request.contextPath}/js/sigh_up.js"></script>




</body>

</html>
