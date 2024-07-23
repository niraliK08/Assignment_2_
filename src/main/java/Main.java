import javax.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String inputFilePath = "students.json";
        String outputFilePath = "updated_students.json";

        //part A
        //creates two maps to store the grades of two ppl
        Map<String, Integer> gradesJohn = new HashMap<>();
        gradesJohn.put("Math", 85);
        gradesJohn.put("Physics", 90);
        gradesJohn.put("Chemistry", 78);

        Map<String, Integer> gradesJane = new HashMap<>();
        gradesJane.put("Biology", 88);
        gradesJane.put("Math", 92);
        gradesJane.put("English", 81);

        //creates JSON objects for the two students and combines them into an array
        JsonObject johnDoe = JsonMethods.createStudentJson("John Doe", gradesJohn);
        JsonObject janeSmith = JsonMethods.createStudentJson("Jane Smith", gradesJane);


        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder()
                .add(johnDoe)
                .add(janeSmith);

        JsonObject studentsJsonObject = Json.createObjectBuilder()
                .add("students", studentsArrayBuilder.build())
                .build();

        //Writes the JSON object to a file called students.json + error handling
        try (FileWriter fileWriter = new FileWriter("students.json");
             JsonWriter jsonWriter = Json.createWriter(fileWriter)) {

            jsonWriter.writeObject(studentsJsonObject);
            System.out.println("JSON file 'students.json' has been created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }


        JsonMethods.createStudentJson("Jane Smith", gradesJane);

        //part B

        JsonObject readJsonObject = null;
        try {
            readJsonObject = JsonMethods.readJsonFile(inputFilePath);
            System.out.println("JSON file has been read successfully.");
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            return; // Exit the method if file reading fails
        }



        JsonArray studentsArray = (JsonArray) studentsJsonObject.getJsonArray("students");

        List<Student> stu = JsonMethods.parseStudents((JsonArray) studentsArray);

        //calculate averages and rank
        JsonMethods.calculateAveragesAndRank();
        // Sort students by average marks (higher marks = higher rank)
        JsonMethods.sortStudentsByAverageMarks(JsonMethods.student);

        JsonMethods.buildUpdatedJson(stu);

        JsonMethods.printOutput();
    }
}
