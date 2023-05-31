package com.example.learnpython.challenge;

import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.model.CreateChallengeRequest;
import com.example.learnpython.challenge.model.ExecuteChallengeRequest;
import com.example.learnpython.challenge.model.ExecutedChallengeResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ChallengeService {
    List<ChallengeResponse> getChallengeByName(String name);
    ChallengeResponse getChallengeById(Long challengeId);
    List<ChallengeResponse> getChallenges();
    List<ChallengeResponse> getChallengesByArticleId(Long articleId);
    @Transactional
    ExecutedChallengeResponse execute(ExecuteChallengeRequest request);

}
