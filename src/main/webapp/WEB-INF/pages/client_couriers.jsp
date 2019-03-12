<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../frames/header.jsp"/>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<tag:error errorMap="errors"/>


<h3>My couriers</h3>
<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">First name</th>
        <th scope="col">Last name</th>
        <th scope="col">Login</th>
        <th scope="col">Phone</th>
        <th scope="col">Email</th>
        <th scope="col">Mark</th>
        <th scope="col">Your mark</th>


    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${requestScope.couriers}">
        <tr>
            <th scope="row">1</th>
            <td>${item.key.firstName}</td>
            <td>${item.key.lastName}</td>
            <td>${item.key.login}</td>
            <td>${item.key.phone}</td>
            <td>${item.key.email}</td>
            <td>${item.value}</td>
            <td>
                <div class="starrating risingstar d-flex justify-content-end flex-row-reverse">
                    <input type="radio" id="star5" name="rating" value="10"/><label for="star5" title="5 star">10</label>
                    <input type="radio" id="star4" name="rating" value="8"/><label for="star4" title="4 star">8</label>
                    <input type="radio" id="star3" name="rating" value="6"/><label for="star3" title="3 star">6</label>
                    <input type="radio" id="star2" name="rating" value="4"/><label for="star2" title="2 star">4</label>
                    <input type="radio" id="star1" name="rating" value="2"/><label for="star1" title="1 star">2</label>
                </div>
            </td>


        </tr>
    </c:forEach>
    </tbody>
</table>
<style>

    @import url(//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css);

    /* Styling h1 and links
    ––––––––––––––––––––––––––––––––– */
    h1[alt="Simple"] {color: white;}
    a[href], a[href]:hover {color: grey; font-size: 0.1em; text-decoration: none}


    body
    {
        background: #4a4a4c !important;
    }

    .starrating > input {display: none;}  /* Remove radio buttons */

    .starrating > label:before {
        content: "\f005"; /* Star */
        font-size: 2em;
        font-family: FontAwesome;
        display: inline-block;
    }

    .starrating > label
    {
        color: #222222; /* Start color when not clicked */
    }

    .starrating > input:checked ~ label
    { color: #ffca08 ; } /* Set yellow color when star checked */

    .starrating > input:hover ~ label
    { color: #ffca08 ;  } /* Set yellow color when star hover */


</style>
<jsp:include page="../frames/footer.jsp"/>