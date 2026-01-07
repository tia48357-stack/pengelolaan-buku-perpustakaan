package pengelolaan_buku_perpustakaan.ui

import pengelolaan_buku_perpustakaan.dao.PeminjamanDAO
import pengelolaan_buku_perpustakaan.model.Peminjaman
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableRowSorter

class PeminjamanPanel : JPanel() {

    private val dao = PeminjamanDAO()

    // ================= FORM FIELD =================
    private val txtId = JTextField()
    private val txtNama = JTextField()
    private val txtKelas = JTextField()
    private val txtIdBuku = JTextField()
    private val txtTanggalPinjam = JTextField()
    private val txtTanggalKembali = JTextField()
    private val cbStatus = JComboBox(arrayOf("Dipinjam", "Dikembalikan"))

    private val btnTambah = JButton("Tambah")
    private val btnUbah = JButton("Ubah")
    private val btnHapus = JButton("Hapus")
    private val btnReset = JButton("Reset")

    // ================= SEARCH =================
    private val txtCari = JTextField()

    // ================= TABLE =================
    private val tableModel = DefaultTableModel(
        arrayOf("ID", "Nama", "Kelas", "ID Buku", "Tanggal Pinjam", "Tanggal Kembali", "Status"),
        0
    )
    private val table = JTable(tableModel)
    private val sorter = TableRowSorter(tableModel)

    init {
        layout = BorderLayout(15, 15)
        background = Color(236, 240, 245)
        border = BorderFactory.createEmptyBorder(20, 20, 20, 20)

        table.rowSorter = sorter

        add(createFormCard(), BorderLayout.WEST)
        add(createTableCard(), BorderLayout.CENTER)

        styleTable()
        styleButtons()

        loadData()
        initEvent()
        resetForm()
    }

    // ================= FORM CARD =================
    private fun createFormCard(): JPanel {
        val card = JPanel(BorderLayout())
        card.preferredSize = Dimension(340, 0)
        card.background = Color.WHITE
        card.border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        )

        val title = JLabel("Form Peminjaman")
        title.font = Font("Segoe UI", Font.BOLD, 15)

        val form = JPanel(GridLayout(0, 1, 6, 6))
        form.isOpaque = false

        form.add(JLabel("ID Peminjaman"))
        form.add(txtId)

        form.add(JLabel("Nama Peminjam"))
        form.add(txtNama)

        form.add(JLabel("Kelas"))
        form.add(txtKelas)

        form.add(JLabel("ID Buku"))
        form.add(txtIdBuku)

        form.add(JLabel("Tanggal Pinjam (YYYY-MM-DD)"))
        form.add(txtTanggalPinjam)

        form.add(JLabel("Tanggal Kembali (YYYY-MM-DD)"))
        form.add(txtTanggalKembali)

        form.add(JLabel("Status"))
        form.add(cbStatus)

        val btnPanel = JPanel(GridLayout(1, 4, 8, 8))
        btnPanel.isOpaque = false
        btnPanel.add(btnTambah)
        btnPanel.add(btnUbah)
        btnPanel.add(btnHapus)
        btnPanel.add(btnReset)

        card.add(title, BorderLayout.NORTH)
        card.add(form, BorderLayout.CENTER)
        card.add(btnPanel, BorderLayout.SOUTH)

