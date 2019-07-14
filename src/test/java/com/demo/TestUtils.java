package com.demo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtils
{
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static String getSamplesJsonString(String jsonFileName) throws IOException
    {
        Resource resource = new ClassPathResource(jsonFileName);
        InputStream inputStream = resource.getInputStream();
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
    }

    public static <T> T getSamplesJsonBean(String jsonFileName, Class<T> returnType) throws IOException
    {
        Resource resource = new ClassPathResource(jsonFileName);
        InputStream inputStream = resource.getInputStream();
        return mapper.readValue(inputStream, returnType);
    }

}
