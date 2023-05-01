package io.wisoft.capstonedesign.global.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation()
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(
                responseCode = "404",
                content = @Content(schema = @Schema(implementation = Error.class)))
})
public @interface SwaggerApiError {
}
