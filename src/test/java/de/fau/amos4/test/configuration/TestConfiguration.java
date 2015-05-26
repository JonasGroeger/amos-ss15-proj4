package de.fau.amos4.test.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
@EnableAutoConfiguration
@ComponentScan("de.fau.amos4")
public class TestConfiguration
{
    // TODO: Add beans for the profile "unit-test", i.e. test hibernate (in-memory)
}
