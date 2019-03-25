<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../frames/header.jsp"/>

<fmt:requestEncoding value="UTF-8"/>


<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">
            <fmt:message key="transport_types" bundle="${bundle}"/>
        </h1>
    </div>
    <div class="modal fade" id="modalLoginForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h4 class="modal-title w-100 font-weight-bold">
                        <fmt:message key="all_transport_types.new_transport_type" bundle="${bundle}"/>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body mx-3">
                    <form class="md-form mb-5" method="POST" accept-charset=UTF-8"
                          action="${pageContext.servletContext.contextPath}/index?command=add_transport_type">
                        <input required type="text" id="defaultForm-coefficient"
                               pattern="^[+-]?(\d){1,14}(\.(\d){0,2})|$"
                               class="form-control validate" name="coefficient">
                        <label data-error="wrong" data-success="right" for="defaultForm-coefficient">
                            <fmt:message key="all_transport_types.coefficient" bundle="${bundle}"/>
                        </label>

                        <input required type="text" id="defaultForm-email" pattern="^(\w|\d|-|[a-яА-Я]){1,35}$"
                               class="form-control validate" name="transport_name">
                        <label data-error="wrong" data-success="right" for="defaultForm-email">
                            <fmt:message key="all_transport_types.transport_name" bundle="${bundle}"/>

                            Transport
                            name</label>
                        <div class="d-flex justify-content-center">
                            <button class="btn btn-primary">
                                <fmt:message key="all_transport_types.add_transport_type" bundle="${bundle}"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="modalDeleteForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h4 class="modal-title w-100 font-weight-bold">
                        <fmt:message key="all_transport_types.edit_transport" bundle="${bundle}"/>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body mx-3">
                    <form class="md-form mb-5" method="POST"
                          action="${pageContext.servletContext.contextPath}/index?command=change_transport_type">
                        <i class="fas fa fa-pencil fa-2x prefix grey-text" aria-hidden="true"></i>


                        <input required type="text" id="defaultForm-coefficient2"
                               pattern="^[+-]?(\d){1,14}(\.(\d){0,2})|$"
                               class="form-control validate" name="coefficient">
                        <label data-error="wrong" data-success="right" for="defaultForm-coefficient">
                            <fmt:message key="all_transport_types.coefficient" bundle="${bundle}"/>
                        </label>
                        <input type="text" required id="newName" pattern="^(\w|\d|-|[a-яА-Я]){1,35}$"
                               name="transport_name"
                               class="form-control validate">
                        <label data-error="wrong" data-success="right" for="newName">
                            <fmt:message key="all_transport_types.transport_name" bundle="${bundle}"/>
                        </label>
                        <input required id="changingTransportTypeId" name="transport_type_id" type="hidden" value="">
                        <div class=" d-flex justify-content-center">
                            <button class="btn btn-primary">
                                <fmt:message key="all_transport_types.edit_transport_type" bundle="${bundle}"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <%-- end modal-forms--%>
    <div class="text-left">
        <a href="" class="btn btn-primary btn-rounded mb-4" data-toggle="modal" data-target="#modalLoginForm">
            <fmt:message key="all_transport_types.new_transport_type" bundle="${bundle}"/>
         </a>
    </div>

    <div class="row">
        <c:forEach var="elem" items="${requestScope.transport_types}" varStatus="status">
            <div class="col-xl-3 col-md-6 mb-4 typesTransport">
                <div class="card border-left-primary shadow h-100 py-2">

                    <form name="transport_form_${elem.id}"
                          action="${pageContext.servletContext.contextPath}/index?command=delete_transport_type"
                          method="POST">
                        <input name="transport_type_id" type="hidden" value="${elem.id}">
                        <button class="btn" style="color:red;text-align: right; margin-right: -10px">
                            <i class="fas fa-window-close fa-2x "></i>
                        </button>
                    </form>
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <!-- <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Earnings (Monthly)</div> -->
                                <fmt:message key="all_transport_types.type" bundle="${bundle}"/>
                                :
                                <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out
                                        value="${elem.transportType}"/></div>
                                <fmt:message key="all_transport_types.coefficient_word" bundle="${bundle}"/>
                                :
                                <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out
                                        value="${elem.coefficient}"/></div>
                            </div>
                            <a data-toggle="modal" class="btn d-flex justify-content-left editTransportTypeLink"
                               data-target="#modalDeleteForm">
                                <i class="fa fa-eraser fa-2x" aria-hidden="true"></i>
                                <input class="TransportTypeIdClass" type="hidden" value="${elem.id}">
                            </a>
                            <div class="col-auto">
                                <i class="fas fa-car fa-2x text-gray-300"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <script>


        var links = document.getElementsByClassName("editTransportTypeLink")
        for (var i = 0; i < links.length; ++i) {
            links[i].addEventListener('click', editTransportType, false);
        }

        function editTransportType(event) {
            var allElements = event.target.nextElementSibling;
            console.log(allElements);
            var valueAtr = allElements.getAttribute("value");
            console.log(valueAtr);
            document.getElementById("changingTransportTypeId").setAttribute("value", valueAtr);
        }
    </script>

    <jsp:include page="../frames/footer.jsp"/>

