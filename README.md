# amos-ss15-proj4
Personalfragebogen 2.0 @ DATEV

# Setup (*long*)
1. Clone the project using `git clone git@github.com:JOBAA/amos-ss15-proj4.git`
2. Change to the project directory using `cd amos-ss15-proj4`
3. Run `./gradlew wrapper` to bootstrap [Gradle](http://gradle.org/), our build tool.

# Setup (*tl;dr*)
    git clone git@github.com:JOBAA/amos-ss15-proj4.git
    cd amos-ss15-proj4
    ./gradlew wrapper

# Gradle
To get a list of available commands, use `./gradlew tasks`.

# Configuration

Configuration is held in [YAML](http://yaml.org/) files. We have a main configuration with application wide
configuration. Then, there is a developer specific configuration file which overrides any configuration that a developer
might have locally. To enable this, just rename the file:

    # Linux
    mv config/application-developer.yml{.sample,}

    # Windows
    rename-item -path config/application-developer.yml.sample -newname application-developer.yml

This developer specific configuration file is ignored in our .gitignore and should not be committed to version control,
since most probably it contains sensitive information.
