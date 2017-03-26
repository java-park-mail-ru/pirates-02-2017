package api.repository;

import api.model.Model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by Vileven on 27.03.17.
 */
public abstract class AbstractBaseDAO<T extends Model<ID>, ID extends Serializable> implements BaseDAO<T, ID> {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}