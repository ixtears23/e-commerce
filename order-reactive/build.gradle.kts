val jasyncMysqlVersion = "2.2.0"
val r2dbcPoolVersion = "1.0.2.RELEASE"

dependencies {
    implementation(project(":core"))
    implementation(project(":core-order"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.github.jasync-sql:jasync-mysql:$jasyncMysqlVersion")
    implementation("io.r2dbc:r2dbc-pool:${r2dbcPoolVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}