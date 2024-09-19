package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.ICommandeManager;
import fr.eni.pizza.bll.IUtilisateurManager;
import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.bo.Role;
import fr.eni.pizza.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes({"loggedUser"})
@Controller
public class UtilisateurController {

    @Autowired
    private IUtilisateurManager utilisateurManager;

    @Autowired
    private ICommandeManager commandeManager;

    @GetMapping("/list-utilisateur")
    public String showListUtilisateur(Model model) {

        List<Utilisateur> utilisateurs = utilisateurManager.getAll();
        model.addAttribute("utilisateurs", utilisateurs);

        return "list/list-utilisateur";
    }

    @GetMapping("/details-utilisateur/{id}")
    public String showDetailUtilisateur(@PathVariable Long id, Model model) {

        Utilisateur utilisateur = utilisateurManager.getById(id);
        if (utilisateur == null) {
            return "redirect:/";
        }
        model.addAttribute("utilisateur", utilisateur);

        return "details/details-utilisateur";
    }
    @GetMapping({"/show-utilisateur-form/{id}", "/show-utilisateur-form"})

    public String showFormUtilisateur(@PathVariable (required = false) Long id, Model model) {

        Utilisateur utilisateur = new Utilisateur();
        if (id != null) {
            utilisateur = utilisateurManager.getUtilisateurById(id);
            utilisateur.setMot_de_passe(null);
        }
        model.addAttribute("utilisateur", utilisateur);

        List<Role> roles = utilisateurManager.getAllRoles();

        model.addAttribute("roles", roles);

        List<Commande> commandes = commandeManager.getAllCommandes();

        model.addAttribute("commandes", commandes);

        return "form/utilisateur-form";
    }

    @PostMapping("/utilisateur-form")
    public String utilisateurForm(@Valid @ModelAttribute ("utilisateur") Utilisateur utilisateur, BindingResult bindingResult, Model model) {


        List<Role> roles = utilisateurManager.getAllRoles();

        model.addAttribute("roles", roles);


        if(bindingResult.hasErrors()) {
            return "form/utilisateur-form";
        }

        if (utilisateur.getId_utilisateur() == null) {
            utilisateurManager.addUtilisateur(utilisateur);
            return "redirect:/list-utilisateur";
        }
        if (utilisateur.getId_utilisateur() != null) {
            utilisateurManager.updateUtilisateur(utilisateur);
        }
            return "redirect:/details-utilisateur/" + utilisateur.getId_utilisateur();

    }

    @GetMapping("/delete-utilisateur/{id}")
    public String deleteUtilisateur(@PathVariable Long id) {

        if (utilisateurManager.getUtilisateurById(id) == null) {
            System.out.println("erreur");
        }
        if (utilisateurManager.getUtilisateurById(id) == null) {
            utilisateurManager.deleteUtilisateur(utilisateurManager.getUtilisateurById(id));
        }
        return "redirect:/list-utilisateur";
    }

    @GetMapping("/delete-role-utilisateur/{idUtilisateur}/{idRole}")
    public String deleteRoleUtilisateur(@PathVariable Long idUtilisateur , @PathVariable Long idRole) {
        utilisateurManager.deteteRoleUtilisateur(idUtilisateur, idRole);
        return "redirect:/details-utilisateur/" + idUtilisateur;
    }


    @GetMapping("/profil-utilisateur")
    public String showProfilUtilisateur(@AuthenticationPrincipal UserDetails loggedUser, Model model) {

        Utilisateur utilisateur = utilisateurManager.getById(utilisateurManager.getByEmail(loggedUser.getUsername()).getId_utilisateur());
        if (utilisateur == null) {
            return "redirect:/";
        }
        model.addAttribute("utilisateur", utilisateur);

        return "profil/profil-utilisateur";
    }

    @GetMapping("/show-simple-utilisateur-form")

    public String showFormUtilisateurSimple(@AuthenticationPrincipal UserDetails loggedUser, Model model) {

        Utilisateur utilisateur = utilisateurManager.getUtilisateurById(utilisateurManager.getByEmail(loggedUser.getUsername()).getId_utilisateur());
        utilisateur.setMot_de_passe(null);
        model.addAttribute("utilisateur", utilisateur);

        List<Commande> commandes = commandeManager.getAllCommandes();

        model.addAttribute("commandes", commandes);

        return "form/simple-utilisateur-form";
    }

    @PostMapping("/simple-utilisateur-form")
    public String utilisateurFormSimple(@ModelAttribute (name = "utilisateur") Utilisateur utilisateur) {

            utilisateurManager.updateUtilisateur(utilisateur);

        return "redirect:/logout";

    }

}

