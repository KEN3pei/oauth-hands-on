package com.auth.client_application.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/user", "/", "/callback", "/authorize").authenticated()
                .anyRequest().permitAll()
            )
            // .csrf(csrf -> 
                //csrf対策の適用外とする
                //TODO:あとで対策する
                // csrf.ignoringRequestMatchers("/token"))
            .formLogin(withDefaults());
            // 以下にするとカスタムログインページを指定できる
            // .formLogin((form) -> form
			// 	.loginPage("/hello").permitAll()
			// );
        
            return http.build();
    }

    // DBで認証情報を管理できるように、CustomUserDetailsServiceをBeanに登録するように修正
    // @Bean
	// public UserDetailsService userDetailsService() {
	// 	UserDetails user =
	// 		 User.withDefaultPasswordEncoder()
	// 			.username("user")
	// 			.password("password")
	// 			.roles("USER")
	// 			.build();

	// 	return new InMemoryUserDetailsManager(user);
	// }

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}