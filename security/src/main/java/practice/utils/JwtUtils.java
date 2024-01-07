package practice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import practice.user.CustomUserDetails;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {
    @Value("${jwt.secret_key}")
    String key;

    @Value("${jwt.expire_day}")
    int expireDay;

//    @Autowired
//    StringRedisTemplate redisTemplate;

    @Autowired
    UserDetailsService userDetailsService;

    public String createJwt(CustomUserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        Date expire = expire();
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("id", userDetails.getUserId())
                .withClaim("username", userDetails.getUsername())
                .withExpiresAt(expire)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    private Date expire() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expireDay * 24);
        return calendar.getTime();
    }

    public DecodedJWT resolve(String headerToken) {
        String token = convertToken(headerToken);
        if (token == null)
            return null;

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(key)).build();

        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
//            if (this.isInvalidToken(jwt.getId()))
//                return null;
            Date expiredAt = jwt.getExpiresAt();
            return new Date().after(expiredAt) ? null : jwt;

        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public UserDetails toUser(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        String username = claims.get("username").asString();
        return userDetailsService.loadUserByUsername(username);
    }

//    public boolean invalidateJwt(String headerToken) {
//        String token = convertToken(headerToken);
//        if (token == null)
//            return false;
//
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(key)).build();
//
//        try {
//            DecodedJWT jwt = jwtVerifier.verify(token);
//            return deleteToken(jwt.getId(), jwt.getExpiresAt());
//
//        } catch (JWTVerificationException e) {
//            return false;
//        }
//    }

//    private boolean deleteToken(String tokenId, Date expireAt) {
//        if (isInvalidToken(tokenId))
//            return false;
//
//        Date now = new Date();
//        long remainingTime = Math.max(expireAt.getTime() - now.getTime(), 0);
//        redisTemplate.opsForValue().set(JwtClaimsConstant.JWT_BLACK_LIST + tokenId, "", remainingTime, TimeUnit.MILLISECONDS);
//        return true;
//    }
//
//    private boolean isInvalidToken(String tokenId) {
//        return Boolean.TRUE.equals(redisTemplate.hasKey(JwtClaimsConstant.JWT_BLACK_LIST + tokenId));
//    }

    private static String convertToken(String headerToken) {
        if (headerToken == null || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }


    public static Long toId(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return claims.get("id").asLong();
    }
}
