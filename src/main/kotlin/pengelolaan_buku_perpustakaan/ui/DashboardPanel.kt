package pengelolaan_buku_perpustakaan.ui

import java.awt.*
import javax.swing.*
import javax.swing.table.DefaultTableModel

class DashboardPanel : JPanel() {

    init {
        layout = BorderLayout(15, 15)
        background = Color(236, 240, 245)
        border = BorderFactory.createEmptyBorder(20, 20, 20, 20)

        add(createStatsPanel(), BorderLayout.NORTH)
        add(createTablePanel(), BorderLayout.CENTER)
    }

    // =====================================================
    // ICON LOADER
    // =====================================================
    private fun icon(name: String, size: Int = 22): ImageIcon {
        val url = javaClass.getResource("/icons/$name")
            ?: throw RuntimeException("Icon $name tidak ditemukan")
        val img = ImageIcon(url).image
        val scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH)
        return ImageIcon(scaled)
    }

    // =====================================================
    // STAT CARDS
    // =====================================================
    private fun createStatsPanel(): JPanel {
        val panel = JPanel(GridLayout(1, 4, 15, 15))
        panel.isOpaque = false

        panel.add(createCard(
            title = "Total Buku",
            value = "120",
            iconName = "book.png",
            color = Color(93, 135, 197)
        ))

        panel.add(createCard(
            title = "Buku Dipinjam",
            value = "25",
            iconName = "pinjam.png",
            color = Color(243, 156, 18)
        ))

        panel.add(createCard(
            title = "Total Peminjaman",
            value = "240",
            iconName = "users.png",
            color = Color(46, 204, 113)
        ))

        panel.add(createCard(
            title = "Dikembalikan",
            value = "215",
            iconName = "check.png",
            color = Color(52, 152, 219)
        ))

        return panel
    }

    private fun createCard(
        title: String,
        value: String,
        iconName: String,
        color: Color
    ): JPanel {

        val panel = JPanel(BorderLayout())
        panel.background = color
        panel.border = BorderFactory.createEmptyBorder(18, 18, 18, 18)

        val lblIcon = JLabel(icon(iconName, 26))

        val lblTitle = JLabel(title)
        lblTitle.foreground = Color.WHITE
        lblTitle.font = Font("Segoe UI", Font.PLAIN, 15)

        val lblValue = JLabel(value)
        lblValue.foreground = Color.WHITE
        lblValue.font = Font("Segoe UI", Font.BOLD, 30)

        val header = JPanel(FlowLayout(FlowLayout.LEFT, 8, 0))
        header.isOpaque = false
        header.add(lblIcon)
        header.add(lblTitle)

        panel.add(header, BorderLayout.NORTH)
        panel.add(lblValue, BorderLayout.CENTER)

        return panel
    }

    // =====================================================
    // TABLE PANEL
    // =====================================================
    private fun createTablePanel(): JPanel {
        val panel = JPanel(BorderLayout(10, 10))
        panel.background = Color.WHITE
        panel.border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        )

        val title = JLabel("Data Peminjaman Terbaru")
        title.font = Font("Segoe UI", Font.BOLD, 16)

        val columns = arrayOf(
            "ID",
            "Nama Peminjam",
            "Judul Buku",
            "Tanggal Pinjam",
            "Status"
        )

        val model = DefaultTableModel(columns, 0)

        // Dummy data (bisa diganti DAO)
        model.addRow(arrayOf("17", "Andi Pratama", "Pemrograman Kotlin", "2026-01-01", "Dipinjam"))
        model.addRow(arrayOf("27", "Siti Aisyah", "Basis Data SQL", "2025-12-20", "Dikembalikan"))
        model.addRow(arrayOf("29", "Budi Santoso", "Algoritma Pemrograman", "2025-12-18", "Dipinjam"))
        model.addRow(arrayOf("30", "Dewi Lestari", "Desain UI/UX", "2025-12-17", "Dipinjam"))

        val table = JTable(model)
        table.font = Font("Segoe UI", Font.PLAIN, 13)
        table.rowHeight = 28
        table.tableHeader.font = Font("Segoe UI", Font.BOLD, 13)
        table.fillsViewportHeight = true

        val scrollPane = JScrollPane(table)

        val btnAll = JButton("Lihat Semua")
        btnAll.font = Font("Segoe UI", Font.PLAIN, 12)

        val bottom = JPanel(FlowLayout(FlowLayout.RIGHT))
        bottom.isOpaque = false
        bottom.add(btnAll)

        panel.add(title, BorderLayout.NORTH)
        panel.add(scrollPane, BorderLayout.CENTER)
        panel.add(bottom, BorderLayout.SOUTH)

        return panel
    }
}
