<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>

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

</head>

<body class="bg-gradient-primary">

<script src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>

<!-- Bootstrap core JavaScript-->
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.easing.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>

<!-- Custom scripts for all pages-->
<div class="container">
    <!-- Outer Row -->
    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            <strong>Oh snap!</strong> ${param.error}
        </div>
    </c:if>
    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Restore password!</h1>
                                </div>
                                <form class="user" action="${pageContext.request.contextPath}/couriers?command=send_restore_token" method="POST">
                                    <div class="form-group">
                                        <input required type="text" pattern="(\w|\d|-){1,35}" class="form-control form-control-user" name="login"   placeholder="Enter login ...">
                                    </div>

                                    <div class="form-group">
                                        <input required type="email" class="form-control form-control-user" name="email"
                                               placeholder="Email Address">
                                    </div>

                                    <div class="form-group">
                                        <input required type="phone"
                                               pattern="^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$"
                                               class="form-control form-control-user" name="phone"
                                               placeholder="Phone">
                                    </div>
                                    <div class="form-group">
                                        <div class="custom-control custom-checkbox small">
                                            <input type="checkbox" class="custom-control-input" id="customCheck">
                                            <label class="custom-control-label" for="customCheck">Remember Me</label>
                                        </div>
                                    </div>
                                    <button  class="btn btn-primary btn-user btn-block">
                                        Restore
                                    </button>
                                </form>
                                <hr>
                                <div class="text-center">
                                    <a class="small" href="${pageContext.servletContext.contextPath}/couriers?command=to_log_in_page">Already have an account? Login!</a>
                                </div>
                                <div class="text-center">
                                    <a class="small" href="${pageContext.servletContext.contextPath}/couriers?command=sign_up">Create an Account!</a>
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

</html>