package io.grupa.surveyapp.controllers;

import io.grupa.surveyapp.entities.Question;
import io.grupa.surveyapp.entities.Survey;
import io.grupa.surveyapp.pojo.GenericResponse;
import io.grupa.surveyapp.pojo.QuestionAndOptions;
import io.grupa.surveyapp.pojo.Response;
import io.grupa.surveyapp.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "api/")
public class SurveyController {

    @Autowired
    SurveyService surveyService;



    @PostMapping("surveys/create")
    GenericResponse createSurvey(Principal loggedInUser, @RequestBody Survey survey){
        return surveyService.createSurvey(loggedInUser.getName(), survey);
    }

    @GetMapping("surveys/")
    Response getCoordinatorSurveys(Principal principal){
        return  surveyService.getCoordinatorSurveys(principal.getName());
    }

    @GetMapping("surveys/{surveyId}")
    Response getSingleSurvey(@PathVariable(name = "surveyId") Long surveyId){
        return  surveyService.getSingleSurvey(surveyId);
    }

    @PutMapping("surveys/{surveyId}")
    Response editSingleSurvey(Principal principal, @PathVariable(name = "surveyId") Long surveyId, @RequestBody Survey survey){
        return  surveyService.editSingleSurvey(principal.getName(), surveyId, survey);
    }

    @GetMapping("surveys/{surveyId}/questions")
    Response getSurveyQuestions(@PathVariable(name = "surveyId")Long surveyId){
        return surveyService.getSurveyQuestions(surveyId);
    }

    @PostMapping("surveys/{surveyId}/questions")
    Response addQuestionToSurvey(Principal loggedInUser, @PathVariable(name = "surveyId")Long surveyId, @RequestBody QuestionAndOptions questionAndOptions){
        return surveyService.addQuestionToSurvey(loggedInUser.getName(), surveyId, questionAndOptions);
    }


}


