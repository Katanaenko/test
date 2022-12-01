plugins {
	`java-library`
	idea
	id("org.springframework.boot") version "2.5.1"
	id("io.spring.dependency-management") version "1.1.0"
	`maven-publish`
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenLocal()  // <- Here
	mavenCentral()
}
java {
	sourceCompatibility = JavaVersion.VERSION_15
	targetCompatibility = JavaVersion.VERSION_15
}
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("ru.sberbank.pprb.sbbol.digitalapi:digitalapifiles-services-spring-boot-starter:unspecified")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
