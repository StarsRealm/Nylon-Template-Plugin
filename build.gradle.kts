plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.7.1"
  id("xyz.jpenilla.run-paper") version "2.2.4"
  id("maven-publish")
}

group = "com.starsrealm.testplugin"
version = "1.0.0-SNAPSHOT"
description = "Template plugin for Nylon Dev"

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
  maven {
    name = "AliYun-Release"
    url = uri("https://packages.aliyun.com/maven/repository/2421751-release-ZmwRAc/")
    credentials {
      username = project.findProperty("aliyun.package.user") as String? ?: System.getenv("ALY_USER")
      password = project.findProperty("aliyun.package.password") as String? ?: System.getenv("ALY_PASSWORD")
    }
  }
  maven {
    name = "AliYun-Snapshot"
    url = uri("https://packages.aliyun.com/maven/repository/2421751-snapshot-i7Aufp/")
    credentials {
      username = project.findProperty("aliyun.package.user") as String? ?: System.getenv("ALY_USER")
      password = project.findProperty("aliyun.package.password") as String? ?: System.getenv("ALY_PASSWORD")
    }
  }
}

dependencies {
  paperweight.devBundle("com.starsrealm.nylon", "1.21-R0.0.1-STARSREALM-SNAPSHOT")
}

tasks {
  compileJava {
    options.release = 21
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
      "apiVersion" to "1.21"
    )
    inputs.properties(props)
    filesMatching("paper-plugin.yml") {
      expand(props)
    }
  }
}

publishing {
  repositories {
    maven {
      name = "AliYun-Release"
      url = uri("https://packages.aliyun.com/maven/repository/2421751-release-ZmwRAc/")
      credentials {
        username = project.findProperty("aliyun.package.user") as String? ?: System.getenv("ALY_USER")
        password = project.findProperty("aliyun.package.password") as String? ?: System.getenv("ALY_PASSWORD")
      }
    }
    maven {
      name = "AliYun-Snapshot"
      url = uri("https://packages.aliyun.com/maven/repository/2421751-snapshot-i7Aufp/")
      credentials {
        username = project.findProperty("aliyun.package.user") as String? ?: System.getenv("ALY_USER")
        password = project.findProperty("aliyun.package.password") as String? ?: System.getenv("ALY_PASSWORD")
      }
    }
  }
  publications {
    create("gpr", MavenPublication::class.java) {
      from(components.getByName("java"))
      groupId = project.group.toString()
    }
  }
}
