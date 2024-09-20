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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@SessionAttributes({"loggedUser"})
@Controller
public class AuthController {

    @Autowired
    private IUtilisateurManager utilisateurManager;

    @GetMapping("/login")
    public String showloginPage(Model model, @AuthenticationPrincipal UserDetails loggedUser, RedirectAttributes redirectAttributes) {

        if (loggedUser != null) {
            redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_ERROR, "Vous êtes déjà connecté(e)"));
            return "redirect:/";
        }

        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);

        return "auth/login-page";
    }

    @GetMapping("/logout")
    public String Logout(SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
        sessionStatus.setComplete();
        System.out.println("Vous êtes déconnecté");
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "Vous êtes déconnecté(e)"));

        return "redirect:/";
    }

    @GetMapping("/user-page")
    public String showRedirectPage(Principal principal, @AuthenticationPrincipal UserDetails loggedUser, Model model) {

        String email = principal.getName();

        Utilisateur loggedUtilisateurFoundedByEmail = utilisateurManager.getByEmail(email);

        model.addAttribute("loggedUser", loggedUser);

        System.out.println("Voici les données du profil connecté : " + loggedUtilisateurFoundedByEmail);

        return "auth/user-page";
    }
}
