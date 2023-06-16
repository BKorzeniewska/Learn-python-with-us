package com.example.learnpython.challenge.service;

import com.example.learnpython.challenge.Challenge;
import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.model.CreateChallengeRequest;
import com.example.learnpython.challenge.model.ExecuteChallengeRequest;
import com.example.learnpython.challenge.model.ExecutedChallengeResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ChallengeService {
    List<ChallengeResponse> getChallengeByName(String name, String ...bearerToken);
    ChallengeResponse getChallengeById(Long challengeId, String ...bearerToken);
    List<ChallengeResponse> getChallenges(String ...bearerToken);
    List<ChallengeResponse> getChallengesByArticleId(Long articleId, String ...bearerToken);
    boolean itsDone(Challenge challenge, String bearerToken);
    @Transactional
    ExecutedChallengeResponse execute(ExecuteChallengeRequest request);

}
