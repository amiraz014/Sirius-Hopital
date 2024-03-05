package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ezip.ing1.pds.business.dto.Medicament;
import edu.ezip.ing1.pds.business.dto.Medicaments;
import edu.ezip.ing1.pds.business.dto.Student;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_EMPLOYEES("SELECT t.id_employee, t.nom, t.prenom, t.adresse, t.email, t.birthdate, t.taille, t.startingdate, t.id_profession FROM \"public\".employee t"),
        SELECT_ALL_MEDICATIONS("SELECT t.code_barre, t.nom, t.categorie FROM \"public\".medicament t"),
        INSERT_EMPLOYEE("INSERT into \"public\".employee (\"nom\", \"prenom\", \"adresse\", \"email\", \"birthdate\", \"taille\", \"startingdate\") values (?, ?, ?, ?, ?, ?, ?)");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }

    public static XMartCityService inst = null;

    public static final XMartCityService getInstance() {
        if (inst == null) {
            inst = new XMartCityService();
        }
        return inst;
    }

    private XMartCityService() {

    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, NoSuchFieldException,
            IOException {
        try {
            if (request.getRequestOrder().equals("SELECT_ALL_EMPLOYEES")) {
                System.out.println("SELECT EMPLOYEES REQUEST");

                final PreparedStatement preparedStatement = connection.prepareStatement(Queries.SELECT_ALL_EMPLOYEES.query);
                ResultSet resultat = preparedStatement.executeQuery();
                Students listStudents = new Students();

                while (resultat.next()) {
                    final Student student = new Student().build(resultat);
                    listStudents.add(student);
                }
                ObjectMapper mapper = new ObjectMapper();
                System.out.println("yessssssssssssssssssssssssssssssssssss");

                System.out.println("====================================================");
                System.out.println(listStudents);
                System.out.println("====================================================");


                return new Response(request.getRequestId(), mapper.writeValueAsString(listStudents));

            } else if (request.getRequestOrder().equals("INSERT_EMPLOYEE")) {
                final PreparedStatement preparedStatement = connection.prepareStatement(Queries.INSERT_EMPLOYEE.query,Statement.RETURN_GENERATED_KEYS);
                ObjectMapper mapper = new ObjectMapper();
                Student student = mapper.readValue(request.getRequestBody(), Student.class);
                int line =student.build(preparedStatement).executeUpdate();
                Response response = new Response();

                // for(int i =0; i<=line; i++ ){

                //     //  ResultSet idRequest=preparedStatement.getGeneratedKeys();
                //     //     while(idRequest.next()){
                //     //         int id= idRequest.getInt(1);

                //         }
                // }
               
                response.setResponseBody("{\"employee_id\": " + line + "}");                
                response.setRequestId(request.getRequestId());
                return response;
            }else if (request.getRequestOrder().equals("SELECT_ALL_MEDICATIONS")){
               System.out.println("Select Medication request");
                final PreparedStatement preparedStatement = connection.prepareStatement(Queries.SELECT_ALL_MEDICATIONS.query);
                ResultSet resultat = preparedStatement.executeQuery();
                Medicaments listMedicaments = new Medicaments();
                System.out.println("00000000000000000000000000000000000000000000000000000");

                while (resultat.next()) {
                    final Medicament medicament = new Medicament().build(resultat);
                    listMedicaments.add(medicament);
                }
                ObjectMapper mapper = new ObjectMapper();
                System.out.println("yessssssssssssssssssssssssssssssssssss");

                System.out.println("====================================================");
                System.out.println(listMedicaments);
                System.out.println("====================================================");

                return new Response(request.getRequestId(), mapper.writeValueAsString(listMedicaments));
            }else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("oooooooooooooooooooooooooooooo");
            e.printStackTrace();
            System.out.println("oooooooooooooooooooooooooooooo");

        } 
        return null;

    }

}
