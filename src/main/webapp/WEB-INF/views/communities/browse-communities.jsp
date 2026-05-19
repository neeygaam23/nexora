<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <main class="page-frame">
            <section class="page-hero">
                <h1>Communities</h1>
                <p class="lede">Join spaces built by creators and discover active discussions around every community.
                </p>
            </section>

            <section class="feature-grid">
                <c:forEach var="comm" items="${communities}">
                    <a class="feature-card"
                        href="${pageContext.request.contextPath}/communities/view?community_id=${comm.id}">
                        <h3>${comm.name}</h3>
                        <p>${comm.description}</p>
                    </a>
                </c:forEach>
                <c:if test="${empty communities}">
                    <div class="empty-state">No communities available yet.</div>
                </c:if>
            </section>
        </main>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />