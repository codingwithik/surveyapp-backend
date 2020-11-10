package io.grupa.surveyapp.pojo;

import io.grupa.surveyapp.entities.Option;
import io.grupa.surveyapp.entities.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionAndOptions {
    Question question;
    List<String> options;
}
