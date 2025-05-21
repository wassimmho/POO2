package views;

import controller.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Timer;
import model.*;


public class CinemaApp extends JFrame implements ActionListener {


    // Declare dashboard panels as instance variables so they are accessible throughout the class
    private JPanel MoviesDashboard;
    private JPanel UserInsightsDashboard;
    private JPanel BroadcastDashboard;
    private JPanel theaterDashboard;

    // Manager elements ------------------------------------------------
    public static MovieManager movieManager;
    public static ClientManager clientManager;
    public static AdminManager adminManager;
    public static TheaterManager theaterManager;
    public static BookingManager bookingManager;
    public static BroadcastManager broadcastManager;

    // Main elements : ------------------------------------------------
    public static Movie CurrentMovie;
    
    // log in elements : ------------------------------------------------
    public JPanel WelcomePanel;
    public JPanel LogInPanel;
    public JPanel SignInPanel;
    public JPanel ForgotPasswordPanel;
    public JPanel ClientPanel;
    public JPanel AdminPanel;
    public JPanel MainPanel;

    //fonctionnalities elements ---------------------------------------
    public Timer timer, timer2, timer3, timer4;
    public int x = -300;
    public int xVelocity = 30;
    public int x2 = -300;
    public int x2Velocity = 30;
    public JScrollPane scrollPane3, scrollPane4;
    public Boolean isPanelVisible = false;
    public LocalTime localTime;

    // decorative Panels ------------------------------------------------
    public TransparentPanel BlurPanel, BlurPanel2, BlurPanel3;

    // layout manager -----------------------------------------------------
    public CardLayout MainCardLayout;

    // User ID ------------------------------------------------------------
    public int USERID ;

    public CinemaApp() {
        // Initialize managers
        movieManager = new MovieManager();
        clientManager = new ClientManager();
        adminManager = new AdminManager();
        theaterManager = new TheaterManager();
        bookingManager = new BookingManager();
        broadcastManager = new BroadcastManager(theaterManager);

      
       // Initialize components
       this.setTitle("MovieBooking App");
       ImageIcon AppLogo = new ImageIcon("Poo2-TRY-\\Fahd\\bookingTICKET\\Images\\Logo.png");
       this.setIconImage(AppLogo.getImage());
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setSize(1200, 750);
       this.setLocationRelativeTo(null);
       this.setVisible(false);
       this.setResizable(false);
       this.setLayout(null);

        MainCardLayout = new CardLayout();
        MainPanel = new JPanel(MainCardLayout);
        

        // openning panel --------------------------------------------------
        WelcomePanel = new JPanel();
        WelcomePanel.setBounds(0, 0, 1200, 750);
        WelcomePanel.setLayout(null);
        WelcomePanel.setOpaque(false);

        JPanel WelcomeBackgroundPanel = CreateBackGroundPanel();
        JPanel WelcomeElements = CreateWelcomePanel();

        WelcomePanel.add(WelcomeBackgroundPanel);
        WelcomePanel.add(WelcomeElements);

        WelcomePanel.setComponentZOrder(WelcomeElements, 0);
        WelcomePanel.setComponentZOrder(WelcomeBackgroundPanel, 1);

        // log in panel ---------------------------------------------------
        JPanel loginpanel = createLoginPanel();

        // Sign in panel ----------------------------------------------------
        JPanel signInPanel = createSignUpPanel();

        //profile admin panel
        int userId = 0; // Replace with the actual user ID you want to use
        JPanel ProfileAdminPanel = createprofile(userId);
        // forgot password panel --------------------------------------------
        ForgotPasswordPanel = new JPanel();
        ForgotPasswordPanel.setBounds(0, 0, 1200, 750);
        ForgotPasswordPanel.setLayout(null);
        ForgotPasswordPanel.setOpaque(false);

        JPanel ForgotPasswordBackground = CreateBackGroundPanel();
        JPanel ForgotPasswordElements = CreateForgotPasswordPanel();

        ForgotPasswordPanel.add(ForgotPasswordBackground);
        ForgotPasswordPanel.add(ForgotPasswordElements);

        ForgotPasswordPanel.setComponentZOrder(ForgotPasswordElements, 0);
        ForgotPasswordPanel.setComponentZOrder(ForgotPasswordBackground, 1);

        // Client / User Interface --------------------------------------------
        ClientPanel = new JPanel();
        ClientPanel.setBounds(0, 0, 1200, 750);
        ClientPanel.setLayout(null);
        ClientPanel.setOpaque(false);

        JPanel ClientElements = CreateClientInterface();

        ClientPanel.add(ClientElements);

        // admin interface -----------------------------------------------------
        JPanel AdminElements = createInterfaceAdminPanel();

        //Account Panel--------------------------------------------------------
        JPanel AccountPanel = CreateAccountPanel();

        //Account admin Panel--------------------------------------------------------
        JPanel AccountAdminPanel = CreateAccountAdminPanel();

        //buy panel--------------------------------------------------------
        JPanel BuyPanel = CreateBuyPanel(CurrentMovie);

        //profile panel 
        
        

        // add panels to the main panel ----------------------------------------
        MainPanel.add(WelcomePanel, "Welcome");
        MainPanel.add(loginpanel, "LogIn");
        MainPanel.add(signInPanel, "SignIn");
        MainPanel.add(ForgotPasswordPanel, "Forgot Password");
        MainPanel.add(ClientPanel, "Client");
        MainPanel.add(AdminElements, "Admin");
        MainPanel.add(AccountPanel, "Account");
        MainPanel.add(AccountAdminPanel, "Account Admin");
        MainPanel.add(BuyPanel, "Buy");
        MainPanel.add(ProfileAdminPanel, "ProfileAdminPanel");

        // add the main panel to the JFrame ----------------------------------
        setContentPane(MainPanel);
        MainCardLayout.show(MainPanel, "Admin");

        // Revalidate and repaint to ensure the SettingsPanel is displayed
        this.revalidate();
        this.repaint();

    }

        private JPanel createLoginPanel() {
        // Colors
        Color bgcolor = new Color(0x121213);
        Color secondarycolor = new Color(0x151517);



            JPanel loginPanel = new JPanel();
            loginPanel.setLayout(null); 
            loginPanel.setBackground(bgcolor);
            
            RoundedPanel cardPanel = new RoundedPanel(5);
            cardPanel.setLayout(null);
            cardPanel.setForeground(Color.WHITE);
            cardPanel.setBackground(secondarycolor);
            cardPanel.setBounds(144, 119, 911, 512);
            cardPanel.setOpaque(false);
            cardPanel.setRoundedBorder(Color.white, 1);
    
            JPanel movingimage = new JPanel();
            movingimage.setLayout(null);
            movingimage.setBackground(null);
            movingimage.setBounds(126, 493, 135, 10);
            cardPanel.add(movingimage);
    
    
            RoundedPanel rect1 = new RoundedPanel(3);
            rect1.setBounds(0, 0, 35, 4);
            rect1.setOpaque(true);
            movingimage.add(rect1);
    
            RoundedPanel rect2 = new RoundedPanel(3);
            rect2.setBounds(45, 0, 35, 4);
            rect2.setOpaque(true);
            movingimage.add(rect2);
    
            RoundedPanel rect3 = new RoundedPanel(3);
            rect3.setBounds(95, 0, 35, 4);
            rect3.setOpaque(true);
            rect3.setVisible(true);
            rect3.setFocusable(false);
            rect3.setBorder(null);
            movingimage.add(rect3);
    
            RoundedPanel imagPanel = new RoundedPanel(5);
            imagPanel.setLayout(null);
            imagPanel.setBounds(16, 16, 355, 456);
            imagPanel.setRoundedBorder(Color.white, 1);
            imagPanel.setOpaque(false);
            cardPanel.add(imagPanel);
    
            String[] imagePaths = {
                "img\\logmovies\\image1.png",
                "img\\logmovies\\image2.png",
                "img\\logmovies\\image3.png",
            };
    
            // Timer to change images
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int currentIndex = 0;
    
                public void run() {
                    rect3.setBackground(Color.gray);
                    imagPanel.setBackgroundImage(imagePaths[currentIndex]);
                    currentIndex = (currentIndex + 1) % imagePaths.length;
                    if(currentIndex == 0){
                        rect1.setBackground(Color.white);
                        rect2.setBackground(Color.GRAY);
                        rect3.setBackground(Color.GRAY);
                    }else if(currentIndex == 1){
                        rect1.setBackground(Color.GRAY);
                        rect2.setBackground(Color.white);
                        rect3.setBackground(Color.GRAY);
                        
                }else if(currentIndex == 2){
                        rect1.setBackground(Color.GRAY);
                        rect2.setBackground(Color.GRAY);
                        rect3.setBackground(Color.white);
                    }
                }
            }, 0, 2300);
    
            JLabel titleLabel = new JLabel("Welcome Back! Login");
            titleLabel.setBounds(435, 45, 375, 48);
            titleLabel.setForeground(Color.white);
            titleLabel.setFont(new Font("Poppins", Font.BOLD, 32));
            cardPanel.add(titleLabel); 
            
            JLabel UserLabel = new JLabel("Username");
            UserLabel.setBounds(435, 144, 100, 18);
            UserLabel.setForeground(Color.white);
            UserLabel.setFont(new Font("Roboto", Font.BOLD, 15));
            cardPanel.add(UserLabel);
    
            JTextField UserField = new JTextField();
            UserField.setBounds(435, 168, 390, 39);
            UserField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                new EmptyBorder(5, 10, 5, 10) 
            ));
            UserField.setForeground(Color.white);
            UserField.setBackground(secondarycolor);
            UserField.setCaretColor(Color.white);
            UserField.setText("Enter your username");
            UserField.setForeground(Color.GRAY);
            UserField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (UserField.getText().equals("Enter your username")) {
                        UserField.setText("");
                        UserField.setForeground(Color.WHITE);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (UserField.getText().isEmpty()) {
                        UserField.setText("Enter your username");
                        UserField.setForeground(Color.GRAY);
                    }
                }
            });
            cardPanel.add(UserField);
    
            JLabel emailLabel = new JLabel("Email");
            emailLabel.setBounds(435, 227, 41, 18);
            emailLabel.setForeground(Color.white);
            emailLabel.setFont(new Font("Roboto", Font.BOLD, 15));
            cardPanel.add(emailLabel);
    
            JTextField emailField = new JTextField();
            emailField.setBounds(435, 251, 390, 39);
            emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                new EmptyBorder(5, 10, 5, 10) // espace da5el textfield min tekteb
            ));
            emailField.setForeground(Color.white);
            emailField.setBackground(secondarycolor);
            emailField.setCaretColor(Color.white);
            emailField.setText("Enter your email");
            emailField.setForeground(Color.GRAY);
            emailField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (emailField.getText().equals("Enter your email")) {
                        emailField.setText("");
                        emailField.setForeground(Color.WHITE);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (emailField.getText().isEmpty()) {
                        emailField.setText("Enter your email");
                        emailField.setForeground(Color.GRAY);
                    }
                }
            });
            cardPanel.add(emailField);
    
            JLabel passwordLabel = new JLabel("Password");
            passwordLabel.setBounds(435, 310, 100, 18);
            passwordLabel.setForeground(Color.white);
            passwordLabel.setFont(new Font("Roboto", Font.BOLD, 15));
            cardPanel.add(passwordLabel);
    
            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(435, 334, 390, 40);
            passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                new EmptyBorder(5, 10, 5, 10) // Add padding inside the text field
            ));
            passwordField.setForeground(Color.white);
            passwordField.setBackground(secondarycolor);
            passwordField.setCaretColor(Color.white);
            passwordField.setForeground(Color.GRAY);
            passwordField.setText("Enter your Password");
            passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (passwordField.getText().equals("Enter your Password")) {
                        passwordField.setText("");
                        passwordField.setForeground(Color.WHITE);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (passwordField.getText().isEmpty()) {
                        passwordField.setText("Enter your Password");
                        passwordField.setForeground(Color.GRAY);
                    }
                }
            });
            cardPanel.add(passwordField);
    
            RoundedButton loginButton = new RoundedButton("Login",3);
            loginButton.setBounds(513, 407, 220, 39);
            loginButton.setBackground(bgcolor);
            loginButton.setForeground(Color.white);
            loginButton.setFont(new Font("Roboto", Font.BOLD, 16));
            loginButton.setOpaque(false);
            loginButton.setRoundedBorder(Color.WHITE, 1);
            loginButton.addActionListener(e -> {
                String username = UserField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
    
                if(username.equals("Enter your username") || email.equals("Enter your email") || password.equals("Enter your Password")){
                    JOptionPane.showMessageDialog(null, "Please fill all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                if(ClientManager.userExists(username, email, password)){
                    
                    JOptionPane.showMessageDialog(null, "User logged in successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    USERID = ClientManager.getuserid(username);
                    JPanel ProfilePanel = createprofile(USERID);
                    MainPanel.add(ProfilePanel, "Profile");
                    MainCardLayout.show(this.MainPanel, "Profile");
                    emailField.setText("");
                    passwordField.setText("");
                    UserField.setText("");
                }else{
                    JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            cardPanel.add(loginButton);
    
            JButton goToSignUpButton = new JButton("Don't have an account? Sign up");
            goToSignUpButton.setBounds(418, 95, 200, 20);
            goToSignUpButton.setForeground(Color.white);
            goToSignUpButton.setFont(new Font("Roboto", Font.PLAIN, 12));
            goToSignUpButton.setBorderPainted(false);
            goToSignUpButton.setContentAreaFilled(false);
            goToSignUpButton.setFocusPainted(false);
            goToSignUpButton.addActionListener(event -> MainCardLayout.show(this.MainPanel, "SignIn"));
            goToSignUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cardPanel.add(goToSignUpButton);
    
            

            loginPanel.add(cardPanel);
            return loginPanel;
        }

        public JPanel createSignUpPanel() {

            // Colors
            Color bgcolor = new Color(0x121213);
            Color secondarycolor = new Color(0x151517);

            JPanel signUpPanel = new JPanel();
            signUpPanel.setLayout(null);
            signUpPanel.setBackground(bgcolor);
    
            RoundedPanel cardPanel = new RoundedPanel(5);
            cardPanel.setLayout(null);
            cardPanel.setForeground(Color.WHITE);
            cardPanel.setBackground(secondarycolor);
            cardPanel.setBounds(144, 119, 911, 512);
            cardPanel.setOpaque(false);
            cardPanel.setRoundedBorder(Color.white, 1);
    
            JPanel movingimage = new JPanel();
            movingimage.setLayout(null);
            movingimage.setBackground(null);
            movingimage.setBounds(126, 493, 135, 5);
            cardPanel.add(movingimage);
    
    
            RoundedPanel rect1 = new RoundedPanel(3);
            rect1.setBounds(0, 0, 35, 4);
            rect1.setOpaque(true);
            movingimage.add(rect1);
    
            RoundedPanel rect2 = new RoundedPanel(3);
            rect2.setBounds(45, 0, 35, 4);
            rect2.setOpaque(true);
            movingimage.add(rect2);
    
            RoundedPanel rect3 = new RoundedPanel(3);
            rect3.setBounds(95, 0, 35, 4);
            rect3.setOpaque(true);
            rect3.setVisible(true);
            rect3.setFocusable(false);
            rect3.setBorder(null);
            movingimage.add(rect3);
    
            RoundedPanel imagPanel = new RoundedPanel(5);
            imagPanel.setLayout(null);
            imagPanel.setBounds(16, 16, 355, 456);
            //imagPanel.setBackgroundImage("Poo2-TRY-\\\\All\\bookingTICKET\\images\\image.png");
            imagPanel.setRoundedBorder(Color.white, 1);
            imagPanel.setOpaque(false);
            cardPanel.add(imagPanel);
    
             String[] imagePaths = {
                "img\\logmovies\\image1.png",
                "img\\logmovies\\image2.png",
                "img\\logmovies\\image3.png",
            };
    
            // Timer to change images
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int currentIndex = 0;
    
                @Override
                public void run() {
                    imagPanel.setBackgroundImage(imagePaths[currentIndex]);
                    currentIndex = (currentIndex + 1) % (imagePaths.length );
                    if(currentIndex == 0){
                        rect1.setBackground(Color.white);
                        rect2.setBackground(Color.GRAY);
                        rect3.setBackground(Color.GRAY);
                    }else if(currentIndex == 1){
                        rect1.setBackground(Color.GRAY);
                        rect2.setBackground(Color.white);
                        rect3.setBackground(Color.GRAY);
                        
                }else if(currentIndex == 2){
                        rect1.setBackground(Color.GRAY);
                        rect2.setBackground(Color.GRAY);
                        rect3.setBackground(Color.white);
                    }
                }
            }, 0, 2300);
    
            JLabel titleLabel = new JLabel("Create an Account");
            titleLabel.setBounds(435, 45, 375, 48);
            titleLabel.setForeground(Color.white);
            titleLabel.setFont(new Font("Poppins", Font.BOLD, 32));
            cardPanel.add(titleLabel); 
    
            JLabel UserLabel = new JLabel("Name");
            UserLabel.setBounds(435, 144, 100, 18);
            UserLabel.setForeground(Color.white);
            UserLabel.setFont(new Font("Roboto", Font.BOLD, 15));
            cardPanel.add(UserLabel);
    
            JTextField UserField = new JTextField();
            UserField.setBounds(435, 168, 180, 39);
            UserField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                new EmptyBorder(5, 10, 5, 10) 
            ));
            UserField.setForeground(Color.white);
            UserField.setBackground(secondarycolor);
            UserField.setCaretColor(Color.white);
            UserField.setText("Enter your Name");
            UserField.setForeground(Color.GRAY);
            UserField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (UserField.getText().equals("Enter your Name")) {
                        UserField.setText("");
                        UserField.setForeground(Color.WHITE);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (UserField.getText().isEmpty()) {
                        UserField.setText("Enter your Name");
                        UserField.setForeground(Color.GRAY);
                    }
                }
            });
            cardPanel.add(UserField);
    
    
            JLabel LastNameLabel = new JLabel("Last name");
            LastNameLabel.setBounds(645, 144, 100, 18);
            LastNameLabel.setForeground(Color.white);
            LastNameLabel.setFont(new Font("Roboto", Font.BOLD, 15));
            cardPanel.add(LastNameLabel);
    
            JTextField LastNameField = new JTextField();
            LastNameField.setBounds(645, 168, 180, 39);
            LastNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                new EmptyBorder(5, 10, 5, 10) 
            ));
            LastNameField.setForeground(Color.white);
            LastNameField.setBackground(secondarycolor);
            LastNameField.setCaretColor(Color.white);
            LastNameField.setText("Enter your Last name");
            LastNameField.setForeground(Color.GRAY);
            LastNameField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (LastNameField.getText().equals("Enter your Last name")) {
                        LastNameField.setText("");
                        LastNameField.setForeground(Color.WHITE);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (LastNameField.getText().isEmpty()) {
                        LastNameField.setText("Enter your Last name");
                        LastNameField.setForeground(Color.GRAY);
                    }
                }
            });
            cardPanel.add(LastNameField);
    
    
    
            JLabel AgeLabel = new JLabel("Age");
            AgeLabel.setBounds(645, 215, 100, 18);
            AgeLabel.setForeground(Color.white);
            AgeLabel.setFont(new Font("Roboto", Font.BOLD, 15));
            cardPanel.add(AgeLabel);
    
            JTextField AgeField = new JTextField();
            AgeField.setBounds(645, 239, 180, 39);
            AgeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                new EmptyBorder(5, 10, 5, 10) 
            ));
            AgeField.setForeground(Color.white);
            AgeField.setBackground(secondarycolor);
            AgeField.setCaretColor(Color.white);
            AgeField.setText("Enter your Age");
            AgeField.setForeground(Color.GRAY);
            AgeField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (AgeField.getText().equals("Enter your Age")) {
                        AgeField.setText("");
                        AgeField.setForeground(Color.WHITE);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (AgeField.getText().isEmpty()) {
                        AgeField.setText("Enter your Age");
                        AgeField.setForeground(Color.GRAY);
                    }
                }
            });
            cardPanel.add(AgeField);
    
    
            JLabel usernameLabel = new JLabel("User name");
            usernameLabel.setBounds(435, 215, 100, 18);
            usernameLabel.setForeground(Color.white);
            usernameLabel.setFont(new Font("Roboto", Font.BOLD, 15));
            cardPanel.add(usernameLabel);
    
            JTextField username = new JTextField();
            username.setBounds(435, 239, 180, 39);
            username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                new EmptyBorder(5, 10, 5, 10) 
            ));
            username.setForeground(Color.white);
            username.setBackground(secondarycolor);
            username.setCaretColor(Color.white);
            username.setText("Enter your username");
            username.setForeground(Color.GRAY);
            username.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (username.getText().equals("Enter your username")) {
                        username.setText("");
                        username.setForeground(Color.WHITE);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (username.getText().isEmpty()) {
                        username.setText("Enter your username");
                        username.setForeground(Color.GRAY);
                    }
                }
            });
            cardPanel.add(username);
    
    
            JLabel emailLabel = new JLabel("Email");
            emailLabel.setBounds(435, 289, 41, 18);
            emailLabel.setForeground(Color.white);
            emailLabel.setFont(new Font("Roboto", Font.BOLD, 15));
            cardPanel.add(emailLabel);
    
            JTextField emailField = new JTextField();
            emailField.setBounds(435, 317, 390, 39);
            emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                new EmptyBorder(5, 10, 5, 10) // Add padding inside the text field
            ));
            emailField.setForeground(Color.white);
            emailField.setBackground(secondarycolor);
            emailField.setCaretColor(Color.white);
            emailField.setText("Enter your email");
            emailField.setForeground(Color.GRAY);
            emailField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (emailField.getText().equals("Enter your email")) {
                        emailField.setText("");
                        emailField.setForeground(Color.WHITE);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (emailField.getText().isEmpty()) {
                        emailField.setText("Enter your email");
                        emailField.setForeground(Color.GRAY);
                    }
                }
            });
            cardPanel.add(emailField);
    
    
    
            JLabel passwordLabel = new JLabel("Password");
            passwordLabel.setBounds(435, 363, 100, 18);
            passwordLabel.setForeground(Color.white);
            passwordLabel.setFont(new Font("Roboto", Font.BOLD, 15));
            cardPanel.add(passwordLabel);
    
            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(435, 387, 390, 40);
            passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                new EmptyBorder(5, 10, 5, 10) 
            ));
            passwordField.setForeground(Color.white);
            passwordField.setBackground(secondarycolor);
            passwordField.setCaretColor(Color.white);
            passwordField.setForeground(Color.GRAY);
            passwordField.setText("Enter your Password");
            passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (passwordField.getText().equals("Enter your Password")) {
                        passwordField.setText("");
                        passwordField.setForeground(Color.WHITE);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (passwordField.getText().isEmpty()) {
                        passwordField.setText("Enter your Password");
                        passwordField.setForeground(Color.GRAY);
                    }
                }
            });
            cardPanel.add(passwordField);
    
            
            RoundedButton signinbutton = new RoundedButton("Sign in",3);
            signinbutton.setBounds(513, 455, 220, 39);
            signinbutton.setBackground(bgcolor);
            signinbutton.setForeground(Color.white);
            signinbutton.setFont(new Font("Roboto", Font.BOLD, 16));
            signinbutton.setOpaque(false);
            signinbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            signinbutton.setRoundedBorder(Color.WHITE, 1);
            signinbutton.addActionListener(event -> {
                String lname = LastNameField.getText();
                String user_name = username.getText();
                String name = UserField.getText();
                String age = AgeField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                if (ClientManager.isDuplicate(user_name, email, password)) {
                    JOptionPane.showMessageDialog(null, "Account already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (ClientManager.addClient(user_name, lname, name, email, password, Integer.parseInt(age), 0)) {
                        JOptionPane.showMessageDialog(null, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        emailField.setText("");
                        passwordField.setText("");
                        UserField.setText("");
                        username.setText("");
                        LastNameField.setText("");
                        AgeField.setText("");
                        MainCardLayout.show(this.MainPanel, "login");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add user!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
            });

            signinbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cardPanel.add(signinbutton);
    
            JButton goToLoginButton = new JButton("Already have an account? Login");
            goToLoginButton.setBounds(395, 95, 250, 30);
            goToLoginButton.setForeground(Color.white);
            goToLoginButton.setFont(new Font("Roboto", Font.PLAIN, 12));
            goToLoginButton.setBorderPainted(false);
            goToLoginButton.setContentAreaFilled(false);
            goToLoginButton.setFocusPainted(false);
            goToLoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cardPanel.add(goToLoginButton);
    
            goToLoginButton.addActionListener(event -> MainCardLayout.show(this.MainPanel, "LogIn"));
    
            signUpPanel.add(cardPanel);
            return signUpPanel;
    }

        public JPanel createprofile(int userid) {

        System.out.println("Creating profile for USERID: " + userid); 
        Color bgcolor = new Color(0x121213);
        Color secondarycolor = new Color(0x151517);

        JPanel profile = new JPanel();
        profile.setBounds(0, 0, 1200, 750);
        profile.setBackground(bgcolor);
        profile.setLayout(null);

        // Left Panel-------------------------------------------------
        JPanel leftpanel = new JPanel();
        leftpanel.setBounds(0, 0, 215, 750);
        leftpanel.setBackground(secondarycolor);
        profile.add(leftpanel);

        //rightpanel-------------------------------------------------
        JPanel rightpanel = new JPanel();
        rightpanel.setBounds(215, 0, 985, 750);
        rightpanel.setBackground(Color.black);
        rightpanel.setLayout(null);

        //editpanel-------------------------------------------------
        JPanel editpanel = new JPanel();
        editpanel.setBounds(985, 0, 291, 750);
        editpanel.setBackground(secondarycolor);
        editpanel.setLayout(null);
        rightpanel.add(editpanel);

        JLabel title = new JLabel("Update Account");
        title.setBounds(45, 37, 285, 50);
        title.setForeground(Color.white);
        title.setFont(new Font("Poppins", Font.PLAIN, 32));
        rightpanel.add(title);

        RoundedPanel imageframe = new RoundedPanel(8);
        imageframe.setBounds(45, 110, 270, 260);
        String imagePath = "img\\default.png";
        try {
            imagePath = ClientManager.getUserImagePath(userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        imageframe.setBackgroundImage(imagePath);
        imageframe.setRoundedBorder(new Color(0x363030), 1);
        rightpanel.add(imageframe);

        RoundedPanel infoframe = new RoundedPanel(1);
        infoframe.setBounds(373, 110, 568, 261);
        infoframe.setBackground(bgcolor);
        infoframe.setLayout(null);
        infoframe.setRoundedBorder(new Color(0x363030), 1);
        rightpanel.add(infoframe);

        JLabel name = new JLabel("");
        try {
            name.setText(ClientManager.getuserfirstname(userid) + " " + ClientManager.getuserlastname(userid));
        } catch (SQLException e) {
            e.printStackTrace();
            name.setText("Error retrieving name");
        }
        name.setFont(new Font("Poppins", Font.BOLD, 20));
        name.setBounds(33, 12, 200, 50);
        name.setForeground(Color.white);
        infoframe.add(name);

        JLabel username = new JLabel("Username :");
        try {
            username.setText("Username : " + ClientManager.getusersname(userid));
        } catch (SQLException e) {
            e.printStackTrace();
            username.setText("Error retrieving user's name");
        }
        username.setBounds(33, 59, 100, 50);
        username.setForeground(Color.white);
        infoframe.add(username);

        JLabel email = new JLabel("Email :");
        try {
            email.setText("Email : " + ClientManager.getuseremail(userid));
        } catch (SQLException e) {
            e.printStackTrace();
            email.setText("Error retrieving email");
        }
        email.setBounds(33, 97, 100, 50);
        email.setForeground(Color.white);
        infoframe.add(email);

        JLabel age = new JLabel("Age : ");
        try {
            age.setText("Age : " + ClientManager.getuserage(userid) + " ");
        } catch (SQLException e) {
            e.printStackTrace();
            age.setText("Error retrieving age");
        }
        age.setBounds(33, 135, 100, 50);
        age.setForeground(Color.white);
        infoframe.add(age);

        JLabel phonenum = new JLabel("Phone Number :");
        try {
            phonenum.setText("Phone Number : " + ClientManager.getuserphone(userid));
        } catch (SQLException e) {
            e.printStackTrace();
            phonenum.setText("Error retrieving phone number");
        }
        phonenum.setBounds(33, 173, 300, 50);
        phonenum.setForeground(Color.white);
        infoframe.add(phonenum);

        JLabel curbalance = new JLabel("Current Balance :");
        try {
            curbalance.setText("Current Balance : " + ClientManager.getuserbalance(userid));
        } catch (SQLException e) {
            e.printStackTrace();
            curbalance.setText("Error retrieving balance");
        }
        curbalance.setBounds(33, 211, 300, 50);
        curbalance.setForeground(Color.white);
        infoframe.add(curbalance);

        //my list-------------------------------------------------
        RoundedButton mylist = new RoundedButton("My List", 3);
        mylist.setBounds(45, 400, 271, 141);
        mylist.setFont(new Font("Poppins", Font.PLAIN, 20));
        mylist.setBackground(secondarycolor);
        mylist.setRoundedBorder(new Color(0x363030), 1);
        mylist.setForeground(Color.white);
        mylist.setFocusPainted(false);
        mylist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mylist.setRoundedBorder(Color.red, 1);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                mylist.setRoundedBorder(new Color(0x363030), 1);
            }
        });
        mylist.addActionListener(e -> {
            MainCardLayout.show(MainPanel, "Admin");
        });
        rightpanel.add(mylist);

        //actions-------------------------------------------------
        RoundedPanel actions = new RoundedPanel(8);
        actions.setBounds(373, 400, 568, 141);
        actions.setBackground(secondarycolor);
        actions.setRoundedBorder(new Color(0x363030), 1);
        actions.setLayout(null);
        rightpanel.add(actions, 1);

        JLabel actiontitle = new JLabel("Your Actions");
        actiontitle.setBounds(20, 10, 200, 50);
        actiontitle.setForeground(Color.white);
        actiontitle.setFont(new Font("Poppins", Font.BOLD, 20));
        actions.add(actiontitle);

        JButton changeimg = new JButton("Change Image");
        changeimg.setBounds(22, 75, 170, 40);
        changeimg.setBorder(null);
        changeimg.setFocusPainted(false);
        changeimg.setBackground(Color.white);
        changeimg.setForeground(Color.black);
        changeimg.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePathupdate = selectedFile.getAbsolutePath();
            imageframe.setBackgroundImage(imagePathupdate);

            // Update the user's image in the database
            ClientManager.updateClientImage(userid, imagePathupdate);
            JOptionPane.showMessageDialog(null, "Profile image updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        actions.add(changeimg);

        JButton removeimg = new JButton("Remove Image");
        removeimg.setBounds(211, 75, 170, 40);
        removeimg.setBorder(null);
        removeimg.setFocusPainted(false);
        removeimg.setBackground(Color.white);
        removeimg.setForeground(Color.black);
        removeimg.addActionListener(e -> {
            imageframe.setBackgroundImage("img\\default.png");
            ClientManager.updateClientImage(userid, "img\\default.png");
        });
        actions.add(removeimg);

        JButton Editebalance = new JButton("Edit Balance");
        Editebalance.setBounds(400, 75, 145, 40);
        Editebalance.setBorder(null);
        Editebalance.setFocusPainted(false);
        Editebalance.setBackground(Color.white);
        Editebalance.setForeground(Color.black);
        actions.add(Editebalance);

        //Account Related-------------------------------------------------
        RoundedPanel Accountpanel = new RoundedPanel(1);
        Accountpanel.setBounds(44, 567, 897, 125);
        Accountpanel.setBackground(secondarycolor);
        Accountpanel.setLayout(null);
        Accountpanel.setRoundedBorder(new Color(0x363030), 1);
        rightpanel.add(Accountpanel);

        JLabel accounttitle = new JLabel("Account Related");
        accounttitle.setBounds(356, 7, 300, 50);
        accounttitle.setForeground(Color.white);
        accounttitle.setFont(new Font("Poppins", Font.BOLD, 20));
        Accountpanel.add(accounttitle);

        JButton DeleteAcc = new JButton("Delete Account");
        DeleteAcc.setBounds(507, 65, 200, 40);
        DeleteAcc.setBorder(null);
        DeleteAcc.setFocusPainted(false);
        DeleteAcc.setBackground(Color.RED);
        DeleteAcc.setForeground(Color.white);
        DeleteAcc.addActionListener(e ->{
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?", "Warning", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                
                ClientManager.removeClient(userid);
                MainCardLayout.show(MainPanel, "SignIn");
                
            }
        });
        Accountpanel.add(DeleteAcc);

        //editpanel-------------------------------------------------
        JLabel editlabel = new JLabel("Edit Profile");
        editlabel.setBounds(23, 20, 200, 50);
        editlabel.setForeground(Color.white);
        editlabel.setFont(new Font("Poppins", Font.BOLD, 20));
        editpanel.add(editlabel);

        JLabel editFirstName = new JLabel("First Name");
        editFirstName.setBounds(23, 70, 150, 30);
        editFirstName.setForeground(Color.white);
        editpanel.add(editFirstName);

        JCheckBox editFirstNameBox = new JCheckBox();
        editFirstNameBox.setBounds(246, 80, 20, 20);
        editFirstNameBox.setBackground(secondarycolor);
        editpanel.add(editFirstNameBox);

        JTextField editFirstNameText = new JTextField();
        editFirstNameText.setBounds(23, 105, 245, 40);
        editFirstNameText.setBackground(secondarycolor);
        editFirstNameText.setForeground(Color.white);
        editFirstNameText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1), new EmptyBorder(0, 10, 0, 0)));
        editFirstNameText.setCaretColor(Color.white);
        editFirstNameText.setText("Enter your First Name");
        editFirstNameText.setForeground(Color.GRAY);
        editFirstNameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            if (editFirstNameText.getText().equals("Enter your First Name")) {
                editFirstNameText.setText("");
                editFirstNameText.setForeground(Color.WHITE);
            }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
            if (editFirstNameText.getText().isEmpty()) {
                editFirstNameText.setText("Enter your First Name");
                editFirstNameText.setForeground(Color.GRAY);
            }
            }
        });
        editpanel.add(editFirstNameText);

        editFirstNameBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (!editFirstNameBox.isSelected()) {
                editFirstNameText.setEnabled(false);
            } else {
                editFirstNameText.setEnabled(true);
            }
            }
        });
        editFirstNameText.setEnabled(false);

        JLabel editLastName = new JLabel("Last Name");
        editLastName.setBounds(23, 140, 150, 30);
        editLastName.setForeground(Color.white);
        editpanel.add(editLastName);

        JCheckBox editLastNameBox = new JCheckBox();
        editLastNameBox.setBounds(246, 150, 20, 20);
        editLastNameBox.setBackground(secondarycolor);
        editpanel.add(editLastNameBox);

        JTextField editLastNameText = new JTextField();
        editLastNameText.setBounds(23, 175, 245, 40);
        editLastNameText.setBackground(secondarycolor);
        editLastNameText.setForeground(Color.white);
        editLastNameText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1), new EmptyBorder(0, 10, 0, 0)));
        editLastNameText.setCaretColor(Color.white);
        editLastNameText.setText("Enter your Last Name");
        editLastNameText.setForeground(Color.GRAY);
        editLastNameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            if (editLastNameText.getText().equals("Enter your Last Name")) {
                editLastNameText.setText("");
                editLastNameText.setForeground(Color.WHITE);
            }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
            if (editLastNameText.getText().isEmpty()) {
                editLastNameText.setText("Enter your Last Name");
                editLastNameText.setForeground(Color.GRAY);
            }
            }
        });
        editpanel.add(editLastNameText);

        editLastNameBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (!editLastNameBox.isSelected()) {
                editLastNameText.setEnabled(false);
            } else {
                editLastNameText.setEnabled(true);
            }
            }
        });
        editLastNameText.setEnabled(false);

        JLabel editusername = new JLabel("Username");
        editusername.setBounds(23, 210, 150, 30);
        editusername.setForeground(Color.white);
        editpanel.add(editusername);

        JCheckBox editusernamebox = new JCheckBox();
        editusernamebox.setBounds(246, 220, 20, 20);
        editusernamebox.setBackground(secondarycolor);
        editpanel.add(editusernamebox);

        JTextField editusernametext = new JTextField();
        editusernametext.setBounds(23, 245, 245, 40);
        editusernametext.setBackground(secondarycolor);
        editusernametext.setForeground(Color.white);
        editusernametext.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1), new EmptyBorder(0, 10, 0, 0)));
        editusernametext.setCaretColor(Color.white);
        editusernametext.setText("Enter your Username");
        editusernametext.setForeground(Color.GRAY);
        editusernametext.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            if (editusernametext.getText().equals("Enter your Username")) {
                editusernametext.setText("");
                editusernametext.setForeground(Color.WHITE);
            }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
            if (editusernametext.getText().isEmpty()) {
                editusernametext.setText("Enter your Username");
                editusernametext.setForeground(Color.GRAY);
            }
            }
        });
        editpanel.add(editusernametext);

        editusernamebox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (!editusernamebox.isSelected()) {
                editusernametext.setEnabled(false);
            } else {
                editusernametext.setEnabled(true);
            }
            }
        });

        editusernametext.setEnabled(false);

        JLabel editEmail = new JLabel("Email");
        editEmail.setBounds(23, 280, 150, 30);
        editEmail.setForeground(Color.white);
        editpanel.add(editEmail);

        JCheckBox editemailnamebox = new JCheckBox();
        editemailnamebox.setBounds(246, 290, 20, 20);
        editemailnamebox.setBackground(secondarycolor);
        editpanel.add(editemailnamebox);

        JTextField editemailtext = new JTextField();
        editemailtext.setBounds(23, 315, 245, 40);
        editemailtext.setBackground(secondarycolor);
        editemailtext.setForeground(Color.white);
        editemailtext.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1), new EmptyBorder(0, 10, 0, 0)));
        editemailtext.setCaretColor(Color.white);
        editemailtext.setText("Enter your Email");
        editemailtext.setForeground(Color.GRAY);
        editemailtext.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            if (editemailtext.getText().equals("Enter your Email")) {
                editemailtext.setText("");
                editemailtext.setForeground(Color.WHITE);
            }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
            if (editemailtext.getText().isEmpty()) {
                editemailtext.setText("Enter your Email");
                editemailtext.setForeground(Color.GRAY);
            }
            }
        });
        editpanel.add(editemailtext);

        editemailnamebox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (!editemailnamebox.isSelected()) {
                editemailtext.setEnabled(false);
            } else {
                editemailtext.setEnabled(true);
            }
            }
        });

        editemailtext.setEnabled(false);

        JLabel editAge = new JLabel("Age");
        editAge.setBounds(23, 350, 150, 30);
        editAge.setForeground(Color.white);
        editpanel.add(editAge);

        JCheckBox editAgebox = new JCheckBox();
        editAgebox.setBounds(246, 360, 20, 20);
        editAgebox.setBackground(secondarycolor);
        editpanel.add(editAgebox);

        JTextField editAgetext = new JTextField();
        editAgetext.setBounds(23, 385, 245, 40);
        editAgetext.setBackground(secondarycolor);
        editAgetext.setForeground(Color.white);
        editAgetext.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1), new EmptyBorder(0, 10, 0, 0)));
        editAgetext.setCaretColor(Color.white);
        editAgetext.setText("Enter your Age");
        editAgetext.setForeground(Color.GRAY);
        editAgetext.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            if (editAgetext.getText().equals("Enter your Age")) {
                editAgetext.setText("");
                editAgetext.setForeground(Color.WHITE);
            }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
            if (editAgetext.getText().isEmpty()) {
                editAgetext.setText("Enter your Age");
                editAgetext.setForeground(Color.GRAY);
            }
            }
        });
        editpanel.add(editAgetext);

        editAgebox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (!editAgebox.isSelected()) {
                editAgetext.setEnabled(false);
            } else {
                editAgetext.setEnabled(true);
            }
            }
        });

        editAgetext.setEnabled(false);

        JLabel editPhonenum = new JLabel("Phone Number");
        editPhonenum.setBounds(23, 420, 150, 30);
        editPhonenum.setForeground(Color.white);
        editpanel.add(editPhonenum);

        JCheckBox editnumbox = new JCheckBox();
        editnumbox.setBounds(246, 440, 20, 20);
        editnumbox.setBackground(secondarycolor);

        editpanel.add(editnumbox);

        JTextField editnumtext = new JTextField();
        editnumtext.setBounds(23, 465, 245, 40);
        editnumtext.setBackground(secondarycolor);
        editnumtext.setForeground(Color.white);
        editnumtext.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1), new EmptyBorder(0, 10, 0, 0)));
        editnumtext.setCaretColor(Color.white);
        editnumtext.setText("Enter your Phone number");
        editnumtext.setForeground(Color.GRAY);
        editnumtext.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            if (editnumtext.getText().equals("Enter your Phone number")) {
                editnumtext.setText("");
                editnumtext.setForeground(Color.WHITE);
            }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
            if (editnumtext.getText().isEmpty()) {
                editnumtext.setText("Enter your Phone number");
                editnumtext.setForeground(Color.GRAY);
            }
            }
        });
        editpanel.add(editnumtext);
        editnumbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (!editnumbox.isSelected()) {
                editnumtext.setEnabled(false);
            } else {
                editnumtext.setEnabled(true);
            }
            }
        });

        editnumtext.setEnabled(editnumbox.isSelected());


        JButton edit = new JButton("Edit Profile");
        edit.setBounds(23, 550, 242, 40);
        edit.setBorder(null);
        edit.setFocusPainted(false);
        edit.setBackground(Color.white);
        edit.setForeground(Color.black);
        int userID = userid; // Assuming you have the user ID available
            edit.addActionListener(e -> {
                // Check which fields are selected for editing
                boolean isFirstNameSelected = editFirstNameBox.isSelected();
                boolean isLastNameSelected = editLastNameBox.isSelected();
                boolean isUsernameSelected = editusernamebox.isSelected();
                boolean isEmailSelected = editemailnamebox.isSelected();
                boolean isAgeSelected = editAgebox.isSelected();
                boolean isPhoneNumberSelected = editnumbox.isSelected();

                // Update the selected fields
                if (isFirstNameSelected) {
                    String newFirstName = editFirstNameText.getText();
                    if (!newFirstName.equals("Enter your First Name") && !newFirstName.isEmpty()) {
                        ClientManager.updateClientFirstName(userID, newFirstName); // Update first name
                    }
                }

                if (isLastNameSelected) {
                    String newLastName = editLastNameText.getText();
                    if (!newLastName.equals("Enter your Last Name") && !newLastName.isEmpty()) {
                        ClientManager.updateClientLastName(userID, newLastName); // Update last name
                    }
                }

                if (isUsernameSelected) {
                    String newUsername = editusernametext.getText();
                    if (!newUsername.equals("Enter your Username") && !newUsername.isEmpty()) {
                        ClientManager.updateClientUsername(userID, newUsername); // Update username
                    }
                }

                if (isEmailSelected) {
                    String newEmail = editemailtext.getText();
                    if (!newEmail.equals("Enter your Email") && !newEmail.isEmpty()) {
                        ClientManager.updateClientEmail(userID, newEmail); // Update email
                    }
                }

                if (isAgeSelected) {
                    try {
                        int newAge = Integer.parseInt(editAgetext.getText());
                        ClientManager.updateClientAge(userID, newAge); // Update age
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid age entered!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (isPhoneNumberSelected) {
                    String newPhoneNumber = editnumtext.getText();
                    if (!newPhoneNumber.equals("Enter your Phone number") && !newPhoneNumber.isEmpty()) {
                        ClientManager.updateClientPhoneNumber(userID, newPhoneNumber); // Update phone number
                    }
                }
            
                // Refresh the profile panel
                try {
                    name.setText(ClientManager.getuserfirstname(userID) + " " + ClientManager.getuserlastname(userID));
                    username.setText("Username: " + ClientManager.getusersname(userID));
                    email.setText("Email: " + ClientManager.getuseremail(userID));
                    age.setText("Age: " + ClientManager.getuserage(userID));
                    phonenum.setText("Phone Number: " + ClientManager.getuserphone(userID));
                    curbalance.setText("Current Balance: " + ClientManager.getuserbalance(userID));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error refreshing profile data!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            
                // Revalidate and repaint the panel
                rightpanel.revalidate();
                rightpanel.repaint();
            // Animation to slide the panel out
            javax.swing.Timer timer = new javax.swing.Timer(5, new ActionListener() {
                int x = 694;
        
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (x < 1200) {
                        x += 20;
                        editpanel.setBounds(x, 0, 291, 750);
        
                        DeleteAcc.setEnabled(true);
                        removeimg.setEnabled(true);
                        Editebalance.setEnabled(true);
                        changeimg.setEnabled(true);
        
                        rightpanel.revalidate();
                        rightpanel.repaint();
                    } else {
                        ((javax.swing.Timer) e.getSource()).stop();
                    }
                }
            });
            timer.start();
        });
        editpanel.add(edit);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(23, 620, 242, 40);
        cancel.setBorder(null);
        cancel.setFocusPainted(false);
        cancel.setBackground(Color.white);
        cancel.setForeground(Color.black);
        cancel.addActionListener(e -> {

            // Animation to slide the panel out
            javax.swing.Timer timer = new javax.swing.Timer(5, new ActionListener() {
                int x = 694;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (x < 1200) {
                        x += 20;
                        editpanel.setBounds(x, 0, 291, 750);

                        // Enable the buttons instead of adding listeners again
                        DeleteAcc.setEnabled(true);
                        removeimg.setEnabled(true);
                        Editebalance.setEnabled(true);
                        changeimg.setEnabled(true);
                        
                        rightpanel.revalidate();
                        rightpanel.repaint();
                    } else {
                        ((javax.swing.Timer) e.getSource()).stop();
                    }
                }
            });
            timer.start();
        });

        editpanel.add(cancel);

        JButton changepersinfo = new JButton("Change Personal Information");
        changepersinfo.setBounds(190, 65, 298, 40);
        changepersinfo.setBorder(null);
        changepersinfo.setFocusPainted(false);
        changepersinfo.setBackground(Color.white);
        changepersinfo.setForeground(Color.black);
        changepersinfo.addActionListener(e -> {

            // Animation to slide the panel in
            javax.swing.Timer slideTimer = new javax.swing.Timer(5, new ActionListener() {
                int x = 1200;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (x > 694) {
                        x -= 20;
                        editpanel.setBounds(x, 0, 291, 750);

                        DeleteAcc.setEnabled(false);
                        removeimg.setEnabled(false);
                        Editebalance.setEnabled(false);
                        changeimg.setEnabled(false);

                        rightpanel.revalidate();
                        rightpanel.repaint();
                    } else {
                        ((javax.swing.Timer) e.getSource()).stop();
                    }
                }
            });
            slideTimer.start();
        });
        Accountpanel.add(changepersinfo);

        ImageIcon image = new ImageIcon("Poo2-TRY-\\All\\bookingTICKET\\images\\image.png");
        JLabel imagelabel = new JLabel(image);
        imagelabel.setBounds(0, 0, 900, 750);
        image.setImage(image.getImage().getScaledInstance(imagelabel.getWidth(), imagelabel.getHeight(), Image.SCALE_SMOOTH));
        rightpanel.add(imagelabel);

        profile.add(rightpanel);
        return profile;
    }



    public JPanel CreateAccountAdminPanel() {
        JPanel AccountAdminPanel = new JPanel();

        return AccountAdminPanel;
    }


    public JPanel CreateBuyPanel(Movie movie){
        JPanel BuyPanel = new JPanel();
        BuyPanel.setLayout(null);
        BuyPanel.setBounds(0, 0, 1200, 750);
        BuyPanel.setBackground(new Color(0x121213));

        //Left panel ----------------------------------------------------------------
        RoundedPanel LeftPanel = new RoundedPanel(35); 
        LeftPanel.setBounds(30,30, 150, 650);
        LeftPanel.setBackground(new Color(0x212121));
        LeftPanel.setLayout(null);

        BuyPanel.add(LeftPanel);

        JPanel StraightLine = new JPanel();
        StraightLine.setBounds(220, 80, 930, 5);
        StraightLine.setBackground(new Color(0x313131));

        BuyPanel.add(StraightLine);

        RoundedPanel SearchBar = new RoundedPanel(35);
        SearchBar.setBounds(250, 30, 800, 30);
        SearchBar.setBackground(new Color(0x212121));
        SearchBar.setLayout(null);

        BuyPanel.add(SearchBar);

        JTextField SearchField = new JTextField();
        SearchField.setBounds(10, 2, 800, 25);
        SearchField.setFont(new Font("Inter", Font.BOLD, 15));
        SearchField.setForeground(Color.white);
        SearchField.setCaretColor(Color.white);
        SearchField.setOpaque(false);
        SearchField.setBorder(null);

        TextfieldBehave(SearchField, "Search for a movie");

        SearchBar.add(SearchField);

        JButton Filter = new JButton();
        Filter.setBounds(200, 32, 30, 30);
        Filter.setFocusPainted(false);
        Filter.setBorder(null);
        Filter.setBackground(new Color(0x212121));
        Filter.setUI(new RoundButtonUI(Color.black));
        Filter.addActionListener(e -> {
            // Open movie filter dialog
        });
        BuyPanel.add(Filter);

        JButton History = new JButton();
        History.setBounds(1070, 29, 30, 30);
        History.setFocusPainted(false);
        History.setBorder(null);
        History.setBackground(new Color(0x212121));
        History.setUI(new RoundButtonUI(Color.black));
        History.addActionListener(e -> {
            // Open movie History dialog
        });
        BuyPanel.add(History);

        JButton Account = new JButton();
        Account.setBounds(1110, 29, 50, 30);
        Account.setFocusPainted(false);
        Account.setBorder(null);
        Account.setBackground(new Color(0x515151));
        Account.setUI(new RoundButtonUI(Color.black));
        Account.addActionListener(e -> {
            // Open movie Account dialog
            MainCardLayout.show(MainPanel, "Account");
        });
        BuyPanel.add(Account);

        JPanel ContentPanel = new JPanel();
        ContentPanel.setLayout(null);
        ContentPanel.setOpaque(true);
        ContentPanel.setBackground(new Color(0x121213));
        ContentPanel.setPreferredSize(new Dimension(919, 1250));

        JScrollPane scrollPane = new JScrollPane(ContentPanel);
        scrollPane.setBounds(227, 85, 919, 750);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);

        ContentPanel.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();// had scroll par rapport l y
            int notches = e.getWheelRotation();
            int currentValue = verticalScrollBar.getValue();
            int scrollAmount = 30; // Adjust scroll speed
            verticalScrollBar.setValue(currentValue + (notches * scrollAmount));
        });

        BuyPanel.add(scrollPane);

        RoundedPanel FilmBanner = new RoundedPanel(35);
        FilmBanner.setBounds(0, 20, 915, 300);
        FilmBanner.setBackground(new Color(0x212121));
        FilmBanner.setLayout(null);

        ContentPanel.add(FilmBanner);

        RoundedPanel InfoPanel = new RoundedPanel(35);
        InfoPanel.setBounds(0, 550, 650, 275);
        InfoPanel.setBackground(new Color(0x121213));
        InfoPanel.setLayout(null);
        InfoPanel.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));

        ContentPanel.add(InfoPanel);

        JLabel FilmTitle = new JLabel("Movie Title");
        FilmTitle.setBounds(0, 335, 900, 50);
        FilmTitle.setForeground(Color.white);
        FilmTitle.setFont(new Font("Inter", Font.BOLD, 36));

        ContentPanel.add(FilmTitle);

        JLabel Description = new JLabel();
        Description.setBounds(0, 350, 1000, 250);
        Description.setForeground(Color.white);
        Description.setFont(new Font("Inter", Font.PLAIN, 15));

        ContentPanel.add(Description);

        JLabel FilmRating = new JLabel("Rating: 8.5/10");
        FilmRating.setBounds(35, 25, 600, 25);
        FilmRating.setForeground(Color.white);
        FilmRating.setFont(new Font("Inter", Font.BOLD, 16));

        InfoPanel.add(FilmRating);

        JLabel AgeRating = new JLabel("Age rating : PG16");
        AgeRating.setBounds(400, 25, 600, 25);
        AgeRating.setForeground(Color.white);
        AgeRating.setFont(new Font("Inter", Font.BOLD, 16));

        InfoPanel.add(AgeRating);

        JLabel Duration = new JLabel("duration : 1h30");
        Duration.setBounds(400, 75, 600, 25);
        Duration.setForeground(Color.white);
        Duration.setFont(new Font("Inter", Font.BOLD, 16));

        InfoPanel.add(Duration);

        JLabel FilmReleaseDate = new JLabel("Release Date: 12/05/2022");
        FilmReleaseDate.setBounds(35, 75, 600, 25);
        FilmReleaseDate.setForeground(Color.white);
        FilmReleaseDate.setFont(new Font("Inter", Font.BOLD, 16));

        InfoPanel.add(FilmReleaseDate);

        JLabel FilmGenre = new JLabel("Genre: Drama, Comedy");
        FilmGenre.setBounds(35, 125, 600, 25);
        FilmGenre.setForeground(Color.white);
        FilmGenre.setFont(new Font("Inter", Font.BOLD, 16));

        InfoPanel.add(FilmGenre);

        JLabel FilmDirector = new JLabel("Director: John Doe");
        FilmDirector.setBounds(35, 175, 600, 25);
        FilmDirector.setForeground(Color.white);
        FilmDirector.setFont(new Font("Inter", Font.BOLD, 16));

        InfoPanel.add(FilmDirector);

        JLabel FilmCast = new JLabel("Cast: John Doe, Jane Doe");
        FilmCast.setBounds(35, 225, 600, 25);
        FilmCast.setForeground(Color.white);
        FilmCast.setFont(new Font("Inter", Font.BOLD, 16));

        InfoPanel.add(FilmCast);

        JButton Buy = new JButton();
        Buy.setBounds(0, 850, 175, 40);
        Buy.setText("Buy Now");
        Buy.setFont(new Font("Inter", Font.BOLD, 15));
        Buy.setBackground(Color.white);
        Buy.setForeground(Color.black);
        Buy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Buy.setUI(new RoundButtonUI(Color.white));
        Buy.addActionListener(e -> {
            // handle buy button click
        });

        ContentPanel.add(Buy);

        JButton WhatchTrailler = new JButton();
        WhatchTrailler.setBounds(200, 850, 250, 40);
        WhatchTrailler.setText("Whatch Trailler Now");
        WhatchTrailler.setFont(new Font("Inter", Font.BOLD, 15));
        WhatchTrailler.setBackground(Color.black);
        WhatchTrailler.setForeground(Color.white);
        WhatchTrailler.setCursor(new Cursor(Cursor.HAND_CURSOR));
        WhatchTrailler.setUI(new RoundButtonUI(Color.black));
        WhatchTrailler.addActionListener(e -> {
            // handle WhatchTrailler button click
        });

        ContentPanel.add(WhatchTrailler);

        return BuyPanel;
    }


    public JPanel createInterfaceAdminPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1200, 750);
        panel.setBackground(Color.blue);



