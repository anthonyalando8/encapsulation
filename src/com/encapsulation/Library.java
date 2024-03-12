package com.encapsulation;

import com.mybean.ProgressDialog;

import javax.swing.*;
import java.sql.*;

public class Library{
    public Library(Books book){
        try{
            Connection connection = new SqlConnector().createConnection();

            JOptionPane.showMessageDialog(null, (insertBookData(book, connection)? "Recorded submitted successfully!": "Error occurred!"));
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Database says\n"+e.getMessage());
        }

    }
    private boolean insertBookData(Books book, Connection connection){
        ProgressDialog progressDialog = new ProgressDialog();
        try {
            progressDialog.show();
            String select = "SELECT * FROM books WHERE ISBN = ?";
            PreparedStatement createStatement = connection.prepareStatement(createQuery());
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery());
            PreparedStatement searchBook = connection.prepareStatement(select);
            createStatement.execute();

            searchBook.setString(1, book.getISBN());
            ResultSet set = searchBook.executeQuery();
            boolean exists = set.next();

            if (exists){
                int opt = JOptionPane.showConfirmDialog(null, "Book exists.\nUpdate record instead.","Record exist", JOptionPane.OK_CANCEL_OPTION);
                if(opt ==JOptionPane.OK_OPTION){
                    int available = set.getInt("total_copies");
                    int new_total = available + book.getTotal_copies();
                    PreparedStatement updateTable = connection.prepareStatement("UPDATE books SET total_copies = ? WHERE ISBN = ?");
                    updateTable.setInt(1, new_total);
                    updateTable.setString(2, book.getISBN());

                    progressDialog.dismiss();
                    return updateTable.executeUpdate() > 0;
                }

            }else {
                insertStatement.setString(1,book.getTitle());
                insertStatement.setString(2,book.getAuthor());
                insertStatement.setString(3,book.getISBN());
                insertStatement.setString(4,book.getPublisher());
                insertStatement.setString(5,book.getEdition());
                insertStatement.setString(6,book.getLanguage());
                insertStatement.setInt(7,book.getNo_Pages());
                insertStatement.setBlob(8,(Blob) null);
                insertStatement.setString(9,book.getSummary());
                insertStatement.setString(10,book.getLocation());
                insertStatement.setString(11,book.getSubject());


                progressDialog.dismiss();
                int insertRow = insertStatement.executeUpdate();
                return insertRow > 0;
            }


        }catch (SQLException e){
            progressDialog.dismiss();
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        return false;
    }

    private String insertQuery(){
        return "INSERT INTO books " +
                "(title, authors, ISBN, publisher, edition, language, number_pages, cover_image, " +
                "summary, location_shelf, subject) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    }
    private String createQuery(){
        return "CREATE TABLE IF NOT EXISTS `books` (" +
                "  `bookID` int PRIMARY KEY AUTO_INCREMENT," +
                "  `title` varchar(255) NOT NULL," +
                "  `authors` varchar(255) NOT NULL," +
                "  `ISBN` varchar(255) NOT NULL," +
                "  `publisher` varchar(255) NOT NULL," +
                "  `edition` varchar(255) NOT NULL," +
                "  `language` varchar(255) NOT NULL," +
                "  `number_pages` int DEFAULT NULL," +
                "  `cover_image` blob," +
                "  `summary` varchar(500) DEFAULT NULL," +
                "  `location_shelf` varchar(255) DEFAULT NULL," +
                "  `acquisition_date` datetime DEFAULT NOW()," +
                "  `availability_status` varchar(255) DEFAULT NULL," +
                "  `total_copies` int DEFAULT 1," +
                "  `total_lend` int DEFAULT 0," +
                "  `total_in_shelf` int DEFAULT 1," +
                "  `subject` varchar(255) DEFAULT NULL);";
    }

}
