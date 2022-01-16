package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.FilterTagDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.FilterGroup;
import com.meesmb.iprwc.model.FilterTag;
import com.meesmb.iprwc.request_objects.FilterGroupRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilterTagController {

    @Autowired
    FilterTagDao dao;

    @GetMapping("filter_tag")
    public HTTPResponse<List<FilterTag>> getTags(@RequestParam(name = "group", defaultValue = "") String group) {
        if (group.equals("")) {
            List<FilterTag> tags = dao.getAllTags();
            return HTTPResponse.<List<FilterTag>>returnSuccess(tags);
        }
        return dao.getFilterTagsByGroup(group);
    }


    @PostMapping("filter_tag")
    public HTTPResponse<FilterTag[]> postTag(@RequestBody FilterTag[] obj) {
        return dao.addFilterTags(obj);
    }

    @GetMapping("filter_group")
    public HTTPResponse<List<FilterGroup>> getGroups() {
        List<FilterGroup> groups = dao.getAllGroups();
        return HTTPResponse.<List<FilterGroup>>returnSuccess(groups);
    }

    @PostMapping("filter_group")
    public HTTPResponse<FilterGroup> postGroup(@RequestBody FilterGroupRequestObject obj) {
        return dao.addFilterGroup(obj.getName());
    }
}
