/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dashboard;

import java.sql.Date;

/**
 *
 * @author velas
 */
public class uploadData {

    private Integer roomNum;
    private String course;
    private String yrndsec;
    private String entryTime;
    private String exitTime;
    private String subjectCode;
    private String prof;
    private Date date;

    public uploadData(int roomNum, String prof, String course, String yrndsec, String subjectCode, String entryTime, String exitTime, Date date) {
        this.roomNum = roomNum;
        this.course = course;
        this.yrndsec = yrndsec;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.subjectCode = subjectCode;
        this.prof = prof;
        this.date = date;
    }
    
    public Integer getRoomNum(){
       return roomNum;

    }
        
    public String getCourse(){
       return course;
        
    }
    
    public String getYrndsec(){
       return yrndsec;
        
    }
    
    public String getEntryTime(){
       return entryTime;
        
    }
    
    public String getExitTime(){
       return exitTime;
        
    }
    
    public String getSubjectCode(){
       return subjectCode;
        
    }
    public String getProf(){
       return prof;
        
    }
    
    public Date getDate(){
       return date;
        
    }

}
