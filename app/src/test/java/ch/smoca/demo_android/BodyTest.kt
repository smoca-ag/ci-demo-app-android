package ch.smoca.demo_android

import org.junit.Test

import org.junit.Assert.*

class BodyTest {

    @Test
    fun bmiCalculationTest() {
        val body = Body(70, 180)
        assertEquals(22, body.bmi)
    }

}