package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class Server {
    private int porta; // porta sulla quale si aspetta il client [MONOTHREAD]

    private ServerSocket server; // il socket che aspetterà l'arrivo del client
    private Socket client; // socket del client

    private BufferedReader inDalClient; // qui si riceve le cose dal client
    private DataOutputStream outNelClient; // si invia al client

    public Server(int porta) {
        this.porta = porta;

        try {
            server = new ServerSocket(porta);
        } catch (Exception e) {
            System.out.println("Erorre nell'inizzializzazione del server \n");
            System.err.println(e.getMessage());
        }
    }

    public void connetti() {

        try {
            System.out.println("Server in attesa di un client...");
            client = server.accept();
            System.out.println("Client connesso: " + client.toString());

            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outNelClient = new DataOutputStream(client.getOutputStream());

        } catch (Exception e) {
            System.out.println("Erorre nella connessione\n");
            System.err.println(e.getMessage());
        }
    }

    public void comunica() {

        String messaggio = "";
        System.out.println("Inizio comunicazione");

        try {
            outNelClient.writeBytes("Benvenuto, invia un messaggio\n");

            while (true) {
                messaggio = inDalClient.readLine();
                System.out.println("messaggio ricevuto dal client: " + messaggio);

                if (messaggio.equalsIgnoreCase("chiudi")) {
                    break;
                }
                outNelClient.writeBytes("messaggio: " + messaggio + "\n"); // messaggio inviato

            }

        } catch (Exception e) {
            System.out.println("Erorre nella comunicazione\n");
            System.err.println(e.getMessage());
            chiudi();
        }

        chiudi();

    }

    public void chiudi() {
        System.out.println("Chiusura totale");
        try {

            inDalClient.close();
            outNelClient.close();
            client.close();
            server.close();
        } catch (Exception e) {
            System.out.println("Errore durante la chiusura");
            System.err.println(e.getMessage());
        }
    }

}
