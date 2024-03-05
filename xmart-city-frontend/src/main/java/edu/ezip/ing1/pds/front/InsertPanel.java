package edu.ezip.ing1.pds.front;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ezip.ing1.pds.business.dto.Student;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.client.InsertStudentsClientRequest;
import edu.ezip.ing1.pds.client.MainInsertClient;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

public class InsertPanel extends JPanel{
    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
private final static String studentsToBeInserted = "students-to-be-inserted.yaml";
private final static String networkConfigFile = "network.yaml";
private static final String threadName = "inserter-client";
private static final String requestOrder = "INSERT_EMPLOYEE";
private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    public InsertPanel() throws IOException, InterruptedException {


  final Students guys = ConfigLoader.loadConfig(Students.class, studentsToBeInserted);
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Students loaded : {}", guys.toString());
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        for(final Student guy : guys.getStudents()) {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(guy);
            logger.trace("Student with its JSON face : {}", jsonifiedGuy);
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(requestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertStudentsClientRequest clientRequest = new InsertStudentsClientRequest (
                                                                        networkConfig,
                                                                        birthdate++, request, guy, requestBytes);
           System.out.println("0000000000000000000000000000000000000000000000000000000000");
                                                                        clientRequests.push(clientRequest);
        }

      

       
    
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("idE");
        model.addColumn("nom");
        model.addColumn("prenom");
        model.addColumn("adresse");
        model.addColumn("email");
        model.addColumn("birthdate");
        model.addColumn("taille");
        model.addColumn("startingdate");
        model.addColumn("idP");


        while (!clientRequests.isEmpty()) {
            final  ClientRequest clientRequest = clientRequests.pop();
            clientRequest.join();
            final Student guy = (Student)clientRequest.getInfo();
            model.addRow(new Object[]{
                guy.getId_employee(),
                guy.getNom(),
                guy.getPrenom(),
                guy.getAdresse(),
                guy.getEmail(),
                guy.getBirthdate(),
                guy.getTaille(),
                guy.getStartingdate(),
                guy.getId_profession(),
            });
        }


       JTable table = new JTable(model);
       JScrollPane sp = new JScrollPane(table);
       add(sp);

 
    }
}
