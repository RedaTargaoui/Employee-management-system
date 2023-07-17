/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 16/06/2023
 */
package View;

import Controller.LoginController;
import Model.LoginModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class AccessView extends StackPane {
    // Attributes :
    private final TableView<LoginModel> usersTable;
    private final ObservableList<LoginModel> tableData;

    public AccessView() {
        LoginController loginController = new LoginController();

        // Create TableView and table columns :
        usersTable = new TableView<>();
        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<LoginModel, String> usernameColumn = new TableColumn<>("Account username");
        TableColumn<LoginModel, String> passwordColumn = new TableColumn<>("Account password");
        TableColumn<LoginModel, String> roleColumn = new TableColumn<>("Account role");

        // Set cell value factories for the table columns :
        usernameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        passwordColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPassword()));
        roleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole()));

        // Add table columns to the TableView :
        usersTable.getColumns().addAll(usernameColumn, passwordColumn, roleColumn);

        // Create Add user button and its action:
        Button addButton = new Button("Add User");
        addButton.setOnAction(event -> {
            TextInputDialog usernameDialog = new TextInputDialog();
            usernameDialog.setTitle("Add User");
            usernameDialog.setHeaderText(null);
            usernameDialog.setGraphic(null);
            usernameDialog.setContentText("Enter username:");
            usernameDialog.showAndWait().ifPresent(username -> {
                // Check if username is not empty or doesn't already exist :
                if (!username.isEmpty()) {
                    // Show password dialog :
                    TextInputDialog passwordDialog = new TextInputDialog();
                    passwordDialog.setTitle("Add User");
                    passwordDialog.setHeaderText(null);
                    passwordDialog.setGraphic(null);
                    passwordDialog.setContentText("Enter password:");
                    passwordDialog.showAndWait().ifPresent(password -> {
                        // Show role dialog :
                        ChoiceDialog<String> roleDialog = new ChoiceDialog<>("Admin", "User");
                        roleDialog.setTitle("Add User");
                        roleDialog.setHeaderText(null);
                        roleDialog.setGraphic(null);
                        roleDialog.setContentText("Select role:");
                        roleDialog.showAndWait().ifPresent(role -> {
                            // add the user details :
                            loginController.addLogin(username, password, role);
                        });
                    });
                }
                else {
                    // Show error message :
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add User");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or username already exists!");
                    alert.showAndWait();
                }
            });
            refreshEmployeeTable(loginController);
        });

        // Create Remove button and its action :
        Button removeButton = new Button("Remove user");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginModel selectedItem = usersTable.getSelectionModel().getSelectedItem();

                if (selectedItem != null) {
                    loginController.removeLogin(selectedItem.getUsername());
                    refreshEmployeeTable(loginController);
                }
            }
        });

        // Create a FlowPane for the buttons :
        FlowPane buttonsPane = new FlowPane(10, 10);
        buttonsPane.setPadding(new Insets(10));
        buttonsPane.getChildren().addAll(addButton, removeButton);

        // Create a BorderPane and set the TableView and buttonsPane :
        BorderPane root = new BorderPane();
        root.setCenter(usersTable);
        root.setBottom(buttonsPane);

        // Initialize the table data and refresh the table :
        tableData = FXCollections.observableArrayList();
        usersTable.setItems(tableData);

        refreshEmployeeTable(loginController);

        // Set the root as the content of the pane :
        getChildren().add(root);
        setMargin(root, new Insets(10));

    }

    /**
     * Refresh table
     * @param loginController LoginController object
     */
    private void refreshEmployeeTable(LoginController loginController) {
        tableData.clear();
        tableData.addAll(loginController.getLoginsList());
        loginController.pushData();
    }
}




