package com.encapsulation;

import com.mybean.AccessUI;
import com.mybean.AuthManagerUI;
import com.mybean.ProgressDialog;
import com.mybean.UserModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication extends AuthManagerUI {
    ProgressDialog progressDialog;
    SqlConnector sqlConnector;
    Connection connection;
    boolean addLogin, addSignUp;

    @Override
    public JFrame initializeUI(AccessUI accessUI) {
        return super.initializeUI(accessUI);
    }
    public Authentication(boolean addLogin, boolean addSignUp){
        this.addLogin = addLogin;
        this.addSignUp = addSignUp;
        this.progressDialog = new ProgressDialog();
        this.sqlConnector = new SqlConnector();
        try{
            this.connection = this.sqlConnector.createConnection();
            initializeUI(accessUI);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Database server says:\n"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
    AccessUI accessUI = new AccessUI() {
        @Override
        public void login(JPanel jPanel, JPanel jPanel1) {
            if(addLogin){
                jPanel.add(jPanel1);
            }
        }

        @Override
        public void register(JPanel jPanel, JPanel jPanel1) {
            if (addSignUp){
                jPanel.add(jPanel1);
            }
        }

        @Override
        public void onVerifyLogin(String s, String s1) {
            try {
                loginUser(s, s1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onVerifyRegister(UserModel userModel) {
            try {
                createUser(userModel);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private void createUser(UserModel userModel) throws SQLException {
        progressDialog.show();
        String createTableSql = "CREATE TABLE IF NOT EXISTS users (userID VARCHAR(50) PRIMARY KEY,userName VARCHAR(255) NOT NULL,email VARCHAR(255),password VARCHAR(255) NOT NULL)";
        String insertQuery = "INSERT INTO users (userID, email, userName, password) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement createTable = connection.prepareStatement(createTableSql);
            PreparedStatement insertValues = connection.prepareStatement(insertQuery);
            createTable.execute();

            insertValues.setString(1, userModel.getUserId());
            insertValues.setString(2, userModel.getEmail());
            insertValues.setString(3, userModel.getUserName());
            insertValues.setString(4, userModel.getUserPassword());

            int rowInserted =  insertValues.executeUpdate();

            progressDialog.dismiss();
            if (rowInserted > 0){
                connection.close();
                this.dispose();
                new MainUI();
            }else {
                JOptionPane.showMessageDialog(null,
                        "Something went wrong!");
            }
        }catch (SQLException e){
            progressDialog.dismiss();
            JOptionPane.showMessageDialog(null, "Database says:\n"
            +e.getMessage());
        }

    }
    private void loginUser(String email, String password) throws SQLException{
        progressDialog.show();
        String select = "SELECT * FROM users WHERE email = ?";


        PreparedStatement selectStatement = connection.prepareStatement(select);
        selectStatement.setString(1, email);
        ResultSet resultSet = selectStatement.executeQuery();

        boolean emailExist = resultSet.next();
        if(emailExist){
            String userPassword = resultSet.getString("password");
            if (password.equals(userPassword)) {
                System.out.println("Logged In");
                progressDialog.dismiss();
                connection.close();
                resultSet.close();
                this.dispose();
                new MainUI();
            } else {
                System.err.println("Incorrect password");
                progressDialog.dismiss();
                JOptionPane.showMessageDialog(null, "Incorrect password");
            }
        }else {
            progressDialog.dismiss();
            JOptionPane.showMessageDialog(null, "Incorrect username or email");
        }

    }
}
