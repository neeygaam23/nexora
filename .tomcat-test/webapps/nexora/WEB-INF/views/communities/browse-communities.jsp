<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <div class="container">
            <h1>Communities</h1>
            <div class="dashboard-list">
                <c:forEach var="comm" items="${communities}">
                    <div class="card">
                        <h3><a
                                href="${pageContext.request.contextPath}/communities/view?community_id=${comm.id}">${comm.name}</a>
                        </h3>
                        <p>${comm.description}</p>
                    </div>
                </c:forEach>
            </div>
        </div>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />