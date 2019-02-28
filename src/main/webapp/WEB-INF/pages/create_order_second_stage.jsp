<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../frames/header.jsp"/>


<div class="container">

    <%--<div class="row">--%>
        <%--<c:forEach var="elem" items="${requestScope.couriers}" varStatus="status">--%>
            <%--<div class="col-xl-3 col-md-6 mb-4" style="height: 250px">--%>
                <%--<h2>${elem.login}</h2>--%>
                <%--<p>Image at the top (card-img-top):</p>--%>
                <%--<div class="card">--%>
                    <%--<img class="card-img-top" src="img_avatar1.png" alt="Card image">--%>
                    <%--<div class="card-body">--%>
                        <%--<h4 class="card-title">John Doe</h4>--%>
                        <%--<p class="card-text">Some example text some example text. John Doe is an architect and--%>
                            <%--engineer</p>--%>
                        <%--<a href="#" class="btn btn-primary">See Profile</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</c:forEach>--%>
    <%--</div>--%>


    <div class="row" id="courier_cards">
        <c:forEach var="elem" items="${requestScope.couriers}" varStatus="status">
            <div class="col-lg-4">
                <div class="our-team-main">

                    <div class="team-front">
                        <img src="http://placehold.it/110x110/9c27b0/fff?text=Dilip" class="img-fluid"/>
                        <h3>${elem.login}</h3>
                        <p>${elem.firstName} ${elem.lastName}</p>
                    </div>

                    <div class="team-back">
                        <div style="display: flex;   align-items: center; justify-content: center;">
                            <h1 class="mark_on_card" style="height: 100px;"> MARK: 10</h1>
                        </div>

                        <form name="transport_form_${elem.id}"
                              action="${pageContext.servletContext.contextPath}/couriers?command=finish_creating_order"
                              method="POST">
                            <input name="courier_id" type="hidden" value="${elem.id}">
                            <button class="btn" style="color:red;text-align: right; margin-right: -10px">
                                <i class="fa fa-plus fa-2x "></i> <h2 style="display: inline">Select</h2>
                            </button>
                        </form>
                    </div>

                </div>
            </div>
        </c:forEach>
    </div>


</div>

<style> .our-team-main {
    width: 100%;
    height: auto;
    background: #fff;
    text-align: center;
    border-radius: 10px;
    overflow: hidden;
    position: relative;
    transition: 0.5s;
    margin-bottom: 28px;
}

.mark_on_card {
    text-align: center;
    margin: auto;
    height: 100%;
}

#courier_cards {
    padding: 30px;
}

.our-team-main img {
    border-radius: 50%;
    margin-bottom: 20px;
    width: 90px;
}

.our-team-main h3 {
    font-size: 20px;
    font-weight: 700;
}

.our-team-main p {
    margin-bottom: 0;
}

.team-back {
    width: 100%;
    height: auto;
    position: absolute;
    top: 0;
    left: 0;
    padding: 5px 15px 0 15px;
    text-align: left;
    background: #fff;

}

.team-front {
    width: 100%;
    height: auto;
    position: relative;
    z-index: 10;
    background: #fff;
    padding: 15px;
    bottom: 0px;
    transition: all 0.5s ease;
}

.our-team-main:hover .team-front {
    bottom: -200px;
    transition: all 0.5s ease;
}

.our-team-main:hover {
    border-color: #777;
    transition: 0.5s;
}</style>
<jsp:include page="../frames/footer.jsp"/>