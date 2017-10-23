package ru.tsc.model.dictionary;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "TSC_WRKRES_FUNC_COMP")
public class FunctionalCompetence {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Getter @Setter private long id;

    @Column
    @Getter @Setter private String text;
}
