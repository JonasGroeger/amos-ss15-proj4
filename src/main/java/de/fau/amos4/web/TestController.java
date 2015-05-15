package de.fau.amos4.web;

import de.fau.amos4.ApplicationSettings;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class TestController
{
    @Resource
    ApplicationSettings applicationSettings;

    @RequestMapping("/test")
    public String message()
    {
        return applicationSettings.getName();
    }
}
