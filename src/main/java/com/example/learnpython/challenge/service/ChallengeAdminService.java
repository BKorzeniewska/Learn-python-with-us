package com.example.learnpython.challenge.service;

import com.example.learnpython.challenge.model.*;
import jakarta.transaction.Transactional;

public interface ChallengeAdminService {
    ChallengeResponse createChallenge(final CreateChallengeRequest request, final String bearerToken);

    ChallengeResponse modifyChallenge(final ModifyChallengeRequest request);

    ChallengeResponse changeVisibleChallenge(VisibleChangeRequest request);

    @Transactional
    void deleteChallenge(final Long challengeId);


}