package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.FilterGroup;
import com.meesmb.iprwc.model.FilterTag;
import com.meesmb.iprwc.repository.FilterGroupRepository;
import com.meesmb.iprwc.repository.FilterTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public HTTPResponse<FilterTag[]> addFilterTags(FilterTag[] tags) {
        for (FilterTag tag : tags) {
            addFilterTag(tag);
        }
        return HTTPResponse.<FilterTag[]>returnSuccess(tags);
    }

    public HTTPResponse<FilterTag> addFilterTag(FilterTag tag) {
        Optional<FilterGroup> group = filterGroupRepository.findByName(tag.getFilterGroup().getName());
        if (group.isEmpty()) {
            addFilterGroup(tag.getFilterGroup().getName());
        }
        filterTagRepository.save(tag);
        return HTTPResponse.<FilterTag>returnSuccess(tag);
    }

    public HTTPResponse<List<FilterTag>> getFilterTagsByGroup(String group) {
        List<FilterTag> tags = filterTagRepository.findByFilterGroup_name(group);
        return HTTPResponse.<List<FilterTag>>returnSuccess(tags);
    }

    public HTTPResponse<FilterGroup> addFilterGroup(String name) {
        FilterGroup group = new FilterGroup(name);
        filterGroupRepository.save(group);
        return HTTPResponse.<FilterGroup>returnSuccess(group);
    }
}
