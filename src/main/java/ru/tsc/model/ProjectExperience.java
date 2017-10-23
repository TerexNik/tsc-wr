package ru.tsc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.tsc.model.dictionary.CompetenceInTechnology;
import ru.tsc.model.dictionary.Project;
import ru.tsc.model.dictionary.ProjectRole;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "TSC_WRKRES_PROJECT_EXP")
@Entity
public class ProjectExperience {
    @Id @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Getter @Setter private long id;

    @Column @Temporal(TemporalType.DATE)
    @Getter @Setter private Date participationStart;

    @Column @Temporal(TemporalType.DATE)
    @Getter @Setter private Date participationEnd;

/*
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "PROJECTROLE_ID")
    @Getter @Setter private ProjectRole projectRole;
*/

    @Column (name = "PROJECTROLE_ID")
    @Getter @Setter private Long projectRole;

/*
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECT_ID")
    @Getter @Setter private Project project;
*/

    @Column (name = "PROJECT_ID")
    @Getter @Setter private Long project;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECTEXPERIENCE_ID")
    @Getter @Setter private List<ProjectCompInTech> competenceInTechnology;
}