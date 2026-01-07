package pengelolaan_buku_perpustakaan.ui

import pengelolaan_buku_perpustakaan.dao.PeminjamanDAO
import java.awt.*
import java.io.FileOutputStream
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableRowSorter
import javax.swing.RowFilter
import java.awt.Font

// ===== PDF (OpenPDF) =====
import com.lowagie.text.*
import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.pdf.PdfWriter

// ===== Excel (Apache POI) =====
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class LaporanPanel : JPanel() {

    private val dao = PeminjamanDAO()

    // ================= TABLE =================
    private val tableModel = DefaultTableModel(
        arrayOf(
            "ID",
            "Nama",
            "Kelas",
            "ID Buku",
            "Tanggal Pinjam",
            "Tanggal Kembali",
            "Status"
        ), 0
    )
    private val table = JTable(tableModel)
    private val sorter = TableRowSorter(tableModel)

    // ================= FILTER =================
    private val txtCari = JTextField()
    private val cbStatus = JComboBox(arrayOf("Semua", "Dipinjam", "Dikembalikan"))

    // ================= BUTTON =================
    private val btnExcel = JButton("Export Excel")
    private val btnPdf = JButton("Export PDF")

    init {
        layout = BorderLayout(10, 10)
        border = BorderFactory.createEmptyBorder(15, 15, 15, 15)

        table.rowSorter = sorter
        table.fillsViewportHeight = true

        add(createHeader(), BorderLayout.NORTH)
        add(JScrollPane(table), BorderLayout.CENTER)

        loadData()
        initEvent()
    }

    // ================= HEADER =================
    private fun createHeader(): JPanel {
        val panel = JPanel(BorderLayout(10, 10))

        val title = JLabel("Laporan Peminjaman")
        title.font = Font("Segoe UI", Font.BOLD, 20)

        val filterPanel = JPanel(GridLayout(1, 4, 10, 0))
        filterPanel.add(JLabel("Cari Nama:"))
        filterPanel.add(txtCari)
        filterPanel.add(JLabel("Status:"))
        filterPanel.add(cbStatus)

        val btnPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        btnPanel.add(btnExcel)
        btnPanel.add(btnPdf)

        panel.add(title, BorderLayout.NORTH)
        panel.add(filterPanel, BorderLayout.CENTER)
        panel.add(btnPanel, BorderLayout.SOUTH)

        return panel
    }

    // ================= EVENT =================
    private fun initEvent() {

        txtCari.document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent) = filter()
            override fun removeUpdate(e: DocumentEvent) = filter()
            override fun changedUpdate(e: DocumentEvent) = filter()
        })

        cbStatus.addActionListener { filter() }

        btnExcel.addActionListener { exportExcel() }
        btnPdf.addActionListener { exportPdf() }
    }

    // ================= FILTER =================
    private fun filter() {
        val keyword = txtCari.text.lowercase()
        val status = cbStatus.selectedItem.toString()

        sorter.rowFilter = object : RowFilter<DefaultTableModel, Int>() {
            override fun include(entry: Entry<out DefaultTableModel, out Int>): Boolean {
                val nama = entry.getStringValue(1).lowercase()
                val statusRow = entry.getStringValue(6)

                val cocokNama = nama.contains(keyword)
                val cocokStatus = status == "Semua" || statusRow == status

                return cocokNama && cocokStatus
            }
        }
    }

    // ================= LOAD DATA =================
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

    // ================= EXPORT EXCEL =================
    private fun exportExcel() {
        val chooser = JFileChooser()
        chooser.selectedFile = java.io.File("laporan_peminjaman.xlsx")

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Laporan Peminjaman")

            val header = sheet.createRow(0)
            for (i in 0 until table.columnCount) {
                header.createCell(i).setCellValue(table.getColumnName(i)
                )
            }

            var rowIndex = 1
            for (i in 0 until table.rowCount) {
                val row = sheet.createRow(rowIndex++)
                for (j in 0 until table.columnCount) {
                    row.createCell(j).setCellValue(
                        table.getValueAt(i, j).toString()
                    )
                }
            }

            FileOutputStream(chooser.selectedFile).use {
                workbook.write(it)
            }
            workbook.close()

            JOptionPane.showMessageDialog(this, "Export Excel berhasil!")
        }
    }

    // ================= EXPORT PDF =================
    private fun exportPdf() {
        val chooser = JFileChooser()
        chooser.selectedFile = java.io.File("laporan_peminjaman.pdf")

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            val document = Document(PageSize.A4.rotate())
            PdfWriter.getInstance(document, FileOutputStream(chooser.selectedFile))
            document.open()

            val titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16f)
            document.add(Paragraph("Laporan Peminjaman", titleFont))
            document.add(Paragraph(" "))

            val pdfTable = PdfPTable(table.columnCount)
            pdfTable.widthPercentage = 100f

            for (i in 0 until table.columnCount) {
                pdfTable.addCell(Phrase(table.getColumnName(i)))
            }

            for (i in 0 until table.rowCount) {
                for (j in 0 until table.columnCount) {
                    pdfTable.addCell(table.getValueAt(i, j).toString())
                }
            }

            document.add(pdfTable)
            document.close()

            JOptionPane.showMessageDialog(this, "Export PDF berhasil!")
        }
    }
}
