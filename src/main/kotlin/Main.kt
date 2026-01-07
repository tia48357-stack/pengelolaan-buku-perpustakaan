package pengelolaan_buku_perpustakaan

import com.formdev.flatlaf.FlatLightLaf
import pengelolaan_buku_perpustakaan.ui.LoginFrame
import javax.swing.SwingUtilities
import javax.swing.UIManager

fun main() {
    UIManager.setLookAndFeel(FlatLightLaf())
    SwingUtilities.invokeLater {
        LoginFrame()
    }
}