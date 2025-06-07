package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;

	@Autowired
	private UserService userService;

	public void saveEntry(JournalEntry journalEntry, String userName) {
		try {
			User user = userService.findByUsername(userName);
			if (user == null) {
				throw new RuntimeException("User not found");
			}
			journalEntry.setId(new ObjectId()); // Ensure ID is set
			journalEntryRepository.save(journalEntry);
			user.getJournalEntries().add(journalEntry);
			userService.saveEntry(user);
		} catch (Exception e) {
			log.error("An error occurred while saving the entry: ", e);
			throw new RuntimeException("An error occurred while saving the entry.", e);
		}
	}

	public void saveEntry(JournalEntry journalEntry) {
		journalEntryRepository.save(journalEntry);
	}

	public List<JournalEntry> getAll() {
		return journalEntryRepository.findAll();
	}

	public Optional<JournalEntry> findById(ObjectId id) {
		return journalEntryRepository.findById(id);
	}

	public boolean deleteById(ObjectId id, String userName) {
		boolean removed = false;
		try {
			User user = userService.findByUsername(userName);
			removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
			if (removed) {
				userService.saveEntry(user);
				journalEntryRepository.deleteById(id);
			}
		} catch (Exception e) {
			log.error("Error ", e);
			throw new RuntimeException("An error occurred while deleting the entry.", e);
		}
		return removed;
	}
}