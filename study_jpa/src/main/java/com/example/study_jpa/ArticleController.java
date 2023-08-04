package com.example.study_jpa;

import com.example.study_jpa.entity.Article;
import com.example.study_jpa.entity.Comment;
import com.example.study_jpa.repository.ArticleRepository;
import com.example.study_jpa.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    // 페이지당 게시물 개수
    private Integer DEFAULT_PAGE_COUNT = 5;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    // C
    @PostMapping
    public ResponseEntity<ArticleDTO> postArticle(@RequestBody ArticleDTO articleDTO) {
        // DTO 객체를 전달받아 엔티티 객체 생성하는 것 유의!
        Article article = ArticleDTO.toEntity(articleDTO);
        articleRepository.save(article);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/users/" + article.getId());
        return new ResponseEntity<>(new ArticleDTO(article), headers, HttpStatus.CREATED);
    }

    // C (댓글)
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> getArticle(@PathVariable("id") Integer id, @RequestBody CommentDTO commentDTO) {
        Article article = articleRepository.findById(id).get();
        // DTO 객체를 전달받아 엔티티 객체 생성하는 것 유의!
        Comment comment = CommentDTO.toEntity(commentDTO);
        // https://stackoverflow.com/questions/46218606/jpa-adding-child-entity-to-parent-entity
        // Then since your relation is bidirectional its logical to have each side of the relation mapped to the other, hence you will need to have reference of each side in the other side
        // 명시적으로 부모, 자식간의 쌍방향(bidirectional) 관계 지정 필요
        comment.setArticle(article); // 부모 관계 지정 (게시글 객체 지정)
        article.getComments().add(comment); // 자식 관계 지정 (게시글의 댓글 리스트에 해당 댓글 추가)
        commentRepository.save(comment); // 댓글 저장

        return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.CREATED);
    }

    // R
    @GetMapping
    public Iterable<ArticleDTO> getAllArticle(@RequestParam(value="page", defaultValue="1") Integer page,
                                              @RequestParam(value="order", defaultValue="desc") String order) {
        List<ArticleDTO> articleDtos = new ArrayList<>();
        // 페이징을 위해서 PageRequest 객체를 전달하며 동시에 반환 타입을 List가 아닌 Page로 설정
        Page<Article> articles = articleRepository.findAll(
                PageRequest.of(
                        page - 1, // 페이지는 0부터 시작
                        DEFAULT_PAGE_COUNT, // 한 페이지 당 가져올 항목의 개수
                        order.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, // DESC or ASC
                        "id" // order by의 대상 칼럼 이름
                )
        );
        for(Article article : articles) articleDtos.add(new ArticleDTO(article));
        return articleDtos;
    }

    // R
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable("id") Integer id) {
        Optional<Article> article = articleRepository.findById(id);
        if(article.isPresent()) {
            return new ResponseEntity<>(new ArticleDTO(article.get(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}