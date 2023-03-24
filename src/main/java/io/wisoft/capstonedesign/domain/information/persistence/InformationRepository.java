package io.wisoft.capstonedesign.domain.information.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InformationRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Information information) {
        em.persist(information);
    }

    public Information findOne(Long id) {
        return em.find(Information.class, id);
    }

    public List<Information> findAll() {
        return em.createQuery("select i from Information i", Information.class)
                .getResultList();
    }

    public List<Information> findByUser(User user) {
        return em.createQuery("select i from Information i where i.user = :user", Information.class)
                .setParameter("user", user)
                .getResultList();
    }

    public void delete(Information information) {
        em.remove(information);
    }
}