package com.sl.blogapp.articles;

import com.sl.blogapp.articles.dtos.CreateArticleRequestDto;
import com.sl.blogapp.articles.dtos.UpdateArticleRequestDto;
import com.sl.blogapp.users.UserService;
import com.sl.blogapp.users.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class ArticleService {

    private final ArticlesRepository articlesRepository;

    private final UsersRepository usersRepository;

    public ArticleService(ArticlesRepository articlesRepository, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles(){
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug){
        var article = articlesRepository.findBySlug(slug);

        if(article == null){
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequestDto req, Long id){
        var author = usersRepository.findById(id).orElseThrow(() -> new UserService.UserNotFoundException(id));

        return ArticleEntity.builder()
                .title(req.getTitle())
                .slug(req.getTitle()) //TODO: need add sluggify function
                .subTitle(req.getSubTitle())
                .body(req.getBody())
                .author(author)
                .build();
    }

    public ArticleEntity updateArticle(Long id, UpdateArticleRequestDto req){
        var article = articlesRepository.findById(id).orElseThrow(() ->new ArticleNotFoundException(id));

        if(req.getTitle() != null){
            article.setTitle(req.getTitle());
            article.setSlug(req.getTitle());
        }

        if(req.getBody() != null){
            article.setBody(req.getBody());
        }

        if(req.getSubTitle() != null){
            article.setSubTitle(req.getSubTitle());
        }

        return article;
    }

    public static class ArticleNotFoundException extends IllegalArgumentException {

        public ArticleNotFoundException(String slug){
            super("Article with "+slug+" not found");
        }

        public ArticleNotFoundException(Long id){
            super("Article with "+id+" not found");
        }
    }
}
