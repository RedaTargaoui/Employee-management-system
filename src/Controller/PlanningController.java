/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 22/05/2023
 */
package Controller;

import Model.FileReader;
import Model.PlanningModel;
import java.time.LocalTime;
import java.util.ArrayList;

public class PlanningController {
    // Attributes :
    private final FileReader<PlanningModel> fileReader;
    private final ArrayList<PlanningModel> planningsList;

    /**
     * Initialize object
     */
    public PlanningController() {
        fileReader = new FileReader<>("./data/Planning.ser");
        this.planningsList = fileReader.pullFromFile();
    }

    /**
     * Get an employee's planning
     * @param EmployeeID employee ID
     * @return employee's planning
     */
    public ArrayList<PlanningModel> getEmployeePlanning(int EmployeeID) {
        ArrayList<PlanningModel> planning = new ArrayList<>();

        // Look for the employeeID :
        for (PlanningModel planningModel : this.planningsList) {
            // If we find it, we add to planning list :
            if (planningModel.getEmployeeID() == EmployeeID) {
                planning.add(planningModel);
            }
        }

        return planning;
    }

    /**
     * Check if an employee's day planning already exist or not
     * @param employeeID employee's ID
     * @param DayName day name
     * @return true if existed, false otherwise
     */
    public boolean exist(int employeeID, String DayName) {
        // get this employee's planning :
        ArrayList<PlanningModel> planning = this.getEmployeePlanning(employeeID);

        // Check if day name already exist :
        for (PlanningModel planningModel : planning) {
            if (planningModel.getDayName().equalsIgnoreCase(DayName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add a day planning for an employee
     * @param EmployeeID : employee's ID
     * @param DayName : the day name
     * @param StartHour : work start hour
     * @param EndHour : work end hour
     */
    public void addPlanning(int EmployeeID, String DayName, LocalTime StartHour, LocalTime EndHour) {
        // Check if day name doesn't already exist :
        if (!this.exist(EmployeeID, DayName)) {
            this.planningsList.add(new PlanningModel(EmployeeID, DayName, StartHour, EndHour));
        }
    }

    /**
     * Remove all planning for a specific employee
     * @param EmployeeID employee's ID
     */
    public void removePlanning(int EmployeeID) {
        // Create a temporary ArrayList to store objects that we want to delete :
        ArrayList<PlanningModel> ToDelete = new ArrayList<>();

        // Find the object that have the EmployeeID :
        for (PlanningModel planningModel : this.planningsList) {
            // If we find the EmployeeID, we add it to ToDelete array :
            if (planningModel.getEmployeeID() == EmployeeID) {
                ToDelete.add(planningModel);
            }
        }

        // Remove all ToDelete objects in planningsList :
        planningsList.removeAll(ToDelete);
    }

    /**
     * Update a day's planning
     * @param EmployeeID : employee's ID
     * @param DayName : day name
     * @param newStartHour : new work start hour
     * @param newEndHour : new work end hour
     */
    public void updatePlanning(int EmployeeID, String DayName, LocalTime newStartHour, LocalTime newEndHour) {
        // We search for the planning to update using EmployeeID :
        for (PlanningModel planningModel : this.planningsList) {
            // If we find the day and EmployeeID:
            if (planningModel.getEmployeeID() == EmployeeID && planningModel.getDayName().equalsIgnoreCase(DayName)) {
                // We update it :
                planningModel.setStartHour(newStartHour);
                planningModel.setEndHour(newEndHour);
            }
        }
    }

    /**
     * Push plannings data
     */
    public void pushData() {
        fileReader.pushToFile(this.planningsList);
    }
}
