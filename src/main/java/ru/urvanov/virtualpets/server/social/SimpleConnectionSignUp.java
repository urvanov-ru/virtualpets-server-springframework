package ru.urvanov.virtualpets.server.social;
//
//import java.util.Date;
//
//import ru.urvanov.virtualpets.server.dao.domain.Role;
//import ru.urvanov.virtualpets.server.dao.domain.User;
//import ru.urvanov.virtualpets.server.service.UserService;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionSignUp;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Simple little {@link ConnectionSignUp} command that allocates new userIds in memory.
// * Doesn't bother storing a user record in any local database, since this quickstart just stores the user id in a cookie.
// * @author Keith Donald
// */
//public final class SimpleConnectionSignUp implements ConnectionSignUp {
//
//        //private final AtomicLong userIdSequence = new AtomicLong();
//        private UserService userService;
//        
//        @Transactional
//        public String execute(Connection<?> connection) {
//                //return Long.toString(userIdSequence.incrementAndGet());
//            String providerUserId = connection.getKey().getProviderUserId();
//            String providerId = connection.getKey().getProviderId();
//            User user = null;
//            if (providerId.equals("facebook")) {
//                user = userService.findByFacebookKey(providerUserId);
//            } else if (providerId.equals("vkontakte")) {
//                user = userService.findByVkontakteKey(providerUserId);
//            } else if (providerId.equals("twitter")) {
//                user = userService.findByTwitterKey(providerUserId);
//            }
//            if (user == null) {
//                user = new User();
//            }
//            if (providerId.equals("facebook")) {
//                user.setFacebookKey(providerUserId);
//            } else if (providerId.equals("vkontakte")) {
//                user.setVkontakteKey(providerUserId);
//            } else if (providerId.equals("twitter")) {
//                user.setTwitterKey(providerUserId);
//            }
//            user.setName(connection.getDisplayName());
//            user.setActiveDate(new Date());
//            user.setLoginDate(new Date());
//            if (user.getRegistrationDate() == null) {
//                user.setRegistrationDate(new Date());
//            }
//            user.setRole(Role.USER);
//            userService.save(user);
//            return Integer.toString(user.getId());
//        }
//
//        /**
//         * @return the userService
//         */
//        public UserService getUserService() {
//            return userService;
//        }
//
//        /**
//         * @param userService the userService to set
//         */
//        public void setUserService(UserService userService) {
//            this.userService = userService;
//        }
//
//}