//---------------------------left panel------------------------------

        JPanel LeftPanel = new JPanel();
        LeftPanel.setLayout(null);
        LeftPanel.setBounds(0, 0, 170, 750);
        LeftPanel.setBackground(new Color(30 , 30 ,30));
        panel.add(LeftPanel);

        JLabel LogoName = new JLabel("INEMATIC");
        LogoName.setBounds(55, 6, 120, 50);
        LogoName.setForeground(Color.WHITE);
        LogoName.setFont(new Font("Bebas Neue", Font.BOLD, 18));
        LeftPanel.add(LogoName);
        JLabel CLogored = new JLabel("C");
        CLogored.setBounds(41, 21, 30, 20);
        CLogored.setForeground(Color.red);
        CLogored.setFont(new Font("Bebas Neue", Font.BOLD, 18));
        LeftPanel.add(CLogored);





        JSeparator separator1 = new JSeparator();
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setBackground(Color.white);
        separator1.setForeground(Color.white);
        separator1.setBounds(168, 0, 5, 750);
        LeftPanel.add(separator1);

 //--------------------------Middle Panel--------------------------------


        JPanel MiddlePanel = new JPanel();
        MiddlePanel.setLayout(null);
        MiddlePanel.setBounds(170, 0, 1200, 750);
        MiddlePanel.setBackground(Color.red);
        panel.add(MiddlePanel);


