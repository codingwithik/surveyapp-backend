package io.grupa.surveyapp.services;

import io.grupa.surveyapp.entities.Survey;
import io.grupa.surveyapp.pojo.GenericResponse;
import org.springframework.stereotype.Service;

public interface SurveyService {
    GenericResponse createSurvey(String username, Survey survey);
}
