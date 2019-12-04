<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Reviews">
    <form method="post">
        <table>
            <thead>
            <tr>
                <td>
                    Username
                </td>
                <td>
                    Rating
                </td>
                <td>
                    Text
                </td>
                <td>
                    Save
                </td>
            </tr>
            </thead>
            <c:forEach var="review" items="${reviewes}">
                <tr>
                    <td>
                            ${review.userName}
                    </td>
                    <td>
                            ${review.rating}
                    </td>
                    <td>
                            ${review.text}
                    </td>
                    <td>
                        <c:url value="/products/*" var="saveUrl"/>
                        <button formaction="${saveUrl}">Save</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</tags:master>
