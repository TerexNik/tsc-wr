package ru.tsc.model.dictionary.CompInTech;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class Products {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column
    private String product_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Vendor vendor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", vendor=" + vendor +
                '}';
    }
}
