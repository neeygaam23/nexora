<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Common header section -->
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<main class="page-frame">

    <!-- Page introduction section -->
    <section class="page-hero">
        <h1>Communities</h1>

        <!-- Short description shown below the heading -->
        <p class="lede">
            Join spaces built by creators and discover active discussions around every community.
        </p>
    </section>

    <!-- Community listing section -->
    <section class="feature-grid">

        <!-- Loop through all available communities -->
        <c:forEach var="comm" items="${communities}">

            <!-- Each community card redirects user to community details page -->
            <a class="feature-card"
               href="${pageContext.request.contextPath}/communities/view?community_id=${comm.id}">

                <!-- Community name -->
                <h3>${comm.name}</h3>

                <!-- Community short description -->
                <p>${comm.description}</p>
            </a>

        </c:forEach>

        <!-- Display message when no communities are found -->
        <c:if test="${empty communities}">
            <div class="empty-state">
                No communities available yet.
            </div>
        </c:if>

    </section>
</main>

<!-- Common footer section -->
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />