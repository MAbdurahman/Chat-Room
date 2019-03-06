package net.mabdurrahman.gui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * The ChatClient Class
 * @author:  MAbdurrahman
 * @date:  15 May 2016
 */
public class ChatClient implements Runnable {
    /** Instance Variables */
    private final Socket socket;
    private Scanner inputScanner;
    private final Scanner outputScanner = new Scanner(System.in);
    private PrintWriter printOut;

    /**
     * ChatClient Constructor -
     * @param socket -
     */
    public ChatClient(Socket socket) {
        this.socket = socket;

    }//end of the ChatClient Constructor
    /**
     * run Method -
     */
    @Override
    public void run() {
        try {
            try {
                inputScanner = new Scanner(socket.getInputStream());
                printOut = new PrintWriter(socket.getOutputStream());
                printOut.flush();
                checkStream();

            } finally {
             this.socket.close();
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }//end of the run Method
    /**
     * checkStream Method -
     */
    public void checkStream() {
        while (true) {
            receiveMessage();

        }
    }//end of the checkStream Method
    /**
     * receiveMessage Method -
     */
    public void receiveMessage() {
        if (inputScanner.hasNext()) {
            String message = inputScanner.nextLine();
            if (message.contains("#?!")) {
                String temp = message.substring(3);
                temp = temp.replace("[", "");
                temp = temp.replace("]", "");
                String[] currentUsers = temp.split(", ");
                ChatClientGUI.ONLINE_NAME_LIST.setListData(currentUsers);
                //ChatClientGUI.ONLINE_NAME_LIST.addAll(Arrays.asList(currentUsers));

            } else {
                ChatClientGUI.CONVERSATION_TEXTAREA.append(message + "\n");
            }
        }
    }//end of the receiveMessage Method
    /**
     * sendMessage Method -
     * @param message - A String of the message to sent
     */
    public void sendMessage(String message) {
        printOut.println(ChatClientGUI.USER_NAME + ":  " + message);
        printOut.flush();
        ChatClientGUI.MESSAGE_TEXTAREA.setText("");

    }//end of the sendMessage Method
    /**
     * disconnectFromServer Method -
     * @throws IOException
     */
    public void disconnectFromServer() throws IOException {
        int userName = ChatClientGUI.ONLINE_NAME_LIST.getSelectedIndex();
        String message = ChatClientGUI.USER_NAME + ", you have disconnected from The Chat Room.";
        ChatClientGUI.ONLINE_NAME_LIST.remove(userName - 1);
        JOptionPane.showMessageDialog(null, message);
        printOut.println(ChatClientGUI.USER_NAME + " has disconnected from chat room");


        printOut.flush();
        socket.close();
        System.exit(0);

    }//end of the disconnectFromServer Method
}//end of the ChatClient Class

