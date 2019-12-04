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
            </tr>
            </thead>
        </table>
                <c:if test="${not empty param.successReview}">
                    <p>
                        <span style="color: green">Successfully added</span>
                    </p>
                </c:if>
                <c:if test="${not empty param.reviewErrorMessage}">
                    <p>
                        <span style="color: red">Input error: ${param.reviewErrorMessage}</span>
                    </p>
                </c:if>
                <p>
                    <label for="username">Username</label>
                    <input id="username" name="username" value="${username}"/>
                </p>
                <p>
                    <label for="rating">Rating</label>
                    <input id="rating" name="rating" value="${rating}"/>
                </p>
                <p>
                    <label for="text">Text</label>
                    <input id="text" name="text" value="${textError}"/>
                </p>

                <p>
                    <c:url value="/products" var="saveUrl"/>
                        <button type="submit">Add</button>
                        <c:if test="${not empty errorMessage}">
                        <br>
        <p class="class-color-red">${errorMessage}</p>
        </c:if>
                </p>
            </form>

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
                </tr>
            </c:forEach>
        </table>
    </form>
</tags:master>
