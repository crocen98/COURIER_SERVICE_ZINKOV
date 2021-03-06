<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../frames/header.jsp"/>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<tag:error errorMap="errors"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<h3>My couriers</h3>
<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">
            <fmt:message key="form.firstname" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="form.lastnmae" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="form.login" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="form.phone" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="form.email" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="client_couriers.mark" bundle="${bundle}"/>
        </th>
        <th scope="col">
            <fmt:message key="client_couriers.your_mark" bundle="${bundle}"/>
        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${requestScope.couriers}" varStatus="loop">
        <tr>
            <th scope="row">${loop.index}</th>
            <td>${item.key.firstName}</td>
            <td>${item.key.lastName}</td>
            <td>${item.key.login}</td>
            <td>${item.key.phone}</td>
            <td>${item.key.email}</td>
            <td>${item.value}</td>
            <td>
                <form method="POST" action="${pageContext.servletContext.contextPath}/index?command=set_user_mark">
                    <div class="starrating risingstar d-flex justify-content-end flex-row-reverse radioStar">
                        <button class="btn btn-warning" style="margin-left: 5px;"><fmt:message key="send_review" bundle="${bundle}"/>
                        </button>
                        <input type="radio" id="star5_${item.key.id}" name="rating" value="10"/><label for="star5_${item.key.id}"
                                                                                        title="5 star">10</label>
                        <input type="radio" id="star4_${item.key.id}" name="rating" value="8"/><label for="star4_${item.key.id}"
                                                                                       title="4 star">8</label>
                        <input type="radio" id="star3_${item.key.id}" name="rating" value="6"/><label for="star3_${item.key.id}"
                                                                                       title="3 star">6</label>
                        <input type="radio" id="star2_${item.key.id}" name="rating" value="4"/><label for="star2_${item.key.id}"
                                                                                       title="2 star">4</label>
                        <input type="radio" id="star1_${item.key.id}" name="rating" value="2"/><label for="star1_${item.key.id}"
                                                                                       title="1 star">2</label>
                        <input type="hidden" name="courier_id" value="${item.key.id}">
                    </div>
                </form>
            </td>


        </tr>
    </c:forEach>
    </tbody>
</table>
<style>

    @import url(//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css);

    .radioStar h1[alt="Simple"] {
        color: white;
    }

    .radioStar a[href], .radioStar a[href]:hover {
        color: grey;
        font-size: 0.1em;
        text-decoration: none
    }

    body {
        background: #4a4a4c !important;
    }

    .starrating > input {
        display: none;
    }
    .starrating > label:before {
        content: "\f005"; /* Star */
        font-size: 2em;
        font-family: FontAwesome;
        display: inline-block;
    }

    .starrating > label {
        color: #222222;
    }

    .starrating > input:checked ~ label {
        color: #ffca08;
    }

    .starrating > input:hover ~ label {
        color: #ffca08;
    }
</style>
<jsp:include page="../frames/footer.jsp"/>