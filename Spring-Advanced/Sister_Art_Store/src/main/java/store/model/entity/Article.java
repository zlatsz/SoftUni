package store.model.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity{

    private MainCategory mainCategory;
    private String name;
    private String pdfUrl;
    private LocalDateTime postTime;
    private User uploader;

    public Article() {
    }

    @Enumerated(value = EnumType.STRING)
    public MainCategory getMainCategory() {
        return this.mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    @Column(name = "article_name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "pdf")
    public String getPdfUrl() {
        return this.pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    @Column(name = "post_time")
    public LocalDateTime getPostTime() {
        return this.postTime;
    }

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUploader() {
        return this.uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

}
