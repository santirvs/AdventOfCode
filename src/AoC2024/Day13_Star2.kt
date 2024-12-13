package AoC2024

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


//Ahora ya no sirve la búsqueda en un espacio de soluciones, ya que el número es demasiado grande

/*

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cmath>

std::vector<std::string> split(const std::string& s, const std::string& delimiter)
{
    size_t pos_start = 0, pos_end, delim_len = delimiter.length();
    std::string token;
    std::vector<std::string> res;

    while ((pos_end = s.find(delimiter, pos_start)) != std::string::npos) {
        token = s.substr(pos_start, pos_end - pos_start);
        pos_start = pos_end + delim_len;
        res.push_back(token);
    }

    res.push_back(s.substr(pos_start));
    return res;
}

int main()
{
    long long a_xdiff, a_ydiff, b_xdiff, b_ydiff, target_x, target_y;
    std::ifstream in("stdin.txt");
    std::string line;
    unsigned long long p2 = 0;
    while (getline(in, line)) {
    std::vector<std::string> aparts = split(line, " ");
    std::string a_xdiff_str = aparts[2].substr(0, aparts[2].size() - 1);
    a_xdiff = std::stoi(split(a_xdiff_str, "+")[1]);
    a_ydiff = std::stoi(split(aparts[3], "+")[1]);

    getline(in, line);
    std::vector<std::string> bparts = split(line, " ");
    std::string b_xdiff_str = bparts[2].substr(0, bparts[2].size() - 1);
    b_xdiff = std::stoi(split(b_xdiff_str, "+")[1]);
    b_ydiff = std::stoi(split(bparts[3], "+")[1]);

    getline(in, line);
    std::vector<std::string> target = split(line, " ");
    std::string target_x_str = target[1].substr(0, target[1].size() - 1);
    target_x = std::stoi(split(target_x_str, "=")[1]) + 10000000000000;
    target_y = std::stoi(split(target[2], "=")[1]) + 10000000000000; //ten trillion babeeey

    getline(in, line); // random newlines

    // this can be represented as a matrix
    // a.x b.x
    // a.y b.y
    // multiplied by (a presses, b presses)
    // that's equal to (target_x, target_y)
    long long det_norm = a_xdiff * b_ydiff - b_xdiff * a_ydiff;
    long long det_x = target_x * b_ydiff - target_y * b_xdiff;
    double x = (double)det_x / (double)det_norm;
    long long det_y = a_xdiff * target_y - target_x * a_ydiff;
    double y = (double)det_y / (double)det_norm;

    const double EPSILON = std::pow(10, -8);
    std::cout << "x: " << x << ", y: " << y << '\n';
    if (std::abs(x - std::llround(x)) > EPSILON || std::abs(y - std::llround(y)) > EPSILON)
        continue;
    if (x < 0 || y < 0)
        continue;

    p2 += 3 * x + y;


}
    std::cout << "p2: " << p2 << '\n';
}

*/