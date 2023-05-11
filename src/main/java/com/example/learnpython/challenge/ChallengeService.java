package com.example.learnpython.challenge;

import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.model.CreateChallengeRequest;
import com.example.learnpython.challenge.model.ExecuteChallengeRequest;
import com.example.learnpython.challenge.model.ExecutedChallengeResponse;

import java.util.List;

public interface ChallengeService {
    ChallengeResponse createChallenge(CreateChallengeRequest request);
    List<ChallengeResponse> getChallengeByName(String name);
    ChallengeResponse getChallengeById(Long challengeId);
    List<ChallengeResponse> getChallenges();
    List<ChallengeResponse> getChallengesByArticleId(Long articleId);

    ExecutedChallengeResponse execute(ExecuteChallengeRequest request);

}
