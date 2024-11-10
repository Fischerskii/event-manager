package ru.trofimov.eventmanager.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.trofimov.eventmanager.enums.Role;

@Component
@ConfigurationProperties(prefix = "default")
public class DefaultUserParameters {

    private final User user = new User();
    private final User admin = new User();

    public User getUser() {
        return user;
    }

    public User getAdmin() {
        return admin;
    }

    public static class User {
        private String login;
        private String password;
        private int age;
        private Role role;

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public int getAge() {
            return age;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }
    }
}
