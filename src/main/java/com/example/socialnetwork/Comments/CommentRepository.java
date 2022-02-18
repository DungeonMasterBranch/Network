package com.example.socialnetwork.Comments;

import com.example.socialnetwork.AppUser.AppUser;
import com.example.socialnetwork.Security.JwtProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.post_id=?1")
    List<Comment> findAllByPost_id(Long id);
}
