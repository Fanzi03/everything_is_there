val lombokVersion = "1.18.38"
val springVersion = "3.5.4"
val testcontainersVersion = "1.21.3"

plugins {
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	application
}

repositories {
	mavenCentral()
}

dependencies {

	//Spring
	implementation("org.springframework.boot:spring-boot-starter:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
	implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")
	testImplementation("org.springframework.boot:spring-boot-testcontainers:$springVersion")

	//optimization
	compileOnly("org.projectlombok:lombok:$lombokVersion")
	annotationProcessor("org.projectlombok:lombok:$lombokVersion")
	testCompileOnly("org.projectlombok:lombok:$lombokVersion")
	testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

	//DB
	implementation("org.postgresql:postgresql:42.7.7")
	testImplementation("org.testcontainers:postgresql:$testcontainersVersion")

	//test
	testImplementation(libs.junit.jupiter)
	testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation(libs.guava)
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

application {
	mainClass.set("org.application.App")
}

tasks.jar {
	enabled = false
}

tasks.bootJar {
	enabled = true
	archiveClassifier.set("")
	archiveFileName.set("app.jar")
	mainClass.set("org.application.App")
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
