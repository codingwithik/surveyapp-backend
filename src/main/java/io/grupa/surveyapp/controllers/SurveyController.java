package io.grupa.surveyapp.controllers;

import io.grupa.surveyapp.entities.Survey;
import io.grupa.surveyapp.pojo.GenericResponse;
import io.grupa.surveyapp.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "api/surveys/")
public class SurveyController {

    @Autowired
    SurveyService surveyService;



    @PostMapping("create")
    GenericResponse createSurvey(Principal loggedInUser, @RequestBody Survey survey){
        loggedInUser.getName();
        return surveyService.createSurvey(loggedInUser.getName(), survey);
    }
}
