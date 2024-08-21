package com.springBatch.POC.entity;

import jakarta.persistence.*;

@Entity
@Table(name="customers_information")
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstname")
    private static String firstName;

    @Column(name = "secondname")
    private static String lastName;

    @Column(name = "email")
    private static String email;

    @Column(name = "status")
    private static String status;

    @Column(name = "uuid")
    private static String uuid;

    public Customer() {
    }


    public static void setStatus(String status) {
        Customer.status = status;
    }

    public static void setUuid(String uuid) {
        Customer.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public String getUuid() {
        return uuid;
    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static String  getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

