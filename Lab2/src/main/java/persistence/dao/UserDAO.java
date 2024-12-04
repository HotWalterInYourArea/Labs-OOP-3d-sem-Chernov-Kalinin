package persistence.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import persistence.entity.MathFunctionEntity;
import persistence.entity.User;

import java.util.Optional;

public class UserDAO extends DAO<User> {
    public UserDAO(){
        modelClass= User.class;
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("sample");
        entityManager = emf.createEntityManager();
    }
    public Optional<User> readByUserPassword(String username,String password){
        EntityTransaction t=entityManager.getTransaction();
        t.begin();
        TypedQuery<User> query=entityManager.createQuery("SELECT u FROM User u WHERE u.name = :username AND u.password = :password", User.class);
        query.setParameter("username",username);
        query.setParameter("password",password);
        t.commit();
        return Optional.of(query.getSingleResult());
    }
    public void delete(String id) {
        EntityTransaction t=entityManager.getTransaction();
        t.begin();
        entityManager.remove(entityManager.find(User.class,id));
        t.commit();
    }
    public void detachAll(){
        entityManager.close();
    }
}
