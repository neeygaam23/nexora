<%@ include file="../includes/header.jsp" %>
    <main class="page-frame">
        <section class="page-hero">
            <h1>Creator Dashboard</h1>
            <p class="lede">Manage your communities, publish courses, and keep everything in one clear workspace.</p>
            <div class="hero-actions">
                <a href="${pageContext.request.contextPath}/creator/create-community" class="btn">Create Community</a>
                <a href="${pageContext.request.contextPath}/creator/create-course" class="btn secondary-btn">Create
                    Course</a>
            </div>
        </section>

        <section class="section-card">
            <div class="section-header">
                <h2>My Communities</h2>
            </div>
            <div class="table-shell">
                <table class="management-table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty communities}">
                                <c:forEach var="community" items="${communities}">
                                    <tr>
                                        <td>${community.name}</td>
                                        <td>${community.description}</td>
                                        <td>
                                            <form method="post"
                                                action="${pageContext.request.contextPath}/creator/delete-community">
                                                <input type="hidden" name="community_id" value="${community.id}" />
                                                <button type="submit" class="btn secondary-btn">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="3">No communities found.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </section>

        <section class="section-card">
            <div class="section-header">
                <h2>My Courses</h2>
            </div>
            <div class="table-shell">
                <table class="management-table">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Paid</th>
                            <th>Price</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty courses}">
                                <c:forEach var="course" items="${courses}">
                                    <tr>
                                        <td>${course.title}</td>
                                        <td>${course.description}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${course.paid}">Yes</c:when>
                                                <c:otherwise>No</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${course.price}</td>
                                        <td>
                                            <form method="post"
                                                action="${pageContext.request.contextPath}/creator/delete-course">
                                                <input type="hidden" name="course_id" value="${course.id}" />
                                                <button type="submit" class="btn secondary-btn">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5">No courses found.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
    <%@ include file="../includes/footer.jsp" %>