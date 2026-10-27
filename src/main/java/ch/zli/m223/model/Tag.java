package ch.zli.m223.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany(mappedBy = "tags")
    private Set<Entry> entries = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}