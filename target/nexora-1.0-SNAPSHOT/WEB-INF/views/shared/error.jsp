<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Error - Nexora</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css" />
    </head>

    <body>
        <main class="page-frame">
            <section class="page-hero">
                <h1>Something went wrong</h1>
                <p class="lede">${requestScope['jakarta.servlet.error.message']}</p>
            </section>
            <section class="section-card">
                <p class="muted">Try going back or returning to the home page.</p>
                <div class="hero-actions">
                    <a class="btn" href="${pageContext.request.contextPath}/">Go Home</a>
                    <a class="btn secondary-btn" href="javascript:history.back()">Go Back</a>
                </div>
            </section>
        </main>
    </body>

    </html>