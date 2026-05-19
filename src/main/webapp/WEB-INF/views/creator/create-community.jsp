<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Shared top navigation and layout section --%>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<main class="page-frame">

    <!-- Intro section for community creation -->
    <section class="page-hero">
        <h1>Create Community</h1>
        <p class="lede">
            Set up a dedicated place for your audience and manage content more effectively.
        </p>
    </section>

    <!-- Community creation form -->
    <section class="section-card">

        <%-- Form submits community details to the same endpoint --%>
        <form method="post" class="community-form">

            <div class="form-group">
                <label for="name">Name</label>

                <%-- Community title input --%>
                <input
                        type="text"
                        id="name"
                        name="name"
                        placeholder="Enter community name"
                        required
                />
            </div>

            <div class="form-group">
                <label for="description">Description</label>

                <%-- Optional short summary about the community --%>
                <textarea
                        id="description"
                        name="description"
                        rows="5"
                        placeholder="Write a short description..."
                ></textarea>
            </div>

            <div class="form-actions">

                <%-- Saves the new community --%>
                <button type="submit">
                    Create
                </button>

                <%-- Returns user back to creator dashboard --%>
                <a
                        class="secondary-link"
                        href="${pageContext.request.contextPath}/creator/dashboard"
                >
                    Cancel
                </a>

            </div>

        </form>
    </section>

</main>

<%-- Shared footer section --%>
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />