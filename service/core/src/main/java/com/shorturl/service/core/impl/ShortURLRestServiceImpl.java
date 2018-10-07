package com.shorturl.service.core.impl;

import java.time.LocalDateTime;
import java.util.TimeZone;

import com.shorturl.service.core.ShortURLRestService;
import com.shorturl.service.core.data.record.ShortURLRecord;
import com.shorturl.service.core.data.repository.ShortURLRepository;
import com.shorturl.service.core.model.ShortURLDetails;
import com.shorturl.service.core.model.URLCreationRequest;
import com.shorturl.service.core.model.URLCreationResponse;
import com.shorturl.shared.cache.LRUCache.LocalCache;
import com.shorturl.shared.util.baseconvertor.BaseConverter;
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

  private LocalCache<String, ShortURLRecord> localCache = new LocalCache<>(1300);

  @Transactional(readOnly = false)
  @Override
  public URLCreationResponse createAlias(@RequestBody URLCreationRequest urlCreationRequest) {
    URLCreationResponse urlCreationResponse = new URLCreationResponse();
    /*Validate Request*/
    validateRequest(urlCreationRequest, urlCreationResponse);
    if (urlCreationResponse.getHttpStatus() != null) {
      return urlCreationResponse;
    }

    /*look for entry in local cache*/
    ShortURLRecord shortURLRecord = localCache.get(urlCreationRequest.getOldUrl());

    /*entry not found in local cache look in db*/
    if (shortURLRecord == null) {
      shortURLRecord = shortURLRepository
          .findByOldUrlAndActive(urlCreationRequest.getOldUrl(), true);
    }

    /*entry not found in db. Concluding as new entry. Initiate a insertion*/
    if (shortURLRecord == null) {
      shortURLRecord = mapper.map(urlCreationRequest, ShortURLRecord.class);
      shortURLRecord.setCustomId(KeyGenerator.getUniqueCustomKey());
      try {
        shortURLRecord = shortURLRepository.save(shortURLRecord);
      } catch (Throwable e) {
        urlCreationResponse.setHttpStatus(HttpStatus.CONFLICT);
        return urlCreationResponse;
      }
    }
    urlCreationResponse = mapper.map(shortURLRecord, URLCreationResponse.class);
    urlCreationResponse.setAlias(BaseConverter
        .encode((long) characterStream.length(), shortURLRecord.getCustomId(), characterStream));

    localCache.put(urlCreationResponse.getAlias(), shortURLRecord);

    urlCreationResponse.setHttpStatus(HttpStatus.CREATED);
    return urlCreationResponse;
  }

  @Override
  public ShortURLDetails getCorrespondingURL(@RequestParam(value = "alias") String alias) {
    ShortURLDetails shortURLDetails = new ShortURLDetails();
    if (alias == null) {
      return null;
    }
    ShortURLRecord shortURLRecord = localCache.get(alias);
    if (shortURLRecord == null) {
      Long customId = BaseConverter.decode((long) characterStream.length(), alias, characterStream);
      shortURLRecord = shortURLRepository.findByCustomIdAndActive(customId, true);
    }
    if (shortURLRecord == null) {
      return null;
    }
    shortURLDetails.setExpirationTime(shortURLRecord.getExpirationTime());
    shortURLDetails.setUrl(shortURLRecord.getOldUrl());
    return shortURLDetails;
  }

  private void validateRequest(URLCreationRequest urlCreationRequest,
      URLCreationResponse urlCreationResponse) {
    if (urlCreationRequest.getOldUrl() == null || StringUtils
        .isEmpty(urlCreationRequest.getOldUrl())) {
      urlCreationResponse.setHttpStatus(HttpStatus.CONFLICT);
    } else if (urlCreationRequest.getExpirationTime() == null) {
      urlCreationRequest.setExpirationTime(DateUtil
          .convertLocalDateTimeToDate(LocalDateTime.now().plusDays(30).with(LocalDateTime.MAX),
              TimeZone.getDefault()));
    }
  }
}
