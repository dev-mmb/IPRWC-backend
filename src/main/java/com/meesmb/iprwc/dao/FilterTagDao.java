package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.model.FilterGroup;
import com.meesmb.iprwc.model.FilterTag;
import com.meesmb.iprwc.repository.FilterGroupRepository;
import com.meesmb.iprwc.repository.FilterTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FilterTagDao {

    @Autowired
    FilterTagRepository filterTagRepository;
    @Autowired
    FilterGroupRepository filterGroupRepository;

    public List<FilterTag> getAllTags() {
        return filterTagRepository.findAll();
    }

    public List<FilterGroup> getAllGroups() {
        return filterGroupRepository.findAll();
    }

    public ResponseEntity<FilterTag[]> addFilterTags(FilterTag[] tags) {
        for (FilterTag tag : tags) {
            addFilterTag(tag);
        }
        return new ResponseEntity<FilterTag[]>(tags, HttpStatus.OK);
    }

    public void addFilterTag(FilterTag tag) {
        Optional<FilterGroup> group = filterGroupRepository.findByName(tag.getFilterGroup().getName());
        if (group.isEmpty()) {
            addFilterGroup(tag.getFilterGroup().getName());
        }
        filterTagRepository.save(tag);
    }

    public ResponseEntity<List<FilterTag>> getFilterTagsByGroup(String group) {
        List<FilterTag> tags = filterTagRepository.findByFilterGroup_name(group);
        return new ResponseEntity<List<FilterTag>>(tags, HttpStatus.OK);
    }

    public ResponseEntity<FilterGroup> addFilterGroup(String name) {
        FilterGroup group = new FilterGroup(name);
        filterGroupRepository.save(group);
        return new ResponseEntity<FilterGroup>(group, HttpStatus.OK);
    }
}
