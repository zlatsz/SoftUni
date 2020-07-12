package store.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    private String description;
    private LocalDateTime commentDate;
    private List<User> user;
    private List<Article> article;

    public Comment() {
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "comment_date")
    public LocalDateTime getCommentDate() {
        return this.commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "comments_users", joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    public List<User> getUser() {
        return this.user;
    }
    public void setUser(List<User> user) {
        this.user = user;
    }

    @ManyToMany(targetEntity = Article.class)
    @JoinTable(name = "comments_articles", joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    public List<Article> getArticle() {
        return this.article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }
}
