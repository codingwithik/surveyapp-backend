package io.grupa.surveyapp.repositories;

import io.grupa.surveyapp.entities.Option;
import io.grupa.surveyapp.entities.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends CrudRepository<Option, Long>{

	Optional<Option> findByUuid(String uuid);

	Optional<Option> findById(Long id);

    List<Option> findByQuestionId(Long id);
}
