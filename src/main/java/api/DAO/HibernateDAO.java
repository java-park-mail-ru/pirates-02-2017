package api.DAO;


import api.model.Model;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


public abstract class HibernateDAO<T extends Model<ID>, ID extends Serializable> implements DAO<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    public abstract Class<T> getEntityClass();


    public String getEntityName() {
        return this.getEntityClass().getSimpleName();
    }


    @Transactional
    @Override
    public T persist(@NotNull T entity) {
        entityManager.persist(entity);
        entityManager.flush();

        return entityManager.find(this.getEntityClass(), entity.getId());
    }


    @Transactional
    @Override
    public T find(@NotNull ID id) {
        return entityManager.find(this.getEntityClass(), id);
    }


    @Transactional
    @Override
    public int update(@NotNull String jpqlQuery, @Nullable Map<String, Object> params) {
        final Query query = entityManager.createQuery(jpqlQuery);

        if (params != null) {
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return query.executeUpdate();
    }


    @Transactional
    @Override
    public void delete(@NotNull ID id) {
        final T entity = entityManager.find(this.getEntityClass(), id);

        entityManager.remove(entity);
    }


    @Transactional
    @Override
    public void deleteAll() {
        entityManager.createQuery(
                "DELETE FROM " + this.getEntityName() + " u").executeUpdate();
    }


    @Transactional
    @Override
    public List<T> select(@NotNull String jpqlQuery, @Nullable Map<String, Object> params) {
        final TypedQuery<T> query = entityManager.createQuery(jpqlQuery, this.getEntityClass());

        if (params != null) {
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return query.getResultList();
    }
}
