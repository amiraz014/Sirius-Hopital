package edu.ezip.ing1.pds.business.dto;


import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.*;


@JsonRootName(value = "student")
public class Student {
    private  Integer id_employee ;
    private  String nom ;
    private  String prenom ;
    private  String adresse ;
    private  Integer id_profession ;
    private  String email ;
    private  String birthdate ;
    private  Double taille ;
    private  String startingdate ; 

    public Student() {
    }
    public final Student build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "id_employee", "nom","prenom", "adresse",  "email", "birthdate", "taille", "startingdate", "id_profession");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement,  nom, prenom, adresse, email);
    }
    public Student(Integer id_employee, String nom, String prenom, String adresse, String email, String birthdate, Double taille, String startingdate, Integer id_profession ) {
        this.id_employee = id_employee;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.birthdate = birthdate;
        this.taille = taille;
        this.startingdate = startingdate;
        this.id_profession = id_profession;
    }

   public Integer getId_employee() {
       return id_employee;
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
    public Integer getId_profession() {
        return id_profession;
    }

   public void setId_employee(Integer id_employee) {
       this.id_employee = id_employee;
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
    public void setId_profession(Integer id_profession) {
        this.id_profession = id_profession;
    }
    // field = 

    private void setFieldsFromResulset(final ResultSet resultSet, final String ...fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for(final String fieldName :  fieldNames ) {
                if (fieldName.equals("id_employee") || fieldName.equals("id_profession")){
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
                "id_employee='" + id_employee + '\'' +
                ", firstname='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", taille='" + taille + '\'' +
                ", startingdate='" + startingdate + '\'' +
                ", id_profession='" + id_profession + '\'' +
                '}';
    }
}
