package com.example;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
    private int porta; // porta su cui si aspetta i client [MONOTHREAD]
    private String indirizzoServer; // indirizzo del server

    private Socket server; // socket per il server

    // cose per la comunicazione
    private BufferedReader inDalServer; // qui si riceve la roba dal server
    private DataOutputStream outNelServer; // invia i messaggi al server

    public Client(int porta, String indirizzoServer) {
        this.porta = porta;
        this.indirizzoServer = indirizzoServer;
    }

    public void connetti() {

        try {

            server = new Socket(indirizzoServer, porta);

            inDalServer = new BufferedReader(new InputStreamReader(server.getInputStream())); // riceve messaggi dal
                                                                                              // server
            outNelServer = new DataOutputStream(server.getOutputStream()); // manda messaggi nel server

        } catch (Exception e) {
            System.out.println("Errore nella connessione col server\n");
            System.err.println(e.getMessage());
        }

    }

    public void comunica() {

        System.out.println("Inizio comunicazione");
        Scanner tastiera = new Scanner(System.in);
        String input;

        while (true) {

            try {

                System.out.println(inDalServer.readLine());
                input = tastiera.nextLine();

                outNelServer.writeBytes(input + '\n');

                if (input.equalsIgnoreCase("chiudi")) {
                    break;
                }

            } catch (Exception e) {
                System.out.println("Errore nella comunicazione\n");
                System.err.println(e.getMessage());
            }
        }
        tastiera.close();
        chiudi();

    }

    public void chiudi() {

        System.out.println("Chiusura in corso");
        try {

            inDalServer.close();
            outNelServer.close();
            server.close();

        } catch (Exception e) {
            System.out.println("Errore nella chiusura\n");
            System.err.println(e.getMessage());
        }

    }
}
