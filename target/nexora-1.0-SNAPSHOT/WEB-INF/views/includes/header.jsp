<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Nexora</title>
            <%@ include file="styles.jspf" %>
        </head>

        <c:set var="themeClass" value="" />
        <c:if test="${not empty sessionScope.currentUser}">
            <c:set var="themeClass" value="${fn:toLowerCase(sessionScope.currentUser.roleName)}-theme" />
        </c:if>

        <c:set var="homeUrl" value="${pageContext.request.contextPath}/home" />
        <c:if test="${not empty sessionScope.currentUser}">
            <c:choose>
                <c:when test="${sessionScope.currentUser.roleName == 'ADMIN'}">
                    <c:set var="homeUrl" value="${pageContext.request.contextPath}/admin/dashboard" />
                </c:when>
                <c:when test="${sessionScope.currentUser.roleName == 'CREATOR'}">
                    <c:set var="homeUrl" value="${pageContext.request.contextPath}/creator/dashboard" />
                </c:when>
                <c:otherwise>
                    <c:set var="homeUrl" value="${pageContext.request.contextPath}/member/dashboard" />
                </c:otherwise>
            </c:choose>
        </c:if>

        <body class="${themeClass}">
            <header class="site-header">
                <div class="brand"><a href="${pageContext.request.contextPath}/home">Nexora</a></div>
                <nav class="main-nav">
                    <ul>
                        <li><a href="${homeUrl}">Home</a></li>
                        <c:choose>
                            <c:when test="${not empty sessionScope.currentUser}">
                                <c:choose>
                                    <c:when test="${sessionScope.currentUser.roleName == 'ADMIN'}">
                                        <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                                        </li>
                                        <li><a href="${pageContext.request.contextPath}/admin/manage-users">Manage
                                                Users</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/manage-communities">Manage
                                                Communities</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/manage-courses">Manage
                                                Courses</a></li>
                                    </c:when>
                                    <c:when test="${sessionScope.currentUser.roleName == 'CREATOR'}">
                                        <li><a href="${pageContext.request.contextPath}/creator/dashboard">Dashboard</a>
                                        </li>
                                        <li><a href="${pageContext.request.contextPath}/communities">Browse
                                                Communities</a></li>
                                        <li><a href="${pageContext.request.contextPath}/creator/create-community">Create
                                                Community</a></li>
                                        <li><a href="${pageContext.request.contextPath}/creator/create-course">Create
                                                Course</a></li>
                                    </c:when>
                                    <c:when test="${sessionScope.currentUser.roleName == 'MEMBER'}">
                                        <li><a href="${pageContext.request.contextPath}/communities">Browse
                                                Communities</a></li>
                                        <li><a href="${pageContext.request.contextPath}/member/enrollments">My
                                                Enrollments</a></li>
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