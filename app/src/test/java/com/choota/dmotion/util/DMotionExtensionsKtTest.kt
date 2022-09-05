package com.choota.dmotion.util

import com.google.common.truth.Truth.assertThat

import org.junit.Test

class DMotionExtensionsKtTest {

    @Test
    fun `verify null string will replace with given value`() {
        val input: String? = null
        val outPut: String? = input.resolve("N/A")

        assertThat(outPut == "N/A").isTrue()
    }

    @Test
    fun `verify null string will replace with empty if no replace value`() {
        val input: String? = null
        val outPut: String? = input.resolve()

        assertThat(outPut!!.isEmpty()).isTrue()
    }

    @Test
    fun `verify country value can be resolve from string`() {
        val input: String? = "lk"
        val outPut: String = input.country()

        assertThat(outPut.lowercase() == "sri lanka").isTrue()
    }

    @Test
    fun `verify language value can be resolve from string`() {
        val input: String? = "en"
        val outPut: String = input.language()

        assertThat(outPut.lowercase() == "english").isTrue()
    }

    @Test
    fun `verify null Int with replace with given value`() {
        val input: Int? = null
        val outPut: Int? = input.resolve(-1)

        assertThat(outPut == -1).isTrue()
    }

    @Test
    fun `verify null Int with replace with default value`() {
        val input: Int? = null
        val outPut: Int? = input.resolve()

        assertThat(outPut == -1).isTrue()
    }
}