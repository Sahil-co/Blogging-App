package com.sl.blogapp.articles.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@Setter(AccessLevel.NONE)
public class UpdateArticleRequestDto {

    @NonNull
    private String title;
    @NonNull
    private String body;
    @Nullable
    private String subTitle;

}
