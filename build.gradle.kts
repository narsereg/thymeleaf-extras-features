plugins {
    kotlin("jvm") version "1.9.24"
    id("org.jetbrains.dokka") version "1.9.20"
    id("org.jmailen.kotlinter") version "4.4.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
}

group = "org.narsereg"
version = "0.2.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    implementation("org.thymeleaf:thymeleaf:3.+")
    implementation("org.togglz:togglz-core:4.+")
    testImplementation("io.mockk:mockk:1.+")
    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.+")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                name.set("Thymeleaf Feature Toggles Extension")
                description.set("Extension to the thymeleaf which allows to use togglz library in the templates")
                url.set("https://github.com/narsereg/thymeleaf-extras-features")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/narsereg/thymeleaf-extras-features.git")
                    developerConnection.set("scm:git:https://github.com/narsereg/thymeleaf-extras-features.git")
                    url.set("https://github.com/narsereg/thymeleaf-extras-features.git")
                }
                developers {
                    developer {
                        id.set("narsereg")
                        name.set("Dmitrii Danileiko")
                        email.set("narsereg@mail.ru")
                    }
                }
            }
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.javaParameters = true
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    val sourceJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    val dokkaHtml by getting(org.jetbrains.dokka.gradle.DokkaTask::class)

    val javadocJar: TaskProvider<Jar> by registering(Jar::class) {
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
        from(dokkaHtml.outputDirectory)
    }

    artifacts {
        archives(sourceJar)
        archives(javadocJar)
        archives(jar)
    }
}
