<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Register - Nexora</title>
        <%@ include file="../includes/styles.jspf" %>
    </head>

    <body class="auth-page">
        <main>
            <section class="form-card">
                <p class="status-pill">Join Nexora</p>
                <h2>Register</h2>
                <p class="muted">Choose your role and start building or learning right away.</p>
                <form method="post" action="${pageContext.request.contextPath}/register">
                    <div class="form-group">
                        <label for="fullName">Full name</label>
                        <input id="fullName" type="text" name="fullName" />
                    </div>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input id="username" type="text" name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input id="email" type="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password" type="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password</label>
                        <input id="confirmPassword" type="password" name="confirmPassword" required>
                    </div>

                    <div class="form-group">
                        <label>Join as</label>
                        <div class="role-selector">
                            <label class="role-option">
                                <input type="radio" name="role" value="MEMBER" checked />
                                <div class="role-card">
                                    <span class="role-icon">🎓</span>
                                    <strong>Member</strong>
                                    <p>Browse communities, enroll in courses, and engage with content.</p>
                                </div>
                            </label>
                            <label class="role-option">
                                <input type="radio" name="role" value="CREATOR" />
                                <div class="role-card">
                                    <span class="role-icon">🚀</span>
                                    <strong>Creator</strong>
                                    <p>Build communities, publish courses, and share your knowledge.</p>
                                </div>
                            </label>
                        </div>
                    </div>

                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/login" class="secondary-link">Already have an
                            account?</a>
                        <button type="submit">Register</button>
                    </div>
                </form>

                <c:if test="${not empty errors}">
                    <div class="error-list">
                        <c:forEach var="err" items="${errors}">
                            <p class="error">${err}</p>
                        </c:forEach>
                    </div>
                </c:if>
            </section>
        </main>
    </body>

    </html>