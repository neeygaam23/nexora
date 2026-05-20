<%-- Shared header section --%>
<%@ include file="../includes/header.jsp" %>

<main class="page-frame">

    <!-- Dashboard top section -->
    <section class="page-hero">
        <h1>Creator Dashboard</h1>

        <p class="lede">
            Manage your communities, publish courses, and organize everything from one place.
        </p>

        <div class="hero-actions">
            <a href="${pageContext.request.contextPath}/creator/create-community" class="btn">
                Create Community
            </a>

            <a href="${pageContext.request.contextPath}/creator/create-course"
               class="btn secondary-btn">
                Create Course
            </a>
        </div>
    </section>

    <!-- Community management area -->
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

                    <%-- Show communities only if available --%>
                    <c:choose>

                        <c:when test="${not empty communities}">

                            <%-- Loop through all creator communities --%>
                            <c:forEach items="${communities}" var="community">

                                <tr>
                                    <td>${community.name}</td>

                                    <td>
                                        ${community.description}
                                    </td>

                                    <td>

                                        <%-- Delete community form --%>
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/creator/delete-community">

                                            <input type="hidden"
                                                   name="community_id"
                                                   value="${community.id}" />

                                            <button type="submit"
                                                    class="btn secondary-btn">
                                                Delete
                                            </button>

                                        </form>

                                    </td>
                                </tr>

                            </c:forEach>

                        </c:when>

                        <%-- Empty state --%>
                        <c:otherwise>

                            <tr>
                                <td colspan="3">
                                    No communities found.
                                </td>
                            </tr>

                        </c:otherwise>

                    </c:choose>

                </tbody>

            </table>

        </div>

    </section>

    <!-- Course management section -->
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

                    <%-- Check whether courses exist --%>
                    <c:choose>

                        <c:when test="${not empty courses}">

                            <%-- Display all available courses --%>
                            <c:forEach items="${courses}" var="course">

                                <tr>

                                    <td>${course.title}</td>

                                    <td>
                                        ${course.description}
                                    </td>

                                    <td>

                                        <%-- Display paid/free status --%>
                                        <c:choose>

                                            <c:when test="${course.paid}">
                                                Yes
                                            </c:when>

                                            <c:otherwise>
                                                No
                                            </c:otherwise>

                                        </c:choose>

                                    </td>

                                    <td>${course.price}</td>

                                    <td>

                                        <%-- Delete course action --%>
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/creator/delete-course">

                                            <input type="hidden"
                                                   name="course_id"
                                                   value="${course.id}" />

                                            <button type="submit"
                                                    class="btn secondary-btn">
                                                Delete
                                            </button>

                                        </form>

                                    </td>

                                </tr>

                            </c:forEach>

                        </c:when>

                        <%-- If no courses exist --%>
                        <c:otherwise>

                            <tr>
                                <td colspan="5">
                                    No courses found.
                                </td>
                            </tr>

                        </c:otherwise>

                    </c:choose>

                </tbody>

            </table>

        </div>

    </section>

</main>

<%-- Shared footer section --%>
<%@ include file="../includes/footer.jsp" %>