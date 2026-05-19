<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <main class="page-frame">
            <section class="page-hero">
                <h1>Browse Courses</h1>
                <p class="lede">Explore the latest courses shared by creators across the platform.</p>
            </section>

            <section class="card-grid">
                <c:if test="${empty courses}">
                    <div class="empty-state">No courses available yet.</div>
                </c:if>

                <c:forEach var="course" items="${courses}">
                    <article class="feature-card resource-card">
                        <h2 class="resource-title">${course.title}</h2>
                        <p>${course.description}</p>
                        <p class="resource-meta"><strong>Community:</strong> ${communityNames[course.communityId]}</p>
                        <a class="btn primary-btn"
                            href="${pageContext.request.contextPath}/courses/view?course_id=${course.id}">
                            View Course
                        </a>
                    </article>
                </c:forEach>
            </section>
        </main>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />