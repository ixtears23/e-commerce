val jasyncMysqlVersion = "2.2.0"

dependencies {
    implementation(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.github.jasync-sql:jasync-mysql:$jasyncMysqlVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}