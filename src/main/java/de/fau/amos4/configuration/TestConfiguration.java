package de.fau.amos4.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
@EnableAutoConfiguration
public class TestConfiguration
{
    // TODO: Add beans for the profile "unit-test", i.e. test hibernate (in-memory)
}
