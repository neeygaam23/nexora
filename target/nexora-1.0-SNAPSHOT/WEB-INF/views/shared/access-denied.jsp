<%@ include file="../includes/header.jsp" %>

<!-- Main content area for the access restriction page -->
<main class="page-frame">

    <!-- Page heading and message section -->
    <section class="page-hero">

        <!-- Main title shown when access is denied -->
        <h1>Access Denied</h1>

        <!-- Informative message for the user -->
        <p class="lede">You do not have permission to access this page.</p>
    </section>

    <!-- Section containing navigation action -->
    <section class="section-card">

        <!-- Button to redirect the user back to the homepage -->
        <a class="btn" href="${pageContext.request.contextPath}/">Return Home</a>
    </section>

</main>

<!-- Footer section included from shared layout -->
<%@ include file="../includes/footer.jsp" %>