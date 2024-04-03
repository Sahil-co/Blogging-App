package com.sl.blogapp.articles;

import com.sl.blogapp.users.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Entity(name = "articles")
@Data
@Builder
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String title;

    @Column(nullable = false, unique = true)
    @NonNull
    private String slug;

    @Column(nullable = true)
    @Nullable
    private String subTitle;

    @Column(nullable = false)
    @NonNull
    private String body;

    @Column(nullable = false)
    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private UserEntity author;
}
