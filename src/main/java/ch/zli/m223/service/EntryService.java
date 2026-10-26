package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import ch.zli.m223.model.Entry;
import ch.zli.m223.repository.EntryRepository;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntryRepository entryRepository;

    @Transactional
    public Entry createEntry(Entry entry) {
        entryRepository.persist(entry);
        return entry;
    }

    public List<Entry> findAll() {
        return entryRepository.listAll();
    }

    @Transactional 
    public boolean deleteEntry(Long id) {
        return entryRepository.deleteById(id);
    }

    @Transactional 
    public Entry updateEntry(Long id, Entry entry) {
        Entry chosenEntry = entryRepository.findById(id);
        chosenEntry.setCheckIn(entry.getCheckIn());
        chosenEntry.setCheckOut(entry.getCheckOut());
        return chosenEntry;
    }
}