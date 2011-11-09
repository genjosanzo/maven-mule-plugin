# Maven Mule Plugin#

The maven-mule-plugin allows packaging a project as a Mule 3 application archive. To enable it, declare the packaging type of your Maven project as **mule** and put the maven-mule-plugin into the list of build plugins. The plugin is available on the Maven central repository so you don't have to add any special repositories to your `pom.xml` to use the plugin.

Example:

    <project>
        ....
        <packaging>mule</packaging>

        <build>
            <plugins>
                <plugin>
                    <groupId>org.mule.tools</groupId>
                    <artifactId>maven-mule-plugin</artifactId>
                    <version>1.7</version>
                    <extensions>true</extensions>
                </plugin>
            </plugins>
        </build>
    </project>

## Managing dependencies ##

The plugin comes with defaults that are designed to make dependency management as painless as possible for users. By default the following behaviour applies:

* all Mule dependencies (i.e. those with a groupId of `org.mule`, `com.mulesource.muleesb` and `com.mulesoft.muleesb`) are excluded. Their transitive dependencies are excluded as well. All of these dependencies will be present in the Mule distribution so there is no need to package them with your application. Set the **excludeMuleDependencies** config switch to `false` if you do not want this behaviour.

* all other dependencies in scope *compile* or *runtime* will be packaged.

There are three ways to exclude dependencies you do not want to package inside your Mule application:

1. use the *provided* scope when declaring that dependency
1. specify exclusions (see below)
1. specify inclusions (see below)

### Exclusions ###

Exclusions are specified analogous to Maven's dependency exclusions, i.e. an exclusion element has a `groupId` and an `artifactId` sub-element.

    <code>
        <plugin>
            <groupId>org.mule.tools</groupId>
            <artifactId>maven-mule-plugin</artifactId>
            <version>1.7</version>
            <extensions>true</extensions>
            <configuration>
                <exclusions>
                    <exclusion>
                        <groupId>org.mule</groupId>
                        <artifactId>mule-core</artifactId>
                    </exclusion>
                </exclusions>
            </configuration>
        </plugin>
    </code>

### Inclusions ###

Inclusion elements mimic the exclusion elements, i.e. an inclusion element has a `groupId` and an `artifactId` sub-element.

    <plugin>
        <groupId>org.mule.tools</groupId>
        <artifactId>maven-mule-plugin</artifactId>
        <version>1.7</version>
        <extensions>true</extensions>
        <configuration>
            <inclusions>
                <inclusion>
                    <groupId>org.mule</groupId>
                    <artifactId>mule-core</artifactId>
                </inclusion>
            </inclusions>
        </configuration>
    </plugin>

## Options##

|Property|Description|Default|Since Version|
|:-------|:----------|:------|:------------|
|appDirectory|Directory containing the app resources.|${basedir}/src/main/app|1.0|
|archiveClasses|Whether a JAR file will be created for the classes in the app. Using this optional configuration parameter will make the generated classes to be archived into a jar file and the classes directory will then be excluded from the app.|false|1.0|
|classesDirectory|Directory containing the classes|${project.build.outputDirectory}|1.0|
|copyToAppsDirectory|Copy the application zip to `$MULE_HOME/apps` as part of the install lifecycle phase. Either set the MULE_HOME environment variable or specify -Dmule.home when invoking Mule.|false|1.6|
|excludeMuleDependencies|Exclude all artifacts with Mule groupIds|true|1.4|
|exclusions| List of exclusion elements (having groupId and artifactId children) to exclude from the application archive.||1.2|
|filterAppDirectory|Apply Maven resource filtering to all files in the appDirectory.|false|1.7|
|finalName|Name of the generated Mule App.|${project.build.finalName}|1.0|
|inclusions| List of inclusion elements (having groupId and artifactId children) to include into the application archive. This includes transitive dependencies of the included artifact.||1.5|
|outputDirectory|Directory containing the generated Mule App.|${project.build.directory}|1.0|
