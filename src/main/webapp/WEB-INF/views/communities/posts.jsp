<%@ include file="../includes/header.jsp" %>

<main class="page-frame">

    <!-- page title section -->
    <section class="page-hero">

        <!-- heading -->
        <h1>Community Discussions</h1>

        <!-- short intro -->
        <p class="lede">
            Start a post, react to updates, and keep the conversation moving.
        </p>
    </section>

    <!-- create post section -->
    <section class="section-card">

        <div class="section-header">
            <h2>Create New Post</h2>
        </div>

        <!-- form to submit a new post -->
        <form method="post"
              action="${pageContext.request.contextPath}/communities/posts">

            <!-- hidden community id -->
            <input type="hidden"
                   name="community_id"
                   value="${communityId}" />

            <!-- title input -->
            <div class="form-group">
                <label for="title">Title</label>

                <input type="text"
                       id="title"
                       name="title"
                       required />
            </div>

            <!-- body input -->
            <div class="form-group">
                <label for="body">Body</label>

                <textarea id="body"
                          name="body"
                          rows="5"
                          required></textarea>
            </div>

            <!-- submit button -->
            <button type="submit" class="btn primary-btn">
                Create Post
            </button>
        </form>
    </section>

    <!-- posts section -->
    <section class="section-card">

        <div class="section-header">
            <h2>Posts</h2>
        </div>

        <!-- check if posts exist -->
        <c:choose>

            <!-- when posts are available -->
            <c:when test="${not empty posts}">

                <div class="card-grid">

                    <!-- loop through each post -->
                    <c:forEach var="post" items="${posts}">

                        <!-- single post container -->
                        <div class="post-card">

                            <div class="card-content">

                                <!-- post title -->
                                <h3>${post.title}</h3>

                                <!-- post content -->
                                <p>${post.body}</p>

                                <!-- author details -->
                                <p>
                                    <strong>Author ID:</strong>
                                    ${post.authorId}
                                </p>

                                <!-- creation time -->
                                <p>
                                    <strong>Created:</strong>
                                    ${post.createdAt}
                                </p>

                                <!-- like/unlike section -->
                                <div class="reaction-bar">

                                    <!-- reaction form -->
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/posts/reaction">

                                        <!-- post id -->
                                        <input type="hidden"
                                               name="post_id"
                                               value="${post.id}" />

                                        <!-- community id -->
                                        <input type="hidden"
                                               name="community_id"
                                               value="${communityId}" />

                                        <!-- like/unlike button -->
                                        <button type="submit"
                                                class="btn secondary-btn">

                                            <c:choose>

                                                <!-- if already liked -->
                                                <c:when test="${userLikedMap[post.id]}">
                                                    Unlike
                                                </c:when>

                                                <!-- if not liked -->
                                                <c:otherwise>
                                                    Like
                                                </c:otherwise>

                                            </c:choose>
                                        </button>
                                    </form>

                                    <!-- total likes -->
                                    <span>
                                        <strong>${reactionCountsMap[post.id]}</strong>
                                        likes
                                    </span>
                                </div>

                                <!-- comments section -->
                                <div class="comments-section">

                                    <h4>Comments</h4>

                                    <c:choose>

                                        <!-- if comments exist -->
                                        <c:when test="${not empty commentsMap[post.id]}">

                                            <!-- loop comments -->
                                            <c:forEach var="comment"
                                                       items="${commentsMap[post.id]}">

                                                <!-- single comment -->
                                                <div class="comment-bubble">

                                                    <!-- comment text -->
                                                    <p>${comment.body}</p>

                                                    <!-- comment time -->
                                                    <small>
                                                        Posted: ${comment.createdAt}
                                                    </small>
                                                </div>

                                            </c:forEach>

                                        </c:when>

                                        <!-- if no comments -->
                                        <c:otherwise>

                                            <p>
                                                <small>No comments yet.</small>
                                            </p>

                                        </c:otherwise>

                                    </c:choose>
                                </div>

                                <!-- add comment form -->
                                <form method="post"
                                      action="${pageContext.request.contextPath}/posts/comment">

                                    <!-- post id -->
                                    <input type="hidden"
                                           name="post_id"
                                           value="${post.id}" />

                                    <!-- community id -->
                                    <input type="hidden"
                                           name="community_id"
                                           value="${communityId}" />

                                    <!-- comment input -->
                                    <div class="form-group">

                                        <label for="comment-${post.id}">
                                            Comment
                                        </label>

                                        <textarea id="comment-${post.id}"
                                                  name="body"
                                                  rows="3"
                                                  required></textarea>
                                    </div>

                                    <!-- submit comment -->
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

            <!-- when no posts exist -->
            <c:otherwise>

                <div class="empty-state">
                    No posts yet. Be the first to share something.
                </div>

            </c:otherwise>

        </c:choose>
    </section>
</main>

<%@ include file="../includes/footer.jsp" %>