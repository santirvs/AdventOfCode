import java.util.*

fun main() {
    var scan = Scanner(System.`in`)

    var sumaTotal:Int  = 0
    var numeros = listOf<String>("zero", "one", "two", "three", "four",
        "five", "six", "seven", "eight","nine")

    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "0") break

        //Reemplazar las cifras en texto
        for (i in 0..9) {
            linea = linea.replace(numeros[i],i.toString())
        }
        var primerDigito: Int = 0
        var ultimoDigito: Int = 0

        var pos: Int = 0
        var encontrado = false
        while (pos < linea.length && !encontrado) {
            if (linea[pos].isDigit()) {
                encontrado = true
                primerDigito = Integer.parseInt(linea.substring(pos, pos+1))
            }
            pos++
        }

        pos = linea.length - 1
        encontrado = false
        while (pos > 0 && !encontrado) {
            if (linea[pos].isDigit()) {
                encontrado = true
                ultimoDigito = Integer.parseInt(linea.substring(pos, pos+1))
            }
            pos--
        }
        var valor:Int = primerDigito*10 + ultimoDigito

        sumaTotal+=valor
    }

    println(sumaTotal)
}