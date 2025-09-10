package net.engineeringdigest.journalApp.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document("journalEntries")
@Data
public class JournalContent {
    @Id
    ObjectId id;
    LocalDateTime date;
    String title;
    String content;

}
