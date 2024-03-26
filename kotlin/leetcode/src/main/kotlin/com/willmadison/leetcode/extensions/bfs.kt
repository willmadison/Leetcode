package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Location
import java.util.*
import java.util.ArrayDeque

private data class Island(val locations: MutableCollection<Location>)

fun numIslands(grid: Array<CharArray>): Int {
    val islands = discoverIslands(grid)
    return islands.size
}

private fun discoverIslands(grid: Array<CharArray>): Collection<Island> {
    val islands = mutableSetOf<Island>()

    val visited = mutableSetOf<Location>()

    var unvisitedLocation = findFirstUnvisitedLandMass(grid, visited)

    while (unvisitedLocation.isPresent) {
        val coordinates = discoverContiguousLand(unvisitedLocation.get(), grid, visited)
        islands.add(Island(coordinates))
        coordinates.map { visited.add(it) }
        unvisitedLocation = findFirstUnvisitedLandMass(grid, visited)
    }

    return islands
}

private fun findFirstUnvisitedLandMass(grid: Array<CharArray>, visited: MutableSet<Location>): Optional<Location> {
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            val l = Location(row, col)
            val isLand = grid[row][col] == '1'

            if (isLand && !visited.contains(l)) {
                return Optional.of(l)
            }
        }
    }

    return Optional.empty()
}

private fun discoverContiguousLand(
    location: Location,
    grid: Array<CharArray>,
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

        val hasLandAbove = current.row - 1 >= 0 && grid[current.row - 1][current.col] == '1'
        val hasLandRight = current.col + 1 < grid[current.row].size && grid[current.row][current.col + 1] == '1'
        val hasLandBelow = current.row + 1 < grid.size && grid[current.row + 1][current.col] == '1'
        val hasLandLeft = current.col - 1 >= 0 && grid[current.row][current.col - 1] == '1'

        if (hasLandAbove) {
            if (!visited.contains(above)) {
                q.add(above)
            }
        }

        if (hasLandRight) {
            if (!visited.contains(right)) {
                q.add(right)
            }
        }

        if (hasLandBelow) {
            if (!visited.contains(below)) {
                q.add(below)
            }
        }

        if (hasLandLeft) {
            if (!visited.contains(left)) {
                q.add(left)
            }
        }
    }

    return locations
}