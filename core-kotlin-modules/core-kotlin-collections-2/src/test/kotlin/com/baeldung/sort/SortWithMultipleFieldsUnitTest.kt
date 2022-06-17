package com.baeldung.sort

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SortWithMultipleFieldsUnitTest {

    private val students = listOf(
        Student(name = "C", age = 9),
        Student(name = "A", age = 11, country = "C1"),
        Student(name = "B", age = 10, country = "C2"),
        Student(name = "A", age = 10),
    )

    @Test
    fun whenSortedWithSelectorsWithoutSpecificOrder_thenGetNewListsAsExpected() {
        assertEquals(
            students.sortedWith(compareBy({ it.name }, { it.age })),
            listOf(
                Student(name = "A", age = 10),
                Student(name = "A", age = 11, country = "C1"),
                Student(name = "B", age = 10, country = "C2"),
                Student(name = "C", age = 9),
            )
        )
    }

    @Test
    fun whenSortedWithPropertiesWithoutSpecificOrder_thenGetNewListsAsExpected() {
        assertEquals(
            students.sortedWith(compareBy(Student::name, Student::age)),
            listOf(
                Student(name = "A", age = 10),
                Student(name = "A", age = 11, country = "C1"),
                Student(name = "B", age = 10, country = "C2"),
                Student(name = "C", age = 9),
            )
        )
    }

    @Test
    fun whenSortedWithComparableInterface_thenGetNewListsAsExpected() {
        assertEquals(
            students.sorted(),
            listOf(
                Student(name = "A", age = 10),
                Student(name = "A", age = 11, country = "C1"),
                Student(name = "B", age = 10, country = "C2"),
                Student(name = "C", age = 9),
            )
        )
    }

    @Test
    fun whenSortedWithMutableListsWithoutSpecificOrder_thenGetListSorted() {
        val mutableStudents = students.toMutableList()
        mutableStudents.sortWith(compareBy(Student::name, Student::age))
        assertEquals(
            mutableStudents,
            listOf(
                Student(name = "A", age = 10),
                Student(name = "A", age = 11, country = "C1"),
                Student(name = "B", age = 10, country = "C2"),
                Student(name = "C", age = 9),
            )
        )

    }

    @Test
    fun whenSortedWithSpecificOrder_thenGetNewListsAsExpected() {
        assertEquals(
            students.sortedWith(compareBy<Student> { it.name }.thenByDescending { it.age }),
            listOf(
                Student(name = "A", age = 11, country = "C1"),
                Student(name = "A", age = 10),
                Student(name = "B", age = 10, country = "C2"),
                Student(name = "C", age = 9),
            )
        )

    }

    @Test
    fun whenSortedWithNullValues_thenGetNewListsAsExpected() {
        assertEquals(
            students.sortedWith(compareBy<Student> { it.country }.thenBy { it.name }),
            listOf(
                Student(name = "A", age = 10),
                Student(name = "C", age = 9),
                Student(name = "A", age = 11, country = "C1"),
                Student(name = "B", age = 10, country = "C2"),
            )
        )
    }

    @Test
    fun whenSortedWithNullValuesLast_thenGetNewListsAsExpected() {
        assertEquals(
            students.sortedWith(compareBy<Student, String?>(nullsLast()) { it.country }.thenBy { it.name }),
            listOf(
                Student(name = "A", age = 11, country = "C1"),
                Student(name = "B", age = 10, country = "C2"),
                Student(name = "A", age = 10),
                Student(name = "C", age = 9),
            )
        )
    }

    private data class Student(val name: String, val age: Int, val country: String? = null) : Comparable<Student> {
        override fun compareTo(other: Student): Int {
            return compareValuesBy(this, other, { it.name }, { it.age })
        }
    }
}