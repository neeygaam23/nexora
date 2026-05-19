<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Shared header section -->
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<main class="page-frame">

    <!-- Community information banner -->
    <section class="page-hero">

        <!-- Community title -->
        <h1>${community.name}</h1>

        <!-- Community description -->
        <p class="lede">${community.description}</p>

        <!-- Action buttons section -->
        <div class="hero-actions">

            <!-- Check whether user is logged in -->
            <c:choose>

                <!-- Logged in user -->
                <c:when test="${not empty sessionScope.currentUser}">

                    <!-- Check if current user is already a member -->
                    <c:choose>

                        <!-- User is already a member -->
                        <c:when test="${isMember}">

                            <!-- Leave community form -->
                            <form method="post"
                                  action="${pageContext.request.contextPath}/communities/leave">

                                <!-- Hidden field to send community ID -->
                                <input type="hidden"
                                       name="community_id"
                                       value="${community.id}" />

                                <!-- Leave button -->
                                <button type="submit" class="btn secondary-btn">
                                    Leave Community
                                </button>
                            </form>

                        </c:when>

                        <!-- User is not a member -->
                        <c:otherwise>

                            <!-- Join community form -->
                            <form method="post"
                                  action="${pageContext.request.contextPath}/communities/join">

                                <!-- Hidden field for selected community -->
                                <input type="hidden"
                                       name="community_id"
                                       value="${community.id}" />

                                <!-- Join button -->
                                <button type="submit" class="btn">
                                    Join Community
                                </button>
                            </form>

                        </c:otherwise>
                    </c:choose>

                    <!-- Link to community discussions -->
                    <a href="${pageContext.request.contextPath}/communities/posts?community_id=${community.id}"
                       class="btn secondary-btn">
                        View Discussions
                    </a>

                </c:when>

                <!-- Guest user section -->
                <c:otherwise>

                    <!-- Redirect user to login page -->
                    <a href="${pageContext.request.contextPath}/login"
                       class="btn">
                        Login to Join
                    </a>

                    <!-- Redirect user to registration page -->
                    <a href="${pageContext.request.contextPath}/register"
                       class="btn secondary-btn">
                        Create Account
                    </a>

                </c:otherwise>
            </c:choose>
        </div>
    </section>

    <!-- Course listing section -->
    <section class="section-card">

        <!-- Section heading -->
        <div class="section-header">
            <h2>Courses</h2>
        </div>

        <!-- Course cards container -->
        <div class="feature-grid">

            <!-- Loop through all available courses -->
            <c:forEach var="course" items="${courses}">

                <!-- Individual course card -->
                <a class="feature-card resource-card"
                   href="${pageContext.request.contextPath}/courses/view?course_id=${course.id}">

                    <!-- Course title -->
                    <h3 class="resource-title">${course.title}</h3>

                    <!-- Course description -->
                    <p>${course.description}</p>

                    <!-- Course pricing information -->
                    <p class="resource-meta">

                        <c:choose>

                            <!-- Paid course -->
                            <c:when test="${course.paid}">
                                NPR ${course.price}
                            </c:when>

                            <!-- Free course -->
                            <c:otherwise>
                                Free
                            </c:otherwise>

                        </c:choose>

                    </p>
                </a>

            </c:forEach>

            <!-- Message shown if no courses exist -->
            <c:if test="${empty courses}">
                <div class="empty-state">
                    No courses posted in this community yet.
                </div>
            </c:if>

        </div>
    </section>
</main>

<!-- Shared footer section -->
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />