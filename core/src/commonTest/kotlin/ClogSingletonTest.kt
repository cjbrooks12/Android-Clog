package clog

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ClogSingletonTest {
    @Test
    fun testBasicLog() {
        clogTest { logger ->
            Clog.v("m1")

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastPriority)
            assertEquals("m1", logger.lastMessage)
        }
    }

    @Test
    fun testLogWithTag() {
        clogTest { logger ->
            Clog.tag("t1").v("m1")

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastPriority)
            assertEquals("t1", logger.lastTag)
            assertEquals("m1", logger.lastMessage)
        }
    }

    @Test
    fun testFormattedLog() {
        clogTest { logger ->
            Clog.v("m1: {}", "p1")

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastPriority)
            assertEquals("m1: p1", logger.lastMessage)
        }
    }

    @Test
    fun testFormattedLogMultipleParams() {
        clogTest { logger ->
            Clog.v("m1: {}, {}, {}, {}", "p1", 2, true, "p4" to "p5")

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastPriority)
            assertEquals("m1: p1, 2, true, (p4, p5)", logger.lastMessage)
        }
    }

    @Test
    fun testTagBlacklisting() {
        clogTest { logger ->
            Clog.getInstance().addTagToBlacklist("t1")

            Clog.tag("t1").v("m1")
            assertFalse(logger.messageWasLogged)

            Clog.tag("t2").v("m2")
            assertTrue(logger.messageWasLogged)
            assertEquals("t2", logger.lastTag)
            assertEquals("m2", logger.lastMessage)
        }
    }

    @Test
    fun testTagWhitelisting() {
        clogTest { logger ->
            Clog.getInstance().addTagToWhitelist("t2")

            Clog.tag("t1").v("m1")
            assertFalse(logger.messageWasLogged)

            Clog.tag("t2").v("m2")
            assertTrue(logger.messageWasLogged)
            assertEquals("t2", logger.lastTag)
            assertEquals("m2", logger.lastMessage)
        }
    }
}
