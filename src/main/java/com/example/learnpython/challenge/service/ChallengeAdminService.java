package com.example.learnpython.challenge.service;

import com.example.learnpython.challenge.model.*;

public interface ChallengeAdminService {
    ChallengeResponse createChallenge(final CreateChallengeRequest request, final String bearerToken);
    ChallengeResponse modifyChallenge(final ModifyChallengeRequest request);
    ChallengeResponse changeVisibleChallenge(VisibleChangeRequest request);
    void deleteChallenge(final Long challengeId);


}