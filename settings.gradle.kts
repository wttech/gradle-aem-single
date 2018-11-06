// Plugins configuration

pluginManagement {
    repositories {
        mavenLocal()
        jcenter()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("http://dl.bintray.com/cognifide/maven-public") }
        maven { url = uri("https://dl.bintray.com/neva-dev/maven-public") }
    }
    resolutionStrategy {
        eachPlugin {
            when {
                requested.id.namespace == "com.cognifide.aem" -> useModule("com.cognifide.gradle:aem-plugin:6.0.0")
                requested.id.id == "com.neva.fork" -> useModule("com.neva.gradle:fork-plugin:1.0.7")
            }
        }
    }
}

// Project structure

rootProject.name = "example"
