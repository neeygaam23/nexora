<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Login - Nexora</title>

    <!-- include shared styles -->
    <%@ include file="../includes/styles.jspf" %>
</head>

<body class="auth-page">

    <main>

        <!-- login form container -->
        <section class="form-card">

            <!-- small welcome label -->
            <p class="status-pill">Welcome back</p>

            <!-- page heading -->
            <h2>Login</h2>

            <!-- helper text -->
            <p class="muted">
                Sign in to continue learning, creating, and managing your communities.
            </p>

            <!-- show success message after registration -->
            <c:if test="${param.registered == '1'}">
                <p class="success">Registration successful. Please login.</p>
            </c:if>

            <!-- login form -->
            <form method="post" action="${pageContext.request.contextPath}/login">

                <!-- username field -->
                <div class="form-group">
                    <label for="username">Username</label>
                    <input id="username" type="text" name="username" required />
                </div>

                <!-- password field -->
                <div class="form-group">
                    <label for="password">Password</label>
                    <input id="password" type="password" name="password" required />
                </div>

                
                <!-- form actions -->
                <div class="form-actions">

                    <!-- link to register page -->
                    <a href="${pageContext.request.contextPath}/register" class="secondary-link">
                        Create an account
                    </a>


                    <!-- submit login -->
                    <button type="submit">Login</button>
                </div>
            </form>

            <!-- display login errors if any -->
            <c:if test="${not empty errors}">
                <div class="error-list">


                    <!-- loop through all error messages -->
                    <c:forEach var="err" items="${errors}">
                        <p class="error">${err}</p>
                    </c:forEach>

                </div>
            </c:if>

        </section>

    </main>

</body>

</html>