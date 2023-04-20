package com.example.learnpython.challenge;

import com.example.learnpython.chapter.ChapterResponse;

import java.util.List;

public interface ChallengeService {
    ChallengeResponse createChallenge(CreateChallengeRequest request);
    List<ChallengeResponse> getChallengeByName(String name);
    ChallengeResponse getChallengeById(Long challengeId);
    List<ChallengeResponse> getChallenges();

}
