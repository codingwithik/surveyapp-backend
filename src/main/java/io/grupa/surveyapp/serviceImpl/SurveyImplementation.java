package io.grupa.surveyapp.serviceImpl;

import io.grupa.surveyapp.entities.Survey;
import io.grupa.surveyapp.entities.User;
import io.grupa.surveyapp.pojo.GenericResponse;
import io.grupa.surveyapp.repositories.SurveyRepository;
import io.grupa.surveyapp.repositories.UserRepository;
import io.grupa.surveyapp.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurveyImplementation implements SurveyService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    MessageSource messageSource;



    @Override
    public GenericResponse createSurvey(String username, Survey survey) {
        System.out.println(username);
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
}
