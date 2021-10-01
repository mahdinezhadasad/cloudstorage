package com.udacity.jwdnd.course1.cloudstorage.Controller;


import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signUpView(){


        return "signup";
    }

    @PostMapping
    public String signUp(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model){
        String signupErrorMessage = null;
        if(!userService.isUserAvailable(user.getUserName())){
            signupErrorMessage = "The user name already is exist";

        }
        if(signupErrorMessage == null){

            int rowsAdded = userService.create(user);

            if(rowsAdded<0){

                signupErrorMessage = "There was an Error when sign up. please try again";

            }
        }

        if(signupErrorMessage==null){

            model.addAttribute("signupSuccess",true);
            redirectAttributes.addFlashAttribute("success",true);

            return "signup";


        }
        if (signupErrorMessage != null) {
            model.addAttribute("error",signupErrorMessage);
            return "signup";
        }

        return "redirect:/login?signup=true";





    }



}
