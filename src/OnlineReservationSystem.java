import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class OnlineReservationSystem {

    JFrame frame;
    JTextField userField;
    JPasswordField passField;

    String loggedUser = "";

    // Store users and reservations in memory
    HashMap<String,String> users = new HashMap<>();
    ArrayList<Reservation> reservations = new ArrayList<>();

    JTextField passengerField, trainField, fromField, toField, dateField;
    JTextField pnrField;

    public OnlineReservationSystem() {

        frame = new JFrame("Online Reservation System");
        frame.setSize(600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginPage();

        frame.setVisible(true);
    }

    // LOGIN PAGE
    void loginPage(){

        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Login Page");
        title.setBounds(250,30,200,30);

        JLabel user = new JLabel("Username:");
        user.setBounds(150,120,100,30);

        userField = new JTextField();
        userField.setBounds(250,120,180,30);

        JLabel pass = new JLabel("Password:");
        pass.setBounds(150,170,100,30);

        passField = new JPasswordField();
        passField.setBounds(250,170,180,30);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(250,240,100,35);

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(250,290,100,35);

        loginBtn.addActionListener(e -> loginUser());
        signupBtn.addActionListener(e -> signupUser());

        panel.add(title);
        panel.add(user);
        panel.add(userField);
        panel.add(pass);
        panel.add(passField);
        panel.add(loginBtn);
        panel.add(signupBtn);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // SIGNUP
    void signupUser(){

        String username = userField.getText();
        String password = String.valueOf(passField.getPassword());

        if(users.containsKey(username)){
            JOptionPane.showMessageDialog(frame,"Username already exists!");
        }
        else{
            users.put(username,password);
            JOptionPane.showMessageDialog(frame,"Signup Successful!");
        }
    }

    // LOGIN
    void loginUser(){

        String username = userField.getText();
        String password = String.valueOf(passField.getPassword());

        if(users.containsKey(username) && users.get(username).equals(password)){

            loggedUser = username;

            JOptionPane.showMessageDialog(frame,"Login Successful! Welcome "+loggedUser);

            showMainMenu();
        }
        else{
            JOptionPane.showMessageDialog(frame,"Invalid Username or Password!");
        }
    }

    // MAIN MENU
    void showMainMenu(){

        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel welcome = new JLabel("Welcome "+loggedUser);
        welcome.setBounds(200,50,300,30);

        JButton reservationBtn = new JButton("Reservation Form");
        reservationBtn.setBounds(200,130,200,50);

        JButton cancelBtn = new JButton("Cancellation Form");
        cancelBtn.setBounds(200,210,200,50);

        reservationBtn.addActionListener(e -> showReservationForm());
        cancelBtn.addActionListener(e -> showCancellationForm());

        panel.add(welcome);
        panel.add(reservationBtn);
        panel.add(cancelBtn);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // RESERVATION FORM
    void showReservationForm(){

        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Reservation Form");
        title.setBounds(220,30,200,30);

        JLabel passengerLabel = new JLabel("Passenger Name:");
        passengerLabel.setBounds(100,100,120,30);

        passengerField = new JTextField();
        passengerField.setBounds(250,100,200,30);

        JLabel trainLabel = new JLabel("Train Name:");
        trainLabel.setBounds(100,150,120,30);

        trainField = new JTextField();
        trainField.setBounds(250,150,200,30);

        JLabel fromLabel = new JLabel("From Station:");
        fromLabel.setBounds(100,200,120,30);

        fromField = new JTextField();
        fromField.setBounds(250,200,200,30);

        JLabel toLabel = new JLabel("To Station:");
        toLabel.setBounds(100,250,120,30);

        toField = new JTextField();
        toField.setBounds(250,250,200,30);

        JLabel dateLabel = new JLabel("Journey Date:");
        dateLabel.setBounds(100,300,120,30);

        dateField = new JTextField();
        dateField.setBounds(250,300,200,30);

        JButton submitBtn = new JButton("Submit Reservation");
        submitBtn.setBounds(200,370,180,40);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20,20,80,30);

        submitBtn.addActionListener(e -> reserveTicket());
        backBtn.addActionListener(e -> showMainMenu());

        panel.add(title);
        panel.add(passengerLabel);
        panel.add(passengerField);
        panel.add(trainLabel);
        panel.add(trainField);
        panel.add(fromLabel);
        panel.add(fromField);
        panel.add(toLabel);
        panel.add(toField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(submitBtn);
        panel.add(backBtn);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // PNR GENERATOR
    int generatePNR(){

        Random r = new Random();
        return 100000 + r.nextInt(900000);
    }

    // RESERVE TICKET
    void reserveTicket(){

        int pnr = generatePNR();

        Reservation r = new Reservation(
                pnr,
                loggedUser,
                passengerField.getText(),
                trainField.getText(),
                fromField.getText(),
                toField.getText(),
                dateField.getText()
        );

        reservations.add(r);

        JOptionPane.showMessageDialog(frame,
                "Ticket Reserved Successfully\nPNR: "+pnr);
    }

    // CANCELLATION FORM
    void showCancellationForm(){

        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Cancellation Form");
        title.setBounds(220,50,200,30);

        JLabel pnrLabel = new JLabel("Enter PNR:");
        pnrLabel.setBounds(150,150,120,30);

        pnrField = new JTextField();
        pnrField.setBounds(250,150,150,30);

        JButton fetchBtn = new JButton("Fetch Ticket");
        fetchBtn.setBounds(200,220,150,40);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(20,20,80,30);

        fetchBtn.addActionListener(e -> fetchTicket());
        backBtn.addActionListener(e -> showMainMenu());

        panel.add(title);
        panel.add(pnrLabel);
        panel.add(pnrField);
        panel.add(fetchBtn);
        panel.add(backBtn);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // FETCH TICKET
    void fetchTicket(){

        int pnr = Integer.parseInt(pnrField.getText());

        for(Reservation r : reservations){

            if(r.pnr == pnr){

                String details =
                        "Passenger: "+r.passenger+
                                "\nTrain: "+r.train+
                                "\nFrom: "+r.from+
                                "\nTo: "+r.to+
                                "\nDate: "+r.date;

                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        details+"\n\nCancel Ticket?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

                if(confirm == JOptionPane.YES_OPTION){

                    reservations.remove(r);

                    JOptionPane.showMessageDialog(frame,
                            "Ticket Cancelled Successfully");

                }

                return;
            }
        }

        JOptionPane.showMessageDialog(frame,"Invalid PNR");
    }

    // RESERVATION CLASS
    class Reservation{

        int pnr;
        String user;
        String passenger;
        String train;
        String from;
        String to;
        String date;

        Reservation(int pnr,String user,String passenger,String train,String from,String to,String date){

            this.pnr = pnr;
            this.user = user;
            this.passenger = passenger;
            this.train = train;
            this.from = from;
            this.to = to;
            this.date = date;
        }
    }

    public static void main(String[] args) {

        new OnlineReservationSystem();
    }
}