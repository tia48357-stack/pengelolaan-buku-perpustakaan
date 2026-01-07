package pengelolaan_buku_perpustakaan.ui

import pengelolaan_buku_perpustakaan.dao.BukuDAO
import pengelolaan_buku_perpustakaan.model.Buku
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.table.DefaultTableModel

class BukuPanel : JPanel() {

    private val bukuDAO = BukuDAO()

    private val txtCari = JTextField("Cari...")
    private val txtId = JTextField()
    private val txtJudul = JTextField()
    private val txtPengarang = JTextField()
    private val txtPenerbit = JTextField()
    private val txtTahun = JTextField()
    private val txtKategori = JTextField()
    private val txtStok = JTextField()

    private val btnTambah = JButton("Tambah")
    private val btnUbah = JButton("Ubah")
    private val btnHapus = JButton("Hapus")
    private val btnReset = JButton("Reset")

    private val tableModel = DefaultTableModel(
        arrayOf("ID", "Judul Buku", "Pengarang", "Penerbit", "Tahun", "Kategori", "Stok"), 0
    )
    private val table = JTable(tableModel)

    init {
        layout = BorderLayout(15, 15)
        background = Color(236, 240, 245)
        border = BorderFactory.createEmptyBorder(20, 20, 20, 20)

        add(createFormCard(), BorderLayout.WEST)
        add(createTableCard(), BorderLayout.CENTER)

        styleButton()
        loadData()
        initEvent()
        resetForm()
    }

    // ================= FORM CARD =================
    private fun createFormCard(): JPanel {
        val card = JPanel(BorderLayout(10, 10))
        card.preferredSize = Dimension(340, 0)
        card.background = Color.WHITE
        card.border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        )

        val title = JLabel("Formulir Buku")
        title.font = Font("Segoe UI", Font.BOLD, 16)

        val form = JPanel(GridLayout(0, 1, 6, 6))
        form.isOpaque = false

        fun add(label: String, field: JTextField) {
            form.add(JLabel(label))
            form.add(field)
        }

        add("ID Buku", txtId)
        add("Judul Buku", txtJudul)
        add("Pengarang", txtPengarang)
        add("Penerbit", txtPenerbit)
        add("Tahun Terbit", txtTahun)
        add("Kategori", txtKategori)
        add("Stok", txtStok)

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

        val header = JPanel(BorderLayout(10, 10))
        header.isOpaque = false

        val title = JLabel("Data Buku")
        title.font = Font("Segoe UI", Font.BOLD, 16)

        txtCari.foreground = Color.GRAY
        header.add(title, BorderLayout.WEST)
        header.add(txtCari, BorderLayout.EAST)

        table.font = Font("Segoe UI", Font.PLAIN, 13)
        table.rowHeight = 28
        table.tableHeader.font = Font("Segoe UI", Font.BOLD, 13)

        card.add(header, BorderLayout.NORTH)
        card.add(JScrollPane(table), BorderLayout.CENTER)

        return card
    }

    // ================= BUTTON STYLE =================
    private fun styleButton() {
        btnTambah.background = Color(46, 204, 113)
        btnUbah.background = Color(241, 196, 15)
        btnHapus.background = Color(231, 76, 60)
        btnReset.background = Color(189, 195, 199)

        listOf(btnTambah, btnUbah, btnHapus, btnReset).forEach {
            it.foreground = Color.WHITE
            it.isFocusPainted = false
        }
    }

    // ================= EVENT =================
    private fun initEvent() {

        table.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val r = table.selectedRow
                if (r >= 0) {
                    txtId.text = tableModel.getValueAt(r, 0).toString()
                    txtJudul.text = tableModel.getValueAt(r, 1).toString()
                    txtPengarang.text = tableModel.getValueAt(r, 2).toString()
                    txtPenerbit.text = tableModel.getValueAt(r, 3).toString()
                    txtTahun.text = tableModel.getValueAt(r, 4).toString()
                    txtKategori.text = tableModel.getValueAt(r, 5).toString()
                    txtStok.text = tableModel.getValueAt(r, 6).toString()

                    btnTambah.isEnabled = false
                    btnUbah.isEnabled = true
                    btnHapus.isEnabled = true
                }
            }
        })

        btnTambah.addActionListener {
            bukuDAO.insert(getBukuFromForm())
            loadData()
            resetForm()
        }

        btnUbah.addActionListener {
            bukuDAO.update(getBukuFromForm())
            loadData()
            resetForm()
        }

        btnHapus.addActionListener {
            bukuDAO.delete(txtId.text.toInt())
            loadData()
            resetForm()
        }

        btnReset.addActionListener {
            resetForm()
            txtCari.text = ""
            loadData()
        }

        txtCari.addKeyListener(object : java.awt.event.KeyAdapter() {
            override fun keyReleased(e: java.awt.event.KeyEvent) {
                val keyword = txtCari.text.trim()
                if (keyword.isEmpty()) loadData() else loadData(keyword)
            }
        })
    }

    // ================= DATA =================
    private fun loadData() {
        tableModel.rowCount = 0
        bukuDAO.getAll().forEach {
            tableModel.addRow(
                arrayOf(
                    it.id_buku,
                    it.judul_buku,
                    it.pengarang,
                    it.penerbit,
                    it.tahun_terbit,
                    it.kategori,
                    it.stok
                )
            )
        }
    }

    private fun loadData(keyword: String) {
        tableModel.rowCount = 0
        bukuDAO.search(keyword).forEach {
            tableModel.addRow(
                arrayOf(
                    it.id_buku,
                    it.judul_buku,
                    it.pengarang,
                    it.penerbit,
                    it.tahun_terbit,
                    it.kategori,
                    it.stok
                )
            )
        }
    }

    private fun getBukuFromForm(): Buku {
        return Buku(
            txtId.text.toInt(),
            txtJudul.text,
            txtPengarang.text,
            txtPenerbit.text,
            txtTahun.text.toInt(),
            txtKategori.text,
            txtStok.text.toInt()
        )
    }

    private fun resetForm() {
        txtId.text = ""
        txtJudul.text = ""
        txtPengarang.text = ""
        txtPenerbit.text = ""
        txtTahun.text = ""
        txtKategori.text = ""
        txtStok.text = ""

        btnTambah.isEnabled = true
        btnUbah.isEnabled = false
        btnHapus.isEnabled = false
        table.clearSelection()
    }
}
