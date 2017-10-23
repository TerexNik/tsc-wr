package ru.tsc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.tsc.model.dictionary.CompetenceInTechnology;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TSC_WRKRES_CERTIFICATE")
public class Certificate implements Serializable {
    @Id
    @JsonIgnore @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Getter @Setter private long id;

    @Column
    @Getter @Setter private String name;

    @Column
    @Getter @Setter private String vendor;

    @Column
    @Temporal(TemporalType.DATE)
    @Getter @Setter private Date certDate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @Column
    @Getter @Setter private String scan;

/*
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @Getter @Setter private CompetenceInTechnology competenceInTechnology;
*/
    @Column (name = "COMPETENCEINTECHNOLOGY_ID")
    @Getter @Setter private Long competenceInTechnology;
}