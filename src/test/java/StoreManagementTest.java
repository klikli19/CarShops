import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.klikli.constant.Role;
import org.klikli.constant.Status;
import org.klikli.entity.Car;
import org.klikli.entity.Order;
import org.klikli.entity.User;
import org.klikli.service.StoreManagement;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StoreManagementTest {

    @BeforeEach
    public void setUp() {
        StoreManagement.getUsers().clear();
        StoreManagement.getCars().clear();
        StoreManagement.getOrders().clear();

        StoreManagement.addUser("admin", "111111", Role.ADMIN);
        StoreManagement.addUser("client", "0000000", Role.CLIENT);
        StoreManagement.addCar(new Car("Toyota", "Camry", 2020, 120000000,"New"));}

    @Test
    public void testAddCar() {
        // Создание нового автомобиля
        Car car = new Car("Toyota", "Camry", 2020, 120000000,"New");

        // Добавление автомобиля
        StoreManagement.addCar(car);

        // Проверка, что автомобиль был добавлен в список
        assertEquals(2, StoreManagement.getCars().size());
        assertEquals("Toyota", StoreManagement.getCars().get(0).getBrand());
        assertEquals("Camry", StoreManagement.getCars().get(0).getModel());
        assertEquals(2020, StoreManagement.getCars().get(0).getYear());
        assertEquals(120000000, StoreManagement.getCars().get(0).getPrice());
        assertEquals("New", StoreManagement.getCars().get(0).getCondition());
    }

    @Test
    public void testUpdateCar_ValidId() {
        // Создание и добавление автомобиля
        Car oldCar = new Car("Toyota", "Camry", 2020, 30000, "New");
        StoreManagement.addCar(oldCar);

        // Создание нового автомобиля для обновления
        Car newCar = new Car("Honda", "Accord", 2021, 32000, "New");

        // Обновление автомобиля по ID
        StoreManagement.updateCar(0, newCar);

        // Проверка, что автомобиль был обновлен
        assertEquals(2, StoreManagement.getCars().size());
        assertEquals("Honda", StoreManagement.getCars().get(0).getBrand());
        assertEquals("Accord", StoreManagement.getCars().get(0).getModel());
        assertEquals(2021, StoreManagement.getCars().get(0).getYear());
        assertEquals(32000, StoreManagement.getCars().get(0).getPrice());
        assertEquals("New", StoreManagement.getCars().get(0).getCondition());
    }

    @Test
    public void testUpdateCar_InvalidId() {
        // Создание и добавление автомобиля
        Car oldCar = new Car("Toyota", "Camry", 2020, 30000, "New");
        StoreManagement.addCar(oldCar);

        // Создание нового автомобиля для обновления
        Car newCar = new Car("Honda", "Accord", 2021, 32000, "New");

        // Попытка обновления автомобиля с недопустимым ID
        StoreManagement.updateCar(1, newCar);

        // Проверка, что список автомобилей остался неизменным
        assertEquals(2, StoreManagement.getCars().size());
        assertEquals("Toyota", StoreManagement.getCars().get(0).getBrand());
        assertEquals("Camry", StoreManagement.getCars().get(0).getModel());
    }

    @Test
    public void testDeleteCar_ValidId() {
        // Создание и добавление автомобилей
        Car car1 = new Car("Toyota", "Camry", 2020, 30000, "New");
        Car car2 = new Car("Honda", "Accord", 2021, 32000, "New");
        StoreManagement.addCar(car1);
        StoreManagement.addCar(car2);

        // Удаление автомобиля по ID
        StoreManagement.deleteCar(0);

        // Проверка, что список автомобилей уменьшился на 1
        assertEquals(2, StoreManagement.getCars().size());
        assertEquals("Toyota", StoreManagement.getCars().get(0).getBrand());
        assertEquals("Camry", StoreManagement.getCars().get(0).getModel());
    }

    @Test
    public void testDeleteCar_InvalidId() {
        // Создание и добавление автомобиля
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        StoreManagement.addCar(car);

        // Попытка удаления автомобиля с недопустимым ID
        StoreManagement.deleteCar(1); // ID вне диапазона

        // Проверка, что список автомобилей остался неизменным
        assertEquals(1, StoreManagement.getCars().size());
        assertEquals("Toyota", StoreManagement.getCars().get(0).getBrand());
        assertEquals("Camry", StoreManagement.getCars().get(0).getModel());
    }

    @Test
    public void testAddOrder() {
        // Создание автомобиля
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");

        // Создание пользователя
        User user = new User("Иванов","111111", Role.CLIENT);
        // Создание заказа
        Order order = new Order(car, user, LocalDate.ofYearDay(2024,8), Status.PENDING);

        // Добавление заказа
        StoreManagement.addOrder(order);

        // Проверка, что заказ был добавлен в список
        assertEquals(1, StoreManagement.getOrders().size());
        assertEquals(user, StoreManagement.getOrders().get(0).getClient());
        assertEquals(Status.PENDING, StoreManagement.getOrders().get(0).getStatus());
        assertEquals(car, StoreManagement.getOrders().get(0).getCar());
    }

    @Test
    public void testUpdateOrderStatus() {
        // Создание автомобиля
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");

        // Создание пользователя
        User user = new User("Иванов","111111", Role.CLIENT);

        // Создание заказа
        Order order = new Order(car, user, LocalDate.ofYearDay(2024,8), Status.PENDING);

        // Добавление заказа
        StoreManagement.addOrder(order);

        // Проверка начального статуса
        assertEquals(Status.PENDING, StoreManagement.getOrders().get(0).getStatus());

        // Обновление статуса заказа
        StoreManagement.updateOrderStatus(0, Status.COMPLETED);

        // Проверка обновленного статуса
        assertEquals(Status.COMPLETED, StoreManagement.getOrders().get(0).getStatus());
    }

    @Test
    public void testUpdateOrderStatusInvalidId() {
        // Попытка обновления статуса с несуществующим ID
        StoreManagement.updateOrderStatus(0, Status.CANCELED);

        // Проверяем, что ничего не изменилось (список заказов пуст)
        assertEquals(0, StoreManagement.getOrders().size());
    }

    @Test
    public void testDeleteOrderValidId() {
        // Создание автомобиля
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");

        // Создание пользователя
        User user = new User("Иванов","111111", Role.CLIENT);
        // Создание пользователя 2
        User user2 = new User("Петров","222222", Role.CLIENT);

        // Создание заказа
        Order order1 = new Order(car, user, LocalDate.ofYearDay(2024,8), Status.PENDING);
        Order order2 = new Order(car, user2, LocalDate.ofYearDay(2024,8), Status.COMPLETED);

        // Добавление заказов
        StoreManagement.addOrder(order1);
        StoreManagement.addOrder(order2);

        // Проверка начального количества заказов
        assertEquals(2, StoreManagement.getOrders().size());

        // Удаление первого заказа
        StoreManagement.deleteOrder(0);

        // Проверка количества заказов после удаления
        assertEquals(1, StoreManagement.getOrders().size());

        // Проверка, что остался только второй заказ
        assertEquals(order2, StoreManagement.getOrders().get(0));
    }

    @Test
    public void testDeleteOrderInvalidId() {
        // Создание автомобиля
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");

        // Создание пользователя
        User user = new User("Иванов","111111", Role.CLIENT);

        // Создание заказа
        Order order = new Order(car, user, LocalDate.ofYearDay(2024,8), Status.PENDING);

        // Добавление заказа
        StoreManagement.addOrder(order);

        // Проверка начального количества заказов
        assertEquals(1, StoreManagement.getOrders().size());

        // Попытка удалить заказ с несуществующим ID
        StoreManagement.deleteOrder(1); // Неверный ID
        // Проверка количества заказов после неудачной попытки удаления
        assertEquals(1, StoreManagement.getOrders().size());

        // Проверка, что заказ остался прежним
        assertEquals(order, StoreManagement.getOrders().get(0));
    }

    @Test
    public void testAddUser() {
        // Данные нового пользователя
        String username = "testUser";
        String password = "password123";
        Role role = Role.ADMIN;

        // Добавление пользователя
        StoreManagement.addUser(username, password, role);

        // Проверка, что пользователь был добавлен
        assertNotNull(StoreManagement.getUsers().get(username));

        // Проверка свойств добавленного пользователя
        User addedUser = StoreManagement.getUsers().get(username);
        assertEquals(username, addedUser.getUsername());
        assertEquals(password, addedUser.getPassword());
        assertEquals(role, addedUser.getRole());
    }

    @Test
    public void testAddUserDuplicate() {
        // Данные для первого пользователя
        String username = "duplicateUser";
        String password = "password123";
        Role role = Role.CLIENT;

        // Добавление первого пользователя
        StoreManagement.addUser(username, password, role);

        // Проверка, что пользователь был добавлен
        assertNotNull(StoreManagement.getUsers().get(username));

        // Попытка добавить пользователя с тем же именем
        String newPassword = "newPassword456";
        Role newRole = Role.MANAGER;
        StoreManagement.addUser(username, newPassword, newRole);

        // Проверка, что пользователь по-прежнему существует и его данные не изменились
        User existingUser = StoreManagement.getUsers().get(username);
        assertEquals(password, existingUser.getPassword()); // Пароль не должен измениться
        assertEquals(role, existingUser.getRole()); // Роль не должна измениться
    }

    @Test
    public void testGetUsersAfterAdding() {
        // Добавление пользователей
        StoreManagement.addUser("user1", "password1", Role.ADMIN);
        StoreManagement.addUser("user2", "password2", Role.MANAGER);

        // Получение списка пользователей
        Map<String, User> users = StoreManagement.getUsers();

        // Проверка, что список не пустой и содержит ожидаемое количество пользователей
        assertFalse(users.isEmpty(), "Список пользователей не должен быть пустым");
        assertEquals(1, users.size(), "Список пользователей должен содержать 2 пользователя");

        // Проверка свойств добавленных пользователей
        User user1 = users.get("user1");
        assertNotNull(user1, "Пользователь user1 должен существовать");
        assertEquals("user1", user1.getUsername());
        assertEquals("password1", user1.getPassword());
        assertEquals(Role.ADMIN, user1.getRole());

        User user2 = users.get("user2");
        assertNotNull(user2, "Пользователь user2 должен существовать");
        assertEquals("user2", user2.getUsername());
        assertEquals("password2", user2.getPassword());
        assertEquals(Role.MANAGER, user2.getRole());
    }

    @Test
    public void testGetUsersAfterAddingDuplicate() {
        // Добавление пользователя
        String username = "duplicateUser";
        String password = "password123";
        Role role = Role.CLIENT;

        StoreManagement.addUser(username, password, role);

        // Попытка добавить дубликат
        StoreManagement.addUser(username, "newPassword456", Role.MANAGER);

        // Получение списка пользователей
        Map<String, User> users = StoreManagement.getUsers();

        // Проверка, что пользователь по-прежнему существует и его данные не изменились
        User existingUser = users.get(username);
        assertNotNull(existingUser, "Пользователь duplicateUser должен существовать");
        assertEquals(password, existingUser.getPassword(), "Пароль не должен измениться при добавлении дубликата");
        assertEquals(role, existingUser.getRole(), "Роль не должна измениться при добавлении дубликата");

        // Проверка количества пользователей в списке
        assertEquals(1, users.size(), "Список пользователей должен содержать 1 пользователя");
    }

}
