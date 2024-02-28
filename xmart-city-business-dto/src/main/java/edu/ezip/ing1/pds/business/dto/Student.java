package edu.ezip.ing1.pds.business.dto;


import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.*;


@JsonRootName(value = "student")
public class Student {
    private  Integer id ;
    private  String nom ;
    private  String prenom ;
    private  String adresse ;
    private  String emploi ;
    private  String email ;
    private  String birthdate ;
    private  Double taille ;
    private  String startingdate ; 

    public Student() {
    }
    public final Student build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "id", "nom","prenom", "adresse", "emploi", "email", "birthdate", "taille", "startingdate");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement,  nom, prenom, adresse, emploi, email);
    }
    public Student(Integer id, String nom, String prenom, String adresse, String email, String birthdate, Double taille, String startingdate ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.birthdate = birthdate;
        this.taille = taille;
        this.startingdate = startingdate;
    }

    public Integer getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getAdresse() {
        return adresse;
    }
    public String getEmploi() {
        return emploi;
    }
    public String getEmail() {
        return email;
    }
    public String getBirthdate() {
        return birthdate;
    }
    public Double getTaille() {
        return taille;
    }
    public String getStartingdate() {
        return startingdate;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public void setEmploi(String emploi) {
        this.emploi = emploi;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    public void setTaille(Double taille) {
        this.taille = taille;
    }
    public void setStartingdate(String startingdate) {
        this.startingdate = startingdate;
    }
    // field = 

    private void setFieldsFromResulset(final ResultSet resultSet, final String ...fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for(final String fieldName :  fieldNames ) {
                if (fieldName.equals("id")){
                    final Field field = this.getClass().getDeclaredField(fieldName);
                    field.set(this, resultSet.getObject(fieldName, Integer.class));
                } else if (fieldName.equals("taille")){
                    final Field field = this.getClass().getDeclaredField(fieldName);
                    field.set(this, resultSet.getObject(fieldName, Double.class));
                } else if (fieldName.equals("birthdate") || fieldName.equals("startingdate")){
                    final Field field = this.getClass().getDeclaredField(fieldName);
                    Timestamp tt = resultSet.getTimestamp(fieldName);
                    String ttString = tt.toString();
                    field.set(this, ttString);
                } else {
                    final Field field = this.getClass().getDeclaredField(fieldName);
                    field.set(this, resultSet.getObject(fieldName, String.class));
                }

            //final Field field = this.getClass().getDeclaredField(fieldName);
            //field.set(this, resultSet.getObject(fieldName));
        }
    }
    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final String ...fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for(final String fieldName : fieldNames ) {
            preparedStatement.setString(++ix, fieldName);
        }
        //àààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààà
        //àààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààà
        Timestamp tt = Timestamp.valueOf(birthdate);
        preparedStatement.setTimestamp(++ix, tt);
        //àààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààà
        preparedStatement.setDouble(++ix, taille);
        //àààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààààà
        Timestamp tt1 = Timestamp.valueOf(startingdate);
        preparedStatement.setTimestamp(++ix, tt1);
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstname='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", emploi='" + emploi + '\'' +
                ", email='" + email + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", taille='" + taille + '\'' +
                ", startingdate='" + startingdate + '\'' +
                '}';
    }
}
