package ru.gozhan.lab05.util;

import ru.gozhan.lab05.model.Company;
import ru.gozhan.lab05.model.Order;
import ru.gozhan.lab05.model.pack.DocumentPackage;
import ru.gozhan.lab05.model.pack.HugePackage;
import ru.gozhan.lab05.model.pack.Package;
import ru.gozhan.lab05.model.pack.SmallPackage;

import java.util.ArrayList;
import java.util.List;

public class CreateAllObjects {

    public static List<Company> createCompanies() {
        List<Company> companies = new ArrayList<>();

        Company company1 = new Company("Apple", "Адрес Apple");
        Company company2 = new Company("Amazon", "Адрес Amazon");
        Company company3 = new Company("Microsoft", "Адрес Microsoft");
        Company company4 = new Company("Google", "Адрес Google");
        Company company5 = new Company("Facebook", "Адрес Facebook");
        Company company6 = new Company("Samsung", "Адрес Samsung");
        Company company7 = new Company("Tesla", "Адрес Tesla");
        Company company8 = new Company("Netflix", "Адрес Netflix");
        Company company9 = new Company("Uber", "Адрес Uber");
        Company company10 = new Company("Airbnb", "Адрес Airbnb");

        companies.add(company1);
        companies.add(company2);
        companies.add(company3);
        companies.add(company4);
        companies.add(company5);
        companies.add(company6);
        companies.add(company7);
        companies.add(company8);
        companies.add(company9);
        companies.add(company10);

        return companies;
    }

    public static List<Order> createOrders(List<Company> companies, List<Package> packages) {
        List<Order> orders = new ArrayList<>();

        // Реальные города
        String[] cities = {"Москва", "Санкт-Петербург", "Нью-Йорк", "Лондон", "Токио", "Париж", "Берлин", "Рим", "Пекин", "Сидней", "Краснодар"};

        // Создание 10 экземпляров Order
        int citiesLength = cities.length; // Получаем длину массива cities
        for (int i = 0; i < citiesLength - 1; i++) {
            Company company = companies.get(i);
            Package pack = packages.get(i);
            String departureCity = cities[i];
            String deliveryCity = cities[i + 1];

            Order order = new Order(company, pack, departureCity, deliveryCity, 100 * (i + 1));
            orders.add(order);
        }

        return orders;
    }

    public static List<Package> createPackages() {
        List<Package> packages = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Package smallPackage = new SmallPackage("Clock", false);
            packages.add(smallPackage);
        }

        for (int i = 0; i < 10; i++) {
            Package hugePackage = new HugePackage("Car", true, 100);
            packages.add(hugePackage);
        }

        for (int i = 0; i < 10; i++) {
            Package documentPackage = new DocumentPackage("Laptop", false, "Requirements", "From", "To");
            packages.add(documentPackage);
        }

        return packages;
    }

}
