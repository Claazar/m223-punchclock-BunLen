package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import ch.zli.m223.model.Tag;
import ch.zli.m223.repository.TagRepository;

@ApplicationScoped
public class TagService {
    @Inject
    private TagRepository tagRepository;

    @Transactional // needed for all "changing" operators
    public Tag createTag(Tag tag) {
        tagRepository.persist(tag);
        return tag;
    }

    public List<Tag> findAll() {
        return tagRepository.listAll();
    }

    @Transactional 
    public boolean deleteTag(Long id) {
        return tagRepository.deleteById(id);
    }

    @Transactional 
    public Tag updateTag(Long id, Tag tag) {
        Tag chosenTag = tagRepository.findById(id);
        chosenTag.setTitle(tag.getTitle());
        return chosenTag;
    }
}