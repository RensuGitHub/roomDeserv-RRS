/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dashboard;

/**
 *
 * @author Ervhyne
 */
public class roomData {
    private Integer roomNumber;
    private String status;
    private String cys;
    private String timeDuration;
    private String subject;

    public roomData(int roomNumber, String status, String cys, String timeDuration, String subject){
        this.roomNumber = roomNumber;
        this.status = status;
        this.cys = cys;
        this.timeDuration = timeDuration;
        this.subject = subject;
    }
 
    
    public Integer getRoomNumber(){
        return roomNumber;
    }
    
    public String getStatus(){
        return status;
    }
    
    public String getCys(){
        return cys;
    }
    
    public String getTimeDuration(){
        return timeDuration;
    }
    
    public String getSubject(){
        return subject;
    }
}
