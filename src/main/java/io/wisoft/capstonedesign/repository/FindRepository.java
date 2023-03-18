package io.wisoft.capstonedesign.repository;

import io.wisoft.capstonedesign.domain.Find;
import io.wisoft.capstonedesign.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FindRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Find find) {
        em.persist(find);
    }

    public Find findOne(Long id) {
        return em.find(Find.class, id);
    }

    public List<Find> findAll() {
        return em.createQuery("select f from Find f", Find.class)
                .getResultList();
    }

    public List<Find> findByUser(User user) {
        return em.createQuery("select f from Find f where f.user = :user", Find.class)
                .setParameter("user", user)
                .getResultList();
    }

    public void delete(Find find) {
        em.remove(find);
    }
}
