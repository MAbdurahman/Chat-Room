package net.mabdurrahman.gui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The ChatServer Class
 * @author:  MAbdurrahman
 * @date:  15 May 2016
 */
public class ChatServer {
    /** Instance Variables */
    public static ArrayList<Socket> CONNECTION_ARRAY = new ArrayList<>();
    public static ArrayList<String> CURRENT_USERS = new ArrayList<>();

    /**
     * main Method - Contains the command line arguments
     * @param args - String[] with the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {
            final int port = 444;
            ServerSocket server = new ServerSocket(port);
            System.out.println("Waiting for clients...");

            while (true) {
                Socket socket = server.accept();
                CONNECTION_ARRAY.add(socket);
                System.out.println("Client connected from: " + socket.getLocalAddress().getHostName());

                addUserName(socket);

                ChatServerReturn chat = new ChatServerReturn(socket);
                Thread chatThread = new Thread(chat);
                chatThread.start();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }//end of the main Method
    /**
     * addUserName Method -
     * @param newSocket - the Socket of the new user
     * @throws IOException -
     */
    public static void addUserName(Socket newSocket) throws IOException {
        Scanner inputScanner = new Scanner(newSocket.getInputStream());
        String userName = inputScanner.nextLine();
        CURRENT_USERS.add(userName);

       for (int i = 1; i <= ChatServer.CONNECTION_ARRAY.size(); i++) {
            Socket tempSocket = ChatServer.CONNECTION_ARRAY.get(i - 1);
            PrintWriter printOut = new PrintWriter(tempSocket.getOutputStream());
            printOut.println("#?!" + CURRENT_USERS);
            printOut.flush();
        }

    }//end of the addUserName Method

}//end of the ChatServer Class

