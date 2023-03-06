plugins {
    java
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.google.cloud.tools.jib") version "3.3.1"
}

group = "dev.tpcoder"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_19
java.targetCompatibility = JavaVersion.VERSION_19

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    val preview = "--enable-preview"
    withType<org.springframework.boot.gradle.tasks.run.BootRun> {
        jvmArgs = mutableListOf(preview)
    }
    withType<JavaExec> {
        jvmArgs = mutableListOf(preview)
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add(preview)
        options.compilerArgs.add("-Xlint:preview")
    }
}

jib {
    from {
        image = "amazoncorretto:19.0.2-al2"
    }
    to {
        image = "marttp/perf-vt-main"
    }
    container {
        jvmFlags = mutableListOf("--enable-preview", "-XX:+UseZGC", "-Xlog:gc*:file=/tmp/gc.log")
    }
}