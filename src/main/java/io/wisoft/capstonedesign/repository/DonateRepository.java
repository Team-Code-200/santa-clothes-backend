package io.wisoft.capstonedesign.repository;

import io.wisoft.capstonedesign.domain.Donate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DonateRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Donate donate) {
        em.persist(donate);
    }

    public Donate findOne(Long id) {
        return em.find(Donate.class, id);
    }

    public List<Donate> findAll() {
        return em.createQuery("select d from Donate d", Donate.class)
                .getResultList();
    }

    public List<Donate> findByUser(String user) {
        return em.createQuery("select d from Donate d where d.user = :user", Donate.class)
                .setParameter("user", user)
                .getResultList();
    }

    public void delete(Donate donate) {
        em.remove(donate);
    }
}
