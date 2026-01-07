package pengelolaan_buku_perpustakaan.ui

import java.awt.*
import javax.swing.JPanel

class GradientPanel : JPanel() {

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g2d = g as Graphics2D
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY
        )

        val width = width
        val height = height

        val color1 = Color(59, 130, 246)   // Biru
        val color2 = Color(34, 197, 94)    // Hijau

        val gradient = GradientPaint(
            0f, 0f, color1,
            width.toFloat(), height.toFloat(), color2
        )

        g2d.paint = gradient
        g2d.fillRect(0, 0, width, height)
    }
}
