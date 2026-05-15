<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Register - Nexora</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css" />
    </head>

    <body class="auth-page">
        <main>
            <section class="form-card">
                <h2>Register</h2>
                <form method="post" action="${pageContext.request.contextPath}/register">
                    <label>Full name: <input type="text" name="fullName" /></label>
                    <label>Username: <input type="text" name="username" required></label>
                    <label>Email: <input type="email" name="email" required></label>
                    <label>Password: <input type="password" name="password" required></label>
                    <label>Confirm Password: <input type="password" name="confirmPassword" required></label>
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