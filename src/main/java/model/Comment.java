package model;

import java.sql.Timestamp;

// Model class used to represent a comment in the system
public class Comment {

    // Unique ID of the comment
    private int id;

    // ID of the post this comment belongs to
    private int postId;

    // ID of the user who created the comment
    private int authorId;

    // Stores parent comment ID for reply/nested comments
    private int parentCommentId;

    // Main content/body of the comment
    private String body;

    // Timestamp showing when the comment was created
    private Timestamp createdAt;

    // Returns the comment ID
    public int getId() {
        return id;
    }

    // Sets the comment ID
    public void setId(int id) {
        this.id = id;
    }

    // Returns the related post ID
    public int getPostId() {
        return postId;
    }

    // Sets the related post ID
    public void setPostId(int postId) {
        this.postId = postId;
    }

    // Returns the author/user ID
    public int getAuthorId() {
        return authorId;
    }

    // Sets the author/user ID
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    // Returns the parent comment ID
    public int getParentCommentId() {
        return parentCommentId;
    }

    // Sets the parent comment ID
    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    // Returns the comment text/body
    public String getBody() {
        return body;
    }

    // Sets the comment text/body
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