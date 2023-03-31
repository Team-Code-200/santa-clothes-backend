package io.wisoft.capstonedesign.domain.usershop.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserShopRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(final UserShop userShop) {
        em.persist(userShop);
    }

    public Optional<UserShop> findOne(final Long id) {
        return Optional.ofNullable(em.find(UserShop.class, id));
    }

    public List<UserShop> findAll() {
        return em.createQuery("select u from UserShop u", UserShop.class)
                .getResultList();
    }

    public List<UserShop> findByUserDESC(final User user) {
        return em.createQuery("select u from UserShop u where u.user = :user order by u.createdDate desc ", UserShop.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<UserShop> findByCreatedDateDESC() {
        return em.createQuery("select u from UserShop u order by u.createdDate desc", UserShop.class)
                .getResultList();
    }

    public void delete(final UserShop userShop) {
        em.remove(userShop);
    }
}
