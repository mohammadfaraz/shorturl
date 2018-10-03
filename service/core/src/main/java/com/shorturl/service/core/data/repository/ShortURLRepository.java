package com.shorturl.service.core.data.repository;

import com.shorturl.service.core.data.record.ShortURLRecord;
import org.springframework.data.repository.CrudRepository;

public interface ShortURLRepository extends CrudRepository<ShortURLRecord, Long> {

}
