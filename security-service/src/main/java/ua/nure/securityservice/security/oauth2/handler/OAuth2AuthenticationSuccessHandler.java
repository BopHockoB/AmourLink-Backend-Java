//package ua.nure.securityservice.security.oauth2.handler;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestClient;
//import ua.nure.securityservice.model.User;
//import ua.nure.securityservice.repository.UserRepository;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//@Component
//@RequiredArgsConstructor
//public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//
//    record EmailDetails(String email, Boolean primary, Boolean verified) {
//    }
//
//    @Value("${oauth2.baseUrl}")
//    private String baseUrl;
//    private final UserRepository userRepository;
//    private final OAuth2AuthorizedClientService authorizedClientService;
//    private final RestClient restClient = RestClient.builder()
//            .baseUrl(baseUrl)
//            .build();
//
//    @Override
//    public void onAuthenticationSuccess(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication auth
//    ) throws IOException {
//        if (auth instanceof OAuth2AuthenticationToken auth2AuthenticationToken) {
//            OAuth2User principal = auth2AuthenticationToken.getPrincipal();
//            String username = principal.getName();
//            String email = fetchUserEmailFromApi(auth2AuthenticationToken.getAuthorizedClientRegistrationId(), username);
//
//            if (!userRepository.existsByEmail(email)) {
//                var user = new User();
//                user.setEmail(email);
//                userRepository.save(user);
//            }
//        }
//
//        super.clearAuthenticationAttributes(request);
//        super.getRedirectStrategy().sendRedirect(request, response, "/api/v1/user/me");
//    }
//
//    private String fetchUserEmailFromApi(String clientRegistrationId, String principalName) {
//        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName);
//        String accessToken = authorizedClient.getAccessToken().getTokenValue();
//
//        var userEmailsResponse = restClient.get()
//                .headers(headers -> headers.setBearerAuth(accessToken))
//                .retrieve()
//                .body(EmailDetails[].class);
//
//        if (userEmailsResponse == null) {
//            return "null";
//        }
//
//        var fetchedEmailDetails = Arrays.stream(userEmailsResponse)
//                .filter(emailDetails -> emailDetails.verified() && emailDetails.primary())
//                .findFirst()
//                .orElseGet(() -> null);
//
//        return fetchedEmailDetails != null ? fetchedEmailDetails.email() : "null";
//    }
//}