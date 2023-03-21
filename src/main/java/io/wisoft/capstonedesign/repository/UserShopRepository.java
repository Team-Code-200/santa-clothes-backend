package io.wisoft.capstonedesign.repository;

import io.wisoft.capstonedesign.domain.User;
import io.wisoft.capstonedesign.domain.UserShop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserShopRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(UserShop userShop) {
        em.persist(userShop);
    }

    public UserShop findOne(Long id) {
        return em.find(UserShop.class, id);
    }

    public List<UserShop> findAll() {
        return em.createQuery("select u from UserShop u", UserShop.class)
                .getResultList();
    }

    public List<UserShop> findByUserDESC(User user) {
        return em.createQuery("select u from UserShop u where u.user = :user order by u.createdDate desc ", UserShop.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<UserShop> findByCreatedDateDESC() {
        return em.createQuery("select u from UserShop u order by u.createdDate desc", UserShop.class)
                .getResultList();
    }

    public void delete(UserShop userShop) {
        em.remove(userShop);
    }
}
