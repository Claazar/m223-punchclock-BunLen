package ch.zli.m223.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import DTO.EntryDto;
import DTO.EntryDetailDto;
import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;

@ApplicationScoped
public class EntryMapper {
    @Inject
    CategoryMapper categoryMapper;

    @Inject
    TagMapper tagMapper;

    public Entry toEntity(EntryDto dto) {
        Entry entry = new Entry();
        entry.setCheckIn(dto.getCheckIn());
        entry.setCheckOut(dto.getCheckOut());
        return entry;
    }


    public EntryDto toDto(Entry entry) {
        EntryDto dto = new EntryDto();
        dto.setId(entry.getId());
        dto.setCheckIn(entry.getCheckIn());
        dto.setCheckOut(entry.getCheckOut());
        dto.setCategoryId(entry.getCategory() == null ? null : entry.getCategory().getId());
        dto.setTagIds(entry.getTags().stream()
                .map(Tag::getId)
                .toList());
        return dto;
    }

    public EntryDetailDto toDetailDto(Entry entry) {
        EntryDetailDto dto = new EntryDetailDto();
        dto.setId(entry.getId());
        dto.setCheckIn(entry.getCheckIn());
        dto.setCheckOut(entry.getCheckOut());

        Category category = entry.getCategory();
        dto.setCategory(category == null ? null : categoryMapper.toDto(category));
        dto.setTags(entry.getTags().stream()
                .map(tagMapper::toDto)
                .toList());
        return dto;
    }

}