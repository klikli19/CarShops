package org.klikli.entity;

import org.klikli.constant.Role;

/**
 * Класс пользователь
 *
 * @author Kilikova Anna
 */
public class User {
    /**
     * Поле имя пользователя
     */
    private String username;
    /**
     * Поле пароль
     */
    private String password;
    /**
     * Поле права на доступ (роль) в базе данных
     */
    private Role role;

    /**
     * Конструктор для создания в пользователя в базе данных магазина
     * @param username пользователь
     * @param password пароль
     * @param role права доступа
     */
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
