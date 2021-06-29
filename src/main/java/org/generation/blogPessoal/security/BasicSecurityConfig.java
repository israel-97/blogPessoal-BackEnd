package org.generation.blogPessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity //Segurança da Web
public class BasicSecurityConfig  extends WebSecurityConfigurerAdapter { //WebSecurityConfigurerAdapter - bloqueia os endPoints

	@Autowired
	private UserDetailsService userdetailsService; //Avisando que vai usar dados de acesso usuario e senha que esta vindo de algum banco de dados.
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Gerenciar a autenticação
		auth.inMemoryAuthentication()
		.withUser("admin").password(passwordEncoder().encode("admin")).authorities("ROLE_ADMIN");
		auth.userDetailsService(userdetailsService); //
		
		
	}
	
	@Bean //Anotação mais básica, para o spring poder trabalhar. Indica um spring que aquele método produz uma instancia que pode ser gerenciada pelo spring, ou seja, se o spring precisar de um objeto do tipo do retorno do metodo com o @bean ele sabe onde buscar./ 
	public PasswordEncoder passwordEncoder() { //Esconder a senha.
		return new BCryptPasswordEncoder(); 
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { //joga a responsabilidade pra quem chamar o metodo...
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll()  //Essa configuração serve para liberar end point's, ou seja, para liberar alguns caminhos dentro do controller, para que o client tenha acesso sem uma chave token. 
		.antMatchers("/usuarios/cadastrar").permitAll() //Libera cadastrar e logar.
		.anyRequest().authenticated() // Todas as outras requisições deverão ser autenticadas.
	    .and().httpBasic() //Utiliza o padrão basic pra liberar a chave token
	    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //API Rest - Tempo de seção, Ela não guarda seção.. Stateless - Significa que não guarda status, quando a gente está falando resquisições http.
	    .and().cors() //Determina de onde e o que é possível acessar na api, lá vc configura por exemplo que apenas um ip pode acessar sua api, quando habilitado ele pega a configuração que vc coloca através da anotação @CrossOrigin nos controllers e então ele considera aquela configuração.
	    .and().csrf().disable(); //proteção contra hackers.
	}
	
	
	
	
}
