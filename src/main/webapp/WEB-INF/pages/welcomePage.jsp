<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<page>
    <body>
    <p>Welcome page</p>
    <c:url value="${friendUrl}" var="redirectUrl"/>
    <a href="http://localhost:8080/phoneshop_servlet_api_war_exploded/products">view</a>
    </body>
</page>
</html>

