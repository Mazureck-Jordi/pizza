package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IUtilisateurManager;
import fr.eni.pizza.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.security.Principal;

@SessionAttributes({"loggedUser"})
@Controller
public class AuthController {

    @Autowired
    private IUtilisateurManager utilisateurManager;

    @GetMapping("/login")
    public String showloginPage(Model model, @AuthenticationPrincipal UserDetails loggedUser) {

        if (loggedUser != null) {
            System.out.println("Utilisateur connecté : " + loggedUser.getUsername());
        return "redirect:/";
        }

        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);

        return "auth/login-page";
    }

    @GetMapping("/logout")
    public String Logout(@AuthenticationPrincipal UserDetails loggedUser, Model model, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        System.out.println("Utilisateur connecté : " + loggedUser.getUsername());
        return "redirect:/";
    }

    @GetMapping("/user-page")
    public String showRedirectPage (Principal principal) {

        String email = principal.getName();

        Utilisateur loggedUtilisateurFoundedByEmail = utilisateurManager.getByEmail(email);

        System.out.println("Voici les données du profil connecté : " + loggedUtilisateurFoundedByEmail);

        return "redirect:/";
    }
}
