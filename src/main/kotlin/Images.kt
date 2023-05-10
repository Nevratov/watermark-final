import java.awt.image.BufferedImage
import java.io.File

abstract class Images(val name: String) {
    val file: File = File(name)
    lateinit var buffered: BufferedImage
}