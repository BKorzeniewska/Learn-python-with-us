package com.example.learnpython.challenge;

import com.example.learnpython.challenge.model.*;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ChallengeAdminService {
    ChallengeResponse createChallenge(CreateChallengeRequest request);
    ChallengeResponse modifyChallenge(ModifyChallengeRequest request);
    void deleteChallenge(final Long challengeId);


}