package org.generation.blogPessoal.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.generation.blogPessoal.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData())
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, responseMessageForGet());
	}
	
	public ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("API - Blog Pessoal")
				.description("Projeto API Spring - Blog Pessoal")
				.version("1.0.0")
				.license("Apache License Version 2.0")
				.license("http://localhost:8080/swagger-ui/")
				.contact(contact()).build();
	}
	
	private Contact contact() {
		return new Contact("Israel Dantas", "https://github.com/israel-97",
				"israel.dantas1@gmail.com");
	
}
	private List<springfox.documentation.service.Response> responseMessageForGet(){
		return new ArrayList<springfox.documentation.service.Response>() {
			private static final long serialVersionUID = 1L;	
			{
				add(new ResponseBuilder().code("200").description("Sucesso!").build());
				add(new ResponseBuilder().code("201").description("Objeto Criado!").build());
				add(new ResponseBuilder().code("401").description("Não Autorizado!").build());
				add(new ResponseBuilder().code("403").description("Proibido!").build());
				add(new ResponseBuilder().code("404").description("Não Encontrado!").build());
				add(new ResponseBuilder().code("500").description("Erro!").build());
			}
};

}
	
	}








