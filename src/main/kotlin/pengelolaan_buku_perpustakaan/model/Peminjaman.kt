package pengelolaan_buku_perpustakaan.model

data class Peminjaman(
    val id_peminjaman: Int,
    val nama_peminjam: String,
    val kelas: String,
    val id_buku: Int,
    val tanggal_pinjam: String,
    val tanggal_kembali: String,
    val status: String
)