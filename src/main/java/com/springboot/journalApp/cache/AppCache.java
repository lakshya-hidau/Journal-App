package com.springboot.journalApp.cache;

import com.springboot.journalApp.entity.ConfigJournalAppEntity;
import com.springboot.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

	@Autowired
	private ConfigJournalAppRepository configJournalAppRepository;

	public Map<String, String> appCache;

	@PostConstruct
	public void init(){
		appCache = new HashMap<>();
		List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
		for (ConfigJournalAppEntity configJournalAppEntity : all) {
			appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
		}
	}

}
