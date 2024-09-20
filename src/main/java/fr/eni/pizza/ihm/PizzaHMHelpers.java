package fr.eni.pizza.ihm;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class PizzaHMHelpers {

    public static void sendCommonFlashMessage(RedirectAttributes redirectAttributes, int type, String message) {
        redirectAttributes.addFlashAttribute("flashMessage",
                new PizzaFlashMessage(type, message));
    }

        public static void sendSuccessFlashMessage(RedirectAttributes redirectAttributes, String message) {
            sendCommonFlashMessage(redirectAttributes, PizzaFlashMessage.TYPE_FLASH_SUCCES, message);

        }
    }
