package com.example.socialnetwork.Post;

import com.example.socialnetwork.Comments.Comment;
import com.example.socialnetwork.Comments.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostDto {
    private Long id;
    private String content;
    private String title;
    private String username;
    private List<CommentDto> comments;

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
