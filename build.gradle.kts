plugins {
    kotlin("multiplatform") version "1.3.72"
    id("maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
}

version = "0.0.1-SNAPSHOT"
group = "com.opennms.cloud"

val kotlinVersion: String by project
val ktorVersion = "1.3.1"
val maasPortalVersion = "1.0.0-SNAPSHOT"
val coroutinesVersion = "1.3.2"

kotlin {
    jvm()
    js {
        browser {
            testTask {
                useMocha()
            }
        }
    }

    sourceSets {
        // Common
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-client-core:$ktorVersion")

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
            }
        }
        val jvmTest by getting {
            dependencies {
                // TODO: Switch this to 'test-junit5' but there is an issue with finding the tests
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
//                implementation("io.ktor:ktor-client-serialization-js:$ktorVersion")

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
