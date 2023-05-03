package com.example.learnpython.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
public class closedQuestions extends ContentJson{
    protected Map<Character, String> possibleAnswers;

}
