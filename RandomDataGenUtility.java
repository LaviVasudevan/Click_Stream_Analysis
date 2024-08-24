package org.example;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomDataGenUtility {

    // Method to generate random time in a day
    public static String randomTimeOfDay() {
        Random random = new Random();
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        return String.format("%02d:%02d", hour, minute);
    }

    // Method to generate random User ID
    public static String randomUserID() {
        Random random = new Random();
        return "user" + (random.nextInt(10000) + 1);
    }

    // Method to generate random Session ID
    public static String randomSessionID() {
        Random random = new Random();
        return "sess" + (random.nextInt(10000) + 1);
    }

    // Method to generate random Geo-Location
    public static Map<String, String> randomGeoLocation(double minLon, double maxLon, double minLat, double maxLat) {
        BigDecimal db = new BigDecimal(Math.random() * (maxLon - minLon) + minLon);
        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString(); // 6 digits after the decimal
        db = new BigDecimal(Math.random() * (maxLat - minLat) + minLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        Map<String, String> map = new HashMap<>();
        map.put("Lon", lon);
        map.put("Lat", lat);
        return map;
    }

    // Method to generate random Referrer URL
    public static String randomReferrerURL() {
        String[] referrers = {"https://example.com", "https://anotherexample.com", "https://yetanotherexample.com"};
        Random random = new Random();
        return referrers[random.nextInt(referrers.length)];
    }

    // Method to generate random Device Type
    public static String randomDeviceType() {
        String[] devices = {"desktop", "mobile", "tablet"};
        Random random = new Random();
        return devices[random.nextInt(devices.length)];
    }

    // Method to generate random Browser Type
    public static String randomBrowserType() {
        String[] browsers = {"Chrome", "Firefox", "Safari", "Edge", "Opera"};
        Random random = new Random();
        return browsers[random.nextInt(browsers.length)];
    }

    // Method to generate random Purchase Intent
    public static boolean randomPurchaseIntent() {
        return new Random().nextBoolean();
    }

    // Method to generate random Discount Availability
    public static boolean randomDiscountAvailability() {
        return new Random().nextBoolean();
    }

    // Method to generate random Stock Availability
    public static boolean randomStockAvailability() {
        return new Random().nextBoolean();
    }

    // Method to generate random Purchase Amount
    public static double randomPurchaseAmount(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    // Method to generate random Page Duration
    public static double randomPageDuration(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    // Method to generate random User Demographics
    public static String[] randomUserDemographics() {
        String[] ages = {"18-24", "25-34", "35-44", "45-54", "55-64", "65+"};
        String[] genders = {"Male", "Female", "Other"};
        Random random = new Random();
        String age = ages[random.nextInt(ages.length)];
        String gender = genders[random.nextInt(genders.length)];
        return new String[]{age, gender};
    }

    // Method to generate random integer between two values
    public static int randomIntBetween(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    // Method to randomly select an element from an array
    public static String randomElement(String[] elements) {
        Random random = new Random();
        return elements[random.nextInt(elements.length)];
    }

    // Method to generate random phone number (assuming a simple format)
    public static String randomPhoneno() {
        Random random = new Random();
        return String.format("%010d", random.nextInt(1000000000));
    }
}
