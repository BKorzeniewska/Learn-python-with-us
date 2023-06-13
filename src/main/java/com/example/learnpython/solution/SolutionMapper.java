package com.example.learnpython.solution;

import com.example.learnpython.solution.model.Solution;
import com.example.learnpython.solution.model.SolutionRequest;
import com.example.learnpython.solution.model.SolutionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolutionMapper {
    @Mapping(target="challenge.id", source="challengeId")
    Solution toSolution(SolutionRequest solutionRequest);
    @Mapping(target="challengeId", source="solution.challenge.id")
    @Mapping(target="userId", source="solution.user.id")
    SolutionResponse toSolutionResponse(Solution solution);
}
