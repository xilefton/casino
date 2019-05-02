package ch.bbbaden.casino;

import java.util.ArrayList;

public class AdminUser extends User {

    private ArrayList<User> users;

    public AdminUser() {
        super(false);
    }

    public ArrayList<NormalUser> getNormalUsers() {
        ArrayList<NormalUser> normalUsers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof NormalUser) {
                normalUsers.add((NormalUser) user);
            }
        }
        return normalUsers;
    }

    private void updateUsers() {
        users = new ArrayList<>();
        users.add(new NormalUser());
    }
}
