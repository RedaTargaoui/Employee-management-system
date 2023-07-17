/**
 * Launch program
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 19/05/2023
 */
package Main;

import View.LoginView;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        // Launch view :
        Application.launch(LoginView.class, args);
    }
}