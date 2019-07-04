package com.ercancelik.eclipselink.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.persistence.config.EntityManagerProperties;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

@Slf4j
public class TenantAwareJpaTransactionManager extends JpaTransactionManager {

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        super.doBegin(transaction, definition);

        final EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(getEntityManagerFactory());
        final EntityManager em = emHolder.getEntityManager();

        String tenant = TenantContext.getCurrentTenant();
        if (StringUtils.isEmpty(tenant)) {
            throw new NoTenantProvidedException();
        }
        log.info("{} is set to EntityManager", tenant);
        em.setProperty(EntityManagerProperties.MULTITENANT_PROPERTY_DEFAULT, tenant);
    }

    class NoTenantProvidedException extends RuntimeException {

        public NoTenantProvidedException() {
            super("No tenant identifier could be resolved instance.");
        }
    }
}