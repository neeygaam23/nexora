<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <!-- Basic page setup and responsive configuration -->
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />

    <!-- Page title shown in browser tab -->
    <title>Error - Nexora</title>

    <!-- Main stylesheet for page styling -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css" />
</head>

<body>

    <!-- Main container for the error page -->
    <main class="page-frame">

        <!-- Error message section -->
        <section class="page-hero">

            <!-- Main heading displayed to the user -->
            <h1>Something went wrong</h1>

            <!-- Dynamic error message received from the server -->
            <p class="lede">${requestScope['jakarta.servlet.error.message']}</p>
        </section>

        <!-- Section containing navigation actions -->
        <section class="section-card">

            <!-- Small helper text for the user -->
            <p class="muted">Try going back or returning to the home page.</p>

            <!-- Action buttons -->
            <div class="hero-actions">

                <!-- Redirect user to homepage -->
                <a class="btn" href="${pageContext.request.contextPath}/">Go Home</a>

                <!-- Return to previous page using browser history -->
                <a class="btn secondary-btn" href="javascript:history.back()">Go Back</a>
            </div>
        </section>

    </main>

</body>

</html>