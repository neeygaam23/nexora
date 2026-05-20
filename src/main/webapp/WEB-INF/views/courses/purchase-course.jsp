<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />

        <main class="page-frame">
            <section class="page-hero">
                <h1>Pay with eSewa</h1>
                <p class="lede">This is a demo checkout that simulates a successful eSewa payment for the selected
                    course.</p>
            </section>

            <section class="section-card">
                <div class="content-panel">
                    <h2>${course.title}</h2>
                    <p>${course.description}</p>
                    <div class="reaction-bar">
                        <span class="status-pill">NPR ${course.price}</span>
                        <span class="status-pill success">Dummy eSewa flow</span>
                    </div>
                    <form method="post" action="${pageContext.request.contextPath}/courses/purchase">
                        <input type="hidden" name="course_id" value="${course.id}" />
                        <div class="form-group">
                            <label>Payment method</label>
                            <input type="text" value="eSewa demo" readonly />
                        </div>
                        <div class="form-group">
                            <label>Amount</label>
                            <input type="text" value="NPR ${course.price}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Reference</label>
                            <input type="text" value="DEMO-${course.id}-ESEWA" readonly />
                        </div>
                        <div class="form-actions">
                            <button type="submit">Confirm Payment</button>
                            <a class="secondary-link"
                                href="${pageContext.request.contextPath}/courses/view?course_id=${course.id}">Cancel</a>
                        </div>
                    </form>
                </div>
            </section>
        </main>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />