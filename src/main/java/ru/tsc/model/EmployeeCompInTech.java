package ru.tsc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.tsc.model.dictionary.CompetenceInTechnology;

import javax.persistence.*;

@Entity
@Table(name = "TSC_WRKRES_EMPL_COMP_IN_TECH")
public class EmployeeCompInTech {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Getter @Setter private long id;

    /*@LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.MERGE) @PrimaryKeyJoinColumn @JsonIgnore
    @Getter @Setter private Employee employee;*/

/*
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne @PrimaryKeyJoinColumn
    @Getter @Setter private CompetenceInTechnology competenceInTechnology;
*/

    @Column (name = "COMPETENCEINTECHNOLOGY_ID")
    @Getter @Setter private Long competenceInTechnology;

    @Column
    @Getter @Setter private int selfEvaluation;
}
