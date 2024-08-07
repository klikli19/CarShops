package org.klikli.constant;

/**
 * Enum Статус
 * Используется для заказов в магазине
 *
 * @author Kilikova Anna
 */
public enum Status {
    PENDING("В ожидании"),
    COMPLETED("Завершен"),
    CANCELED("Отменен");
    /**
     * Поле описание статуса заказа
     */
    private final String description;

    /**
     * Конструктор для добавления описания к статусу
     * @param description описание
     */
    Status(String description) {
        this.description = description;
    }

    /**
     * Получение описания статуса заказа
     * @return описание
     */
    public String getDescription() {
        return description;
    }

}
