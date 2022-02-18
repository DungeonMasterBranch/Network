package com.example.socialnetwork.Post;

import com.example.socialnetwork.AppUser.AppUser;
import com.example.socialnetwork.AppUser.AppUserRepository;
import com.example.socialnetwork.Comments.CommentDto;
import com.example.socialnetwork.Comments.CommentService;
import com.example.socialnetwork.Registration.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/api/posts")
@AllArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto){
        postService.createPost(postDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getPosts/{id}")
    public ResponseEntity getPostsOfUser(@PathVariable @RequestBody Long id) {
        AppUser appUser = appUserRepository.getById(id);
        return new ResponseEntity<>(postService.readUsersPosts(appUser.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }

    @PostMapping("/comments/add/")
    public ResponseEntity addComment(@RequestBody CommentDto commentDto){
        commentService.addComment(commentDto);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/comments/get/{id}")
    public ResponseEntity showCommentsOfPost(@PathVariable @RequestBody Long id){
        return new ResponseEntity<>(commentService.getAllCommentsOfPost(id), HttpStatus.OK);
    }
}
