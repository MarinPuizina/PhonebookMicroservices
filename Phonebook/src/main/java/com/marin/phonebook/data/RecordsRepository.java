package com.marin.phonebook.data;

import org.springframework.data.repository.CrudRepository;

public interface RecordsRepository extends CrudRepository<RecordEntity, Long> {
    
}
