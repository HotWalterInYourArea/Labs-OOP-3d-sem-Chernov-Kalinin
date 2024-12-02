package persistence.dao;

import jakarta.persistence.*;
import persistence.entity.MathFunctionEntity;

import java.util.List;

public class MathFunctionEntityDAO extends DAO<MathFunctionEntity>{
    public MathFunctionEntityDAO(){
        modelClass= MathFunctionEntity.class;
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("sample");
        entityManager = emf.createEntityManager();
    }
    public List<MathFunctionEntity> readByName(String name) {
        EntityTransaction t=entityManager.getTransaction();
        t.begin();
        TypedQuery<MathFunctionEntity> typedQuery = entityManager.createQuery("SELECT e FROM " + modelClass.getSimpleName() + " e where e.name=:name", modelClass);
        typedQuery.setParameter("name",name);
        t.commit();
        return typedQuery.getResultList();
    }
    public void delete(Long Id) {
        EntityTransaction t=entityManager.getTransaction();
        t.begin();
        entityManager.remove(entityManager.find(MathFunctionEntity.class,Id));
        t.commit();
    }
    public void detachAll(){
        entityManager.close();
    }
}
