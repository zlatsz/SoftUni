package store.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CommentCleanupScheduler {

  private final CommentService commentService;

  public CommentCleanupScheduler(CommentService commentService) {
    this.commentService = commentService;
  }

  //cleans up old comments.
  @Scheduled(cron = "0 0 0 1 1 *")
  public void cleanUpOldComment() {
    commentService.cleanUpOldComment();
  }


}
