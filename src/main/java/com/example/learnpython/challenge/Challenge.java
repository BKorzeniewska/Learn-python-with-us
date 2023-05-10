package com.example.learnpython.challenge;


import com.example.learnpython.article.Article;
import com.example.learnpython.challenge.model.ContentJson;
import com.example.learnpython.challenge.model.Type;
import com.example.learnpython.solution.Solution;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    @OneToMany(mappedBy = "challenge")
    private List<Solution> solutions;

    @ManyToMany(mappedBy = "challenges")
    @JsonIgnore
    private Set<Article> articles;
}
