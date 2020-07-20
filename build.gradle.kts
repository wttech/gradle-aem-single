plugins {
    id("com.neva.fork")
    id("com.cognifide.sling.instance.local")
    id("com.cognifide.sling.bundle")
    id("com.cognifide.sling.package")
    id("com.cognifide.sling.package.sync")
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

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")
}

sling {
    instance {
        provisioner {
            deployPackage("com.neva.felix:search-webconsole-plugin:1.3.0")
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
        create<MavenPublication>("mavenSling") {
            artifact(common.publicationArtifact(tasks.packageCompose))
        }
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

