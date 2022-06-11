package EE.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.log4j.Log4j2;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Log4j2
@Singleton
public class JWAuth implements HttpAuthenticationMechanism {

    private final InMemoryIdentityStore identity;
    private final Key key;

    @Inject
    public JWAuth(InMemoryIdentityStore identity, @Named(ConstantesSecurity.JWT) Key key) {
        this.identity = identity;
        this.key = key;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) throws AuthenticationException {


        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] valores = header.split(ConstantesSecurity.ESPACIO);

            if (valores[0].equalsIgnoreCase(ConstantesSecurity.BASIC)) {
                String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
                String[] userPassSeparado = userPass.split(ConstantesSecurity.DOS_PUNTOS);
                c = identity.validate(new UsernamePasswordCredential(userPassSeparado[0], userPassSeparado[1]));

                if (c.getStatus() == CredentialValidationResult.Status.VALID) {

                    String jws = Jwts.builder()
                            .setExpiration(Date
                                    .from(LocalDateTime.now().plusMinutes(25).atZone(ZoneId.systemDefault())
                                            .toInstant()))
                            .claim(ConstantesSecurity.USER, c.getCallerPrincipal().getName())
                            .claim(ConstantesSecurity.GROUP, c.getCallerGroups())
                            .signWith(key).compact();

                    httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, jws);

                }
            } else if (valores[0].equalsIgnoreCase(ConstantesSecurity.BEARER)) {

                try {

                    Jws<Claims> jws = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(valores[1]);

                    List<String> group = (List<String>) jws.getBody().get(ConstantesSecurity.GROUP);

                    c = new CredentialValidationResult(jws.getBody().get(ConstantesSecurity.USER).toString(), new HashSet<>(group));

                } catch (ExpiredJwtException exception) {
                    log.error(exception.getMessage());
                    httpServletResponse.setHeader(HttpHeaders.EXPIRES, ConstantesSecurity.EL_TOKEN_HA_EXPIRADO);

                } catch (Exception exception) {

                    log.error(exception.getMessage());
                }

            }

        }


        if (c.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            return httpMessageContext.doNothing();
        }


        return httpMessageContext.notifyContainerAboutLogin(c);

    }
}
