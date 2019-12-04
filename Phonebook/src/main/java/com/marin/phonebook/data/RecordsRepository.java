package com.marin.phonebook.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecordsRepository extends CrudRepository<RecordEntity, Long> {

    RecordEntity findByPhoneNumber(String phoneNumber);
    List<RecordEntity> findByPersonName(String personName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE records r SET r.person_name = :personName, r.record_type = :recordType WHERE r.phone_number = :phoneNumber", nativeQuery = true)
    void updateRecord(@Param("personName") String personName, @Param("recordType") String recordType, @Param("phoneNumber") String phoneNumber);

}
