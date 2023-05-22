/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import roomdeservrrs.database;
import upload.getData;

/**
 * FXML Controller class
 *
 * @author Ervhyne
 */
public class DashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private Button minimizeBtn;
    @FXML
    private Button closeBtn;
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

    // Manage Room <DashboardController, uploadData>
    @FXML
    private TableView<uploadData> dashboard_tableView;
    @FXML
    private TableColumn<uploadData, String> sched_roomNum;
    @FXML
    private TableColumn<uploadData, String> sched_status;
    @FXML
    private TableColumn<uploadData, String> sched_course;
    @FXML
    private TableColumn<uploadData, String> sched_yearsec;
    @FXML
    private TableColumn<uploadData, String> sched_date;
    @FXML
    private TableColumn<uploadData, String> sched_entryTime;
    @FXML
    private TableColumn<uploadData, String> sched_exitTime;
    @FXML
    private TableColumn<uploadData, String> sched_subjectCode;
    @FXML
    private TableColumn<uploadData, String> sched_prof;
    @FXML
    private AnchorPane manageRooms_form;
    @FXML
    private ComboBox mr_status;
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
    private JFXTextField mr_roomNum;
    @FXML
    private JFXTextField mr_cys;
    @FXML
    private JFXTextField mr_subject;
    @FXML
    private ComboBox mr_timeDuration;
    @FXML
    private TableView<roomData> mr_tableView;
    @FXML
    private TableColumn<roomData, String> mr_col_floorNum;
    @FXML
    private TableColumn<roomData, String> mr_col_roomNum;
    @FXML
    private TableColumn<roomData, String> mr_col_status;
    @FXML
    private TableColumn<roomData, String> mr_col_cys;
    @FXML
    private TableColumn<roomData, String> mr_col_timeDuration;
    @FXML
    private TableColumn<roomData, String> mr_col_subject;
    @FXML
    private TextField mr_search;

    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    // DASHBOARD Navigation Menu
    
    // Username 
    public void displayUsername() {
        username.setText(getData.Username);
    }
    
    // DASHBOARD Count // CURRENTLY WORKING ON IT.
    public void dashboardCount() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT * schedule WHERE entryTime = '"+sqlDate+"'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
        } catch(Exception e){e.printStackTrace();}
    }
    
    
    // Executing commands for SQL Preparation, fow window 'MANAGE ROOM/Manage Room'
    public ObservableList<roomData> mrListData() {
        ObservableList<roomData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM room";
        connect = database.connectDb();

        try {
            roomData roomD;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                roomD = new roomData(result.getInt("roomNumber"),
                        result.getString("status"),
                        result.getString("cys"),
                        result.getString("timeDuration"),
                        result.getString("subject"));

                listData.add(roomD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // Manage Room - DISPLAY/SHOW List of Data 
    // Use " mrShowData(); " in any possible fit of a situation. 
    private ObservableList<roomData> roomDataList;

    public void mrShowData() {

        roomDataList = mrListData();
        mr_col_roomNum.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        mr_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        mr_col_cys.setCellValueFactory(new PropertyValueFactory<>("cys"));
        mr_col_timeDuration.setCellValueFactory(new PropertyValueFactory<>("timeDuration"));
        mr_col_subject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        mr_tableView.setItems(roomDataList);

    }

    // Fixed. Omegalul - 05/22/2023 - Fixed the SQL lines and it's unnecessaries
    // Executing commands for SQL Preparation, fow window 'DASHBOARD/Dashboard'
    public ObservableList<uploadData> scheduleListData() {
        ObservableList<uploadData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM schedule";
        connect = database.connectDb();

        try {
            uploadData schedD;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                schedD = new uploadData(result.getInt("roomNum"),
                        result.getString("prof"),
                        result.getString("course"),
                        result.getString("yrndsec"),
                        result.getString("subjectCode"),
                        result.getString("entryTime"),
                        result.getString("exitTime"),
                        result.getDate("date"));

                listData.add(schedD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    // DASHBOARD - Show All Data in Table
    // DASHBOARD - Show List of Schedules
    private ObservableList<uploadData> listUploadData;

    public void scheduleShowData() {
        listUploadData = scheduleListData();

        sched_roomNum.setCellValueFactory(new PropertyValueFactory<>("roomNum"));
        sched_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        sched_yearsec.setCellValueFactory(new PropertyValueFactory<>("yrndsec"));
        sched_entryTime.setCellValueFactory(new PropertyValueFactory<>("entryTime"));
        sched_exitTime.setCellValueFactory(new PropertyValueFactory<>("exitTime"));
        sched_subjectCode.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        sched_prof.setCellValueFactory(new PropertyValueFactory<>("prof"));
        sched_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        dashboard_tableView.setItems(listUploadData);

    }
    
    /* NAVIGATION BUTTONS (SWITCHING FORMS)
    *   [DASHBOARD BUTTON] [MANAGE ROOMS]
    */
    
    public void switchForm(ActionEvent event) {
        
        // Dashboard Button - show Data & re-enable Search
        
        if(event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            manageRooms_form.setVisible(false);
            System.out.println("Switched to Dashboard.");
            
            scheduleShowData();
            scheduleSearch();
        
        // Manage Room Button - show Data & re-enable Search    
        
        } else if(event.getSource() == manageRooms_btn) {
            dashboard_form.setVisible(false);
            manageRooms_form.setVisible(true);
            System.out.println("Switched to Manage Rooms.");
            
            mrShowData();
            scheduleSearch();
            mrSearch();
        }
    }
    
    // DASHBOARD - Search Bar
    public void scheduleSearch() {

        FilteredList<uploadData> filter = new FilteredList<>(listUploadData, e -> true);

        dashboard_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateSchedule -> {

                if (newValue == null && newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateSchedule.getProf().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateSchedule.getClass().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateSchedule.getYrndsec().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateSchedule.getRoomNum().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateSchedule.getSubjectCode().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateSchedule.getEntryTime().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateSchedule.getExitTime().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateSchedule.getDate().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });

        });
        
        SortedList<uploadData> sortList = new SortedList<>(filter);
        
        sortList.comparatorProperty().bind(dashboard_tableView.comparatorProperty());
        dashboard_tableView.setItems(sortList);
        
        
    }
    
    // MANAGE ROOM - Search Bar
    public void mrSearch() {
    
        FilteredList<roomData> filter = new FilteredList<>(roomDataList, e -> true);

        mr_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateRoom -> {

                if (newValue == null && newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateRoom.getRoomNumber().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRoom.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRoom.getCys().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRoom.getTimeDuration().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRoom.getSubject().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });

        });
        
        SortedList<roomData> sortList = new SortedList<>(filter);
        
        sortList.comparatorProperty().bind(mr_tableView.comparatorProperty());
        mr_tableView.setItems(sortList);
        
        
    }
    
    // Manage Room - Select Data in Table [ON-CLICK]
    public void mrSelectData() {
        roomData roomD = mr_tableView.getSelectionModel().getSelectedItem();
        int num = mr_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        mr_roomNum.setText(String.valueOf(roomD.getRoomNumber()));
        mr_cys.setText(String.valueOf(roomD.getCys()));
        mr_subject.setText(String.valueOf(roomD.getSubject()));
    }

    // Manage Room - Add Data [BUTTON]
    // After Adding, show the updated Table & Clear the Text Field after Updating.
    public void mrAdd() {
        String sql = "INSERT INTO room (roomNumber,status,cys,timeDuration,subject) VALUES(?,?,?,?,?)";

        connect = database.connectDb();

        try {

            String roomNumber = mr_roomNum.getText();
            String status = (String) mr_status.getSelectionModel().getSelectedItem();
            String cys = mr_cys.getText();
            String timeDuration = (String) mr_timeDuration.getSelectionModel().getSelectedItem();
            String subject = mr_subject.getText();

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, roomNumber);
            prepare.setString(2, status);
            prepare.setString(3, cys);
            prepare.setString(4, timeDuration);
            prepare.setString(5, subject);

            Alert alert = null;

            if (roomNumber == null || status == null || cys == null || timeDuration == null || subject == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {

                String check = "SELECT roomNumber FROM room WHERE roomNumber = '" + roomNumber + "'";

                prepare = connect.prepareStatement(check);
                result = prepare.executeQuery();

                //Check if the room number has the same number to the previous one
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Room #" + roomNumber + " already exists on the data");
                    alert.showAndWait();
                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, roomNumber);
                    prepare.setString(2, status);
                    prepare.setString(3, cys);
                    prepare.setString(4, timeDuration);
                    prepare.setString(5, subject);

                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Added!");
                    alert.showAndWait();

                    mrShowData();
                    //Clear field infos
                    mrClear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Manage Room - Update Data [BUTTON]
    // After Updating, show the updated Table & Clear the Text Field after Updating.
    public void mrUpdate() {
        String roomNum = mr_roomNum.getText();
        String status1 = (String) mr_status.getSelectionModel().getSelectedItem();
        String cys1 = mr_cys.getText();
        String timeDuration1 = (String) mr_timeDuration.getSelectionModel().getSelectedItem();
        String subject1 = mr_subject.getText();

        String sql = "UPDATE room SET status = '"
                + status1 + "', timeDuration = '" + timeDuration1
                + "', cys = '" + cys1 + "', subject = '" + subject1
                + "' WHERE roomNumber ='" + roomNum + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (roomNum == null || status1 == null || cys1 == null || timeDuration1 == null || subject1 == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the data first");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();

                //To show the updated table view
                mrShowData();
                //To clear the fields
                mrClear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Manage Room - Delete Data [BUTTON]
    // After Deleting, show the updated Table & Clear the Text Field after Updating.
    public void mrDelete() {
        String roomNum = mr_roomNum.getText();
        String status1 = (String) mr_status.getSelectionModel().getSelectedItem();
        String cys1 = mr_cys.getText();
        String timeDuration1 = (String) mr_timeDuration.getSelectionModel().getSelectedItem();
        String subject1 = mr_subject.getText();

        String deleteData = "DELETE FROM room WHERE roomNumber = '" + roomNum + "'";
        connect = database.connectDb();

        try {

            Alert alert;

            if (roomNum == null || status1 == null || cys1 == null || timeDuration1 == null || subject1 == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the data first");
                alert.showAndWait();

            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete Room #" + roomNum + "");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    mrShowData();
                    mrClear();

                } else {
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Manage Room - Clear Text Field [BUTTON]
    // Clear Text Field
    public void mrClear() {
        mr_roomNum.setText("");
        mr_status.getSelectionModel().clearSelection();
        mr_cys.setText("");
        mr_timeDuration.getSelectionModel().clearSelection();
        mr_subject.setText("");
    }

    // [SEND DATA TO DASHBOARD] Manage Room - Check-in Upload
    public void mrCheckIn() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/upload/upload.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            int width = 690, height = 620;
            stage.setMinHeight(400 + 15);
            stage.setMinWidth(400 + 15);

            Image icon = new Image(getClass().getResourceAsStream("/img/uploadrrsIcon.png"));
            stage.getIcons().add(icon);

            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String[] status = {"Deserved", "Vacant", "Room/Skill Issue", "Defense", "Admission"};

    // Manage Room - Select Room Status 
    private void mrStatus() {
        List<String> listData = new ArrayList<>();

        for (String data : status) {
            listData.add(data);
        }

        ObservableList list = FXCollections.observableArrayList(listData);
        mr_status.setItems(list);
    }

    // Manage Room - Enter Time Duration
    private String duration[] = {"---", "1hr", "2hr", "3hr", "4hr", "5hr", "Uncertain"};

    private void mrTimeDuration() {
        List<String> listData = new ArrayList<>();

        for (String data : duration) {
            listData.add(data);
        }

        ObservableList list = FXCollections.observableArrayList(listData);
        mr_timeDuration.setItems(list);
    }

    private double x = 0;
    private double y = 0;

    // Dashboard Sign-out / Log-out
    public void logout() {
        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                Parent root = FXMLLoader.load(getClass().getResource("/login/LoginPage.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((javafx.scene.input.MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((javafx.scene.input.MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                Image icon = new Image(getClass().getResourceAsStream("/img/appicon.png"));
                stage.getIcons().add(icon);

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
                logout_btn.getScene().getWindow().hide();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // All Window - Close Button
    public void close() {
        System.exit(0);
    }

    // All Window - Minimize Button
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    // Execute Commands / Initilize (Execute Order 66!)
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        mrTimeDuration();
        mrStatus();
        
        // Shows Table Data in MANAGE ROOM Form
        mrShowData();
        // Shows Table Data in DASHBOARD Form
        scheduleShowData();
        // Enables Search Bar in DASHBOARD Form
        scheduleSearch();
        // Enables Search Bar in MANAGE ROOM Form
        mrSearch();

    }
}
