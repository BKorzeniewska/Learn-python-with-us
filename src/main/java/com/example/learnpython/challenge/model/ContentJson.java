package com.example.learnpython.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentJson {
    protected String correctAnswer;

    protected Map<Character, String> possibleAnswers;
    protected Map<String,String> code;

}
