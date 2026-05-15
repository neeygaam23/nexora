<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Nexora</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css" />
        </head>

        <c:set var="themeClass" value="" />
        <c:if test="${not empty sessionScope.currentUser}">
            <c:set var="themeClass" value="${fn:toLowerCase(sessionScope.currentUser.roleName)}-theme" />
        </c:if>

        <body class="${themeClass}">
            <header class="site-header">
                <div class="brand"><a href="${pageContext.request.contextPath}/nexora">Nexora</a></div>
                <nav class="main-nav">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <c:choose>
                            <c:when test="${not empty sessionScope.currentUser}">
                                <c:choose>
                                    <c:when test="${sessionScope.currentUser.roleName == 'ADMIN'}">
                                        <li><a href="${pageContext.request.contextPath}/admin/dashboard">Admin</a></li>
                                    </c:when>
                                    <c:when test="${sessionScope.currentUser.roleName == 'CREATOR'}">
                                        <li><a href="${pageContext.request.contextPath}/creator/dashboard">Creator</a>
                                        </li>
                                    </c:when>
                                    <c:when test="${sessionScope.currentUser.roleName == 'MEMBER'}">
                                        <li><a href="${pageContext.request.contextPath}/member/dashboard">Member</a>
                                        </li>
                                    </c:when>
                                </c:choose>
                                <li class="spacer"></li>
                                <li class="welcome">Welcome, ${sessionScope.currentUser.username}</li>
                                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                                <li><a href="${pageContext.request.contextPath}/register">Register</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>
            </header>