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

kotlin {
    jvm()
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
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
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
