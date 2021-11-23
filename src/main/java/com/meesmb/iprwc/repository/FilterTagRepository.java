package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.FilterTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterTagRepository extends JpaRepository<FilterTag, String> {

}