//---------------------------about Home--------------------------------
        JPanel HomeDashboard = new JPanel();
        HomeDashboard.setLayout(null);
        HomeDashboard.setBounds(0, 0, 1400, 750);
        HomeDashboard.setBackground(new Color(30, 30, 30));
        MiddlePanel.add(HomeDashboard);

        // Top three rounded panels for 
        int panelWidth = 280;
        int panelHeight = 150;
        int spacing = 20;
        int startX = 60;
        int startY = 80;

        // First panel: Number of Clients
        RoundedPanel clientsPanel = new RoundedPanel(30);
        clientsPanel.setBounds(startX, startY, panelWidth, panelHeight);
        clientsPanel.setBackground(new Color(50, 50, 50));
        clientsPanel.setLayout(null);
        HomeDashboard.add(clientsPanel);

        JLabel clientsTitle = new JLabel("Number of Clients");
        clientsTitle.setBounds(20, 20, 200, 30);
        clientsTitle.setForeground(Color.WHITE);
        clientsTitle.setFont(new Font("Bebas Neue", Font.BOLD, 18));
        clientsPanel.add(clientsTitle);

        JLabel clientsCount = new JLabel(String.valueOf(ClientManager.numberofusers()));
        clientsCount.setBounds(20, 60, 200, 50);
        clientsCount.setForeground(Color.WHITE);
        clientsCount.setFont(new Font("Bebas Neue", Font.BOLD, 40));
        clientsPanel.add(clientsCount);

        // Second panel: Number of Movies
        RoundedPanel moviesPanel = new RoundedPanel(30);
        moviesPanel.setBounds(startX + panelWidth + spacing, startY, panelWidth, panelHeight);
        moviesPanel.setBackground(new Color(50, 50, 50));
        moviesPanel.setLayout(null);
        HomeDashboard.add(moviesPanel);

        JLabel moviesTitle = new JLabel("Number of Movies");
        moviesTitle.setBounds(20, 20, 200, 30);
        moviesTitle.setForeground(Color.WHITE);
        moviesTitle.setFont(new Font("Bebas Neue", Font.BOLD, 18));
        moviesPanel.add(moviesTitle);

        JLabel moviesCount = new JLabel(String.valueOf(MovieManager.numberofmovies()));
        moviesCount.setBounds(20, 60, 200, 50);
        moviesCount.setForeground(Color.WHITE);
        moviesCount.setFont(new Font("Bebas Neue", Font.BOLD, 40));
        moviesPanel.add(moviesCount);

        // Third panel: Number of Theaters
        RoundedPanel theatersPanel = new RoundedPanel(30);
        theatersPanel.setBounds(startX + 2 * (panelWidth + spacing), startY, panelWidth, panelHeight);
        theatersPanel.setBackground(new Color(50, 50, 50));
        theatersPanel.setLayout(null);
        HomeDashboard.add(theatersPanel);

        JLabel theatersTitle = new JLabel("Number of Theaters");
        theatersTitle.setBounds(20, 20, 200, 30);
        theatersTitle.setForeground(Color.WHITE);
        theatersTitle.setFont(new Font("Bebas Neue", Font.BOLD, 18));
        theatersPanel.add(theatersTitle);

        JLabel theatersCount = new JLabel(String.valueOf(TheaterManager.numberOfTheaters()));
        theatersCount.setBounds(20, 60, 200, 50);
        theatersCount.setForeground(Color.WHITE);
        theatersCount.setFont(new Font("Bebas Neue", Font.BOLD, 40));
        theatersPanel.add(theatersCount);

        // Big panel for quick actions
        int bigPanelWidth = panelWidth * 3 + spacing * 2;
        int bigPanelHeight = 400;
        int bigPanelY = startY + panelHeight + spacing;

        RoundedPanel quickActionsPanel = new RoundedPanel(30);
        quickActionsPanel.setBounds(startX, bigPanelY, bigPanelWidth, bigPanelHeight);
        quickActionsPanel.setBackground(new Color(40, 40, 40));
        quickActionsPanel.setLayout(null);
        HomeDashboard.add(quickActionsPanel);

        JLabel quickActionsTitle = new JLabel("Quick Actions");
        quickActionsTitle.setBounds(20, 20, 300, 30);
        quickActionsTitle.setForeground(Color.WHITE);
        quickActionsTitle.setFont(new Font("Bebas Neue", Font.BOLD, 20));
        quickActionsPanel.add(quickActionsTitle);

        // Add Broadcast Button
        RoundedButton addBroadcastButton = new RoundedButton("Add Broadcast", 20);
        addBroadcastButton.setBounds(50, 80, 200, 50);
        addBroadcastButton.setForeground(Color.WHITE);
        addBroadcastButton.setBackground(new Color(60, 120, 180));
        addBroadcastButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        // addBroadcastButton.addActionListener(e -> {
        //     JPanel addTheaterModal = new JPanel();
        //     addTheaterModal.setLayout(null);
        //     addTheaterModal.setPreferredSize(new Dimension(1000, 500)); // Adjust size to make it smaller
        //     addTheaterModal.setBackground(new Color(30, 30, 30));
            
        //     // Title for the panel
        //     JLabel panelBroadcast = new JLabel("Add New Broadcast");
        //     panelBroadcast.setBounds(50, 10, 200, 30);
        //     panelBroadcast.setForeground(Color.WHITE);
        //     panelBroadcast.setFont(new Font("Segoe UI", Font.BOLD, 22));
        //     addTheaterModal.add(panelBroadcast);

        //     // Theater choice for broad
        //     JLabel Theater = new JLabel("Theater Name");
        //     Theater.setBounds(50, 50, 100, 30);
        //     Theater.setForeground(Color.WHITE);
        //     // Replace with a String array of theater names, e.g., from TheaterManager or database
        //     JComboBox<String> Theaterchoice = new JComboBox<>(TheaterManager.getAllTheaterNames().toArray(new String[0]));
        //     Theaterchoice.addActionListener(evt -> {
        //         JSpinner DateSpinner = new JSpinner(new SpinnerDateModel());
        //         DateSpinner.setBounds(50, 160, 300, 30); // Corrected y position for DateSpinner
        //         DateSpinner.setBackground(new Color(50, 50, 50));
        //         DateSpinner.setForeground(Color.WHITE);
        //         LocalDate selectedDate = ((SpinnerDateModel) DateSpinner.getModel()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //         ArrayList<LocalDate> availableDates = TheaterManager.getAvailableDatesByTheaterName((String) Theaterchoice.getSelectedItem());
        //         // Update the DateSpinner with available dates
        //     });
        //     Theaterchoice.setBounds(50, 80, 300, 30);
        //     Theaterchoice.setBackground(new Color(50, 50, 50));
        //     Theaterchoice.setForeground(Color.WHITE);
        //     Theaterchoice.setBorder(BorderFactory.createCompoundBorder(
        //         BorderFactory.createLineBorder(Color.GRAY),
        //         BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
        //     ));
        //     addTheaterModal.add(Theater);
        //     addTheaterModal.add(Theaterchoice);
            
        //     // Movie choice for broad
        //     JLabel MovieLabel = new JLabel("Movie Title");
        //     MovieLabel.setBounds(50, 210, 150, 30); // Adjusted width for label
        //     MovieLabel.setForeground(Color.WHITE);
        //     JComboBox<String> MovieChoice = new JComboBox<>(MovieManager.getAllMoviesNames().toArray(new String[0]));
        //     MovieChoice.setBounds(50, 240, 300, 30); // Corrected y position for MovieChoice
        //     MovieChoice.setBackground(new Color(50, 50, 50));
        //     MovieChoice.setForeground(Color.WHITE);
        //     addTheaterModal.add(MovieLabel);
        //     addTheaterModal.add(MovieChoice);

        //     RoundedButton addBroadcastButton2 = new RoundedButton("Add Broadcast", 5);
        //     addBroadcastButton2.setBounds(380, 450, 130, 30); // Adjusted position
        //     addBroadcastButton2.setForeground(Color.BLACK);
        //     addBroadcastButton2.setBackground(Color.WHITE);
        //     addBroadcastButton2.addActionListener(event -> {
        //         LocalDate Date = DateSpinner.getValue();
        //         Theater theater = TheaterManager.getTheaterByNaTheaterchoice.getSelectedItem());
        //         Movie movie = MovieManager.getMovieByTitle((String) MovieChoice.getSelectedItem());
        //         try{
        //             BroadcastManager.addBroadcast(theater, movie, Date);
        //             JOptionPane.showMessageDialog(null, "Broadcast added successfully!");
        //         } catch (SQLException ex) {
        //             ex.printStackTrace();
        //             JOptionPane.showMessageDialog(null, "Failed to add broadcast. See console for details.", "Error", JOptionPane.ERROR_MESSAGE);
        //         }

        //         if (!Date.isEmpty() && theater != null && movie != null) {
        //             if (Date <= (LocalDate.now() + 7)) {
        //                 JOptionPane.showMessageDialog(null, "The broadcast date must be within the next 7 days!", "Error", JOptionPane.ERROR_MESSAGE);
        //                 return;
        //             }

        //             BroadcastManager broadcastManager = new BroadcastManager();
        //             boolean addedBroadcast = broadcastManager.addBroadcast(theater, movie, Date);

        //             if (addedBroadcast) {
        //                 JOptionPane.showMessageDialog(null, "Broadcast added successfully!");
        //                 SwingUtilities.getWindowAncestor(addTheaterModal).dispose();
        //             } else {
        //                 JOptionPane.showMessageDialog(null, "Failed to add broadcast. See console for details.", "Error", JOptionPane.ERROR_MESSAGE);
        //             }
        //         } else {
        //             JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        //         }
        //     });    
        //     addTheaterModal.add(addBroadcastButton2);
        //     addTheaterModal.setVisible(true);
              
        quickActionsPanel.add(addBroadcastButton);

        // Add Movie Button
        RoundedButton addMovieButton = new RoundedButton("Add Movie", 20);
        addMovieButton.setBounds(300, 80, 200, 50);
        addMovieButton.setForeground(Color.WHITE);
        addMovieButton.setBackground(new Color(80, 160, 100));
        addMovieButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addMovieButton.addActionListener(e -> {
        
                // Create the Add Movie panel directly
                JPanel addMovieModal = new JPanel();
                addMovieModal.setLayout(null);
                addMovieModal.setPreferredSize(new Dimension(1000, 500)); // Adjust size to make it smaller
                addMovieModal.setBackground(new Color(30, 30, 30));
            
                // Title for the panel
                JLabel panelTitle = new JLabel("Add New Movie");
                panelTitle.setBounds(50, 10, 200, 30);
                panelTitle.setForeground(Color.WHITE);
                panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
                addMovieModal.add(panelTitle);
            
                // Title Label and TextField
                JLabel titleLabel = new JLabel("Title");
                titleLabel.setBounds(50, 50, 100, 30);
                titleLabel.setForeground(Color.WHITE);
                JTextField titleTextField = new JTextField();
                titleTextField.setBounds(50, 80, 300, 30);
                titleTextField.setBackground(new Color(50, 50, 50));
                titleTextField.setForeground(Color.WHITE);
                titleTextField.setCaretColor(Color.WHITE);
                titleTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                addMovieModal.add(titleLabel);
                addMovieModal.add(titleTextField);
            
                // Genre Label and TextField
                JLabel genreLabel = new JLabel("Genre");
                genreLabel.setBounds(50, 130, 100, 30); // Same x as Title, different y
                genreLabel.setForeground(Color.WHITE);
                JComboBox<Movie.MovieGenre> genreComboBox = new JComboBox<>(Movie.MovieGenre.values());
                genreComboBox.setBounds(50, 160, 300, 30); // Same x as Title, different y
                genreComboBox.setBackground(new Color(50, 50, 50));
                genreComboBox.setForeground(Color.WHITE);
                addMovieModal.add(genreLabel);
                addMovieModal.add(genreComboBox);
            
                // Age Rating Label and TextField
                JLabel ageRatingLabel = new JLabel("Age Rating");
                ageRatingLabel.setBounds(50, 210, 100, 30); // Same x as Title, different y
                ageRatingLabel.setForeground(Color.WHITE);
                JComboBox<Movie.MovieAgeRating> ageRatingComboBox = new JComboBox<>(Movie.MovieAgeRating.values());
                ageRatingComboBox.setBounds(50, 240, 300, 30); // Same x as Title, different y
                ageRatingComboBox.setBackground(new Color(50, 50, 50));
                ageRatingComboBox.setForeground(Color.WHITE);
                addMovieModal.add(ageRatingLabel);
                addMovieModal.add(ageRatingComboBox);
            
                // Rating Label and TextField
                JLabel ratingLabel = new JLabel("Rating (0.0 - 10.0)");
                ratingLabel.setBounds(50, 300, 150, 30);
                ratingLabel.setForeground(Color.WHITE);
                JTextField ratingTextField = new JTextField();
                ratingTextField.setBounds(50, 330, 300, 30);
                ratingTextField.setBackground(new Color(50, 50, 50));
                ratingTextField.setForeground(Color.WHITE);
                ratingTextField.setCaretColor(Color.WHITE);
                ratingTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                addMovieModal.add(ratingLabel);
                addMovieModal.add(ratingTextField);
            
                // Trailer URL Label and TextField
                JLabel trailerUrlLabel = new JLabel("Trailer URL");
                trailerUrlLabel.setBounds(380, 50, 100, 30);
                trailerUrlLabel.setForeground(Color.WHITE);
                JTextField trailerUrlTextField = new JTextField();
                trailerUrlTextField.setBounds(380, 80, 300, 30);
                trailerUrlTextField.setBackground(new Color(50, 50, 50));
                trailerUrlTextField.setForeground(Color.WHITE);
                trailerUrlTextField.setCaretColor(Color.WHITE);
                trailerUrlTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                addMovieModal.add(trailerUrlLabel);
                addMovieModal.add(trailerUrlTextField);
            
                // Release Date Label and Date Picker
                JLabel releaseDateLabel = new JLabel("Release Date");
                releaseDateLabel.setBounds(380, 130, 100, 30);
                releaseDateLabel.setForeground(Color.WHITE);
                JSpinner releaseDateSpinner = new JSpinner(new SpinnerDateModel());
                releaseDateSpinner.setBounds(380, 160, 300, 30);
                releaseDateSpinner.setEditor(new JSpinner.DateEditor(releaseDateSpinner, "yyyy-MM-dd"));
                releaseDateSpinner.setBackground(new Color(50, 50, 50));
                releaseDateSpinner.setForeground(Color.WHITE);
                addMovieModal.add(releaseDateLabel);
                addMovieModal.add(releaseDateSpinner);
            
                // Director Label and TextField
                JLabel directorLabel = new JLabel("Director");
                directorLabel.setBounds(50, 380, 100, 30);
                directorLabel.setForeground(Color.WHITE);
                JTextField directorTextField = new JTextField();
                directorTextField.setBounds(50, 410, 300, 30);
                directorTextField.setBackground(new Color(50, 50, 50));
                directorTextField.setForeground(Color.WHITE);
                directorTextField.setCaretColor(Color.WHITE);
                directorTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                addMovieModal.add(directorLabel);
                addMovieModal.add(directorTextField);
            
                // Description Label and TextArea
                JLabel descriptionLabel = new JLabel("Description");
                descriptionLabel.setBounds(380, 210, 100, 30);
                descriptionLabel.setForeground(Color.WHITE);
                JTextArea descriptionTextArea = new JTextArea();
                descriptionTextArea.setBounds(380, 240, 300, 100);
                descriptionTextArea.setBackground(new Color(50, 50, 50));
                descriptionTextArea.setForeground(Color.WHITE);
                descriptionTextArea.setCaretColor(Color.WHITE);
                descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                descriptionTextArea.setLineWrap(true);
                descriptionTextArea.setWrapStyleWord(true);
                addMovieModal.add(descriptionLabel);
                addMovieModal.add(descriptionTextArea);
            
                // Image Panel
                JPanel imagePanel = new JPanel();
                imagePanel.setBounds(720, 130, 250, 300); // Adjusted position
                imagePanel.setBackground(new Color(50, 50, 50));
                imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                addMovieModal.add(imagePanel);
            
                // Choose Image Button
                JLabel imageLabelHolder = new JLabel();
                String[] imagePathHolder = new String[1]; // Use an array to hold the image path
                RoundedButton chooseImageButton = new RoundedButton("Choose Image", 5);
                chooseImageButton.setBounds(720, 60, 130, 30); // Moved to the top of the image panel
                chooseImageButton.setForeground(Color.BLACK);
                chooseImageButton.setBackground(Color.WHITE);
                chooseImageButton.addActionListener(event -> {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        imagePathHolder[0] = selectedFile.getAbsolutePath(); // Store the image path in the array
                        ImageIcon imageIcon = new ImageIcon(imagePathHolder[0]);
                        Image image = imageIcon.getImage().getScaledInstance(imagePanel.getWidth(), imagePanel.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon scaledImageIcon = new ImageIcon(image);
                        imageLabelHolder.setIcon(scaledImageIcon);
                        imagePanel.removeAll();
                        imagePanel.add(imageLabelHolder);
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    }
                });
                addMovieModal.add(chooseImageButton);
            
                // Add Movie Button
                RoundedButton addMovieButton2 = new RoundedButton("Add Movie", 5);
                addMovieButton2.setBounds(380, 450, 130, 30); // Adjusted position
                addMovieButton2.setForeground(Color.BLACK);
                addMovieButton2.setBackground(Color.WHITE);
                addMovieButton2.addActionListener(event -> {
                    String title = titleTextField.getText();
                    Movie.MovieGenre genre = (Movie.MovieGenre) genreComboBox.getSelectedItem();
                    Movie.MovieAgeRating ageRating = (Movie.MovieAgeRating) ageRatingComboBox.getSelectedItem();
                    String ratingText = ratingTextField.getText();
                    String trailerUrl = trailerUrlTextField.getText();
                    Date releaseDate = (Date) releaseDateSpinner.getValue();
                    String director = directorTextField.getText();
                    String description = descriptionTextArea.getText();
            
                    // Use imagePathHolder[0] to access the image path
                    if (!title.isEmpty() && genre != null && !ratingText.isEmpty() && !director.isEmpty() && !description.isEmpty() && imagePathHolder[0] != null) {
                        try {
                            float rating = Float.parseFloat(ratingText);
            
                            // Validate rating
                            if (rating < 0 || rating > 10) {
                                JOptionPane.showMessageDialog(null, "Rating must be between 0.0 and 10.0!", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
            
                            // Format rating to one decimal place
                            rating = Math.round(rating * 10) / 10.0f;
            
                            LocalDate localReleaseDate = new java.sql.Date(releaseDate.getTime()).toLocalDate();
            
                            // Call MovieManager.addMovieToDatabase
                            boolean added = MovieManager.addMovieToDatabase(title, genre, description, rating, ageRating, imagePathHolder[0], trailerUrl, localReleaseDate, director);
            
                            if (added) {
                                JOptionPane.showMessageDialog(null, "Movie added successfully!");
                                SwingUtilities.getWindowAncestor(addMovieModal).dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to add movie. See console for details.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
            
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid rating format!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill in all fields and choose an image!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                addMovieModal.add(addMovieButton2);
            
                // Cancel Button
                RoundedButton cancelButton = new RoundedButton("Cancel", 5);
                cancelButton.setBounds(550, 450, 130, 30); // Adjusted position
                cancelButton.setForeground(Color.BLACK);
                cancelButton.setBackground(Color.WHITE);
                cancelButton.addActionListener(event -> {
                    SwingUtilities.getWindowAncestor(addMovieModal).dispose();
                });
                addMovieModal.add(cancelButton);
            
                // Show the modal in a JDialog
                JDialog dialog = new JDialog();
                dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                dialog.setModal(true); // Prevent interaction with other windows
                dialog.getContentPane().add(addMovieModal);
                dialog.pack();
                dialog.setLocationRelativeTo(null); // Center on screen
                dialog.setVisible(true);
            });                
               
            
        
        quickActionsPanel.add(addMovieButton);

        // Add Theater Button
        RoundedButton addTheaterButton = new RoundedButton("Add Theater", 20);
        addTheaterButton.setBounds(550, 80, 200, 50);
        addTheaterButton.setForeground(Color.WHITE);
        addTheaterButton.setBackground(new Color(200, 100, 80));
        addTheaterButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addTheaterButton.addActionListener(e -> {
            JPanel addTheaterModal = new JPanel();
            addTheaterModal.setLayout(null);
            addTheaterModal.setPreferredSize(new Dimension(1000, 500)); // Adjust size to make it smaller
            addTheaterModal.setBackground(new Color(30, 30, 30));
            
            // Title for the panel
            JLabel panelTitle = new JLabel("Add New Theater");
            panelTitle.setBounds(50, 10, 200, 30);
            panelTitle.setForeground(Color.WHITE);
            panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
            addTheaterModal.add(panelTitle);
            
            // Title Label and TextField
            JLabel titleLabel = new JLabel("Name");
            titleLabel.setBounds(50, 50, 100, 30);
            titleLabel.setForeground(Color.WHITE);
            JTextField titleTextField = new JTextField();
            titleTextField.setBounds(50, 80, 300, 30);
            titleTextField.setBackground(new Color(50, 50, 50));
            titleTextField.setForeground(Color.WHITE);
            titleTextField.setCaretColor(Color.WHITE);
            titleTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            addTheaterModal.add(titleLabel);
            addTheaterModal.add(titleTextField);
            
            // VIPSeats Label and TextField
            JLabel VIPseatsLabel = new JLabel("Number of VIP Seats");
            VIPseatsLabel.setBounds(50, 130, 150, 30); // Adjusted width for label
            VIPseatsLabel.setForeground(Color.WHITE);
            JTextField VIPSeats = new JTextField();
            VIPSeats.setBounds(50, 160, 300, 30); // Corrected y position for VIPSeats
            VIPSeats.setBackground(new Color(50, 50, 50));
            VIPSeats.setForeground(Color.WHITE);
            addTheaterModal.add(VIPseatsLabel);
            addTheaterModal.add(VIPSeats);
            
            // NormalSeats Label and TextField
            JLabel NormalSeatsLabel = new JLabel("Number of Normal Seats");
            NormalSeatsLabel.setBounds(50, 210, 150, 30); // Adjusted width for label
            NormalSeatsLabel.setForeground(Color.WHITE);
            JTextField NormalSeats = new JTextField();
            NormalSeats.setBounds(50, 240, 300, 30); // Corrected y position for NormalSeats
            NormalSeats.setBackground(new Color(50, 50, 50));
            NormalSeats.setForeground(Color.WHITE);
            addTheaterModal.add(NormalSeatsLabel);
            addTheaterModal.add(NormalSeats);

            // Add Theater Button
            RoundedButton addTheaterButton2 = new RoundedButton("Add Theater", 5);
            addTheaterButton2.setBounds(380, 450, 130, 30); // Adjusted position
            addTheaterButton2.setForeground(Color.BLACK);
            addTheaterButton2.setBackground(Color.WHITE);
            addTheaterButton2.addActionListener(event -> {
                String Name = titleTextField.getText();
                Integer VipSeats = null;
                Integer normalSeats = null;
                try {
                    VipSeats = Integer.parseInt(VIPSeats.getText());
                    normalSeats = Integer.parseInt(NormalSeats.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid format for the seats!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Name.isEmpty() && VipSeats != null && normalSeats != null) {
                    if (VipSeats < 0 || normalSeats < 0) {
                        JOptionPane.showMessageDialog(null, "The number of VIP Seats and Normal Seats must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    TheaterManager theaterManager = new TheaterManager();
                    boolean addedTheater = theaterManager.addTheater(Name, normalSeats, VipSeats);

                    if (addedTheater) {
                        JOptionPane.showMessageDialog(null, "Theater added successfully!");
                        SwingUtilities.getWindowAncestor(addTheaterModal).dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add theater. See console for details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            addTheaterModal.add(addTheaterButton2);

            // Cancel Button
            RoundedButton cancelButton = new RoundedButton("Cancel", 5);
            cancelButton.setBounds(550, 450, 130, 30);
            cancelButton.setForeground(Color.BLACK);
            cancelButton.setBackground(Color.WHITE);
            cancelButton.addActionListener(event -> {
                SwingUtilities.getWindowAncestor(addTheaterModal).dispose();
            });
            addTheaterModal.add(cancelButton);

            // Show the modal in a JDialog
            JDialog dialog = new JDialog();
            dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            dialog.setModal(true); // Prevent interaction with other windows
            dialog.getContentPane().add(addTheaterModal);
            dialog.pack();
            dialog.setLocationRelativeTo(null); // Center on screen
            dialog.setVisible(true);
        });
        quickActionsPanel.add(addTheaterButton);

        // View Reports Button
        RoundedButton viewReportsButton = new RoundedButton("View Reports", 20);
        viewReportsButton.setBounds(50, 160, 200, 50);
        viewReportsButton.setForeground(Color.WHITE);
        viewReportsButton.setBackground(new Color(150, 75, 200));
        viewReportsButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        viewReportsButton.addActionListener(e -> {
            // Action to view reports
            JOptionPane.showMessageDialog(null, "View Reports clicked!");
        });
        quickActionsPanel.add(viewReportsButton);

        // Settings Button
        RoundedButton settingsButton = new RoundedButton("Settings", 20);
        settingsButton.setBounds(300, 160, 200, 50);
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setBackground(new Color(100, 100, 100));
        settingsButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        theaterDashboard = new JPanel();
        theaterDashboard.setLayout(null);
        theaterDashboard.setBounds(0, 0, 1400, 750);
        theaterDashboard.setBackground(new Color(30, 30, 30));
        MiddlePanel.add(theaterDashboard);
//--------------------------------about theater--------------
        JPanel theaterDashboard = new JPanel();
        theaterDashboard.setLayout(null);
        theaterDashboard.setBounds(0, 0, 1400, 750);
        theaterDashboard.setBackground(new Color(30, 30, 30));
        MiddlePanel.add(theaterDashboard);

        JLabel TheaterTitlePanellbl = new JLabel("Theater  Dashboard");
        TheaterTitlePanellbl.setBounds(30, 26, 300, 30);
        TheaterTitlePanellbl.setForeground(Color.WHITE);
        TheaterTitlePanellbl.setFont(new Font("Bebas Neue", Font.BOLD, 28));
        theaterDashboard.add(TheaterTitlePanellbl);

        JLabel Selectlbltheater = new JLabel("Select");
        Selectlbltheater.setBounds(30, 70, 100, 30);
        Selectlbltheater.setForeground(Color.WHITE);
        Selectlbltheater.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        theaterDashboard.add(Selectlbltheater);

        JLabel namelblTheater = new JLabel("Name");
        namelblTheater.setBounds(230, 70, 100, 30);
        namelblTheater.setForeground(Color.WHITE);
        namelblTheater.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        theaterDashboard.add(namelblTheater);

        JLabel NormalSeatslbl = new JLabel("Normal Seats");
        NormalSeatslbl.setBounds(430, 70, 100, 30);
        NormalSeatslbl.setForeground(Color.WHITE);
        NormalSeatslbl.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        theaterDashboard.add(NormalSeatslbl);

        JLabel Vipseatlbl = new JLabel("VIP Seats");
        Vipseatlbl.setBounds(630, 70, 100, 30);
        Vipseatlbl.setForeground(Color.WHITE);
        Vipseatlbl.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        theaterDashboard.add(Vipseatlbl);

        JSeparator separatorhorTheater = new JSeparator();
        separatorhorTheater.setOrientation(SwingConstants.HORIZONTAL);
        separatorhorTheater.setBackground(Color.white);
        separatorhorTheater.setForeground(Color.white);
        separatorhorTheater.setBounds(21, 105, 720, 1);
        theaterDashboard.add(separatorhorTheater);

        JPanel TheaterPanelList = new JPanel();
        TheaterPanelList.setLayout(null);
        TheaterPanelList.setBounds(30, 130, 700, 440);
        TheaterPanelList.setBackground(new Color(30, 30, 30));

        JPanel contentPanelTheaterList = new JPanel();
        contentPanelTheaterList.setLayout(null);
        contentPanelTheaterList.setBackground(new Color(30, 30, 30));

        int totalHeightTheaterList = clientManager.clients.size() * 50;
        contentPanelTheaterList.setPreferredSize(new Dimension(650, Math.max(500, totalHeightTheaterList)));

        ArrayList<JCheckBox> checkBoxesTheaterList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM theaters")) {

            int rowIndex = 0;
            while (rs.next()) {
            JPanel TheaterListRow = new JPanel();
            TheaterListRow.setLayout(null);
            TheaterListRow.setBounds(0, rowIndex * 50, 700, 40);
            TheaterListRow.setBackground(new Color(30, 30, 30));

            JCheckBox selectCheckBox = new JCheckBox();
            selectCheckBox.setBounds(0, 10, 20, 20);
            selectCheckBox.setBackground(new Color(30, 30, 30));
            selectCheckBox.setForeground(Color.white);
            selectCheckBox.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
            TheaterListRow.add(selectCheckBox);
            checkBoxesTheaterList.add(selectCheckBox);

            JLabel NameTheater = new JLabel(rs.getString("TheaterName"));
            NameTheater.setBounds(200, 10, 200, 20);
            NameTheater.setForeground(Color.white);
            NameTheater.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
            TheaterListRow.add(NameTheater);

            JLabel NormalSeats = new JLabel(String.valueOf(rs.getInt("NormalSeats")));
            NormalSeats.setBounds(400, 10, 100, 20);
            NormalSeats.setForeground(Color.white);
            NormalSeats.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
            TheaterListRow.add(NormalSeats);

            JLabel VIPSeats = new JLabel(String.valueOf(rs.getInt("VipSeats")));
            VIPSeats.setBounds(600, 10, 100, 20);
            VIPSeats.setForeground(Color.white);
            VIPSeats.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
            TheaterListRow.add(VIPSeats);

            contentPanelTheaterList.add(TheaterListRow);
            rowIndex++;
            }

            contentPanelTheaterList.setPreferredSize(new Dimension(650, Math.max(500, rowIndex * 50)));
            contentPanelTheaterList.revalidate();
            contentPanelTheaterList.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching theater data from the database!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane scrollPanelTheaterList = new JScrollPane(contentPanelTheaterList);
        scrollPanelTheaterList.setBounds(35, 110, 700, 440);
        scrollPanelTheaterList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPanelTheaterList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelTheaterList.setBorder(null);
        scrollPanelTheaterList.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanelTheaterList.getViewport().setBackground(new Color(30, 30, 30));

        contentPanelTheaterList.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPanelTheaterList.getVerticalScrollBar();
            int notches = e.getWheelRotation();
            int currentValue = verticalScrollBar.getValue();
            int scrollAmount = 30;
            verticalScrollBar.setValue(currentValue + (notches * scrollAmount));
        });

        theaterDashboard.add(scrollPanelTheaterList);

        RoundedButton UnSelectAllTheater = new RoundedButton("Unselected All", 5);
        UnSelectAllTheater.setBounds(64, 650, 97, 27);
        UnSelectAllTheater.setForeground(Color.BLACK);
        UnSelectAllTheater.setBackground(Color.white);
        UnSelectAllTheater.addActionListener(e -> {
            for (JCheckBox checkBox : checkBoxesTheaterList) {
            if (checkBox.isSelected()) {
                checkBox.setSelected(false);
            }
            }
        });
        theaterDashboard.add(UnSelectAllTheater);

        int buttonwidth1 = 97;
        int gap1 = 20;
        RoundedButton selectAllTheaters = new RoundedButton("Selected All", 5);
        selectAllTheaters.setBounds(64 + buttonwidth1 + gap1, 650, 120, 27);
        selectAllTheaters.setForeground(Color.BLACK);
        selectAllTheaters.setBackground(Color.white);
        selectAllTheaters.addActionListener(e -> {
            for (JCheckBox checkBox : checkBoxesTheaterList) {
            if (!checkBox.isSelected()) {
                checkBox.setSelected(true);
            }
            }
        });
        theaterDashboard.add(selectAllTheaters);

        RoundedButton EditTheater = new RoundedButton("Edit" , 5);
        EditTheater.setBounds(70 + buttonwidth1*2 + gap1*2, 650, 97, 27);
        EditTheater.setForeground(Color.BLACK);
        EditTheater.setBackground(Color.white);
        EditTheater.addActionListener(e -> {
            int counttheaters = 0;
            int selectedIndex = -1;
            String selectedTheaterName = null;
            for (int i = 0; i < checkBoxesTheaterList.size(); i++) {
                if (checkBoxesTheaterList.get(i).isSelected()) {
                    counttheaters++;
                    selectedIndex = i;
                    selectedTheaterName = ((JLabel) ((JPanel) contentPanelTheaterList.getComponent(i)).getComponent(1)).getText();
                }
            }
            if (counttheaters == 1 && selectedTheaterName != null) {
                int theaterId = TheaterManager.getTheaterIdByName(selectedTheaterName);
                JPanel editTheaterModal = new JPanel();
                editTheaterModal.setLayout(null);
                editTheaterModal.setPreferredSize(new Dimension(1000, 500)); // Adjust size to make it smaller
                editTheaterModal.setBackground(new Color(30, 30, 30));
                
                // Title for the panel
                JLabel panelTitle = new JLabel("Edit Theater");
                panelTitle.setBounds(50, 10, 200, 30);
                panelTitle.setForeground(Color.WHITE);
                panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
                editTheaterModal.add(panelTitle);
                
                // Title Label and TextField
                JLabel titleLabel = new JLabel("Name");
                titleLabel.setBounds(50, 50, 100, 30);
                titleLabel.setForeground(Color.WHITE);
                JTextField titleTextField = new JTextField();
                titleTextField.setBounds(50, 80, 300, 30);
                titleTextField.setBackground(new Color(50, 50, 50));
                titleTextField.setForeground(Color.WHITE);
                titleTextField.setCaretColor(Color.WHITE);
                titleTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                editTheaterModal.add(titleLabel);
                editTheaterModal.add(titleTextField);

                // VIPSeats Label and TextField
                JLabel VIPseatsLabel = new JLabel("Number of VIP Seats");
                VIPseatsLabel.setBounds(50, 130, 150, 30); // Adjusted width for label
                VIPseatsLabel.setForeground(Color.WHITE);
                JTextField VIPSeats = new JTextField();
                VIPSeats.setBounds(50, 160, 300, 30); // Corrected y position for VIPSeats
                VIPSeats.setBackground(new Color(50, 50, 50));
                VIPSeats.setForeground(Color.WHITE);
                editTheaterModal.add(VIPseatsLabel);
                editTheaterModal.add(VIPSeats);
                theaterDashboard.add(editTheaterModal);
                
                // NormalSeats Label and TextField
                JLabel NormalSeatsLabel = new JLabel("Number of Normal Seats");
                NormalSeatsLabel.setBounds(50, 210, 150, 30); // Adjusted width for label
                NormalSeatsLabel.setForeground(Color.WHITE);
                JTextField NormalSeats = new JTextField();
                NormalSeats.setBounds(50, 240, 300, 30); // Corrected y position for NormalSeats
                NormalSeats.setBackground(new Color(50, 50, 50));
                NormalSeats.setForeground(Color.WHITE);
                editTheaterModal.add(NormalSeatsLabel);
                editTheaterModal.add(NormalSeats);

                // Edit Theater Button
                RoundedButton editTheaterButton = new RoundedButton("Edit Theater", 5);
                editTheaterButton.setBounds(380, 450, 130, 30); // Adjusted position
                editTheaterButton.setForeground(Color.BLACK);
                editTheaterButton.setBackground(Color.WHITE);
                editTheaterButton.addActionListener(event -> {
                    String name = titleTextField.getText();
                    Integer vipSeats = VIPSeats.getText().isEmpty() ? null : Integer.parseInt(VIPSeats.getText());
                    Integer normalSeats = NormalSeats.getText().isEmpty() ? null : Integer.parseInt(NormalSeats.getText());
                    if(!name.isEmpty()) {
                        TheaterManager.updateTheaterName(theaterId, name);
                    }
                    if(vipSeats != null) {
                        TheaterManager.updateVIPSeats(theaterId, vipSeats);
                    }
                    if(normalSeats != null) {
                        TheaterManager.updateNormalSeats(theaterId, normalSeats);
                    }
                    JOptionPane.showMessageDialog(null, "Theater updated successfully!");
                });
                editTheaterModal.add(editTheaterButton);
            } else {
                JOptionPane.showMessageDialog(null, "Please select one and only one theater to edit!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        theaterDashboard.add(EditTheater);

        RoundedButton AddTheater = new RoundedButton("Add" , 5);
        AddTheater.setBounds(64 + buttonwidth1*3 + gap1*3, 650, 97, 27);
        AddTheater.setForeground(Color.BLACK);
        AddTheater.setBackground(Color.white);
        AddTheater.addActionListener(e -> {
            JPanel addTheaterModal = new JPanel();
            addTheaterModal.setLayout(null);
            addTheaterModal.setPreferredSize(new Dimension(1000, 500)); // Adjust size to make it smaller
            addTheaterModal.setBackground(new Color(30, 30, 30));
            
            // Title for the panel
            JLabel panelTitle = new JLabel("Add New Theater");
            panelTitle.setBounds(50, 10, 200, 30);
            panelTitle.setForeground(Color.WHITE);
            panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
            addTheaterModal.add(panelTitle);
            
            // Title Label and TextField
            JLabel titleLabel = new JLabel("Name");
            titleLabel.setBounds(50, 50, 100, 30);
            titleLabel.setForeground(Color.WHITE);
            JTextField titleTextField = new JTextField();
            titleTextField.setBounds(50, 80, 300, 30);
            titleTextField.setBackground(new Color(50, 50, 50));
            titleTextField.setForeground(Color.WHITE);
            titleTextField.setCaretColor(Color.WHITE);
            titleTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            addTheaterModal.add(titleLabel);
            addTheaterModal.add(titleTextField);
            
            // VIPSeats Label and TextField
            JLabel VIPseatsLabel = new JLabel("Number of VIP Seats");
            VIPseatsLabel.setBounds(50, 130, 150, 30); // Adjusted width for label
            VIPseatsLabel.setForeground(Color.WHITE);
            JTextField VIPSeats = new JTextField();
            VIPSeats.setBounds(50, 160, 300, 30); // Corrected y position for VIPSeats
            VIPSeats.setBackground(new Color(50, 50, 50));
            VIPSeats.setForeground(Color.WHITE);
            addTheaterModal.add(VIPseatsLabel);
            addTheaterModal.add(VIPSeats);
            
            // NormalSeats Label and TextField
            JLabel NormalSeatsLabel = new JLabel("Number of Normal Seats");
            NormalSeatsLabel.setBounds(50, 210, 150, 30); // Adjusted width for label
            NormalSeatsLabel.setForeground(Color.WHITE);
            JTextField NormalSeats = new JTextField();
            NormalSeats.setBounds(50, 240, 300, 30); // Corrected y position for NormalSeats
            NormalSeats.setBackground(new Color(50, 50, 50));
            NormalSeats.setForeground(Color.WHITE);
            addTheaterModal.add(NormalSeatsLabel);
            addTheaterModal.add(NormalSeats);

            // Add Theater Button
            RoundedButton addTheaterButton2 = new RoundedButton("Add Theater", 5);
            addTheaterButton2.setBounds(380, 450, 130, 30); // Adjusted position
            addTheaterButton2.setForeground(Color.BLACK);
            addTheaterButton2.setBackground(Color.WHITE);
            addTheaterButton2.addActionListener(event -> {
                String Name = titleTextField.getText();
                Integer VipSeats = null;
                Integer normalSeats = null;
                try {
                    VipSeats = Integer.parseInt(VIPSeats.getText());
                    normalSeats = Integer.parseInt(NormalSeats.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid format for the seats!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Name.isEmpty() && VipSeats != null && normalSeats != null) {
                    if (VipSeats < 0 || normalSeats < 0) {
                        JOptionPane.showMessageDialog(null, "The number of VIP Seats and Normal Seats must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    TheaterManager theaterManager = new TheaterManager();
                    boolean addedTheater = theaterManager.addTheater(Name, normalSeats, VipSeats);

                    if (addedTheater) {
                        JOptionPane.showMessageDialog(null, "Theater added successfully!");
                        SwingUtilities.getWindowAncestor(addTheaterModal).dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add theater. See console for details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            addTheaterModal.add(addTheaterButton2);

            // Cancel Button
            RoundedButton cancelButton = new RoundedButton("Cancel", 5);
            cancelButton.setBounds(550, 450, 130, 30);
            cancelButton.setForeground(Color.BLACK);
            cancelButton.setBackground(Color.WHITE);
            cancelButton.addActionListener(event -> {
                SwingUtilities.getWindowAncestor(addTheaterModal).dispose();
            });
            addTheaterModal.add(cancelButton);

            // Show the modal in a JDialog
            JDialog dialog = new JDialog();
            dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            dialog.setModal(true); // Prevent interaction with other windows
            dialog.getContentPane().add(addTheaterModal);
            dialog.pack();
            dialog.setLocationRelativeTo(null); // Center on screen
            dialog.setVisible(true);
        });
        theaterDashboard.add(AddTheater);

        RoundedButton Deletetheater = new RoundedButton("Delete" , 5);
        Deletetheater.setBounds(64 + buttonwidth1*4 + gap1*4, 650, 97, 27);
        Deletetheater.setForeground(Color.BLACK);
        Deletetheater.setBackground(Color.white);
        Deletetheater.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure you want to delete the theater(s) you selected", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (response == JOptionPane.YES_OPTION) {    
                for (JCheckBox checkBox : checkBoxesTheaterList) {
                    if (checkBox.isSelected()) {
                        try (Connection conn = DatabaseConnection.connect();
                             Statement stmt = conn.createStatement()) {
                            String theaterName = ((JLabel) checkBox.getParent().getComponent(1)).getText();
                            String sql = "DELETE FROM theaters WHERE TheaterName = '" + theaterName + "'";
                            stmt.executeUpdate(sql);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error deleting theater from the database!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });        
        theaterDashboard.add(Deletetheater);

        // slide panel 
        JPanel EditTheaterpanel = new JPanel();
        EditTheaterpanel.setLayout(null);
        EditTheaterpanel.setBounds(1200,0, 270, 750);
        EditTheaterpanel.setBackground(new Color(30,30,30));
        theaterDashboard.add(EditTheaterpanel);

        JLabel Edittheaterlbl = new JLabel("Edit Theater ");
        Edittheaterlbl.setBounds(30 ,30 ,191,36);
        Edittheaterlbl.setFont(new Font("Segoe UI" , Font.BOLD , 24));
        Edittheaterlbl.setForeground(Color.white);
        EditTheaterpanel.add(Edittheaterlbl); 

        JLabel editJLabelTheaterName = new JLabel("Edit Theater Title");
        editJLabelTheaterName.setBounds(20,120,130,24);
        editJLabelTheaterName.setFont(new Font("Segoe UI" , Font.BOLD ,14));
        editJLabelTheaterName.setForeground(Color.white);
        EditTheaterpanel.add(editJLabelTheaterName);

        JCheckBox CheckEditTheatername = new JCheckBox();
        CheckEditTheatername.setBounds(160,123, 20, 20);
        CheckEditTheatername.setBackground(new Color(30, 30, 30));
        CheckEditTheatername.setForeground(Color.white);
        CheckEditTheatername.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        CheckEditTheatername.setSelected(true);
        EditTheaterpanel.add(CheckEditTheatername);

        JTextField editJTextFieldNameTheater = new JTextField(" Theater Name");
        editJTextFieldNameTheater.setBounds(20,160,200,38);
        editJTextFieldNameTheater.setFont(new Font("Segoe UI" , Font.PLAIN , 14));
        editJTextFieldNameTheater.setForeground(Color.white);
        editJTextFieldNameTheater.setBackground(new Color(30,30,30));
        TextfieldBehave(editJTextFieldNameTheater, " Theater Name");
        CheckEditTheatername.addItemListener(e -> {
            if (CheckEditTheatername.isSelected()) {
                editJTextFieldNameTheater.setEnabled(true);
            } else {
                editJTextFieldNameTheater.setEnabled(false);
            }
        });
        EditTheaterpanel.add(editJTextFieldNameTheater);

        JLabel editJLabelnbrseatsnrml = new JLabel("Number of Normal Seats ");
        editJLabelnbrseatsnrml.setBounds(20,230,180,24);
        editJLabelnbrseatsnrml.setFont(new Font("Segoe UI" , Font.BOLD ,14));
        editJLabelnbrseatsnrml.setForeground(Color.white);
        EditTheaterpanel.add(editJLabelnbrseatsnrml);

        JCheckBox CheckEditNrmlseats = new JCheckBox();
        CheckEditNrmlseats.setBounds(190,233, 20, 20);
        CheckEditNrmlseats.setBackground(new Color(30, 30, 30));
        CheckEditNrmlseats.setForeground(Color.white);
        CheckEditNrmlseats.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        CheckEditNrmlseats.setSelected(true);
        EditTheaterpanel.add(CheckEditNrmlseats);

        JTextField editJTextFieldnbrseatnrml = new JTextField();
        editJTextFieldnbrseatnrml.setBounds(20,273,200,38);
        editJTextFieldnbrseatnrml.setFont(new Font("Segoe UI" , Font.PLAIN , 14));
        editJTextFieldnbrseatnrml.setForeground(Color.white);
        editJTextFieldnbrseatnrml.setBackground(new Color(30,30,30));
        //TextfieldBehave(editJTextFieldnbrseatnrml, " ");
        CheckEditNrmlseats.addItemListener(e -> {
            if (CheckEditNrmlseats.isSelected()) {
                editJTextFieldnbrseatnrml.setEnabled(true);
            } else {
                editJTextFieldnbrseatnrml.setEnabled(false);
            }
        });
        EditTheaterpanel.add(editJTextFieldnbrseatnrml);

        JLabel editJLabelnbrseatsvip = new JLabel("Number of VIP Seats");
        editJLabelnbrseatsvip.setBounds(20,343,150,24);
        editJLabelnbrseatsvip.setFont(new Font("Segoe UI" , Font.BOLD ,14));
        editJLabelnbrseatsvip.setForeground(Color.white);
        EditTheaterpanel.add(editJLabelnbrseatsvip);

        JCheckBox CheckEditnbrseatsvip = new JCheckBox();
        CheckEditnbrseatsvip.setBounds(170,346, 20, 20);
        CheckEditnbrseatsvip.setBackground(new Color(30, 30, 30));
        CheckEditnbrseatsvip.setForeground(Color.white);
        CheckEditnbrseatsvip.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        CheckEditnbrseatsvip.setSelected(true);
        EditTheaterpanel.add(CheckEditnbrseatsvip);

        JTextField editJTextFieldnbrseatsvip = new JTextField();
        editJTextFieldnbrseatsvip.setBounds(20,386,200,38);
        editJTextFieldnbrseatsvip.setFont(new Font("Segoe UI" , Font.PLAIN , 14));
        editJTextFieldnbrseatsvip.setForeground(Color.white);
        editJTextFieldnbrseatsvip.setBackground(new Color(30,30,30));
        //TextfieldBehave(editJTextFieldnbrseatsvip, " Title");
        CheckEditnbrseatsvip.addItemListener(e -> {
            if (CheckEditnbrseatsvip.isSelected()) {
                editJTextFieldnbrseatsvip.setEnabled(true);
            } else {
                editJTextFieldnbrseatsvip.setEnabled(false);
            }
        });
        EditTheaterpanel.add(editJTextFieldnbrseatsvip);

        RoundedButton EditButtontheaterEdit = new RoundedButton("Edit ", 5);
        EditButtontheaterEdit.setBounds(35, 550, 170, 36);
        EditButtontheaterEdit.setBackground(Color.white);
        EditTheaterpanel.add(EditButtontheaterEdit);

        RoundedButton CancelButtonEdit = new RoundedButton("Cancel" , 5);
        CancelButtonEdit.setBackground(Color.white);
        CancelButtonEdit.setBounds(35, 600, 170, 36);
        CancelButtonEdit.addActionListener(e -> {
            if (isPanelVisible) {
                slidePanel(EditTheaterpanel, 782, 1200, 0, 270, 750);
                isPanelVisible = false;
            }
        });
        EditTheaterpanel.add(CancelButtonEdit);

        EditTheater.addActionListener(e->{
            if (!isPanelVisible) {
                slidePanel(EditTheaterpanel, 1200, 782, 0, 270, 750);
                isPanelVisible = true;
            }
        });

        //----------right Things


        JSeparator separator1Theater = new JSeparator();
        separator1Theater.setOrientation(SwingConstants.VERTICAL);
        separator1Theater.setBackground(Color.white);
        separator1Theater.setForeground(Color.white);
        separator1Theater.setBounds(780, 00, 2, 750);
        theaterDashboard.add(separator1Theater);

        // Center Panel for Theater Statistics
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setBounds(800, 20, 350, 700);
        centerPanel.setBackground(new Color(0, 0, 0, 0));
        theaterDashboard.add(centerPanel);

        // Total Number of Theaters
        JLabel nbrTheaterLabel = new JLabel("Total Number of Theaters");
        nbrTheaterLabel.setBounds(40, 0, 200, 30);
        nbrTheaterLabel.setForeground(Color.WHITE);
        nbrTheaterLabel.setFont(new Font("Bebas Neue", Font.BOLD, 15));
        centerPanel.add(nbrTheaterLabel);

        RoundedPanel CircleTheater = new RoundedPanel(100);
        CircleTheater.setLayout(null);
        CircleTheater.setBounds(50, 40, 100, 100);
        CircleTheater.setBackground(new Color(0, 0, 0, 0));
        CircleTheater.setRoundedBorder(Color.WHITE, 2);
        centerPanel.add(CircleTheater);

        int totalTheaters = TheaterManager.numberOfTheaters();
        JLabel nbrTotalTheater = new JLabel(String.valueOf(totalTheaters));
        nbrTotalTheater.setBounds(totalTheaters < 100 ? 40 : 30, 32, 100, 30);
        nbrTotalTheater.setForeground(Color.WHITE);
        nbrTotalTheater.setFont(new Font("Bebas Neue", Font.BOLD, 30));
        CircleTheater.add(nbrTotalTheater);

        JSeparator separatorleft1 = new JSeparator();
        separator1.setOrientation(SwingConstants.HORIZONTAL);
        separator1.setBackground(Color.white);
        separator1.setForeground(Color.white);
        separator1.setBounds(50, 160, 250, 1);
        centerPanel.add(separator1);

        // Total VIP Seats
        JLabel nbrVIPSeatsLabel = new JLabel("Total VIP Seats");
        nbrVIPSeatsLabel.setBounds(50, 180, 200, 30);
        nbrVIPSeatsLabel.setForeground(Color.WHITE);
        nbrVIPSeatsLabel.setFont(new Font("Bebas Neue", Font.BOLD, 15));
        centerPanel.add(nbrVIPSeatsLabel);

        RoundedPanel CircleVIPSeats = new RoundedPanel(100);
        CircleVIPSeats.setLayout(null);
        CircleVIPSeats.setBounds(50, 220, 100, 100);
        CircleVIPSeats.setBackground(new Color(0, 0, 0, 0));
        CircleVIPSeats.setRoundedBorder(Color.WHITE, 2);
        centerPanel.add(CircleVIPSeats);

        int totalVIPSeats = TheaterManager.totalVIPSeats();
        JLabel nbrTotalVIPSeats = new JLabel(String.valueOf(totalVIPSeats));
        nbrTotalVIPSeats.setBounds(totalVIPSeats < 10 ? 40 : 33, 32, 100, 30);
        nbrTotalVIPSeats.setForeground(Color.WHITE);
        nbrTotalVIPSeats.setFont(new Font("Bebas Neue", Font.BOLD, 30));
        CircleVIPSeats.add(nbrTotalVIPSeats);

        JSeparator separator2 = new JSeparator();
        separator2.setOrientation(SwingConstants.HORIZONTAL);
        separator2.setBackground(Color.white);
        separator2.setForeground(Color.white);
        separator2.setBounds(5, 340, 250, 1);
        centerPanel.add(separator2);

        // Total Normal Seats
        JLabel nbrNormalSeatsLabel = new JLabel("Total Normal Seats");
        nbrNormalSeatsLabel.setBounds(50, 360, 200, 30);
        nbrNormalSeatsLabel.setForeground(Color.WHITE);
        nbrNormalSeatsLabel.setFont(new Font("Bebas Neue", Font.BOLD, 15));
        centerPanel.add(nbrNormalSeatsLabel);

        RoundedPanel CircleNormalSeats = new RoundedPanel(100);
        CircleNormalSeats.setLayout(null);
        CircleNormalSeats.setBounds(50, 400, 100, 100);
        CircleNormalSeats.setBackground(new Color(0, 0, 0, 0));
        CircleNormalSeats.setRoundedBorder(Color.WHITE, 2);
        centerPanel.add(CircleNormalSeats);

        int totalNormalSeats = TheaterManager.totalNormalSeats();
        JLabel nbrTotalNormalSeats = new JLabel(String.valueOf(totalNormalSeats));
        nbrTotalNormalSeats.setBounds(totalNormalSeats < 10 ? 40 : 33, 32, 100, 30);
        nbrTotalNormalSeats.setForeground(Color.WHITE);
        nbrTotalNormalSeats.setFont(new Font("Bebas Neue", Font.BOLD, 30));
        CircleNormalSeats.add(nbrTotalNormalSeats);

        JSeparator separator3 = new JSeparator();
        separator3.setOrientation(SwingConstants.HORIZONTAL);
        separator3.setBackground(Color.white);
        separator3.setForeground(Color.white);
        separator3.setBounds(50, 520, 250, 1);
        centerPanel.add(separator3);

        // Date
        JLabel Datetodaytheater = new JLabel("Date:");
        Datetodaytheater.setBounds(50, 700, 200, 30);
        Datetodaytheater.setForeground(Color.WHITE);
        Datetodaytheater.setFont(new Font("Bebas Neue", Font.BOLD, 13));
        centerPanel.add(Datetodaytheater);

        JLabel datenowTheater = new JLabel(LocalDate.now().toString());
        datenowTheater.setBounds(50, 700, 200, 30);
        BroadcastDashboard = new JPanel();
        BroadcastDashboard.setLayout(null);
        BroadcastDashboard.setBounds(0, 0, 1400, 750);
        BroadcastDashboard.setBackground(new Color(30, 30, 30));
        MiddlePanel.add(BroadcastDashboard);

        JPanel BroadcastDashboard = new JPanel();
        BroadcastDashboard.setLayout(null);
        BroadcastDashboard.setBounds(0, 0, 1400, 750);
        BroadcastDashboard.setBackground(new Color(30, 30, 30));
        MiddlePanel.add(BroadcastDashboard);

        JLabel Broadcastlbl = new JLabel("Broadcast  Dashboard");
        Broadcastlbl.setBounds(35, 26, 300, 30);
        Broadcastlbl.setForeground(Color.WHITE);
        Broadcastlbl.setFont(new Font("Bebas Neue", Font.BOLD, 23));
        BroadcastDashboard.add(Broadcastlbl);


        JLabel Movielblbroad = new JLabel("   Movie");
        Movielblbroad.setBounds(170, 87, 200, 30);
        Movielblbroad.setForeground(Color.WHITE);
        Movielblbroad.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        BroadcastDashboard.add(Movielblbroad);

        JLabel Theaterlblbroad = new JLabel("Theater");
        Theaterlblbroad.setBounds(390, 87, 200, 30);
        Theaterlblbroad.setForeground(Color.WHITE);
        Theaterlblbroad.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        BroadcastDashboard.add(Theaterlblbroad);

        JLabel Datelblbroad = new JLabel("Date");
        Datelblbroad.setBounds(610, 87, 200, 30);
        Datelblbroad.setForeground(Color.WHITE);
        Datelblbroad.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        BroadcastDashboard.add(Datelblbroad);  

        JLabel SelectlblBroad = new JLabel("Select");
        SelectlblBroad.setBounds(30, 87, 200, 30);
        SelectlblBroad.setForeground(Color.WHITE);
        SelectlblBroad.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        BroadcastDashboard.add(SelectlblBroad);

        JSeparator separatorhorBroad = new JSeparator();
        separatorhorBroad.setOrientation(SwingConstants.HORIZONTAL);
        separatorhorBroad.setBackground(Color.white);
        separatorhorBroad.setForeground(Color.white); 
        separatorhorBroad.setBounds(21, 120, 720, 1);
        BroadcastDashboard.add(separatorhorBroad);
   
        JPanel Broadcast = new JPanel();
        Broadcast.setLayout(null);
        Broadcast.setBounds(5, 130, 700, 440);
        Broadcast.setBackground(new Color(30, 30, 30));
        

        JPanel contentPanelBroad = new JPanel();
        contentPanelBroad.setLayout(null);
        contentPanelBroad.setBackground(new Color(30, 30, 30));


        int totalHeightBroad = broadcastManager.broadcasts.size() * 50; 
        contentPanelBroad.setPreferredSize(new Dimension(650, Math.max(500, totalHeightBroad)));

        ArrayList<JCheckBox> checkBoxesBroadcast = new ArrayList<JCheckBox>();

                try (Connection conn = DatabaseConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT broadcasts.BroadcastID, movies.Title AS MovieTitle, theaters.TheaterName AS TheaterName, " +
                "broadcasts.Language, broadcasts.BroadcastDate " +
                "FROM broadcasts " +
                "JOIN movies ON broadcasts.MovieID = movies.MovieID " +
                "JOIN theaters ON broadcasts.TheaterID = theaters.TheaterID")) {

            int rowIndex = 0;
            while (rs.next()) {
                JPanel BroadcastRow = new JPanel();
                BroadcastRow.setLayout(null);
                BroadcastRow.setBounds(0, rowIndex * 50, 700, 40);
                BroadcastRow.setBackground(new Color(30, 30, 30));

                JLabel MovieTitleLabel = new JLabel(rs.getString("MovieTitle"));
                MovieTitleLabel.setBounds(170, 5, 200, 30);
                MovieTitleLabel.setForeground(Color.white);
                MovieTitleLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                BroadcastRow.add(MovieTitleLabel);

                JLabel TheaterNameLabel = new JLabel(rs.getString("TheaterName"));
                TheaterNameLabel.setBounds(390, 5, 200, 30);
                TheaterNameLabel.setForeground(Color.white);
                TheaterNameLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                BroadcastRow.add(TheaterNameLabel);

                JLabel BroadcastDateLabel = new JLabel(rs.getDate("BroadcastDate").toString());
                BroadcastDateLabel.setBounds(610, 5, 200, 30);
                BroadcastDateLabel.setForeground(Color.white);
                BroadcastDateLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                BroadcastRow.add(BroadcastDateLabel);

                JCheckBox selectCheckBox = new JCheckBox();
                selectCheckBox.setBounds(30, 5, 20, 20);
                selectCheckBox.setBackground(new Color(30, 30, 30));
                selectCheckBox.setForeground(Color.white);
                selectCheckBox.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                BroadcastRow.add(selectCheckBox);
                checkBoxesBroadcast.add(selectCheckBox);

                contentPanelBroad.add(BroadcastRow);
                rowIndex++;
            }

            // Update the preferred size of the content panel based on the number of rows
            contentPanelBroad.setPreferredSize(new Dimension(650, Math.max(500, rowIndex * 50)));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching broadcast data from the database!", "Error", JOptionPane.ERROR_MESSAGE);
        }

      
        JScrollPane scrollPanelBroadcast = new JScrollPane(contentPanelBroad);
        scrollPanelBroadcast.setBounds(5, 130, 700, 440);
        scrollPanelBroadcast.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPanelBroadcast.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelBroadcast.setBorder(null);
        scrollPanelBroadcast.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanelBroadcast.getViewport().setBackground(new Color(30, 30, 30));

       
        contentPanelBroad.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPanelBroadcast.getVerticalScrollBar();
            int notches = e.getWheelRotation();
            int currentValue = verticalScrollBar.getValue();
            int scrollAmount = 30; 
            verticalScrollBar.setValue(currentValue + (notches * scrollAmount));
        });

        BroadcastDashboard.add(scrollPanelBroadcast);

        int buttonwidth = 97 ;
        int gap = 20 ;

        RoundedButton UnSelectAllBroad = new RoundedButton("Unselected All" , 5);
        UnSelectAllBroad.setBounds(64, 650, 97, 27);
        UnSelectAllBroad.setForeground(Color.BLACK);
        UnSelectAllBroad.setBackground(Color.white);
        UnSelectAllBroad.addActionListener(e->{
            for(JCheckBox checkBox : checkBoxesBroadcast) {
                if(checkBox.isSelected()){
                    checkBox.setSelected(false);
                }
                
            }
        });
        BroadcastDashboard.add(UnSelectAllBroad);

        
        RoundedButton selectAllBroad = new RoundedButton("Selected All" , 5);
        selectAllBroad.setBounds(64 + buttonwidth + gap , 650, 97, 27);
        selectAllBroad.setForeground(Color.BLACK);
        selectAllBroad.setBackground(Color.white);
        selectAllBroad.addActionListener(e->{
            for(JCheckBox checkBox : checkBoxesBroadcast) {
                if(!checkBox.isSelected()){
                    checkBox.setSelected(true);
                }
            }
        });
        BroadcastDashboard.add(selectAllBroad);

        JPanel EditBroadcast = new JPanel();
        EditBroadcast.setLayout(null);
        EditBroadcast.setBounds(1200,0, 270, 750);
        EditBroadcast.setBackground(new Color(30,30,30));
        BroadcastDashboard.add(EditBroadcast);

        JLabel LabelBroadcast = new JLabel("Edit Broadcast");
        LabelBroadcast.setBounds(30 ,30 ,191,36);
        LabelBroadcast.setFont(new Font("Segoe UI" , Font.BOLD , 24));
        LabelBroadcast.setForeground(Color.white);
        EditBroadcast.add(LabelBroadcast);  


        JLabel editJLabelBroadcast = new JLabel("Edit Theater");
        editJLabelBroadcast.setBounds(20,120,105,24);
        editJLabelBroadcast.setFont(new Font("Segoe UI" , Font.BOLD ,14));
        editJLabelBroadcast.setForeground(Color.white);
        EditBroadcast.add(editJLabelBroadcast);
        
        JCheckBox CheckEditBroadcastHall = new JCheckBox();
        CheckEditBroadcastHall.setBounds(146,123, 20, 20);
        CheckEditBroadcastHall.setBackground(new Color(30, 30, 30));
        CheckEditBroadcastHall.setForeground(Color.white);
        CheckEditBroadcastHall.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        CheckEditBroadcastHall.setSelected(true);
        EditBroadcast.add(CheckEditBroadcastHall);


        JTextField editJTextFieldBroadcastNamehall = new JTextField("Big Hall");
        editJTextFieldBroadcastNamehall.setBounds(20,160,200,38);
        editJTextFieldBroadcastNamehall.setFont(new Font("Segoe UI" , Font.PLAIN , 14));
        editJTextFieldBroadcastNamehall.setForeground(Color.white);
        editJTextFieldBroadcastNamehall.setBackground(new Color(30,30,30));
        TextfieldBehave(editJTextFieldBroadcastNamehall, "Big Hall");
        CheckEditBroadcastHall.addItemListener(e -> {
            if (CheckEditBroadcastHall.isSelected()) {
                editJTextFieldBroadcastNamehall.setEnabled(true);
            } else {
                editJTextFieldBroadcastNamehall.setEnabled(false);
            }
        });
        EditBroadcast.add(editJTextFieldBroadcastNamehall);


        JLabel edittime = new JLabel("Edit Time");
        edittime.setBounds(20,270,105,24);
        edittime.setFont(new Font("Segoe UI" , Font.BOLD ,14));
        edittime.setForeground(Color.white);
        EditBroadcast.add(edittime);

        JCheckBox CheckEditBroadcastTime = new JCheckBox();
        CheckEditBroadcastTime.setBounds(146,273, 20, 20);
        CheckEditBroadcastTime.setBackground(new Color(30, 30, 30));
        CheckEditBroadcastTime.setForeground(Color.white);
        CheckEditBroadcastTime.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        //inisialise the checkbox like not checked
        CheckEditBroadcastTime.setSelected(true);

        EditBroadcast.add(CheckEditBroadcastTime);

        JDialog timeDialogBroadcast = new JDialog(this, "Select Time", true);
        timeDialogBroadcast.setLayout(new BorderLayout());
        timeDialogBroadcast.setSize(300, 200);
        timeDialogBroadcast.setLocationRelativeTo(this);

        


        RoundedButton showTimeBtn = new RoundedButton(LocalDate.now().toString() , 7);
        showTimeBtn.setBounds(20, 310, 200, 30);
        showTimeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        showTimeBtn.setForeground(Color.white);
        showTimeBtn.setBackground(new Color(30,30,30));
        showTimeBtn.setRoundedBorder(Color.white, 1);
        CheckEditBroadcastTime.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (CheckEditBroadcastTime.isSelected()) {
                    showTimeBtn.setEnabled(true);
                } else {
                    showTimeBtn.setEnabled(false);
                }
            }
        });
        showTimeBtn.addActionListener(e -> {
            timeDialogBroadcast.setVisible(true); 
        });
        EditBroadcast.add(showTimeBtn);

        JPanel TimePanelBroadcast = new JPanel(new FlowLayout());
        SpinnerDateModel timeModelbroad = new SpinnerDateModel();
        JSpinner timeSpinnerbroad = new JSpinner(timeModelbroad);
        JSpinner.DateEditor timeEditorbroad = new JSpinner.DateEditor(timeSpinnerbroad, "yyyy/MM/dd");//yyyy/MM/dd HH:mm
        timeSpinnerbroad.setEditor(timeEditorbroad);
        TimePanelBroadcast.add(new JLabel("Select Time: "));
        TimePanelBroadcast.add(timeSpinnerbroad);

        
        JPanel buttonPanelbroad = new JPanel(new FlowLayout());
        JButton okButtonbroad = new JButton("OK");
        JButton cancelButtonbroad = new JButton("Cancel");
        buttonPanelbroad.add(okButtonbroad);
        buttonPanelbroad.add(cancelButtonbroad);

        timeDialogBroadcast.add(TimePanelBroadcast, BorderLayout.CENTER);
        timeDialogBroadcast.add(buttonPanelbroad, BorderLayout.SOUTH);
      

        
        okButtonbroad.addActionListener(e -> {
            Date selectedTime = (Date) timeSpinnerbroad.getValue();
            localTime = selectedTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
            String formattedTime = selectedTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd"));
           showTimeBtn.setText(formattedTime);
            timeDialogBroadcast.dispose();
        });


        cancelButtonbroad.addActionListener(e -> {
            
            timeDialogBroadcast.dispose();
        });

        JLabel editmovie = new JLabel("Edit Movie");
        editmovie.setBounds(20,410,105,24);
        editmovie.setFont(new Font("Segoe UI" , Font.BOLD ,14));
        editmovie.setForeground(Color.white);
        EditBroadcast.add(editmovie);

        JCheckBox CheckEditBroadcastMovie = new JCheckBox();
        CheckEditBroadcastMovie.setBounds(146,413, 20, 20);
        CheckEditBroadcastMovie.setBackground(new Color(30, 30, 30));
        CheckEditBroadcastMovie.setForeground(Color.white);
        CheckEditBroadcastMovie.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        CheckEditBroadcastMovie.setSelected(true);
        EditBroadcast.add(CheckEditBroadcastMovie);

        JComboBox<String> Moviesbox = new JComboBox<>(movieManager.movies.stream().map(movie -> movie.Title).toArray(String[]::new));
        Moviesbox.setBounds(20, 450, 200, 30);
        Moviesbox.setForeground(Color.white);
        Moviesbox.setFocusable(false);
        Moviesbox.setBorder(BorderFactory.createEmptyBorder());
        Moviesbox.setBackground(new Color(30,30,30));
        Moviesbox.setFont(new Font("Arial", Font.PLAIN, 16));
        EditBroadcast.add(Moviesbox);

        RoundedButton EditButtonBroadcastEdit = new RoundedButton("Edit" , 5);
        EditButtonBroadcastEdit.setBackground(Color.white);
        EditButtonBroadcastEdit.setBounds(35, 520, 170, 36);
        EditBroadcast.add(EditButtonBroadcastEdit);

        RoundedButton CancelButtonBroadcastedit = new RoundedButton("Cancel" , 5);
        CancelButtonBroadcastedit.setBackground(Color.white);
        CancelButtonBroadcastedit.setBounds(35, 580, 170, 36);
        CancelButtonBroadcastedit.addActionListener(e -> {
            if (isPanelVisible) {
                slidePanel(EditBroadcast, 782, 1200, 0, 270, 750);
                isPanelVisible = false;
            }
        });
        EditBroadcast.add(CancelButtonBroadcastedit);



        RoundedButton EditBroad = new RoundedButton("Edit" , 5);
        EditBroad.setBounds(64 + buttonwidth*2 + gap*2, 650, 97, 27);
        EditBroad.setForeground(Color.BLACK);
        EditBroad.setBackground(Color.white);
        EditBroad.addActionListener(e->{
            
        });
        BroadcastDashboard.add(EditBroad);

        RoundedButton AddBroad = new RoundedButton("Add" , 5);
        AddBroad.setBounds(64 + buttonwidth*3 + gap*3, 650, 97, 27);
        AddBroad.setForeground(Color.BLACK);
        AddBroad.setBackground(Color.white);
        // AddBroad.addActionListener(e -> {
        //     JDialog addBroadcastModal = new JDialog(this, "Add Broadcast", true);
        //     addBroadcastModal.setLayout(null);
        //     addBroadcastModal.setSize(400, 300);
        //     addBroadcastModal.setLocationRelativeTo(this);
        //     addBroadcastModal.getContentPane().setBackground(new Color(30, 30, 30));

        //     JLabel movieLabel = new JLabel("Movie:");
        //     movieLabel.setBounds(20, 70, 100, 30);
        //     movieLabel.setForeground(Color.WHITE);
        //     movieLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 14));
        //     addBroadcastModal.add(movieLabel);

        //     JComboBox<String> movieComboBox = new JComboBox<>(movieManager.movies.stream().map(movie -> movie.Title).toArray(String[]::new));
        //     movieComboBox.setBounds(120, 70, 200, 30);
        //     movieComboBox.setForeground(Color.white);
        //     movieComboBox.setFocusable(false);
        //     movieComboBox.setBorder(BorderFactory.createEmptyBorder());
        //     movieComboBox.setBackground(new Color(30,30,30));
        //     movieComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        //     addBroadcastModal.add(movieComboBox);

            
        //     JLabel theaterLabel = new JLabel("Theater:");
        //     theaterLabel.setBounds(20, 120, 100, 30);
        //     theaterLabel.setForeground(Color.WHITE);
        //     theaterLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 14));
        //     addBroadcastModal.add(theaterLabel);

            
        //     JComboBox<String> theaterComboBox = new JComboBox<>(TheaterManager.theaters.stream().map(theater -> theater.Name).toArray(String[]::new));
        //     theaterComboBox.setBounds(120, 120, 200, 30);
        //     theaterComboBox.setForeground(Color.white);
        //     theaterComboBox.setFocusable(false);
        //     theaterComboBox.setBorder(BorderFactory.createEmptyBorder());
        //     theaterComboBox.setBackground(new Color(30,30,30));
        //     theaterComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        //     addBroadcastModal.add(theaterComboBox);

        //     JLabel dateLabel = new JLabel("Date:");
        //     dateLabel.setBounds(20, 170, 100, 30);
        //     dateLabel.setForeground(Color.WHITE);
        //     dateLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 14));
        //     addBroadcastModal.add(dateLabel);
        //     //Create a JSpinner for date selection
        //     JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        //     JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        //     dateSpinner.setEditor(dateEditor);
        //     dateSpinner.setBounds(120, 170, 200, 30);
        //     addBroadcastModal.add(dateSpinner);

        //     JLabel timeLabel = new JLabel("Time:");
        //     timeLabel.setBounds(20, 220, 100, 30);
        //     timeLabel.setForeground(Color.WHITE);
        //     timeLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 14));
        //     addBroadcastModal.add(timeLabel);
        //     // Create a JSpinner for time selection
        //     JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        //     JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        //     timeSpinner.setEditor(timeEditor);
        //     timeSpinner.setBounds(120, 220, 200, 30);
        //     addBroadcastModal.add(timeSpinner);


        //     JButton addbroadcast = new JButton("Add");
        //     addbroadcast.setBounds(120, 220, 80, 30);
        //     addbroadcast.setBounds(120, 220, 80, 30);
        //     addbroadcast.setBackground(new Color(30, 30, 30));
        //     addbroadcast.setForeground(Color.WHITE);
        //     addbroadcast.setFont(new Font("Bebas Neue", Font.PLAIN, 14));
        //     addbroadcast.addActionListener(e1 -> {
        //         String selectedMovie = (String) movieComboBox.getSelectedItem();
        //         String selectedTheater = (String) theaterComboBox.getSelectedItem();
        //         Date selectedDate = (Date) dateSpinner.getValue();
        //         Time selectedTime = (Time) timeSpinner.getValue();
        //         try {
        //             localDate = LocalDate.parse(selectedDate);
        //         } catch (DateTimeParseException ex) {
        //             JOptionPane.showMessageDialog(null, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
        //             return;
        //         }
                
        //         if (selectedMovie != null && selectedTheater != null && localDate != null) {
        //             if (VipSeats < 0 || normalSeats < 0) {
        //                 JOptionPane.showMessageDialog(null, "The number of VIP Seats and Normal Seats must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
        //                 return;
        //             }

        //             TheaterManager theaterManager = new TheaterManager();
        //             boolean addedTheater = theaterManager.addTheater(Name, normalSeats, VipSeats);

        //             if (addedTheater) {
        //                 JOptionPane.showMessageDialog(null, "Theater added successfully!");
        //                 SwingUtilities.getWindowAncestor(addTheaterModal).dispose();
        //             } else {
        //                 JOptionPane.showMessageDialog(null, "Failed to add theater. See console for details.", "Error", JOptionPane.ERROR_MESSAGE);
        //             }
        //         } else {
        //             JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        //         }
        //     });
        //     });
        //     addBroadcastModal.add(addButton);

        //     addBroadcastModal.setVisible(true);
        // });
        BroadcastDashboard.add(AddBroad);

        RoundedButton DeleteBroad = new RoundedButton("Delete" , 5);
        DeleteBroad.setBounds(64 + buttonwidth*4 + gap*4, 650, 97, 27);
        DeleteBroad.setForeground(Color.BLACK);
        DeleteBroad.setBackground(Color.white);
        DeleteBroad.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure you want to delete the broadcast(s) you selected", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (response == JOptionPane.YES_OPTION) {    
                for (JCheckBox checkBox : checkBoxesBroadcast) {
                    if (checkBox.isSelected()) {
                        try (Connection conn = DatabaseConnection.connect();
                             Statement stmt = conn.createStatement()) {
                            String movieTitle = ((JLabel) checkBox.getParent().getComponent(1)).getText();
                            String sql = "DELETE FROM broadcasts WHERE MovieID = '" + movieTitle + "'";
                            stmt.executeUpdate(sql);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error deleting broadcast from the database!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        BroadcastDashboard.add(DeleteBroad);




        //-------right things

        JSeparator separator1Broad = new JSeparator();
        separator1Broad.setOrientation(SwingConstants.VERTICAL);
        separator1Broad.setBackground(Color.white);
        separator1Broad.setForeground(Color.white);
        separator1Broad.setBounds(780, 00, 2, 750);
        BroadcastDashboard.add(separator1Broad);
        

        JLabel nbrBroad = new JLabel("Total Number of Broadcast");
        nbrBroad.setBounds(800, 20, 200, 30);
        nbrBroad.setForeground(Color.WHITE);
        nbrBroad.setFont(new Font("Bebas Neue", Font.BOLD, 15));
        BroadcastDashboard.add(nbrBroad);


        RoundedPanel CircleBroad = new RoundedPanel(100);
        CircleBroad.setLayout(null);
        CircleBroad.setBounds(855, 80, 100, 100);
        CircleBroad.setBackground( new Color(0, 0, 0, 0));
        CircleBroad.setRoundedBorder(Color.WHITE, 2);
        BroadcastDashboard.add(CircleBroad);

        JLabel nbrUserLabelBroad = new JLabel();

        if(broadcastManager.broadcasts.size() < 10){
            nbrUserLabelBroad.setBounds(40, 32, 100, 30);
            nbrUserLabelBroad.setText(String.valueOf(broadcastManager.broadcasts.size()));
        }else{
            nbrUserLabelBroad.setBounds(33, 32, 100, 30);
            nbrUserLabelBroad.setText(String.valueOf(broadcastManager.broadcasts.size()));
        }
        
        nbrUserLabelBroad.setForeground(Color.WHITE);
        nbrUserLabelBroad.setFont(new Font("Bebas Neue", Font.BOLD, 30));
        CircleBroad.add(nbrUserLabelBroad);
        
        JSeparator separatorRght1Broadcast = new JSeparator();
        separatorRght1Broadcast.setOrientation(SwingConstants.HORIZONTAL);
        separatorRght1Broadcast.setBackground(Color.white);
        separatorRght1Broadcast.setForeground(Color.white); 
        separatorRght1Broadcast.setBounds(790, 240, 220, 1);
        BroadcastDashboard.add(separatorRght1Broadcast);

        JLabel TopWatchedMovie = new JLabel("Top Watched Movie");
        TopWatchedMovie.setBounds(830, 255, 250, 30);
        TopWatchedMovie.setForeground(Color.WHITE);
        TopWatchedMovie.setFont(new Font("Bebas Neue", Font.BOLD, 15));
        BroadcastDashboard.add(TopWatchedMovie);

        JLabel NameTopMovie = new JLabel(); 
        NameTopMovie.setBounds(850, 300, 250, 30);
        NameTopMovie.setForeground(Color.WHITE);
        NameTopMovie.setFont(new Font("Roboto", Font.TYPE1_FONT, 13));
        String mostFrequentMovie = null;
        int maxCount = 0;

        
        for (int i = 0; i < broadcastManager.broadcasts.size(); i++) {
            String currentMovie = broadcastManager.broadcasts.get(i).movie.Title;
            int currentCount = 0;

            
            for (int j = 0; j <  broadcastManager.broadcasts.size(); j++) {
                if (broadcastManager.broadcasts.get(j).movie.Title.equals(currentMovie)) {
                    currentCount++;
                }
            }

            
            if (currentCount > maxCount) {
                maxCount = currentCount;
                mostFrequentMovie = currentMovie;
            }
        }
        NameTopMovie.setText(mostFrequentMovie);
        BroadcastDashboard.add(NameTopMovie);

        JLabel nbrofbroadcast = new JLabel();
        nbrofbroadcast.setBounds(860, 350, 250, 30);
        nbrofbroadcast.setForeground(Color.WHITE);
        nbrofbroadcast.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        nbrofbroadcast.setText(String.valueOf(maxCount + "   Broadcasts"));
        BroadcastDashboard.add(nbrofbroadcast);

        JSeparator separatorRght2Broadcast = new JSeparator();
        separatorRght2Broadcast.setOrientation(SwingConstants.HORIZONTAL);
        separatorRght2Broadcast.setBackground(Color.white);
        separatorRght2Broadcast.setForeground(Color.white);
        separatorRght2Broadcast.setBounds(790, 400, 220, 1);
        BroadcastDashboard.add(separatorRght2Broadcast);

        JSeparator separatorRght3Broadcast = new JSeparator();
        separatorRght3Broadcast.setOrientation(SwingConstants.HORIZONTAL);
        separatorRght3Broadcast.setBackground(Color.white);
        separatorRght3Broadcast.setForeground(Color.white);
        separatorRght3Broadcast.setBounds(790, 660, 220, 1);
        BroadcastDashboard.add(separatorRght3Broadcast);

        JLabel DatetodayBroad = new JLabel("Date:");
        DatetodayBroad.setBounds(840, 675, 200, 30);
        DatetodayBroad.setForeground(Color.WHITE);
        DatetodayBroad.setFont(new Font("Bebas Neue", Font.BOLD, 13));
        BroadcastDashboard.add(DatetodayBroad);
        
        JLabel datenowBroad = new JLabel(LocalDate.now().toString());
        datenowBroad.setBounds(880, 675, 200, 30);
        datenowBroad.setForeground(Color.WHITE);
        datenowBroad.setFont(new Font("Bebas Neue", Font.BOLD, 13));
        BroadcastDashboard.add(datenowBroad);


        JPanel MoviesDashboard = new JPanel();
        MoviesDashboard.setLayout(null);
        MoviesDashboard.setBounds(0, 0, 750, 750);
        MoviesDashboard.setBackground(new Color(30 , 30 ,30));
        MiddlePanel.add(MoviesDashboard);

        JLabel Moviecastlbl = new JLabel("Movies  Dashboard");
        Moviecastlbl.setBounds(35, 26, 300, 30);
        Moviecastlbl.setForeground(Color.WHITE);
        Moviecastlbl.setFont(new Font("Bebas Neue", Font.BOLD, 23));
        MoviesDashboard.add(Moviecastlbl);


        JSeparator separatorhorMovie = new JSeparator();
        separatorhorMovie.setOrientation(SwingConstants.HORIZONTAL);
        separatorhorMovie.setBackground(Color.white);
        separatorhorMovie.setForeground(Color.white); 
        separatorhorMovie.setBounds(21, 120, 720, 1);
        MoviesDashboard.add(separatorhorMovie);

        // Column widths and positions
        final int SELECT_COL_WIDTH = 60;
        final int TITLE_COL_WIDTH = 200;
        final int GENRE_COL_WIDTH = 150;
        final int RATING_COL_WIDTH = 100;
        final int AGE_RATING_COL_WIDTH = 100;
        final int RELEASE_DATE_COL_WIDTH = 100;
        
        // Starting X position for all columns - moved further left
        final int START_X = 0; // Reduced from 5 to 0
        
        // Header labels with consistent positioning
        JLabel selectedlblMovie = new JLabel("Selected", SwingConstants.CENTER);
        selectedlblMovie.setBounds(START_X + 15, 87, SELECT_COL_WIDTH, 30);
        selectedlblMovie.setForeground(Color.white);
        selectedlblMovie.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        MoviesDashboard.add(selectedlblMovie);

        JLabel MovieTitlelbl = new JLabel("Title", SwingConstants.CENTER);
        MovieTitlelbl.setBounds(START_X + SELECT_COL_WIDTH, 87, TITLE_COL_WIDTH, 30);
        MovieTitlelbl.setForeground(Color.WHITE);
        MovieTitlelbl.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        MoviesDashboard.add(MovieTitlelbl);

        JLabel GenrelblMovie = new JLabel("Genre", SwingConstants.CENTER);
        GenrelblMovie.setBounds(START_X + SELECT_COL_WIDTH + TITLE_COL_WIDTH, 87, GENRE_COL_WIDTH, 30);
        GenrelblMovie.setForeground(Color.WHITE);
        GenrelblMovie.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        MoviesDashboard.add(GenrelblMovie);

        JLabel RatinglblMovie = new JLabel("Rating", SwingConstants.CENTER);
        RatinglblMovie.setBounds(START_X + SELECT_COL_WIDTH + TITLE_COL_WIDTH + GENRE_COL_WIDTH, 87, RATING_COL_WIDTH, 30);
        RatinglblMovie.setForeground(Color.WHITE);
        RatinglblMovie.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        MoviesDashboard.add(RatinglblMovie);
        
        JLabel AgeRatinglblMovie = new JLabel("Age rating", SwingConstants.CENTER);
        AgeRatinglblMovie.setBounds(START_X + SELECT_COL_WIDTH + TITLE_COL_WIDTH + GENRE_COL_WIDTH + RATING_COL_WIDTH, 87, AGE_RATING_COL_WIDTH, 30);
        AgeRatinglblMovie.setForeground(Color.WHITE);
        AgeRatinglblMovie.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        MoviesDashboard.add(AgeRatinglblMovie); 

        JLabel releaseDatelbl = new JLabel("Release Date", SwingConstants.CENTER);
        releaseDatelbl.setBounds(START_X + SELECT_COL_WIDTH + TITLE_COL_WIDTH + GENRE_COL_WIDTH + RATING_COL_WIDTH + AGE_RATING_COL_WIDTH, 87, RELEASE_DATE_COL_WIDTH, 30);
        releaseDatelbl.setForeground(Color.WHITE);
        releaseDatelbl.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        MoviesDashboard.add(releaseDatelbl); 

        JPanel MoviePanelList = new JPanel();
        MoviePanelList.setLayout(null);
        MoviePanelList.setBounds(START_X, 130, 700, 440);
        MoviePanelList.setBackground(new Color(30, 30, 30));

        JPanel contentPanelMovieList = new JPanel();
        contentPanelMovieList.setLayout(null);
        contentPanelMovieList.setBackground(new Color(30, 30, 30));

        int totalHeightMovieList = movieManager.movies.size() * 50; 
        contentPanelMovieList.setPreferredSize(new Dimension(700, Math.max(500, totalHeightMovieList)));

        ArrayList<JCheckBox> checkBoxesMovieList = new ArrayList<JCheckBox>();

        try (Connection conn = DatabaseConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Title, Genre, Rating, AgeRating, ReleaseDate FROM movies")) {

            int rowIndex = 0;
            while (rs.next()) {
                JPanel MovieListRow = new JPanel();
                MovieListRow.setLayout(null);
                MovieListRow.setBounds(0, rowIndex * 50, 700, 40);
                MovieListRow.setBackground(new Color(30, 30, 30));

                JLabel TitleLabel = new JLabel(rs.getString("Title"), SwingConstants.CENTER);
                TitleLabel.setBounds(SELECT_COL_WIDTH, 5, TITLE_COL_WIDTH, 30);
                TitleLabel.setForeground(Color.white);
                TitleLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                MovieListRow.add(TitleLabel);

                JLabel GenreLabel = new JLabel(rs.getString("Genre"), SwingConstants.CENTER);
                GenreLabel.setBounds(SELECT_COL_WIDTH + TITLE_COL_WIDTH, 5, GENRE_COL_WIDTH, 30);
                GenreLabel.setForeground(Color.white);
                GenreLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                MovieListRow.add(GenreLabel);

                JLabel RatingLabel = new JLabel(String.valueOf(rs.getDouble("Rating")), SwingConstants.CENTER);
                RatingLabel.setBounds(SELECT_COL_WIDTH + TITLE_COL_WIDTH + GENRE_COL_WIDTH, 5, RATING_COL_WIDTH, 30);
                RatingLabel.setForeground(Color.white);
                RatingLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                MovieListRow.add(RatingLabel);

                JLabel AgeRatingLabel = new JLabel(rs.getString("AgeRating"), SwingConstants.CENTER);
                AgeRatingLabel.setBounds(SELECT_COL_WIDTH + TITLE_COL_WIDTH + GENRE_COL_WIDTH + RATING_COL_WIDTH, 5, AGE_RATING_COL_WIDTH, 30);
                AgeRatingLabel.setForeground(Color.white);
                AgeRatingLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                MovieListRow.add(AgeRatingLabel);

                // Format the date to show full date including year
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(rs.getDate("ReleaseDate"));
                JLabel ReleaseDateLabel = new JLabel(formattedDate, SwingConstants.CENTER);
                ReleaseDateLabel.setBounds(SELECT_COL_WIDTH + TITLE_COL_WIDTH + GENRE_COL_WIDTH + RATING_COL_WIDTH + AGE_RATING_COL_WIDTH, 5, RELEASE_DATE_COL_WIDTH, 30);
                ReleaseDateLabel.setForeground(Color.white);
                ReleaseDateLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                MovieListRow.add(ReleaseDateLabel);

                JCheckBox selectCheckBox = new JCheckBox();
                selectCheckBox.setBounds(15, 5, SELECT_COL_WIDTH, 30);
                selectCheckBox.setBackground(new Color(30, 30, 30));
                selectCheckBox.setForeground(Color.white);
                selectCheckBox.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                MovieListRow.add(selectCheckBox);
                checkBoxesMovieList.add(selectCheckBox);

                contentPanelMovieList.add(MovieListRow);
                rowIndex++;
            }

            contentPanelMovieList.setPreferredSize(new Dimension(700, Math.max(500, rowIndex * 50)));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching movie data from the database!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane scrollPanelMovieList = new JScrollPane(contentPanelMovieList);
        scrollPanelMovieList.setBounds(START_X, 130, 700, 440);
        scrollPanelMovieList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPanelMovieList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelMovieList.setBorder(null);
        scrollPanelMovieList.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanelMovieList.getViewport().setBackground(new Color(30, 30, 30));

        contentPanelMovieList.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPanelMovieList.getVerticalScrollBar();
            int notches = e.getWheelRotation();
            int currentValue = verticalScrollBar.getValue();
            int scrollAmount = 30; 
            verticalScrollBar.setValue(currentValue + (notches * scrollAmount));
        });

        MoviesDashboard.add(scrollPanelMovieList);


        RoundedButton UnSelectAllMovie = new RoundedButton("Unselected All" , 5);
        UnSelectAllMovie.setBounds(64, 650, 97, 27);
        UnSelectAllMovie.setForeground(Color.BLACK);
        UnSelectAllMovie.setBackground(Color.white);
        UnSelectAllMovie.addActionListener(e->{
            for(JCheckBox checkBox : checkBoxesMovieList) {
                if(checkBox.isSelected()){
                    checkBox.setSelected(false);
                }
                
            }
        });
        MoviesDashboard.add(UnSelectAllMovie);

        
        RoundedButton selectAllMovie = new RoundedButton("Selected All" , 5);
        selectAllMovie.setBounds(64 + buttonwidth + gap , 650, 97, 27);
        selectAllMovie.setForeground(Color.BLACK);
        selectAllMovie.setBackground(Color.white);
        selectAllMovie.addActionListener(e->{
            for(JCheckBox checkBox : checkBoxesMovieList) {
                if(!checkBox.isSelected()){
                    checkBox.setSelected(true);
                }
            }
        });
        MoviesDashboard.add(selectAllMovie);

        RoundedButton EditMovie = new RoundedButton("Edit" , 5);
        EditMovie.setBounds(64 + buttonwidth*2 + gap*2, 650, 97, 27);
        EditMovie.setForeground(Color.BLACK);
        EditMovie.setBackground(Color.white);
        EditMovie.addActionListener(e->{
            int countmovies = 0;
            for(JCheckBox checkBox : checkBoxesMovieList) {
                if(checkBox.isSelected()){
                    countmovies++;
                }
                
            }
            if(countmovies == 1){
                // Get the selected movie title
                String selectedMovieTitle = null;
                for (int i = 0; i < checkBoxesMovieList.size(); i++) {
                    if (checkBoxesMovieList.get(i).isSelected()) {
                        selectedMovieTitle = ((JLabel) ((JPanel) contentPanelMovieList.getComponent(i)).getComponent(1)).getText();
                        break;
                    }
                }
                MovieManager moviemanager = new MovieManager();
                int movieid = moviemanager.getMovieIdByName(selectedMovieTitle);
                // Create the Edit Movie panel directly
                JPanel editMovieModal = new JPanel();
                editMovieModal.setLayout(null);
                editMovieModal.setPreferredSize(new Dimension(1000, 500)); // Adjust size to make it smaller
                editMovieModal.setBackground(new Color(30, 30, 30));

                // Title for the panel
                JLabel panelTitle = new JLabel("Edit Movie");
                panelTitle.setBounds(50, 10, 200, 30);
                panelTitle.setForeground(Color.WHITE);
                panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
                editMovieModal.add(panelTitle);

                // Title Label and TextField
                JLabel titleLabel = new JLabel("Title");
                titleLabel.setBounds(50, 50, 100, 30);
                titleLabel.setForeground(Color.WHITE);
                JTextField titleTextField = new JTextField();
                titleTextField.setBounds(50, 80, 300, 30);
                titleTextField.setBackground(new Color(50, 50, 50));
                titleTextField.setForeground(Color.WHITE);
                titleTextField.setCaretColor(Color.WHITE);
                titleTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                editMovieModal.add(titleLabel);
                editMovieModal.add(titleTextField);

                // Genre Label and TextField
                JLabel genreLabel = new JLabel("Genre");
                genreLabel.setBounds(50, 130, 100, 30); // Same x as Title, different y
                genreLabel.setForeground(Color.WHITE);
                JComboBox<Movie.MovieGenre> genreComboBox = new JComboBox<>(Movie.MovieGenre.values());
                genreComboBox.setBounds(50, 160, 300, 30); // Same x as Title, different y
                genreComboBox.setBackground(new Color(50, 50, 50));
                genreComboBox.setForeground(Color.WHITE);
                editMovieModal.add(genreLabel);
                editMovieModal.add(genreComboBox);
            
                // Age Rating Label and TextField
                JLabel ageRatingLabel = new JLabel("Age Rating");
                ageRatingLabel.setBounds(50, 210, 100, 30); // Same x as Title, different y
                ageRatingLabel.setForeground(Color.WHITE);
                JComboBox<Movie.MovieAgeRating> ageRatingComboBox = new JComboBox<>(Movie.MovieAgeRating.values());
                ageRatingComboBox.setBounds(50, 240, 300, 30); // Same x as Title, different y
                ageRatingComboBox.setBackground(new Color(50, 50, 50));
                ageRatingComboBox.setForeground(Color.WHITE);
                editMovieModal.add(ageRatingLabel);
                editMovieModal.add(ageRatingComboBox);
            
                // Rating Label and TextField
                JLabel ratingLabel = new JLabel("Rating (0.0 - 10.0)");
                ratingLabel.setBounds(50, 300, 150, 30);
                ratingLabel.setForeground(Color.WHITE);
                JTextField ratingTextField = new JTextField();
                ratingTextField.setBounds(50, 330, 300, 30);
                ratingTextField.setBackground(new Color(50, 50, 50));
                ratingTextField.setForeground(Color.WHITE);
                ratingTextField.setCaretColor(Color.WHITE);
                ratingTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                editMovieModal.add(ratingLabel);
                editMovieModal.add(ratingTextField);

                // Trailer URL Label and TextField
                JLabel trailerUrlLabel = new JLabel("Trailer URL");
                trailerUrlLabel.setBounds(380, 50, 100, 30);
                trailerUrlLabel.setForeground(Color.WHITE);
                JTextField trailerUrlTextField = new JTextField();
                trailerUrlTextField.setBounds(380, 80, 300, 30);
                trailerUrlTextField.setBackground(new Color(50, 50, 50));
                trailerUrlTextField.setForeground(Color.WHITE);
                trailerUrlTextField.setCaretColor(Color.WHITE);
                trailerUrlTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                editMovieModal.add(trailerUrlLabel);
                editMovieModal.add(trailerUrlTextField);

                // Release Date Label and Date Picker
                JLabel releaseDateLabel = new JLabel("Release Date");
                releaseDateLabel.setBounds(380, 130, 100, 30);
                releaseDateLabel.setForeground(Color.WHITE);
                JSpinner releaseDateSpinner = new JSpinner(new SpinnerDateModel());
                releaseDateSpinner.setBounds(380, 160, 300, 30);
                releaseDateSpinner.setEditor(new JSpinner.DateEditor(releaseDateSpinner, "yyyy-MM-dd"));
                releaseDateSpinner.setBackground(new Color(50, 50, 50));
                releaseDateSpinner.setForeground(Color.WHITE);
                editMovieModal.add(releaseDateLabel);
                editMovieModal.add(releaseDateSpinner);
            
                // Director Label and TextField
                JLabel directorLabel = new JLabel("Director");
                directorLabel.setBounds(50, 380, 100, 30);
                directorLabel.setForeground(Color.WHITE);
                JTextField directorTextField = new JTextField();
                directorTextField.setBounds(50, 410, 300, 30);
                directorTextField.setBackground(new Color(50, 50, 50));
                directorTextField.setForeground(Color.WHITE);
                directorTextField.setCaretColor(Color.WHITE);
                directorTextField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                editMovieModal.add(directorLabel);
                editMovieModal.add(directorTextField);
            
                // Description Label and TextArea
                JLabel descriptionLabel = new JLabel("Description");
                descriptionLabel.setBounds(380, 210, 100, 30);
                descriptionLabel.setForeground(Color.WHITE);
                JTextArea descriptionTextArea = new JTextArea();
                descriptionTextArea.setBounds(380, 240, 300, 100);
                descriptionTextArea.setBackground(new Color(50, 50, 50));
                descriptionTextArea.setForeground(Color.WHITE);
                descriptionTextArea.setCaretColor(Color.WHITE);
                descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                ));
                descriptionTextArea.setLineWrap(true);
                descriptionTextArea.setWrapStyleWord(true);
                editMovieModal.add(descriptionLabel);
                editMovieModal.add(descriptionTextArea);
            
                // Image Panel
                JPanel imagePanel = new JPanel();
                imagePanel.setBounds(720, 130, 250, 300); // Adjusted position
                imagePanel.setBackground(new Color(50, 50, 50));
                imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                editMovieModal.add(imagePanel);

                // Choose Image Button
                JLabel imageLabelHolder = new JLabel();
                String[] imagePathHolder = new String[1]; // Use an array to hold the image path
                RoundedButton chooseImageButton = new RoundedButton("Choose Image", 5);
                chooseImageButton.setBounds(720, 60, 130, 30); // Moved to the top of the image panel
                chooseImageButton.setForeground(Color.BLACK);
                chooseImageButton.setBackground(Color.WHITE);
                chooseImageButton.addActionListener(event -> {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        imagePathHolder[0] = selectedFile.getAbsolutePath(); // Store the image path in the array
                        ImageIcon imageIcon = new ImageIcon(imagePathHolder[0]);
                        Image image = imageIcon.getImage().getScaledInstance(imagePanel.getWidth(), imagePanel.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon scaledImageIcon = new ImageIcon(image);
                        imageLabelHolder.setIcon(scaledImageIcon);
                        imagePanel.removeAll();
                        imagePanel.add(imageLabelHolder);
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    }
                });
                editMovieModal.add(chooseImageButton);
            
                // Edit Movie Button
                RoundedButton editMovieButton2 = new RoundedButton("Edit Movie", 5);
                editMovieButton2.setBounds(380, 450, 130, 30); // Adjusted position
                editMovieButton2.setForeground(Color.BLACK);
                editMovieButton2.setBackground(Color.WHITE);
                editMovieButton2.addActionListener(event -> {
                    String title = titleTextField.getText();
                    Movie.MovieGenre genre = (Movie.MovieGenre) genreComboBox.getSelectedItem();
                    Movie.MovieAgeRating ageRating = (Movie.MovieAgeRating) ageRatingComboBox.getSelectedItem();
                    String ratingText = ratingTextField.getText();
                    String trailerUrl = trailerUrlTextField.getText();
                    Date releaseDate = (Date) releaseDateSpinner.getValue();
                    String director = directorTextField.getText();
                    String description = descriptionTextArea.getText();
            
                    
                    if (!title.isEmpty()) {
                        moviemanager.updateMovieTitle(movieid, title);
                    }
                    if (genre != null) {
                        moviemanager.updateMovieGenre(movieid, genre);
                    }
                    if (ageRating != null) {
                        moviemanager.updateMovieAgeRating(movieid, ageRating);
                    }
                    if (!ratingText.isEmpty()) {
                        try {
                            float rating = Float.parseFloat(ratingText);
                            if (rating < 0 || rating > 10) {
                            JOptionPane.showMessageDialog(null, "Rating must be between 0.0 and 10.0!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                            moviemanager.updateMovieRating(movieid, rating);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid rating format!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if (!trailerUrl.isEmpty()) {
                        moviemanager.updateMovieTrailerURL(movieid, trailerUrl);
                    }
                    if (releaseDate != null) {
                        LocalDate localReleaseDate = new java.sql.Date(releaseDate.getTime()).toLocalDate();
                        moviemanager.updateMovieReleaseDate(movieid, localReleaseDate);
                    }
                    if (!director.isEmpty()) {
                        moviemanager.updateMovieDirector(movieid, director);
                    }
                    if (!description.isEmpty()) {
                        moviemanager.updateMovieDescription(movieid, description);
                    }
                    // Optionally, show a success message and close the dialog
                    JOptionPane.showMessageDialog(null, "Movie updated successfully!");
                    SwingUtilities.getWindowAncestor(editMovieModal).dispose();

                });
                editMovieModal.add(editMovieButton2);
            
                // Cancel Button
                RoundedButton cancelButton = new RoundedButton("Cancel", 5);
                cancelButton.setBounds(550, 450, 130, 30); // Adjusted position
                cancelButton.setForeground(Color.BLACK);
                cancelButton.setBackground(Color.WHITE);
                cancelButton.addActionListener(event -> {
                    SwingUtilities.getWindowAncestor(editMovieModal).dispose();
                });
                editMovieModal.add(cancelButton);
            
                // Show the modal in a JDialog
                JDialog dialog = new JDialog();
                dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                dialog.setModal(true); // Prevent interaction with other windows
                dialog.getContentPane().add(editMovieModal);
                dialog.pack();
                dialog.setLocationRelativeTo(null); // Center on screen
                dialog.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Please select one and only one movie to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }    
        });
        MoviesDashboard.add(EditMovie);

        RoundedButton AddMovie = new RoundedButton("Add" , 5);
        AddMovie.setBounds(64 + buttonwidth*3 + gap*3, 650, 97, 27);
        AddMovie.setForeground(Color.BLACK);
        AddMovie.setBackground(Color.white);
        MoviesDashboard.add(AddMovie);
        AddMovie.addActionListener(e -> {
            // Create the Add Movie panel directly
            JPanel addMovieModal = new JPanel();
            addMovieModal.setLayout(null);
            addMovieModal.setPreferredSize(new Dimension(1000, 500)); // Adjust size to make it smaller
            addMovieModal.setBackground(new Color(30, 30, 30));
        
            // Title for the panel
            JLabel panelTitle = new JLabel("Add New Movie");
            panelTitle.setBounds(50, 10, 200, 30);
            panelTitle.setForeground(Color.WHITE);
            panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
            addMovieModal.add(panelTitle);
        
            // Title Label and TextField
            JLabel titleLabel = new JLabel("Title");
            titleLabel.setBounds(50, 50, 100, 30);
            titleLabel.setForeground(Color.WHITE);
            JTextField titleTextField = new JTextField();
            titleTextField.setBounds(50, 80, 300, 30);
            titleTextField.setBackground(new Color(50, 50, 50));
            titleTextField.setForeground(Color.WHITE);
            titleTextField.setCaretColor(Color.WHITE);
            titleTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            addMovieModal.add(titleLabel);
            addMovieModal.add(titleTextField);
        
            // Genre Label and TextField
            JLabel genreLabel = new JLabel("Genre");
            genreLabel.setBounds(50, 130, 100, 30); // Same x as Title, different y
            genreLabel.setForeground(Color.WHITE);
            JComboBox<Movie.MovieGenre> genreComboBox = new JComboBox<>(Movie.MovieGenre.values());
            genreComboBox.setBounds(50, 160, 300, 30); // Same x as Title, different y
            genreComboBox.setBackground(new Color(50, 50, 50));
            genreComboBox.setForeground(Color.WHITE);
            addMovieModal.add(genreLabel);
            addMovieModal.add(genreComboBox);
        
            // Age Rating Label and TextField
            JLabel ageRatingLabel = new JLabel("Age Rating");
            ageRatingLabel.setBounds(50, 210, 100, 30); // Same x as Title, different y
            ageRatingLabel.setForeground(Color.WHITE);
            JComboBox<Movie.MovieAgeRating> ageRatingComboBox = new JComboBox<>(Movie.MovieAgeRating.values());
            ageRatingComboBox.setBounds(50, 240, 300, 30); // Same x as Title, different y
            ageRatingComboBox.setBackground(new Color(50, 50, 50));
            ageRatingComboBox.setForeground(Color.WHITE);
            addMovieModal.add(ageRatingLabel);
            addMovieModal.add(ageRatingComboBox);
        
            // Rating Label and TextField
            JLabel ratingLabel = new JLabel("Rating (0.0 - 10.0)");
            ratingLabel.setBounds(50, 300, 150, 30);
            ratingLabel.setForeground(Color.WHITE);
            JTextField ratingTextField = new JTextField();
            ratingTextField.setBounds(50, 330, 300, 30);
            ratingTextField.setBackground(new Color(50, 50, 50));
            ratingTextField.setForeground(Color.WHITE);
            ratingTextField.setCaretColor(Color.WHITE);
            ratingTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            addMovieModal.add(ratingLabel);
            addMovieModal.add(ratingTextField);
        
            // Trailer URL Label and TextField
            JLabel trailerUrlLabel = new JLabel("Trailer URL");
            trailerUrlLabel.setBounds(380, 50, 100, 30);
            trailerUrlLabel.setForeground(Color.WHITE);
            JTextField trailerUrlTextField = new JTextField();
            trailerUrlTextField.setBounds(380, 80, 300, 30);
            trailerUrlTextField.setBackground(new Color(50, 50, 50));
            trailerUrlTextField.setForeground(Color.WHITE);
            trailerUrlTextField.setCaretColor(Color.WHITE);
            trailerUrlTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            addMovieModal.add(trailerUrlLabel);
            addMovieModal.add(trailerUrlTextField);
        
            // Release Date Label and Date Picker
            JLabel releaseDateLabel = new JLabel("Release Date");
            releaseDateLabel.setBounds(380, 130, 100, 30);
            releaseDateLabel.setForeground(Color.WHITE);
            JSpinner releaseDateSpinner = new JSpinner(new SpinnerDateModel());
            releaseDateSpinner.setBounds(380, 160, 300, 30);
            releaseDateSpinner.setEditor(new JSpinner.DateEditor(releaseDateSpinner, "yyyy-MM-dd"));
            releaseDateSpinner.setBackground(new Color(50, 50, 50));
            releaseDateSpinner.setForeground(Color.WHITE);
            addMovieModal.add(releaseDateLabel);
            addMovieModal.add(releaseDateSpinner);
        
            // Director Label and TextField
            JLabel directorLabel = new JLabel("Director");
            directorLabel.setBounds(50, 380, 100, 30);
            directorLabel.setForeground(Color.WHITE);
            JTextField directorTextField = new JTextField();
            directorTextField.setBounds(50, 410, 300, 30);
            directorTextField.setBackground(new Color(50, 50, 50));
            directorTextField.setForeground(Color.WHITE);
            directorTextField.setCaretColor(Color.WHITE);
            directorTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            addMovieModal.add(directorLabel);
            addMovieModal.add(directorTextField);
        
            // Description Label and TextArea
            JLabel descriptionLabel = new JLabel("Description");
            descriptionLabel.setBounds(380, 210, 100, 30);
            descriptionLabel.setForeground(Color.WHITE);
            JTextArea descriptionTextArea = new JTextArea();
            descriptionTextArea.setBounds(380, 240, 300, 100);
            descriptionTextArea.setBackground(new Color(50, 50, 50));
            descriptionTextArea.setForeground(Color.WHITE);
            descriptionTextArea.setCaretColor(Color.WHITE);
            descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            descriptionTextArea.setLineWrap(true);
            descriptionTextArea.setWrapStyleWord(true);
            addMovieModal.add(descriptionLabel);
            addMovieModal.add(descriptionTextArea);
        
            // Image Panel
            JPanel imagePanel = new JPanel();
            imagePanel.setBounds(720, 130, 250, 300); // Adjusted position
            imagePanel.setBackground(new Color(50, 50, 50));
            imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            addMovieModal.add(imagePanel);
        
            // Choose Image Button
            JLabel imageLabelHolder = new JLabel();
            String[] imagePathHolder = new String[1]; // Use an array to hold the image path
            RoundedButton chooseImageButton = new RoundedButton("Choose Image", 5);
            chooseImageButton.setBounds(720, 60, 130, 30); // Moved to the top of the image panel
            chooseImageButton.setForeground(Color.BLACK);
            chooseImageButton.setBackground(Color.WHITE);
            chooseImageButton.addActionListener(event -> {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePathHolder[0] = selectedFile.getAbsolutePath(); // Store the image path in the array
                    ImageIcon imageIcon = new ImageIcon(imagePathHolder[0]);
                    Image image = imageIcon.getImage().getScaledInstance(imagePanel.getWidth(), imagePanel.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(image);
                    imageLabelHolder.setIcon(scaledImageIcon);
                    imagePanel.removeAll();
                    imagePanel.add(imageLabelHolder);
                    imagePanel.revalidate();
                    imagePanel.repaint();
                }
            });
            addMovieModal.add(chooseImageButton);
        
            // Add Movie Button
            RoundedButton addMovieButton2 = new RoundedButton("Add Movie", 5);
            addMovieButton2.setBounds(380, 450, 130, 30); // Adjusted position
            addMovieButton2.setForeground(Color.BLACK);
            addMovieButton2.setBackground(Color.WHITE);
            addMovieButton2.addActionListener(event -> {
                String title = titleTextField.getText();
                Movie.MovieGenre genre = (Movie.MovieGenre) genreComboBox.getSelectedItem();
                Movie.MovieAgeRating ageRating = (Movie.MovieAgeRating) ageRatingComboBox.getSelectedItem();
                String ratingText = ratingTextField.getText();
                String trailerUrl = trailerUrlTextField.getText();
                Date releaseDate = (Date) releaseDateSpinner.getValue();
                String director = directorTextField.getText();
                String description = descriptionTextArea.getText();
        
                // Use imagePathHolder[0] to access the image path
                if (!title.isEmpty() && genre != null && !ratingText.isEmpty() && !director.isEmpty() && !description.isEmpty() && imagePathHolder[0] != null) {
                    try {
                        float rating = Float.parseFloat(ratingText);
        
                        // Validate rating
                        if (rating < 0 || rating > 10) {
                            JOptionPane.showMessageDialog(null, "Rating must be between 0.0 and 10.0!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
        
                        // Format rating to one decimal place
                        rating = Math.round(rating * 10) / 10.0f;
        
                        LocalDate localReleaseDate = new java.sql.Date(releaseDate.getTime()).toLocalDate();
        
                        // Call MovieManager.addMovieToDatabase
                        boolean added = MovieManager.addMovieToDatabase(title, genre, description, rating, ageRating, imagePathHolder[0], trailerUrl, localReleaseDate, director);
        
                        if (added) {
                            JOptionPane.showMessageDialog(null, "Movie added successfully!");
                            SwingUtilities.getWindowAncestor(addMovieModal).dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to add movie. See console for details.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
        
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid rating format!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields and choose an image!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            addMovieModal.add(addMovieButton2);
        
            // Cancel Button
            RoundedButton cancelButton = new RoundedButton("Cancel", 5);
            cancelButton.setBounds(550, 450, 130, 30); // Adjusted position
            cancelButton.setForeground(Color.BLACK);
            cancelButton.setBackground(Color.WHITE);
            cancelButton.addActionListener(event -> {
                SwingUtilities.getWindowAncestor(addMovieModal).dispose();
            });
            addMovieModal.add(cancelButton);
        
            // Show the modal in a JDialog
            JDialog dialog = new JDialog();
            dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            dialog.setModal(true); // Prevent interaction with other windows
            dialog.getContentPane().add(addMovieModal);
            dialog.pack();
            dialog.setLocationRelativeTo(null); // Center on screen
            dialog.setVisible(true);
        });

        RoundedButton DeleteMovie = new RoundedButton("Delete" , 5);
        DeleteMovie.setBounds(64 + buttonwidth*4 + gap*4, 650, 97, 27);
        DeleteMovie.setForeground(Color.BLACK);
        DeleteMovie.setBackground(Color.white);
        DeleteMovie.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure you want to delete the movie(s) you selected", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (response == JOptionPane.YES_OPTION) {    
                for (JCheckBox checkBox : checkBoxesMovieList) {
                    if (checkBox.isSelected()) {
                        try (Connection conn = DatabaseConnection.connect();
                             Statement stmt = conn.createStatement()) {
                            String movieTitle = ((JLabel) checkBox.getParent().getComponent(1)).getText();
                            String sql = "DELETE FROM movies WHERE Title = '" + movieTitle + "'";
                            stmt.executeUpdate(sql);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error deleting movie from the database!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        MoviesDashboard.add(DeleteMovie);

        JPanel EditMoviepanel = new JPanel();
        EditMoviepanel.setLayout(null);
        EditMoviepanel.setBounds(1200,0, 270, 750);
        EditMoviepanel.setBackground(new Color(30,30,30));
        MoviesDashboard.add(EditMoviepanel);
        EditMovie.addActionListener(e->{
            if (!isPanelVisible) {
                slidePanel(EditMoviepanel, 1200, 782, 0, 270, 750);
                isPanelVisible = true;
            }
        });
        
        EditMovie.addActionListener(e -> {
            // Create the Add Movie panel directly
            JPanel addMovieModal = new JPanel();
            addMovieModal.setLayout(null);
            addMovieModal.setPreferredSize(new Dimension(1000, 500)); // Adjust size to make it smaller
            addMovieModal.setBackground(new Color(30, 30, 30));
        
            // Title for the panel
            JLabel panelTitle = new JLabel("Add New Movie");
            panelTitle.setBounds(50, 10, 200, 30);
            panelTitle.setForeground(Color.WHITE);
            panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
            addMovieModal.add(panelTitle);
        
            // Title Label and TextField
            JCheckBox titleCheckBox = new JCheckBox();
            titleCheckBox.setBounds(50, 50, 20, 20);
            titleCheckBox.setBackground(new Color(30, 30, 30));
            titleCheckBox.setForeground(Color.WHITE);
            JLabel titleLabel = new JLabel("Title");
            titleLabel.setBounds(80, 50, 100, 30);
            titleLabel.setForeground(Color.WHITE);
            JTextField titleTextField = new JTextField();
            titleTextField.setBounds(50, 80, 300, 30);
            titleTextField.setBackground(new Color(50, 50, 50));
            titleTextField.setForeground(Color.WHITE);
            titleTextField.setCaretColor(Color.WHITE);
            titleTextField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            titleTextField.setEnabled(false);
            titleCheckBox.addActionListener(e1 -> titleTextField.setEnabled(titleCheckBox.isSelected()));
            addMovieModal.add(titleCheckBox);
            addMovieModal.add(titleLabel);
            addMovieModal.add(titleTextField);
        
            // Genre Label and ComboBox
            JCheckBox genreCheckBox = new JCheckBox();
            genreCheckBox.setBounds(50, 130, 20, 20);
            genreCheckBox.setBackground(new Color(30, 30, 30));
            genreCheckBox.setForeground(Color.WHITE);
            JLabel genreLabel = new JLabel("Genre");
            genreLabel.setBounds(80, 130, 100, 30);
            genreLabel.setForeground(Color.WHITE);
            JComboBox<Movie.MovieGenre> genreComboBox = new JComboBox<>(Movie.MovieGenre.values());
            genreComboBox.setBounds(50, 160, 300, 30);
            genreComboBox.setBackground(new Color(50, 50, 50));
            genreComboBox.setForeground(Color.WHITE);
            genreComboBox.setEnabled(false);
            genreCheckBox.addActionListener(e1 -> genreComboBox.setEnabled(genreCheckBox.isSelected()));
            addMovieModal.add(genreCheckBox);
            addMovieModal.add(genreLabel);
            addMovieModal.add(genreComboBox);
        
            // Age Rating Label and ComboBox
            JCheckBox ageRatingCheckBox = new JCheckBox();
            ageRatingCheckBox.setBounds(50, 210, 20, 20);
            ageRatingCheckBox.setBackground(new Color(30, 30, 30));
            ageRatingCheckBox.setForeground(Color.WHITE);
            JLabel ageRatingLabel = new JLabel("Age Rating");
            ageRatingLabel.setBounds(80, 210, 100, 30);
            ageRatingLabel.setForeground(Color.WHITE);
            JComboBox<Movie.MovieAgeRating> ageRatingComboBox = new JComboBox<>(Movie.MovieAgeRating.values());
            ageRatingComboBox.setBounds(50, 240, 300, 30);
            ageRatingComboBox.setBackground(new Color(50, 50, 50));
            ageRatingComboBox.setForeground(Color.WHITE);
            ageRatingComboBox.setEnabled(false);
            ageRatingCheckBox.addActionListener(e1 -> ageRatingComboBox.setEnabled(ageRatingCheckBox.isSelected()));
            addMovieModal.add(ageRatingCheckBox);
            addMovieModal.add(ageRatingLabel);
            addMovieModal.add(ageRatingComboBox);
        
            // Rating Label and TextField
            JCheckBox ratingCheckBox = new JCheckBox();
            ratingCheckBox.setBounds(50, 300, 20, 20);
            ratingCheckBox.setBackground(new Color(30, 30, 30));
            ratingCheckBox.setForeground(Color.WHITE);
            JLabel ratingLabel = new JLabel("Rating (0.0 - 10.0)");
            ratingLabel.setBounds(80, 300, 150, 30);
            ratingLabel.setForeground(Color.WHITE);
            JTextField ratingTextField = new JTextField();
            ratingTextField.setBounds(50, 330, 300, 30);
            ratingTextField.setBackground(new Color(50, 50, 50));
            ratingTextField.setForeground(Color.WHITE);
            ratingTextField.setCaretColor(Color.WHITE);
            ratingTextField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            ratingTextField.setEnabled(false);
            ratingCheckBox.addActionListener(e1 -> ratingTextField.setEnabled(ratingCheckBox.isSelected()));
            addMovieModal.add(ratingCheckBox);
            addMovieModal.add(ratingLabel);
            addMovieModal.add(ratingTextField);
        
            // Trailer URL Label and TextField
            JCheckBox trailerUrlCheckBox = new JCheckBox();
            trailerUrlCheckBox.setBounds(380, 50, 20, 20);
            trailerUrlCheckBox.setBackground(new Color(30, 30, 30));
            trailerUrlCheckBox.setForeground(Color.WHITE);
            JLabel trailerUrlLabel = new JLabel("Trailer URL");
            trailerUrlLabel.setBounds(410, 50, 100, 30);
            trailerUrlLabel.setForeground(Color.WHITE);
            JTextField trailerUrlTextField = new JTextField();
            trailerUrlTextField.setBounds(380, 80, 300, 30);
            trailerUrlTextField.setBackground(new Color(50, 50, 50));
            trailerUrlTextField.setForeground(Color.WHITE);
            trailerUrlTextField.setCaretColor(Color.WHITE);
            trailerUrlTextField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            trailerUrlTextField.setEnabled(false);
            trailerUrlCheckBox.addActionListener(e1 -> trailerUrlTextField.setEnabled(trailerUrlCheckBox.isSelected()));
            addMovieModal.add(trailerUrlCheckBox);
            addMovieModal.add(trailerUrlLabel);
            addMovieModal.add(trailerUrlTextField);
        
            // Release Date Label and Date Picker
            JCheckBox releaseDateCheckBox = new JCheckBox();
            releaseDateCheckBox.setBounds(380, 130, 20, 20);
            releaseDateCheckBox.setBackground(new Color(30, 30, 30));
            releaseDateCheckBox.setForeground(Color.WHITE);
            JLabel releaseDateLabel = new JLabel("Release Date");
            releaseDateLabel.setBounds(410, 130, 100, 30);
            releaseDateLabel.setForeground(Color.WHITE);
            JSpinner releaseDateSpinner = new JSpinner(new SpinnerDateModel());
            releaseDateSpinner.setBounds(380, 160, 300, 30);
            releaseDateSpinner.setEditor(new JSpinner.DateEditor(releaseDateSpinner, "yyyy-MM-dd"));
            releaseDateSpinner.setBackground(new Color(50, 50, 50));
            releaseDateSpinner.setForeground(Color.WHITE);
            releaseDateSpinner.setEnabled(false);
            releaseDateCheckBox.addActionListener(e1 -> releaseDateSpinner.setEnabled(releaseDateCheckBox.isSelected()));
            addMovieModal.add(releaseDateCheckBox);
            addMovieModal.add(releaseDateLabel);
            addMovieModal.add(releaseDateSpinner);
        
            // Director Label and TextField
            JCheckBox directorCheckBox = new JCheckBox();
            directorCheckBox.setBounds(50, 380, 20, 20);
            directorCheckBox.setBackground(new Color(30, 30, 30));
            directorCheckBox.setForeground(Color.WHITE);
            JLabel directorLabel = new JLabel("Director");
            directorLabel.setBounds(80, 380, 100, 30);
            directorLabel.setForeground(Color.WHITE);
            JTextField directorTextField = new JTextField();
            directorTextField.setBounds(50, 410, 300, 30);
            directorTextField.setBackground(new Color(50, 50, 50));
            directorTextField.setForeground(Color.WHITE);
            directorTextField.setCaretColor(Color.WHITE);
            directorTextField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            directorTextField.setEnabled(false);
            directorCheckBox.addActionListener(e1 -> directorTextField.setEnabled(directorCheckBox.isSelected()));
            addMovieModal.add(directorCheckBox);
            addMovieModal.add(directorLabel);
            addMovieModal.add(directorTextField);
        
            // Description Label and TextArea
            JCheckBox descriptionCheckBox = new JCheckBox();
            descriptionCheckBox.setBounds(380, 210, 20, 20);
            descriptionCheckBox.setBackground(new Color(30, 30, 30));
            descriptionCheckBox.setForeground(Color.WHITE);
            JLabel descriptionLabel = new JLabel("Description");
            descriptionLabel.setBounds(410, 210, 100, 30);
            descriptionLabel.setForeground(Color.WHITE);
            JTextArea descriptionTextArea = new JTextArea();
            descriptionTextArea.setBounds(380, 240, 300, 100);
            descriptionTextArea.setBackground(new Color(50, 50, 50));
            descriptionTextArea.setForeground(Color.WHITE);
            descriptionTextArea.setCaretColor(Color.WHITE);
            descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
            ));
            descriptionTextArea.setLineWrap(true);
            descriptionTextArea.setWrapStyleWord(true);
            descriptionTextArea.setEnabled(false);
            descriptionCheckBox.addActionListener(e1 -> descriptionTextArea.setEnabled(descriptionCheckBox.isSelected()));
            addMovieModal.add(descriptionCheckBox);
            addMovieModal.add(descriptionLabel);
            addMovieModal.add(descriptionTextArea);
        
            // Image Panel
            JPanel imagePanel = new JPanel();
            imagePanel.setBounds(720, 130, 250, 300);
            imagePanel.setBackground(new Color(50, 50, 50));
            imagePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            addMovieModal.add(imagePanel);
        
            // Choose Image Button
            JCheckBox imageCheckBox = new JCheckBox();
            imageCheckBox.setBounds(720, 60, 20, 20);
            imageCheckBox.setBackground(new Color(30, 30, 30));
            imageCheckBox.setForeground(Color.WHITE);
            JLabel imageLabelHolder = new JLabel();
            String[] imagePathHolder = new String[1];
            RoundedButton chooseImageButton = new RoundedButton("Choose Image", 5);
            chooseImageButton.setBounds(750, 60, 130, 30);
            chooseImageButton.setForeground(Color.BLACK);
            chooseImageButton.setBackground(Color.WHITE);
            chooseImageButton.setEnabled(false);
            imageCheckBox.addActionListener(e1 -> chooseImageButton.setEnabled(imageCheckBox.isSelected()));
            chooseImageButton.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePathHolder[0] = selectedFile.getAbsolutePath();
                ImageIcon imageIcon = new ImageIcon(imagePathHolder[0]);
                Image image = imageIcon.getImage().getScaledInstance(imagePanel.getWidth(), imagePanel.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledImageIcon = new ImageIcon(image);
                imageLabelHolder.setIcon(scaledImageIcon);
                imagePanel.removeAll();
                imagePanel.add(imageLabelHolder);
                imagePanel.revalidate();
                imagePanel.repaint();
            }
            });
            addMovieModal.add(imageCheckBox);
            addMovieModal.add(chooseImageButton);
        
            // Add Movie Button
            RoundedButton addMovieButton2 = new RoundedButton("Edit Movie", 5);
            addMovieButton2.setBounds(380, 450, 130, 30);
            addMovieButton2.setForeground(Color.BLACK);
            addMovieButton2.setBackground(Color.WHITE);
            addMovieButton2.addActionListener(event -> {
            String title = titleTextField.getText();
            Movie.MovieGenre genre = (Movie.MovieGenre) genreComboBox.getSelectedItem();
            Movie.MovieAgeRating ageRating = (Movie.MovieAgeRating) ageRatingComboBox.getSelectedItem();
            String ratingText = ratingTextField.getText();
            String trailerUrl = trailerUrlTextField.getText();
            Date releaseDate = (Date) releaseDateSpinner.getValue();
            String director = directorTextField.getText();
            String description = descriptionTextArea.getText();
        
            if (!title.isEmpty() && genre != null && !ratingText.isEmpty() && !director.isEmpty() && !description.isEmpty() && imagePathHolder[0] != null) {
                try {
                float rating = Float.parseFloat(ratingText);
                if (rating < 0 || rating > 10) {
                    JOptionPane.showMessageDialog(null, "Rating must be between 0.0 and 10.0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                rating = Math.round(rating * 10) / 10.0f;
                LocalDate localReleaseDate = new java.sql.Date(releaseDate.getTime()).toLocalDate();
                boolean added = MovieManager.addMovieToDatabase(title, genre, description, rating, ageRating, imagePathHolder[0], trailerUrl, localReleaseDate, director);
                if (added) {
                    JOptionPane.showMessageDialog(null, "Movie added successfully!");
                    SwingUtilities.getWindowAncestor(addMovieModal).dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add movie. See console for details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid rating format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields and choose an image!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            });
            addMovieModal.add(addMovieButton2);
        
            // Cancel Button
            RoundedButton cancelButton = new RoundedButton("Cancel", 5);
            cancelButton.setBounds(550, 450, 130, 30);
            cancelButton.setForeground(Color.BLACK);
            cancelButton.setBackground(Color.WHITE);
            cancelButton.addActionListener(event -> SwingUtilities.getWindowAncestor(addMovieModal).dispose());
            addMovieModal.add(cancelButton);
        
            // Show the modal in a JDialog
            JDialog dialog = new JDialog();
            dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            dialog.setModal(true);
            dialog.getContentPane().add(addMovieModal);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //------------------------Right Things Movie------------

    JSeparator separator1Movie = new JSeparator();
    separator1Movie.setOrientation(SwingConstants.VERTICAL);
    separator1Movie.setBackground(Color.white);
    separator1Movie.setForeground(Color.white);
    separator1Movie.setBounds(780, 00, 2, 750);
    MoviesDashboard.add(separator1Movie);

    // right side number of movies
        JLabel nbrmovie = new JLabel("Total Number of Movies");
        nbrmovie.setBounds(815, 20, 200, 30);
        nbrmovie.setForeground(Color.WHITE);
        nbrmovie.setFont(new Font("Bebas Neue", Font.BOLD, 15));
        MoviesDashboard.add(nbrmovie);

        RoundedPanel CircleMovie = new RoundedPanel(100);
        CircleMovie.setLayout(null);
        CircleMovie.setBounds(855, 80, 100, 100);
        CircleMovie.setBackground(new Color(0, 0, 0, 0));
        CircleMovie.setRoundedBorder(Color.WHITE, 2);
        MoviesDashboard.add(CircleMovie);

        // Fetch the total number of movies using the method
        int totalMovies = MovieManager.numberofmovies();

        JLabel nbrTotalMovie = new JLabel();
        if (totalMovies < 10) {
            nbrTotalMovie.setBounds(40, 32, 100, 30);
            nbrTotalMovie.setText(String.valueOf(totalMovies));
        } else {
            nbrTotalMovie.setBounds(33, 32, 100, 30);
            nbrTotalMovie.setText(String.valueOf(totalMovies));
        }

        nbrTotalMovie.setForeground(Color.WHITE);
        nbrTotalMovie.setFont(new Font("Bebas Neue", Font.BOLD, 30));
        CircleMovie.add(nbrTotalMovie);

    JSeparator separatorRght1Movie = new JSeparator();
    separatorRght1Movie.setOrientation(SwingConstants.HORIZONTAL);
    separatorRght1Movie.setBackground(Color.white);
    separatorRght1Movie.setForeground(Color.white); 
    separatorRght1Movie.setBounds(790, 220, 220, 1);
    MoviesDashboard.add(separatorRght1Movie);

    JLabel NewReleaseMovielbl = new JLabel("Top Watched Movie");
    NewReleaseMovielbl.setBounds(830, 255, 250, 30);
    NewReleaseMovielbl.setForeground(Color.WHITE);
    NewReleaseMovielbl.setFont(new Font("Bebas Neue", Font.BOLD, 15));
    MoviesDashboard.add(NewReleaseMovielbl);

    JLabel NamereleaseMovie = new JLabel(); 
    NamereleaseMovie.setBounds(880, 300, 250, 30);
    NamereleaseMovie.setForeground(Color.WHITE);
    NamereleaseMovie.setFont(new Font("Roboto", Font.TYPE1_FONT, 13));
    NamereleaseMovie.setText(String.valueOf(movieManager.movies.get(movieManager.movies.size()-1).Title));
    MoviesDashboard.add(NamereleaseMovie);

    JSeparator separatorRght2Movie = new JSeparator();
    separatorRght2Movie.setOrientation(SwingConstants.HORIZONTAL);
    separatorRght2Movie.setBackground(Color.white);
    separatorRght2Movie.setForeground(Color.white);
    separatorRght2Movie.setBounds(790, 400, 220, 1);
    MoviesDashboard.add(separatorRght2Movie);

    JLabel MostPopularGenrelbl = new JLabel("Most Watched Genre");
    MostPopularGenrelbl.setBounds(830, 423, 250, 30);
    MostPopularGenrelbl.setForeground(Color.WHITE);
    MostPopularGenrelbl.setFont(new Font("Bebas Neue", Font.BOLD, 15));
    MoviesDashboard.add(MostPopularGenrelbl);

    JLabel MostPopularGenre = new JLabel();
    MostPopularGenre.setBounds(880, 460, 250, 30);
    MostPopularGenre.setForeground(Color.WHITE);
    MostPopularGenre.setFont(new Font("Roboto", Font.TYPE1_FONT, 13));
    String MostGenre = null;
    int maxappeare = 0;
    
        for (int i = 0; i < movieManager.movies.size(); i++) {
            String currentGenre = String.valueOf(movieManager.movies.get(i).Genre);
            int currentCount = 0;

            
            for (int j = 0; j <  movieManager.movies.size(); j++) {
                if (String.valueOf(movieManager.movies.get(j).Genre).equals(currentGenre)) {
                    currentCount++;
                }
            }

            
            if (currentCount > maxappeare) {
                maxappeare = currentCount;
                MostGenre = currentGenre;
            }
        }
    MostPopularGenre.setText(MostGenre);
    MoviesDashboard.add(MostPopularGenre);

    JPanel firstgenrepanel = new JPanel();
    firstgenrepanel.setBounds(820, 550, 12, 12);
    firstgenrepanel.setBackground(new Color(255,130,37));
    firstgenrepanel.setBorder(BorderFactory.createLineBorder(Color.white));
    MoviesDashboard.add(firstgenrepanel);

    JLabel firstgenre = new JLabel();
    firstgenre.setBounds(845,544,100,20);
    firstgenre.setForeground(Color.WHITE);
    firstgenre.setFont(new Font("Bebas Neue", Font.BOLD, 12));
    firstgenre.setText(MostGenre);
    MoviesDashboard.add(firstgenre);


    JPanel secondgenrepanel = new JPanel(); 
    secondgenrepanel.setBounds(820, 575, 12, 12);
    secondgenrepanel.setBackground(new  Color(180,63,63));
    secondgenrepanel.setBorder(BorderFactory.createLineBorder(Color.white));
    MoviesDashboard.add(secondgenrepanel);

    JLabel Secondgenre = new JLabel();
    Secondgenre.setBounds(845,569,100,20);
    Secondgenre.setForeground(Color.WHITE);
    Secondgenre.setFont(new Font("Bebas Neue", Font.BOLD, 12));
    Secondgenre.setText(MostGenre);
    MoviesDashboard.add(Secondgenre);

    JPanel thirdgenrepanel = new JPanel(); 
    thirdgenrepanel.setBounds(820, 600, 12, 12);
    thirdgenrepanel.setBackground(new Color(23,59,69));
    thirdgenrepanel.setBorder(BorderFactory.createLineBorder(Color.white));
    MoviesDashboard.add(thirdgenrepanel);

    JLabel thirdgenre = new JLabel();
    thirdgenre.setBounds(845,594,100,20);
    thirdgenre.setForeground(Color.WHITE);
    thirdgenre.setFont(new Font("Bebas Neue", Font.BOLD, 12));
    thirdgenre.setText(MostGenre);
    MoviesDashboard.add(thirdgenre);

    String Mostgenre = null;
    String secondMostFrequentMovie = null;
    String thirdMostFrequentMovie = null;
    int maxcounte = 0;
    int secondMaxCount = 0;
    int thirdMaxCount = 0;

    
    for (int i = 0; i < movieManager.movies.size(); i++) {
        String currentGenre = String.valueOf(movieManager.movies.get(i).Genre);
        int currentCount = 0;

 
        for (int j = 0; j < movieManager.movies.size(); j++) {
            if (String.valueOf(movieManager.movies.get(j).Genre).equals(currentGenre)) {
                currentCount++;
            }
        }

        
        if (currentCount > maxcounte) {

            thirdMaxCount = secondMaxCount;
            thirdMostFrequentMovie = secondMostFrequentMovie;

            secondMaxCount = maxcounte;
            secondMostFrequentMovie = mostFrequentMovie;

           
            maxcounte = currentCount;
            mostFrequentMovie = currentGenre;
        } else if (currentCount > secondMaxCount && !currentGenre.equals(mostFrequentMovie)) {

            thirdMaxCount = secondMaxCount;
            thirdMostFrequentMovie = secondMostFrequentMovie;

            secondMaxCount = currentCount;
            secondMostFrequentMovie = currentGenre;
        } else if (currentCount > thirdMaxCount && !currentGenre.equals(mostFrequentMovie) && !currentGenre.equals(secondMostFrequentMovie)) {

            thirdMaxCount = currentCount;
            thirdMostFrequentMovie = currentGenre;
        }
    }
    Secondgenre.setText(secondMostFrequentMovie);
    thirdgenre.setText(thirdMostFrequentMovie);

    JLabel nbrfirstgenrelbl = new JLabel();
    nbrfirstgenrelbl.setBounds(950,544,100,20);
    nbrfirstgenrelbl.setForeground(Color.WHITE);
    nbrfirstgenrelbl.setFont(new Font("Bebas Neue", Font.BOLD, 12));
    MoviesDashboard.add(nbrfirstgenrelbl);

    int count = 0;
    for(int i = 0; i < movieManager.movies.size(); i++){
        if(String.valueOf(movieManager.movies.get(i).Genre).equals(MostGenre)){
            count++;
        }
    }
    nbrfirstgenrelbl.setText(String.valueOf((count*100)/movieManager.movies.size() + "%"));

    count =0;
    JLabel nbrsecondgenrelbl = new JLabel();
    nbrsecondgenrelbl.setBounds(950,575,100,20);
    nbrsecondgenrelbl.setForeground(Color.WHITE);
    nbrsecondgenrelbl.setFont(new Font("Bebas Neue", Font.BOLD, 12));
    MoviesDashboard.add(nbrsecondgenrelbl);

    for(int i = 0; i < movieManager.movies.size(); i++){
        if(String.valueOf(movieManager.movies.get(i).Genre).equals(secondMostFrequentMovie)){
            count++;
        }
    }
    nbrsecondgenrelbl.setText(String.valueOf((count*100)/movieManager.movies.size() + "%"));

    JLabel nbrthirdgenrelbl = new JLabel();
    nbrthirdgenrelbl.setBounds(950,600,100,20);
    nbrthirdgenrelbl.setForeground(Color.WHITE);
    nbrthirdgenrelbl.setFont(new Font("Bebas Neue", Font.BOLD, 12));
    MoviesDashboard.add(nbrthirdgenrelbl);

    count = 0;
    for(int i = 0; i < movieManager.movies.size(); i++){
        if(String.valueOf(movieManager.movies.get(i).Genre).equals(thirdMostFrequentMovie)){
            count++;
        }
    }
    nbrthirdgenrelbl.setText(String.valueOf((count*100)/movieManager.movies.size() + "%"));


    JSeparator separatorRght3Movie = new JSeparator();
    separatorRght3Movie.setOrientation(SwingConstants.HORIZONTAL);
    separatorRght3Movie.setBackground(Color.white);
    separatorRght3Movie.setForeground(Color.white); 
    separatorRght3Movie.setBounds(790, 670, 220, 1);
    MoviesDashboard.add(separatorRght3Movie);

    JLabel DatetodayMovie = new JLabel("Date:");
    DatetodayMovie.setBounds(840, 675, 200, 30);
    DatetodayMovie.setForeground(Color.WHITE);
    DatetodayMovie.setFont(new Font("Bebas Neue", Font.BOLD, 13));
    MoviesDashboard.add(DatetodayMovie);
    
    JLabel datenowMovie = new JLabel(LocalDate.now().toString());
    datenowMovie.setBounds(880, 675, 200, 30);
    datenowMovie.setForeground(Color.WHITE);
    datenowMovie.setFont(new Font("Bebas Neue", Font.BOLD, 13));
    MoviesDashboard.add(datenowMovie);

    





        UserInsightsDashboard = new JPanel();
        UserInsightsDashboard.setLayout(null);
        UserInsightsDashboard.setBounds(100, 0, 1200, 750);
        UserInsightsDashboard.setBackground(new Color(30 ,30,30));
        MiddlePanel.add(UserInsightsDashboard);

        JPanel UserInsightsDashboard = new JPanel();
        UserInsightsDashboard.setLayout(null);
        UserInsightsDashboard.setBounds(100, 0, 1200, 750);
        UserInsightsDashboard.setBackground(new Color(30 ,30,30));
        MiddlePanel.add(UserInsightsDashboard);


        JLabel UserInsightlbl = new JLabel("User Insights Dashboard");
        UserInsightlbl.setBounds(35, 26, 300, 30);
        UserInsightlbl.setForeground(Color.WHITE);
        UserInsightlbl.setFont(new Font("Bebas Neue", Font.BOLD, 23));
        UserInsightsDashboard.add(UserInsightlbl);



        JSeparator separatorhorUser = new JSeparator();
        separatorhorUser.setOrientation(SwingConstants.HORIZONTAL);
        separatorhorUser.setBackground(Color.white);
        separatorhorUser.setForeground(Color.white); 
        separatorhorUser.setBounds(21, 120, 720, 1);
        UserInsightsDashboard.add(separatorhorUser);

        JLabel selectedlblUser = new JLabel ("Selected");
        selectedlblUser.setBounds(20, 87, 60, 30);
        selectedlblUser.setForeground(Color.white);
        selectedlblUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        UserInsightsDashboard.add(selectedlblUser);

        JLabel NamelblUser = new JLabel("Name");
        NamelblUser.setBounds(110, 87, 60, 30);
        NamelblUser.setForeground(Color.WHITE);
        NamelblUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        UserInsightsDashboard.add(NamelblUser);

        JLabel EmaillblUser = new JLabel("Email");
        EmaillblUser.setBounds(235, 87, 60, 30);
        EmaillblUser.setForeground(Color.WHITE);
        EmaillblUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        UserInsightsDashboard.add(EmaillblUser);

        JLabel PhoneNumberLblUser = new JLabel("PhoneNumber");
        PhoneNumberLblUser.setBounds(380, 87, 80, 30);
        PhoneNumberLblUser.setForeground(Color.WHITE);
        PhoneNumberLblUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        UserInsightsDashboard.add(PhoneNumberLblUser); 
        
        JLabel AgelblUser = new JLabel("Age");
        AgelblUser.setBounds(570, 87, 60, 30);
        AgelblUser.setForeground(Color.WHITE);
        AgelblUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        UserInsightsDashboard.add(AgelblUser); 

        JLabel BalancelblUser = new JLabel("Balance");
        BalancelblUser.setBounds(655, 87, 60, 30);
        BalancelblUser.setForeground(Color.WHITE);
        BalancelblUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
        UserInsightsDashboard.add(BalancelblUser); 

        JPanel UsersPanelList = new JPanel();
        UsersPanelList.setLayout(null);
        UsersPanelList.setBounds(20, 130, 700, 440);
        UsersPanelList.setBackground(new Color(30, 30, 30));

        JPanel contentPanelUserList = new JPanel();
        contentPanelUserList.setLayout(null);
        contentPanelUserList.setBackground(new Color(30, 30, 30));

        int totalHeightUserList = clientManager.clients.size() * 50; 
        contentPanelUserList.setPreferredSize(new Dimension(650, Math.max(500, totalHeightUserList)));

        ArrayList<JCheckBox> checkBoxesUserList = new ArrayList<JCheckBox>();

        try (Connection conn = DatabaseConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

            int rowIndex = 0;
            while (rs.next()) {
                JPanel UserListRow = new JPanel();
                UserListRow.setLayout(null);
                UserListRow.setBounds(0, rowIndex * 50, 700, 40);
                UserListRow.setBackground(new Color(30, 30, 30));

                JLabel NameUser = new JLabel(rs.getString("Name"));
                NameUser.setBounds(67, 5, 200, 30);
                NameUser.setForeground(Color.white);
                NameUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                UserListRow.add(NameUser);

                JLabel EmailUser = new JLabel(rs.getString("Email"));
                EmailUser.setBounds(190, 5, 200, 30);
                EmailUser.setForeground(Color.white);
                EmailUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                UserListRow.add(EmailUser);

                JLabel PhonenumberUser = new JLabel(rs.getString("PhoneNumber"));
                PhonenumberUser.setBounds(370, 5, 200, 30);
                PhonenumberUser.setForeground(Color.white);
                PhonenumberUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                UserListRow.add(PhonenumberUser);

                JLabel AgeUser = new JLabel(String.valueOf(rs.getInt("Age")));
                AgeUser.setBounds(540, 5, 200, 30);
                AgeUser.setForeground(Color.white);
                AgeUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                UserListRow.add(AgeUser);

                JLabel BalanceUser = new JLabel(String.valueOf(rs.getInt("Balance")));
                BalanceUser.setBounds(635, 5, 400, 30);
                BalanceUser.setForeground(Color.white);
                BalanceUser.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                UserListRow.add(BalanceUser);

                JCheckBox selectCheckBox = new JCheckBox();
                selectCheckBox.setBounds(0, 5, 20, 20);
                selectCheckBox.setBackground(new Color(30, 30, 30));
                selectCheckBox.setForeground(Color.white);
                selectCheckBox.setFont(new Font("Bebas Neue", Font.PLAIN, 13));
                UserListRow.add(selectCheckBox);
                checkBoxesUserList.add(selectCheckBox);

                contentPanelUserList.add(UserListRow);
                rowIndex++;
            }

            contentPanelUserList.setPreferredSize(new Dimension(650, Math.max(500, rowIndex * 50)));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching user data from the database!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane scrollPanelUserList = new JScrollPane(contentPanelUserList);
        scrollPanelUserList.setBounds(35, 130, 700, 440);
        scrollPanelUserList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPanelUserList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelUserList.setBorder(null);
        scrollPanelUserList.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanelUserList.getViewport().setBackground(new Color(30, 30, 30));

        contentPanelUserList.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPanelUserList.getVerticalScrollBar();
            int notches = e.getWheelRotation();
            int currentValue = verticalScrollBar.getValue();
            int scrollAmount = 30; 
            verticalScrollBar.setValue(currentValue + (notches * scrollAmount));
        });

        UserInsightsDashboard.add(scrollPanelUserList);

        RoundedButton UnSelectAllUser = new RoundedButton("Unselected All" , 5);
        UnSelectAllUser.setBounds(20, 650, 120, 30);
        UnSelectAllUser.setForeground(Color.BLACK);
        UnSelectAllUser.setBackground(Color.white);
        UnSelectAllUser.addActionListener(e->{
            for(JCheckBox checkBox : checkBoxesUserList) {
                if(checkBox.isSelected()){
                    checkBox.setSelected(false);
                }
                
            }
        });
        UserInsightsDashboard.add(UnSelectAllUser);
        
        RoundedButton selectAllUser = new RoundedButton("Selected All" , 5);
        selectAllUser.setBounds(180, 650, 120, 30);
        selectAllUser.setForeground(Color.BLACK);
        selectAllUser.setBackground(Color.white);
        selectAllUser.addActionListener(e->{
            for(JCheckBox checkBox : checkBoxesUserList) {
                if(!checkBox.isSelected()){
                    checkBox.setSelected(true);
                }
            }
        });
        UserInsightsDashboard.add(selectAllUser);

        //-------right things

        JSeparator separator1User = new JSeparator();
        separator1User.setOrientation(SwingConstants.VERTICAL);
        separator1User.setBackground(Color.white);
        separator1User.setForeground(Color.white);
        separator1User.setBounds(780, 00, 2, 750);
        UserInsightsDashboard.add(separator1User);
        

        JLabel nbrUser = new JLabel("Number of Users");
        nbrUser.setBounds(840, 20, 200, 30);
        nbrUser.setForeground(Color.WHITE);
        nbrUser.setFont(new Font("Bebas Neue", Font.BOLD, 16));
        UserInsightsDashboard.add(nbrUser);


        RoundedPanel CircleUser = new RoundedPanel(100);
        CircleUser.setLayout(null);
        CircleUser.setBounds(855, 80, 100, 100);
        CircleUser.setBackground( new Color(0, 0, 0, 0));
        CircleUser.setRoundedBorder(Color.WHITE, 2);
        UserInsightsDashboard.add(CircleUser);

        JLabel nbrUserLabel = new JLabel();
         if(clientManager.clients.size() < 10){
            nbrUserLabel.setBounds(40, 32, 100, 30);
            nbrUserLabel.setText(String.valueOf(clientManager.clients.size()));

         }else{
            nbrUserLabel.setBounds(33, 32, 100, 30);
            nbrUserLabel.setText(String.valueOf(clientManager.clients.size()));
         }
        
        nbrUserLabel.setForeground(Color.WHITE);
        nbrUserLabel.setFont(new Font("Bebas Neue", Font.BOLD, 30));
        CircleUser.add(nbrUserLabel);
        
        JSeparator separatorRght1User = new JSeparator();
        separatorRght1User.setOrientation(SwingConstants.HORIZONTAL);
        separatorRght1User.setBackground(Color.white);
        separatorRght1User.setForeground(Color.white); 
        separatorRght1User.setBounds(790, 240, 220, 1);
        UserInsightsDashboard.add(separatorRght1User);



        RoundedPanel Circle2User = new RoundedPanel(100);
        Circle2User.setLayout(null);
        Circle2User.setBounds(855, 320, 100, 100);
        Circle2User.setBackground(new Color(0, 0, 0, 0));
        Circle2User.setRoundedBorder(Color.WHITE, 2);
        UserInsightsDashboard.add(Circle2User);

        JLabel AvgBalancelbl = new JLabel("Average Balance per user ");
        AvgBalancelbl.setBounds(800, 260, 200, 30);
        AvgBalancelbl.setForeground(Color.WHITE);
        AvgBalancelbl.setFont(new Font("Bebas Neue", Font.BOLD, 16));
        UserInsightsDashboard.add(AvgBalancelbl);

        JLabel AvgBalance = new JLabel();
        int AvrgBalance = ClientManager.averagebalance(); // Use the averagebalance method

        if (AvrgBalance < 10) {
            AvgBalance.setBounds(30, 34, 100, 30);
            AvgBalance.setFont(new Font("Bebas Neue", Font.BOLD, 24));
            AvgBalance.setText(String.valueOf(AvrgBalance) + "da");
        } else if (AvrgBalance > 10 && AvrgBalance < 1000) {
            AvgBalance.setBounds(20, 32, 100, 30);
            AvgBalance.setFont(new Font("Bebas Neue", Font.BOLD, 24));
            AvgBalance.setText(String.valueOf(AvrgBalance) + "da");
        } else if (AvrgBalance > 1000) {
            AvgBalance.setBounds(15, 32, 100, 30);
            AvgBalance.setFont(new Font("Bebas Neue", Font.BOLD, 19));
            AvgBalance.setText(String.valueOf(AvrgBalance) + "da");
        }

        AvgBalance.setForeground(Color.WHITE);
        Circle2User.add(AvgBalance);


        JSeparator separatorRght2User = new JSeparator();
        separatorRght2User.setOrientation(SwingConstants.HORIZONTAL);
        separatorRght2User.setBackground(Color.white);
        separatorRght2User.setForeground(Color.white); 
        separatorRght2User.setBounds(790, 470, 220, 1);
        UserInsightsDashboard.add(separatorRght2User);

        JLabel AvregeAgelbl = new JLabel("Most Common Age Groups");
        AvregeAgelbl.setBounds(820, 490, 200, 30);
        AvregeAgelbl.setForeground(Color.WHITE);
        AvregeAgelbl.setFont(new Font("Bebas Neue", Font.BOLD, 13));
        UserInsightsDashboard.add(AvregeAgelbl);

        JLabel AvrgAge = new JLabel();
        int avrgAge = 0;
        int sumAge = 0;
        for (Client client : clientManager.clients) {
            sumAge += client.Age;
        }
        avrgAge = sumAge / clientManager.clients.size();
        AvrgAge.setText(String.valueOf(avrgAge));
        AvrgAge.setBounds(890, 530, 100, 20);
        AvrgAge.setForeground(Color.WHITE);
        AvrgAge.setFont(new Font("Bebas Neue", Font.BOLD, 19));
        UserInsightsDashboard.add(AvrgAge);

        JPanel down20 = new JPanel();
        down20.setBounds(820, 575, 12, 12);
        down20.setBackground(new Color(180,63,63));
        down20.setBorder(BorderFactory.createLineBorder(Color.white));
        UserInsightsDashboard.add(down20);

        JLabel down20lbl = new JLabel(">20");
        down20lbl.setBounds(845,569,100,20);
        down20lbl.setForeground(Color.WHITE);
        down20lbl.setFont(new Font("Bebas Neue", Font.BOLD, 12));
        UserInsightsDashboard.add(down20lbl);

        JLabel nbrofdown20lbl = new JLabel();
        nbrofdown20lbl.setBounds(950,569,100,20);
        nbrofdown20lbl.setForeground(Color.WHITE);
        nbrofdown20lbl.setFont(new Font("Bebas Neue", Font.BOLD, 12));
        UserInsightsDashboard.add(nbrofdown20lbl);


        JPanel btw21_30 = new JPanel();
        btw21_30.setBounds(820, 600, 12, 12);
        btw21_30.setBackground(new Color(255,130,37));
        btw21_30.setBorder(BorderFactory.createLineBorder(Color.white));
        UserInsightsDashboard.add(btw21_30);


        JLabel btw21_30lbl = new JLabel("21 - 30");
        btw21_30lbl.setBounds(845,594,100,20);
        btw21_30lbl.setForeground(Color.WHITE);
        btw21_30lbl.setFont(new Font("Bebas Neue", Font.BOLD, 12));
        UserInsightsDashboard.add(btw21_30lbl);

        JLabel nbrbtw21_30lbl = new JLabel();
        nbrbtw21_30lbl.setBounds(950,594,100,20);
        nbrbtw21_30lbl.setForeground(Color.WHITE);
        nbrbtw21_30lbl.setFont(new Font("Bebas Neue", Font.BOLD, 12));
        UserInsightsDashboard.add(nbrbtw21_30lbl);


        JPanel up30 = new JPanel();
        up30.setBounds(820, 625, 12, 12);
        up30.setBackground(new Color(23,59,69));
        up30.setBorder(BorderFactory.createLineBorder(Color.white));
        UserInsightsDashboard.add(up30);


        JLabel up30lbl = new JLabel("<30");
        up30lbl.setBounds(845,619,100,20);
        up30lbl.setForeground(Color.WHITE);
        up30lbl.setFont(new Font("Bebas Neue", Font.BOLD, 12));
        UserInsightsDashboard.add(up30lbl);

        JLabel nbrup30lbl = new JLabel();
        nbrup30lbl.setBounds(950,619,100,20);
        nbrup30lbl.setForeground(Color.WHITE);
        nbrup30lbl.setFont(new Font("Bebas Neue", Font.BOLD, 12));
        UserInsightsDashboard.add(nbrup30lbl);

        int nbrup30 = 0;
        for (Client client : clientManager.clients) {
            if(client.Age > 30)//up30
                nbrup30++;
        }
        int nbrofdown20 = 0;
        for (Client client : clientManager.clients) {
            if(client.Age<20)//down 20
                nbrofdown20++;
        }
        int nbrbtw21_30 = 0;
        for (Client client : clientManager.clients) {
            if(client.Age >= 21 && client.Age <= 30)//btw 21 30
                nbrbtw21_30++;
        }

        nbrup30lbl.setText(String.valueOf((nbrup30 * 100)/clientManager.clients.size()) + "%");
        nbrbtw21_30lbl.setText(String.valueOf((nbrbtw21_30 * 100)/clientManager.clients.size()) + "%");
        nbrofdown20lbl.setText(String.valueOf((nbrofdown20 * 100)/clientManager.clients.size()) + "%");


        JSeparator separatorRght3User = new JSeparator();
        separatorRght3User.setOrientation(SwingConstants.HORIZONTAL);
        separatorRght3User.setBackground(Color.white);
        separatorRght3User.setForeground(Color.white); 
        separatorRght3User.setBounds(790, 670, 220, 1);
        UserInsightsDashboard.add(separatorRght3User);




        JLabel DatetodayUser = new JLabel("Date:");
        DatetodayUser.setBounds(840, 675, 200, 30);
        DatetodayUser.setForeground(Color.WHITE);
        DatetodayUser.setFont(new Font("Bebas Neue", Font.BOLD, 13));
        UserInsightsDashboard.add(DatetodayUser);
        
        JLabel datenowUser = new JLabel(LocalDate.now().toString());
        datenowUser.setBounds(880, 675, 200, 30);
        datenowUser.setForeground(Color.WHITE);
        datenowUser.setFont(new Font("Bebas Neue", Font.BOLD, 13));
        UserInsightsDashboard.add(datenowUser);



//---------------------fin middle ----------------------








        JPanel cardContainer = new JPanel();
        cardContainer.setLayout(new CardLayout());
        cardContainer.setBounds(0, 0, 1200, 750);
        cardContainer.add(BroadcastDashboard, "broadcast");
        cardContainer.add(MoviesDashboard, "movies");
        cardContainer.add(UserInsightsDashboard, "users");
        cardContainer.add(HomeDashboard, "home");
        cardContainer.add(theaterDashboard,"theater");


        ((CardLayout) cardContainer.getLayout()).show(cardContainer, "movies");


        MiddlePanel.setLayout(null);
        MiddlePanel.add(cardContainer);



        RoundedButton homebutton = new RoundedButton("Home", 20);
        homebutton.setBounds(10, 100, 150, 40);
        homebutton.setForeground(Color.WHITE);
        homebutton.setBackground(new Color(0, 0, 0, 0));
        homebutton.setContentAreaFilled(false);
        homebutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        homebutton.setHorizontalAlignment(SwingConstants.LEFT);
        homebutton.setHoverEffect(new Color(0, 0, 0 , 0), new Color(40, 40, 40));
        homebutton.addActionListener(e -> ((CardLayout) cardContainer.getLayout()).show(cardContainer, "home"));
        LeftPanel.add(homebutton);

        RoundedButton moviesButton = new RoundedButton("Movies", 20);
        moviesButton.setBounds(10, 160, 150, 40);
        moviesButton.setForeground(Color.WHITE);
        moviesButton.setBackground(new Color(0, 0, 0, 0));
        moviesButton.setContentAreaFilled(false);
        moviesButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        moviesButton.setHorizontalAlignment(SwingConstants.LEFT);
        moviesButton.addActionListener(e -> {
            isPanelVisible = false;
            ((CardLayout) cardContainer.getLayout()).show(cardContainer, "movies");
        });
        moviesButton.setHoverEffect(new Color(0, 0, 0 , 0), new Color(40, 40, 40));
        LeftPanel.add(moviesButton);

        RoundedButton usersButton = new RoundedButton("Users", 20);
        usersButton.setBounds(10, 220, 150, 40);
        usersButton.setForeground(Color.WHITE);
        usersButton.setBackground(new Color(0, 0, 0, 0));
        usersButton.setContentAreaFilled(false);
        usersButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usersButton.setHorizontalAlignment(SwingConstants.LEFT);
        usersButton.addActionListener(e -> {
            isPanelVisible = false;
            ((CardLayout) cardContainer.getLayout()).show(cardContainer, "users");
        });
        usersButton.setHoverEffect(new Color(0, 0, 0 , 0), new Color(40, 40, 40));
        LeftPanel.add(usersButton);

        RoundedButton Theaterbutton = new RoundedButton("Theater", 20);
        Theaterbutton.setBounds(10, 280, 150, 40);
        Theaterbutton.setForeground(Color.WHITE);
        Theaterbutton.setBackground(new Color(0, 0, 0, 0));
        Theaterbutton.setContentAreaFilled(false);
        Theaterbutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        Theaterbutton.setHorizontalAlignment(SwingConstants.LEFT);
        Theaterbutton.setHoverEffect(new Color(0, 0, 0 , 0), new Color(40, 40, 40));
        Theaterbutton.addActionListener(e->{
            isPanelVisible = false;
            ((CardLayout) cardContainer.getLayout()).show(cardContainer, "theater");
        });
        LeftPanel.add(Theaterbutton);

        RoundedButton broadcastButton = new RoundedButton("Broadcast", 20);
        broadcastButton.setBounds(10, 350, 150, 40);
        broadcastButton.setForeground(Color.WHITE);
        broadcastButton.setBackground(new Color(0, 0, 0, 0));
        broadcastButton.setContentAreaFilled(false);
        broadcastButton.setLayout(null);
        broadcastButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        broadcastButton.setHorizontalAlignment(SwingConstants.LEFT);
        broadcastButton.addActionListener(e ->{
            isPanelVisible = false;
            ((CardLayout) cardContainer.getLayout()).show(cardContainer, "broadcast");
        });
        broadcastButton.setHoverEffect(new Color(0, 0, 0 , 0), new Color(40, 40, 40));
        LeftPanel.add(broadcastButton);

        JSeparator separatorLeft = new JSeparator();
        separatorLeft.setOrientation(SwingConstants.HORIZONTAL);
        separatorLeft.setBackground(Color.white);
        separatorLeft.setForeground(Color.white);
        separatorLeft.setBounds(10, 460, 150, 1);
        LeftPanel.add(separatorLeft);

        RoundedButton Logoutbutton = new RoundedButton("Log out", 20);
        Logoutbutton.setBounds(10, 630, 150, 40);
        Logoutbutton.setForeground(Color.WHITE);
        Logoutbutton.setBackground(new Color(0, 0, 0, 0));
        Logoutbutton.setContentAreaFilled(false);
        Logoutbutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        Logoutbutton.setHorizontalAlignment(SwingConstants.LEFT);
        Logoutbutton.setHoverEffect(new Color(0, 0, 0 , 0), new Color(40, 40, 40));
        Logoutbutton.addActionListener(e ->{
            int response = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure you want to proceed?", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION
            );
            if (response == JOptionPane.YES_OPTION) {
                MainCardLayout.show(MainPanel, "LogIn");
            }  
        });
        LeftPanel.add(Logoutbutton);
        
        return panel;
    }
   





    // handle events ------------------------------------------------------------------------
    public void HandleLogIn(String email, String password) {
        // handle log in
    }

    public void HandleSignIn(JTextField username, JPasswordField password, JTextField email, JTextField cardNumber, 
    JTextField ccvnbr, JTextField PhoneNumber) {
        // handle SignIn
    }



     public ImageIcon resizedIcon(String path , int width , int height){ // hadi bch tbdl l img l size li rak habo w trj3 direct ImageIcon t7yo direct f label ou f button

        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        
        return  resizedIcon;
    }

    public void validateInput(JTextField textField, String regex) { // hadi tcoloriyi l border ta3 textfield 3la hsab type li rak hab l user ydkhlo f textfield

        String input = textField.getText().trim();
        if (input.equals("   Movie Name") || input.equals("   Film duration")) {
            return;
        }
        if (input.isEmpty()) {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED));
            return;
        }


        if (input.matches(regex)) {
            textField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        } else {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED)); 
         //   JOptionPane.showMessageDialog(this, "Please enter a valid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void TextAreaBehave(JTextArea textField, String placeholder) {// hadi tktb dakhel l textarea ida makach focus 3lih ida kayen tfasi klch ida tnha l focus w user maktb walo t3awed tktb wch kan fiha
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    SwingUtilities.invokeLater(() -> {
                        textField.setText("");
                        textField.setForeground(Color.WHITE);
                    });
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        textField.setText(placeholder);
                        textField.setForeground(Color.GRAY);
                    });
                }
            }
        });
    }

    public void TextfieldBehave(JTextField textField, String placeholder) {// kima TextAreaBehave nrk hadi 3la textfield mais brk 
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    SwingUtilities.invokeLater(() -> {
                        textField.setText("");
                        textField.setForeground(Color.WHITE);
                    });
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        textField.setText(placeholder);
                        textField.setForeground(Color.GRAY);
                    });
                }
            }
        });
    }

    public void slidePanel(JPanel panel, int startX, int endX, int y, int width, int height) {
        javax.swing.Timer slideTimer = new javax.swing.Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentX = panel.getX();
                int step = 10; // Adjust the step size for smoother/faster animation
    
                if (startX < endX) {
                    // Sliding to the right
                    if (currentX + step >= endX) {
                        panel.setBounds(endX, y, width, height);
                        ((javax.swing.Timer) e.getSource()).stop();
                    } else {
                        panel.setBounds(currentX + step, y, width, height);
                    }
                } else {
                    // Sliding to the left
                    if (currentX - step <= endX) {
                        panel.setBounds(endX, y, width, height);
                        ((javax.swing.Timer) e.getSource()).stop();
                    } else {
                        panel.setBounds(currentX - step, y, width, height);
                    }
                }
            }
        });
        slideTimer.start();
    }

    public static void main(String[] args) {
        try {
            CinemaApp Frame = new CinemaApp();
            Frame.setVisible(true);

            // Revalidate and repaint to ensure the SettingsPanel is displayed
            Frame.revalidate();
            Frame.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }


    
    // create components ------------------------------------------------------------------
    public JPanel CreateBackGroundPanel() {

        JPanel BackgroundPanel = new JPanel();
        BackgroundPanel.setBounds(0, 0, 1200, 750);
        BackgroundPanel.setLayout(null);
        BackgroundPanel.setOpaque(false);

        // Blur panel--------------------------------------------------
        this.BlurPanel = new TransparentPanel(0.75f);
        BlurPanel.setBounds(0, 0, 1200, 750);
        BlurPanel.setLayout(null);
        BlurPanel.setBackground(new java.awt.Color(0x2D3142));

        BlurPanel.setVisible(true);

        // The background image:
        ImageIcon originalIcon = new ImageIcon(
                "Poo2-TRY-\\Fahd\\bookingTICKET\\Images\\MilesBackground.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(BackgroundPanel.getWidth(), BackgroundPanel.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel Background = new JLabel(scaledIcon);
        Background.setBounds(0, 0, BackgroundPanel.getWidth(), BackgroundPanel.getHeight());

        JPanel RedPanel = new JPanel();
        RedPanel.setBounds(0, 710, 1200, 60);
        RedPanel.setBackground(new java.awt.Color(0x550000));

        BackgroundPanel.add(RedPanel);

        JPanel BluePanel = new JPanel();
        BluePanel.setBounds(0, 725, 1200, 60);
        BluePanel.setBackground(new java.awt.Color(0x000044));

        BackgroundPanel.add(BluePanel);

        BackgroundPanel.add(Background);
        BackgroundPanel.add(BlurPanel);

        BackgroundPanel.setComponentZOrder(Background, 2);
        BackgroundPanel.setComponentZOrder(BlurPanel, 0);
        BackgroundPanel.setComponentZOrder(BluePanel, 0);
        BackgroundPanel.setComponentZOrder(RedPanel, 1);

        return BackgroundPanel;
    }

    public JPanel CreateWelcomePanel(){
        JPanel WelcomeElements = new JPanel();
        WelcomeElements.setBounds(0, 0, 1200, 750);
        WelcomeElements.setLayout(null);
        WelcomeElements.setOpaque(false);

        JLabel LogoName = new JLabel("POOMovie");
        LogoName.setBounds(70, 24, 192, 52);
        LogoName.setFont(new Font("Inter", Font.BOLD, 35));
        LogoName.setForeground(Color.red);

        WelcomeElements.add(LogoName);

        ImageIcon iconMovie = new ImageIcon("Poo2-TRY-/Rayan/bookingTICKET/img/movie.png");
        JLabel imageLabel = new JLabel(iconMovie);
        imageLabel.setBounds(10, 20, 64, 64);

        WelcomeElements.add(imageLabel);

        JLabel WelcomText1 = new JLabel("Unlimited films in theaters,");// and more to come
        WelcomText1.setBounds(332, 222, 575, 115);
        WelcomText1.setFont(new Font("Inter", Font.BOLD, 40));
        WelcomText1.setForeground(Color.white);

        JLabel WelcomText2 = new JLabel("and more to come");
        WelcomText2.setBounds(400, 270, 575, 115);
        WelcomText2.setFont(new Font("Inter", Font.BOLD, 40));
        WelcomText2.setForeground(Color.white);

        WelcomeElements.add(WelcomText1);
        WelcomeElements.add(WelcomText2);

        JLabel DescriptionText = new JLabel("Experience the magic of PooMovie, where unlimited films await you.");
        DescriptionText.setBounds(340, 310, 1160, 115);
        DescriptionText.setFont(new Font("Inter", Font.BOLD, 15));
        DescriptionText.setForeground(Color.white);

        WelcomeElements.add(DescriptionText);

        // Sign in and Log in buttons ------------------------------------
        JButton LogIn = new JButton("log In");
        LogIn.setBounds(1090, 25, 75, 30);
        LogIn.setBackground(Color.RED);
        LogIn.setForeground(Color.white);
        LogIn.setFont(new Font("Inter", Font.BOLD, 15));
        LogIn.setFocusable(false);
        LogIn.setUI(new RoundButtonUI(new Color(0x000000)));

        LogIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LogIn.setBackground(new Color(0x550000));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                LogIn.setBackground(new Color(255, 0, 0));
            }
        });

        LogIn.addActionListener(e -> {
            MainCardLayout.show(MainPanel, "LogIn");
        });

        WelcomeElements.add(LogIn);

        JButton SignIn = new JButton("Sign in");
        SignIn.setBounds(790, 430, 130, 45);
        SignIn.setBackground(Color.RED);
        SignIn.setForeground(Color.white);
        SignIn.setFont(new Font("Inter", Font.BOLD, 15));
        SignIn.setFocusable(false);
        SignIn.setUI(new RoundButtonUI(new Color(0x000000)));

        SignIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SignIn.setBackground(new Color(0x550000));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                SignIn.setBackground(new Color(255, 0, 0));
            }
        });

        SignIn.addActionListener(e -> {
            MainCardLayout.show(MainPanel, "SignIn");
        });

        WelcomeElements.add(SignIn);

        JTextField AdressInput = new JTextField();
        AdressInput.setBounds(285, 430, 500, 45);
        AdressInput.setFont(new Font("Arial", Font.BOLD, 20));
        AdressInput.setBackground(new java.awt.Color(0xBBBBBB));
        AdressInput.setForeground(Color.BLACK);
        AdressInput.setCaretColor(Color.WHITE);
        TextfieldBehave(AdressInput, "Enter your email address");

        WelcomeElements.add(AdressInput);

        JButton ExitButton = new JButton("Exit");
        ExitButton.setBounds(950, 25, 130, 30);
        ExitButton.setBackground(Color.WHITE);
        ExitButton.setFont(new Font("Inter", Font.BOLD, 12));
        ExitButton.setFocusable(false);
        ExitButton.setUI(new RoundButtonUI(new Color(0x000000)));

        ExitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ExitButton.setBackground(new Color(0x999999));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                ExitButton.setBackground(Color.WHITE);
            }
        });

        ExitButton.addActionListener(e -> {
            System.exit(0);
        });

        WelcomeElements.add(ExitButton);

        return WelcomeElements;
    }

    public JPanel CreateSignInElementsPanel() {
        JPanel SignInElements = new JPanel();
        SignInElements.setBounds(0, 0, 1200, 750);
        SignInElements.setLayout(null);
        SignInElements.setOpaque(false);

        JLabel LogoName = new JLabel("POOMovie");
        LogoName.setBounds(70, 24, 192, 52);
        LogoName.setFont(new Font("Inter", Font.BOLD, 35));
        LogoName.setForeground(Color.red);

        SignInElements.add(LogoName);

        ImageIcon iconMovie = new ImageIcon("Poo2-TRY-/Rayan/bookingTICKET/img/movie.png");
        JLabel imageLabel = new JLabel(iconMovie);
        imageLabel.setBounds(10, 20, 64, 64);

        SignInElements.add(imageLabel);

        // signUP panel -----------------------------------------------------------
        TransparentPanel SignUpPanel = new TransparentPanel(0.5f);
        SignUpPanel.setBounds(400, 100, 440, 620);
        SignUpPanel.setLayout(null);
        SignUpPanel.setBackground(Color.black);

        JLabel SigninLabel = new JLabel("Sign In");
        SigninLabel.setBounds(50, 40, 200, 50);
        SigninLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        SigninLabel.setForeground(Color.white);

        SignUpPanel.add(SigninLabel);

        JTextField UserNameField = new JTextField();
        UserNameField.setBounds(50, 120, 340, 40);
        UserNameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        UserNameField.setForeground(Color.black);
        UserNameField.setCaretColor(Color.black);
        UserNameField.setBackground(new Color(0xBBBBBB));
        UserNameField.setOpaque(true);
        TextfieldBehave(UserNameField, "    Username");
        SignUpPanel.add(UserNameField);

        JPasswordField PasswordsField = new JPasswordField("    Password");
        PasswordsField.setBounds(50, 200, 340, 40);
        PasswordsField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        PasswordsField.setForeground(Color.black);
        PasswordsField.setCaretColor(Color.black);
        PasswordsField.setBackground(new Color(0xBBBBBB));
        PasswordsField.setOpaque(true);
        TextfieldBehave(PasswordsField, "    Password");
        SignUpPanel.add(PasswordsField);

        JTextField EmailField = new JTextField("   email@example.com");
        EmailField.setBounds(50, 280, 340, 40);
        EmailField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        EmailField.setForeground(Color.black);
        EmailField.setCaretColor(Color.black);
        EmailField.setBackground(new Color(0xBBBBBB));
        EmailField.setOpaque(true);

        TextfieldBehave(EmailField, "   email@example.com");
        SignUpPanel.add(EmailField);

        JTextField CardNmbrField = new JTextField("   Card Number");
        CardNmbrField.setBounds(50, 360, 340, 40);
        CardNmbrField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        CardNmbrField.setForeground(Color.black);
        CardNmbrField.setCaretColor(Color.black);
        CardNmbrField.setBackground(new Color(0xBBBBBB));
        CardNmbrField.setOpaque(true);
        TextfieldBehave(CardNmbrField, "   Card Number");
        SignUpPanel.add(CardNmbrField);

        JTextField CCVNmbrField = new JTextField("   CCV Number");
        CCVNmbrField.setBounds(50, 440, 340, 40);
        CCVNmbrField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        CCVNmbrField.setForeground(Color.black);
        CCVNmbrField.setCaretColor(Color.black);
        CCVNmbrField.setBackground(new Color(0xBBBBBB));
        CCVNmbrField.setOpaque(true);
        
        TextfieldBehave(CCVNmbrField, "   CCV Number");
        SignUpPanel.add(CCVNmbrField);

        JButton ConfirmSignUpButton = new JButton("sign up");
        ConfirmSignUpButton.setBounds(250, 520, 180, 50);
        ConfirmSignUpButton.setForeground(Color.white);
        ConfirmSignUpButton.setFocusPainted(false);
        ConfirmSignUpButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ConfirmSignUpButton.setBackground(Color.red);
        ConfirmSignUpButton.setUI(new RoundButtonUI(new Color(0x000000)));

        ConfirmSignUpButton.addActionListener(e -> {
            HandleSignIn(UserNameField, PasswordsField, 
            EmailField, CardNmbrField, CCVNmbrField, CCVNmbrField);
        });

        SignUpPanel.add(ConfirmSignUpButton);

        JButton ReturnBtn = new JButton("return");
        ReturnBtn.setBounds(20, 520, 180, 50);
        ReturnBtn.setForeground(Color.white);
        ReturnBtn.setFocusPainted(false);
        ReturnBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ReturnBtn.setBackground(Color.red);
        ReturnBtn.setUI(new RoundButtonUI(new Color(0x000000)));
        ReturnBtn.addActionListener(e -> {
            MainCardLayout.show(MainPanel, "Welcome");
        });
        SignUpPanel.add(ReturnBtn);

        SignInElements.add(SignUpPanel);

        return SignInElements;
    }

    public JPanel CreateLogInElementsPanel() {
        JPanel LogInElements = new JPanel();
        LogInElements.setBounds(0, 0, 1200, 750);
        LogInElements.setLayout(null);
        LogInElements.setOpaque(false);

        JLabel LogoName = new JLabel("POOMovie");
        LogoName.setBounds(70, 24, 192, 52);
        LogoName.setFont(new Font("Inter", Font.BOLD, 35));
        LogoName.setForeground(Color.red);
        LogInElements.add(LogoName);

        ImageIcon iconMovie = new ImageIcon("Poo2-TRY-/Rayan/bookingTICKET/img/movie.png");
        JLabel imageLabel = new JLabel(iconMovie);
        imageLabel.setBounds(10, 20, 64, 64);
        LogInElements.add(imageLabel);

        TransparentPanel RectangleLogin = new TransparentPanel(0.5f);
        RectangleLogin.setBounds(400, 100, 440, 620);
        RectangleLogin.setLayout(null);
        RectangleLogin.setBackground(Color.black);
        LogInElements.add(RectangleLogin);

        JLabel LogInLabel = new JLabel("Log In");
        LogInLabel.setBounds(50, 40, 200, 50);
        LogInLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        LogInLabel.setForeground(Color.white);
        RectangleLogin.add(LogInLabel);

        JTextField EmailField = new JTextField("   Email or username or phone number");
        EmailField.setBounds(50, 120, 340, 40);
        EmailField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        EmailField.setForeground(Color.black);
        EmailField.setCaretColor(Color.black);
        // EmailField.setBackground(new Color(80, 77, 74, 230));
        EmailField.setBackground(new Color(0xBBBBBB));
        EmailField.setOpaque(true);

        TextfieldBehave(EmailField, "   Email or username or phone number");

        RectangleLogin.add(EmailField);

        JPasswordField PasswordField = new JPasswordField(" Password");
        PasswordField.setBounds(50, 200, 340, 40);
        PasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        PasswordField.setForeground(Color.black);
        PasswordField.setCaretColor(Color.black);
        // PasswordField.setBackground(new Color(80, 77, 74, 230));
        PasswordField.setBackground(new Color(0xBBBBBB));
        PasswordField.setOpaque(true);
        TextfieldBehave(PasswordField, " Password");
        RectangleLogin.add(PasswordField);


        JButton LogInButton = new JButton("log in");
        LogInButton.setBounds(50, 270, 340, 40);
        LogInButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        LogInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LogInButton.setBackground(Color.red);
        LogInButton.setForeground(Color.white);
        LogInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        LogInButton.setBorder(BorderFactory.createEmptyBorder());
        LogInButton.setUI(new RoundButtonUI(new Color(0x000000)));

        LogInButton.addActionListener(e -> {
            HandleLogIn(EmailField.getText(), PasswordField.getText());
        });


        RectangleLogin.add(LogInButton);

        JLabel Or = new JLabel("OR");
        Or.setBounds(106 + 100, 310, 30, 40); // :- )
        Or.setFont(new Font("Segoe UI", Font.BOLD, 20));
        Or.setForeground(Color.white);
        RectangleLogin.add(Or);

        JButton btnforgetpassword = new JButton("Forgot password ?");
        btnforgetpassword.setBounds(25, 400, 200, 30);
        btnforgetpassword.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnforgetpassword.setForeground(Color.white);
        btnforgetpassword.setContentAreaFilled(false);
        btnforgetpassword.setBorderPainted(false);
        btnforgetpassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnforgetpassword.addActionListener(e -> {
            MainCardLayout.show(MainPanel, "Forgot Password");
        });
        RectangleLogin.add(btnforgetpassword);

        JLabel SignupLabel = new JLabel("New to POOMovie? ");
        SignupLabel.setBounds(40, 450, 300, 50);
        SignupLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        SignupLabel.setForeground(Color.white);
        RectangleLogin.add(SignupLabel);

        JButton SignupButton = new JButton("Sign Up Now...");
        SignupButton.setBounds(140, 461, 200, 30);
        SignupButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
        SignupButton.setForeground(Color.white);
        SignupButton.setContentAreaFilled(false);
        SignupButton.setBorderPainted(false);
        SignupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SignupButton.addActionListener(e -> {
            MainCardLayout.show(MainPanel, "SignIn");
        });

        RectangleLogin.add(SignupButton);

        JButton returnbtnWelcomePanel = new JButton("return");
        returnbtnWelcomePanel.setBounds(50, 350, 340, 40);
        returnbtnWelcomePanel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        returnbtnWelcomePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        returnbtnWelcomePanel.setBackground(new java.awt.Color(0x777777));
        returnbtnWelcomePanel.setForeground(Color.white);
        returnbtnWelcomePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        returnbtnWelcomePanel.setBorder(BorderFactory.createEmptyBorder());
        returnbtnWelcomePanel.setUI(new RoundButtonUI(new Color(0x000000)));
        returnbtnWelcomePanel.addActionListener(e -> {
            MainCardLayout.show(MainPanel, "Welcome");
        });
        RectangleLogin.add(returnbtnWelcomePanel);

        return LogInElements;
    }

    public JPanel CreateForgotPasswordPanel() {
        JPanel ForgotPasswordElements = new JPanel();
        ForgotPasswordElements.setLayout(null);
        ForgotPasswordElements.setBounds(0, 0, 1200, 750);
        ForgotPasswordElements.setOpaque(false);

        // Logo
        JLabel LogoName = new JLabel("POOMovie");
        LogoName.setBounds(70, 24, 192, 52);
        LogoName.setFont(new Font("Inter", Font.BOLD, 35));
        LogoName.setForeground(Color.red);

        ForgotPasswordElements.add(LogoName);

        ImageIcon iconMovie = new ImageIcon("Poo2-TRY-/Rayan/bookingTICKET/img/movie.png");
        JLabel imageLabel = new JLabel(iconMovie);
        imageLabel.setBounds(10, 20, 64, 64);

        ForgotPasswordElements.add(imageLabel);

        // Center Rectangle Panel
        JPanel rectangleForgot = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2d.setColor(new Color(0, 0, 0));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        rectangleForgot.setBounds(400, 100, 440, 620);
        rectangleForgot.setLayout(null);
        rectangleForgot.setOpaque(false);
        ForgotPasswordElements.add(rectangleForgot);

        // Title
        JLabel titleLabel = new JLabel("Password Recovery");
        titleLabel.setBounds(50, 40, 300, 50);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        titleLabel.setForeground(Color.white);
        rectangleForgot.add(titleLabel);

        // Username field
        JTextField usernameField = new JTextField("Enter your username");
        usernameField.setBounds(50, 120, 340, 40);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        usernameField.setForeground(Color.black);
        usernameField.setBackground(new Color(0xBBBBBB));
        usernameField.setCaretColor(Color.white);
        usernameField.setOpaque(true);
        usernameField.setBorder(null);
        TextfieldBehave(usernameField, "Enter your username");
        rectangleForgot.add(usernameField);

        // Security Question
        JLabel questionLabel = new JLabel("Security Question: What is 15 + 7 ?");
        questionLabel.setBounds(50, 190, 340, 30);
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        questionLabel.setForeground(Color.white);
        rectangleForgot.add(questionLabel);

        // Answer field
        JTextField answerField = new JTextField("Enter your answer");
        answerField.setBounds(50, 230, 340, 40);
        answerField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        answerField.setForeground(Color.black);
        answerField.setBackground(new Color(0xBBBBBB));
        answerField.setCaretColor(Color.white);
        answerField.setOpaque(true);
        answerField.setBorder(null);
        TextfieldBehave(answerField, "Enter your answer");
        rectangleForgot.add(answerField);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(50, 300, 340, 40);
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        submitButton.setBackground(Color.red);
        submitButton.setForeground(Color.white);
        submitButton.setFocusPainted(false);
        submitButton.setUI(new RoundButtonUI(new Color(0x000000)));
        submitButton.addActionListener(e -> {
            if (answerField.getText().equals("22")) {
                // Show password in a dialog
                JOptionPane.showMessageDialog(ForgotPasswordElements,
                        "Your password is: YourStoredPassword",
                        "Password Recovery",
                        JOptionPane.INFORMATION_MESSAGE);

                MainCardLayout.show(MainPanel, "LogIn");
            } else {
                JOptionPane.showMessageDialog(ForgotPasswordElements,
                        "Incorrect answer. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        rectangleForgot.add(submitButton);

        // Return Button
        JButton returnButton = new JButton("Return to log in");
        returnButton.setBounds(50, 360, 340, 40);
        returnButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        returnButton.setBackground(Color.gray);
        returnButton.setForeground(Color.white);
        returnButton.setFocusPainted(false);
        returnButton.setUI(new RoundButtonUI(new Color(0x000000)));
        returnButton.addActionListener(e -> MainCardLayout.show(MainPanel, "LogIn"));
        rectangleForgot.add(returnButton);

        return ForgotPasswordElements;
    }

    public JPanel CreateClientInterface() {

        CardLayout layout = new CardLayout();

        JPanel HomePanel = new JPanel(layout);
        HomePanel.setLayout(null);
        HomePanel.setBounds(0, 0, 1200, 750);
        HomePanel.setOpaque(true);
        HomePanel.setBackground(new Color(0x121213));

        //Left panel ----------------------------------------------------------------
        RoundedPanel LeftPanel = new RoundedPanel(35); 
        LeftPanel.setBounds(30,30, 150, 650);
        LeftPanel.setBackground(new Color(0x212121));
        LeftPanel.setLayout(null);

        HomePanel.add(LeftPanel);

        JButton HomeButton = new JButton();
        HomeButton.setBounds(20, 100, 30, 30);

        ImageIcon originalIcon6 = new ImageIcon(
                "Poo2-TRY-\\Fahd\\bookingTICKET\\Images\\home (1).png");
        Image originalImage6 = originalIcon6.getImage();
        Image scaledImage6 = originalImage6.getScaledInstance(20, 20,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon6 = new ImageIcon(scaledImage6);

        HomeButton.setIcon(scaledIcon6);
        HomeButton.setBackground(new Color(0xCBCBCB));
        HomeButton.setForeground(new Color(0x191D22));
        HomeButton.setFont(new Font("Inter", Font.BOLD, 15));
        HomeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        HomeButton.setUI(new RoundButtonUI(new Color(0x000000)));

        TransparentPanel HoverHome = new TransparentPanel(0.5f);
        HoverHome.setBounds(0, 105, 7, 20);
        HoverHome.setVisible(false);
        HoverHome.setBackground(new Color(0x878787));

        LeftPanel.add(HoverHome);

        JButton HomeButtontext = new JButton("Home");
        HomeButtontext.setBounds(40, 100, 100, 30);
        HomeButtontext.setContentAreaFilled(false);
        HomeButtontext.setBorderPainted(false);
        HomeButtontext.setForeground(new Color(0xFFFFFF));
        HomeButtontext.setFont(new Font("Inter", Font.BOLD, 15));
        HomeButtontext.setCursor(new Cursor(Cursor.HAND_CURSOR));

        HomeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HomeButtontext.setForeground(new Color(0x878787));
                HomeButton.setBackground(new Color(0x878787));
                HoverHome.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                HomeButtontext.setForeground(new Color(0xFFFFFF));
                HomeButton.setBackground(new Color(0xCBCBCB));
                HoverHome.setVisible(false);
            }
        });

        LeftPanel.add(HomeButton);

        HomeButtontext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HomeButtontext.setForeground(new Color(0x878787));
                HomeButton.setBackground(new Color(0x878787));
                HoverHome.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                HomeButtontext.setForeground(new Color(0xFFFFFF));
                HomeButton.setBackground(new Color(0xCBCBCB));
                HoverHome.setVisible(false);
            }
        });

        LeftPanel.add(HomeButtontext);
        


        JButton AllButton = new JButton();
        AllButton.setBounds(20, 150, 30, 30);

        ImageIcon originalIcon = new ImageIcon(
                "Poo2-TRY-\\Fahd\\bookingTICKET\\Images\\all.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(20, 20,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        AllButton.setIcon(scaledIcon);
        AllButton.setBackground(new Color(0xCBCBCB));
        AllButton.setForeground(new Color(0x191D22));
        AllButton.setFont(new Font("Inter", Font.BOLD, 15));
        AllButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AllButton.setUI(new RoundButtonUI(new Color(0x000000)));
        AllButton.setContentAreaFilled(false); // Make the button background transparent
        AllButton.setBorderPainted(false); // Remove the button border
        AllButton.setFocusPainted(false); // Remove the focus border

        TransparentPanel HoverAll = new TransparentPanel(0.5f);
        HoverAll.setBounds(0, 155, 7, 20);
        HoverAll.setVisible(false);
        HoverAll.setBackground(new Color(0x878787));

        LeftPanel.add(HoverAll);

        JButton AllMoviestext = new JButton("All movies");
        AllMoviestext.setBounds(40, 150, 125, 30);
        AllMoviestext.setContentAreaFilled(false);
        AllMoviestext.setBorderPainted(false);
        AllMoviestext.setForeground(new Color(0xFFFFFF));
        AllMoviestext.setFont(new Font("Inter", Font.BOLD, 15));
        AllMoviestext.setCursor(new Cursor(Cursor.HAND_CURSOR));

        
        AllButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AllMoviestext.setForeground(new Color(0x878787));
                AllButton.setBackground(new Color(0x878787));
                HoverAll.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                AllMoviestext.setForeground(new Color(0xFFFFFF));
                AllButton.setBackground(new Color(0xCBCBCB));
                HoverAll.setVisible(false);
            }
        });

        LeftPanel.add(AllButton);

        AllMoviestext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AllMoviestext.setForeground(new Color(0x878787));
                AllButton.setBackground(new Color(0x878787));
                HoverAll.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                AllMoviestext.setForeground(new Color(0xFFFFFF));
                AllButton.setBackground(new Color(0xCBCBCB));
                HoverAll.setVisible(false);
            }
        });

        LeftPanel.add(AllMoviestext);



        JButton FavoriesButton = new JButton();
        FavoriesButton.setBounds(20, 280, 30, 30);

        ImageIcon originalIcon2 = new ImageIcon(
                "Poo2-TRY-\\Fahd\\bookingTICKET\\Images\\ticket.png");
        Image originalImage2 = originalIcon2.getImage();
        Image scaledImage2 = originalImage2.getScaledInstance(20, 20,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);

        FavoriesButton.setIcon(scaledIcon2);
        FavoriesButton.setBackground(new Color(0xCBCBCB));
        FavoriesButton.setForeground(new Color(0x191D22));
        FavoriesButton.setFont(new Font("Inter", Font.BOLD, 15));
        FavoriesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FavoriesButton.setUI(new RoundButtonUI(new Color(0x000000)));

        TransparentPanel HoverTicket = new TransparentPanel(0.5f);
        HoverTicket.setBounds(0, 285, 7, 20);
        HoverTicket.setVisible(false);
        HoverTicket.setBackground(new Color(0x878787));

        LeftPanel.add(HoverTicket);

        JButton FavoriesText = new JButton("favories");
        FavoriesText.setBounds(40, 280, 125, 30);
        FavoriesText.setContentAreaFilled(false);
        FavoriesText.setBorderPainted(false);
        FavoriesText.setForeground(new Color(0xFFFFFF));
        FavoriesText.setFont(new Font("Inter", Font.BOLD, 15));
        FavoriesText.setCursor(new Cursor(Cursor.HAND_CURSOR));

        FavoriesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                FavoriesButton.setBackground(new Color(0x878787));
                FavoriesText.setForeground(new Color(0x878787));
                HoverTicket.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                FavoriesButton.setBackground(new Color(0xCBCBCB));
                FavoriesText.setForeground(new Color(0xCBCBCB));
                HoverTicket.setVisible(false);
            }
        });

        LeftPanel.add(FavoriesButton);

        FavoriesText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                FavoriesButton.setBackground(new Color(0x878787));
                FavoriesText.setForeground(new Color(0x878787));
                HoverTicket.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                FavoriesButton.setBackground(new Color(0xCBCBCB));
                FavoriesText.setForeground(new Color(0xCBCBCB));
                HoverTicket.setVisible(false);
            }
        });

        LeftPanel.add(FavoriesText);



        JButton BalanceButton = new JButton();
        BalanceButton.setBounds(20, 330, 30, 30);

        ImageIcon originalIcon3 = new ImageIcon(
            "Poo2-TRY-\\Fahd\\bookingTICKET\\Images\\dollar.png");
        Image originalImage3 = originalIcon3.getImage();
        Image scaledImage3 = originalImage3.getScaledInstance(20, 20,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);

        BalanceButton.setIcon(scaledIcon3);
        BalanceButton.setBackground(new Color(0xAD3000));
        BalanceButton.setForeground(new Color(0x191D22));
        BalanceButton.setFont(new Font("Inter", Font.BOLD, 14));
        BalanceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BalanceButton.setUI(new RoundButtonUI(new Color(0x000000)));

        TransparentPanel HoverBalance = new TransparentPanel(0.5f);
        HoverBalance.setBounds(0, 335, 7, 20);
        HoverBalance.setVisible(false);
        HoverBalance.setBackground(new Color(0xFF0000));

        LeftPanel.add(HoverBalance);

        JButton BalanceText = new JButton("my balance");
        BalanceText.setBounds(40, 330, 125, 30);
        BalanceText.setContentAreaFilled(false);
        BalanceText.setBorderPainted(false);
        BalanceText.setForeground(new Color(0xAD3000));
        BalanceText.setFont(new Font("Inter", Font.BOLD, 15));
        BalanceText.setCursor(new Cursor(Cursor.HAND_CURSOR));

        BalanceButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BalanceButton.setBackground(new Color(0x550000));
                BalanceText.setForeground(new Color(0x550000));
                HoverBalance.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                BalanceButton.setBackground(new Color(0xAD3000));
                BalanceText.setForeground(new Color(0xAD3000));
                HoverBalance.setVisible(false);
            }
        });

        LeftPanel.add(BalanceButton);

        BalanceText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BalanceButton.setBackground(new Color(0x550000));
                BalanceText.setForeground(new Color(0x550000));
                HoverBalance.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                BalanceButton.setBackground(new Color(0xAD3000));
                BalanceText.setForeground(new Color(0xAD3000));
                HoverBalance.setVisible(false);
            }
        });

        LeftPanel.add(BalanceText);



        JButton FilterButton = new JButton();
        FilterButton.setBounds(20, 430, 30, 30);

        ImageIcon originalIcon4 = new ImageIcon(
            "Poo2-TRY-\\Fahd\\bookingTICKET\\Images\\filter.png");
        Image originalImage4 = originalIcon4.getImage();
        Image scaledImage4 = originalImage4.getScaledInstance(20, 20,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4 = new ImageIcon(scaledImage4);

        FilterButton.setIcon(scaledIcon4);
        FilterButton.setBackground(new Color(0xA96E00));
        FilterButton.setForeground(new Color(0x191D22));
        FilterButton.setFont(new Font("Inter", Font.BOLD, 15));
        FilterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FilterButton.setUI(new RoundButtonUI(new Color(0x000000)));
        FilterButton.setVisible(false);

        LeftPanel.add(FilterButton);

        TransparentPanel HoverFilter = new TransparentPanel(0.5f);
        HoverFilter.setBounds(0, 435, 7, 20);
        HoverFilter.setVisible(false);
        HoverFilter.setBackground(new Color(0xFFFF00));

        LeftPanel.add(HoverFilter);

        JButton FilterText = new JButton("filter");
        FilterText.setBounds(40, 430, 125, 30);
        FilterText.setContentAreaFilled(false);
        FilterText.setBorderPainted(false);
        FilterText.setForeground(new Color(0xA96E00));
        FilterText.setFont(new Font("Inter", Font.BOLD, 15));
        FilterText.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FilterText.setVisible(false);

        FilterText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                FilterButton.setBackground(new Color(0x652B00));
                FilterText.setForeground(new Color(0x652B00));
                HoverFilter.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                FilterButton.setBackground(new Color(0xA96E00));
                FilterText.setForeground(new Color(0xA96E00));
                HoverFilter.setVisible(false);
            }
        });


        FilterButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                FilterButton.setBackground(new Color(0x652B00));
                FilterText.setForeground(new Color(0x652B00));
                HoverFilter.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                FilterButton.setBackground(new Color(0xA96E00));
                FilterText.setForeground(new Color(0xA96E00));
                HoverFilter.setVisible(false);
            }
        });

        LeftPanel.add(FilterText);



        JButton ChangeTheme = new JButton();
        ChangeTheme.setBounds(20, 380, 30, 30);
        ChangeTheme.setBackground(new Color(0xA96E00));
        ChangeTheme.setForeground(new Color(0x191D22));
        ChangeTheme.setFont(new Font("Inter", Font.BOLD, 15));
        ChangeTheme.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ChangeTheme.setUI(new RoundButtonUI(new Color(0x000000)));

        TransparentPanel HoverTheme = new TransparentPanel(0.5f);
        HoverTheme.setBounds(0, 385, 7, 20);
        HoverTheme.setVisible(false);
        HoverTheme.setBackground(new Color(0xA96E00));

        LeftPanel.add(HoverTheme);

        JButton ChangeThemeText = new JButton("?");
        ChangeThemeText.setBounds(40, 380, 125, 30);
        ChangeThemeText.setContentAreaFilled(false);
        ChangeThemeText.setBorderPainted(false);
        ChangeThemeText.setForeground(new Color(0xA96E00));
        ChangeThemeText.setFont(new Font("Inter", Font.BOLD, 15));
        ChangeThemeText.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ChangeTheme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ChangeTheme.setBackground(new Color(0x652B00));
                ChangeThemeText.setForeground(new Color(0x652B00));
                HoverTheme.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                ChangeTheme.setBackground(new Color(0xA96E00));
                ChangeThemeText.setForeground(new Color(0xA96E00));
                HoverTheme.setVisible(false);
            }
        });

        LeftPanel.add(ChangeTheme);

        ChangeThemeText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ChangeTheme.setBackground(new Color(0x652B00));
                ChangeThemeText.setForeground(new Color(0x652B00));
                HoverTheme.setVisible(true);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                ChangeTheme.setBackground(new Color(0xA96E00));
                ChangeThemeText.setForeground(new Color(0xA96E00));
                HoverTheme.setVisible(false);
            }
        });

        LeftPanel.add(ChangeThemeText);

        JPanel StraightLine = new JPanel();
        StraightLine.setBounds(220, 80, 930, 5);
        StraightLine.setBackground(new Color(0x313131));

        HomePanel.add(StraightLine);

        RoundedPanel SearchBar = new RoundedPanel(35);
        SearchBar.setBounds(250, 30, 800, 30);
        SearchBar.setBackground(new Color(0x212121));
        SearchBar.setLayout(null);

        HomePanel.add(SearchBar);

        JTextField SearchField = new JTextField();
        SearchField.setBounds(10, 2, 800, 25);
        SearchField.setFont(new Font("Inter", Font.BOLD, 15));
        SearchField.setForeground(Color.white);
        SearchField.setCaretColor(Color.white);
        SearchField.setOpaque(false);
        SearchField.setBorder(null);

        TextfieldBehave(SearchField, "Search for a movie");

        SearchBar.add(SearchField);

        JButton Filter = new JButton();
        Filter.setBounds(200, 32, 30, 30);
        Filter.setFocusPainted(false);
        Filter.setBorder(null);
        Filter.setBackground(new Color(0x212121));
        Filter.setUI(new RoundButtonUI(Color.black));
        Filter.addActionListener(e -> {
            // Open movie filter dialog
        });
        HomePanel.add(Filter);

        JButton History = new JButton();
        History.setBounds(1070, 29, 30, 30);
        History.setFocusPainted(false);
        History.setBorder(null);
        History.setBackground(new Color(0x212121));
        History.setUI(new RoundButtonUI(Color.black));
        History.addActionListener(e -> {
            // Open movie History dialog
        });
        HomePanel.add(History);

        JButton Account = new JButton();
        Account.setBounds(1110, 29, 50, 30);
        Account.setFocusPainted(false);
        Account.setBorder(null);
        Account.setBackground(new Color(0x515151));
        Account.setUI(new RoundButtonUI(Color.black));
        Account.addActionListener(e -> {
            // Open movie Account dialog
            MainCardLayout.show(MainPanel, "Account");
        });
        HomePanel.add(Account);

        //content panel---------------------------------------------------------------
        JPanel ContentPanel = CreateContentPanel();

        JScrollPane scrollPane = new JScrollPane(ContentPanel);
        scrollPane.setBounds(227, 85, 919, 750);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);

        ContentPanel.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();// had scroll par rapport l y
            int notches = e.getWheelRotation();
            int currentValue = verticalScrollBar.getValue();
            int scrollAmount = 30; // Adjust scroll speed
            verticalScrollBar.setValue(currentValue + (notches * scrollAmount));
        });

        HomePanel.add(scrollPane, "home");
        scrollPane.setVisible(true);

        //Movie panel---------------------------------------------------------------
        JPanel MoviePanel = CreateMoviePanel();

        JScrollPane scrollPane2 = new JScrollPane(MoviePanel);
        scrollPane2.setBounds(179, 85, 1009, 750);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane2.setOpaque(false);
        scrollPane2.setBorder(null);

        MoviePanel.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPane2.getVerticalScrollBar();// had scroll par rapport l y
            int notches = e.getWheelRotation();
            int currentValue = verticalScrollBar.getValue();
            int scrollAmount = 30; // Adjust scroll speed
            verticalScrollBar.setValue(currentValue + (notches * scrollAmount));
        });

        HomePanel.add(scrollPane2, "movies");
        scrollPane2.setVisible(false);

        //balance Panel ----------------------------------------------------------------
        


        //Ticket / history panel ----------------------------------------------------------------


        HomePanel.setComponentZOrder(scrollPane, 1);
        HomePanel.setComponentZOrder(scrollPane2, 1);
        HomePanel.setComponentZOrder(LeftPanel, 0);
        HomePanel.setComponentZOrder(StraightLine, 0);
        HomePanel.setComponentZOrder(SearchBar, 0);
        HomePanel.setComponentZOrder(Filter, 0);
        HomePanel.setComponentZOrder(History, 0);
        HomePanel.setComponentZOrder(Account, 0);

        

        return HomePanel;
    }

    public JPanel CreateContentPanel() {
        JPanel ContentPanel = new JPanel();
        ContentPanel.setLayout(null);
        ContentPanel.setOpaque(true);
        ContentPanel.setBackground(new Color(0x121213));
        ContentPanel.setPreferredSize(new Dimension(1009, 2750));

        // Most popular movie
        RoundedPanel MostPopularPanel = new RoundedPanel(35);
        MostPopularPanel.setBounds(0, 20, 940, 400);
        MostPopularPanel.setBackground(new Color(0x212121));
        MostPopularPanel.setLayout(null);

        ImageIcon imageIcon = resizedIcon("Poo2-TRY-\\Fahd\\bookingTICKET\\Images\\spiderman_no_way_home.png", 940, 400);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, 940, 400);

        MostPopularPanel.add(imageLabel);

        TransparentPanel backgroundPanel = new TransparentPanel(0.25f);
        backgroundPanel.setBounds(0, 0, 940, 400);
        backgroundPanel.setBackground(new Color(0x000000));
        backgroundPanel.setLayout(null);

        MostPopularPanel.add(backgroundPanel);

        JLabel MostPopularLabel = new JLabel("Most Popular Movie");
        MostPopularLabel.setBounds(25, 20, 200, 30);
        MostPopularLabel.setFont(new Font("Inter", Font.BOLD, 20));
        MostPopularLabel.setForeground(Color.white);

        MostPopularPanel.add(MostPopularLabel);

        JButton BookNow = new JButton("Book Now");
        BookNow.setBounds(25, 350, 125, 30);
        BookNow.setFont(new Font("Inter", Font.BOLD, 12));
        BookNow.setForeground(Color.black);
        BookNow.setBackground(Color.white);
        BookNow.setFocusPainted(false);
        BookNow.setUI(new RoundButtonUI(Color.black));
        BookNow.addActionListener(e -> {
            // Open Booking dialog
        });

        MostPopularPanel.add(BookNow);

        JButton MoreInfo = new JButton("More Info :");
        MoreInfo.setBounds(175, 350, 125, 30);
        MoreInfo.setFont(new Font("Inter", Font.BOLD, 15));
        MoreInfo.setForeground(Color.white);
        MoreInfo.setBackground(Color.black);
        MoreInfo.setFocusPainted(false);
        MoreInfo.setUI(new RoundButtonUI(Color.black));
        MoreInfo.addActionListener(e -> {
            // Open Booking dialog
        });

        MostPopularPanel.add(MoreInfo);

        MostPopularPanel.setComponentZOrder(imageLabel, 2);
        MostPopularPanel.setComponentZOrder(backgroundPanel, 1);
        MostPopularPanel.setComponentZOrder(MostPopularLabel, 0);
        MostPopularPanel.setComponentZOrder(BookNow, 0);
        MostPopularPanel.setComponentZOrder(MoreInfo, 0);


        ContentPanel.add(MostPopularPanel);

        // other popular movies
        JLabel OtherPopularLabel = new JLabel("Other Popular Movies");
        OtherPopularLabel.setBounds(0, 450, 600, 30);
        OtherPopularLabel.setFont(new Font("Inter", Font.BOLD, 17));
        OtherPopularLabel.setForeground(Color.white);

        ContentPanel.add(OtherPopularLabel);

        JPanel OtherPopularPanel = new JPanel();
        OtherPopularPanel.setBounds(0, 500, 920, 300);
        OtherPopularPanel.setBackground(new Color(0x121213));
        OtherPopularPanel.setLayout(new GridLayout(1, 3, 100, 20));
        OtherPopularPanel.setBorder(null);
        OtherPopularPanel.setOpaque(false);


        for (int i = 0; i < 3; i++) {
            RoundedPanel moviePanel = new RoundedPanel(15);
            moviePanel.setBounds(0, 0, 280, 360);
            moviePanel.setLayout(null);
            moviePanel.setBackground(new Color(0x212121));

            // Hayla hadi :-)
            moviePanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    moviePanel.setBorder(BorderFactory.createLineBorder(new Color(0xFF6700), 3));// ta3 ki t intiracti m3a l panel
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    moviePanel.setBorder(null);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {

                    JOptionPane.showMessageDialog(null, "Opening film details...");
                }
            });

            OtherPopularPanel.add(moviePanel);
        }

        ContentPanel.add(OtherPopularPanel);

        // best rated movie
        RoundedPanel BestRatedPanel = new RoundedPanel(35);
        BestRatedPanel.setBounds(0, 850, 940, 400);
        BestRatedPanel.setBackground(new Color(0x212121));
        BestRatedPanel.setLayout(null);

        ImageIcon imageIcon2 = resizedIcon("Poo2-TRY-\\Fahd\\bookingTICKET\\Images\\Invincible-Title-Card.jpg", 1000, 600);
        JLabel imageLabel2 = new JLabel(imageIcon2);
        imageLabel2.setBounds(0, 0, 940, 400);

        BestRatedPanel.add(imageLabel2);

        TransparentPanel backgroundPanel2 = new TransparentPanel(0.25f);
        backgroundPanel2.setBounds(0, 0, 940, 400);
        backgroundPanel2.setBackground(new Color(0x000000));
        backgroundPanel2.setLayout(null);

        BestRatedPanel.add(backgroundPanel2);

        JLabel BestRatedLabel = new JLabel("Best rated Movie");
        BestRatedLabel.setBounds(25, 20, 200, 30);
        BestRatedLabel.setFont(new Font("Inter", Font.BOLD, 20));
        BestRatedLabel.setForeground(Color.white);

        BestRatedPanel.add(BestRatedLabel);

        JButton BookNow2 = new JButton("Book Now");
        BookNow2.setBounds(25, 350, 125, 30);
        BookNow2.setFont(new Font("Inter", Font.BOLD, 12));
        BookNow2.setForeground(Color.black);
        BookNow2.setBackground(Color.white);
        BookNow2.setFocusPainted(false);
        BookNow2.setUI(new RoundButtonUI(Color.black));
        BookNow2.addActionListener(e -> {
            // Open Booking dialog
        });

        BestRatedPanel.add(BookNow2);

        JButton MoreInfo2 = new JButton("More Info :");
        MoreInfo2.setBounds(175, 350, 125, 30);
        MoreInfo2.setFont(new Font("Inter", Font.BOLD, 15));
        MoreInfo2.setForeground(Color.white);
        MoreInfo2.setBackground(Color.black);
        MoreInfo2.setFocusPainted(false);
        MoreInfo2.setUI(new RoundButtonUI(Color.black));
        MoreInfo2.addActionListener(e -> {
            // Open Booking dialog
        });

        BestRatedPanel.add(MoreInfo2);

        BestRatedPanel.setComponentZOrder(imageLabel2, 2);
        BestRatedPanel.setComponentZOrder(backgroundPanel2, 1);
        BestRatedPanel.setComponentZOrder(BestRatedLabel, 0);
        BestRatedPanel.setComponentZOrder(BookNow2, 0);
        BestRatedPanel.setComponentZOrder(MoreInfo2, 0);


        ContentPanel.add(BestRatedPanel);

        // movies you may like
        JLabel YouMayLikeLabel = new JLabel("Movies You May Like");
        YouMayLikeLabel.setBounds(0, 1300, 600, 30);
        YouMayLikeLabel.setFont(new Font("Inter", Font.BOLD, 17));
        YouMayLikeLabel.setForeground(Color.white);

        ContentPanel.add(YouMayLikeLabel);

        JPanel YouMayLikePanel = new JPanel();
        YouMayLikePanel.setBounds(0, 1350, 920, 600);
        YouMayLikePanel.setBackground(new Color(0x121213));
        YouMayLikePanel.setLayout(new GridLayout(0, 4, 25, 25));
        YouMayLikePanel.setBorder(null);
        YouMayLikePanel.setOpaque(false);

        for (int i = 0; i < 8; i++) {
            RoundedPanel moviePanel = new RoundedPanel(15);
            moviePanel.setBounds(0, 0, 200, 300);
            moviePanel.setLayout(null);
            moviePanel.setBackground(new Color(0x212121));

            // Hayla hadi :-)
            moviePanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    moviePanel.setBorder(BorderFactory.createLineBorder(new Color(0xFF6700), 3));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    moviePanel.setBorder(null);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    JOptionPane.showMessageDialog(null, "Opening film details...");
                }
            });

            YouMayLikePanel.add(moviePanel);
        }

        ContentPanel.add(YouMayLikePanel);

        //Promotions
        JLabel PromotionsLabel = new JLabel("Promotions");
        PromotionsLabel.setBounds(0, 2000, 600, 30);
        PromotionsLabel.setFont(new Font("Inter", Font.BOLD, 17));
        PromotionsLabel.setForeground(Color.white);

        ContentPanel.add(PromotionsLabel);

        JPanel PromotionsPanel = new JPanel();
        PromotionsPanel.setBounds(0, 2050, 920, 400);
        PromotionsPanel.setBackground(new Color(0x121213));
        PromotionsPanel.setLayout(new GridLayout(1, 2, 35, 25));
        PromotionsPanel.setBorder(null);
        PromotionsPanel.setOpaque(false);

        for (int i = 0; i < 2; i++) {
            RoundedPanel moviePanel = new RoundedPanel(15);
            moviePanel.setBounds(0, 0, 200, 300);
            moviePanel.setLayout(null);
            moviePanel.setBackground(new Color(0x212121));

            // Hayla hadi :-)
            moviePanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    moviePanel.setBorder(BorderFactory.createLineBorder(new Color(0xFF6700), 3));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    moviePanel.setBorder(null);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    JOptionPane.showMessageDialog(null, "Opening film details...");
                }
            });

            PromotionsPanel.add(moviePanel);
        }

        ContentPanel.add(PromotionsPanel);


        return ContentPanel;
    }

    public JPanel CreateMoviePanel(){
        JPanel MoviePanel = new JPanel();
        MoviePanel.setLayout(null);
        MoviePanel.setOpaque(true);
        MoviePanel.setBackground(new Color(0x121213));
        MoviePanel.setPreferredSize(new Dimension(1009, 2250));

        // All movies
        JLabel AllMoviesLabel = new JLabel("Other Popular Movies");
        AllMoviesLabel.setBounds(25, 20, 600, 30);
        AllMoviesLabel.setFont(new Font("Inter", Font.BOLD, 17));
        AllMoviesLabel.setForeground(Color.white);

        MoviePanel.add(AllMoviesLabel);

        JPanel AllMoviesPanel = new JPanel();
        AllMoviesPanel.setBounds(25, 70, 950, MovieManager.movies.size() * 50 + MovieManager.movies.size() * 25);
        AllMoviesPanel.setBackground(new Color(0x121213));
        AllMoviesPanel.setLayout(new GridLayout(0, 4, 40, 25));

        for (int i = 0; i < MovieManager.movies.size(); i++) {
            RoundedPanel moviePanel = new RoundedPanel(15);
            moviePanel.setBounds(0, 0, 200, 300);
            moviePanel.setLayout(null);
            moviePanel.setBackground(new Color(0x212121));

            // Hayla hadi :-)
            moviePanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    moviePanel.setBorder(BorderFactory.createLineBorder(new Color(0xFF6700), 3));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    moviePanel.setBorder(null);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    JOptionPane.showMessageDialog(null, "Opening film details...");
                }
            });

            AllMoviesPanel.add(moviePanel);
        }

        MoviePanel.add(AllMoviesPanel);
        
        return MoviePanel;
    }



    public JPanel CreateAccountPanel() {
        JPanel AccountPanel = new JPanel();
        AccountPanel.setOpaque(false);
        AccountPanel.setLayout(null);
        AccountPanel.setPreferredSize(new Dimension(1200, 3500));

        JPanel OptionPanel = new JPanel();
        OptionPanel.setBounds(0, 0, 350, 750);
        OptionPanel.setLayout(null);
        OptionPanel.setBackground(new Color(0x0A0D10));

        AccountPanel.add(OptionPanel);

        JLabel AccountTitle = new JLabel("Account");
        AccountTitle.setBounds(50, 100, 250, 50);
        AccountTitle.setFont(new Font("Segoe UI", Font.BOLD, 35));
        AccountTitle.setForeground(Color.white);

        OptionPanel.add(AccountTitle);

        JLabel AccountTitle2 = new JLabel("Management");
        AccountTitle2.setBounds(50, 150, 350, 50);
        AccountTitle2.setFont(new Font("Segoe UI", Font.BOLD, 35));
        AccountTitle2.setForeground(Color.white);

        OptionPanel.add(AccountTitle2);

        JButton IdSettings = new JButton("Account ID");
        IdSettings.setBounds(25, 275, 250, 30);
        IdSettings.setFont(new Font("Segoe UI", Font.BOLD, 17));
        IdSettings.setForeground(new Color(0xCBCBCB));
        IdSettings.setContentAreaFilled(false);
        IdSettings.setBorderPainted(false);
        IdSettings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        IdSettings.addActionListener(e -> {
            
        });
        JButton IdButton = new JButton();
        IdButton.setBounds(50, 280, 25, 25);
        IdButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
        IdButton.setBackground(new Color(0xCBCBCB));
        IdButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        IdButton.setUI(new RoundButtonUI(new Color(0x000000)));

        IdButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                IdSettings.setForeground(new Color(0xD42E00));
                IdButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                IdSettings.setForeground(new Color(0xCBCBCB));
                IdButton.setBackground(new Color(0xCBCBCB));
            }
        });

        IdSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                IdSettings.setForeground(new Color(0xD42E00));
                IdButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                IdSettings.setForeground(new Color(0xCBCBCB));
                IdButton.setBackground(new Color(0xCBCBCB));
            }
        });

        OptionPanel.add(IdSettings);
        OptionPanel.add(IdButton);
        
        JButton PersonalInfo = new JButton("Personnal information");
        PersonalInfo.setBounds(70, 320, 250, 30);
        PersonalInfo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        PersonalInfo.setForeground(new Color(0xCBCBCB));
        PersonalInfo.setContentAreaFilled(false);
        PersonalInfo.setBorderPainted(false);
        PersonalInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        PersonalInfo.addActionListener(e -> {
            
        });
        JButton PersonalButton = new JButton();
        PersonalButton.setBounds(50, 325, 25, 25);
        PersonalButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
        PersonalButton.setBackground(new Color(0xCBCBCB));
        PersonalButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        PersonalButton.setUI(new RoundButtonUI(new Color(0x000000)));

        PersonalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PersonalInfo.setForeground(new Color(0xD42E00));
                PersonalButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                PersonalInfo.setForeground(new Color(0xCBCBCB));
                PersonalButton.setBackground(new Color(0xCBCBCB));
            }
        });

        PersonalInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PersonalInfo.setForeground(new Color(0xD42E00));
                PersonalButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                PersonalInfo.setForeground(new Color(0xCBCBCB));
                PersonalButton.setBackground(new Color(0xCBCBCB));
            }
        });

        OptionPanel.add(PersonalInfo);
        OptionPanel.add(PersonalButton);


        JButton PaymentInfo = new JButton("Payement information");
        PaymentInfo.setBounds(70, 365, 250, 30);
        PaymentInfo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        PaymentInfo.setForeground(new Color(0xCBCBCB));
        PaymentInfo.setContentAreaFilled(false);
        PaymentInfo.setBorderPainted(false);
        PaymentInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        PaymentInfo.addActionListener(e -> {
            
        });
        JButton PayementButton = new JButton();
        PayementButton.setBounds(50, 370, 25, 25);
        PayementButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
        PayementButton.setBackground(new Color(0xCBCBCB));
        PayementButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        PayementButton.setUI(new RoundButtonUI(new Color(0x000000)));

        PayementButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PaymentInfo.setForeground(new Color(0xD42E00));
                PayementButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                PaymentInfo.setForeground(new Color(0xCBCBCB));
                PayementButton.setBackground(new Color(0xCBCBCB));
            }
        });

        PaymentInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PaymentInfo.setForeground(new Color(0xD42E00));
                PayementButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                PaymentInfo.setForeground(new Color(0xCBCBCB));
                PayementButton.setBackground(new Color(0xCBCBCB));
            }
        });

        OptionPanel.add(PaymentInfo);
        OptionPanel.add(PayementButton);


        JButton SignInInfo = new JButton("Account sign in management");
        SignInInfo.setBounds(47, 410, 350, 30);
        SignInInfo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        SignInInfo.setForeground(new Color(0xCBCBCB));
        SignInInfo.setContentAreaFilled(false);
        SignInInfo.setBorderPainted(false);
        SignInInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SignInInfo.addActionListener(e -> {
            
        });
        JButton SignInButton = new JButton();
        SignInButton.setBounds(50, 415, 25, 25);
        SignInButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
        SignInButton.setBackground(new Color(0xCBCBCB));
        SignInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SignInButton.setUI(new RoundButtonUI(new Color(0x000000)));

        SignInButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SignInInfo.setForeground(new Color(0xD42E00));
                SignInButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                SignInInfo.setForeground(new Color(0xCBCBCB));
                SignInButton.setBackground(new Color(0xCBCBCB));
            }
        });

        SignInInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SignInInfo.setForeground(new Color(0xD42E00));
                SignInButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                SignInInfo.setForeground(new Color(0xCBCBCB));
                SignInButton.setBackground(new Color(0xCBCBCB));
            }
        });

        OptionPanel.add(SignInInfo);
        OptionPanel.add(SignInButton);


        JButton Exit = new JButton("Exit");
        Exit.setBounds(-50, 650, 350, 30);
        Exit.setFont(new Font("Segoe UI", Font.BOLD, 17));
        Exit.setForeground(new Color(0xCBCBCB));
        Exit.setContentAreaFilled(false);
        Exit.setBorderPainted(false);
        Exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton ExitButton = new JButton();
        ExitButton.setBounds(50, 655, 25, 25);
        ExitButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
        ExitButton.setBackground(new Color(0xCBCBCB));
        ExitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ExitButton.setUI(new RoundButtonUI(new Color(0x000000)));

        ExitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Exit.setForeground(new Color(0xD42E00));
                ExitButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Exit.setForeground(new Color(0xCBCBCB));
                ExitButton.setBackground(new Color(0xCBCBCB));
            }
        });

        Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Exit.setForeground(new Color(0xD42E00));
                ExitButton.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Exit.setForeground(new Color(0xCBCBCB));
                ExitButton.setBackground(new Color(0xCBCBCB));
            }
        });

        OptionPanel.add(Exit);
        OptionPanel.add(ExitButton);



        //elements panel --------------------------------------------------------
        JPanel ElementsPanel = new JPanel();
        ElementsPanel.setOpaque(false);
        ElementsPanel.setLayout(null);
        ElementsPanel.setPreferredSize(new Dimension(850, 1750));

        JScrollPane scrollPane = new JScrollPane(ElementsPanel);
        scrollPane.setBounds(349, -1, 855, 755);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setBackground(new Color(0x0A0D10));

        ElementsPanel.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();// had scroll par rapport l y
            int notches = e.getWheelRotation();
            int currentValue = verticalScrollBar.getValue();
            int scrollAmount = 30; // Adjust scroll speed
            verticalScrollBar.setValue(currentValue + (notches * scrollAmount));
        });

        AccountPanel.add(scrollPane);


        //id panel-------------------------------------------------------------------
        JPanel IdPanel = new JPanel();
        IdPanel.setBounds(50, 75, 750, 250);
        IdPanel.setBackground(new Color(0x2B2B2B));
        IdPanel.setLayout(null);

        JLabel NameInfo = new JLabel("Your personnal name");
        NameInfo.setBounds(375, 25, 250, 30);
        NameInfo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        NameInfo.setForeground(Color.white);

        IdPanel.add(NameInfo);

        JTextField NameField = new JTextField("  Name");
        NameField.setBounds(375, 65, 250, 30);
        NameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        NameField.setForeground(Color.white);
        NameField.setCaretColor(Color.white);
        // NameField.setBackground(new Color(80, 77, 74, 230));
        NameField.setBackground(new Color(0x151515));
        NameField.setOpaque(true);

        TextfieldBehave(NameField, "  Name");

        IdPanel.add(NameField);

        JButton SaveId = new JButton("Save changes");
        SaveId.setBounds(575, 200, 150, 30);
        SaveId.setFont(new Font("Segoe UI", Font.BOLD, 15));
        SaveId.setBackground(new Color(0xCBCBCB));
        SaveId.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SaveId.setUI(new RoundButtonUI(new Color(0x000000)));

        SaveId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SaveId.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                SaveId.setBackground(new Color(0xCBCBCB));
            }
        });

        IdPanel.add(SaveId);


        JPanel IdHolderPanel = new JPanel();
        IdHolderPanel.setBounds(0, 0, 325, 250);
        IdHolderPanel.setLayout(null);
        IdHolderPanel.setBackground(new Color(0x1C2837));

        JLabel IdText = new JLabel("Account ID");
        IdText.setBounds(50, 25, 200, 30);
        IdText.setForeground(Color.white);
        IdText.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel IdTextDescription = new JLabel("This Id information are used to log in ,but ");
        IdTextDescription.setBounds(50, 75, 250, 30);
        IdTextDescription.setForeground(Color.white);
        IdTextDescription.setFont(new Font("Segoe UI", Font.BOLD, 10));

        JLabel IdTextDescription2 = new JLabel("also to allow a better service provider");
        IdTextDescription2.setBounds(50, 95, 250, 30);
        IdTextDescription2.setForeground(Color.white);
        IdTextDescription2.setFont(new Font("Segoe UI", Font.BOLD, 10));

        IdHolderPanel.add(IdTextDescription2);
        IdHolderPanel.add(IdTextDescription);
        IdHolderPanel.add(IdText);


        IdPanel.add(IdHolderPanel);

        ElementsPanel.add(IdPanel);


        //personnal panel ----------------------------------------------------------------
        JPanel PersoPanel = new JPanel();
        PersoPanel.setBounds(50, 365, 750, 500);
        PersoPanel.setBackground(new Color(0x2B2B2B));
        PersoPanel.setLayout(null);

        JLabel PersonnalInfo = new JLabel("Personnal informations ");
        PersonnalInfo.setBounds(375, 25, 250, 30);
        PersonnalInfo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        PersonnalInfo.setForeground(Color.white);

        PersoPanel.add(PersonnalInfo);

        JTextField FirstNameField = new JTextField("  First name");
        FirstNameField.setBounds(375, 65, 250, 30);
        FirstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        FirstNameField.setForeground(Color.white);
        FirstNameField.setCaretColor(Color.white);
        // FirstNameField.setBackground(new Color(80, 77, 74, 230));
        FirstNameField.setBackground(new Color(0x151515));
        FirstNameField.setOpaque(true);

        TextfieldBehave(FirstNameField, "  First name");

        PersoPanel.add(FirstNameField);

        JTextField LastNameField = new JTextField("  Last name");
        LastNameField.setBounds(375, 105, 250, 30);
        LastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        LastNameField.setForeground(Color.white);
        LastNameField.setCaretColor(Color.white);
        // LastNameField.setBackground(new Color(80, 77, 74, 230));
        LastNameField.setBackground(new Color(0x151515));
        LastNameField.setOpaque(true);

        TextfieldBehave(LastNameField, "  Last name");

        PersoPanel.add(LastNameField);

        JTextField EmailField = new JTextField("  email@gmail.com");
        EmailField.setBounds(375, 145, 250, 30);
        EmailField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        EmailField.setForeground(Color.white);
        EmailField.setCaretColor(Color.white);
        // EmailField.setBackground(new Color(80, 77, 74, 230));
        EmailField.setBackground(new Color(0x151515));
        EmailField.setOpaque(true);

        TextfieldBehave(EmailField, "  emailemail@gmail.com");

        PersoPanel.add(EmailField);

        JTextField AgeField = new JTextField("  22");
        AgeField.setBounds(375, 185, 250, 30);
        AgeField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        AgeField.setForeground(Color.white);
        AgeField.setCaretColor(Color.white);
        // AgeField.setBackground(new Color(80, 77, 74, 230));
        AgeField.setBackground(new Color(0x151515));
        AgeField.setOpaque(true);

        TextfieldBehave(AgeField, "  22");

        PersoPanel.add(AgeField);

        JTextField PhoneNumberField = new JTextField(" 05XX XX XX XX");
        PhoneNumberField.setBounds(375, 225, 250, 30);
        PhoneNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        PhoneNumberField.setForeground(Color.white);
        PhoneNumberField.setCaretColor(Color.white);
        // PhoneNumberField.setBackground(new Color(80, 77, 74, 230));
        PhoneNumberField.setBackground(new Color(0x151515));
        PhoneNumberField.setOpaque(true);

        TextfieldBehave(PhoneNumberField, " 05XX XX XX XX");

        PersoPanel.add(PhoneNumberField);

        JButton SavePersonnal = new JButton("Save changes");
        SavePersonnal.setBounds(575, 450, 150, 30);
        SavePersonnal.setFont(new Font("Segoe UI", Font.BOLD, 15));
        SavePersonnal.setBackground(new Color(0xCBCBCB));
        SavePersonnal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SavePersonnal.setUI(new RoundButtonUI(new Color(0x000000)));

        SavePersonnal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SavePersonnal.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                SavePersonnal.setBackground(new Color(0xCBCBCB));
            }
        });

        PersoPanel.add(SavePersonnal);


        JPanel PersoHolder = new JPanel();
        PersoHolder.setBounds(0, 0, 325, 500);
        PersoHolder.setLayout(null);
        PersoHolder.setBackground(new Color(0x1C2837));

        JLabel PersoText = new JLabel("Personnal informations");
        PersoText.setBounds(50, 25, 350, 30);
        PersoText.setForeground(Color.white);
        PersoText.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel PersoTextDescription = new JLabel("This informations are private and will ");
        PersoTextDescription.setBounds(50, 75, 250, 30);
        PersoTextDescription.setForeground(Color.white);
        PersoTextDescription.setFont(new Font("Segoe UI", Font.BOLD, 10));

        JLabel PersoTextDescription2 = new JLabel("not be shared with others");
        PersoTextDescription2.setBounds(50, 95, 250, 30);
        PersoTextDescription2.setForeground(Color.white);
        PersoTextDescription2.setFont(new Font("Segoe UI", Font.BOLD, 10));

        PersoHolder.add(PersoTextDescription2);
        PersoHolder.add(PersoTextDescription);
        PersoHolder.add(PersoText);

        PersoPanel.add(PersoHolder);

        ElementsPanel.add(PersoPanel);



        //payements panel---------------------------------------------------------
        JPanel PayPanel = new JPanel();
        PayPanel.setBounds(50, 905, 750, 200);
        PayPanel.setBackground(new Color(0x2B2B2B));
        PayPanel.setLayout(null);

        JLabel PayInfo = new JLabel("Payement informations ");
        PayInfo.setBounds(375, 25, 250, 30);
        PayInfo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        PayInfo.setForeground(Color.white);

        PayPanel.add(PayInfo);

        JTextField CardNumber = new JTextField("  Card number");
        CardNumber.setBounds(375, 65, 250, 30);
        CardNumber.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        CardNumber.setForeground(Color.white);
        CardNumber.setCaretColor(Color.white);
        // CardNumber.setBackground(new Color(80, 77, 74, 230));
        CardNumber.setBackground(new Color(0x151515));
        CardNumber.setOpaque(true);

        TextfieldBehave(CardNumber, "  Card number");

        PayPanel.add(CardNumber);

        JTextField CcvNumber = new JTextField("  Ccv number");
        CcvNumber.setBounds(375, 105, 250, 30);
        CcvNumber.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        CcvNumber.setForeground(Color.white);
        CcvNumber.setCaretColor(Color.white);
        // CcvNumber.setBackground(new Color(80, 77, 74, 230));
        CcvNumber.setBackground(new Color(0x151515));
        CcvNumber.setOpaque(true);

        TextfieldBehave(CcvNumber, "  Ccv number");

        PayPanel.add(CcvNumber);

        JButton SavePayement = new JButton("Save changes");
        SavePayement.setBounds(575, 150, 150, 30);
        SavePayement.setFont(new Font("Segoe UI", Font.BOLD, 15));
        SavePayement.setBackground(new Color(0xCBCBCB));
        SavePayement.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SavePayement.setUI(new RoundButtonUI(new Color(0x000000)));

        SavePayement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SavePayement.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                SavePayement.setBackground(new Color(0xCBCBCB));
            }
        });

        PayPanel.add(SavePayement);

        JPanel PayHolder = new JPanel();
        PayHolder.setBounds(0, 0, 325, 200);
        PayHolder.setLayout(null);
        PayHolder.setBackground(new Color(0x1C2837));

        JLabel PayText = new JLabel("Payment informations");
        PayText.setBounds(50, 25, 350, 30);
        PayText.setForeground(Color.white);
        PayText.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel PayTextDescription = new JLabel("your credit and payement information");
        PayTextDescription.setBounds(50, 75, 250, 30);
        PayTextDescription.setForeground(Color.white);
        PayTextDescription.setFont(new Font("Segoe UI", Font.BOLD, 10));

        PayHolder.add(PayTextDescription);
        PayHolder.add(PayText);

        PayPanel.add(PayHolder);

        ElementsPanel.add(PayPanel);



        //sign in infos panel ------------------------------------------------
        JPanel SignPanel = new JPanel();
        SignPanel.setBounds(50, 1145, 750, 400);
        SignPanel.setBackground(new Color(0x2B2B2B));
        SignPanel.setLayout(null);

        JLabel SignInfo = new JLabel("sign and log in informations");
        SignInfo.setBounds(375, 25, 250, 30);
        SignInfo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        SignInfo.setForeground(Color.white);

        SignPanel.add(SignInfo);

        JTextField UsernameField = new JTextField("  username");
        UsernameField.setBounds(375, 65, 250, 30);
        UsernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        UsernameField.setForeground(Color.white);
        UsernameField.setCaretColor(Color.white);
        // UsernameField.setBackground(new Color(80, 77, 74, 230));
        UsernameField.setBackground(new Color(0x151515));
        UsernameField.setOpaque(true);

        TextfieldBehave(UsernameField, "  username");

        SignPanel.add(UsernameField);

        JLabel PasswordInfo = new JLabel("Password");
        PasswordInfo.setBounds(375, 145, 250, 30);
        PasswordInfo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        PasswordInfo.setForeground(Color.white);

        SignPanel.add(PasswordInfo);

        JTextField CurrentPassword = new JTextField();
        CurrentPassword.setBounds(375, 185, 250, 30);
        CurrentPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        CurrentPassword.setForeground(Color.white);
        CurrentPassword.setCaretColor(Color.white);
        // CurrentPassword.setBackground(new Color(80, 77, 74, 230));
        CurrentPassword.setBackground(new Color(0x151515));
        CurrentPassword.setOpaque(true);

        TextfieldBehave(CurrentPassword, "  Current password");

        SignPanel.add(CurrentPassword);

        JTextField NewPassword = new JTextField("  New password");
        NewPassword.setBounds(375, 225, 250, 30);
        NewPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        NewPassword.setForeground(Color.white);
        NewPassword.setCaretColor(Color.white);
        // NewPassword.setBackground(new Color(80, 77, 74, 230));
        NewPassword.setBackground(new Color(0x151515));
        NewPassword.setOpaque(true);

        TextfieldBehave(NewPassword, "  New password");

        SignPanel.add(NewPassword);

        JTextField ConfirmPassword = new JTextField("  Confirm password");
        ConfirmPassword.setBounds(375, 265, 250, 30);
        ConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ConfirmPassword.setForeground(Color.white);
        ConfirmPassword.setCaretColor(Color.white);
        // ConfirmPassword.setBackground(new Color(80, 77, 74, 230));
        ConfirmPassword.setBackground(new Color(0x151515));
        ConfirmPassword.setOpaque(true);

        TextfieldBehave(ConfirmPassword, "  Confirm password");

        SignPanel.add(ConfirmPassword);

        JButton SaveSignIn = new JButton("Save changes");
        SaveSignIn.setBounds(575, 350, 150, 30);
        SaveSignIn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        SaveSignIn.setBackground(new Color(0xCBCBCB));
        SaveSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SaveSignIn.setUI(new RoundButtonUI(new Color(0x000000)));

        SaveSignIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SaveSignIn.setBackground(new Color(0xD42E00));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                SaveSignIn.setBackground(new Color(0xCBCBCB));
            }
        });

        SignPanel.add(SaveSignIn);

        JPanel SignHolder = new JPanel();
        SignHolder.setBounds(0, 0, 325, 400);
        SignHolder.setLayout(null);
        SignHolder.setBackground(new Color(0x1C2837));

        JLabel SignText = new JLabel("Sign in Management");
        SignText.setBounds(50, 25, 350, 30);
        SignText.setForeground(Color.white);
        SignText.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel SignTextDescription = new JLabel("change your sign and log in infos ");
        SignTextDescription.setBounds(50, 75, 250, 30);
        SignTextDescription.setForeground(Color.white);
        SignTextDescription.setFont(new Font("Segoe UI", Font.BOLD, 10));

        SignHolder.add(SignTextDescription);
        SignHolder.add(SignText);

        SignPanel.add(SignHolder);

        ElementsPanel.add(SignPanel);




        Exit.addActionListener(e -> {
            MainCardLayout.show(MainPanel, "Client");
        });

        ExitButton.addActionListener(e -> {
            MainCardLayout.show(MainPanel, "Client");
        });

        return AccountPanel;
    }


}
