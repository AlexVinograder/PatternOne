package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {

    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        var city = new String[]{"Уфа", "Новосибирск", "Москва", "Рязань", "Краснодар", "Красноярск", "Барнаул", "Горно-Алтайск", "Казань", "Орёл"};

        return city[new Random().nextInt(city.length)];
    }

    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));

        return faker.name().fullName();
    }

    public static String generatePhone(String locale) {
        var faker = new Faker(new Locale(locale));

        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {

            return new UserInfo(generateCity(), generateName(locale), generatePhone(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }
}