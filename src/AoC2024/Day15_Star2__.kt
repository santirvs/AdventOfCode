package AoC2024

import java.util.*

//Ahora las cajas tiene el doble de ancho...
//Cargar el mapa del almacén
//Identificar la posición del robot
//Leer los movimientos del robot y mover las cajas en consequencia
//Al finalizar, recorrer el mapa y, para cada caja, calcular su posición GPS (fila*100 + columna)
//Sumas todas las posiciones GPS de las cajas y devolver el resultado


fun main() {
    val scan = Scanner(System.`in`)

    var mapa : MutableList<MutableList<Char>> = mutableListOf()
    var posRobot:Posicion = Posicion(0,0)

    //Leer el mapa
    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "") break  //Final del mapa

        //Transformar las líneas al doble de ancho
        linea = linea.map { if (it == '#') "##" else "$it" }.joinToString("")
        linea = linea.map { if (it == 'O') "[]" else "$it" }.joinToString("")
        linea = linea.map { if (it == '.') ".." else "$it" }.joinToString("")
        linea = linea.map { if (it == '@') "@." else "$it" }.joinToString("")

        mapa.add(linea.toMutableList())

        //Buscar la posición del robot
        if (linea.contains('@')) {
            posRobot.fila = mapa.size-1
            posRobot.columna = linea.indexOf('@')
        }
    }

    imprimirMapa(mapa)

    //Leer los movimientos del robot
    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "-1") break  //Final de la entrada interactiva

        //Mover el robot
        for (movimiento in linea) {

            println("Movimiento: $movimiento")

            moverRobot2(mapa, posRobot, movimiento)

            imprimirMapa(mapa)
        }
    }


    //Recorrer el mapa y calcular la posición GPS de las cajas
    var suma = 0
    for (fila in mapa.indices) {
        for (columna in mapa[fila].indices) {
            if (mapa[fila][columna] == '[') {
                suma += fila*100 + columna
            }
        }
    }

    println(suma)

}

fun moverRobot2(mapa:MutableList<MutableList<Char>>, posRobot: Posicion, movimiento:Char) {

    var deltaFila = 0
    var deltaColumna = 0

    var fila = posRobot.fila
    var columna = posRobot.columna

    when (movimiento) {
        '^' -> deltaFila--
        'v' -> deltaFila++
        '<' -> deltaColumna--
        '>' -> deltaColumna++
    }

    //Si la casilla a la que se quiere mover el robot está vacía, moverlo
    if (mapa[fila+deltaFila][columna+deltaColumna] == '.') {
        mapa[fila+deltaFila][columna+deltaColumna] = '@'
        mapa[fila][columna] = '.'
        //Actualizar la posición del robot
        posRobot.fila += deltaFila
        posRobot.columna += deltaColumna
    }


    //Si la casilla a la que se quiere mover el robot tiene una caja, buscar si se puede mover toda la secuencia de cajas
    var numCajas = 1
    while (mapa[fila+(deltaFila*numCajas)][columna+(deltaColumna*numCajas)] == '[' || mapa[fila+(deltaFila*numCajas)][columna+(deltaColumna*numCajas)] == ']') {
        numCajas++
    }
    //Si al final hay una casilla vacía, mover las cajas, si no, no hacer nada
    if (numCajas > 1 && mapa[fila+(deltaFila*numCajas)][columna+(deltaColumna*numCajas)] == '.') {

        //Antes de mover las cajas, hay que calcular el ancho del conjunto de cajas
        var anchoPositivo = 0
        var anchoNegativo = 0


        //// ****  FALTA MOVER EL ANCHO DE CAJAS  **** ////

        //Actualizar la posición del robot
        posRobot.fila += deltaFila
        posRobot.columna += deltaColumna
    }
}

