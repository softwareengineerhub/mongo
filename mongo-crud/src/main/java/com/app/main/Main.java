/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.main;

import com.app.dao.DAO;
import com.app.dao.DAOImpl;
import com.app.model.DataItem;
import java.util.List;

/**
 *
 * @author Denys.Prokopiuk
 */
public class Main {
    
    public static void main(String[] args) {
        DAO dao = new DAOImpl();
        DataItem dataItem = new DataItem();
        dataItem.setType("B");
        dataItem.setValue("Data6");
        //dao.save(dataItem);        
        
        //List<DataItem> items = dao.get("B");
        //System.out.println(items.size());
        
        DataItem updateDataItem = new DataItem();
        updateDataItem.setType("APPLICATION");
        updateDataItem.setValue("WORKFLOW");
       // dao.update(updateDataItem, "B");
      
       //dao.delete("Data1");
       
       List<String> resList= dao.getValuesByType("APPLICATION");
       resList.forEach(System.out::println);
    }
    
}
