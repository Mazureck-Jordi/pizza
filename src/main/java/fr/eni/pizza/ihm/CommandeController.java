package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.*;
import fr.eni.pizza.bll.impl.DetailCommandeManager;
import fr.eni.pizza.bo.*;
import fr.eni.pizza.dao.impl.DAODetailCommandeMySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SessionAttributes({"loggedUser"})
@Controller
public class CommandeController {

    @Autowired
    private ICommandeManager commandeManager;

    @Autowired
    private IUtilisateurManager utilisateurManager;

    @Autowired
    private IClientManager clientManager;

    @Autowired
    private IProduitManager produitManager;
    @Autowired
    private DetailCommandeManager detailCommandeManager;

    @GetMapping("/list-commandes")
    public String ShowListCommandes(Model model) {
        List<Commande> commandes = commandeManager.getAllCommandes();

        model.addAttribute("commandes", commandes);


        return "list/list-commandes";
    }

    @GetMapping("/list-commande-by-etat")
    public String ShowListCommandesCondition(@AuthenticationPrincipal UserDetails loggedUser, Model model) {

        boolean isLivreur = false;
        boolean isGerant = false;
        boolean isPazzaiolo = false;
        Utilisateur user = utilisateurManager.getByEmail(loggedUser.getUsername());
        Utilisateur userWithRoles = utilisateurManager.getUtilisateurById(user.getId_utilisateur());
        for (Role role : userWithRoles.getRoles()) {
            if (role.getId_role() == 1) {
                isPazzaiolo = true;
            }
            if (role.getId_role() == 2) {
                isGerant = true;
            }
            if (role.getId_role() == 3) {
                isLivreur = true;
            }
        }

        if (isGerant) {
            List<Commande> commandes = commandeManager.getAllCommandes();
            List<Commande> commandesTriees = commandes.stream()
                    .sorted(Comparator.comparing(Commande::getDate_heure_livraison))
                    .toList();
            for (Commande commande : commandesTriees) {
                commande.setDate_heure_livraison(commande.getDate_heure_livraison());
            }
            model.addAttribute("commandes", commandesTriees);

        } else if (!isGerant && isPazzaiolo) {

            List<Commande> commandes = commandeManager.getCommandesByIdEtat(1L);
            commandes.addAll(commandeManager.getCommandesByIdEtat(2L));
            commandes.addAll(commandeManager.getCommandesByIdEtat(3L));


            List<Commande> commandesTriees = commandes.stream()
                    .sorted(Comparator.comparing(Commande::getDate_heure_livraison))
                    .toList();
            for (Commande commande : commandesTriees) {
                commande.setDate_heure_livraison(commande.getDate_heure_livraison());
            }
            model.addAttribute("commandes", commandesTriees);

        } else if (!isGerant && isLivreur) {

            List<Commande> commandes = commandeManager.getCommandesByIdEtat(4L);
            commandes.addAll(commandeManager.getCommandesByIdEtat(5L));
            commandes.addAll(commandeManager.getCommandesByIdEtat(6L));

            List<Commande> commandesTriees = commandes.stream()
                    .sorted(Comparator.comparing(Commande::getDate_heure_livraison))
                    .toList();
            for (Commande commande : commandesTriees) {
                commande.setDate_heure_livraison(commande.getDate_heure_livraison());
            }
            model.addAttribute("commandes", commandesTriees);
        }
        return "list/list-commande-by-etat";
    }

    @GetMapping("/details-commande/{id}")
    public String ShowDetailCommande(@PathVariable Long id, Model model) {

        Commande commande = commandeManager.getCommandeById(id);
        if (commande == null) {
            return "redirect:/";
        }
        model.addAttribute("commande", commande);


        return "details/details-commande";
    }

    @GetMapping({"/show-commande-form/{id}", "/show-commande-form"})
    public String ShowFormCommande(@AuthenticationPrincipal UserDetails loggedUser, @PathVariable(required = false) Long id, Model model) {

        Utilisateur user = utilisateurManager.getByEmail(loggedUser.getUsername());
       Utilisateur userWithRoles = utilisateurManager.getUtilisateurById(user.getId_utilisateur());
        boolean isLivreur = false;
        boolean isGerant = false;
        for (Role role : userWithRoles.getRoles()) {
            if (role.getId_role() == 3) {
                isLivreur = true;
            }
            if (role.getId_role() == 2) {
                isGerant = true;
            }
        }
        if (isLivreur && !isGerant) {
            return "redirect:/";
        }

        Commande commande = new Commande();
        if (id != null) {
            commande = commandeManager.getCommandeById(id);
        }

        model.addAttribute("commande", commande);

        List<Etat> etats = commandeManager.getAllEtats();

        model.addAttribute("etats", etats);

        List<Utilisateur> utilisateurs = utilisateurManager.getAll();

        model.addAttribute("utilisateurs", utilisateurs);

        List<Role> roles = utilisateurManager.getAllRoles();

        model.addAttribute("roles", roles);

        List<Client> clients = clientManager.getAllClients();

        model.addAttribute("clients", clients);

        List<Produit> Produits = produitManager.getAllProduits();

        model.addAttribute("Produits", Produits);

        List<TypeProduit> TypeProduits = produitManager.getAllTypeProduits();

        return "form/commande-form";
    }

