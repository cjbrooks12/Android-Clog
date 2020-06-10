package clog

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JvmClogSlf4jTest {
    @Test
    fun testBasicLog() {
        clogTest { logger ->
            val slf4j: Logger = LoggerFactory.getLogger(JvmClogSlf4jTest::class.java)

            slf4j.trace("m1")

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastPriority)
            assertEquals("clog.JvmClogSlf4jTest", logger.lastTag)
            assertEquals("m1", logger.lastMessage)
        }
    }

    @Test
    fun testLogInAnonymousObject() {
        clogTest { logger ->
            val slf4j: Logger = LoggerFactory.getLogger(JvmClogSlf4jTest::class.java)

            val o = object {
                fun doLog() {
                    slf4j.trace("m1")
                }
            }

            o.doLog()

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastPriority)
            assertEquals("clog.JvmClogSlf4jTest", logger.lastTag)
            assertEquals("m1", logger.lastMessage)
        }
    }

    @Test
    fun testLogInLambda() {
        clogTest { logger ->
            val slf4j: Logger = LoggerFactory.getLogger(JvmClogSlf4jTest::class.java)

            val doLog = {
                slf4j.trace("m1")
            }

            doLog()

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastPriority)
            assertEquals("clog.JvmClogSlf4jTest", logger.lastTag)
            assertEquals("m1", logger.lastMessage)
        }
    }
}
