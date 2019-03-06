package net.mabdurrahman.gui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * The ChatServerReturn Class
 * @author:  MAbdurrahman
 * @date: 15 May 2016
 */
public class ChatServerReturn implements Runnable {
    /** Instance Variables */
    private final Socket socket;
    private Scanner inputScanner;
    private PrintWriter printOut;
    private String message = "";

    /**
     * ChatServerReturn Constructor -
     * @param socket - Socket
     */
    public ChatServerReturn(Socket socket) {
        this.socket = socket;

    }//end of the ChatServerReturn Constructor
    /**
     * checkConnection Method -
     * @throws IOException
     */
    public void checkConnection() throws IOException {
        if (!socket.isConnected()) {
            for (int i = 1; i <= ChatServer.CONNECTION_ARRAY.size(); i++) {
                if (ChatServer.CONNECTION_ARRAY.get(i) == socket) {
                    ChatServer.CONNECTION_ARRAY.remove(i);

                    String userName = ChatClientGUI.USER_NAME;
                    //ChatServer.CURRENT_USERS.remove(i);
                    ChatServer.CONNECTION_ARRAY.remove(i);
                    ChatServer.CURRENT_USERS.remove(userName);
                    printOut.println("#?!" + ChatServer.CURRENT_USERS);
                    this.socket.close();

                }
            }
            for (int i = 1; i <= ChatServer.CONNECTION_ARRAY.size(); i++) {
                Socket tempSocket = ChatServer.CONNECTION_ARRAY.get(i - 1);
                printOut = new PrintWriter(tempSocket.getOutputStream());
                printOut.println(tempSocket.getLocalAddress().getHostName() + " is disconnected");
                printOut.flush();
                //PrintWriter printTempSocketOut = new PrintWriter(tempSocket.getOutputStream());
                // printTempSocketOut.println(tempSocket.getLocalAddress().getHostName() + " disconnected");
                // printTempSocketOut.flush();
                System.out.println(tempSocket.getLocalAddress().getHostName() + " disconnected");
            }
        }
    }//end of the checkConnection Method
    /**
     * run Method -
     */
    @Override
    public void run() {
        try {
            try {
                inputScanner = new Scanner(socket.getInputStream());
                printOut = new PrintWriter(socket.getOutputStream());

                while (true) {
                    checkConnection();
                    if (!inputScanner.hasNext()) {
                        return;
                    }
                    message = inputScanner.nextLine();
                    System.out.println("Client said:  " + message);

                    for (int i = 1; i <= ChatServer.CONNECTION_ARRAY.size(); i++) {
                        Socket tempSocket = (Socket) ChatServer.CONNECTION_ARRAY.get(i - 1);
                        PrintWriter printTempSocketOut = new PrintWriter(tempSocket.getOutputStream());
                        printTempSocketOut.println(message);
                        printTempSocketOut.flush();
                        System.out.println("Sent to:  " + tempSocket.getLocalAddress().getHostName());

                    }//end of the for loop
                }//end of the while loop
            } finally {
                socket.close();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }//end of the run Method
}//end of the ChatServerReturn Class

