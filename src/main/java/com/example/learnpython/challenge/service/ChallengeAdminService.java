package com.example.learnpython.challenge.service;

import com.example.learnpython.challenge.model.*;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ChallengeAdminService {
    ChallengeResponse createChallenge(final CreateChallengeRequest request, final String bearerToken);
    ChallengeResponse modifyChallenge(final ModifyChallengeRequest request);
    void deleteChallenge(final Long challengeId);


}