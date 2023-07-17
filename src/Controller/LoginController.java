/**
 * @author Reda TARGAOUI
 * @version 1.0
 * @since 12/06/2022
 */
package Controller;

import Model.FileReader;
import Model.LoginModel;

import java.util.ArrayList;

public class LoginController {
    // Attributes :
    private final FileReader<LoginModel> fileReader;
    private final ArrayList<LoginModel> loginsList;

    /**
     * Initialize object
     */
    public LoginController() {
        fileReader = new FileReader<>("./data/Access.ser");
        this.loginsList = fileReader.pullFromFile();
    }

    /**
     * Get logins list
     * @return loginsList
     */
    public ArrayList<LoginModel> getLoginsList() {
        return loginsList;
    }

    /**
     * Check if an account already exist
     * @param username account username
     * @param password account password
     * @return LoginModel object if existed, null otherwise
     */
    public LoginModel exist(String username, String password) {
        // We go through loginsList and look for username :
        for (LoginModel loginModel : this.loginsList) {
            // If we find username, we check its password :
            if (loginModel.getUsername().equals(username)) {
                if (loginModel.getPassword().equals(password)) {
                    return loginModel;
                }
            }
        }

        return null;
    }

    /**
     * Add user
     * @param username account username
     * @param password account password
     * @return true if we added the login, false otherwise
     */
    public boolean addLogin(String username, String password, String role) {
        // Check if user's info doesn't already exist :
        if (this.exist(username, password) == null) {
            this.loginsList.add(new LoginModel(username, password, role));
            return true;
        }

        return false;
    }

    /**
     * Remove an account
     * @param username account username
     */
    public void removeLogin(String username) {
        // We go through loginsList and look for username :
        for (int i = 0; i < this.loginsList.size(); i++) {
            // If we find username, we delete the account :
            if (this.loginsList.get(i).getUsername().equals(username)) {
                this.loginsList.remove(i);
            }
        }
    }

    /**
     * Push data to file
     */
    public void pushData() {
        fileReader.pushToFile(this.loginsList);
    }
}
