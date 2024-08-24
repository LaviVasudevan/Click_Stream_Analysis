package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateClickstreamInfo {
    public static void main(String[] args) throws InterruptedException, IOException {
        File file = new File("clickstream-info1.json");
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        while (true) {
            int recordsCount = RandomDataGenUtility.randomIntBetween(5, 15);  // Generating random number of records
            for (int i = 0; i < recordsCount; i++) {
                ClickstreamInfo clickstreamInfo = new ClickstreamInfo();

                clickstreamInfo.setUserID(RandomDataGenUtility.randomUserID());
                clickstreamInfo.setSessionID(RandomDataGenUtility.randomSessionID());
                clickstreamInfo.setTimeOfDay(RandomDataGenUtility.randomTimeOfDay());
                clickstreamInfo.setReferrerURL(RandomDataGenUtility.randomReferrerURL());
                clickstreamInfo.setDeviceType(RandomDataGenUtility.randomDeviceType());
                clickstreamInfo.setBrowserType(RandomDataGenUtility.randomBrowserType());
                clickstreamInfo.setPurchaseIntent(RandomDataGenUtility.randomPurchaseIntent());
                clickstreamInfo.setDiscountAvailability(RandomDataGenUtility.randomDiscountAvailability());
                clickstreamInfo.setStockAvailability(RandomDataGenUtility.randomStockAvailability());
                clickstreamInfo.setPurchaseAmount(RandomDataGenUtility.randomPurchaseAmount(10.0, 500.0));
                clickstreamInfo.setPageDuration(RandomDataGenUtility.randomPageDuration(1.0, 15.0));
                clickstreamInfo.setUserDemographics(RandomDataGenUtility.randomUserDemographics());
                clickstreamInfo.setGeoLocation(RandomDataGenUtility.randomGeoLocation(10.0, 50.0, -20.0, 20.0));

                bw.write(clickstreamInfo.toString() + "\n");
            }

            bw.flush();
            System.out.println("Written " + recordsCount + " records to the file.");
            Thread.sleep(1000);  // Sleep for 1 second before generating the next set of records
        }
    }
}
