package com.nl.cgi.bff.config.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfiguration {
    @Bean
    public Docket component() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Login verify and country details")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nl.cgi.bff.web"))
                .paths(PathSelectors.ant("/login-service/**"))
                .build().apiInfo(componentInfo());
    }

    private ApiInfo componentInfo() {
        return new ApiInfoBuilder().title("This controller used to verify the user")
                .description("This controller used to verify the user")
                .version("0.0.1")
                .build();
    }



}