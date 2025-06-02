group = "com.brocode.apply"
version = "1.0-SNAPSHOT"

tasks.register("wakeup") {
    group = "other"
    description = "Example void task to be depend on"

    doLast({
        println("Yaaaaaaawn...")
    })
}

tasks.register("hello-gradle-with-kotlin") {
    dependsOn("wakeup")

    group = "onboarding"
    description = "Welcoming tasks that lists interesting topics"

    finalizedBy("tasks")

    doLast({
        println("Hello from Kotlin-powered gradle script!")
    })
}