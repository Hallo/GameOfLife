ackage gameoflife

import java.util.LinkedList
import java.util.Random
import java.util.ArrayList

class State(val height: Int, val width: Int) {
    class object {
	private val ReaktorString : String = "---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------------------------------------------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------------------------------------------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------------------------------------------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------------------------------------------------------------\n--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n---------------------------------------------------------------------------------------------------------------------------------------------\n"
        fun createRowFromString(str: String): Array<Boolean> {
            val row = ArrayList<Boolean>()
            for (char in str) {
                if (char == 'x') row.add(true)
                else row.add(false)
            }
            return Array<Boolean>(row.size(), { row.get(it) })
        }

        fun reaktorState(): State {
            val lines = reaktorString.split('\n')
            val state = State(lines.size, lines[0].size)
            for (lineIdx in lines.indices) {
                state.aliveGrid[lineIdx] = createRowFromString(lines[lineIdx])
            }
            return state
        }
    }

    val aliveGrid = Array<Array<Boolean>>(height, {
        Array<Boolean>(width, { false })
    })

    fun randomize(seed: Long = 1) {
        val random = Random(seed)
        for (row in aliveGrid.indices) {
            for (col in aliveGrid[row].indices) {
                aliveGrid[row][col] = random.nextBoolean()
            }
        }
    }

    fun isValidCell(row: Int, col: Int) = !(row < 0 || col < 0 || row >= height || col >= width)

    fun oneIfCellIsAlive(row: Int, col: Int): Int {
        if (isValidCell(row, col)) {
            val alive = aliveGrid[row][col]
            if (alive) return 1
            else return 0
        } else return 0
    }

    fun aliveNeighboursAmount(row: Int, col: Int): Int {
        var aliveAmount = 0
        // Left
        aliveAmount += oneIfCellIsAlive(row - 1, col)
        // Upper-Left
        aliveAmount += oneIfCellIsAlive(row - 1, col - 1)
        // Up
        aliveAmount += oneIfCellIsAlive(row, col - 1)
        // Upper-Right
        aliveAmount += oneIfCellIsAlive(row - 1, col + 1)
        // Right
        aliveAmount += oneIfCellIsAlive(row, col + 1)
        // Lower-Right
        aliveAmount += oneIfCellIsAlive(row + 1, col + 1)
        // Down
        aliveAmount += oneIfCellIsAlive(row + 1, col)
        // Lower-Left
        aliveAmount += oneIfCellIsAlive(row + 1, col - 1)
        return aliveAmount
    }

    fun aliveAfterInteraction(row: Int, col: Int): Boolean {
        val aliveNeighbours = aliveNeighboursAmount(row, col)
        val alive = aliveGrid[row][col]
        if (alive) {
            if (aliveNeighbours < 2) {
                return false
            } else if (aliveNeighbours < 4) {
                return true
            } else {
                return false
            }
        } else {
            if (aliveNeighbours == 3) {
                return true
            } else {
                return false
            }
        }
    }

    fun stateAfterInteraction(): State {
        val newState = State(height, width)

        for (row in aliveGrid.indices) {
            for (col in aliveGrid[row].indices) {
                newState.aliveGrid[row][col] = aliveAfterInteraction(row, col)
            }
        }
        return newState
    }

    fun toString(): String {
        val stringBuilder = StringBuilder()
        for (row in aliveGrid.indices) {
            for (col in aliveGrid[row].indices) {
                val alive = aliveGrid[row][col]
                stringBuilder.append(if (alive) "X" else "-")
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}
