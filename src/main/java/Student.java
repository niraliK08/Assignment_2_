import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Map;
import java.util.HashMap;

public class Student {
    public double averageMarks;
    private String name;
    public Map<String, Integer> grades;
    private int rank;

    // Constructor with initialization of grades map
    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = new HashMap<>(grades); // Initialize to avoid null reference
        this.averageMarks = calculateAverageMarks(); // Set initial average
    }

    // Method to add or update a grade
    public void addGrade(String subject, int score) {
        grades.put(subject, score);
        this.averageMarks = calculateAverageMarks(); // Recalculate average when grades are updated
    }

    // Method to convert the Student object to a JSON object
    public JsonObject toJson() {
        JsonObjectBuilder gradesBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            gradesBuilder.add(entry.getKey(), entry.getValue());
        }

        return Json.createObjectBuilder()
                .add("name", name)
                .add("grades", gradesBuilder.build())
                .add("averageMarks", averageMarks)
                .add("rank", rank)
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
        averageMarks = total / grades.size();
        return total / grades.size();
    }

    // Getter for averageMarks
    public double getAverageMarks() {
        return averageMarks;
    }

    public Map<String, Integer> getGrades()
    {
        return grades;
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

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }
}
