package io.wisoft.capstonedesign.repository;

import io.wisoft.capstonedesign.domain.Shop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Shop shop) {
        em.persist(shop);
    }

    public Shop findOne(Long id) {
        return em.find(Shop.class, id);
    }

    public List<Shop> findAll() {
        return em.createQuery("select s from Shop s", Shop.class)
                .getResultList();
    }

    public List<Shop> findByTitle(String title) {
        return em.createQuery("select s from Shop s where s.title = :title", Shop.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Shop> findByCreatedDateDESC() {
        return em.createQuery("select s from Shop s order by s.createdDate desc", Shop.class)
                .getResultList();
    }

    public void delete(Shop shop) {
        em.remove(shop);
    }
}
