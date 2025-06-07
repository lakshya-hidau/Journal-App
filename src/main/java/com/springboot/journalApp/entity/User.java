package com.springboot.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private ObjectId id;

	@NonNull
	private String username;
	private String email;
	private boolean SentimenetAnalysis = true;

	@NonNull
	private String password;

	@DBRef
	private List<JournalEntry> journalEntries = new ArrayList<>();

	private List<String> roles = new ArrayList<>();
}
