<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />

    <!-- Makes the website responsive on mobile devices -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>Nexora</title>

    <!-- Including external styles file -->
    <%@ include file="styles.jspf" %>
</head>

<!-- 
    Setting default theme class.
    If user is logged in, theme will change based on role.
-->
<c:set var="themeClass" value="" />

<c:if test="${not empty sessionScope.currentUser}">
    <!-- Convert role name to lowercase and append '-theme' -->
    <c:set var="themeClass"
        value="${fn:toLowerCase(sessionScope.currentUser.roleName)}-theme" />
</c:if>

<!-- Default home URL for guest users -->
<c:set var="homeUrl" value="${pageContext.request.contextPath}/home" />

<!-- 
    Change home URL dynamically according to user role
-->
<c:if test="${not empty sessionScope.currentUser}">

    <c:choose>

        <!-- Admin home page -->
        <c:when test="${sessionScope.currentUser.roleName == 'ADMIN'}">
            <c:set var="homeUrl"
                value="${pageContext.request.contextPath}/admin/dashboard" />
        </c:when>

        <!-- Creator home page -->
        <c:when test="${sessionScope.currentUser.roleName == 'CREATOR'}">
            <c:set var="homeUrl"
                value="${pageContext.request.contextPath}/creator/dashboard" />
        </c:when>

        <!-- Member home page -->
        <c:otherwise>
            <c:set var="homeUrl"
                value="${pageContext.request.contextPath}/member/dashboard" />
        </c:otherwise>

    </c:choose>
</c:if>

<body class="${themeClass}">

    <!-- Main website header -->
    <header class="site-header">

        <!-- Website logo / brand name -->
        <div class="brand">
            <a href="${pageContext.request.contextPath}/home">Nexora</a>
        </div>

        <!-- Navigation bar -->
        <nav class="main-nav">
            <ul>

                <!-- Dynamic home link -->
                <li>
                    <a href="${homeUrl}">Home</a>
                </li>

                <c:choose>

                    <!-- Navigation for logged in users -->
                    <c:when test="${not empty sessionScope.currentUser}">

                        <c:choose>

                            <!-- Admin specific navigation -->
                            <c:when test="${sessionScope.currentUser.roleName == 'ADMIN'}">

                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/dashboard">
                                        Dashboard
                                    </a>
                                </li>

                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/manage-users">
                                        Manage Users
                                    </a>
                                </li>

                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/manage-communities">
                                        Manage Communities
                                    </a>
                                </li>

                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/manage-courses">
                                        Manage Courses
                                    </a>
                                </li>

                            </c:when>

                            <!-- Creator specific navigation -->
                            <c:when test="${sessionScope.currentUser.roleName == 'CREATOR'}">

                                <li>
                                    <a href="${pageContext.request.contextPath}/creator/dashboard">
                                        Dashboard
                                    </a>
                                </li>

                                <li>
                                    <a href="${pageContext.request.contextPath}/communities">
                                        Browse Communities
                                    </a>
                                </li>

                                <li>
                                    <a href="${pageContext.request.contextPath}/creator/create-community">
                                        Create Community
                                    </a>
                                </li>

                                <li>
                                    <a href="${pageContext.request.contextPath}/creator/create-course">
                                        Create Course
                                    </a>
                                </li>

                            </c:when>

                            <!-- Member specific navigation -->
                            <c:when test="${sessionScope.currentUser.roleName == 'MEMBER'}">

                                <li>
                                    <a href="${pageContext.request.contextPath}/communities">
                                        Browse Communities
                                    </a>
                                </li>

                                <li>
                                    <a href="${pageContext.request.contextPath}/member/enrollments">
                                        My Enrollments
                                    </a>
                                </li>

                            </c:when>

                        </c:choose>

                        <!-- Push welcome text and logout to the right -->
                        <li class="spacer"></li>

                        <!-- Display logged in username -->
                        <li class="welcome">
                            Welcome, ${sessionScope.currentUser.username}
                        </li>

                        <!-- Logout button -->
                        <li>
                            <a href="${pageContext.request.contextPath}/logout">
                                Logout
                            </a>
                        </li>

                    </c:when>

                    <!-- Navigation for guest users -->
                    <c:otherwise>

                        <li>
                            <a href="${pageContext.request.contextPath}/login">
                                Login
                            </a>
                        </li>

                        <li>
                            <a href="${pageContext.request.contextPath}/register">
                                Register
                            </a>
                        </li>

                    </c:otherwise>

                </c:choose>

            </ul>
        </nav>
    </header>