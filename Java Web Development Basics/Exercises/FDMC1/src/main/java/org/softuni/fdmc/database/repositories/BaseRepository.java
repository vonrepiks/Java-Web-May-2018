package org.softuni.fdmc.database.repositories;

import org.hibernate.TransactionException;
import org.softuni.fdmc.database.RepositoryActionInvoker;
import org.softuni.fdmc.database.RepositoryActionResult;
import org.softuni.fdmc.database.listeners.LocalEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public abstract class BaseRepository {
    private EntityTransaction entityTransaction;
    protected EntityManager entityManager;

    protected BaseRepository() {
    }

    private void initializeEntityManager() {
        this.entityManager = LocalEntityManagerFactory.createEntityManager();
    }

    private void initializeEntityTransaction() {
        this.entityTransaction = this.entityManager.getTransaction();
    }

    private void dismiss() {
        this.entityManager.close();
    }

    protected RepositoryActionResult executeAction(RepositoryActionInvoker repositoryActionInvoker) {
        this.initializeEntityManager();
        this.initializeEntityTransaction();

        RepositoryActionResult repositoryActionResult = new RepositoryActionResult();
        try {
            this.entityTransaction.begin();

            repositoryActionInvoker.invoke(repositoryActionResult);

            this.entityTransaction.commit();
        } catch (TransactionException te) {
            if (this.entityTransaction != null && this.entityTransaction.isActive()) {
                this.entityTransaction.rollback();
            }
            te.printStackTrace();
        }

        this.dismiss();

        return repositoryActionResult;
    }
}
