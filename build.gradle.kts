plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral() // Główne repozytorium Maven
    maven( "file:D:/Users/jbull/.jdks/GDX/libgdx")
    // maven("https://oss.sonatype.org/content/repositories/snapshots/") // Snapshots dla libGD
}

dependencies {
    implementation("com.badlogicgames.gdx:gdx:1.13.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}

tasks.test {
    useJUnitPlatform()
}