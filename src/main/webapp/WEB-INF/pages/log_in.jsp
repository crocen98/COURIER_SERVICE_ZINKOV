<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>


<fmt:requestEncoding value="UTF-8"/>

<fmt:setLocale value="${sessionScope.lang}"/>

<fmt:setBundle basename="language" var="bundle" scope="application"/>

<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Login</title>


    <link href="${pageContext.request.contextPath}/css/fontawesome-free/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

<script src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>

<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.easing.min.js"></script>

<script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>

<a class="sidebar-brand d-flex align-items-center justify-content-center" style="color:white; position: absolute; top: 20px; left: 20px"
       href="${pageContext.servletContext.contextPath}/">
    <div class="sidebar-brand-icon rotate-n-15">
        <i class="fas fa-laugh-wink fa-3x"></i>
    </div>
</a>

<div class="container">
    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            <strong>
                <fmt:message key="error.tag.letdown" bundle="${bundle}"/>
            </strong><fmt:message key="${param.error}" bundle="${bundle}"/>
        </div>
    </c:if>
    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4"><fmt:message key="activate.welcomeback"
                                                                                   bundle="${bundle}"/></h1>
                                </div>
                                <form class="user" action="${pageContext.request.contextPath}/index?command=log_in"
                                      method="POST" id="form_signup">
                                    <div class="form-group">
                                        <input required type="text" pattern="(\w|\d|-|_){1,35}"
                                               class="form-control form-control-user" name="login" value="client"
                                               placeholder="Enter login ..." id="logininput">
                                    </div>

                                    <div class="form-group">
                                        <input required type="password" pattern="(\w|\d|-|_){1,35}"
                                               class="form-control form-control-user" value="12345" name="password"
                                               placeholder="Password" id="exampleInputPassword">
                                    </div>

                                    <input type="hidden" id="password_hash" name="password_hash">

                                    <button class="btn btn-primary btn-user btn-block">
                                        <fmt:message key="form.login" bundle="${bundle}"/>
                                    </button>
                                </form>
                                <hr>
                                <div class="text-center">
                                    <a class="small"
                                       href="${pageContext.servletContext.contextPath}/index?command=to_password_recovery_page"><fmt:message
                                            key="signup.forgotpassword" bundle="${bundle}"/></a>
                                </div>
                                <div class="text-center">
                                    <a class="small"
                                       href="${pageContext.servletContext.contextPath}/index?command=sign_up">
                                        <fmt:message key="signup.formname" bundle="${bundle}"/>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>

</body>
<script src="${pageContext.request.contextPath}/js/sha256.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<script>
    hashPass("password_hash", "form_signup", "exampleInputPassword", "logininput");
</script>
</html>
