package io.grupa.surveyapp.serviceImpl;

import io.grupa.surveyapp.entities.Option;
import io.grupa.surveyapp.entities.Question;
import io.grupa.surveyapp.entities.Survey;
import io.grupa.surveyapp.entities.User;
import io.grupa.surveyapp.pojo.GenericDataResponse;
import io.grupa.surveyapp.pojo.GenericResponse;
import io.grupa.surveyapp.pojo.QuestionAndOptions;
import io.grupa.surveyapp.pojo.Response;
import io.grupa.surveyapp.repositories.OptionRepository;
import io.grupa.surveyapp.repositories.QuestionRepository;
import io.grupa.surveyapp.repositories.SurveyRepository;
import io.grupa.surveyapp.repositories.UserRepository;
import io.grupa.surveyapp.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyImplementation implements SurveyService {
	
    @Autowired
    UserRepository userRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    MessageSource messageSource;



    @Override
    public GenericResponse createSurvey(String username, Survey survey) {
        Optional<User> user = userRepository.findByPhoneNumber(username);
        if(user.isEmpty())
            return new GenericResponse(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()),
                    false, "101");
        if(survey.getName().isEmpty())
            return new GenericResponse(messageSource.getMessage("attribute.required", new Object[]{"name"} , LocaleContextHolder.getLocale()),
                    false, "101");
        if(survey.getDescription() == null || survey.getDescription().isEmpty())
            return new GenericResponse(messageSource.getMessage("attribute.required", new Object[]{"description"} , LocaleContextHolder.getLocale()),
                    false, "101");

        // generate uuid for survey
        String surveyUuid = java.util.UUID.randomUUID().toString();
        survey.setUuid(surveyUuid);
        survey.setCoordinatorId(user.get().getId());

        surveyRepository.save(survey);

        return new GenericResponse(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()), true, "000");

    }

    @Override
    public Response getCoordinatorSurveys(String loggedInUser) {
        Optional<User> user = userRepository.findByPhoneNumber(loggedInUser);
        if(user.isEmpty())
            return new GenericResponse(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()),
                    false, "101");

        GenericDataResponse<List<Survey>> response = new GenericDataResponse<List<Survey>>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                true, "000");
        response.setData(surveyRepository.findByCoordinatorId(user.get().getId()));
        return response;
    }

    @Override
    public Response getSingleSurvey(Long surveyId) {
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        if(survey.isEmpty())
            return new GenericResponse(messageSource.getMessage("not.found", new Object[]{surveyId, "Survey"}, LocaleContextHolder.getLocale()),
                    false, "101");
        GenericDataResponse<Survey> response = new GenericDataResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                true, "000");
        survey.get().getQuestions();
        response.setData(survey.get());
        return  response;
    }


    @Override
    public Response addQuestionToSurvey(String loggedInUserName, Long surveyId, QuestionAndOptions questionAndOptions) {
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        if(survey.isEmpty())
            return new GenericResponse(messageSource.getMessage("not.found", new Object[]{surveyId, "Survey"}, LocaleContextHolder.getLocale()),
                    false, "101");
        Question question = questionAndOptions.getQuestion();
        question.setSurveyId(surveyId);
        String uuid = java.util.UUID.randomUUID().toString();
        question.setUuid(uuid);

        question = questionRepository.save(question);

        List<String> optionsStr = questionAndOptions.getOptions();
        List<Option> options = new ArrayList<>();
        for(String optionStr: optionsStr){
            Option option = new Option();
            option.setOption(optionStr);
            option.setQuestionId(question.getId());
            String ouuid = java.util.UUID.randomUUID().toString();
            option.setUuid(ouuid);
        }

        optionRepository.saveAll(options);


        return  new GenericResponse(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                true, "000");
    }

    @Override
    public Response getSurveyQuestions(Long surveyId) {
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        if(survey.isEmpty())
            return new GenericResponse(messageSource.getMessage("not.found", new Object[]{surveyId, "Survey"}, LocaleContextHolder.getLocale()),
                    false, "101");
        GenericDataResponse<Survey> response = new GenericDataResponse<>(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()),
                true, "000");
        survey.get().setQuestions(questionRepository.findBySurveyId(surveyId));
        response.setData(survey.get());
        return  response;
    }


    @Override
    public Response editSingleSurvey(String name, Long surveyId, Survey survey) {
        Optional<User> user = userRepository.findByPhoneNumber(name);
        if(user.isEmpty())
            return new GenericResponse(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()),
                    false, "101");
        Optional<Survey> oldSurvey = surveyRepository.findByIdAndCoordinatorId(surveyId, user.get().getId());
        if(oldSurvey.isEmpty())
            return new GenericResponse(messageSource.getMessage("not.found", new Object[]{surveyId, "Survey"}, LocaleContextHolder.getLocale()),
                    false, "101");

        if(survey.getName().isEmpty())
            return new GenericResponse(messageSource.getMessage("attribute.required", new Object[]{"name"} , LocaleContextHolder.getLocale()),
                    false, "101");
        else oldSurvey.get().setName(survey.getName());

        if(survey.getDescription() == null || survey.getDescription().isEmpty())
            return new GenericResponse(messageSource.getMessage("attribute.required", new Object[]{"description"} , LocaleContextHolder.getLocale()),
                    false, "101");
        else oldSurvey.get().setDescription(survey.getDescription());

        surveyRepository.save(oldSurvey.get());

        return new GenericResponse(messageSource.getMessage("000", null, LocaleContextHolder.getLocale()), true, "000");

    }
}
