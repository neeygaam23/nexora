<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- header -->
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<main class="page-frame">

    <!-- page title -->
    <section class="page-hero">
        <h1>Communities</h1>

        <!-- small intro text -->
        <p class="lede">
            Join spaces built by creators and discover active discussions around every community.
        </p>
    </section>

    <!-- community list -->
    <section class="feature-grid">

        <!-- loop all communities -->
        <c:forEach var="comm" items="${communities}">

            <!-- single community card -->
            <a class="feature-card"
               href="${pageContext.request.contextPath}/communities/view?community_id=${comm.id}">

                <!-- name -->
                <h3>${comm.name}</h3>

                <!-- description -->
                <p>${comm.description}</p>

            </a>

        </c:forEach>

        <!-- if no communities -->
        <c:if test="${empty communities}">
            <div class="empty-state">
                No communities available yet.
            </div>
        </c:if>

    </section>
</main>

<!-- footer -->
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />