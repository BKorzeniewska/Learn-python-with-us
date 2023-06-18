package com.example.learnpython.challenge;

import com.example.learnpython.challenge.model.ChallengeResponse;
import com.example.learnpython.challenge.service.ChallengeService;
import jakarta.transaction.Transactional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {

    @Transactional
    @Mapping(target="content", expression="java(jsonConverter.convertToDatabaseColumn(challenge.getContent())).build()")
    @Mapping(target="done", expression="java(challengeService.itsDone(challenge, bearerToken))")
    @Mapping(target="userId", source="challenge.user.id")
    @Mapping(target = "articlesID", expression = "java(challenge.getArticlesID())")
     ChallengeResponse toCreateChallengeResponse(Challenge challenge, JsonConverter jsonConverter, ChallengeService challengeService, String bearerToken);
}
