package org.klikli.service;

import org.klikli.constant.Role;
import org.klikli.constant.Status;
import org.klikli.entity.Car;
import org.klikli.entity.Order;
import org.klikli.entity.User;

import java.time.LocalDate;
import java.util.*;

/**
 * Класс для входа в консольное меню и работой с магазином и его управлением
 *
 * @author Kilikova Anna
 */
public class StoreManagement {
    /**
     * Поле Список пользователь в БД магазина
     */
    private static final Map<String, User> users = new HashMap<>();
    /**
     * Поле список авто в магазине
     */
    private static final List<Car> cars = new ArrayList<>();
    /**
     * Поле список заказов
     */
    private static final List<Order> orders = new ArrayList<>();
    /**
     * Поле пользователь
     */
    private static User currentUser;
    /**
     * Поле роль (право доступа) пользователя в БД
     */
    private static Role role;


    /**
     * Метод получения всех пользователей из БД
     * @return пользователи
     */
    public static Map<String, User> getUsers() {
        return users;
    }

    /**
     * Метод добавления пользователя с установлением роли
     * @param username имя пользователя
     * @param password пароль
     * @param role право доступа
     */
    public static void addUser(String username, String password, Role role) {
        users.put(username, new User(username, password, role));
    }

    // Управление автомобилями

    /**
     * Метод получения всех авто
     * @return список авто из БД
     */
    public static List<Car> getCars() {
        return cars;
    }

    /**
     * Метод добавления авто в БД
     * @param car авто
     * */
    public static void addCar(Car car) {
        cars.add(car);
    }

    /**
     * Метод изменения инфо об авто
     * @param id идентификатор
     * @param newCar новые данные авто
     */

    public static void updateCar(int id, Car newCar) {
        if (id >= 0 && id < cars.size()) {
            cars.set(id, newCar);
        }
    }

    /**
     * Метод удаления авто
     * @param id идентификатор авто
     */
    public static void deleteCar(int id) {
        if (id >= 0 && id < cars.size()) {
            cars.remove(id);
        }
    }

    // Управление заказами

    /**
     * Метод получения из БД всех заказов
     * @return список всех заказов
     */
    public static List<Order> getOrders() {
        return orders;
    }

    /**
     * Метод добавления заказа в БД магазина
     * @param order заказ
     */
    public static void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Метод изменения сведений о заказе
     * @param id идентификатор заказа
     * @param newStatus измененный заказ
     */
    public static void updateOrderStatus(int id, Status newStatus) {
        if (id >= 0 && id < orders.size()) {
            orders.get(id).setStatus(newStatus);
        }
    }

    /**
     * Метод удаления заказа
     * @param id идентификатор заказа
     */
    public static void deleteOrder(int id) {
        if (id >= 0 && id < orders.size()) {
            orders.remove(id);
        }
    }

