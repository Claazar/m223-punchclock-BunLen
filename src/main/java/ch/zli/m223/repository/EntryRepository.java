package ch.zli.m223.repository;

import ch.zli.m223.model.Entry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EntryRepository implements PanacheRepository<Entry> {
    public List<Entry> listWithRelationships() {
        return find("select distinct entry from Entry entry left join fetch entry.category left join fetch entry.tags").list();
    }

    public Entry findByIdWithRelationships(Long id) {
        return find("select distinct entry from Entry entry left join fetch entry.category left join fetch entry.tags where entry.id = ?1", id)
                .list()
                .stream()
                .findFirst()
                .orElse(null);
    }
}

/*
Repository commands:
listAll(), findById(id), persist(entry), deleteById(id)
*/