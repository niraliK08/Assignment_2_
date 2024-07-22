import javax.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        //part A

        Map<String, Integer> gradesJohn = new HashMap<>();
        gradesJohn.put("Math", 85);
        gradesJohn.put("Physics", 90);
        gradesJohn.put("Chemistry", 78);

        Map<String, Integer> gradesJane = new HashMap<>();
        gradesJane.put("Biology", 88);
        gradesJane.put("Math", 92);
        gradesJane.put("English", 81);

        JsonObject johnDoe = createStudentJson("John Doe", gradesJohn);
        JsonObject janeSmith = createStudentJson("Jane Smith", gradesJane);

        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder()
                .add(johnDoe)
                .add(janeSmith);

        JsonObject studentsJsonObject = Json.createObjectBuilder()
                .add("students", studentsArrayBuilder.build())
                .build();

        try (FileWriter fileWriter = new FileWriter("students.json");
             JsonWriter jsonWriter = Json.createWriter(fileWriter)) {

            jsonWriter.writeObject(studentsJsonObject);
            System.out.println("JSON file 'students.json' has been created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static JsonObject createStudentJson(String name, Map<String, Integer> grades) {
        JsonObjectBuilder gradesBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            gradesBuilder.add(entry.getKey(), entry.getValue());
        }

        return Json.createObjectBuilder()
                .add("name", name)
                .add("grades", gradesBuilder.build())
                .build();
    }

    //part B

    String inputFilePath = "students.json";
    String outputFilePath = "updated_students.json";
    static List<Student> students = new ArrayList<>();


    JsonObject studentsJsonObject;

    {
        try {
            studentsJsonObject = JsonMethods.readJsonFile(inputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    JsonArray studentsArray = (JsonArray) studentsJsonObject.getJsonArray("students");

    List<Student> stu = JsonMethods.parseStudents((javax.json.JsonArray) studentsArray);

    public void calculateAveragesAndRank() {
        // Calculate average marks for each student
        for (Student student : students) {
            student.setAverageMarks(student.calculateAverageMarks());
        }

        // Sort students by average marks (higher marks = higher rank)
        JsonMethods.sortStudentsByAverageMarks(students);

        // Assign ranks based on sorted order
        for (int i = 0; i < students.size(); i++) {
            students.get(i).setRank(i + 1);
        }
    }

    JsonObject updatedStudentsJsonObject = buildUpdatedJson(students);

    private static JsonObject buildUpdatedJson(List<Student> students) {
        JsonArrayBuilder updatedStudentsArrayBuilder = Json.createArrayBuilder();
        for (Student student : students) {
            JsonObjectBuilder studentBuilder = Json.createObjectBuilder()
                    .add("name", student.n)
                    .add("grades", JsonMethods.createGradesObject(student.grades))
                    .add("average_marks", student.averageMarks)
                    .add("rank", student.rank);
            updatedStudentsArrayBuilder.add(studentBuilder);
        }
        return Json.createObjectBuilder()
                .add("students", updatedStudentsArrayBuilder.build())
                .build();
    }

    public static void printOutput() {
        System.out.println("Students with average marks and ranks: ");
        for (Student student : students) {
            System.out.println("Name: " + student.getName() + ", Average Marks: " + student.getAverageMarks() + ", Rank: " + student.getRank());
        }

    }
}