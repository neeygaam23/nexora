<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <main class="page-frame">
            <section class="page-hero">
                <h1>${community.name}</h1>
                <p class="lede">${community.description}</p>
                <div class="hero-actions">
                    <c:choose>
                        <c:when test="${not empty sessionScope.currentUser}">
                            <c:choose>
                                <c:when test="${isMember}">
                                    <form method="post" action="${pageContext.request.contextPath}/communities/leave">
                                        <input type="hidden" name="community_id" value="${community.id}" />
                                        <button type="submit" class="btn secondary-btn">Leave Community</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form method="post" action="${pageContext.request.contextPath}/communities/join">
                                        <input type="hidden" name="community_id" value="${community.id}" />
                                        <button type="submit" class="btn">Join Community</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                            <a href="${pageContext.request.contextPath}/communities/posts?community_id=${community.id}"
                                class="btn secondary-btn">View Discussions</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/login" class="btn">Login to Join</a>
                            <a href="${pageContext.request.contextPath}/register" class="btn secondary-btn">Create
                                Account</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>

            <section class="section-card">
                <div class="section-header">
                    <h2>Courses</h2>
                </div>
                <div class="feature-grid">
                    <c:forEach var="course" items="${courses}">
                        <a class="feature-card resource-card"
                            href="${pageContext.request.contextPath}/courses/view?course_id=${course.id}">
                            <h3 class="resource-title">${course.title}</h3>
                            <p>${course.description}</p>
                        </a>
                    </c:forEach>
                    <c:if test="${empty courses}">
                        <div class="empty-state">No courses posted in this community yet.</div>
                    </c:if>
                </div>
            </section>
        </main>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />