import javax.json.*;
import java.util.ArrayList;
import javax.json.stream.JsonParsingException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class JsonMethods {


    public static JsonObject readJsonFile(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath);
             JsonReader jsonReader = Json.createReader(inputStream)) {
            return jsonReader.readObject();
        }
    }

    public static void writeJsonFile(String filePath, JsonObject jsonObject) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath);
             JsonWriter jsonWriter = Json.createWriter(fileWriter)) {
            jsonWriter.writeObject(jsonObject);
        }
    }

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

    public static JsonObject createGradesObject(Map<String, Integer> grades) {
        JsonObjectBuilder gradesBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            gradesBuilder.add(entry.getKey(), entry.getValue());
        }
        return gradesBuilder.build();
    }


    public static void sortStudentsByAverageMarks(List<Student> students) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students.get(j).getAverageMarks() < students.get(j + 1).getAverageMarks()) {
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
    }

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



}
