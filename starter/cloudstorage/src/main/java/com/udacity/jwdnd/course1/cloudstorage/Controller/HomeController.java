package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CredentialService creationService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private NoteService noteService;
    @GetMapping("/home")
    public String homeView(Model model,
                           HttpServletRequest req,
                           HttpServletResponse response
                           ){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) auth.getPrincipal();
        User user = userService.getUser(userName);

        if(user == null){
            model.addAttribute("errorUpload", "ERROR Usernot found");
            return "redirect:/logout";
        }
        Set<File> files = fileService.getFilesById(user.getUserId());
        List<Note> notes = noteService.getAllNotes(user.getUserId());
        List<Credential> credentials = creationService.getCredentialListById(user.getUserId());

        if(user == null){

            model.addAttribute("errorUpload","Not files found from this username");
        }
        if (notes == null) {
            model.addAttribute("errorNote",
                    "Notes not found from this Username!");
            return null;
        }
        if (credentials == null) {
            model.addAttribute("errorCredentials",
                    "Credentials not found from this Username!");
            return null;
        }

        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("encryptionService",encryptionService);
        return "home";
    }
}
