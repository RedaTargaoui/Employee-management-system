/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 11/06/2023
 */
package Model;

import java.io.Serializable;

public class LoginModel implements Serializable {
    // Attributes :
    private final String username;
    private final String password;
    private final String role;

    /**
     * Initialize object
     * @param username account username
     * @param password account password
     * @param role account role
     */
    public LoginModel(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Get username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get account role
     * @return role
     */
    public String getRole() {
        return role;
    }
}
