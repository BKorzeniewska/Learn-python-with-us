package com.example.learnpython.solution;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolutionMapper {
    Solution toSolution(SolutionRequest solutionRequest);
    SolutionResponse toSolutionResponse(Solution solution);
}
