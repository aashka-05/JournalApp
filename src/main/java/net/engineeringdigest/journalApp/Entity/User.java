package net.engineeringdigest.journalApp.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;
@Document("Users")
@Data
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique=true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    @DBRef
    private List<JournalContent> entries = new ArrayList<>();
    private List<String> roles = new ArrayList<>();


}
