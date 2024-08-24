package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenerateClickstreamInfoStreamToKafka {
    public static void main(String[] args) throws InterruptedException, IOException {
        try {
            while (true) {
                int recordsCount = RandomDataGenUtility.randomIntBetween(4, 15);
                for (int i = 0; i < recordsCount; i++) {
                    ClickstreamInfo clickstreamInfo = new ClickstreamInfo();

                    // Set fields for ClickstreamInfo
                    clickstreamInfo.setSessionID(RandomDataGenUtility.randomSessionID());
                    clickstreamInfo.setUserID(RandomDataGenUtility.randomUserID());
                    clickstreamInfo.setTimeOfDay(RandomDataGenUtility.randomTimeOfDay());
                    clickstreamInfo.setReferrerURL(RandomDataGenUtility.randomReferrerURL());
                    clickstreamInfo.setDeviceType(RandomDataGenUtility.randomDeviceType());
                    clickstreamInfo.setBrowserType(RandomDataGenUtility.randomBrowserType());
                    clickstreamInfo.setPurchaseIntent(RandomDataGenUtility.randomPurchaseIntent());
                    clickstreamInfo.setDiscountAvailability(RandomDataGenUtility.randomDiscountAvailability());
                    clickstreamInfo.setStockAvailability(RandomDataGenUtility.randomStockAvailability());
                    clickstreamInfo.setPurchaseAmount(RandomDataGenUtility.randomPurchaseAmount(10.0, 500.0)); // Example range
                    clickstreamInfo.setPageDuration(RandomDataGenUtility.randomPageDuration(1.0, 120.0)); // Example range

                    // Set user demographics
                    clickstreamInfo.setUserDemographics(RandomDataGenUtility.randomUserDemographics());

                    // Set geo-location
                    Map<String, String> geoLocation = RandomDataGenUtility.randomGeoLocation(-180.0, 180.0, -90.0, 90.0);
                    clickstreamInfo.setGeoLocation(geoLocation);

                    String key = clickstreamInfo.getSessionID();
                    String value = clickstreamInfo.toString();

                    // Send data to Kafka
                    TestKafkaProducer.sendDataToKafka("bigstream", value, key);

                    System.out.println(value); // Debugging
                }
                Thread.sleep(7000); // Adjust the delay as needed
            }
        } finally {
            TestKafkaProducer.close(); // Ensure resources are closed when done
        }
    }
}
