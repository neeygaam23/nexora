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
        <main>
            <section class="card">
                <h2>Error</h2>
                <p class="error">${requestScope['jakarta.servlet.error.message']}</p>
            </section>
        </main>
    </body>

    </html>