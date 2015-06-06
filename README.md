# amos-ss15-proj4 [![Build Status](https://travis-ci.org/JOBAA/amos-ss15-proj4.svg?branch=master)](https://travis-ci.org/JOBAA/amos-ss15-proj4)
Personalfragebogen 2.0 @ DATEV

# Setup (*long*)
1. Clone the project using `git clone git@github.com:JOBAA/amos-ss15-proj4.git`
2. Change to the project directory using `cd amos-ss15-proj4`
3. Run `./gradlew idea` or `./gradlew eclipse`, depending on which IDE you use.
4. Load the project in the IDE using "Import existing (Gradle) Project" or similar.

# Setup (*tl;dr*)
    git clone git@github.com:JOBAA/amos-ss15-proj4.git
    cd amos-ss15-proj4
    ./gradlew idea # For IntelliJ Idea
    ./gradlew eclipse # For Eclipse

# Gradle
To get a list of available commands, use `./gradlew tasks`.

# Configuration

Configuration is held in [YAML](http://yaml.org/) files. We have a main configuration with application wide
configuration. Then, there is a developer specific configuration file which overrides any configuration that a developer
might have locally. To enable this, just rename the file:

    # Linux
    mv src/main/resources/config/application-developer-specific.yml{.sample,}

    # Windows
    rename-item -path src/main/resources/config/application-developer-specific.yml.sample -newname application-developer-specific.yml

This developer specific configuration file is ignored in our .gitignore and should not be committed to version control,
since most probably it contains sensitive information. Further comments on what is loaded in which profile can be found
in the file `src/main/resources/config/application.yml`.

To not confuse the terms *Configuration* and *Profile*: a configuration can contain multiple profiles. For example, we
could have a configuration `use-in-memory-db` and a configuration `load-sql-from-file-on-startup`. These could be
combined (and will be resolved hierarchically),
[as seen in the Spring Boot documentation](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html).

# Localization

When working on the l18n, just use [the online tool native2ascii](http://native2ascii.net/) to convert. If you dont want
to do that for some reason, use the provided table below:
	
    ä -> \u00e4
    ö -> \u00f6
    ü -> \u00fc
    Ä -> \u00c4
    Ö -> \u00d6
    Ü -> \u00dc
    ß -> \u00df


# Running the Tests

If you want to run the tests using for example `./gradlew test`.
