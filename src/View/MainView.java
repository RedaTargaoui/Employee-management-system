/**
 * Represents the system's main view
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 14/06/2023
 */
package View;

import Model.LoginModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainView extends Application {
    // Attributes :
    private final LoginModel loginModel;

    /**
     * Initialize object
     * @param loginModel loginModel object that contains logged-in user's info
     */
    public MainView(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    /**
     * Create main view
     * @param primaryStage Stage
     */
    @Override
    public void start(Stage primaryStage) {
        // Set title :
        primaryStage.setTitle("Central app");

        // Create tabs :
        TabPane tabPane = new TabPane();

        // Create tabs for each functionality :
        Tab employeeTab = createEmployeeTab();
        Tab departmentTab = createDepartmentTab();
        Tab planningTab = createPlanningTab();
        Tab accessTab = createAccessTab();

        // Disable closable property for all tabs :
        employeeTab.setClosable(false);
        departmentTab.setClosable(false);
        planningTab.setClosable(false);
        accessTab.setClosable(false);

        // Add tabs to the tab pane :
        tabPane.getTabs().addAll(employeeTab, departmentTab, planningTab, accessTab);

        // Create scene :
        Scene scene = new Scene(tabPane, 500, 400);

        // Set the scene on the primary stage :
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create the Employee tab
     * @return Employee tab
     */
    private Tab createEmployeeTab() {
        // Create the Employee tab :
        Tab employeeTab = new Tab("Employee");

        // Set the EmployeeView as the content of the tab :
        employeeTab.setContent(new EmployeeView());

        return employeeTab;
    }

    /**
     * Create the Department tab.
     * @return Department tab
     */
    private Tab createDepartmentTab() {
        // Create the Department tab :
        Tab departmentTab = new Tab("Department");

        // Set the DepartmentView as the content of the tab :
        departmentTab.setContent(new DepartmentView());

        return departmentTab;
    }

    /**
     * Create the Planning tab
     * @return Planning tab
     */
    private Tab createPlanningTab() {
        // Create the Planning tab :
        Tab planningTab = new Tab("Planning");

        // Set the PlanningView as the content of the tab :
        planningTab.setContent(new PlanningView());

        return planningTab;
    }

    /**
     * Create the access tab
     * @return access tab
     */
    private Tab createAccessTab() {
        // Create access tab :
        Tab accessTab = new Tab("Access");

        // If logged-in user's role is admin
        if (this.loginModel.getRole().equalsIgnoreCase("admin")) {
            // Set AccessView as the content of the tab :
            accessTab.setContent(new AccessView());
        }
        // If not admin :
        else {
            Label notAdminLabel = new Label("You can't access this tab. You're not admin.");

            // Set the label as the content of the tab using a StackPane :
            StackPane contentPane = new StackPane();
            contentPane.getChildren().add(notAdminLabel);
            accessTab.setContent(contentPane);
        }

        return accessTab;
    }
}