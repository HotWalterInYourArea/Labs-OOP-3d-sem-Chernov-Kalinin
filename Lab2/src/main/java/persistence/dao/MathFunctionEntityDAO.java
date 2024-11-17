package persistence.dao;

import jakarta.persistence.*;
import persistence.entity.MathFunctionEntity;

import java.util.List;

public class MathFunctionEntityDAO extends DAO<MathFunctionEntity>{
    public MathFunctionEntityDAO(){
        modelClass= MathFunctionEntity.class;
    }
    public List<MathFunctionEntity> readByName(String name) {
        EntityTransaction t=entityManager.getTransaction();
        t.begin();
        TypedQuery<MathFunctionEntity> typedQuery = entityManager.createQuery("SELECT e FROM " + modelClass.getSimpleName() + " e where e.name=:name", modelClass);
        typedQuery.setParameter("name",name);
        t.commit();
        return typedQuery.getResultList();
    }
    @Override
    public void delete(MathFunctionEntity entity) {
        EntityTransaction t=entityManager.getTransaction();
        t.begin();
        entityManager.remove(entityManager.find(MathFunctionEntity.class,entity.getFunction_id()));
        t.commit();
    }
}
