<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:choose>
    <c:when test="${login_status == false}">
        <label style="color: red" class="disabled" for="logininput" id="logininputlabel"><fmt:message key="error.logincheck" bundle="${bundle}"/></label>
    </c:when>
    <c:when test="${login_status == true}">
        <label style="color: green"  for="logininput" id="logininputlabel"><fmt:message key="info.logincheck" bundle="${bundle}"/></label>
    </c:when>
</c:choose>

