<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../frames/header.jsp"/>

<fmt:requestEncoding value="UTF-8"/>


<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Cargo types</h1>
    </div>
    <%-- begin modal-forms--%>
    <div class="modal fade" id="modalLoginForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h4 class="modal-title w-100 font-weight-bold">New cargo type</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body mx-3">
                    <form class="md-form mb-5" method="POST" accept-charset=UTF-8"
                          action="${pageContext.servletContext.contextPath}/couriers?command=add_cargo_type">


                        <input required type="text" id="defaultForm-email" pattern="^(\w|\d|-|[a-яА-Я]){1,35}$"
                               class="form-control validate" name="cargo_type">
                        <label data-error="wrong" data-success="right" for="defaultForm-email">Cargo type
                            </label>
                        <div class="d-flex justify-content-center">
                            <button class="btn btn-primary">ADD CARGO TYPE</button>
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
                    <h4 class="modal-title w-100 font-weight-bold">Edit cargo</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body mx-3">
                    <form class="md-form mb-5" method="POST"
                          action="${pageContext.servletContext.contextPath}/couriers?command=edit_cargo_type">
                        <input type="text" required id="newName" pattern="^(\w|\d|-|[a-яА-Я]){1,35}$" name="cargo_type"
                               class="form-control validate">
                        <label data-error="wrong" data-success="right" for="newName">New name</label>
                        <input required id="changingTransportTypeId" name="cargo_type_id" type="hidden" value="">
                        <div class=" d-flex justify-content-center">
                            <button class="btn btn-primary">CHANGE CARGO TYPE</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="text-left">
        <a href="" class="btn btn-primary btn-rounded mb-4" data-toggle="modal" data-target="#modalLoginForm">
            Add new cargo type</a>
    </div>

    <div class="row">

        <style>

            input:invalid {
                border-color: red;
            }

            input:valid {
                border-color: green;
            }

            .typesTransport {
                height: 200px;
            }
        </style>


        <!-- Earnings (Monthly) Card Example -->
        <c:forEach var="elem" items="${requestScope.cargo_types}" varStatus="status">
            <div class="col-xl-3 col-md-6 mb-4 typesTransport">
                <div class="card border-left-primary shadow h-100 py-2">

                    <form name="transport_form_${elem.id}"
                          action="${pageContext.servletContext.contextPath}/couriers?command=delete_cargo_type"
                          method="POST">
                        <input name="cargo_type_id" type="hidden" value="${elem.id}">
                        <button class="btn" style="color:red;text-align: right; margin-right: -10px">
                            <i class="fas fa-window-close fa-2x "></i>
                        </button>
                    </form>
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <!-- <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Earnings (Monthly)</div> -->
                                Type:
                                <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out
                                        value="${elem.type}"/></div>
                            </div>
                            <a data-toggle="modal" class="btn d-flex justify-content-left editCargoTypeLink"
                               data-target="#modalDeleteForm">
                                <i class="fa fa-eraser fa-2x" aria-hidden="true"></i>
                                <input class="TransportTypeIdClass" type="hidden" value="${elem.id}">
                            </a>
                            <div class="col-auto">
                                <i class="fas fa-dice-d6 fa-2x text-gray-300"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <script>


        var links = document.getElementsByClassName("editCargoTypeLink")
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

