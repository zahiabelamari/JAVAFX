package com.example.javafxCat.dao;


import com.example.javafxCat.entities.Cat;

import java.util.List;
public interface CatDao {
    void insert(Cat cat);

    void update(Cat cat);

    void deleteById(Integer id);
    public void exporterVersExcel(String path);
    public void importerDepuisExcel(String path) ;


    Cat findById(Integer id);

    List<Cat> findAll();

    void delete(Integer id);

    void update(Cat cat, Integer id);

    void exporterVersJson(String path);

    void importerDepuisJson(String path);
    public void exporterVersTxt(String path);
    public void importerDepuisTxt(String path) ;
}
