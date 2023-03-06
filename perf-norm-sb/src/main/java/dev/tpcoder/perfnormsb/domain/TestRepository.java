package dev.tpcoder.perfnormsb.domain;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Message, Long> {
  @Query(value = "SELECT 1, PG_SLEEP(:second)")
  Object executeSleep(int second);
}
