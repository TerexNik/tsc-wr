package ru.tsc.model.dictionary.CompInTech;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table
public class Module {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column
    private String module_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Products products;

//    @ManyToOne
//    @JoinColumn(name = "products_name")

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", module_name='" + module_name + '\'' +
                ", products=" + products +
                '}';
    }
}
