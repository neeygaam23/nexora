<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8" />
            <%-- Default home URL for guest users --%>
                <%-- Makes the website responsive on mobile devices --%>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

                    <title>Nexora</title>

                    <%-- Including external styles file --%>
                        <%@ include file="styles.jspf" %>
        </head>

        <%-- Setting default theme class. If user is logged in, theme will change based on role. --%>
            <c:set var="themeClass" value="" />
            <%-- Admin home page --%>
                <c:if test="${not empty sessionScope.currentUser}">
                    <%-- Convert role name to lowercase and append '-theme' --%>
                        <c:set var="themeClass" value="${fn:toLowerCase(sessionScope.currentUser.roleName)}-theme" />
                </c:if>
                <%-- Creator home page --%>
                    <%-- Default home URL for guest users --%>
                        <c:set var="homeUrl" value="${pageContext.request.contextPath}/home" />

                        <%-- Member home page --%>
                            <c:if test="${not empty sessionScope.currentUser}">

                                <c:choose>

                                    <%-- Admin home page --%>
                                        <c:when test="${sessionScope.currentUser.roleName == 'ADMIN'}">
                                            <c:set var="homeUrl"
                                                value="${pageContext.request.contextPath}/admin/dashboard" />
                                        </c:when>

                                        <%-- Creator home page --%>
                                            <c:when test="${sessionScope.currentUser.roleName == 'CREATOR'}">
                                                <c:set var="homeUrl"
                                                    value="${pageContext.request.contextPath}/creator/dashboard" />
                                            </c:when>

                                            <%-- Member home page --%>
                                                <c:otherwise>
                                                    <c:set var="homeUrl"
                                                        value="${pageContext.request.contextPath}/member/dashboard" />
                                                </c:otherwise>

                                </c:choose>
                            </c:if>

                            <body class="${themeClass}">

                                <%-- Main website header --%>
                                    <header class="site-header">

                                        <%-- Website logo / brand name --%>
                                            <div class="brand">
                                                <a href="${pageContext.request.contextPath}/home">Nexora</a>
                                                <%-- Admin specific navigation --%>

                                                    <nav class="main-nav">
                                                        <ul>
                                                            <li><a href="${homeUrl}">Home</a></li>

                                                            <c:choose>

                                                                <c:when test="${not empty sessionScope.currentUser}">

                                                                    <c:choose>
                                                                        <c:when
                                                                            test="${sessionScope.currentUser.roleName == 'ADMIN'}">
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                                                                            </li>
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/admin/manage-users">Manage
                                                                                    Users</a></li>
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/admin/manage-communities">Manage
                                                                                    Communities</a></li>
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/admin/manage-courses">Manage
                                                                                    Courses</a></li>
                                                                        </c:when>

                                                                        <c:when
                                                                            test="${sessionScope.currentUser.roleName == 'CREATOR'}">
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/creator/dashboard">Dashboard</a>
                                                                            </li>
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/communities">Browse
                                                                                    Communities</a></li>
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/creator/create-community">Create
                                                                                    Community</a></li>
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/creator/create-course">Create
                                                                                    Course</a></li>
                                                                        </c:when>

                                                                        <c:when
                                                                            test="${sessionScope.currentUser.roleName == 'MEMBER'}">
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/communities">Browse
                                                                                    Communities</a></li>
                                                                            <li><a
                                                                                    href="${pageContext.request.contextPath}/member/enrollments">My
                                                                                    Enrollments</a></li>
                                                                        </c:when>
                                                                    </c:choose>

                                                                    <li class="spacer"></li>
                                                                    <li class="welcome">Welcome,
                                                                        ${sessionScope.currentUser.username}</li>
                                                                    <li><a
                                                                            href="${pageContext.request.contextPath}/logout">Logout</a>
                                                                    </li>

                                                                </c:when>

                                                                <c:otherwise>
                                                                    <li><a
                                                                            href="${pageContext.request.contextPath}/login">Login</a>
                                                                    </li>
                                                                    <li><a
                                                                            href="${pageContext.request.contextPath}/register">Register</a>
                                                                    </li>
                                                                </c:otherwise>

                                                            </c:choose>
                                                        </ul>
                                                    </nav>
                                    </header>