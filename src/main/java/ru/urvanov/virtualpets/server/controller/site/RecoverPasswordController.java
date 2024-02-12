/**
 * 
 */
package ru.urvanov.virtualpets.server.controller.site;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import ru.urvanov.virtualpets.server.controller.site.domain.RecoverPassword;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.UserService;

/**
 * @author fedya
 * 
 */
@Controller
@RequestMapping("site")
public class RecoverPasswordController {
    private Logger log = LoggerFactory
            .getLogger(RecoverPasswordController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/recoverPassword", method = RequestMethod.GET)
    public String recoverPasswordForm(Locale locale, Model model,
            @RequestParam String recoverPasswordKey) {
        RecoverPassword recoverPassword = new RecoverPassword();
        recoverPassword.setRecoverPasswordKey(recoverPasswordKey);
        recoverPassword.setNewPassword("");
        model.addAttribute("recoverPassword", recoverPassword);
        return "recoverPasswordForm";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(RecoverPassword recoverPassword, BindingResult bindingResult, Model model, Locale locale) {
        User user = userService.findByRecoverPasswordKey(recoverPassword
                .getRecoverPasswordKey());
        if (user != null) {
//            MessageDigest md5;
//            try {
//                md5 = MessageDigest.getInstance("MD5");
//            } catch (NoSuchAlgorithmException e) {
//                log.error("MD5 is not available.", e);
//                return "passwordChangeFailed";
//            }
//            String hexPasswordMd5;
//            try {
//                hexPasswordMd5 = (new HexBinaryAdapter()).marshal(md5
//                        .digest(recoverPassword.getNewPassword().getBytes("UTF-8")));
//            } catch (UnsupportedEncodingException e) {
//                log.error("Unsupported encoding", e);
//                return "passwordChangeFailed";
//            }
//            user.setPassword(hexPasswordMd5);
//            user.setRecoverPasswordKey(null);
//            userService.save(user);
            // TODO: implement password change
            return "passwordChanged";
        } else {
            return "passwordChangeFailed";
        }
    }
    
    @RequestMapping(value = "/passwordChanged", method=RequestMethod.GET)
    public String passwordChanged() {
        return "passwordChanged";
    }
    
    @RequestMapping(value="passwordChangeFailed", method=RequestMethod.GET)
    public String passwordChangeFailed() {
        return "passwordChangeFailed";
    }
}
