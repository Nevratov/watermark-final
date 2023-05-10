import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess

class NewFile(mainImage: BufferedImage) {
    private var positionMethod = ""
    private var outputName = ""
    private val outputImage = mainImage

    private fun errorPositionWatermark() {
        println("The position input is invalid.")
        exitProcess(0)
    }

    private fun errorRangePositionWatermark() {
        println("The position input is out of range.")
        exitProcess(0)
    }

    fun setPositionMethod() {
        println("Choose the position method (single, grid):")
        positionMethod = readln().lowercase()
    }

    private val watermarkPosition = arrayListOf<Int>()

    fun setWatermarkPosition(mainImage: BufferedImage, watermarkImage: BufferedImage) {
        val diffX = mainImage.width - watermarkImage.width
        val diffY = mainImage.height - watermarkImage.height

        if (positionMethod == "single") {
            println("Input the watermark position ([x 0-$diffX] [y 0-$diffY]):")
            val inputWatermarkPosition = readln().split(" ")
            val x = inputWatermarkPosition[0]
            val y = inputWatermarkPosition[1]
            try {
                if (inputWatermarkPosition.size != 2) errorPositionWatermark()
                else if (x.toInt() in 0..diffX && y.toInt() in 0..diffY) {
                    watermarkPosition.add(x.toInt())
                    watermarkPosition.add(y.toInt())
                } else errorRangePositionWatermark()
            } catch (e: Exception) {
                errorPositionWatermark()
            }
        } else if (positionMethod != "grid") {
            println("The position method input is invalid.")
            exitProcess(0)
        }
    }

    fun setOutputFileName() {
        println("Input the output image filename (jpg or png extension):")
        outputName = readln()
        if (".jpg" !in outputName && ".png" !in outputName) {
            println("The output file extension isn't \"jpg\" or \"png\".")
            exitProcess(0)
        }
    }

    fun outputFile(mainImage: MainImage, watermarkImage: WatermarkImage) {
        val transparency = watermarkImage.transparency
        val transparencyColor = watermarkImage.transparencyColor

            for (x in 0 until mainImage.buffered.width) {
                for (y in 0 until mainImage.buffered.height) {

                        val i = Color(mainImage.buffered.getRGB(x, y)) // image.color
                        val w = if (positionMethod == "single") wForSingle(mainImage, watermarkImage, watermarkPosition, x, y)
                        else wForGrid(watermarkImage, x, y)

                        val newColor = if (watermarkImage.alphaChanel == "yes" && w.alpha == 0) Color(i.rgb)
                        else if (watermarkImage.transparencyColorList.isNotEmpty() && w == transparencyColor) Color(i.rgb)
                        else Color(
                            (transparency * w.red + (100 - transparency) * i.red) / 100,
                            (transparency * w.green + (100 - transparency) * i.green) / 100,
                            (transparency * w.blue + (100 - transparency) * i.blue) / 100
                        )

                        outputImage.setRGB(x, y, newColor.rgb)
                    }
                }

        val outputFile = File(outputName)
        ImageIO.write(outputImage, outputName.substringAfterLast('.'), outputFile)
        println("The watermarked image $outputName has been created.")
    }
}

fun wForGrid(watermarkImage: WatermarkImage, x: Int, y: Int): Color {
    return if (watermarkImage.alphaChanel == "yes") // watermark.color
        Color(watermarkImage.buffered.getRGB(x % watermarkImage.buffered.width, y % watermarkImage.buffered.height), true)
    else Color(watermarkImage.buffered.getRGB(x % watermarkImage.buffered.width, y % watermarkImage.buffered.height))
}

fun wForSingle(mainImage: MainImage, watermarkImage: WatermarkImage, watermarkPosition: ArrayList<Int>, x: Int, y: Int): Color {
    if (x in watermarkPosition[0] until watermarkPosition[0] + watermarkImage.buffered.width &&
        y in watermarkPosition[1] until watermarkPosition[1] + watermarkImage.buffered.height
    ) {
        return if (watermarkImage.alphaChanel == "yes") // watermark.color
            Color(watermarkImage.buffered.getRGB(x - watermarkPosition[0], y - watermarkPosition[1]), true)
        else
            Color(watermarkImage.buffered.getRGB(x - watermarkPosition[0], y - watermarkPosition[1]))
    }
    return Color(mainImage.buffered.getRGB(x, y))
}