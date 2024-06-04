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

    @Autowired
    private IUserService userService;

    @Value("${spring.jwt.secret}")
    private String JWT_SECRET;

    @Value("${spring.jwt.jwtExpirationInMs}")
    private int JWT_EXPIRATION_TIME_IN_MILLISECONDS;

    public JwtService(int JWT_EXPIRATION_TIME_IN_MILLISECONDS, String JWT_SECRET) {
        this.JWT_EXPIRATION_TIME_IN_MILLISECONDS = JWT_EXPIRATION_TIME_IN_MILLISECONDS;
        this.JWT_SECRET = JWT_SECRET;
    }

    public JwtService(IUserService userService,
                      @Value("${spring.jwt.secret}") String JWT_SECRET,
                      @Value("${spring.jwt.jwtExpirationInMs}") int JWT_EXPIRATION_TIME_IN_MILLISECONDS) {
        this.userService = userService;
        this.JWT_SECRET = JWT_SECRET;
        this.JWT_EXPIRATION_TIME_IN_MILLISECONDS = JWT_EXPIRATION_TIME_IN_MILLISECONDS;
    }

    public String generateToken(String username){
        User user = userService.findUser(username);

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());

        return tokenCreator(claims, user);
    }

    public String tokenCreator(Map<String, Object> claims, User user){
        return Jwts.builder()
                .setClaims(claims)
                .setId(user.getUserId().toString())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME_IN_MILLISECONDS))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsernameFromToken(String token){
        return extractClaim(token, Claims ::getSubject);
    }
    public Date extractExpirationTimeFromToken(String token) {
        return extractClaim(token, Claims :: getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private   <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private  Claims extractAllClaims(String token) {
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
    private Key getSignedKey(){
        byte[] keyByte = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyByte);
    }

}
