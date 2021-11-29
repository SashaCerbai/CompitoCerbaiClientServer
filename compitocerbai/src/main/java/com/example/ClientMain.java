package com.example;

public class ClientMain {

    public static void main(String[] args) {
        Client client = new Client(5000, "localhost");

        client.connetti();
        client.comunica();
    }

}
