/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 13/06/2023
 */
package View;

import Controller.LoginController;
import Model.LoginModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView extends Application {
    // Attributes :
    private TextField usernameField;
    private PasswordField passwordField;
    private Stage primaryStage;
    private LoginModel loginModel;

    /**
     * Create view
     * @param primaryStage stage
     */
    @Override
    public void start(Stage primaryStage) {
        this.loginModel = null;
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Login");

        // Set the dimensions of the stage :
        double mainViewWidth = 500;
        double mainViewHeight = 400;

        // Create the username label and field :
        Label usernameLabel = new Label("Username :");
        usernameField = new TextField();

        // Create the password label and field :
        Label passwordLabel = new Label("Password :");
        passwordField = new PasswordField();

        // Create the login button :
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(260);

        // Create a layout grid pane :
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add the labels and fields to the grid pane :
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        // Apply styling to the grid pane and its components :
        gridPane.setStyle("-fx-background-color: #f5f5f5; -fx-alignment: center; -fx-padding: 20;");
        usernameLabel.setStyle("-fx-font-size: 14px;");
        passwordLabel.setStyle("-fx-font-size: 14px;");
        usernameField.setStyle("-fx-pref-height: 30px; -fx-font-size: 14px;");
        passwordField.setStyle("-fx-pref-height: 30px; -fx-font-size: 14px;");

        // Set constraints for the grid pane columns :
        GridPane.setConstraints(usernameLabel, 0, 0);
        GridPane.setConstraints(usernameField, 1, 0);
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordField, 1, 1);

        // Add the login button to the grid pane :
        gridPane.add(loginButton, 0, 2, 2, 1);
        GridPane.setMargin(loginButton, new Insets(10, 0, 0, 0));

        loginButton.setOnAction(e -> handleLogin(gridPane));

        // Create a scene with the grid pane and dimensions :
        Scene scene = new Scene(gridPane, mainViewWidth, mainViewHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handle login inputs
     * @param gridPane GridPane
     */
    private void handleLogin(GridPane gridPane) {
        // Get user's inputs :
        String username = usernameField.getText();
        String password = passwordField.getText();

        LoginController loginController = new LoginController();

        // Check if inputs are correct :
        if ((this.loginModel = loginController.exist(username, password)) != null) {
            // Create an instance of MainView :
            MainView mainView = new MainView(this.loginModel);
            // Call the start method of MainView with the primary stage :
            mainView.start(primaryStage);
        }
        else {
            // Create a label to display the error message :
            Label errorMessageLabel = new Label("Invalid username or password.");
            errorMessageLabel.setStyle("-fx-text-fill: red;");

            // Add the error message label to the grid pane :
            GridPane.setConstraints(errorMessageLabel, 0, 2, 2, 7);
            GridPane.setMargin(errorMessageLabel, new Insets(10, 0, 0, 0));
            gridPane.getChildren().add(errorMessageLabel);
        }
    }

}
