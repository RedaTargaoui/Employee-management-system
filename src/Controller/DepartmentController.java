/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 22/05/2023
 */
package Controller;

import Model.DepartmentModel;
import Model.FileReader;

import java.util.ArrayList;

public class DepartmentController {
    // Attributes :
    private final FileReader<DepartmentModel> fileReader;
    private final ArrayList<DepartmentModel> departmentList;

    /**
     * Initialize object
     */
    public DepartmentController() {
        fileReader = new FileReader<>("./data/Department.ser");
        this.departmentList = fileReader.pullFromFile();
    }

    /**
     * Get departments list
     * @return departments list
     */
    public ArrayList<DepartmentModel> getDepartmentList() {
        return this.departmentList;
    }

    /**
     * Find if department exist using its name
     * @param departmentName department name
     * @return Department number if existed, -1 otherwise
     */
    public int exist(String departmentName) {
        // We look the department number using its name :
        for (DepartmentModel departmentModel : this.departmentList) {
            if (departmentModel.getDepartmentName().equalsIgnoreCase(departmentName)) {
                return departmentModel.getDepartmentNumber();
            }
        }

        return -1;
    }

    /**
     * Get last added department's number
     * @return last added Department number
     */
    public int getLastDepartmentNumber() {
        int number = 0;
        for (DepartmentModel departmentModel : this.departmentList) {
            if (departmentModel.getDepartmentNumber() > number) {
                number = departmentModel.getDepartmentNumber();
            }
        }

        return number;
    }

    /**
     * Add department
     * @param departmentName department name
     */
    public void addDepartment(String departmentName) {
        // Check if department doesn't already exist :
        if (this.exist(departmentName) == -1) {
            this.departmentList.add(new DepartmentModel(departmentName, this.getLastDepartmentNumber() + 1));
        }
    }

    /**
     * Remove a department
     * @param departmentNumber department number
     */
    public void removeDepartment(int departmentNumber) {
        // We search for the department to delete using its Number :
        for (int i = 0; i < this.departmentList.size(); i++) {
            // If we find the Number :
            if (this.departmentList.get(i).getDepartmentNumber() == departmentNumber) {
                // We delete it from the departmentList :
                this.departmentList.remove(i);
            }
        }
    }

    /**
     * Update a department
     * @param departmentNumber department number
     * @param newDepartmentName department new name
     */
    public void updateDepartment(int departmentNumber, String newDepartmentName) {
        // We search for the department to update using its departmentNumber :
        for (DepartmentModel departmentModel : this.departmentList) {
            // If we find the departmentNumber :
            if (departmentModel.getDepartmentNumber() == departmentNumber) {
                // We update it :
                departmentModel.setDepartmentName(newDepartmentName);
            }
        }
    }

    public String getDepartmentName(int departmentID) {
        // We look for the department using its ID :
        for (DepartmentModel departmentModel : this.departmentList) {
            // If we found it, we return its name :
            if (departmentModel.getDepartmentNumber() == departmentID) {
                return departmentModel.getDepartmentName();
            }
        }

        return null;
    }

    /**
     * Push departments data
     */
    public void pushData() {
        fileReader.pushToFile(this.departmentList);
    }
}
