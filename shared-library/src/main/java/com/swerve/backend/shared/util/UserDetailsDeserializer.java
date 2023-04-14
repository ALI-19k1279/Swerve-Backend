package com.swerve.backend.shared.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.swerve.backend.shared.dto.UserDetailsDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.swerve.backend.shared.security.SecurityUtils.ROOT_USER_ID;

public class UserDetailsDeserializer extends StdDeserializer<UserDetails> {

    public UserDetailsDeserializer() {
        this(null);
    }

    public UserDetailsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UserDetails deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode authoritiesNode = (ArrayNode) node.get("authorities");
        List<String> authorities = new ArrayList<>();
        for (JsonNode authorityNode : authoritiesNode) {
            authorities.add(authorityNode.get("authority").asText());
        }
        Set<SimpleGrantedAuthority> permissions = authorities.stream()
                .map(permission -> new SimpleGrantedAuthority(permission))
                .collect(Collectors.toSet());
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (JsonNode authorityNode : authoritiesNode) {
//            GrantedAuthority authority = mapper.treeToValue(authorityNode, SimpleGrantedAuthority.class);
//            authorities.add(authority);
//        }
        Long id = node.get("id").asLong();
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        boolean accountNonExpired = node.get("accountNonExpired").asBoolean();
        boolean accountNonLocked = node.get("accountNonLocked").asBoolean();
        boolean credentialsNonExpired = node.get("credentialsNonExpired").asBoolean();
        boolean enabled = node.get("enabled").asBoolean();
        String portal = node.get("portal").asText();
        UserDetailsDTO userDetails =
                UserDetailsDTO.builder()
                        .id(id)
                        .username(username)
                        .password(password)
                        .portal(portal)
                        .authorities(permissions)
//                        .roles(Set.of(root, admin))
                        .accountNonExpired(accountNonExpired)
                        .accountNonLocked(accountNonLocked)
                        .credentialsNonExpired(credentialsNonExpired)
                        .enabled(enabled)
                        .build();
//        UserDetailsDTO userDetails = new UserDetailsDTO(username, password, authorities, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, portal);
        return userDetails;
    }
}

