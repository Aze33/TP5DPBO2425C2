# Janji

Saya Zahran Zaidan Saputra dengan NIM 2415429 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain Pemrograman Berorientasi Objek (DPBO) untuk keberkahan-Nya, maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain

### Produk Atribut

* **`id`** : Kode unik gamenya.
* **`nama`** : Judul gamenya.
* **`harga`** : Harga jual gamenya.
* **`kategori`** : Genre gamenya (RPG, Action, dll.).
* **`ratingUsia`** : Batas usia untuk game tersebut.

### Method

* **`Getter`**
* **`Setter`**

### Elemen Utama GUI

* **`JPanel` (`mainPanel`)**: Panel utama yang menjadi wadah untuk semua komponen lainnya.
* **`JLabel`**: Digunakan untuk menampilkan teks statis seperti "ID Produk", "Nama", "Harga", dll.
* **`JTextField`**: Digunakan sebagai kolom input untuk ID, Nama, dan Harga game.
* **`JComboBox`**: Digunakan untuk menampilkan daftar pilihan kategori game yang bisa dipilih.
* **`JRadioButton` & `ButtonGroup`**: Tiga `JRadioButton` ("Semua Umur", "Remaja", "Dewasa") yang dikelompokkan oleh sebuah `ButtonGroup` untuk memastikan hanya satu pilihan rating usia yang bisa aktif.
* **`JButton`**: Tombol-tombol interaktif: "Add/Update", "Delete", dan "Cancel".
* **`JTable` (`productTable`)**: Komponen utama untuk menampilkan semua data game dalam bentuk tabel yang rapi.

# Penjelasan Alur Program

1.  **Koneksi Awal**: Saat aplikasi pertama kali dijalankan, objek dari kelas `Database` akan dibuat, yang secara otomatis langsung membuka koneksi ke server MySQL dan database `db_products`.

2.  **Menampilkan Data (Read)**: Setelah koneksi berhasil, method `setTable()` akan dieksekusi. Method ini menjalankan query `SELECT * FROM product` untuk mengambil semua data dari tabel `product` di database.Data yang didapat kemudian ditampilkan di `JTable`.

3.  **Menambah Data (Create)**:
    * Pengguna mengisi semua kolom pada form input.
    * Saat tombol "Add" ditekan, program akan melakukan dua validasi penting sesuai tugas:
        * Memastikan tidak ada kolom input yang kosong.
        * Memastikan ID yang dimasukkan belum ada di database (mencegah duplikat).
    * Jika data valid, program akan membuat perintah `INSERT INTO` dan mengirimkannya ke database untuk disimpan. Tabel kemudian akan dimuat ulang untuk menampilkan data baru.

4.  **Mengubah Data (Update)**:
    * Pengguna mengklik salah satu baris di tabel. Data dari baris tersebut akan ditampilkan di form input.
    * Setelah pengguna mengubah data dan menekan tombol "Update", program akan membuat perintah `UPDATE` yang menargetkan data berdasarkan `id`-nya. Data di database akan diperbarui, dan tabel akan dimuat ulang.

5.  **Menghapus Data (Delete)**:
    * Pengguna memilih data yang akan dihapus dengan mengklik baris di tabel.
    * Saat tombol "Delete" ditekan, sebuah dialog konfirmasi akan muncul.
    * Jika disetujui, program akan membuat perintah `DELETE FROM` berdasarkan `id` produk untuk menghapus data dari database. Tabel kemudian akan dimuat ulang.

# Dokumentasi

### Add Data

![AddData](https://github.com/user-attachments/assets/c34f8d2e-8e34-4cf4-ae42-3c69dc15c0aa)

<img width="812" height="586" alt="image" src="https://github.com/user-attachments/assets/36b09909-3c53-4270-94b2-bc21450fc65f" />

### Update

![UpdateData](https://github.com/user-attachments/assets/68a699a9-e52f-4d40-9c29-f4beb1f38be8)

<img width="818" height="578" alt="image" src="https://github.com/user-attachments/assets/581f3beb-dfaa-40b3-a456-2a15f37c3ca7" />

### Delete

![DeleteData](https://github.com/user-attachments/assets/4b710131-357b-4870-b0b4-db7090ae6eda)

<img width="814" height="538" alt="image" src="https://github.com/user-attachments/assets/5839ae09-8c41-42f4-9ef7-d8a8d985a4b9" />

### Prompt error jika salah satu tabel kosong

![Failed1](https://github.com/user-attachments/assets/247b21db-3494-4165-bf39-b317067981bb)

### Prompt error jika ada ID yang sama

![failed2](https://github.com/user-attachments/assets/d3dd1fad-955c-4342-a4fe-18cd812e09de)

### Prompt error jika Harga yang dimasukkan berupa huruf bukan angka

![failed3](https://github.com/user-attachments/assets/58d74d7c-ee1e-43f1-8b60-874a64b2698f)








