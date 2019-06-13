import com.cognifide.gradle.aem.common.instance.local.Source
import com.neva.gradle.fork.ForkExtension

configure<ForkExtension> {
    properties {
        define(mapOf(
                "projectName" to {
                    description = "Artifact 'name' coordinate (lowercase)"
                    validator { lowercased(); alphanumeric() }
                },
                "projectLabel" to { description = "Nice project name (human-readable)" },
                "projectGroup" to {
                    description = "Java package in source code and artifact 'group' coordinate"
                    validator { javaPackage(); notEndsWith("projectName") }
                },
                "instanceAuthorHttpUrl" to {
                    url("http://localhost:4502")
                    optional()
                    description = "URL for accessing AEM author instance"
                },
                "instancePublishHttpUrl" to {
                    url("http://localhost:4503")
                    optional()
                    description = "URL for accessing AEM publish instance"
                },
                "instanceType" to {
                    select("local", "remote")
                    description = "local - instance will be created on local file system\nremote - connecting to remote instance only"
                    controller { toggle(value == "local", "aemInstanceRunModes", "aemInstanceJvmOpts", "aemLocalInstance*") }
                },
                "instanceRunModes" to { text("local,nosamplecontent") },
                "instanceJvmOpts" to { text("-server -Xmx2048m -XX:MaxPermSize=512M -Djava.awt.headless=true") },
                "localInstanceSource" to {
                    description = "Local instance source\nControls how instances will be created (from scratch or backup)"
                    select(Source.values().map { it.name.toLowerCase() }, Source.AUTO.name.toLowerCase())
                },
                "localInstanceQuickstartJarUri" to {
                    description = "Quickstart JAR (cq-quickstart-x.x.x.jar)"
                },
                "localInstanceQuickstartLicenseUri" to {
                    description = "Quickstart license file (license.properties)"
                },
                "localInstanceBackupDownloadUri" to {
                    description = "URL to backup file (SMB/SFTP/HTTP)"
                    optional()
                },
                "localInstanceBackupUploadUri" to {
                    description = "URL to backup directory (SMB/SFTP)"
                    optional()
                }
        ))
    }
    config {
        cloneFiles()
        moveFiles(mapOf(
                "/com/company/aem/example" to "/{{projectGroup|substitute('.', '/')}}/aem/{{projectName}}",
                "/example" to "/{{projectName}}"
        ))
        replaceContents(mapOf(
                "com.company.aem.example" to "{{projectGroup}}.aem.{{projectName}}",
                "com.company.aem" to "{{projectGroup}}.aem",
                "Example" to "{{projectLabel}}",
                "example" to "{{projectName}}"
        ))
    }
}
