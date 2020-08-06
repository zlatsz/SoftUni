package store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.model.entity.Article;
import store.model.entity.Comment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    void deleteByCommentDateBefore(Instant endTime);

}
