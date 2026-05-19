<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <main class="page-frame">
            <section class="page-hero">
                <h1>Create Community</h1>
                <p class="lede">Set up a new space for your audience and start organizing content around it.</p>
            </section>

            <section class="section-card">
                <form method="post">
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input id="name" name="name" required />
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description"></textarea>
                    </div>
                    <div class="form-actions">
                        <button type="submit">Create</button>
                        <a class="secondary-link" href="${pageContext.request.contextPath}/creator/dashboard">Cancel</a>
                    </div>
                </form>
            </section>
        </main>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />