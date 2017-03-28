package api.repository;

import javax.persistence.EntityManager;


public interface EntityManagerAware {
    EntityManager getEntityManager();
}
