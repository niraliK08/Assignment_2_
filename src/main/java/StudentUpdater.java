import javax.json.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;


public class StudentUpdater {

    private static final String FILE_PATH = "students.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ensure file exists; create if it does not
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            createNewFile();
        }

        while (true) {
            System.out.println("Enter student details:");


            System.out.print("Name: ");
            String name = scanner.nextLine();

            Map<String, Integer> grades = new HashMap<>();
            System.out.println("Enter all Subjects and Grades (type 'done' to finish):");
            while (true) {
                System.out.print("Subject: ");
                String subject = scanner.nextLine();
                if (subject.equalsIgnoreCase("done")) {
                    break;
                }

                System.out.print("Grade: ");
                try {
                    int grade = Integer.parseInt(scanner.nextLine());
                    grades.put(subject, grade);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid grade entered. Please enter a valid number.");
                }
            }

            JsonObject newStudentJson = createStudentJson(name, grades);
            updateJsonFile(newStudentJson);

            System.out.print("Do you want to add another student? (yes/no): ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("yes")) {
                break;
            }
        }

        scanner.close();
        System.out.println("Student data updated successfully.");
    }

    private static void createNewFile() {
        JsonObject initialJson = Json.createObjectBuilder()
                .add("students", Json.createArrayBuilder().build())
                .build();
        try (FileWriter fileWriter = new FileWriter(FILE_PATH);
             JsonWriter jsonWriter = Json.createWriter(fileWriter)) {
            jsonWriter.writeObject(initialJson);
        } catch (IOException e) {
            System.err.println("Error creating new file: " + e.getMessage());
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

    private static void updateJsonFile(JsonObject newStudentJson) {
        JsonObject jsonObject;
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();

        try (FileReader fileReader = new FileReader(FILE_PATH);
             JsonReader jsonReader = Json.createReader(fileReader)) {

            jsonObject = jsonReader.readObject();
            JsonArray studentsArray = jsonObject.getJsonArray("students");

            // Add existing students to the new array
            for (JsonValue studentValue : studentsArray) {
                studentsArrayBuilder.add(studentValue);
            }

            // Add the new student
            studentsArrayBuilder.add(newStudentJson);

        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return;
        }

        JsonObject updatedJsonObject = Json.createObjectBuilder()
                .add("students", studentsArrayBuilder.build())
                .build();

        try (FileWriter fileWriter = new FileWriter(FILE_PATH);
             JsonWriter jsonWriter = Json.createWriter(fileWriter)) {

            jsonWriter.writeObject(updatedJsonObject);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
