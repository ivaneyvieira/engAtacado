import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val karibudsl_version = "0.7.0"

plugins {
  kotlin("jvm") version "1.3.60"
  id("org.gretty") version "2.3.1"
  id("com.devsoap.vaadin-flow") version "1.2"
}

repositories {
  mavenCentral()
  maven {
    url = uri("https://maven.vaadin.com/vaadin-addons")
  }
}


vaadin {
  version = "14.0.12"
  // submitStatistics=true
}
//vaadin.productionMode = true
//vaadin.submitStatistics=true
defaultTasks("clean", "build")

group = "engAtacado"
version = "1.0"

apply(plugin = "war")
apply(plugin = "kotlin")

repositories {
  mavenCentral()
  maven {
    url = uri("https://maven.vaadin.com/vaadin-addons")
  }
}


gretty {
  contextPath = "/"
  servletContainer = "jetty9.4"
}
val staging by configurations.creating

dependencies {
  // Karibu-DSL dependency
  compile("com.github.mvysny.karibudsl:karibu-dsl-v10:$karibudsl_version")
  // Vaadin 14
  compile("com.vaadin:vaadin-core:${vaadin.version}")
  compile("com.vaadin:flow-server-compatibility-mode:2.0.10")
  compile("javax.servlet:javax.servlet-api:3.1.0")

  compile("org.claspina:confirm-dialog:2.0.0")

  compile("ch.qos.logback:logback-classic:1.2.3")
  compile("org.slf4j:slf4j-api:1.7.25")
  compile("org.slf4j:jul-to-slf4j:1.7.25")

  compile("org.sql2o:sql2o:1.6.0")
  compile("mysql:mysql-connector-java:5.1.48")
  compile("com.zaxxer:HikariCP:3.4.1")
  compile("org.imgscalr:imgscalr-lib:4.2")
  // logging
  // currently we are logging through the SLF4J API to SLF4J-Simple. See src/main/resources/simplelogger.properties file for the logger configuration
  compile("org.slf4j:slf4j-simple:1.7.28")

  compile(kotlin("stdlib-jdk8"))
  //compile("org.jetbrains.kotlin:kotlin-reflect")
  // test support
  testCompile("com.github.mvysny.kaributesting:karibu-testing-v10:1.1.16")
  testCompile("com.github.mvysny.dynatest:dynatest-engine:0.15")
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

