import javax.json.*;
import java.util.ArrayList;
import javax.json.stream.JsonParsingException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class JsonMethods {

    private static Object students;
    static List<Student> student = new ArrayList<>();

    //reads Json file
    public static JsonObject readJsonFile(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath);
             JsonReader jsonReader = Json.createReader(inputStream)) {
            return jsonReader.readObject();
        }
    }

    //writes a JsonObject to a Json file
    public static void writeJsonFile(String filePath, JsonObject jsonObject) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath);
             JsonWriter jsonWriter = Json.createWriter(fileWriter)) {
            jsonWriter.writeObject(jsonObject);
        }
    }

    //converts a JsonArray into a list of Student Objects
    public static List<Student> parseStudents(JsonArray studentsArray) {
        List<Student> students = new ArrayList<>();
        for (JsonValue value : studentsArray) {
            JsonObject studentObject = (JsonObject) value;
            String name = studentObject.getString("name");
            JsonObject gradesObject = studentObject.getJsonObject("grades");

            Map<String, Integer> grades = new HashMap<>();
            for (String key : gradesObject.keySet()) {
                grades.put(key, gradesObject.getInt(key));
            }

            students.add(new Student(name, grades));
        }
        return students;
    }

    //creates a JsonObject from a map of grades
    public static JsonObject createGradesObject(Map<String, Integer> grades) {
        JsonObjectBuilder gradesBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            gradesBuilder.add(entry.getKey(), entry.getValue());
        }
        return gradesBuilder.build();
    }

    //sorts students based on marks
    public static double sortStudentsByAverageMarks(List<Student> students) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students.get(j).getAverageMarks() < students.get(j + 1).getAverageMarks()) {
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
            System.out.println(students);
        }
        return 0.0;
    }

    //creates a JsonObject to represent a student with name and grades
    public static JsonObject createStudentJson(String name, Map<String, Integer> grades) {
        JsonObjectBuilder gradesBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            gradesBuilder.add(entry.getKey(), entry.getValue());
        }

        return Json.createObjectBuilder()
                .add("name", name)
                .add("grades", gradesBuilder.build())
                .build();
    }

    //calculate average marks and rank
    public static void calculateAveragesAndRank() {
        // Calculate average marks for each student
        for (Student student : student) {
            student.setAverageMarks(student.calculateAverageMarks());
        }
        for (int i = 0; i < student.size(); i++) {
            student.get(i).setRank(i + 1);
        }
    }

    //creates a JsonObject that represents a list of students with their names, grades, average marks, and ranks
    public static JsonObject buildUpdatedJson(List<Student> students) {
        JsonArrayBuilder updatedStudentsArrayBuilder = Json.createArrayBuilder();
        for (Student student : students) {
            JsonObjectBuilder studentBuilder = Json.createObjectBuilder()
                    .add("name", student.getName()) // Access name correctly
                    .add("grades", createGradesObject(student.getGrades())) // Access grades correctly
                    .add("average_marks", student.getAverageMarks()) // Access averageMarks correctly
                    .add("rank", student.getRank()); // Access rank correctly
            updatedStudentsArrayBuilder.add(studentBuilder); // Add the student JSON object to the array builder
        }
        return Json.createObjectBuilder()
                .add("students", updatedStudentsArrayBuilder.build()) // Build the final JSON object
                .build();
    }

    // Prints out each student's name, average marks, and rank to the console
    public static void printOutput() {
        System.out.println("Students with average marks and ranks: ");
        for (Student student : student) {
            System.out.println("Name: " + student.getName() + ", Average Marks: " + student.getAverageMarks() + ", Rank: " + student.getRank());
        }
    }


}
