package ru.nifontbus.testcourse

import org.junit.Assert.*
import org.junit.Test
import ru.nifontbus.testcourse.HomeWork1.EmailValidator
import ru.nifontbus.testcourse.HomeWork1.FirstTest

class EmailValidatorTest {

    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null))
    }

    // my:
    @Test
    fun emailValidator_EmailWithoutDomain_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name"))
    }

    @Test
    fun emailValidator_EmailWithoutDomainDog_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@"))
    }

    @Test
    fun emailValidator_InvalidEmailSpecSymbol_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("*name@ya.ru"))
    }

    @Test
    fun testEquals() {
        assertEquals(243.0, FirstTest.power(3.0, 5.0), 0.01)
    }

    @Test
    fun testNotEquals() {
        assertNotEquals(FirstTest.power(3.0, 5.0), 81.0)
    }

    @Test
    fun testArrayEquals() {
        val array2 = FirstTest.array1.clone()
        assertArrayEquals(FirstTest.array1, array2)
    }

    @Test
    fun testAssertNull() {
        assertNull(null)
    }

    @Test
    fun testAssertNotNull() {
        assertNotNull(FirstTest.array1)
    }

    @Test
    fun testAssertSame() {
        val array2 = FirstTest.array1
        assertSame(FirstTest.array1, array2)
    }

}
