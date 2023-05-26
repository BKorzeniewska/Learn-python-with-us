package com.example.learnpython.challenge;


import com.example.learnpython.article.Article;
import com.example.learnpython.challenge.model.ContentJson;
import com.example.learnpython.challenge.model.Type;
import com.example.learnpython.solution.Solution;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Convert(converter = JsonConverter.class)
    @Column(name = "CONTENT", columnDefinition = "jsonb")
    private ContentJson content;

    @JsonIgnore
    @OneToMany(mappedBy = "challenge", fetch = FetchType.EAGER)
    private List<Solution> solutions;

    @ManyToMany(mappedBy = "challenges", fetch = FetchType.EAGER)
    @JsonIgnore
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
