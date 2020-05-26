package mvcrest.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.crypto.MacProvider;
import mvcrest.avioni.AvioniRepository;
import mvcrest.avioni.Korisnik;
import mvcrest.user.User;
import mvcrest.user.UserRepository;
import mvcrest.util.Util;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    public static String generateJWT(Korisnik korisnik){
        return Jwts.builder().setSubject(korisnik.getUsername())
                .setExpiration(new Date(new Date().getTime() + 1000*60*60L))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, generalKey())
                .compact();
    }

    public static boolean isAuthorized(String token){
        if(!Util.isEmpty(token) && token.contains("Bearer ")){
            String jwt = token.substring(token.indexOf("Bearer ") + 7);
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwt);
                return AvioniRepository.getKorisnikByUsername(claims.getBody().getSubject()) != null;
            }catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static SecretKey generalKey(){
        String stringKey = "MOjMNOGOJAKLJUC";
        byte[] encodedKey = Base64.getDecoder().decode(stringKey);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA512");
    }
}
