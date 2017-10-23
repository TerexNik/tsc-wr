package ru.tsc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.tsc.model.dictionary.CompetenceInTechnology;

import javax.persistence.*;

/**
 * @author Terekhin Nikita
 **/

@Entity
@Table(name = "TSC_WRKRES_PEXP_COMP_IN_TECH")
public class ProjectCompInTech {
    @Id @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Getter @Setter private long id;

    /*@LazyCollection(LazyCollectionOption.FALSE) @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECTEXPERIENCE_ID")
    @Getter @Setter private ProjectExperience projectExperience;*/

    /*@LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPETENCEINTECHNOLOGY_ID")
    @Getter @Setter private CompetenceInTechnology competenceInTechnology;*/

    @Column (name = "COMPETENCEINTECHNOLOGY_ID")
    @Getter @Setter private Long competenceInTechnology;

}
