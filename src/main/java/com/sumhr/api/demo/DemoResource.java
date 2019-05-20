package com.sumhr.api.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumhr.api.error.CustomError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = { "demo" })
@Slf4j
@Controller
public class DemoResource {

    @Value("${demo.value:unknown}")
    private String demoEnvValue;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiResponses(value = {
        @ApiResponse(code = 200, response = DemoOutput.class, message = "demo resource request processed"),
        @ApiResponse(code = 404, response = CustomError.class, message = "not found"),
        @ApiResponse(code = 409, response = CustomError.class, message = "logical error"),
        @ApiResponse(code = 422, response = CustomError.class, message = "request data invalid, check body parameters")})
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createDemoResource(HttpServletRequest request,
            @Valid @RequestBody @NotNull DemoInput demoInput) throws Exception {
        log.info("~ AccountResource.generateAccountOtp ~ body ~ {} ",
                objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(demoInput));
        DemoOutput demoOutput = new DemoOutput();
        demoOutput.setOutput(demoEnvValue + demoInput.getInput());
        return ResponseEntity.status(HttpStatus.OK).body(demoOutput);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, response = DemoOutput.class, message = "demo resource request processed"),
        @ApiResponse(code = 404, response = CustomError.class, message = "not found"),
        @ApiResponse(code = 409, response = CustomError.class, message = "logical error"),
        @ApiResponse(code = 422, response = CustomError.class, message = "request data invalid, check body parameters")})
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getDemoResource(HttpServletRequest request) throws Exception {
        DemoOutput demoOutput = new DemoOutput();
        demoOutput.setOutput(demoEnvValue + "Demo OutPut");
        return ResponseEntity.status(HttpStatus.OK).body(demoOutput);
    }

}
