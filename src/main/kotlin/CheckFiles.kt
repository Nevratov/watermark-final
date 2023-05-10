import java.awt.image.BufferedImage
import java.awt.image.ColorModel
import javax.imageio.ImageIO
import kotlin.system.exitProcess

class CheckFiles {
    fun exist(image: Images) {
        if (!image.file.exists()) {
            println("The file ${image.name} doesn't exist.")
            exitProcess(0)
        } else image.buffered = ImageIO.read(image.file)
    }

    fun colorModel(colorModel: ColorModel, typeImage: String) {
        if (colorModel.numColorComponents != 3) {
            println("The number of $typeImage color components isn't 3.")
            exitProcess(0)
        } else if (colorModel.pixelSize != 24 && colorModel.pixelSize != 32) {
            println("The $typeImage isn't 24 or 32-bit.")
            exitProcess(0)
        }
    }

    fun size(mainImage: BufferedImage, watermarkImage: BufferedImage) {
        if (mainImage.width < watermarkImage.width || mainImage.height < watermarkImage.height) {
            println("The watermark's dimensions are larger.")
            exitProcess(0)
        }
    }
}