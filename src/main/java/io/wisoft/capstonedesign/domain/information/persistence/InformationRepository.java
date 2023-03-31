package io.wisoft.capstonedesign.domain.information.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InformationRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(final Information information) {
        em.persist(information);
    }

    public Optional<Information> findOne(final Long id) {
        return Optional.ofNullable(em.find(Information.class, id));
    }

    public List<Information> findAll() {
        return em.createQuery("select i from Information i", Information.class)
                .getResultList();
    }

    public List<Information> findByUser(final User user) {
        return em.createQuery("select i from Information i where i.user = :user", Information.class)
                .setParameter("user", user)
                .getResultList();
    }

    public void delete(final Information information) {
        em.remove(information);
    }
}
