package com.example.learnpython.challenge;


import com.example.learnpython.article.model.Article;
import com.example.learnpython.challenge.model.ContentJson;
import com.example.learnpython.challenge.model.Type;
import com.example.learnpython.solution.model.Solution;
import com.example.learnpython.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;


import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHALLENGE_ID")
    private long id;

    @Column(name = "QUESTION")
    private String question;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private Type type;

    @Column(name = "NAME")
    private String name;
    @Column(name = "VISIBLE")
    private Boolean visible;
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Column(name = "EXP")
    private Long exp;

    @Convert(converter = JsonConverter.class)
    @Column(name = "CONTENT", columnDefinition = "jsonb")
    private ContentJson content;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solution> solutions;

    @ManyToMany(mappedBy = "challenges", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Article> articles;

    public List<Article> getArticles() {
        Hibernate.initialize(articles);
        return articles;
    }

    public List<Long> getArticlesID() {
        List<Long> articleIds = new ArrayList<>();
        List<Article> initializedArticles = getArticles();

        for (Article article : initializedArticles) {
            articleIds.add(article.getId());
        }

        return articleIds;
    }
}
