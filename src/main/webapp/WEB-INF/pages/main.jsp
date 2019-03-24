<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<c:if test="${sessionScope.lang == null}">
    <fmt:setLocale value="en"/>
</c:if>

<fmt:setBundle basename="language" var="bundle" scope="application"/>
<html>
<head>
    <title>Landing Page - Start Bootstrap Theme</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="${pageContext.request.contextPath}/css/fontawesome-free/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/simple-line-icons/simple-line-icons.css" rel="stylesheet"
          type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet"
          type="text/css">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/css/landing-page.min.css" rel="stylesheet">

</head>

<body>

<!-- Navigation -->
<nav class="navbar navbar-light bg-light static-top">
    <div class="container d-flex justify-content-end">
        <div class="btn-group">
            <button type="button" class="btn btn-info"><fmt:message key="language.button" bundle="${bundle}"/></button>
            <button type="button" class="btn btn-info dropdown-toggle dropdown-toggle-split" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                <span class="sr-only">Toggle Dropdown</span>
            </button>

            <c:set var="parametersString" value="${pageContext.request.queryString}"/>
            <c:set var="lang" value="lang"/>
            <c:if test="${fn:contains(parametersString, lang)}">
                <c:set var="paramsStringsWithLangParameter" value="${fn:substringBefore(parametersString, lang)}"/>
                <c:set var="parametersString" value="${fn:substring(paramsStringsWithLangParameter, 0, fn:length(paramsStringsWithLangParameter) - 1)}"/>
            </c:if>

            <div class="dropdown-menu">
                <a class="dropdown-item" href="${requestScope.requestURI}?${parametersString}&lang=en">English</a>
                <a class="dropdown-item" href="${requestScope.requestURI}?${parametersString}&lang=ru">Русский</a>
            </div>
        </div>

        <%--<a class="navbar-brand" href="#">Language</a>--%>
        <a class="btn btn-primary" style="margin-left: 10px" href="${pageContext.servletContext.contextPath}/index?command=to_log_in_page">
            <fmt:message key="main.loginbuttom" bundle="${bundle}"/></a>
    </div>
</nav>

<!-- Masthead -->

<tag:error errorMap="errors"/>
<c:if test="${param.error != null}">
<div class="alert alert-danger" role="alert">
    <strong>
        <fmt:message key="error.tag.letdown" bundle="${bundle}"/>
    </strong><fmt:message key="${param.error}" bundle="${bundle}"/>
</div>
</c:if>
<header class="masthead text-white text-center">
    <div class="overlay"></div>
    <div class="container">
        <div class="row">
            <div class="col-xl-9 mx-auto">
                <h1 class="mb-5">
                    <fmt:message key="main.title" bundle="${bundle}"/>
                </h1>
            </div>
            <div class="col-md-10 col-lg-8 col-xl-7 mx-auto">
                <%--<form >--%>
                <%--<div class="form-row">--%>
                <div class="col-12 col-md-12">
                    <a class="btn btn-block btn-lg btn-primary"
                       href="${pageContext.servletContext.contextPath}/index?command=sign_up">
                        <fmt:message key="main.signpbuttom" bundle="${bundle}"/>
                    </a>
                </div>
                <%--</div>--%>
                <%--</form>--%>
            </div>
        </div>
    </div>
</header>

<!-- Icons Grid -->
<section class="features-icons bg-light text-center">
    <div class="container">
        <h2 style="margin-bottom: 40px">
            <fmt:message key="main.for_clients" bundle="${bundle}"/></h2>
        <div class="row">
            <div class="col-lg-4">
                <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                    <div class="features-icons-icon d-flex align-content-center">
                        <i class="fas fa-arrow-right m-auto text-primary"></i>
                    </div>
                    <h4>
                        <fmt:message key="main.1" bundle="${bundle}"/>
                    </h4>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                    <div class="features-icons-icon d-flex">
                        <i class="fas fa-arrow-right m-auto text-primary"></i>
                    </div>
                    <h4>
                        <fmt:message key="main.2" bundle="${bundle}"/>

                    </h4>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="features-icons-item mx-auto mb-0 mb-lg-3">
                    <div class="features-icons-icon m-auto text-primary" >
                        <i class="fas fa-flag-checkered"></i>
                    </div>
                    <h4 >
                        <fmt:message key="main.3" bundle="${bundle}"/>

                    </h4>
                </div>
            </div>
        </div>
    </div>
</section>


<section class="features-icons bg-light text-center">
    <div class="container">
        <h2 style="margin-bottom: 40px; ">
            <fmt:message key="main.for_couriers" bundle="${bundle}"/>
           </h2>
        <div class="row">
            <div class="col-lg-4">
                <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                    <div class="features-icons-icon d-flex align-content-center">
                        <i class="fas fa-arrow-right m-auto text-primary"></i>
                    </div>
                    <h4>
                        <fmt:message key="main.1с" bundle="${bundle}"/>
                    </h4>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                    <div class="features-icons-icon m-auto text-primary">
                        <i class="fas fa-arrow-right"></i>
                    </div>
                    <h4>
                        <fmt:message key="main.2с" bundle="${bundle}"/>

                    </h4>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="features-icons-item mx-auto mb-0 mb-lg-3">
                    <div class="features-icons-icon d-flex">
                        <i class="fas fa-flag-checkered m-auto text-primary"></i>
                    </div>
                    <h4>
                        <fmt:message key="main.3с" bundle="${bundle}"/>

                    </h4>
                </div>
            </div>
        </div>
    </div>
</section>


<!-- Image Showcases -->
<section class="showcase">
    <div class="container-fluid p-0">
        <div class="row no-gutters">

            <div class="col-lg-6 order-lg-2 text-white showcase-img"
                  id="firsttt"></div>
            <div class="col-lg-6 order-lg-1 my-auto showcase-text">
                <h2> <fmt:message key="main.title.autodetecting" bundle="${bundle}"/></h2>
                <p class="lead mb-0">
                    <fmt:message key="main.title.autodetecting.descr" bundle="${bundle}"/>
                </p>
            </div>
        </div>
        <div class="row no-gutters">
            <div class="col-lg-6 text-white showcase-img"  id="seconddd"></div>
            <div class="col-lg-6 my-auto showcase-text">
                <h2><fmt:message key="main.title.select_courier" bundle="${bundle}"/></h2>
                <p class="lead mb-0"><fmt:message key="main.title.select_courier.descr" bundle="${bundle}"/></p>
            </div>
        </div>
        <div class="row no-gutters">
            <div class="col-lg-6 order-lg-2 text-white showcase-img" id="thurddd"></div>
            <div class="col-lg-6 order-lg-1 my-auto showcase-text">
                <h2><fmt:message key="main.title.easy" bundle="${bundle}"/></h2>
                <p class="lead mb-0"><fmt:message key="main.title.easy.descr" bundle="${bundle}"/></p>
            </div>
        </div>
    </div>
</section>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.bundle.min.js"></script>


<!-- Bootstrap core JavaScript -->



