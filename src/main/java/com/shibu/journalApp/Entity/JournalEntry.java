package com.shibu.journalApp.Entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "journal_entries")
@Data //Lombok
@NoArgsConstructor
public class JournalEntry {
    @Id
    private String id;
    @NonNull
    private String heading;
    private String content;
    private LocalDateTime date;
}
