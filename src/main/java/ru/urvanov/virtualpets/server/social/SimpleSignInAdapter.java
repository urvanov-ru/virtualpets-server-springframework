package ru.urvanov.virtualpets.server.social;

//
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import ru.urvanov.virtualpets.server.dao.domain.Role;
//import ru.urvanov.virtualpets.server.dao.domain.User;
//import ru.urvanov.virtualpets.server.service.UserService;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.web.SignInAdapter;
//import org.springframework.web.context.request.NativeWebRequest;
//
///**
// * Signs the user in by setting the currentUser property on the {@link SecurityContext}.
// * Remembers the sign-in after the current request completes by storing the user's id in a cookie.
// * This is cookie is read in {@link UserInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)} on subsequent requests.
// * @author Keith Donald
// * @see UserInterceptor
// */
//public final class SimpleSignInAdapter implements SignInAdapter {
//        
//        private UserService userService;
//        
//        public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
//                SecurityContext context = SecurityContextHolder.getContext();
//                //Authentication auth = context.getAuthentication();
//                
//                Set<GrantedAuthority> granted = new HashSet<GrantedAuthority>();
//                granted.add(new SimpleGrantedAuthority("ROLE_USER"));
//                
//                
//                
//                String providerUserId = connection.getKey().getProviderUserId();
//                String providerId = connection.getKey().getProviderId();
//                User user = null;
//                if (providerId.equals("facebook")) {
//                    user = userService.findByFacebookKey(providerUserId);
//                } else if (providerId.equals("vkontakte")) {
//                    user = userService.findByVkontakteKey(providerUserId);
//                } else if (providerId.equals("twitter")) {
//                    user = userService.findByTwitterKey(providerUserId);
//                }
//                if (user == null) {
//                    user = new User();
//                }
//                if (providerId.equals("facebook")) {
//                    user.setFacebookKey(providerUserId);
//                } else if (providerId.equals("vkontakte")) {
//                    user.setVkontakteKey(providerUserId);
//                } else if (providerId.equals("twitter")) {
//                    user.setTwitterKey(providerUserId);
//                }
//                user.setName(connection.getDisplayName());
//                user.setActiveDate(new Date());
//                user.setLoginDate(new Date());
//                if (user.getRegistrationDate() == null) {
//                    user.setRegistrationDate(new Date());
//                }
//                user.setRole(Role.USER);
//                userService.save(user);
//                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                        user, null, granted);
//                context.setAuthentication(token);
//                //userCookieGenerator.addCookie(userId, request.getNativeResponse(HttpServletResponse.class));
//                return null;
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
