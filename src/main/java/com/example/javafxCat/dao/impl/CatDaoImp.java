package com.example.javafxCat.dao.impl;


import com.example.javafxCat.dao.CatDao;
import com.example.javafxCat.entities.Cat;
    import com.google.gson.Gson;
    import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CatDaoImp implements CatDao {

    private Connection conn=DB.getConnection();

    /*@Override
    public void insert(Cat cat) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO chat (Nom) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, cat.getNom());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    cat.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion ");;
        } finally {
            DB.closeStatement(ps);
        }
    }
*/
    @Override
    public void update(Cat cat) {

    }

   /* @Override
    public void update(Cat cat) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE chat SET Nom = ? WHERE Id = ?");

            ps.setString(1, cat.getNom());
            ps.setInt(2, cat.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour");;
        } finally {
           DB.closeStatement(ps);
        }

    }*/

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM chat WHERE Id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression ");;
        } finally {
           DB.closeStatement(ps);
        }

    }

    @Override
    public void exporterVersExcel(String path) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Cats");

            List<Cat> cats = findAll();

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nom");
            headerRow.createCell(1).setCellValue("Race");
            headerRow.createCell(2).setCellValue("Sexe");
            headerRow.createCell(3).setCellValue("AnneeN");
            headerRow.createCell(4).setCellValue("Poids");
            int rowNum = 1;
            for (Cat cat : cats) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(cat.getNom());
                row.createCell(1).setCellValue(cat.getRace());
                row.createCell(2).setCellValue(cat.getSexe());
                row.createCell(3).setCellValue(cat.getAnneeNaissance());
                row.createCell(4).setCellValue(cat.getPoids());
            }

            try (FileOutputStream outputStream = new FileOutputStream(path)) {
                workbook.write(outputStream);
            }

            System.out.println("Exportation vers le fichier Excel réussie !");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'exportation vers le fichier Excel : " + e.getMessage());
        }
    }
    @Override
    /*
    public void importerDepuisExcel(String path) {
        try (Workbook workbook = new XSSFWorkbook(path)) {
            Sheet sheet = workbook.getSheetAt(0);

            int numRow = 1;
            Row row = sheet.getRow(numRow++);
            while (row != null) {
                Cat cat = new Cat();

                cat.setNom(row.getCell(0).getStringCellValue());
                cat.setRace(row.getCell(1).getStringCellValue());
                cat.setSexe(row.getCell(2).getStringCellValue());
                cat.setAnneeNaissance((int) row.getCell(3).getNumericCellValue());
                cat.setPoids(row.getCell(4).getNumericCellValue());

                insert(cat);

                row = sheet.getRow(numRow++);
            }

            System.out.println("Importation depuis le fichier Excel réussie !");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'importation depuis le fichier Excel : " + e.getMessage());
        }
    }*/



    public void importerDepuisExcel(String path) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(path))) {
            Sheet sheet = workbook.getSheetAt(0);

            // Start from row 1 as row 0 contains the header
            for (int i = 1; i <= ((Sheet) sheet).getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                String nom = row.getCell(0).getStringCellValue();
                String race = row.getCell(1).getStringCellValue();
                String sexe = row.getCell(2).getStringCellValue();
                int anneeN = (int) row.getCell(3).getNumericCellValue();
                double poids = row.getCell(4).getNumericCellValue();


                // Parse the date using SimpleDateFormat
                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                //Date dateCreation = dateFormat.parse(dateStr);

                // Create the  object
                Cat cat = new Cat( nom,  race,  sexe,  anneeN,  poids);

                // Save or process the equipe object as needed
                insert(cat);
            }
        } catch (IOException e) {
System.out.println("errroooor");        }
    }
    @Override
    public Cat findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM chat WHERE Id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Cat cat = new Cat(9, "Su3ad", "European", "femelle", 2021, 25);

                cat.setId(rs.getInt("Id"));
                cat.setNom(rs.getString("Nom"));

                return cat;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête ");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

    }

    @Override
    public List<Cat> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM chat");
            rs = ps.executeQuery();

            List<Cat> listCat = new ArrayList<>();

            while (rs.next()) {
                Cat cat = new Cat(7, "Su3ad", "European", "femelle", 2021 , 25);

                cat.setId(rs.getInt("id"));
                cat.setNom(rs.getString("Nom"));
                cat.setRace(rs.getString("Race"));
                cat.setSexe(rs.getString("Sexe"));

                listCat.add(cat);
            }

            return listCat;
        } catch (SQLException e) {
            System.err.println("problème de requête ");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

    }

    @Override
    public void delete(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM chat WHERE Id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression");;
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void update(Cat cat ,Integer id ) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE chat SET nom = ? , race = ?,sexe = ? , anneeN = ? , poids = ?  WHERE id = ?");

            ps.setString(1, cat.getNom());
            ps.setString(2, cat.getRace());
            ps.setString(3, cat.getSexe());
            ps.setInt(4, cat.getAnneeNaissance());
            ps.setDouble(5, cat.getPoids());
            ps.setInt(6, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'un chat");;
        } finally {
            DB.closeStatement(ps);
        }

    }
    @Override
    public void exporterVersJson(String path) {
      /*  String url = "jdbc:mysql://localhost:3306/cat";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM chat")) {

            List<Cat> cats = new ArrayList<>();
            while (resultSet.next()) {
                Cat cat = new Cat();
                cat.setId(resultSet.getInt("id"));
                cat.setNom(resultSet.getString("nom"));
                cat.setRace(resultSet.getString("race"));
                cat.setSexe(resultSet.getString("sexe"));
                cat.setAnneeNaissance(resultSet.getInt("anneeN"));
                cat.setPoids(resultSet.getDouble("poids"));
                cats.add(cat);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter(path)) {
                gson.toJson(cats, writer);
                System.out.println("Exportation vers le fichier JSON réussie !");
            } catch (IOException e) {
                System.out.println("Erreur lors de l'exportation vers le fichier JSON : " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }*/
    }
    @Override
    public void importerDepuisJson(String path) {
   /*     String url = "jdbc:mysql://localhost:3306/cat";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            Gson gson = new GsonBuilder().create();
            try (FileReader reader = new FileReader(path)) {
                Cat[] cats = gson.fromJson(reader, Cat[].class);

                for (Cat cat : cats) {
                    String insertQuery = "INSERT INTO chat (Id, nom, race,sexe, anneeN, poids) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, cat.getId());
                    preparedStatement.setString(2, cat.getNom());
                    preparedStatement.setString(3, cat.getRace());
                    preparedStatement.setString(4, cat.getSexe());
                    preparedStatement.setInt(5, cat.getAnneeNaissance());
                    preparedStatement.setDouble(6, cat.getPoids());

                    preparedStatement.executeUpdate();
                }

                System.out.println("Importation depuis le fichier JSON réussie !");
            } catch (IOException e) {
                System.out.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }*/
    }
    @Override
    public void exporterVersTxt(String path) {

        String url = "jdbc:mysql://localhost:3306/cat";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM chat")) {

            List<String> lines = new ArrayList<>();

            while (resultSet.next()) {
                StringBuilder line = new StringBuilder();
                line.append(resultSet.getString("id")).append("\t");
                line.append(resultSet.getString("nom")).append("\t");
                line.append(resultSet.getString("race")).append("\t");
                line.append(resultSet.getString("sexe")).append("\t");
                line.append(resultSet.getString("anneeN")).append("\t");
                line.append(resultSet.getString("poids")).append("\t");
                lines.add(line.toString());
            }

            try (FileWriter writer = new FileWriter(path)) {
                for (String line : lines) {
                    writer.write(line);
                    writer.write(System.lineSeparator());
                }
                System.out.println("Exportation vers le fichier texte réussie !");
            } catch (IOException e) {
                System.out.println("Erreur lors de l'exportation vers le fichier texte : " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }

    }
    @Override
    public void importerDepuisTxt(String path) {
        String url = "jdbc:mysql://localhost:3306/cat";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            List<String> lines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                System.out.println("Erreur lors de la lecture du fichier texte : " + e.getMessage());
                return;
            }

            String insertQuery = "INSERT INTO chat (Id, nom, race, sexe, anneeN, poids) " +
                    "VALUES (?, ?, ?, ?, ?, ? )";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            for (String line : lines) {
                String[] values = line.split("\t");
                preparedStatement.setInt(1, Integer.parseInt(values[0]));
                preparedStatement.setString(2, values[1]);
                preparedStatement.setString(3, values[2]);
                preparedStatement.setString(4, values[3]);
                preparedStatement.setInt(5, Integer.parseInt(values[4]));
                preparedStatement.setDouble(6, Double.parseDouble(values[5]));

                preparedStatement.executeUpdate();
            }

            System.out.println("Importation depuis le fichier texte réussie !");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }
    @Override
    public void insert(Cat cat) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO chat (nom, race, sexe,anneeN, poids) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, cat.getNom());
            ps.setString(2, cat.getRace());
            ps.setString(3, cat.getSexe());
            ps.setInt(4, cat.getAnneeNaissance());
            ps.setDouble(5, cat.getPoids());



            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    cat.setNom(String.valueOf(id));
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //System.err.println("problème d'insertion ");;
        } finally {
            DB.closeStatement(ps);
        }
    }



}

