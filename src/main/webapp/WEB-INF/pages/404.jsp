<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>404</title>
</head>




<link href="${pageContext.request.contextPath}/css/fontawesome-free/all.min.css" rel="stylesheet" type="text/css">


<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
      rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet">

<div class="container-fluid">

    <div class="text-center">
        <div class="error mx-auto" data-text="404">404</div>
        <p class="lead text-gray-800 mb-5"><fmt:message key="page404.not_found" bundle="${bundle}"/></p>
        <p class="text-gray-500 mb-0"><fmt:message key="page404.msg" bundle="${bundle}"/></p>
        <a href="${pageContext.servletContext.contextPath}/">&larr; <fmt:message key="page404.back_to_main" bundle="${bundle}"/></a>
    </div>

</div>





<jsp:include page="../frames/footer.jsp"/>
