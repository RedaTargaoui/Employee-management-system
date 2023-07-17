/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 19/05/2023
 */
package Model;

import java.io.*;
import java.util.ArrayList;

public class DepartmentModel implements Serializable {
    // Attributes :
    private int DepartmentNumber;
    private String DepartmentName;

    /**
     * Initialise object
     * @param departmentName department name
     * @param departmentNumber department number
     */
    public DepartmentModel(String departmentName, int departmentNumber) {
        setDepartmentName(departmentName);
        setDepartmentNumber(departmentNumber);
    }

    /**
     * Get department number
     * @return Department number
     */
    public int getDepartmentNumber() {
        return DepartmentNumber;
    }

    /**
     * Set department number
     * @param departmentNumber department number
     */
    public void setDepartmentNumber(int departmentNumber) {
        this.DepartmentNumber = departmentNumber;
    }

    /**
     * Get department name
     * @return Department name
     */
    public String getDepartmentName() {
        return DepartmentName;
    }

    /**
     * Set department name
     * @param departmentName department name
     */
    public void setDepartmentName(String departmentName) {
        this.DepartmentName = departmentName;
    }

}
