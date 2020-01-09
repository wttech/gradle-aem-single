import com.neva.gradle.fork.ForkExtension

configure<ForkExtension> {
    properties {
        define(mapOf(
                "projectName" to {
                    description = "Artifact 'name' coordinate (lowercase)"
                    validator { lowercased(); alphanumeric() }
                    controller { other("targetPath").value = File(File(other("sourcePath").value).parentFile, value).toString() }
                    defaultValue = "example"
                },
                "projectLabel" to {
                    description = "Nice project name (human-readable)"
                    defaultValue = "Example"
                },
                "projectGroup" to {
                    description = "Java package in source code and artifact 'group' coordinate"
                    validator { javaPackage(); notEndsWith("projectName") }
                    defaultValue = "com.company.aem"
                },
                "sourcePath" to { enabled = false },
                "targetPath" to { enabled = false }
        ))
    }
    config {
        cloneFiles()
        moveFiles(mapOf(
                "/com/company/example/aem" to "/{{projectGroup|substitute('.', '/')}}/{{projectName}}/aem",
                "/example" to "/{{projectName}}"
        ))
        replaceContents(mapOf(
                "com.company.example.aem" to "{{projectGroup}}.{{projectName}}.aem",
                "com.company.example" to "{{projectGroup}}.{{projectName}}",
                "com.company" to "{{projectGroup}}",
                "Example" to "{{projectLabel}}",
                "example" to "{{projectName}}"
        ))
        copyTemplateFile("README.MD")
        removeFiles(listOf(
                "LICENSE",
                "azure-pipelines.yml",
                "gh-md-toc",
                "docs/*",
                "gradle/fork/fork.gradle.kts",
                "gradle/fork/README.MD.peb"
        ))
        removeTexts(listOf(
                """apply(from = "gradle/fork/fork.gradle.kts")""" + "\n"
        ))
    }
}
