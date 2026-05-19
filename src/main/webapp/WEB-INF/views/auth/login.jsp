<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Login - Nexora</title>
        <%@ include file="../includes/styles.jspf" %>
    </head>

    <body class="auth-page">
        <main>
            <section class="form-card">
                <p class="status-pill">Welcome back</p>
                <h2>Login</h2>
                <p class="muted">Sign in to continue learning, creating, and managing your communities.</p>
                <c:if test="${param.registered == '1'}">
                    <p class="success">Registration successful. Please login.</p>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/login">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input id="username" type="text" name="username" required />
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password" type="password" name="password" required />
                    </div>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/register" class="secondary-link">Create an
                            account</a>
                        <button type="submit">Login</button>
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