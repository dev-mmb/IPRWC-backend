package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.FilterGroup;
import com.meesmb.iprwc.model.FilterTag;
import com.meesmb.iprwc.model.Product;
import com.meesmb.iprwc.repository.FilterGroupRepository;
import com.meesmb.iprwc.repository.FilterTagRepository;
import com.meesmb.iprwc.repository.FilterTagRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FilterTagDao {

    @Autowired
    FilterTagRepository repository;
    @Autowired
    FilterGroupRepository filterGroupRepository;

    public List<FilterTag> getAllTags() {
        return repository.findAll();
    }
    public List<FilterGroup> getAllGroups() {
        return filterGroupRepository.findAll();
    }
    public HTTPResponse<FilterTag[]> addFilterTags(FilterTagRequestObject[] objects) {
        FilterTag[] returnValues = new FilterTag[objects.length];

        for (int i = 0; i < objects.length; i++) {
            HTTPResponse<FilterTag> response = addFilterTag(objects[i]);

            if (!response.isSuccess())
                return HTTPResponse.<FilterTag[]>returnFailure(response.getErrorMessage());

            returnValues[i] = response.getData();
        }

        return HTTPResponse.<FilterTag[]>returnSuccess(returnValues);
    }

    public HTTPResponse<FilterTag> addFilterTag(FilterTagRequestObject obj) {
        Optional<FilterGroup> group = filterGroupRepository.findByName(obj.getFilterGroup());
        if (group.isEmpty())
            return HTTPResponse.<FilterTag>returnFailure("group with id: " + obj.getFilterGroup() + " not found");
        FilterTag tag = new FilterTag(obj.getName(), group.get());
        repository.save(tag);
        return HTTPResponse.<FilterTag>returnSuccess(tag);
    }


    public HTTPResponse<FilterGroup> addFilterGroup(String name) {
        FilterGroup group = new FilterGroup(name);
        filterGroupRepository.save(group);
        return HTTPResponse.<FilterGroup>returnSuccess(group);
    }
}
