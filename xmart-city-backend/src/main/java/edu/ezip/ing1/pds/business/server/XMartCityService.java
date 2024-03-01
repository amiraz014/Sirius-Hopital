package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.group FROM \"public\".students t"),
        INSERT_STUDENT("INSERT into \"public\".students (\"name\", \"firstname\", \"group\") values (?, ?, ?)");

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
            if (request.getRequestOrder().equals("SELECT_ALL_STUDENTS")) {

                final PreparedStatement preparedStatement = connection
                        .prepareStatement(Queries.SELECT_ALL_STUDENTS.query);
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
            } else if (request.getRequestOrder().equals("INSERT_STUDENT")) {
                final PreparedStatement preparedStatement = connection.prepareStatement(Queries.INSERT_STUDENT.query);
                ObjectMapper mapper = new ObjectMapper();
                Student student = mapper.readValue(request.getRequestBody(), Student.class);
                int row = student.build(preparedStatement).executeUpdate();
                return new Response(request.getRequestId(), "{\"student_id\": " + row + "}");
            }

        } catch (Exception e) {
            System.out.println("oooooooooooooooooooooooooooooo");
            System.out.println(e.getMessage());
            System.out.println("oooooooooooooooooooooooooooooo");

        }
        System.out.println("eroooooooooooooooooooooo");
        return null;

    }

}
