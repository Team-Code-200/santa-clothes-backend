package io.wisoft.capstonedesign.domain.donate.persistence;

import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.user.persistence.User;
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

    public List<Donate> findByUser(User user) {
        return em.createQuery("select d from Donate d where d.user = :user", Donate.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Donate> findByCreatedDateDESC() {
        return em.createQuery("select d from Donate d order by d.createdDate desc", Donate.class)
                .getResultList();
    }

    public List<Donate> findByTag(Tag tag) {
        return em.createQuery("select d from Donate d where d.tag = :tag", Donate.class)
                .setParameter("tag", tag)
                .getResultList();
    }

    public void delete(Donate donate) {
        em.remove(donate);
    }
}
