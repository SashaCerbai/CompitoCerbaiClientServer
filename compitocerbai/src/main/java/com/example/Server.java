package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class Server {
    private int porta; // porta sulla quale si aspetta il client

    private ServerSocket server; // il socket che aspetterà l'arrivo del client
    private Socket client; // socket del client

    private BufferedReader inDalClient; // qui si riceve le cose dal client
    private DataOutputStream outNelClient; // si invia al client

    private ArrayList<String> lista = new ArrayList<>();

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
            outNelClient.writeBytes("Benvenuto, aggiungi i prodotti alla lista");
            outNelClient.writeBytes("Comandi disponibili: chiudi(per la chiusura della connessione) e lista(per vedere la lista)\n");

            while (true) {

                messaggio = inDalClient.readLine();

                if (messaggio.equalsIgnoreCase("chiudi")) {
                    break;
                }

                if (messaggio.equalsIgnoreCase("lista")) {
                    outNelClient.writeBytes("La lista contiene: ");
                    outNelClient.writeBytes(stampaLista() + " -> ");
                } else {
                    lista.add(messaggio);
                    System.out.println("Prodotto aggiunto alla lista: " + messaggio);
                    outNelClient.writeBytes("--------Lista Aggiornata--------");
                }

                outNelClient.writeBytes(
                        "Inserisci un prodotto nella lista o digita LISTA per vedere la lista con i prodotti gia inseriti\n");// richiesta al client
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

    public String stampaLista() {
        String tuttaLista = "";
        for (int i = 0; i < lista.size(); i++) {
            if (i == lista.size()-1) {
                tuttaLista += lista.get(i);
            } else {
                tuttaLista += lista.get(i) + ", ";
            }
        }
        return tuttaLista;
    }

}
