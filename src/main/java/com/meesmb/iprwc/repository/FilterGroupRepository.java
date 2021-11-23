package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.FilterGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilterGroupRepository extends JpaRepository<FilterGroup, String> {
    Optional<FilterGroup> findByName(String name);
}
