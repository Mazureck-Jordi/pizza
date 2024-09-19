package fr.eni.pizza.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {


        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, mot_de_passe, 1 FROM utilisateur WHERE email = ?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT u.id_utilisateur AS UTILISATEUR_id_utilisateur, r.id_role AS ROLE_id_role,  r.libelle ,  u.nom, u.prenom, u.email, u.mot_de_passe, u.COMMANDE_id_commande FROM role_utilisateur ru JOIN utilisateur u ON u.id_utilisateur = ru.UTILISATEUR_id_utilisateur JOIN role r ON r.id_role = ru.ROLE_id_role WHERE u.email = ?");

        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain web (HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/date-form/**").permitAll()
                        .requestMatchers("/est-paye/**").permitAll()
                        .requestMatchers("/non-paye/**").permitAll()
                        .requestMatchers("/en-cours-creation/**").permitAll()
                        .requestMatchers("/en-preparation-form/**").permitAll()
                        .requestMatchers("/fin de-preparation-form/**").permitAll()
                        .requestMatchers("/livraison-form/**").permitAll()
                        .requestMatchers("/enregistrer/**").permitAll()
                        .requestMatchers("/en-livraison-form/**").permitAll()
                        .requestMatchers("/fin de-livraison-form/**").permitAll()
                        .requestMatchers("/list-commandes-by-etat").permitAll()
                        .requestMatchers("/detail-commande-pizzaiolo/**").permitAll()
                        .requestMatchers("/user-page").permitAll()
                        .requestMatchers("/delete-role-utilisateur/**").permitAll()
                        .requestMatchers("/profil-utilisateur").permitAll()
                        .requestMatchers("/show-simple-utilisateur-form").permitAll()
                        .requestMatchers("/simple-utilisateur-form").permitAll()
                        .requestMatchers("/list-utilisateur").hasAnyAuthority("2")
                        .requestMatchers("/details-utilisateur/**").hasAnyAuthority("2")
                        .requestMatchers("/show-utilisateur-form/**").permitAll()
                        .requestMatchers("/show-utilisateur-form").permitAll()
                        .requestMatchers("/utilisateur-form").permitAll()
                        .requestMatchers("/delete-utilisateur/**").permitAll()
                        .requestMatchers("/delete-role-utilisateur/**").hasAnyAuthority("2")
                        .requestMatchers("/etape1").hasAnyAuthority("2", "1")
                        .requestMatchers("/list-produits").permitAll()
                        .requestMatchers("/details-produit/**").permitAll()
                        .requestMatchers("/show-produit-form/**").hasAnyAuthority("2")
                        .requestMatchers("/show-produit-form").hasAnyAuthority("2")
                        .requestMatchers("/produit-form").permitAll()
                        .requestMatchers("/delete-produit/**").hasAnyAuthority("2")
                        .requestMatchers("/show-creation-commande/**").permitAll()
                        .requestMatchers("/creation-commande").hasAnyAuthority("2", "1")
                        .requestMatchers("/list-detail-commande").permitAll()
                        .requestMatchers("/details-detail-commande/**").permitAll()
                        .requestMatchers("/show-detail-commande-form/**").permitAll()
                        .requestMatchers("/show-detail-commande-form").permitAll()
                        .requestMatchers("detail-commande-form").permitAll()
                        .requestMatchers("/delete-detail-commande/**").permitAll()
                        .requestMatchers("/list-commandes").permitAll()
                        .requestMatchers("/details-commande/**").permitAll()
                        .requestMatchers("/show-commande-form/**").permitAll()
                        .requestMatchers("/show-commande-form").permitAll()
                        .requestMatchers("/commande-form").permitAll()
                        .requestMatchers("/delete-commande/{id}").permitAll()
                        .requestMatchers("/list-clients").permitAll()
                        .requestMatchers("/details-client/**").permitAll()
                        .requestMatchers("/show-client-form/**").permitAll()
                        .requestMatchers("/show-client-form").permitAll()
                        .requestMatchers("/client-form").permitAll()
                        .requestMatchers("/delete-client/**").permitAll()
                        .requestMatchers("/vendor/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/delete-produit-details-commande/**").permitAll()
                        //.anyRequest().authenticated()
                        .anyRequest().permitAll()
                );

        //configure la page de login de Spring Security
       // http.formLogin(Customizer.withDefaults());

        http.formLogin(form ->
                form.loginPage("/login")
                        .defaultSuccessUrl("/profil-utilisateur"));

        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(
                new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL));

        http
                .logout((logout) ->
                    logout

                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/login?logout")
                        .addLogoutHandler(clearSiteData)
                );

        return http.build();
    }
}
