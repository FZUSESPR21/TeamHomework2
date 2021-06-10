package com.example.scoringsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.HostAuthenticationToken;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTToken implements HostAuthenticationToken {
    static final long serialVersionUID = 9217639903967592166L;
    String token;
    String host;

    public JWTToken(String jwtToken) {
        this(jwtToken, null);
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
