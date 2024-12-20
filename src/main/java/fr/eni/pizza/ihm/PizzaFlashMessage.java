package fr.eni.pizza.ihm;

public class PizzaFlashMessage {

    public static final int TYPE_FLASH_SUCCES = 0;
    public static final int TYPE_FLASH_ERROR = 1;
public static final int TYPE_FLASH_WARNING = 2;

    public int type;
    public String message;

    public PizzaFlashMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getTypeCssClass(){
        if (type == TYPE_FLASH_SUCCES) {
            return "uk-alert-success";
        }
        if(type==TYPE_FLASH_ERROR){
            return "uk-alert-danger";
        }
        if(type == TYPE_FLASH_WARNING){
            return "uk-alert-warning";
        }
        return "uk-alert-primary";
    }

}
