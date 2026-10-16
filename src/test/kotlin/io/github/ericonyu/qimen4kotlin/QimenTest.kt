package io.github.ericonyu.qimen4kotlin

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class QimenTest {
    private val input = mapOf<String, Any>("year" to 2026, "month" to 4, "day" to 10, "hour" to 14, "timezone" to "Asia/Shanghai")

    @Test fun goldenChartAndCanonical() {
        val result = Qimen.calculate(input)
        assertEquals(9, (result["palaces"] as List<*>).size)
        assertEquals("qimen-zhuanpan-chaibu-v1", result["algorithmVersion"])
        assertEquals(1, result["juNumber"])
        assertEquals(8, (result["zhiShi"] as Map<*, *>)["palace"])
        assertTrue(Qimen.canonical(input).isNotEmpty())
    }

    @Test fun invalidDate() {
        assertFails { Qimen.calculate(mapOf("year" to 2026, "month" to 2, "day" to 30, "hour" to 12)) }
    }

    @Test fun lateZiUsesNextDay() {
        val first=Qimen.calculate(mapOf("year" to 2026,"month" to 4,"day" to 10,"hour" to 23,"minute" to 30))
        val second=Qimen.calculate(mapOf("year" to 2026,"month" to 4,"day" to 11,"hour" to 0,"minute" to 30))
        assertEquals(first["siZhu"],second["siZhu"])
    }
}
