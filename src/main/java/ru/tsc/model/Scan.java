package ru.tsc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "TSC_WRKRES_SCAN")
public class Scan {
    @Id
    @JsonIgnore @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Getter @Setter private long id;

    @Column
    @Getter @Setter
    private String name;

    @Column
    @Getter @Setter
    private String format;

    @Column
    @Lob
    @Getter @Setter
    private Blob scanfile;
}