plugins {
    kotlin("multiplatform") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.70"
    id("maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
}

version = "0.0.1-SNAPSHOT"
group = "com.opennms.cloud"

val kotlinVersion: String by project
val ktorVersion = "1.3.0"
val maasPortalVersion = "1.0.0-SNAPSHOT"
val coroutinesVersion = "1.3.2"
val serializationVersion = "0.20.0"

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

    targets.all {
        compilations.all {
            kotlinOptions.freeCompilerArgs += listOf(
                    "-Xopt-in=kotlin.RequiresOptIn",
                    "-Xopt-in=kotlin.OptIn"
            )
        }
    }

//    sourceSets.all {
//        languageSettings.apply {
//            useExperimentalAnnotation("kotlinx.serialization.UnstableDefault")
//        }
//    }

    sourceSets {
        // Common
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        // JVM
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")
                implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.hamcrest:hamcrest-library:2.1")
            }
        }

        // JavaScript
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
                implementation("io.ktor:ktor-client-serialization-js:$ktorVersion")
                // TODO: looks like this isn't needed
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serializationVersion")

                // Note these are included as a workaround for missing JS dependencies in KTOR, known bug see:
                // https://youtrack.jetbrains.com/issue/KT-30619, https://github.com/ktorio/ktor/issues/1822
                implementation(npm("text-encoding"))
                implementation(npm("abort-controller"))
                implementation(npm("node-fetch"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
            }
        }
    }
}
