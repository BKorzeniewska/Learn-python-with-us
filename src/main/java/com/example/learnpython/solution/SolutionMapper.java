package com.example.learnpython.solution;

import com.example.learnpython.solution.model.Solution;
import com.example.learnpython.solution.model.SolutionRequest;
import com.example.learnpython.solution.model.SolutionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolutionMapper {
    Solution toSolution(SolutionRequest solutionRequest);
    SolutionResponse toSolutionResponse(Solution solution);
}
