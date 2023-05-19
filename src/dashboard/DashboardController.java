/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import roomdeservrrs.database;

/**
 * FXML Controller class
 *
 * @author Ervhyne
 */
public class DashboardController implements Initializable {

    @FXML
    private AnchorPane dashboardWholePane;
    @FXML
    private FontAwesomeIconView minimizeBtn;
    @FXML
    private FontAwesomeIconView closeBtn;
    @FXML
    private Label username;
    @FXML
    private JFXButton dashboard_btn;
    @FXML
    private JFXButton manageRooms_btn;
    @FXML
    private JFXButton requests_btn;
    @FXML
    private JFXButton logout_btn;
    @FXML
    private AnchorPane dashboard_form;
    @FXML
    private AnchorPane dashboard_roomsOccupied;
    @FXML
    private Label dashboard_roomsAvailable;
    @FXML
    private TextField dashboard_search;
    @FXML
    private TableView<?> dashboard_table;
    @FXML
    private AnchorPane manageRooms_form;
    @FXML
    private ComboBox<?> mr_status;
    @FXML
    private JFXButton mr_addBtn;
    @FXML
    private JFXButton mr_updateBtn;
    @FXML
    private JFXButton mr_clearBtn;
    @FXML
    private JFXButton mr_deleteBtn;
    @FXML
    private JFXButton mr_uploadBtn;
    @FXML
    private JFXTextField mr_floorNum;
    @FXML
    private JFXTextField mr_roomNum;
    @FXML
    private JFXTextField mr_cys;
    @FXML
    private JFXTextField mr_subject;
    @FXML
    private ComboBox<?> mr_timeDuration;
    @FXML
    private TableView<?> mr_tableView;
    @FXML
    private TableColumn<?, ?> mr_col_floorNum;
    @FXML
    private TableColumn<?, ?> mr_col_roomNum;
    @FXML
    private TableColumn<?, ?> mr_col_status;
    @FXML
    private TableColumn<?, ?> mr_col_cys;
    @FXML
    private TableColumn<?, ?> mr_col_timeDuration;
    @FXML
    private TableColumn<?, ?> mr_col_subject;
    @FXML
    private TextField mr_search;

    
    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    public void mrAdd(){
        String sql = "INSERT INTO room (floorNumber,roomNumber,status,cys,timeDuration,subject) VALUES(?,?,?,?,?,?)";
        
        connect = database.connectDb();
        
        try{
            
            String floorNumber = mr_floorNum.getText();
            String roomNumber = mr_roomNum.getText();
            String status = (String)mr_status.getSelectionModel().getSelectedItem(); 
            String cys = mr_cys.getText();
            String timeDuration = (String)mr_timeDuration.getSelectionModel().getSelectedItem(); 
            String subject = mr_subject.getText();
            
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,floorNumber);
            prepare.setString(2,roomNumber);
            prepare.setString(3,status);
            prepare.setString(4,cys);
            prepare.setString(5,timeDuration);
            prepare.setString(6,subject);
            
            Alert alert;
            
            if(floorNumber.isEmpty() || roomNumber.isEmpty() || status.isEmpty() || cys.isEmpty() || timeDuration.isEmpty() || subject.isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();                
            }else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1,floorNumber);
                prepare.setString(2,roomNumber);
                prepare.setString(3,status);
                prepare.setString(4,cys);
                prepare.setString(5,timeDuration);
                prepare.setString(6,subject);
                
                prepare.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Succesfully Added!");
                alert.showAndWait();                
            
            }
            
        }catch(Exception e){e.printStackTrace();
        }
        
    }
    
    //Combo Boxes Data
    
    private String status[]= {"Occupied", "Unoccupied"};
    
    private void mrStatus(){
        List<String> listData = new ArrayList<>();
        
        for (String data: status){
            listData.add(data);
        }
        
        ObservableList list = FXCollections.observableArrayList(listData);
        mr_status.setItems(list);
    }
    
    private String timeDuration[]= {"1hr", "2hr", "3hr", "4hr", "5hr"};
    
    private void mrTimeDuration(){
        List<String> listData = new ArrayList<>();
        
        for (String data: timeDuration){
            listData.add(data);
        }
        
        ObservableList list = FXCollections.observableArrayList(listData);
        mr_timeDuration.setItems(list);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mrStatus();
        mrTimeDuration();
    }    
   
}
