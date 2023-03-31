package io.wisoft.capstonedesign.domain.findorder.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FindOrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(final FindOrder findOrder) {
        em.persist(findOrder);
    }

    public Optional<FindOrder> findOne(final Long id) {
        return Optional.ofNullable(em.find(FindOrder.class, id));
    }

    public List<FindOrder> findAll() {
        return em.createQuery("select f from FindOrder f", FindOrder.class)
                .getResultList();
    }

    public List<FindOrder> findByUserDESC(final User user) {
        return em.createQuery("select f from FindOrder f where f.user = :user order by f.sendDate desc", FindOrder.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<FindOrder> findByCreatedDateDESC() {
        return em.createQuery("select f from FindOrder f order by f.sendDate desc", FindOrder.class)
                .getResultList();
    }

    public void delete(final FindOrder findOrder) {
        em.remove(findOrder);
    }
}
