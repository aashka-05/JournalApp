package net.engineeringdigest.journalApp.Entity;
import lombok.Data;
import lombok.NonNull;
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
    public ObjectId id;
    @Indexed(unique=true)
    @NonNull
    public String username;
    @NonNull
    String password;
    @DBRef
    public List<JournalContent> entries = new ArrayList<>();
    public List<String> roles = new ArrayList<>();



}
