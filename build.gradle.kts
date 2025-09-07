val lombokVersion = "1.18.38"
val springVersion = "3.5.4"
val testcontainersVersion = "1.21.3"

plugins {
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	application
	id("info.solidsoft.pitest") version "1.19.0-rc.1"
}

repositories {
	mavenCentral()
}

dependencies {

	//Spring
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
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
	testImplementation("com.arcmutate:arcmutate-spring:1.1.1")
	testImplementation("org.testcontainers:postgresql:$testcontainersVersion")

	//test
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


pitest {
	    junit5PluginVersion.set("1.2.3")
	    targetClasses.set(listOf("org.application.*"))
	    excludedClasses.set(listOf(
		"org.application.App*",
		"org.application.config.*",
		"org.application.entity.*",
		"org.application.**.*Exception",
		"org.application.**.*DataTransferObject*"
	    ))

	    excludedTestClasses.set(listOf(
		"**.*IntegrationTest*",
		"**/BaseIntegrationTest*"
	    ))

	    threads.set(4) 

	    mutationThreshold.set(60)
	    coverageThreshold.set(80)
	    outputFormats.set(listOf("XML", "HTML"))
	    timestampedReports.set(false)

}

