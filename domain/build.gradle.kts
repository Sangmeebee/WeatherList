plugins {
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    HiltConfig.run {
        implementation(CORE)
        kapt(COMPILER)
    }

    RoomConfig.run {
        implementation(ROOM_COMMON)
    }

    implementation(CoroutineConfig.CORE)

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(TRUTH)
        testImplementation(COROUTINE_TEST)
        testImplementation(MOCKK)
    }
}