/*

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <array>
#include <set>

enum {
    UP,
    DOWN,
    LEFT,
    RIGHT
};

std::set<std::array<int, 2>> touching_in_dir_vertical(std::vector<std::string>& grid, int row, int col, int dir, bool& movable)
{
    std::set<std::array<int, 2>> this_box_posns;
    if (grid[row][col] == '[') {
        this_box_posns.insert({ row, col });
        this_box_posns.insert({ row, col + 1 }); // must be to the right
    }
    else if (grid[row][col] == ']') {
        this_box_posns.insert({ row, col });
        this_box_posns.insert({ row, col - 1 });
    }
    else {
        if (grid[row][col] == '#')
            movable = false;
        return {};
    }
    std::set<std::array<int, 2>> temp, total_posns;
    for (auto pos : this_box_posns) {
        if (dir == UP)
            temp = touching_in_dir_vertical(grid, pos[0] - 1, pos[1], dir, movable);
        if (dir == DOWN)
            temp = touching_in_dir_vertical(grid, pos[0] + 1, pos[1], dir, movable);
        total_posns.insert(temp.begin(), temp.end());
    }
    total_posns.insert(this_box_posns.begin(), this_box_posns.end());
    return total_posns;
}

std::set<std::array<int, 2>> touching_in_dir_horizontal(std::vector<std::string>& grid, int row, int col, int dir, bool& movable)
{
    std::vector<std::array<int, 2>> this_box_posns;
    if (dir == LEFT) {
        for (int i = col - 1; i >= 0; i--) {
            if (grid[row][i] != '[' && grid[row][i] != ']') {
                if (grid[row][i] == '#')
                    movable = false;
                break;
            }
            this_box_posns.push_back({ row, i });
        }
    }
    else if (dir == RIGHT) {
        for (int i = col + 1; i < grid[0].size(); i++) {
            if (grid[row][i] != '[' && grid[row][i] != ']') {
                if (grid[row][i] == '#')
                    movable = false;
                break;
            }
            this_box_posns.push_back({ row, i });
        }
    }
    return std::set<std::array<int, 2>>(this_box_posns.begin(), this_box_posns.end());
}

void move(std::vector<std::string>& grid, int& row, int& col, int dir)
{
    std::vector<std::string> grid_copy = grid;
    bool movable = true;
    int origrow = row, origcol = col;
    std::set<std::array<int, 2>> box_posns;
    if (dir == UP) {
        box_posns = touching_in_dir_vertical(grid, row - 1, col, dir, movable);
    }
    else if (dir == DOWN) {
        box_posns = touching_in_dir_vertical(grid, row + 1, col, dir, movable);
    }
    else if (dir == LEFT) {
        box_posns = touching_in_dir_horizontal(grid, row, col, dir, movable);
    }
    else if (dir == RIGHT) {
        box_posns = touching_in_dir_horizontal(grid, row, col, dir, movable);
    }

    grid_copy[row][col] = '.';

    if (movable) {
        if (dir == UP)
            row--;
        if (dir == DOWN)
            row++;
        if (dir == LEFT)
            col--;
        if (dir == RIGHT)
            col++;
    }

    if (movable && box_posns.size() != 0) {
        for (std::array<int, 2> pos : box_posns) {
            grid_copy[pos[0]][pos[1]] = '.';
            if (dir == UP) {
                for (std::array<int, 2> pos : box_posns) {
                    grid_copy[pos[0] - 1][pos[1]] = grid[pos[0]][pos[1]];
                }
            }
            else if (dir == DOWN) {
                for (std::array<int, 2> pos : box_posns) {
                    grid_copy[pos[0] + 1][pos[1]] = grid[pos[0]][pos[1]];
                }
            }
            else if (dir == LEFT) {
                for (std::array<int, 2> pos : box_posns) {
                    grid_copy[pos[0]][pos[1] - 1] = grid[pos[0]][pos[1]];
                }
            }
            else if (dir == RIGHT) {
                for (std::array<int, 2>pos : box_posns) {
                    grid_copy[pos[0]][pos[1] + 1] = grid[pos[0]][pos[1]];
                }
            }
        }
    }
    grid_copy[row][col] = '@';
    grid = grid_copy;
}

int main()
{
    std::ifstream in("stdin.txt");
    std::string line;

    std::vector<std::string> grid;
    std::string moves;
    bool reached_moves = false;

    while (getline(in, line)) {
        if (line == "") {
            reached_moves = true;
            continue;
        }

        std::string warehouse_line;
        if (!reached_moves) {
            for (char pos : line) {
                if (pos == '#')
                    warehouse_line += "##";
                else if (pos == '.')
                    warehouse_line += "..";
                else if (pos == 'O')
                    warehouse_line += "[]";
                else if (pos == '@')
                    warehouse_line += "@.";
            }
            grid.push_back(warehouse_line);
        }
        else {
            moves += line;
        }
    }
    int start_rows = -1, start_cols = -1;
    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid[0].size(); j++) {
            if (grid[i][j] == '@') {
                start_rows = i;
                start_cols = j;
                break;
            }
        }
        if (start_rows != -1)
            break;
    }

    for (char dir : moves) {
        if (dir == '<')
            move(grid, start_rows, start_cols, LEFT);
        if (dir == '>')
            move(grid, start_rows, start_cols, RIGHT);
        if (dir == '^')
            move(grid, start_rows, start_cols, UP);
        if (dir == 'v')
            move(grid, start_rows, start_cols, DOWN);
    }
    long long p2 = 0;
    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid[0].size(); j++) {
            if (grid[i][j] == '[') {
                p2 += 100 * i + j;
            }
        }
    }
    std::cout << "p2: " << p2 << '\n';
}

 */