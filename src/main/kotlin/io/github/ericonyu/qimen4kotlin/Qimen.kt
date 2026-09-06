package io.github.ericonyu.qimen4kotlin

import com.google.gson.Gson
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object Qimen {
    private val gson = Gson()
    private val engine by lazy {
        val path = Files.createTempFile("qimen-engine-", ".cjs")
        javaClass.getResourceAsStream("/engine.cjs")!!.use { Files.copy(it, path, StandardCopyOption.REPLACE_EXISTING) }
        path.toFile().deleteOnExit()
        path
    }

    @Suppress("UNCHECKED_CAST")
    private fun run(input: Map<String, Any>): Map<String, Any> {
        val process = ProcessBuilder("node", engine.toString()).start()
        process.outputStream.bufferedWriter().use { it.write(gson.toJson(input)) }
        val stdout = process.inputStream.bufferedReader().readText()
        val stderr = process.errorStream.bufferedReader().readText()
        val code = process.waitFor()
        val envelope = gson.fromJson(stdout, Map::class.java) as Map<String, Any>
        if (code != 0 || envelope["ok"] != true) error(envelope["error"]?.toString() ?: stderr)
        return envelope
    }

    @Suppress("UNCHECKED_CAST")
    fun calculate(input: Map<String, Any>) = run(input)["data"] as Map<String, Any>

    @Suppress("UNCHECKED_CAST")
    fun canonical(input: Map<String, Any>) = run(input)["canonical"] as Map<String, Any>
}
