package com.example.javafxCat.service;

import com.example.javafxCat.dao.impl.CatDaoImp;
import com.example.javafxCat.dao.CatDao;
import com.example.javafxCat.entities.Cat;
import com.example.javafxCat.dao.impl.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
public class CatService {
    private static CatDao catDao = new CatDaoImp();

    public List<Cat> findAll() {
        return catDao.findAll();
    }

    public static void save(Cat cat) {

        catDao.insert(cat);

    }
    public void update(Cat cat) {

        catDao.update(cat);

    }
    public void remove(Cat cat) {
        catDao.deleteById(cat.getId());
    }



    public void exporterVersExcel(String path){
        catDao.exporterVersExcel(path);

    }
    public void importerDepuisExcel(String path){
        catDao.importerDepuisExcel(path);
    }
    

    public void exporterVersTxt(){

    }
    public void importerDepuisTxt(){

    }

    public void delete(Cat cat) {
        catDao.delete(Integer.valueOf(cat.getId()));
    }

    public void update(Cat cat, int catID) {
        catDao.update(cat, catID);
    }


    public void exporterVersTxt(String path) {
        catDao.exporterVersTxt(path);
    }

    public void importerDepuisTxt(String path) {
        catDao.importerDepuisTxt(path);

    }

    public void exporterVersJson(String path) {
        catDao.exporterVersJson(path);

    }

    public void importerDepuisJson(String path) {
        catDao.importerDepuisJson(path);
    }
}
