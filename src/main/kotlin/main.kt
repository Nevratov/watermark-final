import java.awt.Transparency.TRANSLUCENT

fun main() {
    println("Input the image filename:")
    val mainImage = MainImage(readln())
    val check = CheckFiles()
    check.exist(mainImage)
    check.colorModel(mainImage.buffered.colorModel, "image")

    println("Input the watermark image filename:")
    val watermarkImage = WatermarkImage(readln())
    check.exist(watermarkImage)
    check.colorModel(watermarkImage.buffered.colorModel, "watermark")
    check.size(mainImage.buffered, watermarkImage.buffered)

    watermarkImage.setAlphaChanel(watermarkImage.buffered.transparency)
    if (watermarkImage.buffered.transparency != TRANSLUCENT) watermarkImage.setTransparencyColor()

    watermarkImage.setTransparencyPercentage()

    val newFile = NewFile(mainImage.buffered)
    newFile.setPositionMethod()
    newFile.setWatermarkPosition(mainImage.buffered, watermarkImage.buffered)
    newFile.setOutputFileName()
    newFile.outputFile(mainImage, watermarkImage)

}