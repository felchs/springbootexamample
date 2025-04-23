package com.felipe.stefanini;

import com.nimbusds.jwt.JWTClaimNames;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JWTAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    private final String principalAttribute = "preferred_username";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwtSource) {
//        var authorities = Stream.concat(
//                jwtGrantedAuthoritiesConverter.convert(jwtSource).stream(),
//                extractResourceRoles(jwtSource).stream()
//        ).collect(Collectors.toSet());
        var authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwtSource).stream(),
                extractResourceRoles(jwtSource).stream()
        ).collect(Collectors.toList());

        return new JwtAuthenticationToken(jwtSource, authorities, getPrincipalClaimName(jwtSource));
    }

    private String getPrincipalClaimName(Jwt jwtSource) {
        String clainName = JWTClaimNames.SUBJECT;
        if (principalAttribute != null) {
            clainName = principalAttribute;
        }
        return jwtSource.getClaim(clainName);
    }

    private Collection<Object> extractResourceRoles(Jwt jwtSource) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if (jwtSource.getClaim("resource_access") == null) {
            return Set.of();
        }

        resourceAccess = jwtSource.getClaim("resource_access");

        if (resourceAccess.get("felipe-rest-api") == null) {
            return Set.of();
        }

        resource = (Map<String, Object>) resourceAccess.get("felipe-rest-api");

        resourceRoles = (Collection<String>) resource.get("roles");

        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
