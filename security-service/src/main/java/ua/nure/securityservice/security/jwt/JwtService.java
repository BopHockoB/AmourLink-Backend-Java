package ua.nure.securityservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.nure.securityservice.model.User;
import ua.nure.securityservice.responce.AuthenticationResponse;
import ua.nure.securityservice.security.AmourlinkUserDetails;
import ua.nure.securityservice.service.IUserService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Data
@Service
@NoArgsConstructor
public class JwtService {


    private IUserService userService;


    private String JWT_SECRET;


    private long expirationTime;


    private long refreshExpirationTime;

    public JwtService(long expirationTime,
                      long refreshExpirationTime,
                      String JWT_SECRET) {
        this.expirationTime = expirationTime;
        this.JWT_SECRET = JWT_SECRET;
        this.refreshExpirationTime = expirationTime;
    }

    @Autowired
    public JwtService(IUserService userService,
                      @Value("${spring.jwt.secret}") String JWT_SECRET,
                      @Value("${spring.jwt.jwtExpirationInMs}") long expirationTime,
                      @Value("${spring.jwt.jwtRefreshExpirationInMs}") long refreshExpirationTime
    ) {
        this.userService = userService;
        this.JWT_SECRET = JWT_SECRET;
        this.expirationTime = expirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    public String generateToken(String username) {
        User user = userService.findUser(username);

        return generateToken(user);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        claims.put("enabled", user.isEnabled());

        return generateToken(user, claims, expirationTime);
    }

    public String generateRefreshToken(String userName) {
        User user = userService.findUser(userName);
        return generateToken(user, new HashMap<>(), refreshExpirationTime);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, new HashMap<>(), refreshExpirationTime);
    }

    private String generateToken(User user,
                                 Map<String, Object> claims,
                                 long expirationTime) {
        return (buildToken(claims, user, expirationTime));
    }


    public String buildToken(Map<String, Object> claims, User user, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(user.getUserId().toString())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationTimeFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private boolean isTokenExpired(String token) {
        return extractExpirationTimeFromToken(token)
                .before(new Date());
    }

    private Key getSignedKey() {
        byte[] keyByte = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        final String userEmail = extractUsername(refreshToken);

        if (userEmail == null)
            return null;
        User user = userService.findUser(userEmail);
        AmourlinkUserDetails userDetails = new AmourlinkUserDetails(user);

        if (!validateToken(refreshToken, userDetails))
            return null;
        String accessToken = generateToken(user);
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return authResponse;


    }
}