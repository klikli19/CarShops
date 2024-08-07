package org.klikli.entity;

import org.klikli.constant.Status;

import java.time.LocalDate;

/**
 * Класс Заказ
 *
 * @author Kilikova Anna
 */
public class Order {
    /**
     * Поле автомобиль
     */
    private Car car;
    /**
     * Поле клиент
     */
    private User client;
    /**
     * Поле дата заказа
     */
    private LocalDate date;
    /**
     * Поле статус заказа
     */
    private Status status;


    /**
     * Конструктор для создания в базе магазина заказа на авто
     * @param car авто
     * @param client имя клиента
     * @param date дата заказа
     * @param status статус заказа
     */
    public Order(Car car, User client, LocalDate date, Status status) {
        this.car = car;
        this.client = client;
        this.date = date;
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public User getClient() {
        return client;
    }

    public LocalDate getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "car=" + car +
                ", client=" + client +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
