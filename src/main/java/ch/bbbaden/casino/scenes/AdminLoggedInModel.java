package ch.bbbaden.casino.scenes;

import ch.bbbaden.casino.AdminUser;
import ch.bbbaden.casino.Model;

public class AdminLoggedInModel extends Model {
    private AdminUser adminUser;

    public AdminLoggedInModel(AdminUser adminUser) {
        super("/fxml/AdminLoggedIn.fxml", "Admin Logged In", true);
        this.adminUser = adminUser;
    }
}
