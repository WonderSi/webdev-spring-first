val baseUrl: String by env
val path = "greeting"

println("1: GET /$path")
GET("$baseUrl/$path") {
    accept("application/json")
} then {
    if (code == 200) {
        println("1) ACCEPTED: Status 200, Response: ${body?.string()}")
    } else {
        println("1) FAILED: Expected 200, got $code")
    }
}

println("2: POST /$path")
val userId by POST("$baseUrl/$path") {
    contentType("application/json")
    body("""
        {
            "name": "Ivan",
            "surname": "Ivanov"
        }
    """.trimIndent())
} then {
    if (code == 200) {
        val responseStr = body?.string() ?: ""
        println("2) ACCEPTED: Status 200, Response: $responseStr")
        val idPattern = """"id"\s*:\s*"([^"]+)"""".toRegex()
        idPattern.find(responseStr)?.groupValues?.get(1) ?: ""
    } else {
        println("2) FAILED: Expected 200, got $code")
        ""
    }
}

println("3: GET /$path?id=$userId")
GET("$baseUrl/$path?id=$userId") {
    accept("application/json")
} then {
    if (code == 200) {
        println("3) ACCEPTED: Status 200, Response: ${body?.string()}")
    } else {
        println("3) FAILED: Expected 200, got $code")
    }
}

println("4: GET /$path/$userId")
GET("$baseUrl/$path/$userId") {
    accept("application/json")
} then {
    if (code == 200) {
        println("4) ACCEPTED: Status 200, Response: ${body?.string()}")
    } else {
        println("4) FAILED: Expected 200, got $code")
    }
}

println("5: GET (404)")
GET("$baseUrl/$path?id=00000000-0000-0000-0000-000000000000") {
    accept("application/json")
} then {
    if (code == 404) {
        println("5) ACCEPTED: Got 404 as expected")
    } else {
        println("5) FAILED: Expected 404, got $code")
    }
}