/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.User;
/**
 * @author fedya
 *
 */
public interface UserDao {
    public void save(User user);
    public User findByLogin(String login);
    public List<User> list();
    public User findByLoginAndPassword(String login, String password);
    public User findById(Integer id);
    public List<User> findOnline();
    public User findByLoginAndEmail(String name, String email);
    public User findByFacebookKey(String facebookKey);
    public User findByUnid(String unid);
    public User findByRecoverPasswordKey(String recoverKey);
    public User findByVkontakteKey(String vkontakteKey);
    public User findByTwitterKey(String twitterKey);
    public List<User> findLastRegisteredUsers(int start, int limit);
    public User getReference(Integer id);
}
