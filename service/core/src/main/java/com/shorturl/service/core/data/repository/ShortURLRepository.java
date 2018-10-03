package com.shorturl.service.core.data.repository;

import com.shorturl.service.core.data.record.ShortURLRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ShortURLRepository extends CrudRepository<ShortURLRecord, Long> {

  ShortURLRecord findByCustomIdAndActive(Long customId, boolean active);

  ShortURLRecord findByOldUrlAndActive(String oldUrl, boolean active);

}
