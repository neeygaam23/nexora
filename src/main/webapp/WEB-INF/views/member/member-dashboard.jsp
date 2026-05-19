<%@ include file="../includes/header.jsp" %>

    <main class="member-dashboard">

        <!-- HERO SECTION -->
        <section class="hero-section">
            <div class="hero-content">
                <div>
                    <h1>Welcome to Nexora</h1>
                    <p>
                        Explore communities, learn new skills, connect with creators,
                        and even start your own community.
                    </p>

                    <div class="hero-buttons">
                        <a href="${pageContext.request.contextPath}/communities" class="btn primary-btn">
                            Explore Communities
                        </a>
                    </div>
                </div>

                <div class="hero-image">
                    <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" alt="Member">
                </div>
            </div>
        </section>

        <!-- QUICK ACTIONS -->
        <section class="quick-actions">
            <div class="action-card">
                <h3>Browse Communities</h3>
                <p>Discover communities that match your interests.</p>
                <a href="${pageContext.request.contextPath}/communities">Explore</a>
            </div>

            <div class="action-card">
                <h3>My Enrollments</h3>
                <p>View courses and communities you joined.</p>
                <a href="${pageContext.request.contextPath}/member/enrollments">View</a>
            </div>


        </section>


    </main>

    <%@ include file="../includes/footer.jsp" %>