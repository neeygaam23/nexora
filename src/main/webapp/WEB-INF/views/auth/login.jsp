<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Login - Nexora</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css" />
    </head>

    <body class="auth-page">
        <main>
            <section class="form-card">
                <h2>Login</h2>
                <c:if test="${param.registered == '1'}">
                    <p class="success">Registration successful. Please login.</p>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/login">
                    <label>Username: <input type="text" name="username" required /></label>
                    <label>Password: <input type="password" name="password" required /></label>
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