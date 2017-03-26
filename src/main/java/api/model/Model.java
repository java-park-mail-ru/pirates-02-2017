package api.model;

import java.io.Serializable;

/**
 * Интерфейс модели(строки таблицы)
 */
public interface Model<ID extends Serializable> {
    /**
     * Модель должна возвращать идентификатор
     * @return id
     */
    ID getId();

//    void assignId();

    boolean isNew();
}