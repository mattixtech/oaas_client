package sample

import com.opennms.cloud.maas.MaasClient
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SampleTestsJVM {
    @Test
    fun testHello() {
        println(hello())
        assertThat(hello().split(" "), hasItem("JVM"))
        MaasClient("http://localhost:8000").run { 
            runBlocking {
                doIt("test.txt").let {
                    println(it)
                }
            }
        }
    }
}