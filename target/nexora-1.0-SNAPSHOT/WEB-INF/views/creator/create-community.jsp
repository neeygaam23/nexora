<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <div class="container auth-page">
            <h1>Create Community</h1>
            <form method="post">
                <label>Name</label>
                <input name="name" required />
                <label>Description</label>
                <textarea name="description"></textarea>
                <div class="form-actions">
                    <button type="submit">Create</button>
                    <a class="secondary-link" href="${pageContext.request.contextPath}/creator/dashboard">Cancel</a>
                </div>
            </form>
        </div>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />