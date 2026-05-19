<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Common header section used across creator pages --%>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<main class="page-frame">

    <!-- Page introduction -->
    <section class="page-hero">
        <h1>Create Course</h1>

        <p class="lede">
            Publish a course under your community and optionally upload a launch video during setup.
        </p>
    </section>

    <!-- Main course creation form -->
    <section class="section-card">

        <%-- Multipart form required for video uploads --%>
        <form method="post"
              enctype="multipart/form-data"
              class="course-create-form">

            <!-- Course title -->
            <div class="form-group">
                <label for="title">Title</label>

                <input
                        type="text"
                        id="title"
                        name="title"
                        placeholder="Enter course title"
                        required
                />
            </div>

            <!-- Course description -->
            <div class="form-group">
                <label for="description">Description</label>

                <textarea
                        id="description"
                        name="description"
                        rows="5"
                        placeholder="Write a short overview of the course"
                ></textarea>
            </div>

            <!-- Community selection -->
            <div class="form-group">
                <label for="community_id">Community</label>

                <select
                        id="community_id"
                        name="community_id"
                        required
                >
                    <option value="">
                        Select a community
                    </option>

                    <%-- Dynamically load creator communities --%>
                    <c:forEach var="community" items="${communities}">
                        <option value="${community.id}">
                            ${community.name}
                        </option>
                    </c:forEach>

                </select>
            </div>

            <!-- Optional course intro video -->
            <div class="form-group">

                <label for="video_file">
                    Optional Course Video
                </label>

                <input
                        id="video_file"
                        type="file"
                        name="video_file"
                        accept="video/*,.mp4,.webm,.ogg,.mov,.mkv"
                />

                <small class="muted">
                    Upload a video now if you want it attached immediately after course creation.
                </small>

            </div>

            <!-- Form actions -->
            <div class="form-actions">

                <%-- Submit course details --%>
                <button type="submit">
                    Create
                </button>

                <%-- Navigate back to creator dashboard --%>
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

<%-- Shared footer include --%>
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />