package ru.nifontbus.testcourse

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.nifontbus.testcourse.HomeWork1.Coffee


class TestCoffee {

    @Test
    fun testGetMockedCoffeeName_Success() {
        val coffee = mock(Coffee::class.java)

        `when`(coffee.getCoffeeName(1))
            .thenReturn("Капучино")
            .thenReturn("Black")

        assertEquals(coffee.getCoffeeName(1), "Капучино")
        assertEquals(coffee.getCoffeeName(1), "Black")
    }

/*    @Test
    fun testVerify(){
        val coffee = mock(Coffee::class.java)
        `when`(coffee.id).thenReturn(43)

        coffee.id = 25
        var id = coffee.id
        id = coffee.id

        assertEquals(id, 43)
        verify(coffee, times(2)).id
    }*/



}