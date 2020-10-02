package io.grupa.surveyapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
