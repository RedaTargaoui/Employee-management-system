/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 19/05/2023
 */
package Model;

import java.io.*;

public class EmployeeModel implements Serializable {
    // Attributes :
    private int EmployeeID;
    private String EmployeeName;
    private int DepartmentID;

    /**
     * Initialise object
     * @param name Employee's name
     * @param DepartmentID Employee's DepartmentID
     */
    public EmployeeModel(int EmployeeID, String name, int DepartmentID) {
        setEmployeeID(EmployeeID);
        setEmployeeName(name);
        setDepartmentID(DepartmentID);
    }

    /**
     * Get Employee's ID
     * @return Employee's ID
     */
    public int getEmployeeID() {
        return EmployeeID;
    }

    /**
     * Set the Employee's ID
     * @param employeeID Employee's ID
     */
    public void setEmployeeID(int employeeID) {
        this.EmployeeID = employeeID;
    }

    /**
     * Get Employee's name
     * @return Employee's name
     */
    public String getEmployeeName() {
        return EmployeeName;
    }

    /**
     * Set Employee's name
     * @param employeeName Employee's name
     */
    public void setEmployeeName(String employeeName) {
        this.EmployeeName = employeeName;
    }

    /**
     * Get Employee's DepartmentID
     * @return Employee's DepartmentID
     */
    public int getDepartmentID() {
        return DepartmentID;
    }

    /**
     * Set Employee's DepartmentID
     * @param DepartmentID Employee's DepartmentID
     */
    public void setDepartmentID(int DepartmentID) {
        this.DepartmentID = DepartmentID;
    }

}
