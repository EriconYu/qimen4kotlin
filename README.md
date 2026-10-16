# qimen4kotlin

Kotlin/JVM 原生奇门遁甲排盘 SDK，历法使用 `tyme4j`。要求 Java 11+，运行时不依赖 Node.js 或其他语言。

```kotlin
val input = mapOf<String, Any>(
    "year" to 2026, "month" to 4, "day" to 10, "hour" to 14, "minute" to 0,
    "timezone" to "Asia/Shanghai",
)
val chart = Qimen.calculate(input)
val canonical = Qimen.canonical(input)
```

`calculate` 返回完整结构化盘面，`canonical` 返回规范 JSON。当前算法版本为 `qimen-zhuanpan-chaibu-v1`。