        return card
    }

    // ================= TABLE CARD =================
    private fun createTableCard(): JPanel {
        val card = JPanel(BorderLayout(10, 10))
        card.background = Color.WHITE
        card.border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        )

        val title = JLabel("Data Peminjaman")
        title.font = Font("Segoe UI", Font.BOLD, 15)

        val searchPanel = JPanel(BorderLayout(8, 8))
        searchPanel.isOpaque = false
        searchPanel.add(JLabel("Cari:"), BorderLayout.WEST)
        searchPanel.add(txtCari, BorderLayout.CENTER)

        card.add(title, BorderLayout.NORTH)
        card.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE)
        card.add(JScrollPane(table), BorderLayout.CENTER)

        return card
    }

    // ================= STYLE =================
    private fun styleButtons() {
        btnTambah.background = Color(46, 204, 113)
        btnUbah.background = Color(241, 196, 15)
        btnHapus.background = Color(231, 76, 60)
        btnReset.background = Color(189, 195, 199)

        listOf(btnTambah, btnUbah, btnHapus, btnReset).forEach {
            it.foreground = Color.WHITE
            it.font = Font("Segoe UI", Font.BOLD, 12)
            it.isFocusPainted = false
        }
    }

    private fun styleTable() {
        table.font = Font("Segoe UI", Font.PLAIN, 13)
        table.rowHeight = 28
        table.tableHeader.font = Font("Segoe UI", Font.BOLD, 13)
        table.fillsViewportHeight = true
        table.selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION
    }

    // ================= EVENT =================
    private fun initEvent() {

        txtCari.document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent) = filter()
            override fun removeUpdate(e: DocumentEvent) = filter()
            override fun changedUpdate(e: DocumentEvent) = filter()

            private fun filter() {
                val text = txtCari.text
                sorter.rowFilter =
                    if (text.isBlank()) null
                    else RowFilter.regexFilter("(?i)$text")
            }
        })

        table.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val r = table.selectedRow
                if (r >= 0) {
                    val row = table.convertRowIndexToModel(r)
                    txtId.text = tableModel.getValueAt(row, 0).toString()
                    txtNama.text = tableModel.getValueAt(row, 1).toString()
                    txtKelas.text = tableModel.getValueAt(row, 2).toString()
                    txtIdBuku.text = tableModel.getValueAt(row, 3).toString()
                    txtTanggalPinjam.text = tableModel.getValueAt(row, 4).toString()
                    txtTanggalKembali.text = tableModel.getValueAt(row, 5).toString()
                    cbStatus.selectedItem = tableModel.getValueAt(row, 6)

                    btnTambah.isEnabled = false
                    btnUbah.isEnabled = true
                    btnHapus.isEnabled = true
                }
            }
        })

        btnTambah.addActionListener {
            if (isFormValid()) {
                dao.insert(getFromForm())
                loadData()
                resetForm()
            }
        }

        btnUbah.addActionListener {
            if (isFormValid()) {
                dao.update(getFromForm())
                loadData()
                resetForm()
            }
        }

        btnHapus.addActionListener {
            txtId.text.toIntOrNull()?.let {
                dao.delete(it)
                loadData()
                resetForm()
            }
        }

        btnReset.addActionListener { resetForm() }
    }

    // ================= DATA =================
    private fun loadData() {
        tableModel.rowCount = 0
        dao.getAll().forEach {
            tableModel.addRow(
                arrayOf(
                    it.id_peminjaman,
                    it.nama_peminjam,
                    it.kelas,
                    it.id_buku,
                    it.tanggal_pinjam,
                    it.tanggal_kembali,
                    it.status
                )
            )
        }
    }

    private fun getFromForm() = Peminjaman(
        txtId.text.toInt(),
        txtNama.text.trim(),
        txtKelas.text.trim(),
        txtIdBuku.text.toInt(),
        txtTanggalPinjam.text.trim(),
        txtTanggalKembali.text.trim(),
        cbStatus.selectedItem.toString()
    )

    private fun resetForm() {
        listOf(txtId, txtNama, txtKelas, txtIdBuku, txtTanggalPinjam, txtTanggalKembali).forEach { it.text = "" }
        cbStatus.selectedIndex = 0
        txtCari.text = ""
        btnTambah.isEnabled = true
        btnUbah.isEnabled = false
        btnHapus.isEnabled = false
        table.clearSelection()
    }

    private fun isFormValid(): Boolean {
        if (
            txtId.text.isBlank() || txtNama.text.isBlank() ||
            txtKelas.text.isBlank() || txtIdBuku.text.isBlank() ||
            txtTanggalPinjam.text.isBlank() || txtTanggalKembali.text.isBlank()
        ) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!")
            return false
        }
        return true
    }
}
