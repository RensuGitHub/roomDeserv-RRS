/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package upload;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import roomdeservrrs.database;

/**
 * FXML Controller class
 *
 * @author Ervhyne
 */
public class UploadController implements Initializable {

    @FXML
    private AnchorPane upload_form;

    @FXML
    private JFXTextField profName;

    @FXML
    private JFXTextField up_course;

    @FXML
    private JFXTextField up_yrsec;

    @FXML
    private JFXTextField subjectCode;

    @FXML
    private JFXTextField entryTime;

    @FXML
    private JFXTextField exitTime;

    @FXML
    private JFXDatePicker up_date;

    @FXML
    private JFXTextField up_roomNumber;

    @FXML
    private JFXButton up_resetBtn;

    @FXML
    private JFXButton up_receiptBtn;

    @FXML
    private JFXButton up_createSchedule;
    
    // Separator //
    
    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    
    // Buttons
    // Reset Btn
    public void reset() {
        profName.setText("");
        up_course.setText("");
        up_yrsec.setText("");
        up_roomNumber.setText("");
        subjectCode.setText("");
        entryTime.setText("");
        exitTime.setText("");
    }
    
    // Upload Button (NOT IN THE UPLOAD POP-UP WINDOW)
    
    public void uploadCheckIn() {
        String insertInfoData = "INSERT INTO schedule (prof,course,yrndsec,roomNum,subjectCode,entryTime,exitTime,date) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        connect = database.connectDb();

        try {

            String professor = profName.getText();
            String course = up_course.getText();
            String yrsec = up_yrsec.getText();
            String roomNum = up_roomNumber.getText();
            String subject = subjectCode.getText();
            String entryT = entryTime.getText();
            String exitT = exitTime.getText();
            String checkDate = String.valueOf(up_date.getValue());

            Alert alert = null;

            if (professor == null || course == null || yrsec == null || roomNum == null
                    || subject == null || entryT == null || exitT == null || checkDate == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();

            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(insertInfoData);
                    prepare.setString(1, professor);
                    prepare.setString(2, course);
                    prepare.setString(3, yrsec);
                    prepare.setString(4, roomNum);
                    prepare.setString(5, subject);
                    prepare.setString(6, entryT);
                    prepare.setString(7, exitT);
                    prepare.setString(8, checkDate);

                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Uploaded!");
                    alert.showAndWait();
                        // Once it succeed, reset the fields.
                    reset();

                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    // PRINTING PROOF (RECEIPT)
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    

}
