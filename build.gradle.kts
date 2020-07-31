val kotlinVersion: String by project
val ktorVersion = "1.3.2"
val kxCoroutinesVersion = "1.3.4"
val kxSerializationVersion = "0.20.0"

plugins {
    kotlin("multiplatform") version "1.3.70"
    kotlin("plugin.serialization") version "1.3.70"
    id("maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

version = "0.0.1-SNAPSHOT"
group = "com.opennms.cloud"

kotlin {
    jvm()
    js {
        configure(setOf(compilations["main"], compilations["test"])) {
            compileKotlinTask.kotlinOptions {
                sourceMapEmbedSources = "always"
                sourceMap = true
                moduleKind = "commonjs"
            }
        }
        nodejs {
            testTask {
                useMocha()
            }
        }
    }

    sourceSets {
        // Common
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$kxSerializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlinVersion")
            }
        }

        // JVM
        val jvmMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$kxCoroutinesVersion")
                implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kxSerializationVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
            }
        }

        // JavaScript
        val jsMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib-js:$kotlinVersion")
                implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$kxCoroutinesVersion")
                implementation("io.ktor:ktor-client-serialization-js:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$kxSerializationVersion")

                // Note these are included as a workaround for missing JS dependencies in KTOR, known bug see:
                // https://youtrack.jetbrains.com/issue/KT-30619, https://github.com/ktorio/ktor/issues/1822
                implementation(npm("text-encoding"))
                implementation(npm("abort-controller"))
                implementation(npm("node-fetch"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-js:$kotlinVersion")
            }
        }
    }
}
