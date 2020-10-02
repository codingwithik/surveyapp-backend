package io.grupa.surveyapp.services;

import io.grupa.surveyapp.entities.Survey;
import io.grupa.surveyapp.pojo.GenericResponse;
import io.grupa.surveyapp.pojo.QuestionAndOptions;
import io.grupa.surveyapp.pojo.Response;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SurveyService {
    GenericResponse createSurvey(String username, Survey survey);

    Response addQuestionToSurvey(String loggedInUserName, Long surveyId, QuestionAndOptions questionAndOptions);

    Response getCoordinatorSurveys(String loggedInUser);

    Response getSingleSurvey(Long surveyId);

    Response editSingleSurvey(String name, Long surveyId, Survey survey);

    Response getSurveyQuestions(Long surveyId);
}
