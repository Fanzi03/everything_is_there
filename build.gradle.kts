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
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	//optimization
	compileOnly("org.projectlombok:lombok:$lombokVersion")
	annotationProcessor("org.projectlombok:lombok:$lombokVersion")
	testCompileOnly("org.projectlombok:lombok:$lombokVersion")
	testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

	//DB
	implementation("org.postgresql:postgresql:42.7.7")
	implementation("org.liquibase:liquibase-core:4.33.0")
	testImplementation("org.testcontainers:postgresql:$testcontainersVersion")

	//test
	testImplementation("org.testng:testng:7.11.0")
	testImplementation(libs.junit.jupiter)
	testImplementation("io.rest-assured:rest-assured:5.5.6")
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
