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
    testImplementation("junit:junit:4.12")

    compileOnly("org.osgi:osgi.cmpn:6.0.0")
    compileOnly("javax.servlet:servlet-api:2.5")
    compileOnly("javax.jcr:jcr:2.0")
    compileOnly("org.slf4j:slf4j-api:1.7.21")
    compileOnly("org.apache.geronimo.specs:geronimo-atinject_1.0_spec:1.0")
    compileOnly("org.apache.sling:org.apache.sling.api:2.16.4")
    compileOnly("org.apache.sling:org.apache.sling.jcr.api:2.4.0")
    compileOnly("org.apache.sling:org.apache.sling.models.api:1.3.6")
    compileOnly("com.google.code.gson:gson:2.8.1")
    compileOnly("com.adobe.aem", "uber-jar", "6.4.0", classifier = "obfuscated-apis")
    compileOnly("joda-time:joda-time:2.9.1")
}

tasks {
    named<ForkTask>(ForkTask.NAME).configure {
        config {
            cloneFiles()
            moveFiles(mapOf(
                    "/com/company/aem/example" to "/{{projectGroup|substitute('.', '/')}}/{{projectName}}",
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
