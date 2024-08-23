package com.springBatch.POC.batchSkip;

import org.springframework.batch.core.SkipListener;
import com.springBatch.POC.entity.Customer;

public class CustomSkipListener implements SkipListener<Customer, Customer> {

    @Override
    public void onSkipInRead(Throwable t) {
        // You can log or handle the error when skipping occurs during reading
        System.out.println("Skipped during reading: " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(Customer customer, Throwable t) {
        // You can log or handle the error when skipping occurs during writing
        System.out.println("Skipped during writing: " + customer);
    }

    @Override
    public void onSkipInProcess(Customer customer, Throwable t) {
        // You can log or handle the error when skipping occurs during processing
        System.out.println("Skipped during processing: " + customer);
    }
}

