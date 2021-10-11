package spring.music.web.controllers;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.music.services.impl.MusicUserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final MusicUserService musicUserService;

    public UserController(MusicUserService musicUserService) {
        this.musicUserService = musicUserService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/login-err")
    public ModelAndView badCredentials(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                    String username){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bad_credentials", true);
        modelAndView.addObject("inputUsername", username);
        modelAndView.setViewName("/login");
        return modelAndView;
    }
}
