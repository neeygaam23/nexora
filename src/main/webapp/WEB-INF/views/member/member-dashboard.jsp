<%@ include file="../includes/header.jsp" %>

    <main class="member-dashboard">

        <!-- HERO SECTION -->
        <section class="hero-section">
            <div class="hero-content">
                <div>
                    <h1>Welcome to Nexora</h1>
                    <p>
                        Explore communities, learn new skills, connect with creators,
                        and even start your own community.
                    </p>

                    <div class="hero-buttons">
                        <a href="${pageContext.request.contextPath}/communities" class="btn primary-btn">
                            Explore Communities
                        </a>
                    </div>
                </div>

                <div class="hero-image">
                    <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" alt="Member">
                </div>
            </div>
        </section>

        <!-- QUICK ACTIONS -->
        <section class="quick-actions">
            <div class="action-card">
                <h3>Browse Communities</h3>
                <p>Discover communities that match your interests.</p>
                <a href="${pageContext.request.contextPath}/communities">Explore</a>
            </div>

            <div class="action-card">
                <h3>My Enrollments</h3>
                <p>View courses and communities you joined.</p>
                <a href="${pageContext.request.contextPath}/member/enrollments">View</a>
            </div>


        </section>

        <!-- RECOMMENDED COMMUNITIES -->
        <section class="dashboard-section">
            <div class="section-header">
                <h2>Recommended Communities</h2>
                <a href="${pageContext.request.contextPath}/communities">View All</a>
            </div>

            <div class="card-grid">
                <c:if test="${empty communities}">
                    <p>No communities yet.</p>
                </c:if>
                <c:forEach var="comm" items="${communities}">
                    <div class="community-card">
                        <div class="card-content">
                            <h3>${comm.name}</h3>
                            <p>${comm.description}</p>
                            <a href="${pageContext.request.contextPath}/communities/view?community_id=${comm.id}"
                                class="btn primary-btn">View</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

        <!-- RECOMMENDED COURSES -->
        <section class="dashboard-section">
            <div class="section-header">
                <h2>Recommended Courses</h2>
                <a href="${pageContext.request.contextPath}/courses/browse">Browse Courses</a>
            </div>

            <div class="card-grid">
                <c:if test="${empty enrolledCourses}">
                    <p>You have no enrollments yet.</p>
                </c:if>
                <c:forEach var="course" items="${enrolledCourses}">
                    <div class="course-card">
                        <div class="course-info">
                            <h3>${course.title}</h3>
                            <p>${course.description}</p>
                            <a href="${pageContext.request.contextPath}/courses/view?course_id=${course.id}"
                                class="btn primary-btn">View Course</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

    </main>

    <%@ include file="../includes/footer.jsp" %>