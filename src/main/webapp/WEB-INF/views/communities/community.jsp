<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <div class="container">
            <h1>${community.name}</h1>
            <p>${community.description}</p>

            <c:choose>
                <c:when test="${isMember}">
                    <form method="post" action="${pageContext.request.contextPath}/communities/leave">
                        <input type="hidden" name="community_id" value="${community.id}" />
                        <button type="submit">Leave Community</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="post" action="${pageContext.request.contextPath}/communities/join">
                        <input type="hidden" name="community_id" value="${community.id}" />
                        <button type="submit">Join Community</button>
                    </form>
                </c:otherwise>
            </c:choose>

            <h2>Courses</h2>
            <div class="dashboard-list">
                <c:forEach var="course" items="${courses}">
                    <div class="card">
                        <h3><a
                                href="${pageContext.request.contextPath}/courses/view?course_id=${course.id}">${course.title}</a>
                        </h3>
                        <p>${course.description}</p>
                    </div>
                </c:forEach>
            </div>
        </div>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />