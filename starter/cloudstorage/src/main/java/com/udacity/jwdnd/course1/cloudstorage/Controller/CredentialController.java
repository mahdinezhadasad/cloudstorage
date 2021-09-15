package com.udacity.jwdnd.course1.cloudstorage.Controller;


import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CredentialController {

    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/credentials")
    public String postCredential(Authentication authentication, RedirectAttributes redirectAttributes, @ModelAttribute Credential credential) {
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        User user = this.userService.getUser(authentication.getName());
        Integer userid = user.getUserId();
        credential.setUserId(userid);

        try {
            if (credential.getCredentialId() == null) {
                credentialService.addOrEditCredential(credential);
            } else {
                credentialService.addOrEditCredential(credential);
            }
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "New credential added!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            redirectAttributes.addFlashAttribute("message", "System error!" + e.getMessage());
        }
        return "redirect:/result";
    }

    @PostMapping("/credentials/delete")
    public String deleteCredential(RedirectAttributes redirectAttributes, @ModelAttribute Credential credential) {
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        Integer credentialId = credential.getCredentialId();


        try {
            credentialService.deleteCredential(credentialId);
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "Credentials deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            redirectAttributes.addFlashAttribute("message", "System error!" + e.getMessage());
        }
        return "redirect:/result";
    }
}


