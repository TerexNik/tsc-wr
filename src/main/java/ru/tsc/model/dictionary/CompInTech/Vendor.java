package ru.tsc.model.dictionary.CompInTech;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table
@Entity
public class Vendor {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column
    private String vendor_name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "id=" + id +
                ", vendor_name='" + vendor_name + '\'' +
                '}';
    }
}
