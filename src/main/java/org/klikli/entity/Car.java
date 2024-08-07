package org.klikli.entity;


/**
 * Класс Автомобиль
 *
 * @author @Kilikova Anna
 */

public class Car {
    /**
     * Поле для название марки
     */
    private String brand;
    /**
     * Поле модели авто
     */
    private String model;
    /**
     * Поле год выпуска
     */
    private int year;
    /**
     * Поле цены авто
     */
    private double price;
    /**
     * Поле состояния авто. Может быть новое или б/у
     */
    private String condition;

    /**
     * Конструктор для создания авто в базе данных магазина
     * @param brand марка
     * @param model модель
     * @param year год
     * @param price цена
     * @param condition состояние
     */
    public Car(String brand, String model, int year, double price, String condition) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", condition='" + condition + '\'' +
                '}';
    }
}
