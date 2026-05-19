<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <main class="page-frame landing-page">
            <div class="landing-orb landing-orb-one"></div>
            <div class="landing-orb landing-orb-two"></div>

            <section class="landing-hero page-hero">
                <div class="landing-hero-copy">
                    <span class="hero-badge">Learn. Create. Connect.</span>
                    <h1>Nexora turns communities into living learning spaces.</h1>
                    <p class="lede">Discover communities, join creator-led courses, and follow discussions in one
                        platform built for modern learners and builders.</p>
                    <div class="hero-actions">
                        <a class="btn" href="${pageContext.request.contextPath}/login">Login</a>
                        <a class="btn secondary-btn" href="${pageContext.request.contextPath}/register">Register</a>
                        <a class="btn secondary-btn" href="${pageContext.request.contextPath}/communities">Browse
                            Communities</a>
                    </div>
                    <div class="hero-points">
                        <span>Creator-led learning</span>
                        <span>Video-ready courses</span>
                        <span>Social discussions</span>
                    </div>
                </div>
                <div class="landing-hero-panel">
                    <div class="hero-visual-card">
                        <div class="hero-visual-topline">Platform Snapshot</div>
                        <div class="stat-grid">
                            <div class="stat-card">
                                <strong>${communityCount}</strong>
                                <span>Communities available</span>
                            </div>
                            <div class="stat-card">
                                <strong>Video</strong>
                                <span>Courses can include media</span>
                            </div>
                            <div class="stat-card">
                                <strong>Live</strong>
                                <span>Comments and reactions</span>
                            </div>
                        </div>
                        <div class="hero-visual-footnote">A clean front door for learners, creators, and communities.
                        </div>
                    </div>
                </div>
            </section>

            <section class="feature-grid feature-grid-tight">
                <article class="feature-card highlight-card">
                    <h3>For New Visitors</h3>
                    <p>Preview active communities, understand the platform, and create an account when ready.</p>
                </article>
                <article class="feature-card highlight-card">
                    <h3>For Members</h3>
                    <p>Join communities, follow courses, react to posts, and keep learning in one place.</p>
                </article>
                <article class="feature-card highlight-card">
                    <h3>For Creators</h3>
                    <p>Publish communities, upload video-backed courses, and build an audience around your content.</p>
                </article>
            </section>

            <section class="section-card">
                <div class="section-header">
                    <div>
                        <h2>Featured Communities</h2>
                        <p>Preview what is active on Nexora. Login or register to join one.</p>
                    </div>
                    <a class="btn secondary-btn" href="${pageContext.request.contextPath}/communities">View All</a>
                </div>

                <div class="feature-grid">
                    <c:forEach var="community" items="${featuredCommunities}">
                        <article class="feature-card resource-card community-preview-card">
                            <div class="resource-meta">Community</div>
                            <h3 class="resource-title">${community.name}</h3>
                            <p>${community.description}</p>
                            <div class="inline-actions">
                                <a class="btn secondary-btn"
                                    href="${pageContext.request.contextPath}/communities/view?community_id=${community.id}">Explore</a>
                                <a class="btn" href="${pageContext.request.contextPath}/login">Login to Join</a>
                            </div>
                        </article>
                    </c:forEach>
                    <c:if test="${empty featuredCommunities}">
                        <div class="empty-state">No communities are available yet.</div>
                    </c:if>
                </div>
            </section>

            <section class="section-card landing-about">
                <div class="section-header">
                    <h2>About Nexora</h2>
                </div>
                <div class="about-grid">
                    <div>
                        <p>Nexora helps creators publish communities and courses, while learners discover content,
                            engage in discussions, and stay connected through a clean social learning experience.</p>
                        <p class="muted">Creators can upload course materials and videos, and members can comment and
                            react to posts as they learn.</p>
                    </div>
                    <div class="about-points">
                        <div><strong>1</strong><span>Create a community</span></div>
                        <div><strong>2</strong><span>Upload video-backed courses</span></div>
                        <div><strong>3</strong><span>Grow with discussion</span></div>
                    </div>
                </div>
            </section>

            <section class="section-card">
                <div class="section-header">
                    <h2>What people say</h2>
                </div>
                <div class="testimonial-grid">
                    <article class="testimonial-card testimonial-card-1">
                        <p>“Nexora makes it easy to turn a small audience into a real learning community.”</p>
                        <strong>Creator, early adopter</strong>
                    </article>
                    <article class="testimonial-card testimonial-card-2">
                        <p>“The course and discussion flow feels natural. I can learn, ask, and follow up in one place.”
                        </p>
                        <strong>Member, active learner</strong>
                    </article>
                    <article class="testimonial-card testimonial-card-3">
                        <p>“It finally gives my community a proper structure for video lessons and post-based
                            discussion.”</p>
                        <strong>Community builder</strong>
                    </article>
                </div>
            </section>

            <section class="section-card landing-cta">
                <h2>Ready to get started?</h2>
                <p>Log in to continue, or create your account in a few seconds.</p>
                <div class="hero-actions">
                    <a class="btn" href="${pageContext.request.contextPath}/login">Login</a>
                    <a class="btn secondary-btn" href="${pageContext.request.contextPath}/register">Register</a>
                </div>
            </section>
        </main>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />