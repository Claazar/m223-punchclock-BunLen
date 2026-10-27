package ch.zli.m223.model;

import java.util.LinkedHashSet;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "category")
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