package dev.dayoung.adventofcode.structures

import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Path2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Visualizes an irregular shape defined by a list of (x, y) points
 * within a large grid and saves it as a PNG image.
 *
 * @param points The list of (x, y) coordinates defining the irregular shape.
 * @param gridSize The size of the square grid (e.g., 100000 for 100k x 100k).
 * @param outputFilePath The path where the final PNG image will be saved.
 * @param imageSize The desired size of the output image (e.g., 1000x1000 pixels).
 */
fun visualizeGridShape(
    points: List<Vec2i>,
    gridSize: Int,
    outputFilePath: String,
    imageSize: Int = 1000
) {
    // 1. Create the Image Buffer
    // TYPE_INT_RGB is a common format for color images
    val image = BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB)

    // 2. Get the Graphics Context (The "paint brush")
    val g2d: Graphics2D = image.createGraphics()

    // --- Configuration ---

    // Fill the background white
    g2d.color = Color.WHITE
    g2d.fillRect(0, 0, imageSize, imageSize)

    // Calculate the scaling factor
    // This maps the 100,000 coordinates to the 1,000 pixel image
    val scaleFactor: Double = imageSize.toDouble() / gridSize.toDouble()

    // --- Draw the Shape ---
    val i = Vec2i(84529, 84413)
    val j = Vec2i(16026, 14915)
    val largeAreaPoints = listOf(i, Vec2i(j.x, i.y), j, Vec2i(i.x, j.y))

    val outline = pathFromPoints(points, scaleFactor)
    val largeArea = pathFromPoints(largeAreaPoints, scaleFactor)


    // Set the fill color and draw the filled shape
//    g2d.color = Color.BLUE
//    g2d.fill(outline)

    // Set the outline color and draw the boundary
    g2d.color = Color.BLACK
    g2d.stroke = java.awt.BasicStroke(2f) // Set line thickness
    g2d.draw(outline)

//    g2d.color = Color.RED
//    g2d.fill(largeArea)
//    g2d.draw(largeArea)

    // 3. Clean up and Save
    g2d.dispose() // Release the graphics resources

    try {
        val outputFile = File(outputFilePath)
        ImageIO.write(image, "png", outputFile)
        println("Image saved successfully to: ${outputFile.absolutePath}")
    } catch (e: Exception) {
        System.err.println("Error saving image: ${e.message}")
    }
}

fun pathFromPoints(points: List<Vec2i>, scaleFactor: Double): Path2D {
    // Path2D is efficient for defining a series of connected lines (a polygon)
    val path = Path2D.Double()

    // Move to the first point, scaled
    if (points.isNotEmpty()) {
        val firstPoint = points.first()
        // Map the grid coordinate (0 to 100k) to the image pixel (0 to 1000)
        val x = (firstPoint.x * scaleFactor)
        val y = (firstPoint.y * scaleFactor)
        println("start at $x $y")
        path.moveTo(x, y)
    }

    // Connect the remaining points
    points.drop(1).forEach { (gx, gy) ->
        val x = (gx * scaleFactor)
        val y = (gy * scaleFactor)
        println("line to $x $y")
        path.lineTo(x, y)
    }


    // Close the path to form a polygon
//    path.closePath()
    return path
}
