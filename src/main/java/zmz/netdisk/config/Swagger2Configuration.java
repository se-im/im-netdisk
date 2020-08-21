package zmz.netdisk.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaomanzhou
 */

@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Configuration
{
    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("zmz.netdisk.controller"))
                .paths(PathSelectors.any())
                .build();
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("im文件管理服务器前后端交互api")
                .description("im文件管理服务器前后端交互api")
                .termsOfServiceUrl("http://localhost:8010/")
                .version("1.0")
                .build();
    }
}
