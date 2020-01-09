import com.cognifide.gradle.aem.common.instance.local.Source
import com.neva.gradle.fork.ForkExtension

configure<ForkExtension> {
    properties {
        define("Instance type", mapOf(
                "instanceType" to {
                    label = "Type"
                    select("local", "remote")
                    description = "local - instance will be created on local file system\nremote - connecting to remote instance only"
                    controller { toggle(value == "local", "instanceRunModes", "instanceJvmOpts", "localInstance*") }
                },
                "instanceAuthorHttpUrl" to {
                    label = "Author HTTP URL"
                    url("http://localhost:4502")
                    optional()
                    description = "URL for accessing AEM author instance"
                },
                "instancePublishHttpUrl" to {
                    label = "Publish HTTP URL"
                    url("http://localhost:4503")
                    optional()
                    description = "URL for accessing AEM publish instance"
                }
        ))

        define("Local instance", mapOf(
                "localInstanceSource" to {
                    label = "Source"
                    description = "Controls how instances will be created (from scratch, backup or automatically determined)"
                    select(Source.values().map { it.name.toLowerCase() }, Source.AUTO.name.toLowerCase())
                },
                "localInstanceQuickstartJarUri" to {
                    label = "Quickstart URI"
                    description = "Quickstart JAR (cq-quickstart-x.x.x.jar)"
                },
                "localInstanceQuickstartLicenseUri" to {
                    label = "Quickstart License URI"
                    description = "Quickstart license file (license.properties)"
                },
                "localInstanceBackupDownloadUri" to {
                    label = "Backup Download URI"
                    description = "URL (SMB/SFTP/HTTP) to backup file"
                    optional()
                },
                "localInstanceBackupUploadUri" to {
                    label = "Backup Upload URI"
                    description = "URL (SMB/SFTP) to directory containing backup files"
                    optional()
                },
                "instanceRunModes" to {
                    label = "Run Modes"
                    text("local,nosamplecontent")
                },
                "instanceJvmOpts" to {
                    label = "JVM Options"
                    text("-server -Xmx2048m -XX:MaxPermSize=512M -Djava.awt.headless=true")
                }
        ))

        define("File transfer", mapOf(
                "companyUser" to {
                    description = "User authorized to access AEM files"
                    defaultValue = System.getProperty("user.name").orEmpty()
                    optional()
                },
                "companyPassword" to {
                    description = "Password for user authorized to access AEM files"
                    optional()
                },
                "companyDomain" to {
                    description = "Needed only when accessing AEM files over SMB"
                    defaultValue = System.getenv("USERDOMAIN").orEmpty()
                    optional()
                }
        ))
    }
}
