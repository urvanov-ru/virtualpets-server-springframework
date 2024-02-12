/**
 * 
 */
package ru.urvanov.virtualpets.server.social;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.social.connect.ConnectionRepository;
//import org.springframework.social.connect.UsersConnectionRepository;
//
///**
// * @author fedya
// *
// */
//public class ConnectionRepositoryFactory {
//
//    private UsersConnectionRepository usersConnectionRepository;
//    
//    public ConnectionRepository createConnectionRepository() {
//        org.springframework.security.core.context.SecurityContext context = SecurityContextHolder.getContext();
//        Authentication auth = context.getAuthentication();
//        Object principal = auth.getPrincipal();
//        if (principal instanceof ru.urvanov.virtualpets.server.dao.domain.User)
//        {
//            ru.urvanov.virtualpets.server.dao.domain.User user = (ru.urvanov.virtualpets.server.dao.domain.User)principal;
//            return usersConnectionRepository.createConnectionRepository(Long.toString(user.getId()));
//        } else {
//            return usersConnectionRepository.createConnectionRepository("anonymous");
//        }
//    }
//
//    /**
//     * @return the usersConnectionRepository
//     */
//    public UsersConnectionRepository getUsersConnectionRepository() {
//        return usersConnectionRepository;
//    }
//
//    /**
//     * @param usersConnectionRepository the usersConnectionRepository to set
//     */
//    public void setUsersConnectionRepository(
//            UsersConnectionRepository usersConnectionRepository) {
//        this.usersConnectionRepository = usersConnectionRepository;
//    }
//}
