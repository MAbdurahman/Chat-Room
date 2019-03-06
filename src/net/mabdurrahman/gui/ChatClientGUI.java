package net.mabdurrahman.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

/**
 * The ChatClientGUI Class
 * @author:  MAbdurrahman
 * @date:  15 May 2016
 */
public class ChatClientGUI {
    /** Instance Variables */
    public static String USER_NAME = "Anonymous";

    /** Main window */
    public static JFrame MAIN_WINDOW;
    public static JButton ABOUT_BUTTON;
    public static JButton CONNECT_BUTTON;
    public static JButton DISCONNECT_BUTTON;
    public static JButton HELP_BUTTON;
    public static JButton SEND_BUTTON;
    public static JTextArea MESSAGE_TEXTAREA;
    public static JScrollPane MESSAGE_SCROLLPANE;
    public static JTextArea CONVERSATION_TEXTAREA;
    public static JScrollPane CONVERSATION_SCROLLPANE;
    public static JList ONLINE_NAME_LIST;
    public static JScrollPane ONLINE_SCROLLPANE;
    public static JLabel LOGGED_IN_AS_LABEL;

    /** Login Window */
    public static JFrame LOGIN_WINDOW;
    public static JTextField USER_NAME_TEXTFIELD;
    public static JButton ENTER_BUTTON;
    public static JLabel ENTER_USERNAME_LABEL;
    public static JPanel LOGIN_PANEL;

    private static ChatClient CHAT_CLIENT;

