package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientThread extends Thread {
    private Socket socket = null;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //folosit ca sa citim comanda trimisa de client
            while (true) {
                String request = in.readLine(); //citim din socket intr-un string cu ajutorul BufferedReader-ului
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                if (request == null) //primit daca clientul scrie null (clientul scrie exit, care nici nu e trimis in socket). In cazul acesta e necesar sa se inchida socketul clientului respectiv din server
                    socket.close();
                else if (request.equals("stop")) //daca primim stop, atunci trimitem Server stopped si inchidem server-ului/tot programul. O solutie usor brutala
                {
                    out.println("Server stopped!");
                    out.flush();
                    System.exit(0);
                } else { //altfel, pentru comenzi cream un PrintWriter pentru a intoarce in socket raspunsul de la server -> client
                    // Send the response to the output stream: server → client
                    String raspuns = "Server received the request " + request + ".";
                    System.out.println(raspuns);
                    out.println(raspuns); //scriem in socket
                    out.flush(); //flush ca sa "curatam" out-ul
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        }
    }
}