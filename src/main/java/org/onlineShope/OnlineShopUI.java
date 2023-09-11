package org.onlineShope;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OnlineShopUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int productId = 0;
        while (true) {      // Запрашиваем ID товара до тех пор, пока не получим корректное значение
            System.out.print("Введите ID: ");
            if (scanner.hasNextInt()) {
                productId = scanner.nextInt();
                break;      // Выходим из цикла, если введено целое число
            } else {
                System.out.println("Некорректный ввод. Пожалуйста, введите целое число для ID.");
                scanner.next();
            }
        }

        int quantity = 0;
        while (true) {      // Запрашиваем количество товаров до тех пор, пока не получим корректное значение
            System.out.print("Укажите кол-во: ");
            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                break;      // Выходим из цикла, если введено целое число
            } else {
                System.out.println("Некорректный ввод. Пожалуйста, введите целое число для количества.");
                scanner.next();
            }
        }

        try {
            double totalPrice = ShopManager.purchaseProduct(productId, quantity);
            System.out.println("К оплате: " + totalPrice + " Руб.");
        } catch (ProductNotFoundException e) {
            System.out.println("Продукт не найден!");
        } catch (InsufficientQuantityException e) {
            System.out.println("На складе нет такого кол-ва товаров");
        }
    }
}

class Product {
    private int id;     // Добавлено поле id
    private String name;
    private int availableQuantity;
    private double price;

    public Product(int id, String name, int availableQuantity, double price) {
        this.id = id;
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public double getPrice() {
        return price;
    }
}

class ProductDatabase {
    private static List<Product> products = new ArrayList<>();
    static {
        // Заполенная база данных различными товарами
        products.add(new Product(1, "Яблоки (за кг)", 10, 139.99));
        products.add(new Product(2, "Груши (за кг)", 15, 179.99));
        products.add(new Product(3, "Малина Fresh", 25, 189.99));
        products.add(new Product(4, "Сыр 'Ламбер'", 8, 249.99));
        products.add(new Product(5, "Хлеб", 50, 51.99));
    }

    public static Product getProduct(int productId) {
        for (Product product : products) {      // Ищем товар по ID в базе данных
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;    // Если товар не найден
    }
}

class ShopManager {
    public static double purchaseProduct(int productId, int quantity)
            throws ProductNotFoundException, InsufficientQuantityException {
        Product product = ProductDatabase.getProduct(productId);
        if (product == null) {
            throw new ProductNotFoundException("Продукт не найден!");
        }
        if (product.getAvailableQuantity() < quantity) {
            throw new InsufficientQuantityException("На складе нет такого кол-ва товаров.");
        }

        double totalPrice = product.getPrice() * quantity;
        return totalPrice;
    }
}

class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}

class InsufficientQuantityException extends Exception {
    public InsufficientQuantityException(String message) {
        super(message);
    }
}