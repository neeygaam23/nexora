<%@ include file="../includes/header.jsp" %>
    <main class="page-frame">
        <section class="section-card">
            <div class="section-header">
                <h2>Manage Courses</h2>
                <p>Review platform courses and remove obsolete entries.</p>
            </div>
            <div class="table-shell">
                <table class="management-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Paid</th>
                            <th>Price</th>
                            <th>Creator ID</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty courses}">
                                <c:forEach var="course" items="${courses}">
                                    <tr>
                                        <td>${course.id}</td>
                                        <td>${course.title}</td>
                                        <td>${course.description}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${course.paid}">Yes</c:when>
                                                <c:otherwise>No</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${course.price}</td>
                                        <td>${course.creatorId}</td>
                                        <td>
                                            <form method="post"
                                                action="${pageContext.request.contextPath}/admin/manage-courses">
                                                <input type="hidden" name="courseId" value="${course.id}" />
                                                <input type="hidden" name="action" value="delete" />
                                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="7">No courses found.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
    <%@ include file="../includes/footer.jsp" %>