    /**
     * buildLoginWindow Method -
     */
    public static void buildLoginWindow() {
        LOGIN_WINDOW = new JFrame();
        LOGIN_WINDOW.setFont(new Font("Teen", Font.PLAIN, 12));
        LOGIN_WINDOW.setTitle("Login Frame");
        LOGIN_WINDOW.setSize(450, 100);
        LOGIN_WINDOW.setLocation(250, 200);
        LOGIN_WINDOW.setResizable(false);

        LOGIN_PANEL = new JPanel();
        LOGIN_PANEL.setFont(new Font("Teen", Font.PLAIN, 12));
        USER_NAME_TEXTFIELD = new JTextField(25);
        USER_NAME_TEXTFIELD.setFont(new Font("Teen", Font.PLAIN, 12));
        ENTER_BUTTON = new JButton("Enter");
        ENTER_BUTTON.setFont(new Font("Teen", Font.PLAIN, 14));
        ENTER_USERNAME_LABEL = new JLabel("Enter Username: ");
        ENTER_USERNAME_LABEL.setFont(new Font("Teen", Font.PLAIN, 12));

        LOGIN_PANEL.add(ENTER_USERNAME_LABEL);
        LOGIN_PANEL.add(USER_NAME_TEXTFIELD);
        LOGIN_PANEL.add(ENTER_BUTTON);
        LOGIN_WINDOW.add(LOGIN_PANEL);

        ENTER_BUTTON.addActionListener(new ActionListener() {
            /**
             * actionPerformed Method -
             * @param ae - the ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!USER_NAME_TEXTFIELD.getText().equals("")) {
                    USER_NAME = USER_NAME_TEXTFIELD.getText().trim();
                    LOGGED_IN_AS_LABEL.setText(USER_NAME);
                    ChatServer.CURRENT_USERS.add(USER_NAME);
                    // MAIN_WINDOW.setTitle(USER_NAME +"'s Chat Box");
                    LOGIN_WINDOW.setVisible(false);
                    SEND_BUTTON.setEnabled(true);
                    DISCONNECT_BUTTON.setEnabled(true);
                    CONNECT_BUTTON.setEnabled(false);
                    try {
                        connectToChatRoom();

                    } catch (UnknownHostException ex) {
                        JOptionPane.showMessageDialog(null, "UnknownHostException");
                        String message = ex.getMessage();
                        System.out.println(message);
                        System.exit(0);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please enter your name.");
                }

            }//end of the actionPerformed Method for Anonymous ActionListener
        });//end of the Anonymous ActionListener for the ENTER_BUTTON

        LOGIN_WINDOW.setVisible(true);

    }//end of the buildLoginWindow Method
    /**
     * buildMainWindow Method -
     */
    public static void buildMainWindow() {
        MAIN_WINDOW = new JFrame();
        MAIN_WINDOW.setTitle("The Chat Box");
        MAIN_WINDOW.setSize(620, 420);
        MAIN_WINDOW.setLocation(350, 200);
        MAIN_WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MAIN_WINDOW.setResizable(false);
        configureMainWindow();
        // mainWindowAction();
        MAIN_WINDOW.setVisible(true);

    }//end of the buildMainWindow Method
    /**
     * configureMainWindow Method -
     */
    public static void configureMainWindow() {
        MAIN_WINDOW.setBackground(new Color(255, 255, 255));
        MAIN_WINDOW.getContentPane().setLayout(null);

        SEND_BUTTON = new JButton();
        SEND_BUTTON.setBackground(new Color(0, 0, 255));
        SEND_BUTTON.setForeground(new Color(255, 255, 255));
        SEND_BUTTON.setFont(new Font("Teen", Font.PLAIN, 14));
        SEND_BUTTON.setText("Send");
        MAIN_WINDOW.getContentPane().add(SEND_BUTTON);
        SEND_BUTTON.setBounds(10, 135, 110, 25);
        SEND_BUTTON.addActionListener(new ActionListener() {
            /**
             * actionPerformed Method -
             * @param ae - the ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!MESSAGE_TEXTAREA.getText().equals("")) {
                    CHAT_CLIENT.sendMessage(MESSAGE_TEXTAREA.getText());
                    MESSAGE_TEXTAREA.requestFocus();
                }
            }//end of the actionPerformed Method of the Anonymous ActionListener
        });//end of the Anonymous ActionListener for the SEND_BUTTON

        CONNECT_BUTTON = new JButton();
        CONNECT_BUTTON.setBackground(new Color(0, 0, 255));
        CONNECT_BUTTON.setForeground(new Color(255, 255, 255));
        CONNECT_BUTTON.setFont(new Font("Teen", Font.PLAIN, 14));
        CONNECT_BUTTON.setText("Connect");
        CONNECT_BUTTON.setToolTipText("Connect to chat room");
        MAIN_WINDOW.getContentPane().add(CONNECT_BUTTON);
        CONNECT_BUTTON.setBounds(130, 135, 110, 25);
        CONNECT_BUTTON.addActionListener(new ActionListener() {
            /**
             * actionPerformed Method -
             * @param ae - the ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                buildLoginWindow();

            }//end of the actionPerformed Method for the Anonymous ActionListener
        });//end of the Anonymous ActionListener for the CONNECT_BUTTON

        DISCONNECT_BUTTON = new JButton();
        DISCONNECT_BUTTON.setBackground(new Color(0, 0, 255));
        DISCONNECT_BUTTON.setForeground(new Color(255, 255, 255));
        DISCONNECT_BUTTON.setFont(new Font("Teen", Font.PLAIN, 14));
        DISCONNECT_BUTTON.setText("Disconnect");
        MAIN_WINDOW.getContentPane().add(DISCONNECT_BUTTON);
        DISCONNECT_BUTTON.setBounds(250, 135, 110, 25);
        DISCONNECT_BUTTON.addActionListener(new ActionListener() {
            /**
             * actionPerformed Method -
             * @param ae - the ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    CHAT_CLIENT.disconnectFromServer();
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    JOptionPane.showMessageDialog(null, message);

                }
            }//end of the actionPerformed Method for the Anonymous ActionListener
        });//end of the Anonymous ActionListener

        HELP_BUTTON = new JButton();
        HELP_BUTTON.setBackground(new Color(0, 0, 255));
        HELP_BUTTON.setForeground(new Color(255, 255, 255));
        HELP_BUTTON.setFont(new Font("Teen", Font.PLAIN, 14));
        HELP_BUTTON.setText("Help");
        MAIN_WINDOW.getContentPane().add(HELP_BUTTON);
        HELP_BUTTON.setBounds(370, 135, 110, 25);

        ABOUT_BUTTON = new JButton();
        ABOUT_BUTTON.setBackground(new Color(0, 0, 255));
        ABOUT_BUTTON.setForeground(new Color(255, 255, 255));
        ABOUT_BUTTON.setFont(new Font("Teen", Font.PLAIN, 14));
        ABOUT_BUTTON.setText("About");
        MAIN_WINDOW.getContentPane().add(ABOUT_BUTTON);
        ABOUT_BUTTON.setBounds(490, 135, 110, 25);

        MESSAGE_TEXTAREA = new JTextArea();
        MESSAGE_TEXTAREA.setForeground(new Color(0, 0, 255));
        MESSAGE_TEXTAREA.setFont(new Font("Teen", Font.PLAIN, 12));
        MESSAGE_TEXTAREA.setLineWrap(true);
        MESSAGE_TEXTAREA.setColumns(30);
        MESSAGE_TEXTAREA.setRows(13);
        MESSAGE_TEXTAREA.requestFocus();

        MESSAGE_SCROLLPANE = new JScrollPane();
        MESSAGE_SCROLLPANE.setHorizontalScrollBarPolicy
                (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        MESSAGE_SCROLLPANE.setVerticalScrollBarPolicy
                (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        MESSAGE_SCROLLPANE.setViewportView(MESSAGE_TEXTAREA);
        MAIN_WINDOW.getContentPane().add(MESSAGE_SCROLLPANE);
        MESSAGE_SCROLLPANE.setBounds(10, 50, 590, 75);
        MESSAGE_SCROLLPANE.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.blue),
                "Message", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Teen", Font.PLAIN, 12), Color.blue));

        CONVERSATION_TEXTAREA = new JTextArea();
        CONVERSATION_TEXTAREA.setColumns(30);
        CONVERSATION_TEXTAREA.setRows(8);
        CONVERSATION_TEXTAREA.setFont(new Font("Teen", Font.PLAIN, 12));
        CONVERSATION_TEXTAREA.setForeground(new Color(0, 0, 255));
        CONVERSATION_TEXTAREA.setLineWrap(true);
        CONVERSATION_TEXTAREA.setEditable(false);

        CONVERSATION_SCROLLPANE = new JScrollPane();
        CONVERSATION_SCROLLPANE.setHorizontalScrollBarPolicy
                (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        CONVERSATION_SCROLLPANE.setVerticalScrollBarPolicy
                (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        CONVERSATION_SCROLLPANE.setViewportView(CONVERSATION_TEXTAREA);
        MAIN_WINDOW.getContentPane().add(CONVERSATION_SCROLLPANE);
        CONVERSATION_SCROLLPANE.setBounds(10, 180, 445, 195);
        CONVERSATION_SCROLLPANE.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createMatteBorder(1, 1, 1, 1, Color.blue),
                        "Conversation", TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("Teen", Font.PLAIN, 12), Color.blue));

        //String[] testNames = {"Bob Jones", "Sue Jackson", "Pat Boone", "James Brown"};
        ONLINE_NAME_LIST = new JList();
        ONLINE_NAME_LIST.setForeground(new Color(0, 0, 255));
        //ONLINE_NAME_LIST.setListData(testNames);

        ONLINE_SCROLLPANE = new JScrollPane();
        ONLINE_SCROLLPANE.setHorizontalScrollBarPolicy
                (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ONLINE_SCROLLPANE.setVerticalScrollBarPolicy
                (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        ONLINE_SCROLLPANE.setViewportView(ONLINE_NAME_LIST);
        MAIN_WINDOW.getContentPane().add(ONLINE_SCROLLPANE);
        ONLINE_SCROLLPANE.setBounds(470, 180, 130, 195);
        ONLINE_SCROLLPANE.setBorder(BorderFactory.createTitledBorder
                (BorderFactory.createMatteBorder(1, 1, 1, 1, Color.blue),
                        "Currently Online", TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("Teen", Font.PLAIN, 12), Color.blue));

        LOGGED_IN_AS_LABEL = new JLabel();
        LOGGED_IN_AS_LABEL.setFont(new Font("Teen", Font.PLAIN, 12));
        LOGGED_IN_AS_LABEL.setText("Currently logged in as ");
        MAIN_WINDOW.getContentPane().add(LOGGED_IN_AS_LABEL);
        LOGGED_IN_AS_LABEL.setBounds(10, 5, 150, 33);
        LOGGED_IN_AS_LABEL.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 0, Color.blue),
                "Logged On As:", TitledBorder.ABOVE_TOP, TitledBorder.LEFT,
                new Font("Teen", Font.PLAIN, 12), Color.blue));

    }//end of the configureMainWindow Method
    /**
     * connectToChatRoom Method -
     * @throws UnknownHostException
     */
    public static void connectToChatRoom() throws UnknownHostException{
        try {
            final int port = 444;
            final String host = getHostName();
            Socket socket = new Socket(host, port);
            System.out.println("You are connected to server as " + host);

            CHAT_CLIENT = new ChatClient(socket);
            /** Send name to add to ONLINE_NAME_LIST */
            PrintWriter  printOut = new PrintWriter(socket.getOutputStream());
            printOut.println(USER_NAME);
            printOut.flush();

            Thread chatThread = new Thread(CHAT_CLIENT);
            chatThread.start();

        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Server not responding!");
            System.exit(0);
        }
    }//end of the connectToChatRoom Method
    /**
     * enableButtons Method -
     */
    public static void enableButtons() {
        SEND_BUTTON.setEnabled(false);
        DISCONNECT_BUTTON.setEnabled(false);
        CONNECT_BUTTON.setEnabled(true);

    }//end of the enableButtons Method
    /**
     * getHostName Method -
     * @return String - the host name
     * @throws UnknownHostException
     */
    public static String getHostName() throws UnknownHostException {
        InetAddress network = InetAddress.getLocalHost();
        String hostName = network.getHostName();
        return hostName;

    }//end of the getHostName Method
    /**
     * main Method - Contains the command line arguments
     * @param args - String[] with the command line arguments
     */
    public static void main(String[] args) {
        buildMainWindow();
        enableButtons();

    }//end of the main Method
}//end of the ChatClientGUI Class

