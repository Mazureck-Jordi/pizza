package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.ICommandeManager;
import fr.eni.pizza.bll.IUtilisateurManager;
import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.bo.Role;
import fr.eni.pizza.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UtilisateurController {

    @Autowired
    private IUtilisateurManager utilisateurManager;

    @Autowired
    private ICommandeManager commandeManager;

    @GetMapping("/list-utilisateur")
    public String showListUtilisateur(Model model) {

        List<Utilisateur> utilisateurs = utilisateurManager.getAllUtilisateurs();
        model.addAttribute("utilisateurs", utilisateurs);

        return "list/list-utilisateur";
    }

    @GetMapping("/details-utilisateur/{id}")
    public String showDetailUtilisateur(@PathVariable Long id, Model model) {

        Utilisateur utilisateur = utilisateurManager.getUtilisateurById(id);
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
        }
        model.addAttribute("utilisateur", utilisateur);

        List<Role> Roles = utilisateurManager.getAllRoles();

        model.addAttribute("Roles", Roles);

        List<Commande> commandes = commandeManager.getAllCommandes();

        model.addAttribute("commandes", commandes);

        return "form/utilisateur-form";
    }

    @PostMapping("/utilisateur-form")

    public String utilisateurForm(@ModelAttribute (name = "utilisateur") Utilisateur utilisateur) {

        if (utilisateur.getId_utilisateur() == null) {
            utilisateurManager.addUtilisateur(utilisateur);
        }
        if (utilisateur.getId_utilisateur() != null) {
            utilisateurManager.updateUtilisateur(utilisateur);
        }
        return "redirect:/list-utilisateur";
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

}

