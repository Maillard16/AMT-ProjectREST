/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.amtproject.services;

import ch.heigvd.amt.amtproject.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Calixte
 */
@Stateless
public class UserManager implements UserManagerLocal {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public User findUserById(long userId) {
        return em.find(User.class, userId);
    }

    @Override
    public List<User> findAllUser() {
        return em.createNamedQuery("findAllUsers").getResultList();
    }

    @Override
    public long createUser(User user) {
        em.persist(user);
        em.flush();
        return user.getId();
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(long userId) {
        em.remove(em.find(User.class, userId));
    }
}
