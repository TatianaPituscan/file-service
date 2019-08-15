package ro.creativewoolprojects.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.creativewoolprojects.entity.File;

@Repository
public interface FileRepository extends CrudRepository<File, String> {

}
