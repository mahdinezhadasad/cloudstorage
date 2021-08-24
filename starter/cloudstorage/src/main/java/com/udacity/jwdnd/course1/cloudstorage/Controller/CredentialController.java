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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CredentialController {

    @Autowired
    private UserService userService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private HashService hashService;

    public String saveCredentials(@ModelAttribute Credential reqCredential,
                                  Model model,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletRequest req,
                                  HttpServletResponse res){

        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userService.getUser(username);

        if(user == null){
 redirectAttributes.addFlashAttribute("message","User not found");
            return "redirect:/logout";
        }

        if(reqCredential.getUrl() == null || reqCredential.getUrl().isEmpty()
        || reqCredential.getUsername() == null|| reqCredential.getUsername().isEmpty()
                || reqCredential.getPassword().isEmpty()
                || reqCredential.getPassword() == null) {
            redirectAttributes.addFlashAttribute("message", "Credential fields can't be void!");
        }

        if(reqCredential.getUserId() == null ){
  reqCredential.setCredentialId(user.getUserId());
            Integer id = credentialService.saveOne(reqCredential);
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/result";
        } else {
            Integer id = credentialService.addOrEditCredential(reqCredential);
            redirectAttributes.addFlashAttribute("success", true);
        }
        return "redirect:/result";
    }


    @GetMapping("/delete-credential/{id}")
    public String deleteCredential(@PathVariable Integer id,
                                   RedirectAttributes redirectAttributes,
                                   Model model) throws IOException {
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        Integer idDel = credentialService.deleteCredential(id);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/result";
    }



}

