plugins {
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    mavenLocal()
}

group = "com.opennms.cloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.opennms.cloud:oaas_client:0.0.1-SNAPSHOT")
}

application {
    mainClassName = "com.opennms.cloud.demo.Demo"
}
