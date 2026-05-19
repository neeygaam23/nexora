<%@ include file="../includes/header.jsp" %>

    <main class="page-frame">
        <section class="page-hero">
            <h1>My Enrollments</h1>
            <p class="lede">Jump back into the courses you’ve already joined.</p>
        </section>

        <section class="section-card">
            <c:choose>
                <c:when test="${not empty enrolledCourses}">
                    <div class="card-grid">
                        <c:forEach var="course" items="${enrolledCourses}">

                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        You have not enrolled in any courses yet.
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </main>

    <%@ include file="../includes/footer.jsp" %>