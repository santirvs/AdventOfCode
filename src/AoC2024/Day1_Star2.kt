import java.util.*

// Hola, soy un comentario

fun main() {
    val scan = Scanner(System.`in`)

    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()

    //Cargar las listas
    while (scan.hasNext()) {
        val left = scan.nextInt()
        if (left==-1) break             //Salida del bucle interactiva
        val right = scan.nextInt()

        leftList.add(left)
        rightList.add(right)
    }

    //Ordenar las listas
    leftList.sort()
    rightList.sort()

    //Calcular las similitudes entre los elementos de ambas listas
    //Aprovechamos que están ordenadas
    //Recorremos las listas y sumamos el elemento de la lista izquierda si es igual al de la derecha
    //Si son diferentes, avanzamos el puntero de la lista con el menor valor
    var similarity = 0
    var leftIndex = 0
    var rightIndex = 0
    while (leftIndex < leftList.size && rightIndex < rightList.size) {
        if (leftList[leftIndex] == rightList[rightIndex]) {
            similarity+=leftList[leftIndex]
            rightIndex++
        } else if (leftList[leftIndex] < rightList[rightIndex]) {
            leftIndex++
        } else {
            rightIndex++
        }
    }

    //
    println(similarity)

}
