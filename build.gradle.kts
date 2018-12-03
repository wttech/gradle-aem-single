import com.neva.gradle.fork.ForkTask

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
    testImplementation(group = "junit", name = "junit", version = "4.12")

    compileOnly(group = "org.osgi", name = "osgi.cmpn", version = "6.0.0")
    compileOnly(group = "javax.servlet", name = "servlet-api", version = "2.5")
    compileOnly(group = "javax.jcr", name = "jcr", version = "2.0")
    compileOnly(group = "org.slf4j", name = "slf4j-api", version = "1.7.21")
    compileOnly(group = "org.apache.geronimo.specs", name = "geronimo-atinject_1.0_spec", version = "1.0")
    compileOnly(group = "org.apache.sling", name = "org.apache.sling.api", version = "2.16.4")
    compileOnly(group = "org.apache.sling", name = "org.apache.sling.jcr.api", version = "2.4.0")
    compileOnly(group = "org.apache.sling", name = "org.apache.sling.models.api", version = "1.3.6")
    compileOnly(group = "com.google.code.gson", name = "gson", version = "2.8.1")
    compileOnly(group = "com.adobe.aem", name = "uber-jar", version = "6.4.0", classifier = "obfuscated-apis")
    compileOnly(group = "joda-time", name = "joda-time", version = "2.9.1")
}

tasks.named<ForkTask>("fork").configure {
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
