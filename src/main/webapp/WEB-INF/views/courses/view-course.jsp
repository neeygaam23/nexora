<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <main class="page-frame">
            <section class="page-hero">
                <h1>${course.title}</h1>
                <p class="lede">${course.description}</p>
                <c:if test="${not empty purchaseSuccess}">
                    <div class="status-pill success">${purchaseSuccess}</div>
                </c:if>
                <div class="reaction-bar">
                    <c:choose>
                        <c:when test="${isPaidCourse}">
                            <span class="status-pill warn">Paid course</span>
                            <span class="status-pill">NPR ${course.price}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="status-pill success">Free course</span>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${isEnrolled}">
                        <span class="status-pill success">Enrolled</span>
                    </c:if>
                    <c:if test="${not isEnrolled}">
                        <span class="status-pill warn">Preview access</span>
                    </c:if>
                    <span class="status-pill">${totalMaterials} materials</span>
                    <c:if test="${isEnrolled}">
                        <span class="status-pill">Progress ${completedCount}/${totalMaterials}</span>
                    </c:if>
                </div>
                <div class="hero-actions">
                    <c:if test="${isEnrolled}">
                        <form method="post" action="${pageContext.request.contextPath}/courses/unenroll">
                            <input type="hidden" name="course_id" value="${course.id}" />
                            <button type="submit" class="btn secondary-btn">Unenroll</button>
                        </form>
                    </c:if>
                    <c:if test="${not isEnrolled and not isPaidCourse}">
                        <form method="post" action="${pageContext.request.contextPath}/courses/enroll">
                            <input type="hidden" name="course_id" value="${course.id}" />
                            <button type="submit" class="btn">Enroll Free</button>
                        </form>
                    </c:if>
                    <c:if test="${canPurchase}">
                        <form method="get" action="${pageContext.request.contextPath}/courses/purchase">
                            <input type="hidden" name="course_id" value="${course.id}" />
                            <button type="submit" class="btn">Buy with eSewa</button>
                        </form>
                    </c:if>
                </div>
            </section>

            <section class="section-card">
                <div class="section-header">
                    <h2>Materials</h2>
                </div>
                <c:choose>
                    <c:when test="${canAccessMaterials}">
                        <div class="content-grid">
                            <c:if test="${empty materials}">
                                <div class="empty-state">No materials yet.</div>
                            </c:if>
                            <c:forEach var="m" items="${materials}">
                                <article class="content-panel resource-card">
                                    <h3 class="resource-title">${m[1]}</h3>
                                    <c:if test="${m[4] eq 'video'}">
                                        <video class="course-video" controls preload="metadata"
                                            src="${pageContext.request.contextPath}/materials/download?id=${m[0]}&inline=true"></video>
                                    </c:if>
                                    <div class="material-actions">
                                        <a class="btn secondary-btn"
                                            href="${pageContext.request.contextPath}/materials/download?id=${m[0]}">Download</a>
                                        <c:if test="${m[4] eq 'video'}">
                                            <a class="btn secondary-btn"
                                                href="${pageContext.request.contextPath}/materials/download?id=${m[0]}&inline=true">Open
                                                Video</a>
                                        </c:if>
                                    </div>
                                    <c:if test="${isEnrolled}">
                                        <form method="post"
                                            action="${pageContext.request.contextPath}/courses/progress">
                                            <input type="hidden" name="course_id" value="${course.id}" />
                                            <input type="hidden" name="module_id" value="${m[0]}" />
                                            <button type="submit" class="btn btn-sm primary-btn">Mark Complete</button>
                                        </form>
                                    </c:if>
                                </article>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state">
                            <c:choose>
                                <c:when test="${isPaidCourse}">
                                    Buy this course with eSewa to unlock the materials.
                                </c:when>
                                <c:otherwise>
                                    Enroll in this free course to unlock the materials.
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>

            <c:if test="${canUpload}">
                <section class="section-card">
                    <div class="section-header">
                        <h2>Upload Material</h2>
                    </div>
                    <form method="post" action="${pageContext.request.contextPath}/creator/upload-material"
                        enctype="multipart/form-data">
                        <input type="hidden" name="course_id" value="${course.id}" />
                        <input type="file" name="file" accept="video/*,.mp4,.webm,.ogg,.mov,.mkv,.pdf,.doc,.docx" />
                        <small class="muted">Video files play inline on this page. Other files stay
                            downloadable.</small>
                        <button type="submit">Upload</button>
                    </form>
                </section>
            </c:if>
        </main>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />