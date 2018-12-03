import com.neva.gradle.fork.ForkTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("com.cognifide.aem.bundle")
    id("com.cognifide.aem.instance")
    id("com.neva.fork")
}

description = "Example"
defaultTasks = listOf(":aemSatisfy", ":aemDeploy", ":aemAwait")

group = "com.company.aem"
version = "1.0.0-SNAPSHOT"

repositories {
    jcenter()
    maven { url = uri("https://repo.adobe.com/nexus/content/groups/public") }
    maven { url = uri("https://dl.bintray.com/neva-dev/maven-public") }
}

aem {
    bundle {
        javaPackage = "com.company.aem.example.core"
        category = "example"
        vendor = "Company"
    }
}

dependencies {
    compileOnly(group = "org.osgi", name = "osgi.cmpn", version = "6.0.0")
    compileOnly(group = "javax.servlet", name = "servlet-api", version = "2.5")
    compileOnly(group = "javax.jcr", name = "jcr", version = "2.0")
    compileOnly(group = "org.slf4j", name = "slf4j-api", version = "1.7.21")
    compileOnly(group = "org.apache.geronimo.specs", name = "geronimo-atinject_1.0_spec", version = "1.0")
    compileOnly(group = "org.apache.sling", name = "org.apache.sling.api", version = "2.16.4")
    compileOnly(group = "org.apache.sling", name = "org.apache.sling.jcr.api", version = "2.4.0")
    compileOnly(group = "org.apache.sling", name = "org.apache.sling.models.api", version = "1.3.6")
    compileOnly(group = "com.google.code.gson", name = "gson", version = "2.8.1")
    compileOnly(group = "joda-time", name = "joda-time", version = "2.9.1")

    compileOnly(group = "com.adobe.aem", name = "uber-jar", version = "6.4.0", classifier = "obfuscated-apis")
}

tasks {
    withType<Test>().configureEach {
        failFast = true
        useJUnitPlatform()
        testLogging {
            events = setOf(TestLogEvent.FAILED)
            exceptionFormat = TestExceptionFormat.SHORT
        }

        dependencies {
            "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.3.2")
            "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:5.3.2")
            "testImplementation"("io.wcm:io.wcm.testing.aem-mock.junit5:2.3.2")
        }
    }

    named<ForkTask>(ForkTask.NAME).configure {
        config {
            cloneFiles()
            moveFiles(mapOf(
                    "/com/company/aem/example" to "/{{projectGroup|substitute(\".\", \"/\")}}/{{projectName}}",
                    "/Example" to "/{{projectLabel}}",
                    "/example" to "/{{projectName}}"
            ))
            replaceContents(mapOf(
                    "com.company.aem.example" to "{{projectGroup}}.{{projectName}}",
                    "com.company.aem" to "{{projectGroup}}",
                    "Example" to "{{projectLabel}}",
                    "example" to "{{projectName}}"
            ))
        }
    }
}
