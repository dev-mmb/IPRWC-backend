package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.FilterTagDao;
import com.meesmb.iprwc.model.FilterGroup;
import com.meesmb.iprwc.model.FilterTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilterTagController {

    @Autowired
    FilterTagDao dao;

    @GetMapping("filter_tag")
    public ResponseEntity<List<FilterTag>> getTags(@RequestParam(name = "group", defaultValue = "") String group) {
        if (group.equals("")) {
            List<FilterTag> tags = dao.getAllTags();
            return new ResponseEntity<List<FilterTag>>(tags, HttpStatus.OK);
        }
        return dao.getFilterTagsByGroup(group);
    }

    @PostMapping("filter_tag")
    public ResponseEntity<FilterTag[]> postTag(@RequestBody FilterTag[] obj) {
        return dao.addFilterTags(obj);
    }

    @GetMapping("filter_group")
    public ResponseEntity<List<FilterGroup>> getGroups() {
        List<FilterGroup> groups = dao.getAllGroups();
        return new ResponseEntity<List<FilterGroup>>(groups, HttpStatus.OK);
    }

    @PostMapping("filter_group")
    public ResponseEntity<FilterGroup> postGroup(@RequestBody FilterGroup obj) {
        return dao.addFilterGroup(obj.getName());
    }
}
