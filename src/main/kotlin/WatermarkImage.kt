import java.awt.Color
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.lang.NumberFormatException
import kotlin.system.exitProcess

class WatermarkImage(name: String, var alphaChanel: String = "no"): Images(name) {

    fun setAlphaChanel(alphaChanelInt: Int) {
        if (alphaChanelInt == Transparency.TRANSLUCENT) {
            println("Do you want to use the watermark's Alpha channel?")
            alphaChanel = readln().lowercase()
        }
    }

    private fun errorTransparencyColor() {
        println("The transparency color input is invalid.")
        exitProcess(0)
    }

    val transparencyColorList = arrayListOf<Int>()
    var transparencyColor = Color(255, 255, 255)

    fun setTransparencyColor() {
        println("Do you want to set a transparency color?")
        if (readln().lowercase() == "yes") {
            println("Input a transparency color ([Red] [Green] [Blue]):")
            val inputTransparencyColors = readln().split(" ")
            try {
                for (str in inputTransparencyColors) {
                    if (inputTransparencyColors.size == 3 && str.toInt() in 0..255) transparencyColorList.add(str.toInt())
                    else errorTransparencyColor()
                }
                transparencyColor = Color(
                    transparencyColorList[0],
                    transparencyColorList[1],
                    transparencyColorList[2]
                )
            } catch (e: NumberFormatException) {
                errorTransparencyColor()
            }
        }
    }

    var transparency = 0
    fun setTransparencyPercentage() {
        println("Input the watermark transparency percentage (Integer 0-100):")

        try {
            transparency = readln().toInt()
        } catch (e: NumberFormatException) {
            println("The transparency percentage isn't an integer number.")
            exitProcess(0)
        }
        if (transparency !in 0..100) {
            println("The transparency percentage is out of range.")
            exitProcess(0)
        }
    }
}