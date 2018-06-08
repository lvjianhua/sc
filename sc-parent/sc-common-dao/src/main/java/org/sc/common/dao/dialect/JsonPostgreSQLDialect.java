package org.sc.common.dao.dialect;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * Created by Sonic Wang on 2017/4/13.
 */
public class JsonPostgreSQLDialect extends PostgreSQL94Dialect {
    public JsonPostgreSQLDialect(){
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
        this.registerHibernateType(Types.OTHER, StandardBasicTypes.TEXT.getName());
    }
}
