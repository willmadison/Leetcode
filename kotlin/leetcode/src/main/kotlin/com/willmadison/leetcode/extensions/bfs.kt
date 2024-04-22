package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Location
import java.util.*


fun numIslands(grid: Array<CharArray>): Int {
    var islands = 0

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (grid[row][col] == '1') {
                islands++
                grid[row][col] = '0'

                val neighbors = ArrayDeque<Int>()
                neighbors.add(row * grid[row].size + col)

                while (neighbors.isNotEmpty()) {
                    val id = neighbors.remove()

                    val r = id / grid[row].size
                    val c = id % grid[row].size

                    if (r - 1 >= 0 && grid[r - 1][c] == '1') {
                        neighbors.add((r - 1) * grid[row].size + c)
                        grid[r - 1][c] = '0'
                    }

                    if (r + 1 < grid.size && grid[r + 1][c] == '1') {
                        neighbors.add((r + 1) * grid[row].size + c)
                        grid[r + 1][c] = '0'
                    }

                    if (c - 1 >= 0 && grid[r][c - 1] == '1') {
                        neighbors.add(r * grid[row].size + c - 1)
                        grid[r][c - 1] = '0'
                    }

                    if (c + 1 < grid[row].size && grid[r][c + 1] == '1') {
                        neighbors.add(r * grid[row].size + c + 1)
                        grid[r][c + 1] = '0'
                    }
                }
            }
        }
    }

    return islands
}

private data class Landmass(val locations: MutableCollection<Location>)

fun findFarmland(land: Array<IntArray>): Array<IntArray> {
    val farmland = mutableListOf<IntArray>()

    for (row in land.indices) {
        for (col in land[row].indices) {
            if (land[row][col] == 1) {
                var x = row
                var y = col

                while (x < land.size && land[row][col] == 1) {
                    y = col
                    while (y < land[row].size && land[x][y] == 1) {
                        land[x][y] = 0
                        y++
                    }
                    x++
                }

                val arr = intArrayOf(row, col, x - 1, y - 1)
                farmland.add(arr)
            }
        }
    }

    return farmland.toTypedArray()
}

@Suppress("unused")
private fun discoverFarmland(grid: Array<IntArray>): Collection<Landmass> {
    val landmasses = mutableSetOf<Landmass>()

    val locations = mapOutAllLocations(grid)

    val visited = mutableSetOf<Location>()

    var unvisitedLocation = locations.firstOrNull()

    while (unvisitedLocation != null) {
        val coordinates = discoverContiguousLand(unvisitedLocation, grid, visited)
        landmasses.add(Landmass(coordinates))
        visited.addAll(coordinates)
        locations.removeAll(visited)
        unvisitedLocation = locations.firstOrNull()
    }

    return landmasses
}

private fun mapOutAllLocations(grid: Array<IntArray>): MutableCollection<Location> {
    val allLocations = mutableListOf<Location>()

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (grid[row][col] == 1) {
                allLocations.add(Location(row, col))
            }
        }
    }

    return allLocations
}

private fun discoverContiguousLand(
    location: Location,
    grid: Array<IntArray>,
    visited: MutableSet<Location>
): MutableCollection<Location> {
    val locations = mutableListOf<Location>()

    val q = ArrayDeque<Location>()

    if (!visited.contains(location)) {
        q.add(location)
    }

    while (q.isNotEmpty()) {
        val current = q.remove()

        visited.add(current)
        locations.add(current)

        val above = Location(current.row - 1, current.col)
        val right = Location(current.row, current.col + 1)
        val below = Location(current.row + 1, current.col)
        val left = Location(current.row, current.col - 1)

        val hasLandAbove = current.row - 1 >= 0 && grid[current.row - 1][current.col] == 1
        val hasLandRight = current.col + 1 < grid[current.row].size && grid[current.row][current.col + 1] == 1
        val hasLandBelow = current.row + 1 < grid.size && grid[current.row + 1][current.col] == 1
        val hasLandLeft = current.col - 1 >= 0 && grid[current.row][current.col - 1] == 1

        if (hasLandAbove) {
            if (!visited.contains(above)) {
                visited.add(above)
                q.add(above)
            }
        }

        if (hasLandRight) {
            if (!visited.contains(right)) {
                visited.add(right)
                q.add(right)
            }
        }

        if (hasLandBelow) {
            if (!visited.contains(below)) {
                visited.add(below)
                q.add(below)
            }
        }

        if (hasLandLeft) {
            if (!visited.contains(left)) {
                visited.add(left)
                q.add(left)
            }
        }
    }

    return locations
}

@Suppress("unused")
fun validPath(n: Int, edges: Array<IntArray>, source: Int, destination: Int): Boolean {
    val adjacencyList = mutableMapOf<Int, MutableCollection<Int>>()

    for (edge in edges) {
        var adjacents = adjacencyList.getOrPut(edge.first()) { mutableListOf() }
        adjacents.add(edge.last())

        adjacents = adjacencyList.getOrPut(edge.last()) { mutableListOf() }
        adjacents.add(edge.first())
    }

    val q = ArrayDeque<Int>()
    q.add(source)

    val visited = mutableSetOf<Int>()

    while (q.isNotEmpty()) {
        val current = q.remove()

        if (current == destination) {
            return true
        }

        visited.add(current)

        q.addAll(adjacencyList[current]!!.filter { !visited.contains(it) })
    }

    return false
}

fun openLock(deadends: Array<String>, combination: String): Int {
    val nextValues = mapOf(
        '0' to '1',
        '1' to '2',
        '2' to '3',
        '3' to '4',
        '4' to '5',
        '5' to '6',
        '6' to '7',
        '7' to '8',
        '8' to '9',
        '9' to '0',
    )

    val previousValues = mutableMapOf<Char, Char>()

    for (entry in nextValues) {
        previousValues[entry.value] = entry.key
    }

    val seen = mutableSetOf<String>()
    seen.addAll(deadends)

    val combosToTry = ArrayDeque<String>()

    var turns = 0

    if (seen.contains("0000")) return -1

    combosToTry.add("0000")
    seen.add("0000")

    while (combosToTry.isNotEmpty()) {
        val combosAtLevel = combosToTry.size

        for (i in 0..<combosAtLevel) {
            val current = combosToTry.remove()

            if (current == combination) {
                return turns
            }

            for (wheel in 0..3) {
                var comboSb = StringBuilder(current)
                comboSb.setCharAt(wheel, nextValues[comboSb[wheel]]!!)

                var combo = comboSb.toString()

                if (!seen.contains(combo)) {
                    combosToTry.add(combo)
                    seen.add(combo)
                }

                comboSb = StringBuilder(current)
                comboSb.setCharAt(wheel, previousValues[comboSb[wheel]]!!)

                combo = comboSb.toString()

                if (!seen.contains(combo)) {
                    combosToTry.add(combo)
                    seen.add(combo)
                }
            }
        }

        turns++
    }

    return -1
}


