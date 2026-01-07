package pengelolaan_buku_perpustakaan.ui

import java.awt.*
import javax.swing.*

class MainFrame : JFrame("Pengelolaan Buku Perpustakaan") {

    private val contentPanel = JPanel(BorderLayout())

    init {
        setSize(1200, 700)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()

        add(createSidebar(), BorderLayout.WEST)
        add(contentPanel, BorderLayout.CENTER)

        // Default halaman
        showDashboard()

        isVisible = true
    }

    // =====================================================
    // FUNGSI PEMANGGIL IKON
    // =====================================================
    private fun icon(name: String, size: Int = 18): ImageIcon {
        val url = javaClass.getResource("/icons/$name")
            ?: throw RuntimeException("Icon $name tidak ditemukan")
        val img = ImageIcon(url).image
        val scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH)
        return ImageIcon(scaled)
    }

    // =====================================================
    // SIDEBAR
    // =====================================================
    private fun createSidebar(): JPanel {
        val sidebar = JPanel()
        sidebar.preferredSize = Dimension(240, height)
        sidebar.background = Color(52, 73, 94)
        sidebar.layout = BoxLayout(sidebar, BoxLayout.Y_AXIS)

        // HEADER
        val title = JLabel("  Perpustakaan", icon("book.png", 22), JLabel.LEFT)
        title.font = Font("Segoe UI", Font.BOLD, 18)
        title.foreground = Color.WHITE
        title.border = BorderFactory.createEmptyBorder(20, 20, 20, 10)

        sidebar.add(title)
        sidebar.add(Box.createVerticalStrut(15))

        // MENU
        sidebar.add(navButton("Dashboard", "dashboard.png") { showDashboard() })
        sidebar.add(Box.createVerticalStrut(8))

        sidebar.add(navButton("Data Buku", "data.png") { showDataBuku() })
        sidebar.add(Box.createVerticalStrut(8))

        sidebar.add(navButton("Peminjaman", "pinjam.png") { showPeminjaman() })
        sidebar.add(Box.createVerticalStrut(8))

        sidebar.add(navButton("Laporan", "laporan.png") { showLaporan() })

        sidebar.add(Box.createVerticalGlue())

        // LOGOUT
        sidebar.add(navButton("Logout", "logout.png") {
            dispose()
            LoginFrame()
        })

        sidebar.add(Box.createVerticalStrut(20))

        return sidebar
    }

    // =====================================================
    // TOMBOL NAVIGASI
    // =====================================================
    private fun navButton(
        text: String,
        iconName: String,
        action: () -> Unit
    ): JButton {
        val btn = JButton(text, icon(iconName))
        btn.maximumSize = Dimension(220, 46)
        btn.horizontalAlignment = SwingConstants.LEFT
        btn.iconTextGap = 14

        btn.font = Font("Segoe UI", Font.PLAIN, 15)
        btn.foreground = Color.WHITE
        btn.background = Color(52, 73, 94)
        btn.isFocusPainted = false
        btn.border = BorderFactory.createEmptyBorder(10, 20, 10, 10)

        btn.addActionListener { action() }

        return btn
    }

    // =====================================================
    // NAVIGASI HALAMAN (INI YANG PALING PENTING)
    // =====================================================
    private fun showDashboard() {
        contentPanel.removeAll()
        contentPanel.add(DashboardPanel(), BorderLayout.CENTER)
        refresh()
    }

    private fun showDataBuku() {
        contentPanel.removeAll()
        contentPanel.add(BukuPanel(), BorderLayout.CENTER)
        refresh()
    }

    private fun showPeminjaman() {
        contentPanel.removeAll()
        contentPanel.add(PeminjamanPanel(), BorderLayout.CENTER)
        refresh()
    }

    private fun showLaporan() {
        contentPanel.removeAll()
        contentPanel.add(LaporanPanel(), BorderLayout.CENTER)
        refresh()
    }

    private fun refresh() {
        contentPanel.revalidate()
        contentPanel.repaint()
    }
}
