import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParsingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Student {
    public int n;
    private String name;
    public Map<String, Integer> grades;
    double averageMarks;
    int rank;

    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public void addGrade(String subject, int score) {
        grades.put(subject, score);
    }

    public JsonObject toJson() {
        JsonObjectBuilder gradesBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            gradesBuilder.add(entry.getKey(), entry.getValue());
        }

        return Json.createObjectBuilder()
                .add("name", name)
                .add("grades", gradesBuilder.build())
                .build();
    }
    // Method to calculate average marks
    public double calculateAverageMarks() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double total = 0;
        for (int grade : grades.values()) {
            total += grade;
        }
        return total / grades.size();
    }

    // Getter for averageMarks
    public double getAverageMarks() {
        return averageMarks;
    }

    // Setter for averageMarks
    public void setAverageMarks(double averageMarks) {
        this.averageMarks = averageMarks;
    }

    // Getter for rank
    public int getRank() {
        return rank;
    }

    // Setter for rank
    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }




}
