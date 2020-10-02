package io.grupa.surveyapp.repositories;

import io.grupa.surveyapp.entities.Survey;
import io.grupa.surveyapp.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SurveyRepository extends CrudRepository<Survey, Long>{

	Optional<Survey> findByUuid(String uuid);

	Optional<Survey> findById(Long id);

}
