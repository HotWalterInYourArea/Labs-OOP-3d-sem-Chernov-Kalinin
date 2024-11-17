package persistence.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@NoArgsConstructor
@Setter
abstract public class DAO<T> {
        protected Class<T> modelClass;
        protected EntityManager entityManager;
        public void create(T entity) {
            EntityTransaction t=entityManager.getTransaction();
            t.begin();
            entityManager.persist(entity);
            t.commit();
        }
        public Optional<T> read(Object primaryKey) {
            EntityTransaction t=entityManager.getTransaction();
            t.begin();
            Optional<T> entityOptional=Optional.ofNullable(entityManager.find(modelClass, primaryKey));
            t.commit();
            return entityOptional;
        }
        public List<T> readAll() {
            EntityTransaction t=entityManager.getTransaction();
            t.begin();
            TypedQuery<T> typedQuery = entityManager.createQuery("SELECT e FROM " + modelClass.getSimpleName() + " e", modelClass);
            t.commit();
        return typedQuery.getResultList();
        }
        public void delete(T entity) {
            EntityTransaction t=entityManager.getTransaction();
            t.begin();
            entityManager.remove(entity);
            t.commit();
        }
        public void update(T entity) {
            EntityTransaction t=entityManager.getTransaction();
            t.begin();
            entityManager.merge(entity);
            t.commit();
        }

}


