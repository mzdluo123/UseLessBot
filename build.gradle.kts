import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
}

group = "win.rainchan"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11


repositories {
	mavenCentral()
	maven("https://jitpack.io")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.5.3")
	implementation("com.github.mzdluo123:mirai-spring-boot-starter:1.5.2")

	implementation("org.dizitart:nitrite:3.4.3")
	implementation("org.dizitart:potassium-nitrite:3.4.3")
//	implementation(platform("org.dizitart:nitrite-bom:4.0.0-SNAPSHOT"))
//
//	implementation ("org.dizitart:nitrite")
//	implementation ("org.dizitart:nitrite-mvstore-adapter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
