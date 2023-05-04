package com.example.learnpython.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentJson {
    protected String correctAnswer;
    protected Type typeQuestion;
    protected List< String> answers;
}
