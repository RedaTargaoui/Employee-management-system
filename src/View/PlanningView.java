/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 06/06/2023
 */
package View;

import Controller.EmployeeController;
import Controller.PlanningController;
import Model.PlanningModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

public class PlanningView extends StackPane {
    // Attributes :
    private final TableView<PlanningModel> planningTable;
    private final ObservableList<PlanningModel> tableData;

    /**
     * Initialize and create the window
     */
    public PlanningView() {
        PlanningController planningController = new PlanningController();

        // Create TableView and table columns :
        planningTable = new TableView<>();
        planningTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<PlanningModel, Integer> employeeIdColumn = new TableColumn<>("EmployeeID");
        TableColumn<PlanningModel, String> dayNameColumn = new TableColumn<>("Day name");
        TableColumn<PlanningModel, String> startHourColumn = new TableColumn<>("Start hour");
        TableColumn<PlanningModel, String> endHourColumn = new TableColumn<>("End hour");

        // Set cell value factories for the table columns:
        employeeIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getEmployeeID()).asObject());
        dayNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDayName()));
        startHourColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStartHour().toString()));
        endHourColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEndHour().toString()));

        // Add table columns to the TableView :
        planningTable.getColumns().addAll(employeeIdColumn, dayNameColumn, startHourColumn, endHourColumn);

        // Create panel for input and search button :
        FlowPane inputPanel = new FlowPane(10, 10);
        inputPanel.setPadding(new Insets(10));
        inputPanel.getChildren().add(new Label("Employee Name:"));

        TextField employeeInput = new TextField();
        inputPanel.getChildren().add(employeeInput);

        // To get employee :
        EmployeeController employeeController = new EmployeeController();
        final int[] employeeID = {-1};

        // Create search button :
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> {
            // If Input is not empty :
            if (!employeeInput.getText().isEmpty()) {
                // Check if employee exists :
                if (employeeController.exist(employeeInput.getText()) != -1) {
                    employeeID[0] = employeeController.exist(employeeInput.getText());
                    refreshPlanningTable(planningController, employeeID[0]);
                }
                // If given employee doesn't exist :
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Search Employee");
                    alert.setHeaderText(null);
                    //alert.setGraphic(null);
                    alert.setContentText("The entered name doesn't exist!!");
                    alert.showAndWait();
                }
            }
            // If input is empty :
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Search Employee");
                alert.setHeaderText(null);
                alert.setContentText("Please enter an employee name.");
                alert.showAndWait();
            }
        });
        inputPanel.getChildren().add(searchButton);

        // Create panel for buttons :
        FlowPane buttonsPanel = new FlowPane(10, 10);
        buttonsPanel.setPadding(new Insets(10));

        // Create buttons and listeners :
        Button addButton = new Button("Add Planning");
        addButton.setOnAction(event -> {
            try {
                // Use ChoiceBox to select day name :
                ChoiceBox<String> dayNameSelection = new ChoiceBox<>(FXCollections.observableArrayList(
                        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
                );

                Dialog<Pair<String, LocalTime[]>> dialog = new Dialog<>();
                dialog.setTitle("Add Planning");
                dialog.setHeaderText(null);

                ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

                // Add day name :
                FlowPane content = new FlowPane(10, 10);
                content.setPadding(new Insets(10));
                content.getChildren().addAll(new Label("Day Name:"), dayNameSelection);

                // add start hour and end hour :
                TextField startHourInput = new TextField();
                TextField endHourInput = new TextField();
                content.getChildren().addAll(new Label("Start Hour:"), startHourInput, new Label("End Hour:"), endHourInput);

                dialog.getDialogPane().setContent(content);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == addButtonType) {
                        try {
                            String dayName = dayNameSelection.getValue();
                            LocalTime startHour = LocalTime.parse(startHourInput.getText());
                            LocalTime endHour = LocalTime.parse(endHourInput.getText());

                            return new Pair<>(dayName, new LocalTime[]{startHour, endHour});
                        } catch (DateTimeParseException exception) {
                            // If there's any problem with hour format :
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Add Planning");
                            alert.setHeaderText(null);
                            alert.setContentText("The entered time's format is incorrect!!");
                            alert.showAndWait();
                        }
                    }
                    return null;
                });

                Optional<Pair<String, LocalTime[]>> result = dialog.showAndWait();

                // Check if day name is already exist :
                result.ifPresent(pair -> {
                    String dayName = pair.getKey();
                    LocalTime[] hours = pair.getValue();

                    // If day is not empty
                    if (dayName != null && !dayName.isEmpty()) {
                        planningController.addPlanning(employeeID[0], dayName, hours[0], hours[1]);
                    }
                });

                refreshPlanningTable(planningController, employeeID[0]);
            } catch (DateTimeParseException exception) {
                // If there's any problem with hour format :
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Add Planning");
                alert.setHeaderText(null);
                alert.setContentText("The entered time's format is incorrect!!");
                alert.showAndWait();
            }
        });
        buttonsPanel.getChildren().add(addButton);

        Button removeButton = new Button("Remove All Planning");
        removeButton.setOnAction(event -> {
            // Remove all plannings for this EmployeeID :
            planningController.removePlanning(employeeID[0]);
            refreshPlanningTable(planningController, employeeID[0]);
        });
        buttonsPanel.getChildren().add(removeButton);

        Button updateButton = new Button("Update Planning");
        updateButton.setOnAction(event -> {
            PlanningModel selectedPlanning = planningTable.getSelectionModel().getSelectedItem();

            if (selectedPlanning != null) {
                // Create a dialog for the update input:
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Update Planning");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                dialog.setContentText("Enter new start hour (HH:mm):");

                // Get the new start hour input:
                Optional<String> startHourResult = dialog.showAndWait();
                if (startHourResult.isPresent()) {
                    // Parse the start hour:
                    String startHourInput = startHourResult.get();
                    try {
                        LocalTime newStartHour = LocalTime.parse(startHourInput);

                        // Get the new end hour input:
                        dialog.getEditor().setText("");
                        dialog.setContentText("Enter new end hour (HH:mm):");
                        Optional<String> endHourResult = dialog.showAndWait();
                        if (endHourResult.isPresent()) {
                            // Parse the end hour:
                            String endHourInput = endHourResult.get();
                            try {
                                LocalTime newEndHour = LocalTime.parse(endHourInput);

                                planningController.updatePlanning(employeeID[0], selectedPlanning.getDayName(), newStartHour, newEndHour);

                                refreshPlanningTable(planningController, employeeID[0]);
                            } catch (DateTimeParseException exception) {
                                // If there's any problem with the end hour format:
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Update Planning");
                                alert.setHeaderText(null);
                                alert.setContentText("The entered end hour's format is incorrect!!");
                                alert.showAndWait();
                            }
                        }
                    } catch (DateTimeParseException exception) {
                        // If there's any problem with the start hour format:
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Update Planning");
                        alert.setHeaderText(null);
                        alert.setContentText("The entered start hour's format is incorrect!!");
                        alert.showAndWait();
                    }
                }
            }
        });

        buttonsPanel.getChildren().add(updateButton);

        // Create a BorderPane and set the inputPanel and planningTable as its children :
        BorderPane root = new BorderPane();
        root.setTop(inputPanel);
        root.setCenter(planningTable);
        root.setBottom(buttonsPanel);

        // Initialize the table data and refresh the table :
        tableData = FXCollections.observableArrayList();
        planningTable.setItems(tableData);

        refreshPlanningTable(planningController, employeeID[0]);

        // Set the root as the content of the pane :
        getChildren().add(root);
        setMargin(root, new Insets(10));
    }

    /**
     * Update the TableView
     * @param planningController PlanningController object
     * @param employeeID employee's ID
     */
    public void refreshPlanningTable(PlanningController planningController, int employeeID) {
        // Get all plannings list :
        ArrayList<PlanningModel> planningList = planningController.getEmployeePlanning(employeeID);
        tableData.clear();
        tableData.addAll(planningList);
        planningController.pushData();
    }
}
