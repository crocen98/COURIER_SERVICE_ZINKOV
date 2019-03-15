<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<jsp:directive.attribute name="errorMap" required="true" description="map which contains error and fieldName value"/>
<c:if test="${not empty errorMap}">
    <c:set var="bean" value="${requestScope[errorMap]}"/>
            <c:forEach var="type" items="${bean}" varStatus="loop">
                <c:if test="${loop.first}">
                    <div class="alert alert-danger" role="alert" style="margin-top: 10px;" id="errorsBox">
                    <strong> <fmt:message key="error.tag.letdown" bundle="${bundle}"/></strong>
                    <br>
                </c:if>
                Error in ${type.key}: <fmt:message key="${type.value}" bundle="${bundle}"/>
                <br>
                <c:if test="${loop.last}">
                    <div id="delete_error_box" class="btn"
                         style="color:red; position: absolute; top:0; right:0; cursor: pointer">
                        <i class="fas fa-window-close fa-2x "></i>
                    </div>
                    </div>
                </c:if>
            </c:forEach>
</c:if>

<script>
    if (document.getElementById("delete_error_box")) {
        document.getElementById("delete_error_box").addEventListener("click", function () {
            var err = document.getElementById("errorsBox");
            err.style.opacity = 1.0;
            setInterval(function () {
                console.log(err.style.height);
                err.style.opacity -= 0.025;
                if (err.style.opacity < 0) {
                    err.remove();
                }
            }, 25);
        });
    }
</script>