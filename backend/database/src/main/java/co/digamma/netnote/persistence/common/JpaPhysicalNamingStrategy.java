package co.digamma.netnote.persistence.common;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

@Component
public class JpaPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private final static String PREFIX = "jpa";

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment context) {
        Identifier physicalTableName = super.toPhysicalTableName(logicalName, context);
        String text = physicalTableName.getText();
        if (text.toLowerCase().startsWith(PREFIX)) {
            return Identifier.toIdentifier(text.substring(PREFIX.length()));
        }
        return physicalTableName;
    }


}
