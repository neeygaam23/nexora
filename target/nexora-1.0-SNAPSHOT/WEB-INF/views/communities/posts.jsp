<%@ include file="../includes/header.jsp" %>

<main class="page-frame">

    <!-- Page heading section -->
    <section class="page-hero">

        <!-- Main page title -->
        <h1>Community Discussions</h1>

        <!-- Short introduction text -->
        <p class="lede">
            Start a post, react to updates, and keep the conversation moving.
        </p>
    </section>

    <!-- Section for creating a new discussion post -->
    <section class="section-card">

        <div class="section-header">
            <h2>Create New Post</h2>
        </div>

        <!-- Form used to submit a new post -->
        <form method="post"
              action="${pageContext.request.contextPath}/communities/posts">

            <!-- Store current community ID -->
            <input type="hidden"
                   name="community_id"
                   value="${communityId}" />

            <!-- Post title input -->
            <div class="form-group">
                <label for="title">Title</label>

                <input type="text"
                       id="title"
                       name="title"
                       required />
            </div>

            <!-- Post body input -->
            <div class="form-group">
                <label for="body">Body</label>

                <textarea id="body"
                          name="body"
                          rows="5"
                          required></textarea>
            </div>

            <!-- Submit post button -->
            <button type="submit" class="btn primary-btn">
                Create Post
            </button>
        </form>
    </section>

    <!-- Section that displays all discussion posts -->
    <section class="section-card">

        <div class="section-header">
            <h2>Posts</h2>
        </div>

        <!-- Check whether posts exist -->
        <c:choose>

            <!-- Posts are available -->
            <c:when test="${not empty posts}">

                <div class="card-grid">

                    <!-- Loop through all posts -->
                    <c:forEach var="post" items="${posts}">

                        <!-- Individual post card -->
                        <div class="post-card">

                            <div class="card-content">

                                <!-- Post title -->
                                <h3>${post.title}</h3>

                                <!-- Post body -->
                                <p>${post.body}</p>

                                <!-- Author information -->
                                <p>
                                    <strong>Author ID:</strong>
                                    ${post.authorId}
                                </p>

                                <!-- Post creation date -->
                                <p>
                                    <strong>Created:</strong>
                                    ${post.createdAt}
                                </p>

                                <!-- Like and unlike section -->
                                <div class="reaction-bar">

                                    <!-- Reaction form -->
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/posts/reaction">

                                        <!-- Current post ID -->
                                        <input type="hidden"
                                               name="post_id"
                                               value="${post.id}" />

                                        <!-- Current community ID -->
                                        <input type="hidden"
                                               name="community_id"
                                               value="${communityId}" />

                                        <!-- Toggle like/unlike button -->
                                        <button type="submit"
                                                class="btn secondary-btn">

                                            <c:choose>

                                                <!-- If user already liked the post -->
                                                <c:when test="${userLikedMap[post.id]}">
                                                    Unlike
                                                </c:when>

                                                <!-- If user has not liked the post -->
                                                <c:otherwise>
                                                    Like
                                                </c:otherwise>

                                            </c:choose>
                                        </button>
                                    </form>

                                    <!-- Total reaction count -->
                                    <span>
                                        <strong>${reactionCountsMap[post.id]}</strong>
                                        likes
                                    </span>
                                </div>

                                <!-- Comment display section -->
                                <div class="comments-section">

                                    <h4>Comments</h4>

                                    <c:choose>

                                        <!-- Comments available -->
                                        <c:when test="${not empty commentsMap[post.id]}">

                                            <!-- Loop through comments -->
                                            <c:forEach var="comment"
                                                       items="${commentsMap[post.id]}">

                                                <!-- Single comment block -->
                                                <div class="comment-bubble">

                                                    <!-- Comment text -->
                                                    <p>${comment.body}</p>

                                                    <!-- Comment timestamp -->
                                                    <small>
                                                        Posted: ${comment.createdAt}
                                                    </small>
                                                </div>

                                            </c:forEach>

                                        </c:when>

                                        <!-- No comments available -->
                                        <c:otherwise>

                                            <p>
                                                <small>No comments yet.</small>
                                            </p>

                                        </c:otherwise>

                                    </c:choose>
                                </div>

                                <!-- Form for adding new comments -->
                                <form method="post"
                                      action="${pageContext.request.contextPath}/posts/comment">

                                    <!-- Hidden post ID -->
                                    <input type="hidden"
                                           name="post_id"
                                           value="${post.id}" />

                                    <!-- Hidden community ID -->
                                    <input type="hidden"
                                           name="community_id"
                                           value="${communityId}" />

                                    <!-- Comment text area -->
                                    <div class="form-group">

                                        <label for="comment-${post.id}">
                                            Comment
                                        </label>

                                        <textarea id="comment-${post.id}"
                                                  name="body"
                                                  rows="3"
                                                  required></textarea>
                                    </div>

                                    <!-- Comment submit button -->
                                    <button type="submit"
                                            class="btn secondary-btn">
                                        Comment
                                    </button>
                                </form>

                            </div>
                        </div>

                    </c:forEach>
                </div>

            </c:when>

            <!-- No posts available -->
            <c:otherwise>

                <div class="empty-state">
                    No posts yet. Be the first to share something.
                </div>

            </c:otherwise>

        </c:choose>
    </section>
</main>

<%@ include file="../includes/footer.jsp" %>