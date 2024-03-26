package com.willmadison.leetcode

@Suppress("unused")
class Solution : VersionControl()

open class VersionControl {
    fun isBadVersion(version: Int): Boolean {
        val rawFirstBad = System.getenv("FIRST_BAD_VERSION")
        val firstBad = rawFirstBad.toInt()
        return version >= firstBad
    }
}

@Suppress("unused")
class ListNode(var `val`: Int, var next: ListNode? = null)

class TreeNode(var `val`: Int, var left: TreeNode? = null, var right: TreeNode? = null)

class Node(var `val`: Int, var children: MutableList<Node?> = ArrayList<Node?>())

@Suppress("unused")
class ParkingSystem(big: Int, medium: Int, small: Int) {
    private enum class ParkingSpotType(val id: Int) {
        BIG(1),
        MEDIUM(2),
        SMALL(3);

        companion object {
            fun typeById(id: Int): ParkingSpotType {
                return when (id) {
                    1 -> BIG
                    2 -> MEDIUM
                    3 -> SMALL
                    else -> BIG
                }
            }
        }
    }

    private val spotAvailabilityByType = mutableMapOf<ParkingSpotType, Int>()

    init {
        spotAvailabilityByType[ParkingSpotType.BIG] = big
        spotAvailabilityByType[ParkingSpotType.MEDIUM] = medium
        spotAvailabilityByType[ParkingSpotType.SMALL] = small
    }

    fun addCar(carType: Int): Boolean {
        val spotType = ParkingSpotType.typeById(carType)

        if (spotAvailabilityByType[spotType]!! > 0) {
            var slots = spotAvailabilityByType[spotType]!!
            slots--
            spotAvailabilityByType[spotType] = slots
            return true
        }

        return false
    }

}