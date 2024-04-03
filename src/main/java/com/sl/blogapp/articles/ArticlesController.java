package com.sl.blogapp.articles;

import com.sl.blogapp.users.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @GetMapping("")
    public String getArticles(){
        return "articles";
    }

    @GetMapping("{id}")
    public String getArticlesById(@PathVariable("id") Long id){
        return "Get article with id: "+id;
    }

    @PostMapping("")
    public String createArticle(@AuthenticationPrincipal UserEntity userEntity){
        return "Create article called by "+userEntity.getUsername();
    }
}