    public static void main(String[] args) {
        // Установочные (тестовые) данные
        addUser("admin", "123456", Role.ADMIN);
        addUser("manager", "112233", Role.MANAGER);
        addUser("client", "001122", Role.CLIENT);

        // Запуск меню приложения
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Добро пожаловать в систему магазина. Войти.");
            System.out.println("2. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login(scanner);
                    if (currentUser != null) {
                        menu(scanner);
                    }
                    break;
                case 2:
                    System.out.println("До скорой встречи");
                    return;
                default:
                    System.out.println("Вы что-то ввели неправильно. Повторите попытку");
            }
        }
    }

    // Вход в систему
    private static void login(Scanner scanner) {
        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            System.out.println("Вход выполнен как " + currentUser.getUsername() + " (" + currentUser.getRole()
                    + ") выполнен");
        } else {
            System.out.println("Неверные данные. Повторите попытку");
        }
    }

    // Меню управления
    private static void menu(Scanner scanner) {
        while (true) {
            if (currentUser.getRole() == Role.ADMIN) {
                adminMenu(scanner);
            } else if (currentUser.getRole() == Role.MANAGER) {
                managerMenu(scanner);
            } else {
                clientMenu(scanner);
            }
        }
    }

    // Меню администратора
    private static void adminMenu(Scanner scanner) {
        System.out.println("1. Авто");
        System.out.println("2. Пользователи");
        System.out.println("3. Заказы");
        System.out.println("4. Вернуться назад");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                carManagement(scanner);
                break;
            case 2:
                userManagement(scanner);
                break;
            case 3:
                orderManagement(scanner);
                break;
            case 4:
                currentUser = null;
                return;
            default:
                System.out.println("Проверьте правильность выбора");
        }
    }

    private static void managerMenu(Scanner scanner) {
        System.out.println("1. Авто");
        System.out.println("2. Заказы");
        System.out.println("3. Вернуться назад");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                carManagement(scanner);
                break;
            case 2:
                orderManagement(scanner);
                break;
            case 3:
                currentUser = null;
                return;
            default:
                System.out.println("Проверьте правильность выбора");
        }
    }

    private static void clientMenu(Scanner scanner) {
        System.out.println("1. Авто");
        System.out.println("2. Оформить заказ");
        System.out.println("3. Вернуться назад");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewCars();
                break;
            case 2:
                createOrder(scanner);
                break;
            case 3:
                currentUser = null;
                return;
            default:
                System.out.println("Проверьте правильность выбора");
        }
    }

    // Управление авто
    private static void carManagement(Scanner scanner) {
        System.out.println("1. Добавить авто");
        System.out.println("2. Просмотреть авто");
        System.out.println("3. Редактировать авто");
        System.out.println("4. Удалить авто");
        System.out.println("5. Вернуться назад");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addCar(scanner);
                break;
            case 2:
                viewCars();
                break;
            case 3:
                editCar(scanner);
                break;
            case 4:
                deleteCar(scanner);
                break;
            case 5:
                return;
            default:
                System.out.println("Проверьте правильность выбора");
        }
    }

    // Добавить авто в БД
    private static void addCar(Scanner scanner) {
        System.out.println("Введите марку авто:");
        String brand = scanner.nextLine();
        System.out.println("Введите модель авто:");
        String model = scanner.nextLine();
        System.out.println("Введите год выпуска:");
        int year = scanner.nextInt();
        System.out.println("Введите цену:");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Введите состояние (new/used):");
        String condition = scanner.nextLine();

        Car car = new Car(brand, model, year, price, condition);
        addCar(car);
        System.out.println("Авто успешно добавлено.");
    }

    private static void viewCars() {
        List<Car> cars = getCars();
        if (cars.isEmpty()) {
            System.out.println("В БД отсутствуеют авто.");
        } else {
            for (int i = 0; i < cars.size(); i++) {
                System.out.println(i + ": " + cars.get(i));
            }
        }
    }

    // Изменение данных авто
    private static void editCar(Scanner scanner) {
        viewCars();
        System.out.println("Введите id автомобиля для изменения данных:");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id >= 0 && id < getCars().size()) {
            System.out.println("Введите марку автомобиля:");
            String brand = scanner.nextLine();
            System.out.println("Введите модель автомобиля:");
            String model = scanner.nextLine();
            System.out.println("Введите год выпуска:");
            int year = scanner.nextInt();
            System.out.println("Введите цену:");
            double price = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Введите состояние (new/used):");
            String condition = scanner.nextLine();

            Car newCar = new Car(brand, model, year, price, condition);
            updateCar(id, newCar);
            System.out.println("Авто обновлено успешно.");
        } else {
            System.out.println("Id не найден.");
        }
    }

    // Удаление авто
    private static void deleteCar(Scanner scanner) {
        viewCars();
        System.out.println("Введите id автомобиля для удаления:");
        int id = scanner.nextInt();
        if (id >= 0 && id < getCars().size()) {
            deleteCar(id);
            System.out.println("Автомобиль удален успешно.");
        } else {
            System.out.println("Некорректный id.");
        }
    }

    // Управление пользователями
    private static void userManagement(Scanner scanner) {
        System.out.println("1. Просмотреть пользователей");
        System.out.println("2. Добавить пользователя");
        System.out.println("3. Удалить пользователя");
        System.out.println("4. Вернуться назад");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewUsers();
                break;
            case 2:
                addUser(scanner);
                break;
            case 3:
                deleteUser(scanner);
                break;
            case 4:
                return;
            default:
                System.out.println("Проверьте правильность выбора");
        }
    }

    // Просмотр пользователей
    private static void viewUsers() {
        Map<String, User> users = getUsers();
        if (users.isEmpty()) {
            System.out.println("Пользователи не найдены.");
        } else {
            for (Map.Entry<String, User> entry : users.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    // Добавление пользователя
    private static void addUser(Scanner scanner) {
        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        System.out.println("Введите роль/право доступа (ADMIN/MANAGER/CLIENT):");
        String roleString = scanner.nextLine();
        Role role = Role.valueOf(roleString.toUpperCase());

        addUser(username, password, role);
        System.out.println("Пользователь добавлен успешно.");
    }

    // Удаление пользователя
    private static void deleteUser(Scanner scanner) {
        System.out.println("Введите имя пользователя для удаления:");
        String username = scanner.nextLine();
        if (getUsers().remove(username) != null) {
            System.out.println("Пользователь удален успещно.");
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    // Управление заказами
    private static void orderManagement(Scanner scanner) {
        System.out.println("1. Создать заказ");
        System.out.println("2. Просмотреть заказы");
        System.out.println("3. Изменить статус заказа");
        System.out.println("4. Удалить заказ");
        System.out.println("5. Вернуться назад");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                createOrder(scanner);
                break;
            case 2:
                viewOrders();
                break;
            case 3:
                updateOrderStatus(scanner);
                break;
            case 4:
                deleteOrder(scanner);
                break;
            case 5:
                return;
            default:
                System.out.println("Проверьте правильность выбора");
        }
    }

    // Создание заказа
    private static void createOrder(Scanner scanner) {
        viewCars();
        System.out.println("Введите id автомобиля для заказа:");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id >= 0 && id < getCars().size()) {
            System.out.println("Введите имя клиента:");
            String username = scanner.nextLine();
            User client = getUsers().get(username);

            if (client != null && client.getRole() == Role.CLIENT) {
                Car car = getCars().get(id);
                Order order = new Order(car, client, LocalDate.now(), Status.PENDING);
                addOrder(order);
                System.out.println("Заказ создан успешно.");
            } else {
                System.out.println("Клиент  с таким имененм не найден или не имеет права доступа.");
            }
        } else {
            System.out.println("Некорректный id.");
        }
    }

    // Управление заказами
    private static void viewOrders() {
        List<Order> orders = getOrders();
        if (orders.isEmpty()) {
            System.out.println("Заказы не найдены.");
        } else {
            for (int i = 0; i < orders.size(); i++) {
                System.out.println(i + ": " + orders.get(i));
            }
        }
    }

    // Изменение статуса заказа
    private static void updateOrderStatus(Scanner scanner) {
        viewOrders();
        System.out.println("Введите id заказа для изменения статуса:");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id >= 0 && id < getOrders().size()) {
            System.out.println("Введите статус (PENDING/COMPLETED/CANCELED):");
            String statusString = scanner.nextLine();
            Status status = Status.valueOf(statusString.toUpperCase());

            updateOrderStatus(id, status);
            System.out.println("Статус заказа обновлен успешно.");
        } else {
            System.out.println("Некорректный id.");
        }
    }

    // Удаление заказа
    private static void deleteOrder(Scanner scanner) {
        viewOrders();
        System.out.println("Введите id заказа для удаления:");
        int id = scanner.nextInt();
        if (id >= 0 && id < getOrders().size()) {
            deleteOrder(id);
            System.out.println("Заказ удален успешно.");
        } else {
            System.out.println("Некорректный id.");
        }
    }
}
