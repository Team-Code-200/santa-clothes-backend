package io.wisoft.capstonedesign.domain.find.persistence;

import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.user.persistence.User;
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

    public List<Find> findByCreatedDateDESC() {
        return em.createQuery("select f from Find f order by f.createdDate desc", Find.class)
                .getResultList();
    }

    public List<Find> findByTag(Tag tag) {
        return em.createQuery("select f from Find  f where f.tag = :tag", Find.class)
                .setParameter("tag", tag)
                .getResultList();
    }

    public void delete(Find find) {
        em.remove(find);
    }
}