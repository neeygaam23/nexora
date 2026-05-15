<%@ include file="../includes/header.jsp" %>

    <main class="member-dashboard">

        <!-- HERO SECTION -->
        <section class="hero-section">
            <div class="hero-content">
                <div>
                    <h1>Welcome to Nexora</h1>
                    <p>
                        Explore communities, learn new skills, connect with creators,
                        and even start your own community.
                    </p>

                    <div class="hero-buttons">
                        <a href="browse-communities.jsp" class="btn primary-btn">
                            Explore Communities
                        </a>

                        <a href="create-community.jsp" class="btn secondary-btn">
                            Create Community
                        </a>
                    </div>
                </div>

                <div class="hero-image">
                    <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" alt="Member">
                </div>
            </div>
        </section>

        <!-- QUICK ACTIONS -->
        <section class="quick-actions">
            <div class="action-card">
                <h3>Browse Communities</h3>
                <p>Discover communities that match your interests.</p>
                <a href="browse-communities.jsp">Explore</a>
            </div>

            <div class="action-card">
                <h3>My Enrollments</h3>
                <p>View courses and communities you joined.</p>
                <a href="enrollments.jsp">View</a>
            </div>

            <div class="action-card">
                <h3>Share Your Course</h3>
                <p>Become a creator and publish your own course.</p>
                <a href="upload-course.jsp">Upload Course</a>
            </div>

            <div class="action-card">
                <h3>Create Community</h3>
                <p>Build your own audience and connect with people.</p>
                <a href="create-community.jsp">Create</a>
            </div>
        </section>

        <!-- RECOMMENDED COMMUNITIES -->
        <section class="dashboard-section">
            <div class="section-header">
                <h2>Recommended Communities</h2>
                <a href="browse-communities.jsp">View All</a>
            </div>

            <div class="card-grid">

                <div class="community-card">
                    <img src="https://images.unsplash.com/photo-1516321318423-f06f85e504b3" alt="Tech Community">

                    <div class="card-content">
                        <h3>Tech Innovators</h3>
                        <p>Discuss AI, programming, cybersecurity and modern tech.</p>

                        <button>Join Community</button>
                    </div>
                </div>

                <div class="community-card">
                    <img src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f" alt="Design Community">

                    <div class="card-content">
                        <h3>Creative Designers</h3>
                        <p>UI/UX, graphic design and creative collaboration.</p>

                        <button>Join Community</button>
                    </div>
                </div>

                <div class="community-card">
                    <img src="https://images.unsplash.com/photo-1519389950473-47ba0277781c" alt="Startup Community">

                    <div class="card-content">
                        <h3>Startup Founders</h3>
                        <p>Network with entrepreneurs and business creators.</p>

                        <button>Join Community</button>
                    </div>
                </div>

            </div>
        </section>

        <!-- RECOMMENDED COURSES -->
        <section class="dashboard-section">
            <div class="section-header">
                <h2>Recommended Courses</h2>
                <a href="courses.jsp">Browse Courses</a>
            </div>

            <div class="card-grid">

                <div class="course-card">
                    <div class="course-info">
                        <h3>Full Stack Web Development</h3>
                        <p>Learn HTML, CSS, JavaScript, JSP and MySQL.</p>

                        <div class="course-meta">
                            <span>⭐ 4.8</span>
                            <span>1200 Students</span>
                        </div>

                        <button>Enroll Now</button>
                    </div>
                </div>

                <div class="course-card">
                    <div class="course-info">
                        <h3>Cybersecurity Basics</h3>
                        <p>Understand ethical hacking and system security.</p>

                        <div class="course-meta">
                            <span>⭐ 4.7</span>
                            <span>950 Students</span>
                        </div>

                        <button>Enroll Now</button>
                    </div>
                </div>

                <div class="course-card">
                    <div class="course-info">
                        <h3>UI/UX Masterclass</h3>
                        <p>Design modern and user-friendly interfaces.</p>

                        <div class="course-meta">
                            <span>⭐ 4.9</span>
                            <span>800 Students</span>
                        </div>

                        <button>Enroll Now</button>
                    </div>
                </div>

            </div>
        </section>

    </main>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f4f7fb;
        }

        .member-dashboard {
            padding: 40px;
        }

        /* HERO SECTION */

        .hero-section {
            background: linear-gradient(135deg, #4f46e5, #7c3aed);
            border-radius: 20px;
            padding: 50px;
            color: white;
            margin-bottom: 40px;
        }

        .hero-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 30px;
            flex-wrap: wrap;
        }

        .hero-content h1 {
            font-size: 42px;
            margin-bottom: 15px;
        }

        .hero-content p {
            max-width: 600px;
            line-height: 1.6;
            margin-bottom: 25px;
        }

        .hero-buttons {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
        }

        .btn {
            padding: 12px 22px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: bold;
            transition: 0.3s;
        }

        .primary-btn {
            background: white;
            color: #4f46e5;
        }

        .secondary-btn {
            border: 2px solid white;
            color: white;
        }

        .btn:hover {
            opacity: 0.9;
            transform: translateY(-2px);
        }

        .hero-image img {
            width: 180px;
        }

        /* QUICK ACTIONS */

        .quick-actions {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 20px;
            margin-bottom: 50px;
        }

        .action-card {
            background: white;
            padding: 25px;
            border-radius: 18px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            transition: 0.3s;
        }

        .action-card:hover {
            transform: translateY(-5px);
        }

        .action-card h3 {
            margin-bottom: 10px;
            color: #222;
        }

        .action-card p {
            color: #666;
            margin-bottom: 18px;
            line-height: 1.5;
        }

        .action-card a {
            text-decoration: none;
            color: #4f46e5;
            font-weight: bold;
        }

        /* SECTION */

        .dashboard-section {
            margin-bottom: 50px;
        }

        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .section-header h2 {
            color: #222;
        }

        .section-header a {
            text-decoration: none;
            color: #4f46e5;
            font-weight: bold;
        }

        /* CARD GRID */

        .card-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 25px;
        }

        /* COMMUNITY CARD */

        .community-card,
        .course-card {
            background: white;
            border-radius: 18px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            transition: 0.3s;
        }

        .community-card:hover,
        .course-card:hover {
            transform: translateY(-5px);
        }

        .community-card img {
            width: 100%;
            height: 180px;
            object-fit: cover;
        }

        .card-content,
        .course-info {
            padding: 20px;
        }

        .card-content h3,
        .course-info h3 {
            margin-bottom: 10px;
            color: #222;
        }

        .card-content p,
        .course-info p {
            color: #666;
            line-height: 1.5;
            margin-bottom: 18px;
        }

        .course-meta {
            display: flex;
            justify-content: space-between;
            margin-bottom: 18px;
            color: #777;
            font-size: 14px;
        }

        button {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 10px;
            background: #4f46e5;
            color: white;
            font-weight: bold;
            cursor: pointer;
            transition: 0.3s;
        }

        button:hover {
            background: #4338ca;
        }

        /* RESPONSIVE */

        @media(max-width:768px) {

            .member-dashboard {
                padding: 20px;
            }

            .hero-content {
                text-align: center;
                justify-content: center;
            }

            .hero-content h1 {
                font-size: 32px;
            }

            .hero-buttons {
                justify-content: center;
            }
        }
    </style>

    <%@ include file="../includes/footer.jsp" %>