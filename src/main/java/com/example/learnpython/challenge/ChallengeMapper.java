package com.example.learnpython.challenge;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {
    Challenge toChallenge(CreateChallengeRequest createChallengeRequest);
    ChallengeResponse toCreateChallengeResponse(Challenge challenge);
}
