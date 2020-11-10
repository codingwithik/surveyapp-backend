package io.grupa.surveyapp.entities;

import io.grupa.surveyapp.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;
    private String uuid;

    @Column(nullable = false)
    private QuestionType type ;

    @Column(name = "survey_id")
    private Long surveyId;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "options",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )private List<Option> options;

}
