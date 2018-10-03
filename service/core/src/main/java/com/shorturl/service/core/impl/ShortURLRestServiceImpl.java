package com.shorturl.service.core.impl;

import com.shorturl.service.core.ShortURLRestService;
import com.shorturl.service.core.model.ShortURLDetails;
import com.shorturl.service.core.model.URLCreationRequest;
import com.shorturl.service.core.model.URLCreationResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ShortURLRestServiceImpl implements ShortURLRestService {

  @Override
  public URLCreationResponse createAlias(URLCreationRequest urlCreationRequest) {
    return null;
  }

  @Override
  public ShortURLDetails getCorrespondingURL(String alias) {
    return null;
  }
}
