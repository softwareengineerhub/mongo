package com.app.dao;

import com.app.model.DataItem;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Denys.Prokopiuk
 */
public interface DAO {

    public void save(DataItem dataItem);

    public List<DataItem> getDataItemByType(String type);
    
    public List<DataItem> getDataItemByValue(String value);
    
    public List<String> getValuesByType(String type);

    public void update(DataItem dataItem, String valueFilter);

    public void delete(String valueFilter);

}
