package org.klikli.constant;

/**
 * Enum Роль (право доступа)
 * Используется для установки права доступа пользователю в БД
 *
 * @author Kilikova Anna
 */
public enum Role {
    ADMIN("Администратор"),
    MANAGER("Менеджер"),
    CLIENT("Клиент");

    /**
     * Поле описание роли
     */
    private final String description;

    /**
     * Конструктор для описания роли пользователя
     * @param description роль (право доступа)
     */
    Role(String description) {
        this.description = description;
    }

    /**
     * Получение описания роли
     * @return роль
     */
    public String getDescription() {
        return description;
    }
}
