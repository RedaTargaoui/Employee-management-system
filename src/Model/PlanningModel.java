/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 19/05/2023
 */
package Model;

import java.io.*;
import java.time.LocalTime;

public class PlanningModel implements Serializable {
    // Attributes :
    private int EmployeeID;
    private String DayName;
    private LocalTime StartHour;
    private LocalTime EndHour;

    /**
     * Initialize object
     * @param EmployeeID employee ID
     * @param dayName day name
     * @param startHour work start hour
     * @param endHour work end hour
     */
    public PlanningModel(int EmployeeID, String dayName, LocalTime startHour, LocalTime endHour) {
        setEmployeeID(EmployeeID);
        setDayName(dayName);
        setStartHour(startHour);
        setEndHour(endHour);
    }

    /**
     * Get employee ID
     * @return EmployeeID
     */
    public int getEmployeeID() {
        return EmployeeID;
    }

    /**
     * Set employeeID
     * @param employeeID employee ID
     */
    public void setEmployeeID(int employeeID) {
        this.EmployeeID = employeeID;
    }

    /**
     * Get day name
     * @return day name
     */
    public String getDayName() {
        return DayName;
    }

    /**
     * Set day name
     * @param dayName day name
     */
    public void setDayName(String dayName) {
        this.DayName = dayName;
    }

    /**
     * Get start hour
     * @return start hour
     */
    public LocalTime getStartHour() {
        return StartHour;
    }

    /**
     * Set start hour
     * @param startHour start hour
     */
    public void setStartHour(LocalTime startHour) {
        this.StartHour = startHour;
    }

    /**
     * Get end hour
     * @return end hour
     */
    public LocalTime getEndHour() {
        return EndHour;
    }

    /**
     * Set end hour
     * @param endHour end hour
     */
    public void setEndHour(LocalTime endHour) {
        this.EndHour = endHour;
    }

}
