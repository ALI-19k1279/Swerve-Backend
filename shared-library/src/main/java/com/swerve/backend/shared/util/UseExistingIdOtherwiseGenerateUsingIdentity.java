package com.swerve.backend.shared.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class UseExistingIdOtherwiseGenerateUsingIdentity extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object)
            throws HibernateException {
        Serializable id =
                (Serializable) session.getEntityPersister(null, object)
                        .getClassMetadata()
                        .getIdentifier(object, session);
        return id != null ? id : (Serializable) super.generate(session, object);
    }
}
