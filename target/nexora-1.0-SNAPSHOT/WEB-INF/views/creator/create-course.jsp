<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <main class="page-frame">
            <section class="page-hero">
                <h1>Create Course</h1>
                <p class="lede">Publish a course inside one of your communities and attach a launch video in the same
                    step.</p>
            </section>

            <section class="section-card">
                <form method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input id="title" name="title" required />
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="community_id">Community</label>
                        <select id="community_id" name="community_id" required>
                            <option value="">Select a community</option>
                            <c:forEach var="community" items="${communities}">
                                <option value="${community.id}">${community.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="video_file">Optional Course Video</label>
                        <input id="video_file" type="file" name="video_file"
                            accept="video/*,.mp4,.webm,.ogg,.mov,.mkv" />
                        <small class="muted">Upload a video now to attach it to the course immediately after
                            creation.</small>
                    </div>
                    <div class="form-actions">
                        <button type="submit">Create</button>
                        <a class="secondary-link" href="${pageContext.request.contextPath}/creator/dashboard">Cancel</a>
                    </div>
                </form>
            </section>
        </main>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />