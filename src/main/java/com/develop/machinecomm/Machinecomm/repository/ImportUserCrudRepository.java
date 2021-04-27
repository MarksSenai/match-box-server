package com.develop.machinecomm.Machinecomm.repository;

import com.develop.machinecomm.Machinecomm.model.UserImporter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ImportUserCrudRepository  extends CrudRepository<UserImporter, Long> {

    @Query(value = "SELECT TOP 1 * FROM USER_IMPORTER ORDER BY ID DESC" , nativeQuery = true)
    UserImporter getUsersFileData();
}
