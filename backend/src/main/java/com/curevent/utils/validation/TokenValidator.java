package com.curevent.utils.validation;

import com.curevent.exceptions.AuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenValidator {

    public boolean validateToken(String token, String secretKey) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("Expired token", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationException("Invalid token", HttpStatus.BAD_REQUEST);
        } catch (MalformedJwtException e) {
            throw new AuthenticationException("Wrong token", HttpStatus.BAD_REQUEST);
        } catch (SignatureException e) {
            throw new AuthenticationException("Unverified token", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("Illegal argument", HttpStatus.BAD_REQUEST);
        }
    }
}
