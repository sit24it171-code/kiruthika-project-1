import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class OnlineExam {

    static String username = "student";
    static String password = "1234";

    static JFrame frame;
    static int page = 0;
    static double score = 0;

    static String[][] questions = {

            {"Java is a ?", "Programming Language", "Database", "Programming Language"},
            {"JVM stands for ?", "Java Virtual Machine", "Java Variable Method", "Java Virtual Machine"},

            {"Which keyword creates object?", "new", "class", "new"},
            {"Which is not OOP concept?", "Inheritance", "Compilation", "Compilation"},

            {"Java supports ?", "Pointers", "Interfaces", "Interfaces"},
            {"Default value of int?", "0", "null", "0"},

            {"Which is a loop?", "for", "if", "for"},
            {"Keyword for inheritance?", "extends", "import", "extends"},

            {"Java is ?", "Platform Independent", "Platform Dependent", "Platform Independent"},
            {"Swing package is ?", "java.swing", "javax.swing", "javax.swing"}
    };

    static JLabel q1Label, q2Label, timerLabel;
    static JRadioButton q1op1, q1op2, q2op1, q2op2;
    static ButtonGroup group1, group2;
    static JButton nextBtn;

    static java.util.Timer timer;
    static int timeLeft = 60;

    public static void main(String[] args) {
        loginPage();
    }

    // LOGIN PAGE
    static void loginPage() {

        frame = new JFrame("Login - Online Exam");
        frame.setSize(400,300);
        frame.setLayout(null);

        JLabel title = new JLabel("ONLINE EXAM LOGIN");
        title.setBounds(120,20,200,30);
        frame.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50,80,100,30);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(150,80,150,30);
        frame.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50,130,100,30);
        frame.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(150,130,150,30);
        frame.add(passText);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(140,190,100,30);
        frame.add(loginBtn);

        loginBtn.addActionListener(e -> {

            String user = userText.getText();
            String pass = String.valueOf(passText.getPassword());

            if(user.equals(username) && pass.equals(password)){
                JOptionPane.showMessageDialog(frame,"Login Successful");
                frame.dispose();
                menuPage();
            }else{
                JOptionPane.showMessageDialog(frame,"Invalid Login");
            }

        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // MENU PAGE
    static void menuPage(){

        frame = new JFrame("Menu");
        frame.setSize(400,350);
        frame.setLayout(null);

        JLabel title = new JLabel("WELCOME TO ONLINE EXAM");
        title.setBounds(90,30,250,30);
        frame.add(title);

        JButton updateBtn = new JButton("Update Profile");
        updateBtn.setBounds(120,90,150,30);
        frame.add(updateBtn);

        JButton examBtn = new JButton("Start Exam");
        examBtn.setBounds(120,150,150,30);
        frame.add(examBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(120,210,150,30);
        frame.add(logoutBtn);

        updateBtn.addActionListener(e->{
            frame.dispose();
            updateProfilePage();
        });

        examBtn.addActionListener(e->{

            int confirm = JOptionPane.showConfirmDialog(frame,"Start Exam?");
            if(confirm==0){
                frame.dispose();
                startExam();
            }

        });

        logoutBtn.addActionListener(e->{
            JOptionPane.showMessageDialog(frame,"Logged Out");
            frame.dispose();
            loginPage();
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // UPDATE PROFILE
    static void updateProfilePage(){

        frame = new JFrame("Update Profile");
        frame.setSize(400,300);
        frame.setLayout(null);

        JLabel title = new JLabel("UPDATE PROFILE");
        title.setBounds(130,20,200,30);
        frame.add(title);

        JLabel userLabel = new JLabel("New Username:");
        userLabel.setBounds(50,80,120,30);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(180,80,150,30);
        frame.add(userText);

        JLabel passLabel = new JLabel("New Password:");
        passLabel.setBounds(50,130,120,30);
        frame.add(passLabel);

        JTextField passText = new JTextField();
        passText.setBounds(180,130,150,30);
        frame.add(passText);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(140,200,100,30);
        frame.add(saveBtn);

        saveBtn.addActionListener(e->{

            if(userText.getText().isEmpty() || passText.getText().isEmpty()){
                JOptionPane.showMessageDialog(frame,"Fields cannot be empty");
                return;
            }

            username = userText.getText();
            password = passText.getText();

            JOptionPane.showMessageDialog(frame,"Profile Updated");

            frame.dispose();
            menuPage();

        });

        frame.setVisible(true);
    }

    // START EXAM
    static void startExam(){

        page = 0;
        score = 0;
        timeLeft = 60;

        frame = new JFrame("Online Exam");
        frame.setSize(700,500);
        frame.setLayout(null);

        q1Label = new JLabel();
        q1Label.setBounds(50,40,600,30);
        frame.add(q1Label);

        q2Label = new JLabel();
        q2Label.setBounds(50,220,600,30);
        frame.add(q2Label);

        q1op1 = new JRadioButton();
        q1op1.setBounds(80,80,400,30);

        q1op2 = new JRadioButton();
        q1op2.setBounds(80,120,400,30);

        group1 = new ButtonGroup();
        group1.add(q1op1);
        group1.add(q1op2);

        frame.add(q1op1);
        frame.add(q1op2);

        q2op1 = new JRadioButton();
        q2op1.setBounds(80,260,400,30);

        q2op2 = new JRadioButton();
        q2op2.setBounds(80,300,400,30);

        group2 = new ButtonGroup();
        group2.add(q2op1);
        group2.add(q2op2);

        frame.add(q2op1);
        frame.add(q2op2);

        nextBtn = new JButton("Next");
        nextBtn.setBounds(280,380,120,40);
        frame.add(nextBtn);

        timerLabel = new JLabel("Time Left: 60 sec");
        timerLabel.setBounds(520,10,200,30);
        frame.add(timerLabel);

        startTimer();

        loadPage();

        nextBtn.addActionListener(e->{

            checkAnswers();
            page++;

            if(page==5){
                showResult();
            }else{
                loadPage();
            }

        });

        frame.setVisible(true);
    }

    // TIMER
    static void startTimer(){

        timer = new java.util.Timer();

        timer.scheduleAtFixedRate(new TimerTask(){

            public void run(){

                timeLeft--;
                timerLabel.setText("Time Left: "+timeLeft+" sec");

                if(timeLeft==0){
                    timer.cancel();
                    JOptionPane.showMessageDialog(frame,"Time Over!");
                    showResult();
                }

            }

        },1000,1000);
    }

    static void loadPage(){

        group1.clearSelection();
        group2.clearSelection();

        int index1 = page*2;
        int index2 = index1+1;

        q1Label.setText("Q"+(index1+1)+". "+questions[index1][0]);
        q1op1.setText("A. "+questions[index1][1]);
        q1op2.setText("B. "+questions[index1][2]);

        q2Label.setText("Q"+(index2+1)+". "+questions[index2][0]);
        q2op1.setText("A. "+questions[index2][1]);
        q2op2.setText("B. "+questions[index2][2]);

        if(page==4) nextBtn.setText("Submit");
    }

    static void checkAnswers(){

        int index1 = page*2;
        int index2 = index1+1;

        if(q1op1.isSelected()){
            if(questions[index1][1].equals(questions[index1][3])) score++;
            else score -= 0.25;
        }

        if(q1op2.isSelected()){
            if(questions[index1][2].equals(questions[index1][3])) score++;
            else score -= 0.25;
        }

        if(q2op1.isSelected()){
            if(questions[index2][1].equals(questions[index2][3])) score++;
            else score -= 0.25;
        }

        if(q2op2.isSelected()){
            if(questions[index2][2].equals(questions[index2][3])) score++;
            else score -= 0.25;
        }
    }

    static void showResult(){

        frame.dispose();

        StringBuilder ans = new StringBuilder();

        for(int i=0;i<questions.length;i++){
            ans.append("Q").append(i+1).append(": ")
                    .append(questions[i][3]).append("\n");
        }

        JOptionPane.showMessageDialog(null,
                "Exam Finished\n\nScore: "+score+"/10\n\nCorrect Answers:\n"+ans);

        menuPage();
    }
}