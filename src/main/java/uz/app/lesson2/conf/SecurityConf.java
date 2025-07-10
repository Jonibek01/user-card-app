package uz.app.lesson2.conf;

import jakarta.activation.URLDataSource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.web.servlet.filter.OrderedFormContentFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.app.lesson2.repository.AuthRepository;



import javax.sql.DataSource;
import java.net.URL;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConf {

    final AuthRepository userRepository;
    final MyFilter myFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OrderedFormContentFilter formContentFilter) throws Exception {
        return http
                .csrf(c -> c.disable())
                .cors(c -> c.disable())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/**", "/card/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> userRepository.findByEmail(username).orElseThrow();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
