package com.shorturl.service.core.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.shorturl.service.core.ShortURLRestService;
import com.shorturl.service.core.data.record.ShortURLRecord;
import com.shorturl.service.core.data.repository.ShortURLRepository;
import com.shorturl.service.core.model.ShortURLDetails;
import com.shorturl.service.core.model.ShortURLObjectCache;
import com.shorturl.service.core.model.URLCreationRequest;
import com.shorturl.service.core.model.URLCreationResponse;
import com.shorturl.shared.util.baseconvertor.BaseConvertor;
import com.shorturl.shared.util.date.DateUtil;
import com.shorturl.shared.util.generator.KeyGenerator;
import com.shorturl.shared.util.generator.StreamGenerator;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Transactional(readOnly = true)
public class ShortURLRestServiceImpl implements ShortURLRestService {

  private Mapper mapper = new DozerBeanMapper();

  @Autowired
  private ShortURLRepository shortURLRepository;

  private static final String characterStream = StreamGenerator.getBase62Stream();

  private Map<String, ShortURLObjectCache> localCache = new HashMap<>(2000);

  @Transactional(readOnly = false)
  @Override
  public URLCreationResponse createAlias(@RequestBody URLCreationRequest urlCreationRequest) {
    URLCreationResponse urlCreationResponse = new URLCreationResponse();
    /*Validate Request*/
    validateRequest(urlCreationRequest, urlCreationResponse);
    if (urlCreationResponse.getHttpStatus() != null) {
      return urlCreationResponse;
    }
    ShortURLRecord shortURLRecord;

    /*look for entry in local cache*/
    ShortURLObjectCache shortURLObjectCache = localCache.get(urlCreationRequest.getOldUrl());

    /*entry not found in local cache look in db*/
    if (shortURLObjectCache == null) {
      shortURLRecord = shortURLRepository
          .findByOldUrlAndActive(urlCreationRequest.getOldUrl(), true);
      if (shortURLRecord != null) {
        shortURLObjectCache = populateshortURLObjectCache(shortURLRecord);
      }
    }

    /*entry not found in db. Concluding asnew entry. Initiate a insertion*/
    if (shortURLObjectCache == null) {
      shortURLRecord = mapper.map(urlCreationRequest, ShortURLRecord.class);
      shortURLRecord.setCustomId(KeyGenerator.getUniqueCustomKey());
      try {
        shortURLRecord = shortURLRepository.save(shortURLRecord);
        shortURLObjectCache = populateshortURLObjectCache(shortURLRecord);
      } catch (Throwable e) {
        urlCreationResponse.setHttpStatus(HttpStatus.CONFLICT);
        return urlCreationResponse;
      }
    }
    urlCreationResponse = mapper
        .map(shortURLObjectCache.getShortURLRecord(), URLCreationResponse.class);
    urlCreationResponse.setAlias(BaseConvertor.encode((long) characterStream.length(),
        shortURLObjectCache.getShortURLRecord().getCustomId(), characterStream));
    urlCreationResponse.setHttpStatus(HttpStatus.CREATED);
    return urlCreationResponse;
  }

  @Override
  public ShortURLDetails getCorrespondingURL(@RequestParam(value = "alias") String alias) {
    return null;
  }

  private ShortURLObjectCache populateshortURLObjectCache(ShortURLRecord shortURLRecord) {
    ShortURLObjectCache shortURLObjectCache = new ShortURLObjectCache();
    shortURLObjectCache.setAccessedOn(new Date());
    shortURLObjectCache.setShortURLRecord(shortURLRecord);
    localCache.put(shortURLRecord.getOldUrl(), shortURLObjectCache);
    return shortURLObjectCache;
  }

  private void validateRequest(URLCreationRequest urlCreationRequest,
      URLCreationResponse urlCreationResponse) {
    if (urlCreationRequest.getOldUrl() == null || StringUtils
        .isEmpty(urlCreationRequest.getOldUrl())) {
      urlCreationResponse.setHttpStatus(HttpStatus.CONFLICT);
    } else if (urlCreationRequest.getExpirationTime() == null) {
      urlCreationRequest.setExpirationTime(DateUtil
          .convertLocalDateTimeToDate(LocalDateTime.now().plusDays(30), TimeZone.getDefault()));
    }
  }

  /*@PostPersist
  private void checkLocalCacheSize() {
    Set keySet = new HashSet();
    if (localCache.size() >= 1500) {
      localCache.forEach((key, value) -> {
        if(keySet.size() < 600) {

        }

      });
    }

  }*/
}
