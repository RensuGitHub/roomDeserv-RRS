/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package roomdeservrrs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author velas
 */


public class LoginPageController {

    @FXML
    private AnchorPane loginMainPane;

    @FXML
    private AnchorPane loginLeftPane;

    @FXML
    private AnchorPane loginRightPane;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXTextField loginUsername;

    @FXML
    private JFXPasswordField loginPassword;

    @FXML
    private JFXButton loginExit;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    public void login() {
        
        String user = loginUsername.getText();
        String pass = loginPassword.getText();
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";
        
        connect = database.connectDb();
        try {
            prepare = connect.prepareCall(sql);
            prepare.setString(1, user);
            prepare.setString(2, pass);
            
            result = prepare.executeQuery();
            
            Alert alert;
            
            if(result.next()) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Login Message");
                alert.setHeaderText(null);
                alert.setContentText("Succesfully Login!");
                alert.showAndWait();
                
                Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                stage.show();
                
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Loggin Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Username and Password!");
                alert.showAndWait();
            }
        }catch (Exception e){e.printStackTrace();}
    }
    
    public void loginExit () {
        System.exit(0);
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
