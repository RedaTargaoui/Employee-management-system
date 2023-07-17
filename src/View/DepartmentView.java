/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 03/06/2023
 */
package View;

import Controller.DepartmentController;
import Model.DepartmentModel;
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

public class DepartmentView extends StackPane {
    // Attributes :
    private final TableView<DepartmentModel> departmentTable;
    private final ObservableList<DepartmentModel> tableData;

    /**
     * Create view
     */
    public DepartmentView() {
        DepartmentController departmentController = new DepartmentController();

        // Create TableView and table columns :
        departmentTable = new TableView<>();
        departmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<DepartmentModel, Integer> idColumn = new TableColumn<>("Department number");
        TableColumn<DepartmentModel, String> nameColumn = new TableColumn<>("Department name");

        // Set cell value factories for the table columns :
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDepartmentNumber()).asObject());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartmentName()));

        // Add table columns to the TableView :
        departmentTable.getColumns().addAll(idColumn, nameColumn);

        // Create Add department button and its action :
        Button addButton = new Button("Add department");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog departmentNameDialog = new TextInputDialog();
                departmentNameDialog.setTitle("Add Department");
                departmentNameDialog.setHeaderText(null);
                departmentNameDialog.setGraphic(null);
                departmentNameDialog.setContentText("Enter department name:");
                departmentNameDialog.showAndWait().ifPresent(departmentName -> {
                    // Check if department doesn't exist :
                    if (departmentController.exist(departmentName) == -1) {
                        departmentController.addDepartment(departmentName);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Add Department");
                        alert.setHeaderText(null);
                        alert.setContentText("Entered department already exists!!");
                        alert.showAndWait();
                    }
                });

                refreshDepartmentTable(departmentController);
            }
        });

        // Create Remove button and its action :
        Button removeButton = new Button("Remove department");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DepartmentModel selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();

                if (selectedDepartment != null) {
                    departmentController.removeDepartment(selectedDepartment.getDepartmentNumber());
                    refreshDepartmentTable(departmentController);
                }
            }
        });

        // Create Update button and its action :
        Button updateButton = new Button("Edit");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DepartmentModel selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();

                if (selectedDepartment != null) {
                    TextInputDialog newDepartmentNameDialog = new TextInputDialog();
                    newDepartmentNameDialog.setTitle("Update Department");
                    newDepartmentNameDialog.setHeaderText(null);
                    newDepartmentNameDialog.setGraphic(null);
                    newDepartmentNameDialog.setContentText("Enter new department name:");
                    newDepartmentNameDialog.showAndWait().ifPresent(newDepartmentName -> {
                        // Check if department name doesn't exist :
                        if (departmentController.exist(newDepartmentName) == -1) {
                            departmentController.updateDepartment(selectedDepartment.getDepartmentNumber(), newDepartmentName);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Update Department");
                            alert.setHeaderText(null);
                            alert.setContentText("Entered department name already exists!!");
                            alert.showAndWait();
                        }
                    });

                    refreshDepartmentTable(departmentController);
                }
            }
        });

        // Create a FlowPane for the buttons :
        FlowPane buttonsPane = new FlowPane(10, 10);
        buttonsPane.setPadding(new Insets(10));
        buttonsPane.getChildren().addAll(addButton, removeButton, updateButton);

        // Create a BorderPane and set the TableView and buttonsPane :
        BorderPane root = new BorderPane();
        root.setCenter(departmentTable);
        root.setBottom(buttonsPane);

        // Initialize the table data and refresh the table :
        tableData = FXCollections.observableArrayList();
        departmentTable.setItems(tableData);

        refreshDepartmentTable(departmentController);

        // Set the root as the content of the pane :
        getChildren().add(root);
        setMargin(root, new Insets(10));
    }

    /**
     * Refresh table
     * @param departmentController DepartmentController object
     */
    private void refreshDepartmentTable(DepartmentController departmentController) {
        tableData.clear();
        tableData.addAll(departmentController.getDepartmentList());
        departmentController.pushData();
    }
}
