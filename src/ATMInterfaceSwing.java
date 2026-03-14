import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ATMInterfaceSwing {

    static double balance = 10000;
    static ArrayList<String> history = new ArrayList<>();

    static JFrame frame = new JFrame("ATM Interface");

    static String USER_ID = "admin";
    static String PIN = "1234";

    public static void main(String[] args) {

        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        showLoginPage();

        frame.setVisible(true);
    }

    // LOGIN PAGE
    static void showLoginPage() {

        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(4,1,10,10));

        JLabel title = new JLabel("ATM Login", JLabel.CENTER);

        JTextField txtUser = new JTextField(10);
        JPasswordField txtPin = new JPasswordField(10);

        JButton btnLogin = new JButton("LOGIN");

        btnLogin.addActionListener(e -> {

            String user = txtUser.getText();
            String pin = new String(txtPin.getPassword());

            if(user.equals(USER_ID) && pin.equals(PIN)){
                menuPage();
            }
            else{
                JOptionPane.showMessageDialog(frame,"Invalid UserID or PIN");
            }

        });

        JPanel userPanel = new JPanel();
        userPanel.add(new JLabel("User ID: "));
        userPanel.add(txtUser);

        JPanel pinPanel = new JPanel();
        pinPanel.add(new JLabel("PIN: "));
        pinPanel.add(txtPin);

        frame.add(title);
        frame.add(userPanel);
        frame.add(pinPanel);
        frame.add(btnLogin);

        frame.revalidate();
        frame.repaint();
    }

    // MAIN MENU
    static void menuPage() {

        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(7,1,10,5));

        JLabel title = new JLabel("ATM Main Menu", JLabel.CENTER);

        JButton btnWithdraw = new JButton("Withdraw");
        JButton btnDeposit = new JButton("Deposit");
        JButton btnTransfer = new JButton("Transfer");
        JButton btnBalance = new JButton("Check Balance");
        JButton btnHistory = new JButton("Transaction History");
        JButton btnLogout = new JButton("Logout");

        btnWithdraw.addActionListener(e -> operationPage("Withdraw"));
        btnDeposit.addActionListener(e -> operationPage("Deposit"));
        btnTransfer.addActionListener(e -> operationPage("Transfer"));
        btnBalance.addActionListener(e ->
                JOptionPane.showMessageDialog(frame,"Current Balance: ₹"+balance));
        btnHistory.addActionListener(e -> historyPage());

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,"Logout ?");
            if(confirm==0){
                showLoginPage();
            }
        });

        frame.add(title);
        frame.add(btnWithdraw);
        frame.add(btnDeposit);
        frame.add(btnTransfer);
        frame.add(btnBalance);
        frame.add(btnHistory);
        frame.add(btnLogout);

        frame.revalidate();
        frame.repaint();
    }

    // OPERATION PAGE
    static void operationPage(String operation){

        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(6,1,10,5));

        JLabel title = new JLabel(operation+" Page", JLabel.CENTER);
        JLabel result = new JLabel("Enter Amount", JLabel.CENTER);

        JTextField txtAmount = new JTextField(10);
        JTextField txtReceiver = new JTextField(10);

        JButton btnSubmit = new JButton("Submit");
        JButton btnBack = new JButton("Back");

        JPanel amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount: "));
        amountPanel.add(txtAmount);

        JPanel receiverPanel = new JPanel();
        receiverPanel.add(new JLabel("Receiver Acc: "));
        receiverPanel.add(txtReceiver);

        btnSubmit.addActionListener(e->{

            try{

                double amount = Double.parseDouble(txtAmount.getText());

                if(amount<=0){
                    result.setText("Enter valid amount");
                    return;
                }

                if(operation.equals("Withdraw")){

                    if(amount<=balance){
                        balance -= amount;
                        history.add("Withdraw : ₹"+amount);
                        result.setText("Withdraw Successful");
                    }
                    else{
                        result.setText("Insufficient Balance");
                    }
                }

                else if(operation.equals("Deposit")){

                    balance += amount;
                    history.add("Deposit : ₹"+amount);
                    result.setText("Deposit Successful");
                }

                else if(operation.equals("Transfer")){

                    String acc = txtReceiver.getText();

                    if(acc.isEmpty()){
                        result.setText("Enter Receiver Account");
                        return;
                    }

                    if(amount<=balance){
                        balance -= amount;
                        history.add("Transfer ₹"+amount+" to "+acc);
                        result.setText("Transfer Successful");
                    }
                    else{
                        result.setText("Insufficient Balance");
                    }
                }

                txtAmount.setText("");
                txtReceiver.setText("");

            }
            catch(Exception ex){
                result.setText("Invalid Input");
            }

        });

        btnBack.addActionListener(e->menuPage());

        frame.add(title);
        frame.add(result);

        if(operation.equals("Transfer")){
            frame.add(receiverPanel);
        }

        frame.add(amountPanel);
        frame.add(btnSubmit);
        frame.add(btnBack);

        frame.revalidate();
        frame.repaint();
    }

    // TRANSACTION HISTORY
    static void historyPage(){

        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Transaction History", JLabel.CENTER);

        JTextArea txtHistory = new JTextArea();
        txtHistory.setEditable(false);

        if(history.isEmpty()){
            txtHistory.setText("No Transactions Yet");
        }
        else{
            StringBuilder sb = new StringBuilder();

            for(String h: history){
                sb.append(h).append("\n");
            }

            txtHistory.setText(sb.toString());
        }

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e->menuPage());

        frame.add(title,BorderLayout.NORTH);
        frame.add(new JScrollPane(txtHistory),BorderLayout.CENTER);
        frame.add(btnBack,BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();
    }
}