/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistant.ui.statistics;

import java.net.URL;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tab;
import libraryassistant.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author Andrei
 */
public class StatisticsFXMLController implements Initializable {

    @FXML
    private PieChart pieChart;
    
    @FXML
    private PieChart pieChartBooks;
    
    private final ObservableList<PieChart.Data> data=FXCollections.observableArrayList();
    private final ObservableList<PieChart.Data> dataBooks=FXCollections.observableArrayList();
    private static int count16 = 0 ; 
    private static int count24 = 0 ;
    private static int count30 = 0 ; 
    private static int count40 = 0 ; 
    private static int countOlder = 0 ; 
    private static ArrayList<Integer> intDate=new ArrayList<>();
    private static Calendar rightNow = Calendar.getInstance();
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Integer> intBooks=new ArrayList<>();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDB();
        setDataAge();
        setDataBooks();
        
        // TODO
    }    
    public void loadDB(){
        // load dates from db and add currentDate.year-dbDate.year as an int into an arrayList
        String qu = "SELECT dob FROM members ";
        ResultSet rs= DatabaseHandler.getInstance().execQuery(qu);
        
        try {
            while(rs.next()){
                java.util.Date date = new Date();
                date=rs.getDate("dob");
                
                cal.setTime(date);
                intDate.add(rightNow.get(Calendar.YEAR)-cal.get(Calendar.YEAR));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
         String qu2 = "SELECT isAvailable FROM books";
         ResultSet rs1= DatabaseHandler.getInstance().execQuery(qu2);   
        try {
            while(rs1.next()){
                Boolean availability=rs1.getBoolean("isAvailable");
                int myInt = (availability) ? 1:0;
                intBooks.add(myInt);
                
            }   } catch (SQLException ex) {
            Logger.getLogger(StatisticsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    
    
    public void setDataAge(){
        //sorting our ages by groups + add them to Piechart
        Integer[] bar = intDate.toArray(new Integer[intDate.size()]);
        
        for(int i =0 ; i<bar.length;i++){
            if(bar[i]<=17){
                count16++;
            }else if(bar[i]>17&&bar[i]<=24){
                count24++;
            }else if(bar[i]>24&&bar[i]<=30){
                count30++;
            }else if(bar[i]>30&&bar[i]<=40){
                count40++;
            }else if(bar[i]>40){
                countOlder++;
            }
        }
        
        data.addAll(new PieChart.Data("Members age <17",count16),
                    new PieChart.Data("Members age 17-24 ",count24),
                    new PieChart.Data("Members age 24-30 ",count30),
                    new PieChart.Data("Members age 30-40 ",count40),
                    new PieChart.Data("Members age 40+ ",countOlder));
        pieChart.setData(data);
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setLabelsVisible(true);
    }
    
    private void setDataBooks(){
 
         Integer[] bar = intBooks.toArray(new Integer[intBooks.size()]);
        int countAv=0;
        int countNv=0;
        for(int i =0 ; i<bar.length;i++){
            if(bar[i]==0){
                countAv++;
            }else{
                countNv++;
            }

        }
        dataBooks.addAll(new PieChart.Data("Borrowed Books",countAv),
                         new PieChart.Data("Books in library",countNv));
        pieChartBooks.setData(dataBooks);
        pieChartBooks.setLegendSide(Side.BOTTOM);
        pieChartBooks.setLabelsVisible(true);   
  
    }
}
