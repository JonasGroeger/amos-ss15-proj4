# [amos-ss15-proj4](https://osr-amos.cs.fau.de/)

[![Build Status](https://travis-ci.org/JOBAA/amos-ss15-proj4.svg?branch=master)](https://travis-ci.org/JOBAA/amos-ss15-proj4)
[![Join the chat at https://gitter.im/JOBAA/amos-ss15-proj4](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/JOBAA/amos-ss15-proj4)

Personalfragebogen 2.0 is a personal data management software solution, supporting companies of any size in hiring new employees more efficiently.
Our product improves the hiring process by automating the collection of personal data during the hiring procedure, and provides aid in managing the collected data.
Thanks to the intuitive, user-friendly graphical user-interface our software is easy to be deployed and used in any sector.
Compared to the current Excel sheet based solution, our software will require much less manual interaction, which will save our clients time and money.

## Setup
### Longer version
1. Clone the project using `git clone git@github.com:JOBAA/amos-ss15-proj4.git`
2. Change to the project directory using `cd amos-ss15-proj4`
3. Run `./gradlew idea` or `./gradlew eclipse`, depending on which IDE you use.
4. Load the project in the IDE using "Import existing (Gradle) Project" or similar.

### Shorter version
    git clone git@github.com:JOBAA/amos-ss15-proj4.git
    cd amos-ss15-proj4
    ./gradlew idea # For IntelliJ Idea
    ./gradlew eclipse # For Eclipse


## Gradle
To get a list of available commands, use `./gradlew tasks`.


## Configuration
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


## Running the Tests
If you want to run the tests using for example `./gradlew test`.


## Adding the license to new files
We need to have our license at the start of every file. This can be done manually or automatically.

### Automatically (recommended)
The gradle plugin we use (`com.github.hierynomus.license`) introduces two tasks:

    1. license : Checks for header consistency.
    2. licenseFormat : Applies the header (`LICENSE.header`) in files missing it
    
*Yes, there are more than two tasks introduced (6 to be precise). The ones that additionally include the `Main` or
`Test` part in their name. These however just cover the respective source set and not all source sets. Thus, you most
probably do not need them.*

### Manually
Take the license from one of the existing files and copy-paste it over to the new file. Make sure that there is no
newline between the license and the `package ...` part.
