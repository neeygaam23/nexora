<%@ include file="../includes/header.jsp" %>
    <main class="page-frame">
        <section class="section-card">
            <div class="section-header">
                <h2>Manage Communities</h2>
                <p>Review and remove communities when needed.</p>
            </div>
            <div class="table-shell">
                <table class="management-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Creator ID</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty communities}">
                                <c:forEach var="community" items="${communities}">
                                    <tr>
                                        <td>${community.id}</td>
                                        <td>${community.name}</td>
                                        <td>${community.description}</td>
                                        <td>${community.creatorId}</td>
                                        <td>${community.createdAt}</td>
                                        <td>
                                            <form method="post"
                                                action="${pageContext.request.contextPath}/admin/manage-communities">
                                                <input type="hidden" name="communityId" value="${community.id}" />
                                                <input type="hidden" name="action" value="delete" />
                                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="6">No communities found.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
    <%@ include file="../includes/footer.jsp" %>