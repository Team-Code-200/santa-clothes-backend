package io.wisoft.capstonedesign.domain.shop.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShopRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(final Shop shop) {
        em.persist(shop);
    }

    public Optional<Shop> findOne(final Long id) {
        return Optional.ofNullable(em.find(Shop.class, id));
    }

    public List<Shop> findAll() {
        return em.createQuery("select s from Shop s", Shop.class)
                .getResultList();
    }

    public List<Shop> findByTitle(final String title) {
        return em.createQuery("select s from Shop s where s.title = :title", Shop.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Shop> findByCreatedDateDESC() {
        return em.createQuery("select s from Shop s order by s.createdDate desc", Shop.class)
                .getResultList();
    }

    public void delete(final Shop shop) {
        em.remove(shop);
    }
}
