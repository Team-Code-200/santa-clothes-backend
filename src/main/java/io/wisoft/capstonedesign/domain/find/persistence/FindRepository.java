package io.wisoft.capstonedesign.domain.find.persistence;

import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FindRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(final Find find) {
        em.persist(find);
    }

    public Optional<Find> findOne(final Long id) {
        return Optional.ofNullable(em.find(Find.class, id));
    }

    public List<Find> findAll() {
        return em.createQuery("select f from Find f", Find.class)
                .getResultList();
    }

    public List<Find> findByUser(final User user) {
        return em.createQuery("select f from Find f where f.user = :user", Find.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Find> findByCreatedDateDESC() {
        return em.createQuery("select f from Find f order by f.createdDate desc", Find.class)
                .getResultList();
    }

    public List<Find> findByTag(final Tag tag) {
        return em.createQuery("select f from Find  f where f.tag = :tag", Find.class)
                .setParameter("tag", tag)
                .getResultList();
    }

    public void delete(final Find find) {
        em.remove(find);
    }
}
