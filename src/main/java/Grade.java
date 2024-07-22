import javax.json.Json;
import javax.json.JsonObject;

public class Grade {
    private String subject;
    private int score;

    public Grade(String subject, int score) {
        this.subject = subject;
        this.score = score;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add(subject, score)
                .build();
    }
}
