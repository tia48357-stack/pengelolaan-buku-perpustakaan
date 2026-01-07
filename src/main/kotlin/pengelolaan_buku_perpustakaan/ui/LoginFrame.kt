package pengelolaan_buku_perpustakaan.ui

import com.formdev.flatlaf.FlatLightLaf
import java.awt.*
import javax.swing.*

class LoginFrame : JFrame("Login Admin") {

    private val txtUsername = JTextField()
    private val txtPassword = JPasswordField()

    init {
        FlatLightLaf.setup()

        setSize(500, 400)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = true

        // ==== BACKGROUND GRADIENT ====
        contentPane = GradientPanel()
        layout = GridBagLayout()

        add(createLoginCard())
        isVisible = true
    }

    // ================= CARD =================
    private fun createLoginCard(): JPanel {
        val card = JPanel()
        card.preferredSize = Dimension(320, 300)
        card.background = Color(255, 255, 255, 240)
        card.layout = BoxLayout(card, BoxLayout.Y_AXIS)
        card.border = BorderFactory.createEmptyBorder(20, 30, 20, 30)

        val title = JLabel("Sistem Perpustakaan", JLabel.CENTER)
        title.font = Font("Segoe UI", Font.BOLD, 18)
        title.alignmentX = Component.CENTER_ALIGNMENT

        val icon = JLabel("\uD83D\uDCD6", JLabel.CENTER)
        icon.font = Font("Segoe UI Emoji", Font.PLAIN, 48)
        icon.alignmentX = Component.CENTER_ALIGNMENT

        card.add(title)
        card.add(Box.createVerticalStrut(10))
        card.add(icon)
        card.add(Box.createVerticalStrut(20))

        card.add(createField("Username", txtUsername))
        card.add(Box.createVerticalStrut(10))
        card.add(createField("Password", txtPassword))
        card.add(Box.createVerticalStrut(20))
        card.add(createLoginButton())

        return card
    }

    // ================= FIELD =================
    private fun createField(label: String, field: JComponent): JPanel {
        val panel = JPanel(BorderLayout(5, 5))
        panel.isOpaque = false

        val lbl = JLabel(label)
        lbl.font = Font("Segoe UI", Font.PLAIN, 13)

        field.preferredSize = Dimension(200, 35)
        field.font = Font("Segoe UI", Font.PLAIN, 14)

        panel.add(lbl, BorderLayout.NORTH)
        panel.add(field, BorderLayout.CENTER)

        return panel
    }

    // ================= BUTTON =================
    private fun createLoginButton(): JButton {
        val btn = JButton("Login")
        btn.background = Color(59, 130, 246)
        btn.foreground = Color.WHITE
        btn.font = Font("Segoe UI", Font.BOLD, 14)
        btn.isFocusPainted = false
        btn.preferredSize = Dimension(200, 40)
        btn.alignmentX = Component.CENTER_ALIGNMENT

        btn.addActionListener {
            prosesLogin()
        }

        return btn
    }

    // ================= LOGIC LOGIN =================
    private fun prosesLogin() {
        val username = txtUsername.text
        val password = String(txtPassword.password)

        // LOGIN SEMENTARA
        if (username == "admin" && password == "admin123") {
            MainFrame()
            dispose()
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Username atau password salah",
                "Login Gagal",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }
}
