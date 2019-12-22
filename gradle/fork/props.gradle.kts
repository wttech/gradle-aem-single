import com.cognifide.gradle.aem.common.instance.local.Source
import com.neva.gradle.fork.ForkExtension

configure<ForkExtension> {
    properties {
        define(mapOf(
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
                    description = "Controls how instances will be created (from scratch, backup or automatically determined)"
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
}
