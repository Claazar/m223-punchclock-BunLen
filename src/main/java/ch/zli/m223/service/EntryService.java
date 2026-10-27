package ch.zli.m223.service;

import java.util.List;
import java.util.LinkedHashSet;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import ch.zli.m223.repository.CategoryRepository;
import ch.zli.m223.repository.EntryRepository;
import ch.zli.m223.repository.TagRepository;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntryRepository entryRepository;

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private TagRepository tagRepository;

    @Transactional // needed for all "changing" operators
    public Entry createEntry(Entry entry, Long categoryId, List<Long> tagIds) {
        setRelationships(entry, categoryId, tagIds);
        entryRepository.persist(entry);
        return entry;
    }

    public List<Entry> findAll() {
        return entryRepository.listWithRelationships();
    }

    public Entry findById(Long id) {
        return entryRepository.findByIdWithRelationships(id);
    }

    @Transactional 
    public boolean deleteEntry(Long id) {
        return entryRepository.deleteById(id);
    }

    @Transactional 
    public Entry updateEntry(Long id, Entry entry, Long categoryId, List<Long> tagIds) {
        Entry chosenEntry = entryRepository.findByIdWithRelationships(id);
        if (chosenEntry == null) {
            throw new NotFoundException("Entry with ID " + id + " was not found");
        }
        chosenEntry.setCheckIn(entry.getCheckIn());
        chosenEntry.setCheckOut(entry.getCheckOut());
        setRelationships(chosenEntry, categoryId, tagIds);
        return chosenEntry;
    }

    private void setRelationships(Entry entry, Long categoryId, List<Long> tagIds) {
        if (categoryId == null) {
            entry.setCategory(null);
        } else {
            Category category = categoryRepository.findById(categoryId);
            if (category == null) {
                throw new NotFoundException("Category with ID " + categoryId + " was not found");
            }
            entry.setCategory(category);
        }

        LinkedHashSet<Tag> tags = new LinkedHashSet<>();
        if (tagIds != null) {
            for (Long tagId : tagIds) {
                Tag tag = tagId == null ? null : tagRepository.findById(tagId);
                if (tag == null) {
                    throw new NotFoundException("Tag with ID " + tagId + " was not found");
                }
                tags.add(tag);
            }
        }
        entry.setTags(tags);
    }
}