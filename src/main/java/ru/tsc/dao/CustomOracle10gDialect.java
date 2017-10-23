package ru.tsc.dao;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * Created by mkozin on 17.10.2017.
 */
public class CustomOracle10gDialect extends Oracle10gDialect {
    public CustomOracle10gDialect() {
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
    }
}
