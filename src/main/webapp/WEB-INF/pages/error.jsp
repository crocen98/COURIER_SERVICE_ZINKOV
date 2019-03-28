<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<fmt:requestEncoding value="UTF-8"/>

<fmt:setLocale value="${sessionScope.lang}"/>


<fmt:setBundle basename="language" var="bundle" scope="application"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>error 500</title>
    <link href="${pageContext.servletContext.contextPath}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="css/style_er.css" rel="stylesheet">


    <!-- Custom styles for this template-->
</head>

<body class="fix-header card-no-border">
<a class="btn btn-primary" style="position: relative; left: 20px; top:5px;"
   href="${pageContext.servletContext.contextPath}/">Main page</a>
<section id="wrapper" class="error-page">
    <div class="error-box">
        <div class="error-body text-center">
            <h1>500</h1>
            <h3 class="text-uppercase">Server error</h3>
        </div>
    </div>
</section>


</body>

</html>