<%@ include file="../includes/header.jsp" %>
    <main class="page-frame">
        <section class="section-card">
            <div class="section-header">
                <h2>Manage Users</h2>
                <p>Moderate user access and keep the platform safe.</p>
            </div>
            <div class="table-shell">
                <table class="management-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty users}">
                                <c:forEach var="user" items="${users}">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td>${user.username}</td>
                                        <td>${user.email}</td>
                                        <td>${user.roleName}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.active}">Active</c:when>
                                                <c:otherwise>Inactive</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="inline-actions">
                                                <form method="post"
                                                    action="${pageContext.request.contextPath}/admin/manage-users">
                                                    <input type="hidden" name="userId" value="${user.id}" />
                                                    <input type="hidden" name="action" value="deactivate" />
                                                    <button type="submit" class="btn primary-btn">Deactivate</button>
                                                </form>
                                                <form method="post"
                                                    action="${pageContext.request.contextPath}/admin/manage-users">
                                                    <input type="hidden" name="userId" value="${user.id}" />
                                                    <input type="hidden" name="action" value="delete" />
                                                    <button type="submit" class="btn secondary-btn">Delete</button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="6">No users found.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
    <%@ include file="../includes/footer.jsp" %>