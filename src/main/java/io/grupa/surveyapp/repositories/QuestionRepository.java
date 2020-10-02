package io.grupa.surveyapp.repositories;

import io.grupa.surveyapp.entities.Question;
import io.grupa.surveyapp.entities.Survey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long>{

	Optional<Question> findByUuid(String uuid);

	Optional<Question> findById(Long id);

    List<Question> findBySurveyId(Long id);
}
