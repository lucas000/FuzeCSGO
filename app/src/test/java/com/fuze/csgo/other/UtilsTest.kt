package com.fuze.csgo.other

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilsTest {

    @Test
    fun `getTimeMatch`() {
        val response = Utils.getDateTime("2022-11-03T19:00:00Z")

        assertThat(response).isEqualTo("Hoje, 19:00")
    }
}