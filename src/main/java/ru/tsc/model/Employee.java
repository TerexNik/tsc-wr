package ru.tsc.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Table(name = "TSC_WRKRES_EMPLOYEE")
@Entity
public class Employee implements Serializable {
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Getter @Setter private String id;

    @Column(nullable = false)
    @Getter @Setter private String fio;

    @Column
    @Getter @Setter private String login;

    @Column
    @Temporal(TemporalType.DATE)
    @Getter @Setter private Date dateOfEmployment;

    @Column
    @Temporal(TemporalType.DATE)
    @Getter @Setter private Date experienceStart;

    @Column
    @Getter @Setter private long grade;

    @Column
    @Getter @Setter private String position;

    @Column
    @Getter @Setter private String department;

    @Column
    @Getter @Setter private String businessUnit;

/*
    @JoinTable(
            name = "TSC_WRKRES_EMPL_CERT",
            joinColumns = @JoinColumn(
                    name = "EMPLOYEE_ID",
                    referencedColumnName = "ID"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "CERTIFICATES_ID",
                    referencedColumnName = "ID"
            )
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL) @PrimaryKeyJoinColumn
*/
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID")
    @Getter @Setter private List<Certificate> certificates;

/*
    @JoinTable(
            name = "TSC_WRKRES_EMPL_PEXP",
            joinColumns = @JoinColumn(
                    name = "EMPLOYEE_ID",
                    referencedColumnName = "ID"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "PROJECTEXPERIENCE_ID",
                    referencedColumnName = "ID"
            )
    )
*/
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID")
    @Getter @Setter private List<ProjectExperience> projectExperience;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID")
    @Getter @Setter private List<EmployeeFuncComp> employeeFuncComp;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID")
    @Getter @Setter private List<EmployeeCompInTech> employeeCompInTech;
}