package edu.ezip.ing1.pds.front;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.ezip.ing1.pds.business.dto.Student;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.client.MainSelectClient;

public class SelectPanel extends JPanel {
    public SelectPanel () {
        
        try{
       Students s = MainSelectClient.main();
        String [] column = {"id", "nom", "prenom", "adresse", "emploi", "email", "birthdate","taille","startingdate"};
        Object [][] data =new Object[s.getStudents().size()][9];
        int i =0;
        for(final Student student : s.getStudents()){
            data [i][0] = student.getId();
            data [i][1] = student.getNom();
            data [i][2] = student.getPrenom();
            data [i][3] = student.getAdresse();
            data [i][4] = student.getEmploi();
            data [i][5] = student.getEmail();
            data [i][6] = student.getBirthdate();
            data [i][7] = student.getTaille();
            data [i][8] = student.getStartingdate();
            i++;
        }
        DefaultTableModel dtm = new DefaultTableModel(data,column);
        JTable tab = new JTable(dtm);
        JScrollPane sp = new JScrollPane(tab);
        add(sp);} catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
