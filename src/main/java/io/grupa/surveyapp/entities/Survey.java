package io.grupa.surveyapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "surveys")
@Getter
@Setter
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=64, nullable = false)
    private String name;
    private String uuid;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean active = true;

    @Column()
    private Long coordinatorId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(
            name = "questions",
            joinColumns = @JoinColumn(name = "survey_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Question> questions;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


}
