plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.5.11"
  id("xyz.jpenilla.run-paper") version "2.2.3"
}

group = "com.starsrealm"
version = "1.0.0-SNAPSHOT"
description = "Template plugin for Nylon Dev"

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}


repositories {
  maven {
    url = uri("https://maven.pkg.github.com/StarsRealm/Packages")
    credentials {
      username = findProperty("gpr.user") as? String ?: System.getenv("USERNAME")
      password = findProperty("gpr.key") as? String ?: System.getenv("TOKEN")
    }
  }
}

dependencies {
  paperweight.devBundle("com.starsrealm.nylon", "1.20.4-R0.1-SNAPSHOT")
}

tasks {
  assemble {
    dependsOn(reobfJar)
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release.set(17)
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
  processResources {
    filteringCharset = Charsets.UTF_8.name()
    val props = mapOf(
      "name" to project.name,
      "version" to project.version,
      "description" to project.description,
      "apiVersion" to "1.20"
    )
    inputs.properties(props)
    filesMatching("paper-plugin.yml") {
      expand(props)
    }
  }

}
