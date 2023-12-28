package practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocker(){

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder()
                                        .title("Fmmall api documentation")
                                        .description("The documentation describe the api info")
                                        .version("v1.0.0");

        ApiInfo apiInfo = apiInfoBuilder.build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .forCodeGeneration(true)
                .globalOperationParameters(globalParamList())
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("practice.controller"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

    private List<Parameter> globalParamList(){
        Parameter authTokenHeader = new
                ParameterBuilder()
                .name(HttpHeaders.AUTHORIZATION)
                .modelRef(new ModelRef("string"))
                .required(false)
                .parameterType("header")
                .description("Auth token")
                .build();
        return Collections.singletonList(authTokenHeader);
    }
}
