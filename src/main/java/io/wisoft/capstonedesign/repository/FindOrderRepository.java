package io.wisoft.capstonedesign.repository;

import io.wisoft.capstonedesign.domain.FindOrder;
import io.wisoft.capstonedesign.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FindOrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(FindOrder findOrder) {
        em.persist(findOrder);
    }

    public FindOrder findOne(Long id) {
        return em.find(FindOrder.class, id);
    }

    public List<FindOrder> findAll() {
        return em.createQuery("select f from FindOrder f", FindOrder.class)
                .getResultList();
    }

    public List<FindOrder> findByUserDESC(User user) {
        return em.createQuery("select f from FindOrder f where f.user = :user order by f.sendDate desc", FindOrder.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<FindOrder> findByCreatedDateDESC() {
        return em.createQuery("select f from FindOrder f order by f.sendDate desc", FindOrder.class)
                .getResultList();
    }

    public void delete(FindOrder findOrder) {
        em.remove(findOrder);
    }
}
