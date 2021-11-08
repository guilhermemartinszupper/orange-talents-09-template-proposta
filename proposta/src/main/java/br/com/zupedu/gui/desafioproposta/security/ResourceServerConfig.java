package br.com.zupedu.gui.desafioproposta.security;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.*;

@Configuration
@EnableWebSecurity
@Profile(value = {"dev","prod"})
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers(HttpMethod.GET, "/actuator/prometheus").permitAll()
                        .antMatchers(HttpMethod.GET, "/propostas/**").hasAuthority("SCOPE_escopo-proposta")
                        .antMatchers(HttpMethod.POST, "/propostas/**").hasAuthority("SCOPE_escopo-proposta")
                        .antMatchers(HttpMethod.POST, "/biometrias/**").hasAuthority("SCOPE_escopo-proposta")
                        .antMatchers(HttpMethod.POST, "/actuator/**").hasAuthority("SCOPE_escopo-proposta")
                        .anyRequest().authenticated()
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}
