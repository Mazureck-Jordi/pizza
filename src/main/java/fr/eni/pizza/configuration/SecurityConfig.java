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
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        //Visible par le gérant uniquement
                        .requestMatchers("/list-utilisateur").hasAnyAuthority("2")
                        .requestMatchers("/details-utilisateur/**").hasAnyAuthority("2")
                        .requestMatchers("/show-produit-form/**").hasAnyAuthority("2")
                        .requestMatchers("/show-produit-form").hasAnyAuthority("2")
                        .requestMatchers("/produit-form").hasAnyAuthority("2")
                        .requestMatchers("/delete-produit/**").hasAnyAuthority("2")
                        .requestMatchers("/list-commandes").hasAnyAuthority("2")
                        .requestMatchers("/details-commande/**").hasAnyAuthority("2")
                        .requestMatchers("/delete-commande/{id}").hasAnyAuthority("2")
                        .requestMatchers("/show-utilisateur-form/**").hasAnyAuthority("2")
                        .requestMatchers("/show-utilisateur-form").hasAnyAuthority("2")
                        .requestMatchers("/utilisateur-form").hasAnyAuthority("2")
                        .requestMatchers("/delete-utilisateur/**").hasAnyAuthority("2")
                        .requestMatchers("/delete-role-utilisateur/**").hasAnyAuthority("2")
                        .requestMatchers("/delete-role-utilisateur/**").hasAnyAuthority("2")
                        .requestMatchers("/delete-detail-commande/**").hasAnyAuthority("2")

                        //Visible par le gérant et le pizzaiolo
                        .requestMatchers("/show-commande-form").hasAnyAuthority("2", "1")
                        .requestMatchers("/show-commande-form/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/commande-form").hasAnyAuthority("2", "1")
                        .requestMatchers("/show-creation-commande/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/creation-commande").hasAnyAuthority("2", "1")
                        .requestMatchers("/delete-produit-details-commande/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/date-form/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/livraison-form/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/est-paye/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/non-paye/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/en-cours-creation/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/en-preparation-form/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/fin de-preparation-form/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/enregistrer/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/details-detail-commande/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/show-client-form/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/show-client-form").hasAnyAuthority("2", "1")
                        .requestMatchers("/client-form/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/client-form").hasAnyAuthority("2", "1")
                        .requestMatchers("/delete-client/**").hasAnyAuthority("2", "1")
                        .requestMatchers("/list-clients").hasAnyAuthority("2", "1")
                        .requestMatchers("/details-client/**").hasAnyAuthority("2", "1")

                        //Modification de commande par le livreur et le Gérant
                        .requestMatchers("/en-livraison-form/**").hasAnyAuthority("2", "3")
                        .requestMatchers("/fin de-livraison-form/**").hasAnyAuthority("2", "3")

                        //Visible touts les utilisateurs
                        .requestMatchers("/list-commande-by-etat").hasAnyAuthority("3", "2", "1")
                        .requestMatchers("/detail-commande-pizzaiolo/**").hasAnyAuthority("3", "2", "1")
                        .requestMatchers("/show-simple-utilisateur-form").hasAnyAuthority("3", "2", "1")
                        .requestMatchers("/simple-utilisateur-form").hasAnyAuthority("3", "2", "1")

                        //Visible par les personnes connectées
                        .requestMatchers("/logout").authenticated()
                        .requestMatchers("/profil-utilisateur").authenticated()

                                //VIsible par tous
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/vendor/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/list-produits").permitAll()
                        .requestMatchers("/details-produit/**").permitAll()


                        //Poubelle
                        .requestMatchers("/user-page").permitAll()
                        .requestMatchers("/etape1").permitAll()
                        .requestMatchers("/show-detail-commande-form/**").permitAll()
                        .requestMatchers("/show-detail-commande-form").permitAll()
                        .requestMatchers("/list-detail-commande").permitAll()
                        .requestMatchers("detail-commande-form").permitAll()
                        //rejette toutes les requêtes non configurées
                        .anyRequest().permitAll()
                        //rejette toutes les requêtes non configurée aux utilisateurs non connectés
                        //.anyRequest().authenticated()
                        //accepte toutes les requêtes non configurée à tous les utilisateurs
                        //.anyRequest().permitAll()
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
