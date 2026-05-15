<%@ include file="../includes/header.jsp" %>
    <main>
        <div class="card">
            <h2>Creator Dashboard</h2>
            <ul class="dashboard-list">
                <li><a href="${pageContext.request.contextPath}/creator/create-community">Create Community</a></li>
                <li><a href="${pageContext.request.contextPath}/creator/create-course">Create Course</a></li>
            </ul>
        </div>
    </main>
    <%@ include file="../includes/footer.jsp" %>