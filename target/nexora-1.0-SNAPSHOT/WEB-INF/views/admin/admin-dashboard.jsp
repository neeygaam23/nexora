<%@ include file="../includes/header.jsp" %>
    <main class="page-frame">
        <section class="page-hero">
            <h1>Admin Dashboard</h1>
            <p class="lede">Review platform content, moderate users, and keep the ecosystem tidy.</p>
        </section>

        <section class="feature-grid">
            <a class="feature-card" href="${pageContext.request.contextPath}/admin/manage-users">
                <h3>Manage Users</h3>
                <p>Deactivate or remove accounts that need attention.</p>
            </a>
            <a class="feature-card" href="${pageContext.request.contextPath}/admin/manage-communities">
                <h3>Manage Communities</h3>
                <p>Audit community content and structure.</p>
            </a>
            <a class="feature-card" href="${pageContext.request.contextPath}/admin/manage-courses">
                <h3>Manage Courses</h3>
                <p>Review published courses and platform inventory.</p>
            </a>
        </section>
    </main>
    <%@ include file="../includes/footer.jsp" %>