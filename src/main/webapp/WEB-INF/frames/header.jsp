<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<fmt:requestEncoding value="UTF-8"/>

<fmt:setLocale value="${sessionScope.lang}"/>


<fmt:setBundle basename="language" var="bundle" scope="application"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Dashboard</title>

    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/css/fontawesome-free/all.min.css" rel="stylesheet" type="text/css">


    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet">

</head>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center"
           href="${pageContext.servletContext.contextPath}/">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">
                ${sessionScope.user.firstName} ${sessionScope.user.lastName}
            </div>
        </a>
        <hr class="sidebar-divider my-0">
        <c:if test="${sessionScope.user.userRole eq 'CLIENT'}">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.servletContext.contextPath}/index?command=to_create_order_page">
                    <i class="fas fa-fw fa-chart-area"></i>
                    <span>new order</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.servletContext.contextPath}/index?command=to_user_order_page">
                    <i class="fas fa-fw fa-chart-area"></i>
                    <span>My order</span></a>
            </li>


            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.servletContext.contextPath}/index?command=to_client_couriers_page">
                    <i class="fas fa-fw fa-chart-area"></i>
                    <span>My couriers</span></a>
            </li>
        </c:if>

        <c:if test="${sessionScope.user.userRole eq 'COURIER'}">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.servletContext.contextPath}/index?command=edit_courier_profile_page">
                    <i class="fas fa-fw fa-chart-area"></i>
                    <span>Profile</span></a>
            </li>

            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.servletContext.contextPath}/index?command=to_courier_active_order_page">
                    <i class="fas fa-fw fa-chart-area"></i>
                    <span>Active order</span></a>
            </li>
        </c:if>


        <c:if test="${sessionScope.user.userRole eq 'ADMINISTRATOR'}">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.servletContext.contextPath}/index?command=all_transport_types">
                    <i class="fas fa-fw fa-chart-area"></i>
                    <span>Transport types</span></a>
            </li>

            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.servletContext.contextPath}/index?command=all_cargo_types">
                    <i class="fas fa-fw fa-chart-area"></i>
                    <span>Cargo types</span></a>
            </li>

            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.servletContext.contextPath}/index?command=to_all_users_page_command&page=1">
                    <i class="fas fa-fw fa-chart-area"></i>
                    <span>Users</span></a>
            </li>
        </c:if>

        <hr class="sidebar-divider d-none d-md-block">
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>

    </ul>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>
                <ul class="navbar-nav ml-auto">

                    <li class="nav-item dropdown no-arrow d-sm-none">
                        <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-search fa-fw"></i>
                        </a>
                        <!-- Dropdown - Messages -->
                        <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                             aria-labelledby="searchDropdown">
                            <form class="form-inline mr-auto w-100 navbar-search">
                                <div class="input-group">
                                    <input type="text" class="form-control bg-light border-0 small"
                                           placeholder="Search for..." aria-label="Search"
                                           aria-describedby="basic-addon2">
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="button">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </li>

                    <div class="topbar-divider d-none d-sm-block"></div>

                    <!-- Nav Item - User Information -->
                    <li class="nav-item dropdown no-arrow row h-100 justify-content-center align-items-center">

                            <div class="btn-group" style="margin-right: 10px">
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

                        <a class="btn btn-primary  dropdown-toggle "
                           href="${pageContext.servletContext.contextPath}/index/?command=log_out">Log out</a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="userDropdown">
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                Profile
                            </a>

                            <a class="dropdown-item" href="#">
                                <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                Settings
                            </a>
                            <a class="dropdown-item" href="#">
                                <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                                Activity Log
                            </a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                Logout
                            </a>
                        </div>
                    </li>

                </ul>

            </nav>

<c:if test="${param.error != null}">
    <div class="alert alert-danger" role="alert">
        <strong>Oh snap!</strong><fmt:message key="${param.error}" bundle="${bundle}"/>
    </div>
</c:if>
