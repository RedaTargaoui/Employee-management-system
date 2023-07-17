/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 02/06/2023
 */
package View;

import Controller.DepartmentController;
import Controller.EmployeeController;
import Model.EmployeeModel;

import javafx.beans.property.SimpleIntegerProperty;
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

public class EmployeeView extends StackPane {
    // Attributes :
    private final TableView<EmployeeModel> employeeTable;
    private final ObservableList<EmployeeModel> tableData;

    /**
     * Create view
     */
    public EmployeeView() {
        EmployeeController employeeController = new EmployeeController();
        DepartmentController departmentController = new DepartmentController();

        // Create TableView and table columns :
        employeeTable = new TableView<>();
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<EmployeeModel, Integer> idColumn = new TableColumn<>("EmployeeID");
        TableColumn<EmployeeModel, String> nameColumn = new TableColumn<>("Employee Name");
        TableColumn<EmployeeModel, String> departmentColumn = new TableColumn<>("Department");

        // Set cell value factories for the table columns :
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getEmployeeID()).asObject());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmployeeName()));
        departmentColumn.setCellValueFactory(data ->
                new SimpleStringProperty(departmentController.getDepartmentName(data.getValue().getDepartmentID()))
        );

        // Add table columns to the TableView :
        employeeTable.getColumns().addAll(idColumn, nameColumn, departmentColumn);

        // Create Add Employee button and its action :
        Button addButton = new Button("Add Employee");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog employeeNameDialog = new TextInputDialog();
                employeeNameDialog.setTitle("Add Employee");
                employeeNameDialog.setHeaderText(null);
                employeeNameDialog.setGraphic(null);
                employeeNameDialog.setContentText("Enter employee name:");
                employeeNameDialog.showAndWait().ifPresent(employeeName -> {
                    TextInputDialog departmentNameDialog = new TextInputDialog();
                    departmentNameDialog.setTitle("Add Employee");
                    departmentNameDialog.setHeaderText(null);
                    departmentNameDialog.setGraphic(null);
                    departmentNameDialog.setContentText("Enter department name:");
                    departmentNameDialog.showAndWait().ifPresent(departmentName -> {
                        // Check if department doesn't exist :
                        if (departmentController.exist(departmentName) != -1) {
                            employeeController.addEmployee(employeeName, departmentController.exist(departmentName));
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Add Employee");
                            alert.setHeaderText(null);
                            alert.setContentText("Entered department doesn't exist!!");
                            alert.showAndWait();
                        }
                    });
                });

                refreshEmployeeTable(employeeController);
            }
        });

        // Create Remove button and its action :
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmployeeModel selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

                if (selectedEmployee != null) {
                    employeeController.removeEmployee(selectedEmployee.getEmployeeID());
                    refreshEmployeeTable(employeeController);
                }
            }
        });

        // Create Update button and its action :
        Button updateButton = new Button("Update");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmployeeModel selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

                if (selectedEmployee != null) {
                    TextInputDialog newNameDialog = new TextInputDialog();
                    newNameDialog.setTitle("Update Employee");
                    newNameDialog.setHeaderText(null);
                    newNameDialog.setGraphic(null);
                    newNameDialog.setContentText("Enter new name:");
                    newNameDialog.showAndWait().ifPresent(newName -> {
                        TextInputDialog newDepartmentDialog = new TextInputDialog();
                        newDepartmentDialog.setTitle("Update Employee");
                        newDepartmentDialog.setHeaderText(null);
                        newDepartmentDialog.setGraphic(null);
                        newDepartmentDialog.setContentText("Enter new department :");
                        newDepartmentDialog.showAndWait().ifPresent(newDepartment -> {
                            // if department exist :
                            if (departmentController.exist(newDepartment) != -1) {
                                employeeController.updateEmployee(selectedEmployee.getEmployeeID(), newName, departmentController.exist(newDepartment));
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Update Employee");
                                alert.setHeaderText(null);
                                alert.setContentText("Entered department doesn't exist, please create it first in the department tab!!");
                                alert.showAndWait();
                            }
                        });
                    });

                    refreshEmployeeTable(employeeController);
                }
            }
        });

        // Create a FlowPane for the buttons :
        FlowPane buttonsPane = new FlowPane(10, 10);
        buttonsPane.setPadding(new Insets(10));
        buttonsPane.getChildren().addAll(addButton, removeButton, updateButton);

        // Create a BorderPane and set the TableView and buttonsPane :
        BorderPane root = new BorderPane();
        root.setCenter(employeeTable);
        root.setBottom(buttonsPane);

        // Initialize the table data and refresh the table :
        tableData = FXCollections.observableArrayList();
        employeeTable.setItems(tableData);

        refreshEmployeeTable(employeeController);

        // Set the root as the content of the pane :
        getChildren().add(root);
        setMargin(root, new Insets(10));
    }

    /**
     * Refresh table
     * @param employeeController EmployeeController object
     */
    private void refreshEmployeeTable(EmployeeController employeeController) {
        tableData.clear();
        tableData.addAll(employeeController.getEmployeesList());
        employeeController.pushData();
    }
}
