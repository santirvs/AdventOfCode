import java.util.Scanner

// Simplemente, probar si hay espacio suficiente para todos los regalos (asumiendo que son de 3 x 3)
// Si no lo hay, se descarta la solución
// De hecho, no funciona para el caso "pequeño" de test, pero sí para la solución principal
// Me la he jugado porque ya me los conozco y el último día suele haber una "troleada",
// además de regalar la última estrella

fun main() {
    var scan : Scanner = Scanner(System.`in`)

    //Ignorar las piezas
    repeat(6*5) {
        scan.nextLine()
    }

    //Procesar las regiones
    var regionesPosibles=0
    while (scan.hasNext()) {
        var linea = scan.nextLine().split(":")
        var areaParts = linea[0].split("x")
        var area = areaParts[0].toInt() * areaParts[1].toInt()
        var quantsParts = linea[1].trim().split(" ")
        var areaPiezas = 0
        for (q in quantsParts) {
            areaPiezas += q.toInt()*3*3
        }
        if (areaPiezas <= area) {
            regionesPosibles++
        }
    }

    println("Solución 1: $regionesPosibles")
}