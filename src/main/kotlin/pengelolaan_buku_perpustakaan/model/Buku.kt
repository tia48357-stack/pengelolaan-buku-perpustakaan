package pengelolaan_buku_perpustakaan.model

data class Buku(
    val id_buku: Int,
    val judul_buku: String,
    val pengarang: String,
    val penerbit: String,
    val tahun_terbit: Int,
    val kategori: String,
    val stok: Int
)