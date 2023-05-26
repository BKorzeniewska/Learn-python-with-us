package com.example.learnpython.challenge;

import com.example.learnpython.challenge.model.ChallengeResponse;
import jakarta.transaction.Transactional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {
    /*Challenge toChallenge(CreateChallengeRequest createChallengeRequest);
    ChallengeResponse toCreateChallengeResponse(Challenge challenge);*/

    @Transactional
    @Mapping(target="content", expression="java(jsonConverter.convertToDatabaseColumn(challenge.getContent())).build()")
    @Mapping(target = "articlesID", expression = "java(challenge.getArticlesID())")
    public ChallengeResponse toCreateChallengeResponse(Challenge challenge, JsonConverter jsonConverter);
}
