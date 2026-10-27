package ch.zli.m223.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import ch.zli.m223.repository.CategoryRepository;
import ch.zli.m223.repository.EntryRepository;
import ch.zli.m223.repository.TagRepository;

@ApplicationScoped
public class ExampleDataService {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    EntryRepository entryRepository;

    @Inject 
    TagRepository tagRepository;

    @ConfigProperty(name = "punchclock.example-data.enabled", defaultValue = "false")
    boolean enabled;

    @Transactional
    void start(@Observes StartupEvent event) {
        if (enabled) {
            createExampleData();
        }
    }

    @Transactional
    public void createExampleData() {
        if (categoryRepository.count() > 0 || tagRepository.count() > 0 || entryRepository.count() > 0) {
            return;
        }
        Category development = new Category();
        development.setTitle("Development");
        categoryRepository.persist(development);

        Category meetings = new Category();
        meetings.setTitle("Meetings");
        categoryRepository.persist(meetings);

        Category administration = new Category();
        administration.setTitle("Administration");
        categoryRepository.persist(administration);


        Tag backend = new Tag();
        backend.setTitle("backend");
        tagRepository.persist(backend);

        Tag planning = new Tag();
        planning.setTitle("planning");
        tagRepository.persist(planning);

        Tag urgent = new Tag();
        urgent.setTitle("urgent");
        tagRepository.persist(urgent);

        Tag review = new Tag();
        review.setTitle("review");
        tagRepository.persist(review);


        LocalDateTime checkIn1 = LocalDateTime.now().minusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
        Entry entry1 = new Entry();
        entry1.setCheckIn(checkIn1);
        entry1.setCheckOut(checkIn1.plusHours(8));
        entry1.setCategory(development);
        entry1.setTags(new LinkedHashSet<>(Set.of(backend, review)));
        entryRepository.persist(entry1);

        LocalDateTime checkIn2 = LocalDateTime.now().minusDays(1).withHour(13).withMinute(0).withSecond(0).withNano(0);
        Entry entry2 = new Entry();
        entry2.setCheckIn(checkIn2);
        entry2.setCheckOut(checkIn2.plusHours(1));
        entry2.setCategory(meetings);
        entry2.setTags(new LinkedHashSet<>(Set.of(planning)));
        entryRepository.persist(entry2);

        LocalDateTime checkIn3 = LocalDateTime.now().minusDays(2).withHour(9).withMinute(0).withSecond(0).withNano(0);
        Entry entry3 = new Entry();
        entry3.setCheckIn(checkIn3);
        entry3.setCheckOut(checkIn3.plusHours(6));
        entry3.setCategory(administration);
        entry3.setTags(new LinkedHashSet<>(Set.of(urgent)));
        entryRepository.persist(entry3);
    }
}