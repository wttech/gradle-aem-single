plugins {
    id("com.neva.fork")
    id("com.cognifide.aem.instance.local")
    id("com.cognifide.aem.bundle")
    id("com.cognifide.aem.package")
    id("com.cognifide.aem.package.sync")
    id("net.researchgate.release")
    id("maven-publish")
}

apply(from = "gradle/fork/fork.gradle.kts")
apply(from = "gradle/fork/props.gradle.kts")

defaultTasks(":instanceSetup", ":packageDeploy")
description = "Example"
group = "com.company"

repositories {
    jcenter()
    maven("https://repo.adobe.com/nexus/content/groups/public")
    maven("https://dl.bintray.com/acs/releases")
}

dependencies {
    compileOnly("org.osgi:osgi.cmpn:6.0.0")
    compileOnly("org.osgi:org.osgi.core:6.0.0")
    compileOnly("javax.servlet:servlet-api:2.5")
    compileOnly("javax.servlet:jsp-api:2.0")
    compileOnly("javax.jcr:jcr:2.0")
    compileOnly("org.slf4j:slf4j-api:1.7.25")
    compileOnly("org.apache.geronimo.specs:geronimo-atinject_1.0_spec:1.0")
    compileOnly("org.apache.sling:org.apache.sling.api:2.16.4")
    compileOnly("org.apache.sling:org.apache.sling.jcr.api:2.4.0")
    compileOnly("org.apache.sling:org.apache.sling.models.api:1.3.6")
    compileOnly("org.apache.sling:org.apache.sling.settings:1.3.8")
    compileOnly("com.google.guava:guava:15.0")
    compileOnly("com.google.code.gson:gson:2.8.2")
    compileOnly("joda-time:joda-time:2.9.1")

    compileOnly("com.adobe.aem:uber-jar:6.5.0:apis")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")
    testImplementation("io.wcm:io.wcm.testing.aem-mock.junit5:2.3.2")
}

aem {
    `package` {
        validator {
            base("com.adobe.acs:acs-aem-commons-oakpal-checks:4.3.4")
        }
    }
    instance {
        provisioner {
            enableCrxDe()
            deployPackage("https://github.com/Cognifide/aem-stubs/releases/download/1.0.4/stubs-all-1.0.4.zip")
        }
    }
}

tasks {
    test {
        failFast = true
        useJUnitPlatform()
        testLogging.showStandardStreams = true
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenAem") {
            artifact(tasks.packageCompose)
        }
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