    @PostMapping("/commande-form")
    public String commandeForm(@ModelAttribute Commande commande) {

        commande.setDate_heure_livraison(LocalDateTime.now());
        commande.setLivraison(0);
        commande.setPrix_total(0);
        commande.setEst_paye(0);
        commande.setId_etat(commandeManager.getEtatById(1L));

        if (commande.getId_commande() != null) {
            commandeManager.updateCommande(commande);
        }
        if (commande.getId_commande() == null) {
            commandeManager.addCommande(commande);
        }
        return "redirect:/show-creation-commande/" + commande.getId_commande();
    }

    @PostMapping("/date-form/{idCommande}")
    public String dateForm(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setDate_heure_livraison(commande.getDate_heure_livraison());
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "La date de livraison de la commande a été enregistrée avec succès"));
        return "redirect:/show-creation-commande/" + commandeToUpdate.getId_commande();
    }

    @PostMapping("/livraison-form/{idCommande}")
    public String livraisonForm(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setLivraison(commande.getLivraison());
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "Le type de commande a été enregistrée avec succès"));
        return "redirect:/show-creation-commande/" + commandeToUpdate.getId_commande();
    }

    @PostMapping("/en-cours-creation/{idCommande}")
    public String enCoursCreationForm(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setId_etat(commandeManager.getEtatById(1L));
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "La commande est en cours de création"));
        return "redirect:/details-detail-commande/" + commandeToUpdate.getId_commande();
    }

    @PostMapping("/enregistrer/{idCommande}")
    public String enregistrerForm(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setId_etat(commandeManager.getEtatById(2L));
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "La commande est créée"));
        return "redirect:/details-detail-commande/" + commandeToUpdate.getId_commande();
    }

    @PostMapping("/en-preparation-form/{idCommande}")
    public String enPreparationForm(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setId_etat(commandeManager.getEtatById(3L));
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "La commande est en cours de préparation"));
        return "redirect:/detail-commande-pizzaiolo/" + commandeToUpdate.getId_commande();
    }

    @PostMapping("/fin de-preparation-form/{idCommande}")
    public String finDePrerationForm(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setId_etat(commandeManager.getEtatById(4L));
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "La commande est prépararée"));
        return "redirect:/detail-commande-pizzaiolo/" + commandeToUpdate.getId_commande();
    }

    @PostMapping("/en-livraison-form/{idCommande}")
    public String enLivraison(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setId_etat(commandeManager.getEtatById(5L));
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "La commande est en cours de livraison"));
        return "redirect:/detail-commande-pizzaiolo/" + commandeToUpdate.getId_commande();
    }

    @PostMapping("/fin de-livraison-form/{idCommande}")
    public String finDeLivraison(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setId_etat(commandeManager.getEtatById(6L));
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "La commande est livrée"));
        return "redirect:/detail-commande-pizzaiolo/" + commandeToUpdate.getId_commande();
    }

    @PostMapping("/non-paye/{idCommande}")
    public String nonPaye(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setEst_paye(0);
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "La commande est non payée"));
        return "redirect:/detail-commande-pizzaiolo/" + commandeToUpdate.getId_commande();
    }

    @PostMapping("/est-paye/{idCommande}")
    public String estPaye(@PathVariable Long idCommande, @ModelAttribute Commande commande, RedirectAttributes redirectAttributes) {
        Commande commandeToUpdate = commandeManager.getCommandeById(idCommande);
        commandeToUpdate.setEst_paye(1);
        commandeManager.updateCommande(commandeToUpdate);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "La commande est payée"));
        return "redirect:/detail-commande-pizzaiolo/" + commandeToUpdate.getId_commande();
    }

    @GetMapping("/delete-commande/{id}")
    public String deleteCommande(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (commandeManager.getCommandeById(id) == null) {
            System.out.println("Erreur");
        }
        if (commandeManager.getCommandeById(id) != null) {
            commandeManager.deleteCommande(commandeManager.getCommandeById(id));
        }
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_WARNING, "La commande a été supprimée avec succès"));
        return "redirect:/list-commandes";
    }

}
