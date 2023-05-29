package com.example.javafxCat.entities;

import java.util.Date;
import java.util.Objects;

public class Cat {
    private int id;
    private String nom;
    private String race;
    private String sexe;

    private int anneeNaissance;
    private double poids;


    public Cat(int id, String nom, String race, String sexe, int anneeNaissance, double poids) {
        this.id=id;
        this.nom = nom;
        this.race = race;
        this.sexe = sexe;
        this.anneeNaissance = anneeNaissance;
        this.poids = poids;


    }

    public Cat() {

    }

    public Cat(String nom, String race, String sexe, int anneeN, double poids) {
        this.nom = nom;
        this.race = race;
        this.sexe = sexe;
        this.anneeNaissance = anneeNaissance;
        this.poids = poids;
    }


    // Getters and Setters
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getAnneeNaissance() {
        return anneeNaissance;
    }

    public void setAnneeNaissance(int anneeNaissance) {
        this.anneeNaissance = anneeNaissance;
    }
    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cat)) return false;
        Cat cat = (Cat) o;
        return getAnneeNaissance() == cat.getAnneeNaissance() && Double.compare(cat.getPoids(), getPoids()) == 0 && getSexe() == cat.getSexe() && Objects.equals(getNom(), cat.getNom()) && Objects.equals(getRace(), cat.getRace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNom(), getRace(),getSexe(), getAnneeNaissance(), getPoids() );
    }

    @Override
    public String toString() {
        return "Cat{" +
                ", nom='" + nom + '\'' +
                ", race='" + race + '\'' +
                ", sexe=" + sexe +
                ", anneeNaissance=" + anneeNaissance +
                ", poids=" + poids +

                '}';
    }


}
