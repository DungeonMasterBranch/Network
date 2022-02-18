package com.example.socialnetwork.Comments;

import com.example.socialnetwork.Registration.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public void addComment(CommentDto commentDto){
        Comment comment = mapFromDtoToComment(commentDto);
        commentRepository.save(comment);
    }

    @Transactional
    public List<CommentDto> getAllCommentsOfPost(Long post_id) {
        List<Comment> comments = commentRepository.findAllByPost_id(post_id);
        return comments.stream().map(this::mapFromCommentToDto).collect(toList());
    }

    private CommentDto mapFromCommentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setAuthor_username(comment.getAuthor_username());
        commentDto.setContent(comment.getContent());
        commentDto.setPost_id(comment.getPost_id());
        commentDto.setCreatedOn(comment.getCreatedOn());
        return commentDto;
    }


    private Comment mapFromDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setPost_id(commentDto.getPost_id());
        comment.setContent(commentDto.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        comment.setCreatedOn(Instant.now());
        comment.setAuthor_username(loggedInUser.getUsername());
        return comment;
    }

}
