package model;

import java.sql.Timestamp;

// Model class representing a post created in the system
public class Post {

    // Unique identifier for the post
    private int id;

    // ID of the community where the post belongs
    private int communityId;

    // ID of the related course
    private int courseId;

    // ID of the user who created the post
    private int authorId;

    // Title of the post
    private String title;

    // Main content/body of the post
    private String body;

    // Timestamp storing when the post was created
    private Timestamp createdAt;

    // Returns the post ID
    public int getId() {
        return id;
    }

    // Sets the post ID
    public void setId(int id) {
        this.id = id;
    }

    // Returns the community ID
    public int getCommunityId() {
        return communityId;
    }

    // Sets the community ID
    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    // Returns the course ID
    public int getCourseId() {
        return courseId;
    }

    // Sets the course ID
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    // Returns the author/user ID
    public int getAuthorId() {
        return authorId;
    }

    // Sets the author/user ID
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    // Returns the post title
    public String getTitle() {
        return title;
    }

    // Sets the post title
    public void setTitle(String title) {
        this.title = title;
    }

    // Returns the main post content
    public String getBody() {
        return body;
    }

    // Sets the main post content
    public void setBody(String body) {
        this.body = body;
    }

    // Returns the creation timestamp
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Sets the creation timestamp
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}