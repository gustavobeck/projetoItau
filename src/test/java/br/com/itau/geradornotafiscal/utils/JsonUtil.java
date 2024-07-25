package br.com.itau.geradornotafiscal.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {
    private static final String ZONED_TIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX";

    public static ObjectMapper getMapper() {
        final var mapper = new ObjectMapper();

        mapper.setTimeZone(TimeZone.getDefault());

        final var timeModule = new JavaTimeModule();
        timeModule.addSerializer(new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(ZONED_TIME_FORMATTER)));
        mapper.registerModule(timeModule);
        mapper.registerModule(new Jdk8Module());

        return mapper;
    }
}