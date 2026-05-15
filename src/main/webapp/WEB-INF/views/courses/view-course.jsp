<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <div class="container">
            <h1>${course.title}</h1>
            <p>${course.description}</p>

            <div class="form-actions">
                <c:if test="${isEnrolled}">
                    <form method="post" action="${pageContext.request.contextPath}/courses/unenroll">
                        <input type="hidden" name="course_id" value="${course.id}" />
                        <button type="submit">Unenroll</button>
                    </form>
                </c:if>
                <c:if test="${not isEnrolled}">
                    <form method="post" action="${pageContext.request.contextPath}/courses/enroll">
                        <input type="hidden" name="course_id" value="${course.id}" />
                        <button type="submit">Enroll</button>
                    </form>
                </c:if>
            </div>

            <h2>Materials</h2>
            <c:if test="${empty materials}">
                <p>No materials yet.</p>
            </c:if>
            <c:forEach var="m" items="${materials}">
                <div class="card">
                    <p>${m[1]}</p>
                    <a href="${pageContext.request.contextPath}/materials/download?id=${m[0]}">Download</a>
                </div>
            </c:forEach>

            <c:if test="${canUpload}">
                <h3>Upload Material</h3>
                <form method="post" action="${pageContext.request.contextPath}/creator/upload-material"
                    enctype="multipart/form-data">
                    <input type="hidden" name="course_id" value="${course.id}" />
                    <input type="file" name="file" />
                    <button type="submit">Upload</button>
                </form>
            </c:if>
        </div>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />