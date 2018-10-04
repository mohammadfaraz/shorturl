package com.shorturl.service.core;

import com.shorturl.service.core.model.ShortURLDetails;
import com.shorturl.service.core.model.URLCreationRequest;
import com.shorturl.service.core.model.URLCreationResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shortURLService")
public interface ShortURLRestService {

  @RequestMapping(value = "createAlias", method = RequestMethod.POST)
  URLCreationResponse createAlias(@RequestBody URLCreationRequest urlCreationRequest);

  @RequestMapping(value = "getURLByAlias", method = RequestMethod.GET)
  ShortURLDetails getCorrespondingURL(@RequestParam(value = "alias") String alias);

}
