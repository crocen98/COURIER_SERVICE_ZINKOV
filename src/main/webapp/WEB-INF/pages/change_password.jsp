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
            <strong>Oh snap!</strong><fmt:message key="${param.error}" bundle="${bundle}"/>
        </div>
    </c:if>

    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-login-image "></div>   <!-- bg-login-image-->
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Enter new password</h1>
                                </div>
                                <form class="user" action="${pageContext.request.contextPath}/couriers?command=restore_password" method="POST">
                                    <div class="form-group">
                                        <input  required type="password" pattern="^(\w|\d|-|_){1,35}$" class="form-control form-control-user"   name="password" placeholder="Password">
                                    </div>

                                    <div class="form-group">
                                        <input  required type="password" pattern="(\w|\d|-|_){1,35}" class="form-control form-control-user"    placeholder="Repeat">
                                    </div>
                                    <input required id="keyInput" name="key" type="hidden" value="">
                                    <input required id="userIdInput" name="user_id" type="hidden" value="key">

                                    <div class="form-group">
                                    </div>
                                    <button  class="btn btn-primary btn-user btn-block">
                                        Change
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>

</body>
<script>
    var urlParams = new URLSearchParams(window.location.search);
    var key = urlParams.get("value");
    var userId = urlParams.get("user_id");
    document.getElementById("keyInput").setAttribute("value" , key);
    document.getElementById("userIdInput").setAttribute("value" , userId);

</script>
</html>
