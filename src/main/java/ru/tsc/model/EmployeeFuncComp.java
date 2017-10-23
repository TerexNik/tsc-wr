package ru.tsc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.tsc.model.dictionary.FunctionalCompetence;

import javax.persistence.*;


@Entity
@Table(name = "TSC_WRKRES_EMPL_FUNC_COMP")
public class EmployeeFuncComp {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Getter @Setter private long id;


    /*@LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "EMPLOYEE_ID")
    @JsonIgnore @ManyToOne
    @Getter @Setter private Employee employee;*/

/*
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne @PrimaryKeyJoinColumn
    @Getter @Setter private FunctionalCompetence functionalCompetence;
*/
    @Column (name = "FUNCTIONALCOMPETENCE_ID")
    @Getter @Setter private Long functionalCompetence;

    @Column
    @Getter @Setter private int selfEvaluation;
}
