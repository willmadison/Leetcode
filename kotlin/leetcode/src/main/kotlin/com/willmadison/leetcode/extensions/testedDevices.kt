package com.willmadison.leetcode.extensions

fun countTestedDevices(batteryPercentages: IntArray): Int {
    var testedDevices = 0

    for (i in batteryPercentages.indices) {
        if (batteryPercentages[i] > 0) {
            testedDevices++

            for (j in (i + 1)..<batteryPercentages.size) {
                batteryPercentages[j] = java.lang.Integer.max(batteryPercentages[j] - 1, 0)
            }
        }
    }

    return testedDevices
}