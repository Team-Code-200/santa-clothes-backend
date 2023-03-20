package io.wisoft.capstonedesign.repository;

import io.wisoft.capstonedesign.domain.DonateOrder;
import io.wisoft.capstonedesign.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DonateOrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(DonateOrder donateOrder) {
        em.persist(donateOrder);
    }

    public DonateOrder findOne(Long id) {
        return em.find(DonateOrder.class, id);
    }

    public List<DonateOrder> findAll() {
        return em.createQuery("select d from DonateOrder d", DonateOrder.class)
                .getResultList();
    }

    public List<DonateOrder> findByUserDESC(User user) {
        return em.createQuery("select d from DonateOrder d where d.user = :user order by d.sendDate desc", DonateOrder.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<DonateOrder> findByCreatedDateDESC() {
        return em.createQuery("select d from DonateOrder d order by d.sendDate desc", DonateOrder.class)
                .getResultList();
    }

    public void delete(DonateOrder donateOrder) {
        em.remove(donateOrder);
    }
}
