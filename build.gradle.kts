plugins {
    id("com.neva.fork")
}

apply(from = "gradle/fork/fork.gradle.kts")
apply(from = "gradle/fork/props.gradle.kts")

description = "Example - Root"
defaultTasks(":aem:instanceSetup", ":aem:packageDeploy")
