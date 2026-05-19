<%@ include file="../includes/header.jsp" %>

    <main class="page-frame">
        <section class="page-hero">
            <h1>Community Discussions</h1>
            <p class="lede">Start a post, react to updates, and keep the conversation moving.</p>
        </section>

        <section class="section-card">
            <div class="section-header">
                <h2>Create New Post</h2>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/communities/posts">
                <input type="hidden" name="community_id" value="${communityId}" />

                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" required />
                </div>

                <div class="form-group">
                    <label for="body">Body</label>
                    <textarea id="body" name="body" rows="5" required></textarea>
                </div>

                <button type="submit" class="btn primary-btn">Create Post</button>
            </form>
        </section>

        <section class="section-card">
            <div class="section-header">
                <h2>Posts</h2>
            </div>

            <c:choose>
                <c:when test="${not empty posts}">
                    <div class="card-grid">
                        <c:forEach var="post" items="${posts}">
                            <div class="post-card">
                                <div class="card-content">
                                    <h3>${post.title}</h3>
                                    <p>${post.body}</p>
                                    <p><strong>Author ID:</strong> ${post.authorId}</p>
                                    <p><strong>Created:</strong> ${post.createdAt}</p>

                                    <div class="reaction-bar">
                                        <form method="post" action="${pageContext.request.contextPath}/posts/reaction">
                                            <input type="hidden" name="post_id" value="${post.id}" />
                                            <input type="hidden" name="community_id" value="${communityId}" />
                                            <button type="submit" class="btn secondary-btn">
                                                <c:choose>
                                                    <c:when test="${userLikedMap[post.id]}">Unlike</c:when>
                                                    <c:otherwise>Like</c:otherwise>
                                                </c:choose>
                                            </button>
                                        </form>
                                        <span><strong>${reactionCountsMap[post.id]}</strong> likes</span>
                                    </div>

                                    <div class="comments-section">
                                        <h4>Comments</h4>
                                        <c:choose>
                                            <c:when test="${not empty commentsMap[post.id]}">
                                                <c:forEach var="comment" items="${commentsMap[post.id]}">
                                                    <div class="comment-bubble">
                                                        <p>${comment.body}</p>
                                                        <small>Posted: ${comment.createdAt}</small>
                                                    </div>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <p><small>No comments yet.</small></p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <form method="post" action="${pageContext.request.contextPath}/posts/comment">
                                        <input type="hidden" name="post_id" value="${post.id}" />
                                        <input type="hidden" name="community_id" value="${communityId}" />
                                        <div class="form-group">
                                            <label for="comment-${post.id}">Comment</label>
                                            <textarea id="comment-${post.id}" name="body" rows="3" required></textarea>
                                        </div>
                                        <button type="submit" class="btn secondary-btn">Comment</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        No posts yet. Be the first to share something.
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </main>

    <%@ include file="../includes/footer.jsp" %>