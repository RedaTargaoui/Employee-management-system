/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 21/05/2023
 */
package Controller;

import Model.EmployeeModel;
import Model.FileReader;

import java.util.ArrayList;

public class EmployeeController {
    // Attributes :
    private final FileReader<EmployeeModel> fileReader;
    private final ArrayList<EmployeeModel> employeesList;// Contains all employees

    /**
     * Initialize object
     */
    public EmployeeController() {
        fileReader = new FileReader<>("./data/Employee.ser");
        this.employeesList = fileReader.pullFromFile();
    }

    /**
     * Get list of all employees
     * @return employees List
     */
    public ArrayList<EmployeeModel> getEmployeesList() {
        return this.employeesList;
    }

    /**
     * Check if an employee exist using its name
     * @param employeeName Employee's name
     * @return Employee's ID if existed, -1 otherwise
     */
    public int exist(String employeeName) {
        // We search for the employee name:
        for (EmployeeModel employeeModel : this.employeesList) {
            // If we find the name :
            if (employeeModel.getEmployeeName().equalsIgnoreCase(employeeName)) {
                // We delete it from the employeesList :
                return employeeModel.getEmployeeID();
            }
        }
        return -1;
    }

    /**
     * Get last added employee's ID(to increment EmployeeID when we add an employee)
     * @return Employee'ID
     */
    public int getLastEmployeeID() {
        int EmployeeID = 0;
        for (EmployeeModel employeeModel : this.employeesList) {
            if (employeeModel.getEmployeeID() > EmployeeID) {
                EmployeeID = employeeModel.getEmployeeID();
            }
        }

        return EmployeeID;
    }

    /**
     * Add an Employee
     * @param employeeName Employee's name
     * @param departmentID Employee's departmentID
     */
    public void addEmployee(String employeeName, int departmentID) {
        // Check if employee doesn't already exist :
        if (this.exist(employeeName) == -1) {
            // add employee to list of all employees :
            this.employeesList.add(new EmployeeModel(this.getLastEmployeeID() + 1, employeeName, departmentID));
        }
    }

    /**
     * Delete a an employee
     * @param employeeID employee's ID
     */
    public void removeEmployee(int employeeID) {
        // We search for the employee to delete using its ID :
        for (int i = 0; i < this.employeesList.size(); i++) {
            // If we find the ID, We delete it from the employeesList :
            if ( this.employeesList.get(i).getEmployeeID() == employeeID ) {
                this.employeesList.remove(i);
            }
        }
    }

    /**
     * Update an employee's info
     * @param employeeID employee's ID
     * @param newName Employee's new name
     * @param newDepartmentID Employee's new departmentID
     */
    public void updateEmployee(int employeeID, String newName, int newDepartmentID) {
        // We search for the employee to update using its ID :
        for (EmployeeModel employeeModel : this.employeesList) {
            // If we find the ID :
            if (employeeModel.getEmployeeID() == employeeID) {
                // We update it :
                employeeModel.setEmployeeName(newName);
                employeeModel.setDepartmentID(newDepartmentID);
            }
        }
    }

    /**
     * Push employees data
     */
    public void pushData() {
        fileReader.pushToFile(this.employeesList);
    }
}
