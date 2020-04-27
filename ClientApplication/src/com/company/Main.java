package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        try {
            Socket socket = new Socket(serverAddress, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //printer pentru a scrie in socket, reader pentru a citi din el
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in); // scanner pentru a putea scrie de la tastatura
            while (true) {
                System.out.print("Introduceti comanda dorita: ");
                String request = scanner.nextLine(); // citim linia dorita de la tastatura in request
                if (request.equals("exit")) { //daca request e exit, nici nu mai incercam sa trimitem. Pur si simplu inchidem socketul si terminam programul
                    socket.close();
                    System.exit(0);
                } else //altfel trimitem request-ul catre server
                    out.println(request);
                // Wait the response from the server
                String response = in.readLine(); // citim raspunsul primit de la server
                System.out.println(response); // si il afisam in consola
                if (response.equals("Server stopped!")) //daca server-ul este inchis, atunci nu are rost sa mai tinem clientul deschis si il inchidem
                    System.exit(0);
            }
        } catch (IOException e) {
            System.err.println("No server listening... " + e);
        }
    }
}
