package org.bmstu

import org.junit.AfterClass
import java.io.File

abstract class TestBase {
    companion object {
        const val CALCULATION_RESULT = "calculated.txt"
        const val GENERATION_RESULT = "generated.txt"

        @AfterClass
        @JvmStatic
        fun dispose() {
            File(CALCULATION_RESULT).delete()
            File(GENERATION_RESULT).delete()
        }
    }
}