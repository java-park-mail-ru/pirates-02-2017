package api.repository_old;

import api.model.Model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;


public abstract class AbstractBaseDAO<T extends Model<ID>, ID extends Serializable> implements BaseDAO<T, ID> {
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}