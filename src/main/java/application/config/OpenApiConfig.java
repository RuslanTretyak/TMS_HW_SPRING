package application.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Files uploader/downloader",
                description = "uploader/downloader",
                version = "1.0.0"
        )
)
public class OpenApiConfig {
}
