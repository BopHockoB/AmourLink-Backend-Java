package ua.nure.securityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "facebook", url = "https://graph.facebook.com")
public interface FacebookClient {
    @RequestMapping(method = RequestMethod.GET, value = "/debug_token")
    Map<String, Object> verifyToken(@RequestParam("input_token") String inputToken, @RequestParam("access_token") String accessToken);
}